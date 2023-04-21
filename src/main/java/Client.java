import Exceptions.TypeError;
import Terms.Record;
import Terms.*;
import TypeChecker.Jatai;
import Types.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Client {

    public static void basicTypeExample(){
        Jatai typeSystem = new Jatai();

        Int int1 = new Int(1);
        System.out.println(String.format("Term: %s \t Type: %s", int1, typeSystem.inferType(int1)));
        assertTrue(typeSystem.checkType(int1, new TInt()));

        Bool bool1 = new Bool(true);
        System.out.println(String.format("Term: %s \t Type: %s", bool1, typeSystem.inferType(bool1)));
        assertTrue(typeSystem.checkType(bool1, new TBool()));

        Empty empty1 = new Empty();
        System.out.println(String.format("Term: %s \t Type: %s", empty1, typeSystem.inferType(empty1)));
        assertTrue(typeSystem.checkType(empty1, new TUnit()));
    }

    public static void abstractionExample() {
        Jatai typeSystem = new Jatai();

        // λ x.x
        Variable x = new Variable("x");
        TFunction absType1 = new TFunction(new TInt(), new TInt());
        Abstraction abs1 = new Abstraction("x", x, absType1);

        System.out.println(String.format("Term: %s \t Type: %s", abs1, typeSystem.inferType(abs1)));
        assertTrue(typeSystem.checkType(abs1, new TFunction(new TInt(), new TInt())));

        // (λ x.x) 1
        Application app1 = new Application(abs1, new Int(1));
        System.out.println(String.format("Term: %s \t Type: %s", app1, typeSystem.inferType(app1)));
        assertTrue(typeSystem.checkType(app1, new TInt()));

        Application app2 = new Application(abs1, app1);
        System.out.println(String.format("Term: %s \t Type: %s", app2, typeSystem.inferType(app2)));
        assertTrue(typeSystem.checkType(app2, new TInt()));
    }

    public static void annotationExample() {
        Jatai typeSystem = new Jatai();

        // if true then 1 else 2
        Conditional cond1 = new Conditional(new Bool(true), new Int(1), new Int(2));
        assertThrows(TypeError.class, () -> {
            typeSystem.inferType(cond1);
        });
        // (if true then 1 else 2) : Int
        Annotation condAnn1 = new Annotation(cond1, new TInt());
        System.out.println(String.format("Term: %s \t Type: %s", condAnn1, typeSystem.inferType(condAnn1)));
    }

    public static void letExample() {
        Jatai typeSystem = new Jatai();

        Variable z = new Variable("z");
        Annotation zAnn = new Annotation(z, new TInt());
        assertFalse(typeSystem.isTyped(zAnn));

        Type absType = new TFunction(new TInt(), new TInt());
        // if true then (λx.x)(1) else z
        Conditional cond1 = new Conditional(new Bool(true),
                new Application(new Abstraction("x", new Variable("x"), absType), new Int(1)), z);
        // 1. conditionals do not have an inference mode, cannot be typed without annotations 2. z is a free variable
        assertFalse(typeSystem.isTyped(cond1));

        // workaround
        Abstraction abs1 = new Abstraction("z", cond1, new TFunction(new TInt(), new TInt()));
        System.out.println(String.format("Term: %s \t Type: %s", abs1, typeSystem.inferType(abs1)));

        // let z=2 in if true then (λx.x)(1) else z
        Let let1 = new Let("z", new Int(2), cond1);
        Annotation letAnn1 = new Annotation(let1, new TInt());
        System.out.println(String.format("Term: %s \t Type: %s", letAnn1, typeSystem.inferType(letAnn1)));
    }

    public static void recordExample() {
        Jatai typeSystem = new Jatai();

        // int
        Int int1 = new Int(99);
        // λx.x
        Abstraction abs1 = new Abstraction("x", new Variable("x"), new TFunction(new TInt(), new TInt()));
        // let s=1 in (λz.z) (s)
        Let let1 = new Let("s", new Int(1),
                new Application(
                        new Abstraction("z", new Variable("z"), new TFunction(new TInt(), new TInt())),
                        new Variable("s")));
        // (let s=1 in (λz.z) (s)) : Int
        Annotation letAnn1 = new Annotation(let1, new TInt());

        // records: {l1=99, l2=λx.x, l3=(let s=1 in (λz.z) (s)) : Int}
        HashMap<String, Term> records1 = new HashMap<>();
        records1.put("l1", int1);
        records1.put("l2", abs1);
        records1.put("l3", letAnn1);
        Record re1 = new Record(records1);
        Type recordInferredType = typeSystem.inferType(re1);
        System.out.println(String.format("Term: %s \t Type: %s", re1, recordInferredType));

        Type t1 = typeSystem.inferType(int1);
        Type t2 = typeSystem.inferType(abs1);
        Type t3 = typeSystem.inferType(letAnn1);

        // subtyping
        HashMap<String, Type> superTypeMap1 = new HashMap<>();
        superTypeMap1.put("l1", t1);
        superTypeMap1.put("l2", t2);
        TRecord superType1 = new TRecord(superTypeMap1);

        HashMap<String, Type> superTypeMap2 = new HashMap<>();
        superTypeMap2.put("l3", t3);
        superTypeMap2.put("l2", t2);
        superTypeMap2.put("l1", t1);
        TRecord superType2 = new TRecord(superTypeMap2);

        // reflexive
        assertTrue(recordInferredType.typeEquals(recordInferredType));
        assertTrue(recordInferredType.hasSubtype(recordInferredType));
        // subtyping of records
        assertTrue(superType1.hasSubtype(recordInferredType));
        assertTrue(superType2.hasSubtype(recordInferredType));
        // transitive
        assertTrue(superType1.hasSubtype(superType2) && superType2.hasSubtype(recordInferredType) && superType1.hasSubtype(recordInferredType));
    }

    public static void abstractionSubtypingExample(){
        Jatai typeSystem = new Jatai();

        Bool bool1 = new Bool(false);
        Abstraction abs1 = new Abstraction("x", new Variable("x"), new TFunction(new TBool(), new TBool()));

        HashMap<String, Term> re1map = new HashMap<>();
        re1map.put("l1", bool1);
        re1map.put("l2", abs1);
        Record re1 = new Record(re1map);

        HashMap<String, Term> re2map = new HashMap<>();
        re2map.put("l1", new Int(999));
        re2map.put("l2", new Int(892));
        Record re2 = new Record(re2map);

        HashMap<String, Term> re3map = new HashMap<>();
        re3map.put("l2", abs1);
        re3map.put("l1", bool1);
        re3map.put("l3", new Int(2));
        Record re3 = new Record(re3map);

        HashMap<String, Term> re4map = new HashMap<>();
        Record re4 = new Record(re4map);

        Type re1Type = typeSystem.inferType(re1);
        Type re2Type = typeSystem.inferType(re2);
        Type re3Type = typeSystem.inferType(re3);
        Type re4Type = typeSystem.inferType(re4);

        // re3 <: re1; re2 <: re4
        assertTrue(re1Type.hasSubtype(re3Type) && re4Type.hasSubtype(re2Type));

        // λre1.re2
        Abstraction lam1 = new Abstraction("re1", re2, new TFunction(re1Type, re2Type));
        Type result1 = typeSystem.inferType(lam1);
        // λre3.re4
        Abstraction lam2 = new Abstraction("re3", re4, new TFunction(re3Type, re4Type));
        Type result2 = typeSystem.inferType(lam2);
        System.out.println(String.format("Term: %s \t Type: %s", lam1, result1));
        System.out.println(String.format("Term: %s \t Type: %s", lam2, result2));
        // λre3.re4 <: λre1.re2
        assertTrue(result1.hasSubtype(result2));
    }

    public static void main(String[] args) {
        //basicTypeExample();
        //abstractionExample();
        //annotationExample();
        //letExample();
        //recordExample();
        abstractionSubtypingExample();
    }
}

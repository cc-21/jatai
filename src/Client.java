import Terms.*;
import TypeChecker.Jatai;
import Types.*;

import java.util.HashMap;

public class Client {
    public static void main(String[] args) {
        Jatai jatai = new Jatai();

        // base types
        Variable v1 = new Variable("meow");
        jatai.storeInContext("meow", new TUnit());
        printTypeInformation(v1, jatai, new TUnit());

        Int int1 = new Int(1);
        printTypeInformation(int1, jatai, new TInt());

        Bool bool1 = new Bool(true);
        printTypeInformation(bool1, jatai, new TBool());

        Empty empty1 = new Empty();
        printTypeInformation(empty1, jatai, new TUnit());

        // Abstraction
        Variable x = new Variable("x");
        Abstraction abs1 = new Abstraction(x, x, new Annotation(x, new TInt()), new Annotation(x, new TInt()));
        printTypeInformation(abs1, jatai, new TFunction(new TInt(), new TInt()));

        // Application
        Application app1 = new Application(abs1, int1);
        printTypeInformation(app1, jatai, new TInt());

        // Let
        Variable z = new Variable("z");
        Let let1 = new Let("z", new Int(1), new Abstraction(z, z, new Annotation(z, new TInt()), new Annotation(z, new TInt())));
        printTypeInformation(let1, jatai, new TFunction(new TInt(), new TInt()));

        // records
        HashMap<String, Term> records1 = new HashMap<>();
        Variable y = new Variable("y");
        jatai.storeInContext("y", new TInt());
        records1.put("l1", v1);
        records1.put("l2", y);
        records1.put("l3", let1);
        Terms.Record re1 = new Terms.Record(records1);

        HashMap<String, Type> recordTypeMap = new HashMap<>();
        Type t1 = v1.inferType(jatai);
        Type t2 = y.inferType(jatai);
        Type t3 = let1.inferType(jatai);
        recordTypeMap.put("l1", t1);
        recordTypeMap.put("l2", t2);
        recordTypeMap.put("l3", t3);
        TRecord recordType = new TRecord(recordTypeMap);

        printTypeInformation(re1, jatai, recordType);

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

        Type recordInferredType = re1.inferType(jatai);
        System.out.println(recordInferredType);

        // reflexive
        System.out.println(recordType.typeEquals(recordInferredType));
        System.out.println(recordType.hasSubtype(recordInferredType));
        // subtyping of records
        System.out.println(superType1.hasSubtype(recordInferredType));
        System.out.println(superType2.hasSubtype(recordInferredType));
        // transitive
        System.out.println(superType1.hasSubtype(superType2) && superType2.hasSubtype(recordInferredType) && superType1.hasSubtype(recordInferredType));

        // testing
        Variable w = new Variable("w");
        Abstraction abs2 = new Abstraction(w, w, new Annotation(w, new TUnit()), new Annotation(w, new TUnit()));
        Type funcType2 = new TFunction(new TUnit(), new TUnit());
        printTypeInformation(abs2, jatai, funcType2);

        // does not allow duplicate name
        Application app2 = new Application(abs2, new Variable("w"));
        printTypeInformation(app2, jatai, null);

        Variable k = new Variable("k");
        Application app3 = new Application(abs2, k);
        Abstraction abs3 = new Abstraction(k, app3, new Annotation(k, new TUnit()), new Annotation(app3, new TUnit()));
        printTypeInformation(abs3, jatai, funcType2);
    }

    private static void printTypeInformation(Term aTerm, Jatai checker, Type typeToCheck) {
        System.out.println(String.format("Term: %s", aTerm));
        System.out.println(String.format("Is the term well-typed: %b", aTerm.isTyped(checker)));
        System.out.println(String.format("Type: %s", aTerm.inferType(checker)));
        System.out.println(String.format("Check the term %s with the type %s: %b \n", aTerm, typeToCheck, aTerm.checkType(checker, typeToCheck)));
    }
}

package TypeChecker;

import Terms.*;
import Terms.Bool;
import Terms.Record;
import Types.Type;

import java.util.HashMap;

public interface Visitor {
    static final HashMap<String, Type> typingContext = new HashMap<>();

    public Type inferAnnotationType(Annotation aTerm);

    public boolean checkAnnotationType(Type t, Annotation aTerm);

    public Type inferRecordType(Record aTerm);

    public boolean checkRecordType(Type t, Record aTerm);

    public Type inferLetType(Let aTerm);

    public boolean checkLetType(Type t, Let aTerm);

    public Type inferApplicationType(Application aTerm);

    public boolean checkApplicationType(Type t, Application aTerm);

    public Type inferAbstractionType(Abstraction aTerm);

    public boolean checkAbstractionType(Type t, Abstraction aTerm);

    public Type inferVariableType(Variable aTerm);

    public boolean checkVariableType(Type t, Variable aTerm);

    public Type inferBooleanType(Bool aTerm);

    public boolean checkBooleanType(Type t, Bool aTerm);

    public Type inferIntType(Int aTerm);

    public boolean checkIntType(Type t, Int aTerm);

    public Type inferEmptyType(Empty aTerm);

    public boolean checkEmptyType(Type t, Empty aTerm);
}

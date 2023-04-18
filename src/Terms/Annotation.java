package Terms;

import TypeChecker.Jatai;
import Types.Type;

public class Annotation implements Term {

    Term term;
    Type type;

    public Annotation(Term aTerm, Type aType) {
        this.term = aTerm;
        this.type = aType;
    }

    public Term getTerm() {
        return term;
    }

    public Type getType() {
        return type;
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferAnnotationType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkAnnotationType(t, this);
    }

    @Override
    public String toString() {
        return String.format("%s : %s", term, type);
    }
}

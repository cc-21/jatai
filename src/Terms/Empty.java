package Terms;

import TypeChecker.Jatai;
import Types.Type;

public class Empty implements Term {

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferEmptyType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkEmptyType(t, this);
    }

    @Override
    public String toString() {
        return "()";
    }
}

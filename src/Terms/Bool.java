package Terms;

import TypeChecker.Jatai;
import Types.Type;

public class Bool implements Term {
    boolean value;

    public Bool(boolean aValue) {
        this.value = aValue;
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferBooleanType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkBooleanType(t, this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

package Terms;

import TypeChecker.Jatai;
import Types.Type;

public class Int implements Term {
    int value;

    public Int(int aValue) {
        this.value = aValue;
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferIntType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkIntType(t, this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

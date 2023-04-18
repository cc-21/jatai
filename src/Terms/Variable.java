package Terms;

import TypeChecker.Jatai;
import Types.Type;

public class Variable implements Term {

    String name;

    public Variable(String aName) {
        this.name = aName;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferVariableType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkVariableType(t, this);
    }

    @Override
    public String toString() {
        return name;
    }
}

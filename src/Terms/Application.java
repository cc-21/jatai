package Terms;

import TypeChecker.Jatai;
import Types.Type;

public class Application implements Term {
    Term t1;
    Term t2;

    public Application(Term aT1, Term aT2) {
        this.t1 = aT1;
        this.t2 = aT2;
    }

    public Term getTerm1() {
        return t1;
    }

    public Term getTerm2() {
        return t2;
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferApplicationType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkApplicationType(t, this);
    }

    @Override
    public String toString() {
        return String.format("( %s ) ( %s )", t1, t2);
    }
}

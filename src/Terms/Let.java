package Terms;

import TypeChecker.Jatai;
import Types.Type;

public class Let implements Term {
    String name;
    Term assignment;
    Term expression;

    public Let(String name, Term assignment, Term expression) {
        this.name = name;
        this.assignment = assignment;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Term getAssignment() {
        return assignment;
    }

    public Term getExpression() {
        return expression;
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferLetType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkLetType(t, this);
    }

    @Override
    public String toString() {
        return String.format("let %s = %s in %s", name, assignment, expression);
    }
}

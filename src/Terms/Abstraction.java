package Terms;

import Exceptions.IllegalDeclarationError;
import TypeChecker.Jatai;
import Types.Type;

/**
 * syntax-directed, requires the annotation of the whole term, i.e., need argument type and return type specified
 */
public class Abstraction implements Term {
    Variable arg;
    Term expression;
    Annotation argAnn;
    Annotation returnAnn;

    public Abstraction(Variable aVariable, Term aExpression, Annotation aArgAnn, Annotation aReturnAnn) {
        arg = aVariable;
        expression = aExpression;
        if (aArgAnn.getTerm().equals(arg) && aReturnAnn.getTerm().equals(expression)) {
            argAnn = aArgAnn;
            returnAnn = aReturnAnn;
        } else {
            throw new IllegalDeclarationError("Invalid annotations.");
        }
    }

    public Variable getArg() {
        return arg;
    }

    public Term getExpression() {
        return expression;
    }

    public Annotation getArgAnnotation() {
        return argAnn;
    }

    public Annotation getReturnAnnotation() {
        return returnAnn;
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferAbstractionType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkAbstractionType(t, this);
    }

    @Override
    public String toString() {
        return String.format("λ (%s) . (%s)", arg, expression);
    }
}

package Terms;

import Exceptions.IllegalDeclarationError;
import TypeChecker.Context;
import Types.TFunction;
import Types.Type;

/**
 * syntax-directed, requires the annotation of the whole term
 */
public class Abstraction implements Term {
    String arg;
    Term expression;
    Type type;

    public Abstraction(String aVariable, Term aExpression, Type aType) {
        if (aVariable == null || aExpression == null || aType == null) {
            throw new IllegalDeclarationError("Null values detected.");
        }
        arg = aVariable;
        expression = aExpression;
        type = aType;
    }

    @Override
    public String toString() {
        return String.format("λ (%s) . (%s)", arg, expression);
    }

    /**
     * No inference rule, delegate the call to checkType if an annotation is present. Otherwise, throw a type error.
     */
    @Override
    public Type inferType(Context aContext) {
        return checkType(type, aContext) ? type : null;
    }

    /**
     * Γ, x : τ1 ⊢ t ⇐ τ2
     * <p>
     * ---------------------
     * </p>
     * Γ ⊢ λ x . t ⇐ τ1 → τ2
     */
    @Override
    public boolean checkType(Type aType, Context aContext) {
        if (aType instanceof TFunction) {
            TFunction lambdaType = (TFunction) aType;
            aContext.storeTypeInfo(arg, lambdaType.getArgType());
            boolean result = expression.checkType(lambdaType.getReturnType(), aContext);
            aContext.removeTypeInfo(arg, lambdaType.getArgType());
            return result;
        }
        return false;
    }
}

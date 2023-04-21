package Terms;

import Exceptions.TypeError;
import TypeChecker.Context;
import Types.Type;

public class Let implements Term {
    String name;
    Term assignment;
    Term expression;
    Type expressionType;

    public Let(String aName, Term aAssignment, Term aExpression) {
        this.name = aName;
        this.assignment = aAssignment;
        this.expression = aExpression;
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
    public String toString() {
        return String.format("let %s = %s in %s", name, assignment, expression);
    }

    /**
     * <todo>verify</todo>
     * No inference rule, delegate the call to checkType if an annotation is present. Otherwise, throw a type error.
     */
    @Override
    public Type inferType(Context aContext) {
        throw new TypeError("Let expressions do not have an inference mode.");
    }

    /**
     * Γ ⊢ t1 ⇒ 𝐴 and Γ, 𝑥 : 𝐴 ⊢ t2 ⇐ 𝐵
     * <p>
     * --------------------------------
     * <p>
     * Γ ⊢ let 𝑥 = t1 in t2 ⇐ 𝐵
     */
    @Override
    public boolean checkType(Type aType, Context aContext) {
        Type type1 = assignment.inferType(aContext);
        aContext.storeTypeInfo(name, type1);
        boolean result = expression.checkType(aType, aContext);
        aContext.removeTypeInfo(name, type1);
        return result;
    }
}

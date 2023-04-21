package Terms;

import TypeChecker.Context;
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
    public String toString() {
        return name;
    }

    /**
     * (x : τ) ∈ Γ
     * <p>
     * -----------
     * <p>
     * Γ ⊢ x ⇒ τ
     */
    @Override
    public Type inferType(Context aContext) {
        return aContext.get(toString());
    }
}

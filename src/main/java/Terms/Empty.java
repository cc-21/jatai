package Terms;

import TypeChecker.Context;
import Types.TUnit;
import Types.Type;

public class Empty implements Term {
    @Override
    public String toString() {
        return "()";
    }

    /**
     * Γ ⊢ () ⇒ unit
     */
    @Override
    public Type inferType(Context aContext) {
        return new TUnit();
    }
}

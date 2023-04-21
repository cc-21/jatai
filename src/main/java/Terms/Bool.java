package Terms;

import TypeChecker.Context;
import Types.TBool;
import Types.Type;

public class Bool implements Term {
    boolean value;

    public Bool(boolean aValue) {
        this.value = aValue;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Γ ⊢ true ⇒ Bool and
     * Γ ⊢ false ⇒ Bool
     */
    @Override
    public Type inferType(Context aContext) {
        return new TBool();
    }
}

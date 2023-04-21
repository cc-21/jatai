package Terms;

import TypeChecker.Context;
import Types.TInt;
import Types.Type;

public class Int implements Term {
    int value;

    public Int(int aValue) {
        this.value = aValue;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Γ ⊢ [any integer number] ⇒ Int
     */
    @Override
    public Type inferType(Context aContext) {
        return new TInt();
    }
}

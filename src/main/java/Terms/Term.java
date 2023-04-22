package Terms;

import TypeChecker.Context;
import Types.Type;

public interface Term {
    Type inferType(Context aContext);

    /**
     * Γ ⊢ t ⇒ τ
     * <p>
     * ----------
     * <p>
     * Γ ⊢ t ⇐ τ
     */
    default boolean checkType(Type aType, Context aContext) {
        Type inferredType = inferType(aContext);
        return inferredType != null && aType.hasSubtype(inferredType);
    }
}

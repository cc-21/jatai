package Terms;

import TypeChecker.Context;
import Types.Type;

public class Annotation implements Term {

    Term term;
    Type type;

    public Annotation(Term aTerm, Type aType) {
        this.term = aTerm;
        this.type = aType;
    }

    @Override
    public String toString() {
        return String.format("(%s) : (%s)", term, type);
    }

    /**
     * Γ ⊢ t ⇐ τ
     * <p>
     * -------------
     * </p>
     * Γ ⊢ t : τ ⇒ τ
     */
    @Override
    public Type inferType(Context aContext) {
        return term.checkType(type, aContext) ? type : null;
    }
}

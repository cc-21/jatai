package Terms;

import TypeChecker.Context;
import Types.TFunction;
import Types.Type;

public class Application implements Term {
    Term t1;
    Term t2;

    public Application(Term aT1, Term aT2) {
        this.t1 = aT1;
        this.t2 = aT2;
    }

    public Term getTerm1() {
        return t1;
    }

    public Term getTerm2() {
        return t2;
    }

    @Override
    public String toString() {
        return String.format("( %s ) ( %s )", t1, t2);
    }

    /**
     * Γ ⊢ t1 ⇒ τ1 → τ2 and Γ ⊢ t2 ⇐ τ1
     * <p>
     * ----------------------------------
     * <p>
     * Γ ⊢ t1 t2 ⇒ τ2
     */
    @Override
    public Type inferType(Context aContext) {
        Type funcType = t1.inferType(aContext);
        if (funcType instanceof TFunction) {
            TFunction fType = (TFunction) funcType;
            return t2.checkType(fType.getArgType(), aContext) ? fType.getReturnType() : null;
        } else {
            return null;
        }
    }
}

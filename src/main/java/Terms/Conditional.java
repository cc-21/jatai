package Terms;

import Exceptions.TypeError;
import TypeChecker.Context;
import Types.TBool;
import Types.Type;

public class Conditional implements Term {
    Term t1;
    Term t2;
    Term t3;

    public Conditional(Term aT1, Term aT2, Term aT3) {
        t1 = aT1;
        t2 = aT2;
        t3 = aT3;
    }

    @Override
    public String toString() {
        return String.format("if %s then %s else %s", t1, t2, t3);
    }

    /**
     * No inference rule, throw a type error.
     */
    @Override
    public Type inferType(Context aContext) {
        throw new TypeError("Conditionals do not have an inference mode.");
    }

    /**
     * Γ ⊢ t1 ⇐ Bool and Γ ⊢ t2 ⇐ τ and Γ ⊢ t3 ⇐ τ
     * <p>
     * ---------------------------------------------
     * </p>
     * Γ ⊢ if t1 then t2 else t3 ⇐ τ
     */
    @Override
    public boolean checkType(Type aType, Context aContext) {
        return t1.checkType(new TBool(), aContext) &&
                t2.checkType(aType, aContext) &&
                t3.checkType(aType, aContext);
    }
}

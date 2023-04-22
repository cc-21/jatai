package TypeChecker;

import Terms.Term;
import Types.Type;

public interface TypeSystem {
    Type inferType(Term aTerm);

    boolean checkType(Term aTerm, Type aType);

    default boolean isTyped(Term aTerm) {
        try {
            Type result = inferType(aTerm);
            return result != null;
        } catch (Exception e) {
            return false;
        }
    }
}

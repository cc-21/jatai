package TypeChecker;

import Terms.Term;
import Types.Type;

public class Jatai implements TypeSystem {
    private Context typingContext = new Context();

    @Override
    public Type inferType(Term aTerm) {
        return aTerm.inferType(typingContext);
    }

    @Override
    public boolean checkType(Term aTerm, Type aType) {
        if (aTerm == null || aType == null) return false;
        return aTerm.checkType(aType, typingContext);
    }
}

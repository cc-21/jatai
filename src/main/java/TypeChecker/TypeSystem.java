package TypeChecker;

import Terms.Term;
import Types.Type;

public interface TypeSystem {
    public Type inferType(Term aTerm);
    public boolean checkType(Term aTerm, Type aType);
}

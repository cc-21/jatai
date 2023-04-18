package Terms;

import Types.Type;
import TypeChecker.Jatai;

public interface Term {
    public Type inferType(Jatai checker);

    public boolean checkType(Jatai checker, Type t);

    public default boolean isTyped(Jatai checker) {
        return inferType(checker) != null;
    }
}

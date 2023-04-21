package TypeChecker;

import Exceptions.IllegalDeclarationError;
import Types.Type;

import java.util.HashMap;

public class Context extends HashMap<String, Type> {
    public void storeTypeInfo(String aName, Type aType) {
        Type prevType = get(aName);
        if (prevType == null) {
            put(aName, aType);
        } else {
            if (!prevType.typeEquals(aType))
                throw new IllegalDeclarationError("Conflicted types for existing name. Jatai does not support duplicate name currently.");
        }
    }

    public void removeTypeInfo(String aName, Type aType) {
        remove(aName, aType);
    }
}

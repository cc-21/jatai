package Types;

public class TFunction implements Type {
    Type argType;
    Type returnType;

    public TFunction(Type argType, Type returnType) {
        this.argType = argType;
        this.returnType = returnType;
    }

    public Type getArgType() {
        return argType;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public boolean hasSubtype(Type type) {
        if (type instanceof TFunction) {
            TFunction fType = (TFunction) type;
            return argType.hasSubtype(fType.argType) && fType.returnType.hasSubtype(returnType);
        } else {
            return false;
        }
    }

    @Override
    public boolean typeEquals(Type aType) {
        return (aType instanceof TFunction)
                && ((TFunction) aType).argType.typeEquals(argType)
                && ((TFunction) aType).returnType.typeEquals(returnType);
    }

    @Override
    public String toString() {
        return String.format("( %s ) -> ( %s )", argType, returnType);
    }
}

package Types;

public class TInt implements Type {
    @Override
    public boolean hasSubtype(Type aType) {
        return aType instanceof TInt;
    }

    @Override
    public String toString() {
        return "INTEGER";
    }
}

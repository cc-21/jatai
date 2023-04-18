package Types;

public class TBool implements Type {
    @Override
    public boolean hasSubtype(Type aType) {
        return aType instanceof TBool;
    }

    @Override
    public String toString() {
        return "BOOLEAN";
    }
}

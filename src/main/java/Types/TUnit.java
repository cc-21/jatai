package Types;

public class TUnit implements Type {

    @Override
    public boolean hasSubtype(Type aType) {
        return aType instanceof TUnit;
    }

    @Override
    public String toString() {
        return "()";
    }
}

package Types;

public interface Type {

    default boolean typeEquals(Type aType) {
        return this.getClass() == aType.getClass();
    }

    public boolean hasSubtype(Type aType);
}

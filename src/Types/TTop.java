package Types;

public class TTop implements Type {

    @Override
    public boolean hasSubtype(Type type) {
        // Top is the supertype of any types
        return true;
    }

    @Override
    public String toString() {
        return "TOP";
    }
}

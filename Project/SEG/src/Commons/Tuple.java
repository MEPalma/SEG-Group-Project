package Commons;

public class Tuple<L, R> {
    private final L x;
    private final R y;

    public Tuple(L x, R y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tuple) {
            return (((Tuple) obj).y.equals(this.y) && ((Tuple) obj).x.equals(this.x));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.x.hashCode() + this.y.hashCode();
    }

    public L getX() {
        return x;
    }

    public R getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + this.x.toString() + ", " + this.y.toString() + ")";
    }
}

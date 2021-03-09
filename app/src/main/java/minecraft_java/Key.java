package minecraft_java;

public class Key {
    public int x;
    public int z;

    public Key(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Key) {
            Key k = (Key) o;
            return (x == k.x && z == k.z);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (x * 12) + (z * 15);
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + z + ")");
    }
}

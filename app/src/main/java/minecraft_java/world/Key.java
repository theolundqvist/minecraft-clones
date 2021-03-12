package minecraft_java.world;

public class Key {
    public int x;
    public int z;

    public Key(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Key subtract(Key k){
        return new Key(x - k.x, z - k.z);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if (this.getClass() != o.getClass())return false;

        Key k = (Key) o;
        return x == k.x && z == k.z;

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

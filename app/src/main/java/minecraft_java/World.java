package minecraft_java;

import java.util.HashMap;


public class World {
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private final int chunkSize;
    private int loadChunks = 1;

    public World(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Chunk getChunk(Key k){
        return loadedChunks.get(k);
    }

    public Key getPlayerChunk(Player p){
        return new Key((int) p.getPos().x/chunkSize, (int) p.getPos().z/chunkSize);
    }

    public void load(Key k){
        HashMap<Key, Chunk> temp = new HashMap<>();
        for (int i = -loadChunks; i <= loadChunks; i++) {
            for (int j = -loadChunks; j <= loadChunks; j++) {
                k = new Key(k.x += i, k.z += j);
                temp.put(k, getChunk(k));
            }
        }
        loadedChunks = temp;
    }
    
    private static class Key{
        private int x;
        private int z;

        public Key(int x, int z){
            this.x = x;
            this.z = z;
        }
        
        @Override
        public boolean equals(Object o){
            if(o instanceof Key){
                Key k = (Key) o;
                return (x == k.x && z == k.z);
            }
            return false;
        }
        @Override
        public int hashCode() {
            return (x*12)+(z*15);
        }

        @Override
        public String toString(){
            return ("(" + x + ", " + y + ")");
        } 
    }

}

package minecraft_java;

import java.util.HashMap;


public class World {
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private final int chunkSize;

    public World(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Chunk getChunk(Key k){
        return loadedChunks.get(k);
    }

    


    private static class Key{
        private int x;
        private int y;

        public Key(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o){
            if(o instanceof Key){
                Key k = (Key) o;
                return (x == k.x && y == k.y);
            }
            return false;
        }
        @Override
        public int hashCode() {
            return (x*12)+(y*15);
        }

        @Override
        public String toString(){
            return ("(" + x + ", " + y + ")");
        } 
    }

}

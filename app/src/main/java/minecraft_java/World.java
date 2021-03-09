package minecraft_java;

import java.util.HashMap;


public class World {
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private final int chunkSize;
    private int renderDistance = 1;

    public World(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Chunk getChunk(Key k){
        return loadedChunks.get(k);
    }

    private Key oldPlayerChunk;
    public void updateChunks(Player p){
        Key playerChunk = getPlayerChunk(p);
        if(!oldPlayerChunk.equals(playerChunk)){
            oldPlayerChunk = playerChunk;
            loadNewChunks(p);
        }
    }

    private Key getPlayerChunk(Player p){
        return new Key((int) p.getPos().x/chunkSize, (int) p.getPos().z/chunkSize);
    }

    private void loadNewChunks(Player p){
        Key k = getPlayerChunk(p);
        HashMap<Key, Chunk> temp = new HashMap<Key, Chunk>(loadedChunks);
        //om new Key inte existerar put(new chunk)
        for (int i = -renderDistance; i <= renderDistance; i++) {
            for (int j = -renderDistance; j <= renderDistance; j++) {
                k = new Key(k.x += i, k.z += j);
                if (!(loadedChunks.containsKey(k))){
                    loadedChunks.put(k, getChunk(k));
                } else {
                    temp.remove(k);
                }
            }
            unloadedChunks.putAll(temp);
            temp.keySet().forEach(key -> loadedChunks.remove(key));
        }
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
            return ("(" + x + ", " + z + ")");
        } 
    }

}

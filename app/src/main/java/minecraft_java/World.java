package minecraft_java;

import java.util.HashMap;


public final class World {
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private final int chunkSize;
    private final int chunkHeight = 64;
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
        HashMap<Key, Chunk> toUnload = new HashMap<Key, Chunk>(loadedChunks);
        //om new Key inte existerar put(new chunk)
        for (int i = -renderDistance; i <= renderDistance; i++) {
            for (int j = -renderDistance; j <= renderDistance; j++) {
                k = new Key(k.x += i, k.z += j);
                if (!(loadedChunks.containsKey(k))){
                    loadedChunks.put(k, getChunk(k));
                } else {
                    toUnload.remove(k);
                }
            }
            unloadedChunks.putAll(toUnload);
            toUnload.keySet().forEach(key -> loadedChunks.remove(key));
        }
    }

}

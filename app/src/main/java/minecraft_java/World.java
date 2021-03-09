package minecraft_java;

import java.util.HashMap;


public class World {
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private int chunkSize;
    private int chunkHeight = 64;
    private int renderDistance = 1;

    public World(int chunkSize) {
        this.chunkSize = chunkSize;
        loadedChunks = new HashMap<>();
        unloadedChunks = new HashMap<>();
    }

    public int getSize(){
        return loadedChunks.size();
    }

    public Chunk getChunk(Key k){
        return loadedChunks.get(k);
    }

    public void draw(){
        loadedChunks.values().forEach((Chunk c) -> c.draw());
    }

    private Key oldPlayerChunk = new Key(0, 0);
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
                    if(unloadedChunks.containsKey(k)){
                        loadedChunks.put(k, unloadedChunks.get(k));
                        unloadedChunks.remove(k);
                    }
                    else{
                        loadedChunks.put(k, TerrainGenerator.generateChunkBlocks(k, chunkSize, chunkHeight));
                    }
                } else {
                    toUnload.remove(k);
                }
            }
            unloadedChunks.putAll(toUnload);
            toUnload.keySet().forEach(key -> loadedChunks.remove(key));
        }
    }

}

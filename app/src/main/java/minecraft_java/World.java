package minecraft_java;

import java.util.HashMap;


public class World {
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private int chunkSize;
    private int chunkHeight = 64;
    private int renderDistance = 2;

    public World(int chunkSize) {
        this.chunkSize = chunkSize;
        loadedChunks = new HashMap<>();
        unloadedChunks = new HashMap<>();
    }

    public int getSize(){
        return loadedChunks.size();
    }

    public void printDebugData() {
        System.out.println(getSize());
        loadedChunks.keySet().forEach((Key k) -> System.out.println(k.toString()));
    }

    public Chunk getChunk(Key k){
        return loadedChunks.get(k);
    }

    public void draw(){
        loadedChunks.values().forEach((Chunk c) -> c.draw());
    }

    private Key oldPlayerChunk;
    public void updateChunks(Player p){
        Key playerChunk = getPlayerChunk(p);
        //System.out.println(p.getPos().x / chunkSize + " : " + p.getPos().z / chunkSize);
        if(oldPlayerChunk == null || !oldPlayerChunk.equals(playerChunk)){
            oldPlayerChunk = playerChunk;
            loadNewChunks(p);
        }
    }

    private Key getPlayerChunk(Player p){
        
        return new Key((int) p.getPos().x/chunkSize, (int) p.getPos().z/chunkSize);
    }

    private void loadNewChunks(Player p){
        Key pk = getPlayerChunk(p);
        HashMap<Key, Chunk> toUnload = new HashMap<Key, Chunk>(loadedChunks);
        //om new Key inte existerar put(new chunk)
        for (int i = -renderDistance; i <= renderDistance; i++) {
            for (int j = -renderDistance; j <= renderDistance; j++) {
                Key k = new Key(pk.x + i, pk.z + j);
                //Not loaded but should be
                if (!(loadedChunks.containsKey(k))){
                    if(unloadedChunks.containsKey(k)){
                        System.out.println("Loading chunk from memory " + k.toString());
                        loadedChunks.put(k, unloadedChunks.get(k));
                        unloadedChunks.remove(k);
                    }
                    else{
                        System.out.println("Generating new chunk " + k.toString());
                        loadedChunks.put(k, TerrainGenerator.generateChunk(k, chunkSize, chunkHeight));
                        System.out.println("loadedChunks: " + getSize());
                    }
                } else { //loaded and should be
                    toUnload.remove(k);
                }
            }
        }
        unloadedChunks.putAll(toUnload);
        toUnload.keySet().forEach(key -> loadedChunks.remove(key));
        toUnload.keySet().forEach(key -> System.out.println("Unloading chunk " + key.toString()));
    }


}

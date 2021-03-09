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


        //om new Key inte existerar put(new chunk)

    }

}

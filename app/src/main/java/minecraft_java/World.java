package minecraft_java;

import java.util.HashMap;

public class World {
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private int chunkSize = 16;
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
        float x = p.getPos().x, z = p.getPos().z;
        x = x + x/Math.abs(x) * chunkSize/2;
        z = z + z/Math.abs(z) * chunkSize/2;

        return new Key((int) x/chunkSize, (int) z/chunkSize);
    }

    private float distanceToPlayer(Key k, Player p) {
        Key pk = k.subtract(getPlayerChunk(p));
        double x = (double) pk.x;
        double z = (double) pk.z;
        return (float) Math.pow((Math.pow(x, 2)+Math.pow(z, 2)), 0.5);
    }


    private void loadNewChunks(Player p){
        printDebugData();
        //float fov = p.getCam().getFov();
        Key pk = getPlayerChunk(p);
        HashMap<Key, Chunk> toUnload = new HashMap<Key, Chunk>(loadedChunks);
        //om new Key inte existerar put(new chunk)
        for (int i = -renderDistance; i <= renderDistance; i++) {
            for (int j = -renderDistance; j <= renderDistance; j++) {
                Key k = new Key(pk.x + i, pk.z + j);
                //Not loaded but should be
                if (!(loadedChunks.containsKey(k)) && distanceToPlayer(k, p) <= renderDistance){
                    if(unloadedChunks.containsKey(k)){
                        //System.out.println("Loading chunk from memory " + k.toString());
                        System.out.println(k);
                        loadedChunks.put(k, unloadedChunks.get(k));
                        unloadedChunks.remove(k);
                    }
                    else{
                        //System.out.println("Generating new chunk " + k.toString());
                        loadedChunks.put(k, TerrainGenerator.generateChunk(k, chunkSize, chunkHeight));
                        //System.out.println("loadedChunks: " + getSize());
                    }
                } else { //loaded and should be
                    toUnload.remove(k);
                }
            }
        }
        //loadedChunks.keySet().forEach(k -> System.out.println(k));
        unloadedChunks.putAll(toUnload);
        toUnload.keySet().forEach(key -> loadedChunks.remove(key));
        //toUnload.keySet().forEach(key -> System.out.println("Unloading chunk " + key.toString()));
    }

    public void printDebugData() {
        System.out.println("\nCurrently loaded: " + getSize());
        System.out.println("Total generated: " + (getSize() + unloadedChunks.size()));
        System.out.println("Size in ram estimate:\nBlockdata: "
                + (getSize() + unloadedChunks.size()) * 16 * 16 * 64 * 4 / 1E6 + "MB\n" + "Meshdata: "
                + (getSize() + unloadedChunks.size()) * 500 * (12 + 16 * 16 * 3) * 4 / 1E6 + "MB");
    }
}

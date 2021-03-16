package minecraft_java.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3i;

import minecraft_java.entities.Player;

public class World {
    private HashMap<Key, Chunk> chunks;
    private HashMap<Key, Chunk> loadedChunks;
    private HashMap<Key, Chunk> unloadedChunks;
    private int chunkSize = 16;
    private int chunkHeight = 64;
    private int renderDistance = 6;
    private int waterLevel = 20;

    public World(int chunkSize) {
        this.chunkSize = chunkSize;
        loadedChunks = new HashMap<>();
        unloadedChunks = new HashMap<>();
        chunks = new HashMap<>();
    }

    public int getHeight() {
        return chunkHeight;
    }

    public int getSize(){
        return loadedChunks.size();
    }
    public int getChunkSize() {
        return chunkSize;
    }
    public Chunk getChunk(Key k){
        return loadedChunks.get(k);
    }

    public int getRenderDistance() {
        return renderDistance;
    }

    public void draw(){
        loadedChunks.values().forEach((Chunk c) -> c.draw());
    }

    private Key oldPlayerChunk;
    public void updateChunks(Player p){
        Key playerChunk = keyFromWorldPos(p.getPos());
        //System.out.println(p.getPos().x / chunkSize + " : " + p.getPos().z / chunkSize);
        if(oldPlayerChunk == null || !oldPlayerChunk.equals(playerChunk)){
            oldPlayerChunk = playerChunk;
            loadNewChunks(p);
        }
    }

    public int getBlockFromWorldPos(Vector3f v){

        Key k = keyFromWorldPos(v);
        Chunk c = chunks.get(k);
        if(c == null) return -1;
        Vector3i local = worldToLocal(v);
        System.out.println("world: " + v + "\nlocal: " + local + "\nkey: " + k.toString());
        return c.getBlock(local.x, local.y, local.z);
    }

    public int getBlock(Key k, int x, int y, int z){
        Chunk c = chunks.get(k);
        if(c != null) return c.getBlocks()[x][y][z];
        return -1;
    }

    public Key keyFromWorldPos(Vector3f pos){
        float x = pos.x, z = pos.z;
        x = x + x/Math.abs(x) * chunkSize/2;
        z = z + z/Math.abs(z) * chunkSize/2;

        return new Key((int) x/chunkSize, (int) z/chunkSize);
    }
    public ArrayList<Chunk> getNeighboringChunks(Key k){
        ArrayList<Chunk> xs = new ArrayList<>();
        xs.add(chunks.get(new Key(k.x + 1, k.z)));
        xs.add(chunks.get(new Key(k.x - 1, k.z)));
        xs.add(chunks.get(new Key(k.x, k.z + 1)));
        xs.add(chunks.get(new Key(k.x, k.z - 1)));
        return xs;
    }
    public boolean hasNeighbors(Key k){
        return
        chunkExists(new Key(k.x + 1, k.z)) &&
        chunkExists(new Key(k.x - 1, k.z)) && 
        chunkExists(new Key(k.x, k.z + 1)) &&
        chunkExists(new Key(k.x, k.z - 1));
    }
    public boolean chunkExists(Key k){
        return chunks.containsKey(k);
    }

    // public float getHeightFromGround(Player p){
    //     Key k = keyFromWorldPos(p.getPos());
    //     Chunk c = loadedChunks.get(k);
    //     if(c == null) return -1;

    //     Vector3f wPos = p.getPos();
    //     Vector3i local = worldToLocal(wPos);

    //     System.out.println(local.x + " : " + local.z);

    //     for (int y = chunkHeight-1; y >= 0 ; y--) {
    //         if(c.getBlock(local.x, y, local.z) != 0) return y;
    //     }
    //     return 0;
    // }

    public Vector3i worldToLocal(Vector3f w) {
        return worldToLocal(new Vector3i(w, 2));
    }
    public Vector3i worldToLocal(Vector3i w){
        return new Vector3i(w.x % chunkSize + chunkSize/2, w.y, w.z % chunkSize + chunkSize / 2);
    }
    
    public Vector3f localToWorld(Key k, Vector3f l) {
        return new Vector3f(l.x * k.x, l.y, l.z * k.z);
    }
    public Vector3i localToWorld(Key k, Vector3i l) {
        return new Vector3i(l.x * k.x, l.y, l.z * k.z);
    }

    private float distanceToPlayer(Key k, Player p) {
        Key pk = k.subtract(keyFromWorldPos(p.getPos()));
        double x = (double) pk.x;
        double z = (double) pk.z;
        return (float) Math.pow(x*x + z*z, 0.5);
    }


    private void loadNewChunks(Player p){
        printDebugData();
        //float fov = p.getCam().getFov();
        Key pk = keyFromWorldPos(p.getPos());
        HashMap<Key, Chunk> toUnload = new HashMap<Key, Chunk>(loadedChunks);
        //om new Key inte existerar put(new chunk)
        for (int i = -(renderDistance+1); i <= renderDistance+1; i++) {
            for (int j = -(renderDistance+1); j <= renderDistance+1; j++) {
                Key k = new Key(pk.x + i, pk.z + j);
                //Not loaded but should be
                if (!(loadedChunks.containsKey(k)) && distanceToPlayer(k, p) <= renderDistance){
                    if(unloadedChunks.containsKey(k)){
                        //System.out.println("Loading chunk from memory " + k.toString());
                        //LADDA FRÅN MINNET
                        //System.out.println(k);
                        loadedChunks.put(k, unloadedChunks.get(k));
                        unloadedChunks.remove(k);
                    }
                    else{
                        //System.out.println("Generating new chunk " + k.toString());
                        //GEN NEW
                        Chunk chunk = TerrainGenerator.generateChunk(k, chunkSize, chunkHeight);
                        chunk.setWorldRef(this);
                        loadedChunks.put(k, chunk);
                        chunks.put(k, chunk);
                        //System.out.println("loadedChunks: " + getSize());
                    }
                } else if (!loadedChunks.containsKey(k) && distanceToPlayer(k, p) > renderDistance){
                    //??
                    Chunk chunk = TerrainGenerator.generateChunk(k, chunkSize, chunkHeight);
                    chunk.setWorldRef(this);
                    chunks.put(k, chunk);
                } else if (!(distanceToPlayer(k, p) > renderDistance)){ //loaded and should be
                    toUnload.remove(k);
                }
            }
        }
        //loadedChunks.keySet().forEach(k -> System.out.println(k));
        unloadedChunks.putAll(toUnload);
        toUnload.keySet().forEach(key -> loadedChunks.remove(key));
        //toUnload.keySet().forEach(key -> System.out.println("Unloading chunk " + key.toString()));
    
        //Load unloaded Meshes
        for(Map.Entry<Key, Chunk> e : loadedChunks.entrySet()){
            //Key k = entry.getKey();
            Chunk c = e.getValue();
            if (!c.hasMesh() && hasNeighbors(e.getKey())){
                c.updateMesh();
            }
        }

    }

    public void printDebugData() {
        System.out.println("\nCurrently loaded: " + getSize());
        System.out.println("Total generated: " + (getSize() + unloadedChunks.size()));
        System.out.println("Size in ram estimate:\nBlockdata: "
                + (getSize() + unloadedChunks.size()) * 16 * 16 * 64 * 4 / 1E6 + "MB\n" + "Meshdata: "
                + (getSize() + unloadedChunks.size()) * 500 * (12 + 16 * 16 * 3) * 4 / 1E6 + "MB");
    }
}

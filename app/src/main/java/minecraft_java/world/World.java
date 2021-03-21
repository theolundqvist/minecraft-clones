package minecraft_java.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3i;

import minecraft_java.entities.Player;

public class World {
    private HashMap<Key, Chunk> chunks, loadedChunks, unloadedChunks;
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

    public int getHeight() {return chunkHeight;}
    public int getLoadedChunksCount(){return loadedChunks.size();}
    public int getChunkSize() {return chunkSize;}
    public int getRenderDistance() {return renderDistance;}

    public void draw(){
        loadedChunks.values().forEach((Chunk c) -> c.draw());
    }

    //EDITING
    //TEMP FOR TESTING
    public void togglePlayerChunkVisible(Player p){
        Key k = keyFromWorldPos(p.getPos());
        if(loadedChunks.containsKey(k)){
            unloadChunk(k);
        }
        else loadChunk(k);
    }
    //TEMP FÖR TESTING
    public void removeTopBlockOnPos(Vector3f w) {
        Key k = keyFromWorldPos(w);
        Chunk c = loadedChunks.get(k);
        if (c == null)
            return;

        Vector3i local = worldToLocal(w);

        for (int y = chunkHeight - 1; y >= 0; y--) {
            if (c.getBlock(local.x, y, local.z) != 0) {
                c.setBlock(local.x, y, local.z, 0);
                return;
            }
        }
    }

    public void raycastDestroyBlock(Vector3f pos, Vector3f dir){
        System.out.println("Raycast destroy block, pos: " + pos + ", dir: " + dir);
        Vector3i posL = worldToLocal(pos);
        float range = 10f;
        float stepL = 0.1f;
        dir.normalize().mul(stepL);
        for (int i = 0; i <= range/stepL; i++) {
            Vector3i nextL = worldToLocal(pos.add(dir));
            if(!nextL.equals(posL)){
                if(blockFromWorldPos(pos) != 0){
                    setBlockFromWorldPos(pos, 0);
                    return;
                }
            }
        }
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

    public int blockFromWorldPos(Vector3f v){
        Key k = keyFromWorldPos(v);
        Chunk c = chunks.get(k);
        if(c == null) return -1;
        Vector3i local = worldToLocal(v);
        //System.out.println("world: " + v + "\nlocal: " + local + "\nkey: " + k.toString()+"\n");
        return c.getBlock(local.x, local.y, local.z);
    }
    
    public void setBlockFromWorldPos(Vector3f v, int blockType) {
        Key k = keyFromWorldPos(v);
        Chunk c = chunks.get(k);
        if (c == null)
            return;
        Vector3i local = worldToLocal(v);
        // System.out.println("world: " + v + "\nlocal: " + local + "\nkey: " +
        // k.toString()+"\n");
        c.setBlock(local.x, local.y, local.z, blockType);
    }

    public Key keyFromWorldPos(Vector3f pos){
        float x = pos.x, z = pos.z;
        return new Key((int)Math.floor(x/chunkSize), (int)Math.floor(z/chunkSize));
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



    public Vector3i worldToLocal(Vector3f w) {
        return worldToLocal(new Vector3i(w, 2));
    }
    public Vector3i worldToLocal(Vector3i w){
        int x = w.x % 16;
        int z = w.z % 16;
        if(x < 0) x += 16;
        if(z < 0) z += 16;
        return new Vector3i(x, w.y, z);
    }
    
    public Vector3f localToWorld(Key k, Vector3f l) {
        return new Vector3f(chunkSize * k.x + l.x, l.y, chunkSize * k.z + l.z);
    }
    public Vector3i localToWorld(Key k, Vector3i l) {
        return new Vector3i(chunkSize * k.x + l.x, l.y, chunkSize * k.z + l.z);
    }

    private float distanceToPlayer(Key k, Player p) {
        Key pk = k.subtract(keyFromWorldPos(p.getPos()));
        return (float) Math.pow(pk.x*pk.x + pk.z*pk.z, 0.5);
    }

    private void loadChunk(Key k){
        if(!loadedChunks.containsKey(k)){
            if(unloadedChunks.containsKey(k)){
                loadedChunks.put(k, unloadedChunks.get(k));
                unloadedChunks.remove(k);
            }
            else{
                Chunk chunk = new Chunk(this, k, chunkSize, chunkHeight);
                loadedChunks.put(k, chunk);
                chunks.put(k, chunk);
            }
        }
    }
    private void unloadChunk(Key k){
        unloadedChunks.put(k, loadedChunks.remove(k));
    }

    private void loadNewChunks(Player p){
        //printDebugData();

        Key pk = keyFromWorldPos(p.getPos());

        HashSet<Key> chunksToUnload = new HashSet<>();
        chunksToUnload.addAll(loadedChunks.keySet());

        for (int i = -(renderDistance+1); i <= renderDistance+1; i++) {
            for (int j = -(renderDistance+1); j <= renderDistance+1; j++) {
                Key k = new Key(pk.x + i, pk.z + j);
                if (distanceToPlayer(k, p) <= renderDistance){
                    loadChunk(k);
                    chunksToUnload.remove(k);
                }
            }
        }
        chunksToUnload.forEach(k -> unloadChunk(k));
    
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
        System.out.println("\nCurrently loaded: " + getLoadedChunksCount());
        System.out.println("Total generated: " + (getLoadedChunksCount() + unloadedChunks.size()));
        System.out.println("Size in ram estimate:\nBlockdata: "
                + (getLoadedChunksCount() + unloadedChunks.size()) * 16 * 16 * 64 * 4 / 1E6 + "MB\n" + "Meshdata: "
                + (getLoadedChunksCount() + unloadedChunks.size()) * 500 * (12 + 16 * 16 * 3) * 4 / 1E6 + "MB");
    }
}

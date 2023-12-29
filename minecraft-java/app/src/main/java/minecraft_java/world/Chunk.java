package minecraft_java.world;

import org.joml.Vector3f;

import minecraft_java.mesh.ChunkMesh;

public class Chunk {
    private int[][][] blocks;
    public ChunkMesh mesh;
    public Key key;
    public int size;
    public World worldRef;
    public int height;

    public Chunk(World world, Key k, int chunkSize, int chunkHeight) {
        this.key = k;
        this.worldRef = world;
        this.size = chunkSize;
        this.height = chunkHeight;
        setBlocks(TerrainGenerator.generateBlocks(this));
    }

    public int getSize() {
        return size;
    }

    public Key getKey() {
        return key;
    }

    public boolean hasMesh(){
        return mesh != null;
    }

    public void updateMesh(){
        mesh = new ChunkMesh(this);
    }

    public Vector3f getWorldOffset(){
        return new Vector3f(key.x * size, 0, key.z * size);
    }

    public void draw(){
        if(hasMesh()) mesh.draw();
    }

    public int[][][] getBlocks() {
        return this.blocks;
    }

    public void setBlocks(int[][][] blocks) {
        this.blocks = blocks;
        //updateMesh();
    }

    public int getBlock(int x, int y, int z) {
        return this.blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, int value) {
        this.blocks[x][y][z] = value;
        updateMesh();
    }

    public void setWorldRef(World world) {
        worldRef = world;
    }
}

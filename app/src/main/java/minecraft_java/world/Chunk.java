package minecraft_java.world;

import org.joml.Vector3f;

import minecraft_java.mesh.ChunkMesh;

public class Chunk {
    private int[][][] blocks;
    public ChunkMesh mesh;
    private Key pos;
    private int size;
    
    public Chunk(int[][][] blocks, Key pos, int size) {
        this.pos = pos;
        this.size = size;
        setBlocks(blocks);
    }

    private void updateMesh(){
        mesh = new ChunkMesh(this);
    }

    public Vector3f getWorldOffset(){
        return new Vector3f(pos.x * size - size/2, 0, pos.z * size - size/2);
    }

    public void draw(){
        mesh.draw();
    }

    public int[][][] getBlocks() {
        return this.blocks;
    }

    public void setBlocks(int[][][] blocks) {
        this.blocks = blocks;
        updateMesh();
    }

    public int getBlock(int x, int y, int z) {
        return this.blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, int value) {
        this.blocks[x][y][z] = value;
        updateMesh();
    }
}

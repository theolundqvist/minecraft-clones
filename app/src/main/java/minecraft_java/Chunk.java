package minecraft_java;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Chunk {
    private int[][][] blocks;
    public ArrayList<CubeFace> meshData;
    private Key pos;
    private int size;
    
    public Chunk(int[][][] blocks, Key pos, int size) {
        this.pos = pos;
        this.size = size;
        setBlocks(blocks);
    }

    public boolean hasMesh(){
        return meshData != null;
    }

    public void updateMesh(){
        meshData = MeshEngine.createMesh(this);
    }

    public Vector3f getWorldOffset(){
        return new Vector3f(pos.x * size - size/2, 0, pos.z * size - size/2);
    }

    public void draw(){
        if (meshData == null) return;
        for (CubeFace q : meshData) {
            q.draw();
        }
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
}

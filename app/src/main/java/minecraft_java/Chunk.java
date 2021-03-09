package minecraft_java;

import java.util.ArrayList;

public class Chunk {
    private int[][][] blocks;
    private int chunkSize = 16;
    private ArrayList<quadData> meshData;
    
    public Chunk(int[][][] blocks) {
        setBlocks(blocks);
    }

    private void updateMesh(){
        meshData = MeshEngine.createMesh(this);
    }
    
    public Chunk(int[][][] blocks, int chunkSize) {
        setBlocks(blocks);
        this.chunkSize = chunkSize;
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

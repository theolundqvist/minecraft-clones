package minecraft_java;

public class Chunk {
    private int[][][] blocks;
    private int chunkSize = 16;
    
    public Chunk(int[][][] blocks) {
        this.blocks = blocks;
    }
    
    public Chunk(int[][][] blocks, int chunkSize) {
        this.blocks = blocks;
        this.chunkSize = chunkSize;
    }

    public int[][][] getBlocks() {
        return this.blocks;
    }

    public void setBlocks(int[][][] blocks) {
        this.blocks = blocks;
    }
}

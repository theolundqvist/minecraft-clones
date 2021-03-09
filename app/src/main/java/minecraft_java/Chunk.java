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

    public void setBlock(int x, int y, int z, int i){
        blocks[x][y][z] = i;
    }

    public int getBlock(int x, int y, int z){
        return blocks[x][y][z];
    }

    private static class Coord{
        private int x;
        private int y;
        private int z;
        
        public Coord(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public int getZ(){
            return z;
        }

    }
}

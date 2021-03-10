package minecraft_java;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Chunk {
    private int[][][] blocks;
    private ArrayList<QuadMesh> meshData;
    private Key pos;
    private int size;
    
    public Chunk(int[][][] blocks, Key pos, int size) {
        this.pos = pos;
        this.size = size;
        setBlocks(blocks);
    }

    private void updateMesh(){
        meshData = MeshEngine.createMesh(this);
    }

    public Vector3f getWorldOffset(){
        return new Vector3f(pos.x * size, 0, pos.z * size);
    }

    public void draw(){
        for (QuadMesh q : meshData) {
            q.draw();
        }
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

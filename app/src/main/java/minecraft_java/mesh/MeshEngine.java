package minecraft_java.mesh;

import java.util.ArrayList;

import org.joml.Vector3f;

import minecraft_java.world.Chunk;

public class MeshEngine {
    
    public static ArrayList<QuadMesh> createMesh(Chunk chunk){
        ArrayList<QuadMesh> quads = new ArrayList<>();
        Vector3f worldOffset = chunk.getWorldOffset();

        Vector3f color = new Vector3f(0.4f, 0.6f, 0.4f); //TEMP

        int[][][] blocks = chunk.getBlocks();
        for (int x = 1; x < blocks.length-1; x++) {
            for (int y = 1; y < blocks[x].length-1; y++) {
                for (int z = 1; z < blocks[x][y].length - 1; z++) {
                    //FÖR VARJE BLOCK FÖRUTOM KANTERNA
                    int block = blocks[x][y][z];
                    if(block == 0){
                        Vector3f[] dirs = getAllDir();
                        for (Vector3f dir : dirs) {
                            Vector3f otherPos = new Vector3f(x + dir.x, y + dir.y, z + dir.z);
                            int other = blocks[(int)otherPos.x][(int)otherPos.y][(int)otherPos.z];
                            otherPos.add(worldOffset);
                            if (other != 0) {
                                quads.add(new QuadMesh(otherPos.add(dir.negate().div(2)), dir, other));
                            }
                        }
                    }
                }
            }
        }
        return quads;
    }

    public static Vector3f[] getAllDir(){
        Vector3f[] dir = new Vector3f[6];
        Vector3f v = new Vector3f(1,0,0);
        for (int i = 0; i < 4; i++) {
            dir[i] = cpV(v.rotateY((float) Math.PI / 2).round());
        }
        dir[4] = cpV(v.rotateZ((float) Math.PI / 2).round());
        dir[5] = cpV(v.rotateZ((float) Math.PI).round());
        return dir;
    }

    private static Vector3f cpV(Vector3f v){
        return new Vector3f(v);
    }


}

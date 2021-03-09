package minecraft_java;

import java.util.ArrayList;

import org.joml.Vector3f;

class MeshEngine {
    
    public static ArrayList<QuadMesh> createMesh(Chunk chunk){
        ArrayList<QuadMesh> quads = new ArrayList<>();

        int[][][] blocks = chunk.getBlocks();
        for (int x = 1; x < blocks.length-1; x++) {
            for (int y = 1; y < blocks[x].length-1; y++) {
                for (int z = 1; z < blocks[x][z].length - 1; z++) {
                    //FÖR VARJE BLOCK FÖRUTOM KANTERNA
                    int block = blocks[x][y][z];
                    if(block == 0){
                        Vector3f[] dirs = getAllDir();
                        for (Vector3f dir : dirs) {
                            Vector3f otherPos = new Vector3f(x + dir.x, y + dir.y, z + dir.z);
                            int other = blocks[(int)otherPos.x][(int)otherPos.y][(int)otherPos.z];
                            if (other != 0) {
                                quads.add(new QuadMesh(otherPos, dir.negate()));
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
            dir[i] = v.rotateY((float) Math.PI / 2).round();
        }
        dir[4] = v.rotateZ((float) Math.PI / 2).round();
        dir[5] = v.rotateZ((float) Math.PI).round();
        return dir;
    }


}

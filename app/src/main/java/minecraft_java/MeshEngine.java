package minecraft_java;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector3i;

final class MeshEngine {
    
    public ArrayList<quadData> createMesh(Chunk chunk){
        ArrayList<quadData> quads = new ArrayList<>();

        int[][][] blocks = chunk.getBlocks();
        for (int x = 1; x < blocks.length-1; x++) {
            for (int y = 1; y < blocks[x].length-1; y++) {
                for (int z = 1; z < blocks[x][z].length - 1; z++) {
                    int block = blocks[x][y][z];
                    Vector3f[] dirs = getAllDir();
                    for (Vector3f v : dirs) {
                        int otherBlock = blocks[x+(int)v.x][y+(int)v.y][z+(int)v.z];
                        if(otherBlock == 0) 
                    }

                }
            }
        }
        return null;
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

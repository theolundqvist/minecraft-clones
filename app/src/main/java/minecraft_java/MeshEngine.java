package minecraft_java;

import java.util.ArrayList;

import org.joml.Vector3f;

class MeshEngine {
    
    public static ArrayList<CubeFace> createMesh(Chunk chunk){
        ArrayList<CubeFace> quads = new ArrayList<>();
        Vector3f worldOffset = chunk.getWorldOffset();

        Vector3f color = new Vector3f(0.4f, 0.6f, 0.4f); //TEMP

        int[][][] blocks = chunk.getBlocks();
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 1; y < blocks[x].length-1; y++) {
                for (int z = 0; z < blocks[x][y].length; z++) {
                    //FÖR VARJE BLOCK FÖRUTOM KANTERNA
                    int block = blocks[x][y][z];
                    if(block == 0){
                        Vector3f[] dirs = getAllDir();
                        for (Vector3f dir : dirs) {
                            Vector3f otherPos = new Vector3f(x + dir.x, y + dir.y, z + dir.z);
                            if (otherPos.x <= 0 
                            ||  otherPos.x >= blocks[x][y].length 
                            ||  otherPos.y <= blocks[x][y].length
                            ||  otherPos.y >= blocks[x][y].length){


                            }else{
                                int other = blocks[(int)otherPos.x][(int)otherPos.y][(int)otherPos.z];
                                otherPos.add(worldOffset);
                                if (other != 0) {
                                    quads.add(new CubeFace(otherPos.add(dir.negate().div(2)), dir, new Vector3f(color).add(dir.div(8))));
                                }
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

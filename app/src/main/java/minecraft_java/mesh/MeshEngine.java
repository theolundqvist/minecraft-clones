package minecraft_java.mesh;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector3i;

import minecraft_java.world.Chunk;
import minecraft_java.world.World;

public class MeshEngine {
    
    public static ArrayList<QuadMesh> createMesh(Chunk chunk){
        //OM ALLA CHUNKS RUNTOM FINNS ANNARS RETURN

        ArrayList<QuadMesh> quads = new ArrayList<>();
        Vector3f worldOffset = chunk.getWorldOffset();


        int[][][] blocks = chunk.getBlocks();
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 1; y < blocks[x].length-1; y++) {
                for (int z = 0; z < blocks[x][y].length; z++) {
                    //FÖR VARJE BLOCK INK. KANTERNA
                    int block = blocks[x][y][z];
                    if(block == 0){
                        Vector3f[] dirs = getAllDir();
                        for (Vector3f dir : dirs) {
                            Vector3f otherPos = new Vector3f(x + dir.x, y + dir.y, z + dir.z);
                            if (otherPos.x <= 0 
                            ||  otherPos.x >= chunk.getSize() 
                            ||  otherPos.z <= 0
                            ||  otherPos.z >= chunk.getSize()){
                                //UTANFÖR CHUNKEN
                                //KOM ÅT BLOCKET I CHUNKEN BREDVID OM DEN FINNS, STATIC ELLER WORLDREF I CHUNK?
                                //Vector3f wp = World.localToWorld(chunk.getKey(), otherPos);
                                //World.keyFromWorldPos(wp);
                            }
                            else{
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

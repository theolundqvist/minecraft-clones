package minecraft_java.mesh;

import java.util.ArrayList;

import org.joml.Vector3f;

import minecraft_java.world.Chunk;

public class MeshEngine {
    
    public static ArrayList<QuadMesh> createMesh(Chunk chunk){
        ArrayList<QuadMesh> quads = new ArrayList<>();
        
        Vector3f worldOffset = chunk.getWorldOffset();


        int[][][] blocks = chunk.getBlocks();
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 1; y < blocks[x].length-1; y++) {
                for (int z = 0; z < blocks[x][y].length; z++) {
                    //FÖR VARJE BLOCK INK. KANTERNA
                    int block = blocks[x][y][z];
                    if(block != 0){
                        Vector3f[] dirs = getAllDir();
                        Vector3f pos = new Vector3f(x, y, z);
                        for (Vector3f dir : dirs) {
                            Vector3f otherPos = new Vector3f(x + dir.x, y + dir.y, z + dir.z);
                            int other = 1;
                            if (otherPos.x < 0 
                            ||  otherPos.x >= chunk.getSize() 
                            ||  otherPos.z < 0
                            ||  otherPos.z >= chunk.getSize()){
                                //UTANFÖR CHUNKEN
                                //KOM ÅT BLOCKET I CHUNKEN BREDVID OM DEN FINNS
                                
                                //kraschar, index out of bounds
                                other = chunk.worldRef.blockFromWorldPos(otherPos.add(worldOffset));
                                if(other == -1) {
                                    Vector3f otherW = new Vector3f(otherPos);
                                    Vector3f otherL = new Vector3f(otherPos.sub(worldOffset));
                                    System.out.println("\n" + otherW);
                                    System.out.println("other == -1 : lpos: " + otherL + ", \ncalculated lpos: " + chunk.worldRef.worldToLocal(otherW));
                                    System.out.println("k = " + chunk.key + ", calc. key = " + chunk.worldRef.keyFromWorldPos(otherW));
                                    System.out.println(chunk.worldRef.keyFromWorldPos(worldOffset));
                                }
                            }
                            else{
                                //other = chunk.worldRef.getBlockFromWorldPos(otherPos.add(worldOffset));
                                other = blocks[(int)otherPos.x][(int)otherPos.y][(int)otherPos.z];
                            }
                            
                            if (other == 0) {
                                quads.add(
                                    new QuadMesh(
                                        new Vector3f(pos)
                                        .add(worldOffset)
                                        .add(dir.div(2)), dir, block));
                            }
                        }
                    }
                }
            }
        }
        return quads;
    }

    public static Vector3f[] getAllDir(){
        return new Vector3f[]{
            new Vector3f(1, 0, 0),    
            new Vector3f(-1, 0, 0),
            new Vector3f(0, 1, 0),
            new Vector3f(0, -1, 0),
            new Vector3f(0, 0, 1),
            new Vector3f(0, 0, -1),
        };
    }


}

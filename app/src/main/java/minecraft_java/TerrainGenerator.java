package minecraft_java;
import org.joml.Vector2i;

public final class TerrainGenerator {
    
    static float scaleX = 0.1f;
    static float scaleY = 0.1f;
    static float amplitude = 5f;
    static float lowestValue = 0;

    public static double getBlockHeight(int x, int z){
        return amplitude * SimplexNoise.noise((double) x * scaleX, (double) z * scaleY) + amplitude + lowestValue;
    }

    private static int[][] generateChunkHeightMap(Key chunkKey, int chunkSize){
        int[][] heightMap = new int[chunkSize][chunkSize];
        Vector2i cPos = new Vector2i(chunkKey.x * chunkSize, chunkKey.z * chunkSize);
        for (int x = cPos.x-chunkSize/2; x < cPos.x + chunkSize / 2; x++) {
            for (int z = cPos.y - chunkSize / 2; z < cPos.y + chunkSize / 2; z++) {
                heightMap[x][z] = (int)getBlockHeight(x, z);
            }
        }
        return heightMap;
    }

    // public static int[][][] generateChunkBlocks(Key chunkKey, int chunkSize){

    // }

}

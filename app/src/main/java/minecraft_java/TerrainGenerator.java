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

    private static int[][] generateChunkHeightMap(Key k, int chunkSize){

        int[][] heightMap = new int[chunkSize][chunkSize];
        int wx = k.x * chunkSize;
        int wz = k.z * chunkSize;

        for (int x = wx-chunkSize/2; x < wx + chunkSize / 2; x++) {
            for (int z = wz - chunkSize / 2; z < wz + chunkSize / 2; z++) {
                heightMap[x][z] = (int) getBlockHeight(x, z);
            }
        }
        return heightMap;
    }

    public static int[][][] generateChunkBlocks(Key k, int chunkSize, int chunkHeight){

        int[][] heightMap = generateChunkHeightMap(k, chunkSize);
        int[][][] blocks = new int[chunkSize][chunkSize][chunkHeight];



    }

}

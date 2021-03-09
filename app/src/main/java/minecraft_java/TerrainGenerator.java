package minecraft_java;
import org.joml.Vector2i;

public final class TerrainGenerator {
    
    static float scaleX = 0.1f;
    static float scaleY = 0.1f;
    public static float amplitude = 5f;
    public static float lowestValue = 0;

    public static double getBlockHeight(int x, int z){
        return amplitude * SimplexNoise.noise((double) x * scaleX, (double) z * scaleY) + amplitude + lowestValue;
    }

    private static int[][] generateChunkHeightMap(Key k, int chunkSize){

        int[][] heightMap = new int[chunkSize][chunkSize];
        int wx = k.x * chunkSize;
        int wz = k.z * chunkSize;

        for (int x = 0; x < chunkSize; x++) {
            for (int z = 0; z < chunkSize; z++) {
                heightMap[x][z] = (int) getBlockHeight(wx + x - chunkSize/2, wz + z - chunkSize / 2);
            }
        }
        return heightMap;
    }

    public static Chunk generateChunkBlocks(Key k, int chunkSize, int chunkHeight){

        lowestValue = chunkHeight - amplitude;

        //int[][] heightMap = generateChunkHeightMap(k, chunkSize);
        int[][][] blocks = new int[chunkSize][chunkSize][chunkHeight];
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                for (int z = 0; z < blocks[x][y].length; z++) {
                    if(y < 5){
                        blocks[x][y][z] = 1;
                    }
                }
            }
        }

        return new Chunk(blocks);

    }

}

package minecraft_java.world;

import java.util.HashMap;

public final class TerrainGenerator {
    
    static float scaleX = 0.02f;
    static float scaleY = 0.02f;
    public static float amplitude = 5f;
    public static float lowestValue = 0f; //chunkHeight/2

    public static double getBlockHeight(int x, int z){
        return amplitude * SimplexNoise.noise((double) x * scaleX, (double) z * scaleY) + amplitude + lowestValue;
    }
    
    public static double getBlockChance(int x, int y, int z) {
        return SimplexNoise.noise((double) x * scaleX, (double)y * scaleY, (double) z * scaleY);
    }
    public static double[][][] generate3DMap(Key k, int chunkSize, int chunkHeight){
        double[][][] heightMap = new double[chunkSize][chunkHeight][chunkSize];
        int wx = k.x * chunkSize;
        int wz = k.z * chunkSize;

        for (int x = 0; x < chunkSize; x++) {
            for (int y = 0; y < chunkHeight; y++) {       
                for (int z = 0; z < chunkSize; z++) {
                    heightMap[x][y][z] = getBlockChance(wx + x - chunkSize / 2, y, wz + z - chunkSize / 2);
                }
            }
        }
        return heightMap;
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

    public static Chunk generateChunk(Key k, int chunkSize, int chunkHeight){

        lowestValue = chunkHeight/2 - amplitude;

        int[][] heightMap = generateChunkHeightMap(k, chunkSize);
        
        //double[][][] map3D = generate3DMap(k, chunkSize, chunkHeight);
        int[][][] blocks = new int[chunkSize][chunkHeight][chunkSize];
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                for (int z = 0; z < blocks[x][y].length; z++) {
                    //System.out.println(map3D[x][y][z]);
                    if(y < heightMap[x][z]){
                        blocks[x][y][z] = 1;
                    }
                    
                    // if(map3D[x][y][z] < 0.5f)
                    // {
                    //     blocks[x][y][z] = 1;
                    // }
                }
            }
        }

        return new Chunk(blocks, k, chunkSize);

    }

}

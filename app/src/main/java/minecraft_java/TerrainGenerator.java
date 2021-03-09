package minecraft_java;

public final class TerrainGenerator {
    
    static float scaleX = 0.1f;
    static float scaleY = 0.1f;
    static float amplitude = 5f;
    static float lowestValue = 0;

    public static double getBlockHeight(int x, int z){
        return amplitude * SimplexNoise.noise((double) x * scaleX, (double) z * scaleY) + amplitude + lowestValue;
    }

}

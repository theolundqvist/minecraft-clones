package minecraft_java.world;

import java.util.Arrays;

public class Blocks {
    public static final int AIR  = 0;
    public static final int GRASS = 1;
    public static final int STONE = 2;
    public static final int DIRT = 3;

    private static int[] transparent = new int[]{0};

    public static boolean isTransparent(int block) {
        for (int i : transparent) {
            if(block == i) return true;
        }
        return false;
    }
}

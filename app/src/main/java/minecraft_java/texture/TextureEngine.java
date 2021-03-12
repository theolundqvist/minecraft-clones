package minecraft_java.texture;

import java.awt.image.BufferedImage;

import org.joml.Vector3f;

public class TextureEngine {
    BufferedImage textureImg;

    public static int air   = 0;
    public static int grass = 1;
    public static int dirt  = 2;
    public static int stone = 3;

    public static int getTextureID(int blockID, Vector3f dir) {
        return 0;
    }  

}

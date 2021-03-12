package minecraft_java.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {
    private static final int BYTES_PER_PIXEL = 4;
    // 3 for RGB, 4 for RGBA
    static int blocksPerRow = 16;
    static int imageSize = 512;
    static int size = imageSize/blocksPerRow;
    public static int[] genTileMapTextureIDs(BufferedImage image){
        int nbrBlocks = blocksPerRow*blocksPerRow;
        int[] ids = new int[nbrBlocks];
        for (int i = 0; i < blocksPerRow*blocksPerRow; i++) {
            ids[i] = loadTexture(image, i);
        }
        return ids;
    }
    public static int loadTexture(BufferedImage image, int index) {
        
        int[] pixels = new int[size*size];
        int row = index % 16;
        int col = index / 16;
        image.getRGB(row*size, col*size, size, size, pixels, 0, size);
        
        ByteBuffer buffer = BufferUtils.createByteBuffer(size*size * BYTES_PER_PIXEL); 
        
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int pixel = pixels[y * size + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
                buffer.put((byte) (pixel & 0xFF)); // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component. Only for RGBA
            }
        }
        
        buffer.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS
        
        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using
        // whatever OpenGL method you want, for example:
        
        int textureID = glGenTextures(); // Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); // Bind texture ID

        // Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        // Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, size, size, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                buffer);

        // Return the texture ID so we can bind it later again
        return textureID;
    }

    public static BufferedImage loadImage(String loc) {
        try {
            return ImageIO.read(new File(loc));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
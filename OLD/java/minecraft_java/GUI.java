package minecraft_java;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
/**
 * @author Owen Butler
 */
public class GUI {

    //private TrueTypeFont font;
    //private TrueTypeFont font2;

    public GUI(String text) {

        // load a default java font
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        //font = new TrueTypeFont(awtFont, false);

    }

    public void drawText(String text) {
        //font.drawString((int) 100, (int) 50, "THE LIGHTWEIGHT JAVA GAMES LIBRARY", Color.yellow);
    }

}
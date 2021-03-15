package minecraft_java.texture;

import java.awt.image.BufferedImage;

import org.joml.Vector3f;
import minecraft_java.world.Blocks;

public class TextureEngine {
    static private int[] textures;

    public static void init(){
        BufferedImage textureImg = TextureLoader.loadImage("res/texture.png");
        textures = TextureLoader.genTileMapTextureIDs(textureImg);
    }
    static int i = 0;
    public static int getTextureID(int blockID, Vector3f dir) {
        //Gör lite smarta grejer för att returnera top/sida/botten beroende på dir. 
        //vissa block har bara en textur andra flera
        //får nog hårdkoda detta
        switch (blockID) {
            case Blocks.GRASS:
                if(dir.y == 0) blockID += 3;
                else if(dir.y == -1) blockID += 2;
                break;
        
            default:
                break;
        }
        return textures[blockID-1];//a[i++%(a.length-1)]];
    }  

}

package minecraft_java.mesh;

import java.util.ArrayList;

import minecraft_java.world.Chunk;

import static org.lwjgl.opengl.GL46.*;

public class ChunkMesh {
    private ArrayList<QuadMesh> quads;

    public ChunkMesh(Chunk chunk) {
        quads = MeshEngine.createMesh(chunk);
        quads.sort((q1, q2) -> q1.textureID - q2.textureID);
    }

    public void draw(){
        int lastTextureId = -1;
        for (QuadMesh q : quads) {
            if(lastTextureId != q.textureID) {
                glBindTexture(GL_TEXTURE_2D, q.textureID);
                lastTextureId = q.textureID;
            }
            q.draw();
            
        }
    }

    
}

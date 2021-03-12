package minecraft_java.mesh;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import minecraft_java.texture.TextureEngine;

public class QuadMesh {
    public float[][] vertexData = new float[4][3];
    public float[] color;
    public float[] normal;
    public int textureID;

    public QuadMesh(Vector3f pos, Vector3f dir, Vector3f c){
        color = new float[]{c.x, c.y, c.z};
        calculateCorners(pos, dir);
    }
    
    public QuadMesh(Vector3f pos, Vector3f dir, int blockID) {
        this.textureID = TextureEngine.getTextureID(blockID, dir);
        calculateCorners(pos, dir);
    }

    private void calculateCorners(Vector3f pos, Vector3f dir){
        dir.normalize();
        Vector3f side1 = new Vector3f().orthogonalize(dir).div(2);
		Vector3f side2 = new Vector3f().orthogonalize(dir).cross(dir).div(2);
		Vector3f corner = side1.add(side2).add(pos);
		
		for (int i = 0; i < 4; i++) { 
			corner
			.sub(pos).
			rotateAxis((float) Math.PI / 2, dir.x, dir.y, dir.z)
			.add(pos);
            vertexData[i] = new float[]{corner.x, corner.y, corner.z};
        }
        normal = new float[]{dir.x, dir.y, dir.z};
    }

    public void draw(){
        if(color != null) glColor3fv(color);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3fv(vertexData[0]);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3fv(vertexData[1]);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3fv(vertexData[2]);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3fv(vertexData[3]);
        //for (float[] fs : vertexData) {
            //glVertex3fv(fs);
        //}
    }
}

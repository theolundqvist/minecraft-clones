package minecraft_java.mesh;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import minecraft_java.texture.TextureEngine;

public class QuadMesh {
    public float[][] vertexData = new float[4][3];
    public float[] color;
    public float[] normal;
    public int textureID;
    private float[][] textureCoords;

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
        Vector3f up = new Vector3f(0, 1, 0);
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

        //TEXTURE MAPPING
        if(dir.z < 0 || dir.x > 0){
            textureCoords = new float[][] { 
                { 0, 0 }, 
                { 0, 1 }, 
                { 1, 1 }, 
                { 1, 0 } };
        }
        else
            textureCoords = new float[][] { 
                { 1, 1 }, 
                { 1, 0 }, 
                { 0, 0 }, 
                { 0, 1 } };
    }

    public void draw(){
        if(color != null) glColor3fv(color);
        for (int i = 0; i < vertexData.length; i++) {
            glTexCoord2fv(textureCoords[i]);
            glVertex3fv(vertexData[i]);
        }
    }
}

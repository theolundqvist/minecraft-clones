package minecraft_java;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

public class QuadMesh {
    public float[][] vertexData = new float[4][3];

    public QuadMesh(Vector3f pos, Vector3f dir){
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
    }

    public void draw(Vector3f color){
        glColor3fv(new float[]{color.x, color.y, color.z});
        for (float[] fs : vertexData) {
            glVertex3fv(fs);
        }
    }
}

package minecraft_java;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;

public class Camera {
    private float fov;
    private Vector3f pos;
    private float rotX, rotY;
    private float clipNear = 0.5f, clipFar = 1000;

    //WINDOW
    private long window;
    private int width, height;

    //FOR RENDER
    private Matrix4f mat;
    private FloatBuffer buffer;

    public Camera(float fov){
        this.fov = fov;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }


    public void updateCanvasSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void render(){
        glMatrixMode(GL_PROJECTION);

        glMatrixMode(GL_PROJECTION);
        glLoadMatrixf(mat.setPerspective(
            (float) Math.toRadians(fov), 
            (float) width / height, clipNear, clipFar).get(buffer));

        glMatrixMode(GL_MODELVIEW);
        mat.identity().rotateX(rotX).rotateY(rotY).translate(-pos.x, -pos.y, -pos.z);
        glLoadMatrixf(mat.get(buffer));
    }

}

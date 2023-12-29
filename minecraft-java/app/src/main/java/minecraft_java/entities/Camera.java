package minecraft_java.entities;

import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;

import minecraft_java.App;

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

    public Camera(float fov, long window){
        this.fov = fov;
        mat = new Matrix4f();
        buffer = BufferUtils.createFloatBuffer(16);
        this.window = window;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
    public void setRot(Vector3f v) {
        this.rotX = v.x;
        this.rotY = v.z;
    }
    public float getFov() {
        return fov;        
    }


    public void updateCanvasSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void renderView(Player p){

        setPos(p.getPos());
        setRot(p.getRot());
        mat = p.getMat();


        glMatrixMode(GL_PROJECTION);

        glLoadMatrixf(mat.setPerspective(
            (float) Math.toRadians(fov), 
            (float) width / height, clipNear, clipFar).get(buffer));

        glMatrixMode(GL_MODELVIEW);
        mat.identity().rotateX(rotX).rotateY(rotY).translate(-pos.x, -pos.y, -pos.z);
        
        glLoadMatrixf(mat.get(buffer));
    }

}

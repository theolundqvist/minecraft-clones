package minecraft_java;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Player {
    private Vector3f pos;
    private Vector3f rot;
    private float movementSpeed = 5f;

    final byte FORWARDS = 1;
    final byte BACKWARDS = 2;
    final byte LEFT = 3;
    final byte RIGHT = 4;



    public Player() {
        pos = new Vector3f(0, 0, 0);
        rot = new Vector3f();
    }

    public Player(float x, float y, float z) {
        pos = new Vector3f(x, y, z);
        rot = new Vector3f();
    }

    //GET/SET

    public Vector3f getRot() {return rot;}
    public void setRot(Vector3f rot) {this.rot = rot;}

    public Vector3f getPos(){return pos;}
    public void setPos(Vector3f c){pos = c;}
    
    public void draw(){
        App.drawBlock(pos.x, pos.y, pos.z, new Vector3f(0,0,0));
        App.drawBlock(pos.x, pos.y+1, pos.z, new Vector3f(0, 0, 0));
    }

    Matrix4f mat = new Matrix4f();
    
    private float relativeSpeed;
    public void move(byte DIR) {
        
        //Vettefan va detta gör
        Vector3f FB = new Vector3f();
        Vector3f RL = new Vector3f();
        mat.positiveZ(FB).negate().mul(relativeSpeed);
        FB.y = 0.0f; // <- restrict movement on XZ plane
        mat.positiveX(RL).mul(relativeSpeed);

        switch (DIR) {
            case FORWARDS:
                pos.add(FB);
                break;

            case BACKWARDS:
                pos.sub(FB);
                break;
        
            case LEFT:
                pos.sub(RL);
                break;

            case RIGHT:
                pos.add(RL);
                break;
        }
    }

    public Matrix4f getMat(){
        return mat;
    }

    public void setTimeDelta(float f) {
        relativeSpeed = f * movementSpeed;
    }

    public void setRotation(float mouseX, float mouseY) {
        rot = new Vector3f(mouseY, 0, mouseX);
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }
    
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
}

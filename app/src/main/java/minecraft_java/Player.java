package minecraft_java;

import org.joml.Vector3f;

public class Player {
    private Vector3f pos;
    private Vector3f moveDir;
    private Vector3f lookDir;
    private Camera cam;

    final byte FORWARDS = 1;
    final byte BACKWARDS = 2;
    final byte LEFT = 3;
    final byte RIGHT = 4;

    

    public Player() {
        pos = new Vector3f(0f, 1f, 0f);
        moveDir = new Vector3f();
    }

    public Player(float x, float y, float z) {
        pos = new Vector3f(x, y, z);
        moveDir = new Vector3f();
    }

    //GET/SET
    public Vector3f getMoveDir() {return moveDir;}
    public void setMoveDir(Vector3f dir) {this.moveDir = dir;}

    public Vector3f getLookDir() {return lookDir;}
    public void setLookDir(Vector3f dir) {this.lookDir = dir;}

    public Vector3f getPos(){return pos;}
    public void setPos(Vector3f c){pos = c;}
    
    public void draw(){
        //App.drawBlock(pos.x, pos.y, pos.z, new Vector3f(0,0,0));
        //App.drawBlock(pos.x, pos.y+1, pos.z, new Vector3f(0, 0, 0));
    }

    //private float relativeSpeed();

    public void move(byte DIR) {
        switch (DIR) {
            case FORWARDS:

                break;
        
            default:
                break;
        }
    }

    public void setRotation(float mouseX, float mouseY) {
    }

    public float getMovementSpeed() {
        return 0;
    }

    public void setRelativeSpeed(float f) {
    }

    public Camera getCam(){
        return cam;
    }
}

package minecraft_java;

import org.joml.Vector3f;

public class Player {
    private Vector3f pos;
    private Vector3f rot;
    private float movementSpeed = 1f;

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

    private float relativeSpeed;
    public void move(byte DIR) {
        switch (DIR) {
            case FORWARDS:
                pos.add(rotNorm().mul(relativeSpeed));
                break;

            case BACKWARDS:
                pos.add(rotNorm().negate().mul(relativeSpeed));
                break;
        
            case LEFT:
                pos.add(rotNorm().rotateY((float)Math.PI/2).mul(relativeSpeed));
                break;

            case RIGHT:
                pos.add(rotNorm().rotateY((float) -Math.PI / 2).mul(relativeSpeed));
                break;
        }
    }

    private Vector3f rotNorm(){
        return new Vector3f(rot).normalize();
    }

    public void setTimeDelta(float f) {
        relativeSpeed = f * movementSpeed;
    }

    public void setRotation(float mouseX, float mouseY) {
        rot = new Vector3f(mouseX, 0, mouseY);
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }
}

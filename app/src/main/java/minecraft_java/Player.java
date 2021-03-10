package minecraft_java;

import org.joml.Vector3f;

public class Player {
    private Vector3f pos;

    public Player() {
        pos = new Vector3f(0f, 1f, 0f);
    }

    public Player(float x, float y, float z) {
        pos = new Vector3f(x, y, z);
    }

    public void draw(){
        App.drawBlock(pos.x, pos.y, pos.z, new Vector3f(0,0,0));
        App.drawBlock(pos.x, pos.y+1, pos.z, new Vector3f(0, 0, 0));
    }

    public Vector3f getPos(){
        return pos;
    }

    public void setPos(Vector3f c){
        pos = c;
    }


}

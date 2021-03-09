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

    public Vector3f getPos(){
        return pos;
    }

    public void setPos(Player p, Vector3f c){
        p.pos = c;
    }


}

package minecraft_java;

import org.joml.Vector3f;

public class Player {
    private Vector3f coords;

    public Player() {
        coords = new Vector3f(0f, 1f, 0f);
    }

    public Player(float x, float y, float z) {
        coords = new Vector3f(x, y, z);
    }

    public Vector3f getPos(){
        return coords;
    }

    public void setPos(Player p, Vector3f c){
        p.coords = c;
    }


}

package minecraft_java;

import java.util.HashMap;


public class World {
    private HashMap<Key, Chunk> worldMap;
    private int size;

    public World(int size) {
        this.size = size;
    }

    public HashMap<Key,Chunk> getWorldMap() {
        return this.worldMap;
    }

    public void setWorldMap(HashMap<Key,Chunk> worldMap) {
        this.worldMap = worldMap;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }




    private static class Key{
        private int x;
        private int y;

        public Key(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public boolean equals(Key k){
            return (x == k.x && y == k.y);
        }

        public String ToString(){
            return ("(" + x + ", " + y + ")");
        } 
    }

}

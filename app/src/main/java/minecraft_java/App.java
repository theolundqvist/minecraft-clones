package minecraft_java;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

/*
TODO:

WORLD
* sammanfoga chunks, om chunken vid sidan inte finns, kör TerrainGenerator på den x,z koodinaten utan att spara värdet. men då fuckar träd upp?
* ett block kan sparas som en enda byte
* ta bort mesh från unloaded chunks när vi når ett visst antal
* spara ändringar i en chunk kan man antingen spara hela chunken som den är eller bara ändringar; generera + ändringar
* (gzip unused chunks to save memory)
* variabel höjd. (gör chunken lika hög som högsta blocket i heightmap?) (chunk innehåller chunklets? 16x16x16)

RENDERING - LUDVIG
* ladda chunks i en cirkel
* rendera bara chunks där spelaren tittar. i en kon från kameran med theta = fov

UTSEENDE
* dimma,
* texturer
* bättre ljussättning top-sida-botten 0.6-0.4-0.2 ish. (dir.y = (1, 0, -1)).
* solljus, luftblock med block över ritar skugga på närmaste blocket under.
* fackla. lista med artificella ljuskällor i chunk. färga alla block beroende på avstånd från dessa.


GENERATION
* snyggare generation, fler oktaver, (lager noise)
* olika blocktyper


PLAYER
THEO - * first person, 
* change speed with scrollwheel,
* collisions

REFACTOR
THEO - * APP



*/ 

public class App {
	private long window;
	private int width = 800;
	private int height = 600;
	private float mouseX = 0.0f, mouseY = 0.0f;

	private boolean[] keyDown = new boolean[GLFW_KEY_LAST + 1];


	double[][] heightMap = new double[20][20];

	private World world;
	private Player player;
	private Camera cam;

	private void setup() {
		// SETTINGS
		System.out.println("Drag the mouse while holding the left mouse button to rotate the camera");
		System.out.println("Press ENTER to change the center position");
		System.out.println("Scroll the mouse-wheel to zoom in/out");

		glClearColor(0.9f, 0.9f, 0.9f, 1.0f); // BACKGRUNDSFÄRGEN
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED); // MUSPEKARE SYNLIG ELLER EJ, disable i meny
		glEnable(GL_DEPTH_TEST); // ???
		glEnable(GL_CULL_FACE); // RITA BARA FRAMSIDAN AV TRIANGLAR
		glfwSwapInterval(1); // VSYNC
		bindKeyEvents();

		//drawBlock(3, 1, 3, new Vector3f(0,0,0));

		world = new World(16);
		player = new Player(0, 55, 0);
		cam = new Camera(45, window);
		cam.updateCanvasSize(width, height);


	}

	private void render() {
		cam.renderView(player);
	}

	private void update(){
		handleKeyEvents();
		//System.out.println(player.getPos().toString());
		world.updateChunks(player);
		
		glBegin(GL_QUADS);
		world.draw();
		glEnd();
	}

	private long lastTime = System.nanoTime();
	private void handleKeyEvents() {
		long thisTime = System.nanoTime();
		float diff = (float) ((thisTime - lastTime) / 1E9);
		lastTime = thisTime;
		player.setTimeDelta(diff);

		// if (keyDown[GLFW_KEY_LEFT_SHIFT])
		// 	move *= 2.0f;
		// if (keyDown[GLFW_KEY_LEFT_CONTROL])
		// 	move *= 0.5f;
		player.setRotation(mouseX, mouseY);

		if (keyDown[GLFW_KEY_W])
			player.move(player.FORWARDS);
		if (keyDown[GLFW_KEY_S])
			player.move(player.BACKWARDS);
		if (keyDown[GLFW_KEY_A])
			player.move(player.LEFT);
		if (keyDown[GLFW_KEY_D])
			player.move(player.RIGHT);

	}

	public static void drawBlock(float x, float y, float z, Vector3f color){
		Vector3f mid = new Vector3f(x, y, z);
		Vector3f offset = new Vector3f(1, 0, 0);
		Vector3f[] dirs = MeshEngine.getAllDir();
		for (Vector3f dir : dirs) {
			CubeFace qd = new CubeFace(new Vector3f(x+dir.x/2,y+dir.y/2,z+dir.z/2), dir, color);
			glBegin(GL_QUADS);
				qd.draw();
			glEnd();
		}
	}



	Vector3f moveDir = new Vector3f();
	void bindKeyEvents() {
		glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
			if (w > 0 && h > 0) {
				width = w;
				height = h;
				cam.updateCanvasSize(width, height);
			}
		});
		glfwSetCursorPosCallback(window, (win, x, y) -> {
			mouseX = (float) x / width;
			mouseY = (float) y / height;
		});
		//CHANGE MOVEMENT SPEED
		glfwSetScrollCallback(window, (win, x, y) -> {
			if (y > 0) {
				player.setMovementSpeed(player.getMovementSpeed()/1.1f);
			} else {
				player.setMovementSpeed(player.getMovementSpeed() * 1.1f);
			}
		});
		glfwSetKeyCallback(window, (win, k, s, a, m) -> {
			if (k == GLFW_KEY_ESCAPE && a == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true);

			if (a == GLFW_PRESS || a == GLFW_REPEAT) keyDown[k] = true;
			else keyDown[k] = false;
				
		});
	}

	private void init(){

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		window = glfwCreateWindow(width, height, "MINEOFF-RIP!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
		nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
		width = framebufferSize.get(0);
		height = framebufferSize.get(1);
		glfwMakeContextCurrent(window);
		GL.createCapabilities(); // context

		setup();

		while (!glfwWindowShouldClose(window)) {

			render();

			glViewport(0, 0, width, height);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			update();
			
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}



	public static void main(String[] args) {
		new App().init();
	}


}
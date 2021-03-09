package minecraft_java;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

public class App {
	private static long window;
	private static int width = 800;
	private static int height = 600;
	private static float zoom = 20;
	private static int mouseX, mouseY;
	private static final Vector3f center = new Vector3f();
	private static float pitch = 0.3f, yaw = 0.2f;

	//TEST
	private static float rotationAngle = 1f;
	static Vector3f v0 = new Vector3f(0, 1, 0);
	static Vector3f v1 = new Vector3f(1, 1, 0);
	static Vector3f v2 = new Vector3f(0, 1, 0);

	private static void draw(){
		//renderCube(0 ,0, 0);
		renderGrid();
		//v2.rotateX(Math.toRadians(rotationAngle));
		
		//v2.add(v1);

		glBegin(GL_LINES);
		glColor3f(0, 0, 0);
		glVertex3f(v0.x(), v0.y(), v0.z());
		glVertex3f(v1.x(), v1.y(), v1.z());

		glVertex3f(v1.x(), v1.y(), v1.z());
		glVertex3f(v1.x() + v2.x(), v1.x() + v2.y(), v1.x() + v2.z());
		//glVertex3f(v2.x(), v2.y(), v2.z());
		glEnd();
		printFPS();
	}


	static double lastTime = glfwGetTime();
	static int nbFrames = 0;

	private static void printFPS() {
     double currentTime = glfwGetTime();
     nbFrames++;
     if ( currentTime - lastTime >= 1.0 ){ // If last prinf() was more than 1 sec ago
         // printf and reset timer
         System.out.println("fps\n" + nbFrames);
         nbFrames = 0;
         lastTime += 1.0;
     }
	}


	private static void start(){


		

	}

	static void setupCameraControls() {
		glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
			if (w > 0 && h > 0) {
				width = w;
				height = h;
			}
		});
		glfwSetCursorPosCallback(window, (win, x, y) -> {
			if (glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
				yaw += ((int) x - mouseX) * 0.01f;
				pitch += ((int) y - mouseY) * 0.01f;
			}
			mouseX = (int) x;
			mouseY = (int) y;
		});
		glfwSetScrollCallback(window, (win, x, y) -> {
			if (y > 0) {
				zoom /= 1.1f;
			} else {
				zoom *= 1.1f;
			}
		});
	}





	private static void renderCube(int x, int y, int z) {
		glBegin(GL_QUADS);
		glColor4f(0.0f, 0.0f, 0.2f, 0.5f);
		glVertex3i(-1, -1, 0);
		glVertex3i(0, -1, 0);
		glVertex3i(0, 0, 0);
		glVertex3i(-1, 0, 0);


		//midOfPlane.cross(1,0,0);

		//midOfPlane = midOfPlane.add(mid);

		//System.out.println(midOfPlane.toString());

		glEnd();
	}

	private static void renderPlane(){
		
	}

	private static void renderGrid() {
		glBegin(GL_LINES);
		glColor3f(0.2f, 0.2f, 0.2f);
		for (int i = -20; i <= 20; i++) {
			glVertex3f(-20.0f, 0.0f, i);
			glVertex3f(20.0f, 0.0f, i);
			glVertex3f(i, 0.0f, -20.0f);
			glVertex3f(i, 0.0f, 20.0f);
		}
		glEnd();
	}

	public static void main(String[] args) {

		start();








		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		window = glfwCreateWindow(width, height, "Hello ArcBall Camera!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		System.out.println("Drag the mouse while holding the left mouse button to rotate the camera");
		System.out.println("Press ENTER to change the center position");
		System.out.println("Scroll the mouse-wheel to zoom in/out");

		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		glfwSetKeyCallback(window, (win, k, s, a, m) -> {
			if (k == GLFW_KEY_ESCAPE && a == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true);
			if (k == GLFW_KEY_ENTER && a == GLFW_PRESS) {
				center.set((float) Math.random() * 20.0f - 10.0f, 0.0f, (float) Math.random() * 20.0f - 10.0f);
			}
		});
		setupCameraControls();

		IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
		nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
		width = framebufferSize.get(0);
		height = framebufferSize.get(1);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();  //????
		glClearColor(0.9f, 0.9f, 0.9f, 1.0f);  //BACKGRUNDSFÄRGEN
		glEnable(GL_DEPTH_TEST);  //???
		glEnable(GL_CULL_FACE);  //RITA BARA FRAMSIDAN AV TRIANGLAR
		glfwSwapInterval(1); //VSYNC

		Matrix4f mat = new Matrix4f();

		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		while (!glfwWindowShouldClose(window)) {
			glViewport(0, 0, width, height);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glMatrixMode(GL_PROJECTION);

			glLoadMatrixf(
					mat.setPerspective((float) Math.toRadians(45.0f), (float) width / height, 0.01f, 100.0f).get(fb));
			glMatrixMode(GL_MODELVIEW);
			// Load arcball camera view matrix into 'mat':
			glLoadMatrixf(
				mat
				.translation(0, 0, -zoom)
				.rotateX(pitch)
				.rotateY(yaw)
				.translate(-center.x, -center.y, -center.z)
				.get(fb));
			
			// apply model transformation to 'mat':
			//glLoadMatrixf(mat.translate(center).get(fb));

			//DRAW

			draw();



			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}


}
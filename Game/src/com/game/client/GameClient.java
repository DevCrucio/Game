package com.game.client;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.game.util.Text;

public class GameClient {
	/*
	 * Variables
	 */
	private boolean run;
	private long time;
	private Gui gui;
	public Text text;

	/*
	 * Start Method
	 */
	public static void main(String[] args) {
		new GameClient();
	}

	/*
	 * Constructor with actual Game
	 */
	public GameClient() {
		create();
		run();
		destroy();
		System.exit(0);
	}

	/*
	 * Load necessary Stuff
	 */
	private void create() {
		try {
			// Create OpenGL Context
			Display.setDisplayMode(new DisplayMode(960, 540));
			Display.setTitle("Game");
			Display.setVSyncEnabled(true);
			Display.create();
			Keyboard.create();
			Mouse.create();
			// Init OpenGL
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 960, 0, 540, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			// Init other Stuff
			run = true;
			text = new Text();
			gui = new GuiIntro(this);
			gui.create();
			// First Delta timestamp
			getDelta();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Game Loop
	 */
	private void run() {
		while (run) {
			// Update
			float delta = getDelta();
			gui.update(delta);
			// Clear Screen
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT
					| GL11.GL_STENCIL_BUFFER_BIT);
			// Render
			GL11.glPushMatrix();
			gui.render();
			GL11.glPopMatrix();
			// Show on Screen
			Display.update();
			// Cap FPS
			Display.sync(60);
			// Close
			if (Display.isCloseRequested())
				run = false;
		}
	}

	/*
	 * Unload
	 */
	private void destroy() {
		gui.destroy();
		// Destroy OpenGL context
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}

	/*
	 * Stop the Game from a GUI
	 */
	public void stop() {
		run = false;
	}

	/*
	 * Delta Time Calculation 
	 * Delta in ms!
	 */
	private float getDelta() {
		long dif = System.nanoTime() - time;
		time = System.nanoTime();
		return (dif / 1000000f);
	}

	/*
	 * Gui Change Method
	 */
	public void setGui(Gui gui) {
		this.gui.destroy();
		this.gui = gui;
		this.gui.create();
	}
}

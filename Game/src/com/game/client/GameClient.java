package com.game.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.game.util.Text;
import com.game.util.Texture;

public class GameClient {
	/*
	 * Variables
	 */
	private boolean run;
	private long time;
	private Gui gui;
	public Text text;
	public Texture mouse;
	public int mouseX = 960 / 2, mouseY = 540 / 2;

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
			Mouse.setGrabbed(true);
			// Init OpenGL
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 960, 0, 540, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glClearColor(0.2f, 0.2f, 0.6f, 1.0f);
			// Init other Stuff
			run = true;
			text = new Text();
			try {
				mouse = new Texture("./src/Mouse.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			if (delta <= 100)
				gui.update(delta);
			// Mouse update
			mouseX += Mouse.getDX();
			if (mouseX < 0)
				mouseX = 0;
			if (mouseX > 960)
				mouseX = 960;
			mouseY += Mouse.getDY();
			if (mouseY < 0)
				mouseY = 0;
			if (mouseY > 540)
				mouseY = 540;
			// Clear Screen
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT
					| GL11.GL_STENCIL_BUFFER_BIT);
			// Render
			GL11.glPushMatrix();
			gui.render();
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glColor4f(1, 1, 1, 1);
			mouse.bind();
			GL11.glTranslatef(mouseX, mouseY, 0);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(-8, -8);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(8, -8);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(8, 8);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(-8, 8);
			GL11.glEnd();
			GL11.glPopMatrix();
			while (Mouse.next()) {
			}
			while (Keyboard.next()) {
			} // Clear Buffers
				// Show on Screen
			Display.update();
			// Cap FPS
			Display.sync(60);
			// Close
			if (Display.isCloseRequested())
				run = false;
			// Screenshot
			screen();
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

	/*
	 * Screenshot Method
	 */
	boolean down = false;

	public void screen() {
		if (!down) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F12)) {
				down = true;
				GL11.glReadBuffer(GL11.GL_FRONT);
				int width = Display.getDisplayMode().getWidth();
				int height = Display.getDisplayMode().getHeight();
				int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
				ByteBuffer buffer = BufferUtils.createByteBuffer(width * height
						* bpp);
				GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA,
						GL11.GL_UNSIGNED_BYTE, buffer);

				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH-mm-ss");
				File file = new File("./" + dateFormat.format(date) + ".png"); // The file to save to.
				String format = "PNG"; // Example: "PNG" or "JPG"
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						int i = (x + (width * y)) * bpp;
						int r = buffer.get(i) & 0xFF;
						int g = buffer.get(i + 1) & 0xFF;
						int b = buffer.get(i + 2) & 0xFF;
						image.setRGB(x, height - (y + 1), (0xFF << 24)
								| (r << 16) | (g << 8) | b);
					}
				}

				try {
					ImageIO.write(image, format, file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (!Keyboard.isKeyDown(Keyboard.KEY_F12)) {
				down = false;
			}
		}
	}
}

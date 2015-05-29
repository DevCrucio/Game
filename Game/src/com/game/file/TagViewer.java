package com.game.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.game.util.Text;
import com.game.util.Text.ALIGN;

public class TagViewer {
	public static Text text;

	public static void main(String[] args) {
		new TagViewer();
	}

	public TagViewer() {
		try {
			Display.setDisplayMode(new DisplayMode(400, 300));
			Display.setTitle("TagViewer");
			Display.setVSyncEnabled(true);
			Display.create();
			Mouse.create();
			Keyboard.create();
			init2D();
			loadTextures();
			getDelta();
			while (!Display.isCloseRequested()) {
				float delta = getDelta();
				update(delta);
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				GL11.glPushMatrix();
				render();
				GL11.glPopMatrix();
				Display.update();
				Display.sync(60);
			}
			Keyboard.destroy();
			Mouse.destroy();
			Display.destroy();
			System.exit(0);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void init2D() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 400, 0, 300, 100, -100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public long time;

	public float getDelta() {
		long dif = System.nanoTime() - time;
		time = System.nanoTime();
		return dif / 1000000f;
	}

	public void loadTextures() {
		text = new Text();
		tag.add(new TagSubtag("unnamed"));
	}

	/*
	 * Prefix render Methods
	 */
	public static void drawBorder(float x, float y, float w, float h) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + w, y);
		GL11.glVertex2f(x + w, y + 2);
		GL11.glVertex2f(x, y + 2);

		GL11.glVertex2f(x, y + h);
		GL11.glVertex2f(x + w, y + h);
		GL11.glVertex2f(x + w, y + h - 2);
		GL11.glVertex2f(x, y + h - 2);

		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + 2, y);
		GL11.glVertex2f(x + 2, y + h);
		GL11.glVertex2f(x, y + h);

		GL11.glVertex2f(x + w, y);
		GL11.glVertex2f(x + w - 2, y);
		GL11.glVertex2f(x + w - 2, y + h);
		GL11.glVertex2f(x + w, y + h);
		GL11.glEnd();
	}

	public static void drawButton(float x, float y, float w, float h,
			String text2) {
		GL11.glColor4f(.4f, 1f, 1f, 1f);
		drawBorder(x, y, w, h);
		GL11.glPushMatrix();
		GL11.glTranslatef(x + w / 2f, y + 5, 0);
		GL11.glScalef(0.5f, 0.5f, 1f);
		text.draw(text2, 1, ALIGN.CENTER);
		GL11.glPopMatrix();
	}

	public static void drawText(float x, float y, float w, float h,
			String text2, boolean active) {
		if (active)
			GL11.glColor4f(.8f, .2f, .2f, 1f);
		else
			GL11.glColor4f(1f, 1f, 1f, 1f);
		drawBorder(x, y, w, h);
		GL11.glPushMatrix();
		GL11.glTranslatef(x + w / 2f, y + 5, 0);
		GL11.glScalef(0.5f, 0.5f, 1f);
		GL11.glColor4f(.4f, 1f, 1f, 1f);
		text.draw(text2, 1, ALIGN.CENTER);
		GL11.glPopMatrix();

	}

	/*
	 * Loop Methods
	 */
	/*
	 * White -> Textbox Red -> Active Textbox Cyan -> Button
	 */
	public String path = "./unnamed.cgx";
	public List<Tag> tag = new ArrayList<Tag>();

	public void update(float delta) {
		while (Mouse.next()) {
			if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
				int x = Mouse.getX();
				int y = Mouse.getY();
				tag.get(tag.size() - 1).MouseDown(x, y, this);
				// Left Mouse Down (BASE BUTTONS)
				if (y >= 280) {
					if (x <= 100) {
						// Path
						activePath = true;
					} else {
						activePath = false;
						if (x <= 200) {
							// Load
							File file = new File(path);
							if (file.exists()) {
								tag.clear();
								TagSubtag main = (TagSubtag) Tag.load(file);
								tag.add(main);
							}
						} else if (x <= 300) {
							// Save
							Tag.save(tag.get(0), new File(path));
						} else if (x <= 400) {
							// New
							tag.clear();
							TagSubtag main = new TagSubtag(path);
							tag.add(main);
						}
					}
				} else if (y >= 260) {
					activePath = false;
					if (x <= 100) {
						// Back
						if (tag.size() > 1)
							tag.remove(tag.size() - 1);
					} else if (x <= 200) {
						// Type
					} else if (x <= 300) {
						// Name
					} else if (x <= 400) {
						// Content
					}
				} else {
					activePath = false;
				}
			}
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				tag.get(tag.size() - 1).KeyPress(Keyboard.getEventCharacter(),
						Keyboard.getEventKey());
				if (activePath) {
					if (Keyboard.getEventKey() == Keyboard.KEY_BACK) {
						char[] chars = path.toCharArray();
						path = "";
						for (int i = 0; i < chars.length - 1; i++) {
							path = path + chars[i];
						}
					} else if (Keyboard.getEventCharacter() == '.'
							|| Keyboard.getEventCharacter() == '/'
							|| (Keyboard.getEventCharacter() >= 'a' && Keyboard
									.getEventCharacter() <= 'z')
							|| (Keyboard.getEventCharacter() >= 'A' && Keyboard
									.getEventCharacter() <= 'Z')
							|| (Keyboard.getEventCharacter() >= '0' && Keyboard
									.getEventCharacter() <= '9')) {
						path = path + Keyboard.getEventCharacter();
					}
				}
			}
		}
	}

	boolean activePath = false;

	public void render() {
		Tag current = tag.get(tag.size() - 1);
		String content = "Value unidentified!";
		if (current instanceof TagSubtag) {
			content = "" + ((TagSubtag) current).tags.size();
		}
		if (current instanceof TagInt) {
			content = "" + ((TagInt) current).getValue();
		}
		if (current instanceof TagString) {
			content = ((TagString) current).getValue();
		}
		if (current instanceof TagFloat) {
			content = "" + ((TagFloat) current).getValue();
		}
		if (current instanceof TagBoolean) {
			content = "" + ((TagBoolean) current).getValue();
		}

		drawText(0, 280, 100, 20, path, activePath);
		drawButton(100, 280, 100, 20, "Load");
		drawButton(200, 280, 100, 20, "Save");
		drawButton(300, 280, 100, 20, "New");
		drawButton(0, 260, 100, 20, "Back");
		drawText(100, 260, 100, 20, current.getType(), false);
		drawText(200, 260, 100, 20, current.getName(), false);
		drawText(300, 260, 100, 20, content, false);
		// Display Tag Info
		tag.get(tag.size() - 1).render();
	}
}

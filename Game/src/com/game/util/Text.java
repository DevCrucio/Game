package com.game.util;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class Text {
	private Texture tex;

	public static enum ALIGN {
		LEFT, RIGHT, CENTER;
	}

	public Text() {
		try {
			tex = new Texture("./src/font.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		tex.destroy();
	}

	public float length(String text, float size) {
		float length = 0;
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			float f1 = 0;
			switch (chars[i]) {
			case 'i':
				f1 = 4f;
				break;
			case 'f':
				f1 = 10f;
				break;
			case 'I':
				f1 = 8f;
				break;
			case 'l':
				f1 = 6f;
				break;
			case 't':
				f1 = 8f;
				break;
			case '[':
				f1 = 8f;
				break;
			case ']':
				f1 = 8f;
				break;
			case '!':
				f1 = 4f;
				break;
			case '"':
				f1 = 10f;
				break;
			case '(':
				f1 = 10f;
				break;
			case ')':
				f1 = 10f;
				break;
			case '{':
				f1 = 10f;
				break;
			case '}':
				f1 = 10f;
				break;
			case '*':
				f1 = 10f;
				break;
			case '~':
				f1 = 14f;
				break;
			case '.':
				f1 = 4f;
				break;
			case ',':
				f1 = 4f;
				break;
			case ':':
				f1 = 4f;
				break;
			case ';':
				f1 = 4f;
				break;
			case '<':
				f1 = 10f;
				break;
			case '>':
				f1 = 10f;
				break;
			case '|':
				f1 = 4f;
				break;
			case '\'':
				f1 = 6f;
				break;
			case ' ':
				f1 = 6f;
				break;
			case 'k':
				f1 = 10f;
				break;
			default:
				f1 = 12f;
				break;
			}
			length += f1 * size;
		}
		return length;

	}

	public void draw(String text, float size, ALIGN align) {

		GL11.glPushMatrix();
		switch (align) {
		case RIGHT:
			GL11.glTranslatef(-length(text, size), 0, 0);
			break;
		case CENTER:
			GL11.glTranslatef(-(length(text, size) / 2f), 0, 0);
			break;
		default:

			break;
		}
		GL11.glScalef(size, size, 1);
		tex.bind();
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			byte b = (byte) chars[i];
			int tx = 0;
			int ty = 0;
			while (b >= 16) {
				b -= 16;
				ty++;
			}
			tx = b;
			float f1;
			switch (chars[i]) {
			case 'i':
				f1 = 4f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case 'f':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case 'I':
				f1 = 8f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case 'k':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case 'l':
				f1 = 6f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case 't':
				f1 = 8f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '[':
				f1 = 8f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case ']':
				f1 = 8f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '!':
				f1 = 4f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '"':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '(':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case ')':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '{':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '}':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '*':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '~':
				f1 = 14f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '.':
				f1 = 4f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case ',':
				f1 = 4f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case ':':
				f1 = 4f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case ';':
				f1 = 4f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '<':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '>':
				f1 = 10f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '|':
				f1 = 4f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case '\'':
				f1 = 6f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			case ' ':
				f1 = 6f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			default:
				f1 = 12f;
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(tx / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(0, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, (ty + 1) / 16f);
				GL11.glVertex2f(f1, 0);
				GL11.glTexCoord2f((tx + (f1 / 16f)) / 16f, ty / 16f);
				GL11.glVertex2f(f1, 16);
				GL11.glTexCoord2f(tx / 16f, ty / 16f);
				GL11.glVertex2f(0, 16);
				GL11.glEnd();
				GL11.glTranslatef(f1, 0, 0);
				break;
			}
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glPopMatrix();
	}
}

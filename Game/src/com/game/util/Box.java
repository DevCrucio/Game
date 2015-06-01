package com.game.util;

import org.lwjgl.opengl.GL11;

public class Box {
	private float x, y, dx, dy;

	public Box() {
		this.x = 0;
		this.y = 0;
		this.dx = 0;
		this.dy = 0;
	}

	public void renderHitBox() {
		// TEST BOX
		if (true) {
			GL11.glPushMatrix();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			GL11.glTranslatef(getX(), getY(), 0);
			GL11.glColor4f(1, 0, 0, 1);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(-dx, -dy);
			GL11.glVertex2f(dx, -dy);
			GL11.glVertex2f(dx, -dy);
			GL11.glVertex2f(dx, dy);
			GL11.glVertex2f(dx, dy);
			GL11.glVertex2f(-dx, dy);
			GL11.glVertex2f(-dx, dy);
			GL11.glVertex2f(-dx, -dy);
			GL11.glEnd();
			GL11.glPopMatrix();
		}
		// END BOX
	}

	public Box(float x, float y, float dx, float dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}

	public boolean check(Box box) {
		if (box != null) {
			if (getX() + getDx() > box.getX() - box.getDx()) {
				if (getX() - getDx() < box.getX() + box.getDx()) {
					if (getY() + getDy() > box.getY() - box.getDy()) {
						if (getY() - getDy() < box.getY() + box.getDy()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// GET&SET
	public float getDx() {
		return dx;
	}

	public float getDy() {
		return dy;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

}

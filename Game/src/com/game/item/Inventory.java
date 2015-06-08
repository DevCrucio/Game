package com.game.item;

import org.lwjgl.opengl.GL11;

import com.game.client.World;
import com.game.util.Text.ALIGN;

public class Inventory {
	// 8 Slot Inventory
	public Item[] items;
	public int[] amount;

	public Inventory() {
		items = new Item[14];
		amount = new int[14];
	}

	public void drawItem(int num, World world) {
		if (items[num] != null && amount[num] > 0) {
			items[num].render();
			if (amount[num] != 1) {
				GL11.glPushMatrix();
				GL11.glColor3f(50f / 255f, 50f / 255f, 50f / 255f);
				GL11.glTranslatef(12, -14, 0);
				world.gg.gc.text.draw(amount[num] + "", 0.5f, ALIGN.RIGHT);
				GL11.glPopMatrix();
			}
		}
	}

	public boolean addItem(int ID, int amount) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null)
				if (items[i].ID == ID && amount < items[i].maxStack) {
					this.amount[i] += amount;
					return true;
				}
		}
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				this.items[i] = Item.items[ID].clone();
				this.amount[i] = amount;
				return true;
			}
			if (this.amount[i] == 0) {
				this.items[i] = Item.items[ID].clone();
				this.amount[i] = amount;
				return true;
			}
		}
		return false;
	}

	public int countItem(int ID) {
		int amo = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].ID == ID) {
					amo += amount[i];
				}
			}
		}
		return amo;
	}

	public boolean remItem(int ID, int amo) {
		if (countItem(ID) >= amo) {
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					if (items[i].ID == ID) {
						if (amount[i] > amo) {
							amount[i] -= amo;
							return true;
						} else if (amount[i] == amo) {
							amount[i] = 0;
							items[i] = null;
							return true;
						} else {
							amo -= amount[i];
							amount[i] = 0;
							items[i] = null;
						}
					}
				}
			}
		}
		return false;
	}
}

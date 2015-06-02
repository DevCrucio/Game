package com.game.item;

public class Inventory {
	// 8 Slot Inventory
	public int[] items;

	public Inventory() {
		items = new int[14];
	}

	public void drawItem(int num) {
		if (items[num] != -1) {
			Item.render(items[num]);
		}
	}
}

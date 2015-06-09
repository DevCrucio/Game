package com.game.item;

public abstract class Item implements Cloneable {
	public abstract void render();

	/*
	 * Standard Methods
	 */
	public int ID;
	public int maxStack;
	public String name = "";
	public String meta = "";
	public int rarity;

	public Item(int ID) {
		this.ID = ID;
		items[ID] = this;
		maxStack = 100;
	}

	public static void render(int num) {
		if (items[num] != null) {
			items[num].render();
		}
	}

	public static Item[] items = new Item[1024];
	/*
	 * Items
	 */
	public static Item ITEM_STONESWORD = new ItemStoneSword(0, 1);
	public static Item ITEM_STONEPICKAXE = new ItemStonePick(1, 1);
	public static Item ITEM_DIRT = new ItemDirt(2, 128, 2);
	public static Item ITEM_STONE = new ItemStone(3, 128, 3);
	public static Item ITEM_THAUMSWORD = new ItemThaumSword(4, 1);

	@Override
	public Item clone() {
		try {
			Item item = (Item) super.clone();
			return item;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void renderEquiped() {

	}
}

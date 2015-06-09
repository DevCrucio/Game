package com.game.item;

public abstract class TypeBlock extends TypeWeapon {
	public int BlockID;

	public TypeBlock(int ID, int BlockID) {
		super(ID);
		this.BlockID = BlockID;
	}

	@Override
	public void render() {

	}

}

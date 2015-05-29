package com.game.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagBoolean extends Tag {
	private boolean value;

	public TagBoolean(String name) {
		super(5, name);
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public void save(DataOutputStream dos) throws IOException {
		// Always
		dos.writeInt(ID);
		dos.writeUTF(name);
		// Write Value
		dos.writeBoolean(value);
	}

	@Override
	public void load(DataInputStream dis) throws IOException {
		// Read Value
		value = dis.readBoolean();
	}

	@Override
	public String toString() {
		return super.toString() + "Boolean[" + value + "]";
	}

	@Override
	public String getType() {
		return "TagBoolean";
	}

	@Override
	public void render() {
		TagViewer.drawButton(10, 230, 90, 20, "True");
		TagViewer.drawButton(100, 230, 290, 20, "False");
	}

	@Override
	public void MouseDown(int x, int y, TagViewer tv) {
		if (x >= 10 && x <= 100 && y >= 230 && y <= 250) {
			value = true;
		}
		if (x >= 100 && x <= 390 && y >= 230 && y <= 250) {
			value = false;
		}
	}

	@Override
	public void KeyPress(char c, int key) {
	}
}

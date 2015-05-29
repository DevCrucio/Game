package com.game.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class TagInt extends Tag {
	private int value;

	public TagInt(String name) {
		super(3, name);
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void save(DataOutputStream dos) throws IOException {
		// Always
		dos.writeInt(ID);
		dos.writeUTF(name);
		// Write Value
		dos.writeInt(value);
	}

	@Override
	public void load(DataInputStream dis) throws IOException {
		// Read Value
		value = dis.readInt();
	}

	@Override
	public String toString() {
		return super.toString() + "Int[" + value + "]";
	}

	@Override
	public String getType() {
		return "TagInt";
	}

	public boolean input = false;
	public String text = "0";

	@Override
	public void render() {
		TagViewer.drawButton(10, 230, 90, 20, "Set Value");
		TagViewer.drawText(100, 230, 290, 20, text, input);
	}

	@Override
	public void MouseDown(int x, int y, TagViewer tv) {
		if (x >= 10 && x <= 100 && y >= 230 && y <= 250) {
			if (text != "")
				value = Integer.parseInt(text);
		}
		if (x >= 100 && x <= 390 && y >= 230 && y <= 250) {
			input = true;
		} else {
			input = false;
		}
	}

	@Override
	public void KeyPress(char c, int key) {
		if (input) {
			if (key == Keyboard.KEY_BACK) {
				char[] chars = text.toCharArray();
				text = "";
				for (int i = 0; i < chars.length - 1; i++) {
					text = text + chars[i];
				}
			}
			if (c >= '0' && c <= '9') {
				text = text + c;
			}
		}
	}
}

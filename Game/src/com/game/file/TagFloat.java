package com.game.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class TagFloat extends Tag {
	private float value;

	public TagFloat(String name) {
		super(4, name);
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	@Override
	public void save(DataOutputStream dos) throws IOException {
		// Always
		dos.writeInt(ID);
		dos.writeUTF(name);
		// Write Value
		dos.writeFloat(value);
	}

	@Override
	public void load(DataInputStream dis) throws IOException {
		// Read Value
		value = dis.readFloat();
	}

	@Override
	public String toString() {
		return super.toString() + "Float[" + value + "]";
	}

	@Override
	public String getType() {
		return "TagFloat";
	}

	public boolean input = false;
	public String text = "";

	@Override
	public void render() {
		TagViewer.drawButton(10, 230, 90, 20, "Set Value");
		TagViewer.drawText(100, 230, 290, 20, text, input);
	}

	@Override
	public void MouseDown(int x, int y, TagViewer tv) {
		if (x >= 10 && x <= 100 && y >= 230 && y <= 250) {
			if (text != "")
				value = Float.parseFloat(text);
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
			if ((c >= '0' && c <= '9') || c == '.') {
				text = text + c;
			}
		}
	}
}

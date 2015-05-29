package com.game.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

public class TagSubtag extends Tag {
	public TagSubtag(String name) {
		super(1, name);
	}

	protected List<Tag> tags = new ArrayList<Tag>();

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public boolean hasTag(String name) {
		for (Tag tag : tags) {
			if (tag.name.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Tag getTag(String name) {
		for (Tag tag : tags) {
			if (tag.name.equals(name)) {
				return tag;
			}
		}
		return null;
	}

	public void remTag(String name) {
		for (Tag tag : tags) {
			if (tag.name.equals(name)) {
				tags.remove(tag);
				return;
			}
		}
	}

	@Override
	public void save(DataOutputStream dos) throws IOException {
		// Always
		dos.writeInt(ID);
		dos.writeUTF(name);
		// Amount of Subtags
		dos.writeInt(tags.size());
		// Write Tags
		for (Tag tag : tags) {
			tag.save(dos);
		}
	}

	@Override
	public void load(DataInputStream dis) throws IOException {
		// ID and Name already read
		// Read Amount
		int amount = dis.readInt();
		for (int i = 0; i < amount; i++) {
			int id = dis.readInt();
			String name = dis.readUTF();
			Tag tag = Tag.getTagFromID(id, name);
			tag.load(dis);
			tags.add(tag);
		}
	}

	@Override
	public String toString() {
		String text = super.toString() + "Subtag[" + tags.size() + "]\n{\n";
		for (Tag tag : tags) {
			text = text + tag.toString() + "\n";
		}
		text = text + "}";
		return text;
	}

	@Override
	public String getType() {
		return "TagSubtag";
	}

	public boolean active = false;
	public String tagname = "None";
	public int current = 0;

	// TagViewer
	@Override
	public void render() {
		// Left Side TagList
		if (tags.size() <= 12) {
			for (int i = 0; i < tags.size(); i++) {
				Tag tag = tags.get(i);
				TagViewer.drawButton(10, 230 - i * 20, 20, 20, "x");
				TagViewer.drawButton(30, 230 - i * 20, 20, 20, ">");
				TagViewer.drawText(50, 230 - i * 20, 140, 20, tag.getName(),
						false);
			}
		} else {
			for (int i = current; i < current + 12; i++) {
				Tag tag = tags.get(i);
				TagViewer.drawButton(10, 230 - (i - current) * 20, 20, 20, "x");
				TagViewer.drawButton(30, 230 - (i - current) * 20, 20, 20, ">");
				TagViewer.drawText(50, 230 - (i - current) * 20, 120, 20,
						tag.getName(), false);
			}
			TagViewer.drawButton(170, 230, 20, 20, "/\\");
			TagViewer.drawButton(170, 10, 20, 20, "\\/");
		}
		// 12 Maximum!

		// Right Side TagAdd
		TagViewer.drawText(210, 230, 180, 20, tagname, active);
		// List available
		TagViewer.drawButton(210, 210, 180, 20, "SubTag");
		TagViewer.drawButton(210, 190, 90, 20, "String");
		TagViewer.drawButton(300, 190, 90, 20, "Int");
		TagViewer.drawButton(210, 170, 90, 20, "Float");
		TagViewer.drawButton(300, 170, 90, 20, "Boolean");
	}

	@Override
	public void KeyPress(char c, int key) {
		if (active) {
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
					|| (c >= '0' && c <= '9') || c == '.' || c == '-') {
				tagname = tagname + c;
			}
			if (key == Keyboard.KEY_BACK) {
				char[] chars = tagname.toCharArray();
				tagname = "";
				for (int i = 0; i < chars.length - 1; i++) {
					tagname = tagname + chars[i];
				}
			}
		}
	}

	@Override
	public void MouseDown(int x, int y, TagViewer tv) {
		// Left Side TagList
		if (tags.size() <= 12) {
			for (int i = 0; i < tags.size(); i++) {
				Tag tag = tags.get(i);
				if (x >= 10 && x <= 30 && y >= 230 - i * 20
						&& y <= 250 - i * 20) {
					// Cross
					tags.remove(tag);
					return;
				} else if (x >= 30 && x <= 50 && y >= 230 - i * 20
						&& y <= 250 - i * 20) {
					// Open
					tv.tag.add(tag);
					return;
				}
			}
		} else {
			for (int i = current; i < current + 12; i++) {
				Tag tag = tags.get(i);
				if (x >= 10 && x <= 30 && y >= 230 - (i - current) * 20
						&& y <= 250 - (i - current) * 20) {
					// Cross
					tags.remove(tag);
					return;
				} else if (x >= 30 && x <= 50 && y >= 230 - (i - current) * 20
						&& y <= 250 - (i - current) * 20) {
					// Open
					tv.tag.add(tag);
					return;
				}
			}
			// Arrow up and down
			if (x >= 170 && x <= 190 && y >= 230 && y <= 250) {
				// UP
				if (current > 0) {
					current--;
				}
			}
			if (x >= 170 && x <= 190 && y >= 10 && y <= 30) {
				// DOWN
				if (current + 12 < tags.size()) {
					current++;
				}
			}

		}
		// New Tag Name
		if (x >= 210 && x <= 390 && y >= 230 && y <= 250) {
			active = true;
		} else {
			active = false;
		}
		// Subtag Buttons
		if (x >= 210 && x <= 390 && y >= 210 && y <= 230) {
			tags.add(new TagSubtag(tagname));
		}
		if (x >= 210 && x <= 300 && y >= 190 && y <= 210) {
			tags.add(new TagString(tagname));
		}
		if (x >= 300 && x <= 390 && y >= 190 && y <= 210) {
			tags.add(new TagInt(tagname));
		}
		if (x >= 210 && x <= 300 && y >= 170 && y <= 190) {
			tags.add(new TagFloat(tagname));
		}
		if (x >= 300 && x <= 390 && y >= 170 && y <= 190) {
			tags.add(new TagBoolean(tagname));
		}
	}
}

package com.game.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Tag {
	protected final String name;
	protected final int ID;

	public Tag(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	public abstract String getType();

	public final String getName() {
		return name;
	}

	public abstract void save(DataOutputStream dos) throws IOException;

	public abstract void load(DataInputStream dis) throws IOException;

	public static void save(Tag tag, File file) {
		if (file.exists())
			file.delete();
		try {
			file.createNewFile();
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			tag.save(dos);
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Tag load(File file) {
		if (!file.exists()) {
			return null;
		}
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			int id = dis.readInt(); // read ID of first Tag
			String name = dis.readUTF(); // read Name of first Tag
			Tag tag = getTagFromID(id, name);
			tag.load(dis);
			dis.close();
			return tag;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Tag getTagFromID(int ID, String name) {
		switch (ID) {
		case 1:
			return new TagSubtag(name);
		case 2:
			return new TagString(name);
		case 3:
			return new TagInt(name);
		case 4:
			return new TagFloat(name);
		case 5:
			return new TagBoolean(name);
		}
		return null;
	}

	@Override
	public String toString() {
		return (name + " ");
	}

	// TagViewer Methods
	public void MouseDown(int x, int y, TagViewer tv) {

	}

	public void KeyPress(char c, int key) {

	}

	public void render() {

	}
}

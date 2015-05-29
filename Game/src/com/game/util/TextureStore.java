package com.game.util;

import java.util.HashMap;

public class TextureStore {
	public HashMap<String, Texture> tex = new HashMap<String, Texture>();

	public Texture get(String name) {
		if (tex.containsKey(name))
			return tex.get(name);
		else
			return null;
	}

	public void put(String name, Texture tex) {
		if (!this.tex.containsKey(name))
			this.tex.put(name, tex);
	}

	public Texture rem(String name) {
		Texture t = get(name);
		tex.remove(name);
		return t;
	}
}

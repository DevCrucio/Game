package com.game.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {
	private int ID;

	public Texture(String path) throws IOException {
		ID = loadPNGTexture(path, GL13.GL_TEXTURE0);
	}

	private int loadPNGTexture(String filename, int textureUnit) {
		ByteBuffer buf = null;
		int tWidth = 0;
		int tHeight = 0;

		try {
			InputStream in = new FileInputStream(filename);
			PNGDecoder decoder = new PNGDecoder(in);

			tWidth = decoder.getWidth();
			tHeight = decoder.getHeight();
			buf = ByteBuffer.allocateDirect(4 * decoder.getWidth()
					* decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
			buf.flip();

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		int texId = GL11.glGenTextures();
		GL13.glActiveTexture(textureUnit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, tWidth, tHeight,
				0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
				GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_REPEAT);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);

		return texId;
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void destroy() {
		GL11.glDeleteTextures(ID);
	}
}

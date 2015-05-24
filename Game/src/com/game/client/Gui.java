package com.game.client;

public abstract class Gui {
	public final GameClient gc;

	public Gui(GameClient gc) {
		this.gc = gc;
	}

	public abstract void update(float delta);

	public abstract void render();

	public void create() {

	}

	public void destroy() {

	}
}

package com.game.client;

public class GuiIntro extends Gui {

	public GuiIntro(GameClient gc) {
		super(gc);
	}

	@Override
	public void create() {
		gc.setGui(new GuiGame(gc, "127.0.0.1", 12345, "Crucio", "abc123"));
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

}

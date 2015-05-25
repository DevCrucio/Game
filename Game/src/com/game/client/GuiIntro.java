package com.game.client;

public class GuiIntro extends Gui {

	public GuiIntro(GameClient gc) {
		super(gc);
	}

	@Override
	public void create() {
		gc.setGui(new GuiGame(gc, "88.153.74.109", 12345, "Test", "abc123"));
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

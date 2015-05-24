package com.game.client;

public class GuiGame extends Gui {

	public World world;

	public GuiGame(GameClient gc, String string, int i, String string2) {
		super(gc);
		// Setup World
		world = new World(this);
		world.player = new EntityPlayer(world, 1);
		world.entities.put(1, world.player);
	}

	@Override
	public void update(float delta) {
		world.update(delta);
	}

	@Override
	public void render() {
		world.render();
	}

}

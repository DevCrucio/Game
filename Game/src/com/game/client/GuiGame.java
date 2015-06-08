package com.game.client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.game.packet.Login;
import com.game.util.Misc;
import com.game.util.Texture;
import com.game.util.TextureStore;

public class GuiGame extends Gui {

	public World world;
	public Client client;
	public String pass, name;
	public TextureStore store;

	public GuiGame(GameClient gc, String ip, int port, String name, String pass) {
		super(gc);
		this.pass = pass;
		this.name = name;
		try {
			// Setup Client
			client = new Client(8192 * 4, 2048 * 4);
			client.addListener(new ClientListener(this));
			Misc.reg(client.getKryo());
			client.start();
			client.connect(5000, ip, port, port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void connected() {
		// Send Login Packet
		Login login = new Login();
		login.name = name;
		login.pass = pass;
		client.sendTCP(login);
	}

	public void login(int ID, String name) {
		// Setup World
		world = new World(this);
		world.player = new EntityOwn(world, ID);
		world.player.name = name;
		world.player.world.entities.put(ID, world.player);
	}

	@Override
	public void update(float delta) {
		if (world != null)
			world.update(delta);
	}

	@Override
	public void render() {
		if (world != null)
			world.render();
	}

	@Override
	public void create() {
		store = new TextureStore();
		try {
			store.put("Terrain", new Texture("./src/Terrain.png"));
			store.put("Char", new Texture("./src/Char.png"));
			store.put("Gui", new Texture("./src/Gui.png"));
			store.put("Inv", new Texture("./src/Inv.png"));
			store.put("Marker", new Texture("./src/Marker.png"));
			store.put("ItemInfo", new Texture("./src/ItemInfo.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void destroy() {
		store.rem("Terrain").destroy();
		store.rem("Char").destroy();
		store.rem("Gui").destroy();
		store.rem("Inv").destroy();
		store.rem("Marker").destroy();
		store.rem("ItemInfo").destroy();
	}

}

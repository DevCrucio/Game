package com.game.client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.game.packet.Login;
import com.game.util.Misc;

public class GuiGame extends Gui {

	public World world;
	public Client client;
	public String pass, name;

	public GuiGame(GameClient gc, String ip, int port, String name, String pass) {
		super(gc);
		this.pass = pass;
		this.name = name;
		try {
			// Setup Client
			client = new Client();
			client.addListener(new ClientListener(this));
			Misc.reg(client.getKryo());
			client.start();
			client.connect(5000, ip, port, port);
		} catch (IOException e) {
			e.printStackTrace();
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
		world.player = new EntityPlayer(world, ID);
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

}

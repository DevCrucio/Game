package com.game.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;
import com.game.util.Misc;

public class GameServer {
	/*
	 * Start Method
	 */
	public static void main(String[] args) {
		new GameServer();
	}

	/*
	 * Server
	 */
	public Server server;
	public World world;

	/*
	 * Server Start
	 */
	public GameServer() {
		try {
			server = new Server();
			server.addListener(new ServerListener(this));
			Misc.reg(server.getKryo());
			server.bind(12345, 12345);
			server.start();
			world = new World();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

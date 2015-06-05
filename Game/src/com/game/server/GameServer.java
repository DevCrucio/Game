package com.game.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;
import com.game.util.Misc;

public class GameServer extends Thread {
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
	public boolean run = true;

	/*
	 * Server Start
	 */
	public GameServer() {
		try {
			Misc.log("Starting Server ...");
			server = new Server(16384 * 4, 2048 * 4);
			server.addListener(new ServerListener(this));
			Misc.reg(server.getKryo());
			Misc.log("Port " + 12345 + " ...");
			server.bind(12345, 12345);
			server.start();
			Misc.log("Loading World ...");
			world = new World(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Loop
		new Console(this);
		Misc.log("Done!");
		getDelta();
		while (run) {
			world.update(getDelta());
		}
	}

	/*
	 * Delta
	 */
	private long time;

	private float getDelta() {
		long dif = System.nanoTime() - time;
		time = System.nanoTime();
		return (dif / 1000000f);
	}

}

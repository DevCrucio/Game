package com.game.util;

import java.sql.Timestamp;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.game.packet.Accept;
import com.game.packet.ChunkAdd;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;
import com.game.packet.Login;
import com.game.packet.PlayerAdd;

public class Misc {
	/*
	 * For Logging
	 */

	public static void log(String msg) {
		Date date = new Date();
		System.out.println("[" + new Timestamp(date.getTime()) + "] " + msg
				+ " ");
	}

	/*
	 * Method that registers on Client and Server the same way
	 */
	public static void reg(Kryo kryo) {
		// Array
		kryo.register(int[].class);
		// Own Classes
		kryo.register(Accept.class);
		kryo.register(ChunkAdd.class);
		kryo.register(EntityMove.class);
		kryo.register(EntityRem.class);
		kryo.register(Login.class);
		kryo.register(PlayerAdd.class);
	}
}

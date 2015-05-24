package com.game.util;

import java.sql.Timestamp;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.game.packet.Accept;
import com.game.packet.Login;

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
		kryo.register(Accept.class);
		kryo.register(Login.class);
	}
}

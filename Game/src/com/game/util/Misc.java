package com.game.util;

import java.sql.Timestamp;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;

public class Misc {
	/*
	 * For Logging
	 */
	public static Date date = new Date();

	public static void log(String msg) {
		System.out.println("[Info " + new Timestamp(date.getTime()) + "] "
				+ msg);
	}

	/*
	 * Method that registers on Client and Server the same way
	 */
	public static void reg(Kryo kryo) {

	}
}

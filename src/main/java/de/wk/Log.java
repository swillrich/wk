package de.wk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
	private static Logger logger = LogManager.getRootLogger();

	public static void main(String[] args) {
		out("hello world");
	}

	public static void out(String s) {
		logger.info(s);
	}

	public static void newLine() {
		logger.info("");
	}
}

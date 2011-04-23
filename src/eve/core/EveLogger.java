package eve.core;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class EveLogger {
	private static Logger log = initLogger();
	
	private static Logger initLogger() {
		Logger log = Logger.getLogger("eve");
		log.setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		handler.setFormatter(new EveFormatter());
		log.addHandler(handler);
		return log;
	}
	
	public static void severeLeve() {
		log.setLevel(Level.SEVERE);
	}
	
	public static void infoLevel() {
		log.setLevel(Level.INFO);
	}
	
	public static void debugLevel() {
		log.setLevel(Level.FINE);
	}
	
	public static void info(String message) {
		log.info(message);
	}
	
	public static void error(String message) {
		log.severe(message);
	}
	
	public static void debug(String message) {
		log.fine(message);
	}
}

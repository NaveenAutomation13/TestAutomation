package com.mmed.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mmed.utils.Utils;

public class ApplicationLogger {

    private ApplicationLogger() {

    }

    private static final Logger log = Logger.getLogger(ApplicationLogger.class);

    static {
        try {
            PropertyConfigurator.configure(Utils.getPropertyValue("log4jConfigPath"));
            log.info("Log4j appender configuration is successful !!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void info(String message) {
        log.info(message);
    }

    public static void Booleaninfo(boolean displayed) {
        log.info(displayed);

    }

    public static void info(int size) {
        log.info(size);

    }

	public static void info(boolean result) {
		// TODO Auto-generated method stub
		
	}
}
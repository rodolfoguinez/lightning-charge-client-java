package com.rodolfoguinez.lightningchargeclient;
import org.apache.logging.log4j.Logger;

public class LogFacade {
    private Logger logger;
    private static LogFacade instance;

    private LogFacade() {}
    public static LogFacade getInstance(){
        if(instance==null){
            instance = new LogFacade();
        }
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void debug(String s){
        if(logger !=null)
            logger.debug(s);
    }

    public void error(String s, Throwable t) {
        if(logger !=null)
            logger.error(s, t);
    }
}

package com.nata.utils;

import org.apache.log4j.Logger;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-18 16:32
 */
public class LogUtil {

    private static final Logger logger = Logger.getLogger(LogUtil.class);

    public static void debug(String debug){
        logger.debug(debug);
    }

    public static void info(String info){
        logger.info(info);
    }

    public static void error(String error){
        logger.info(error);
    }

    public static void warn(String warn){
        logger.info(warn);
    }
}

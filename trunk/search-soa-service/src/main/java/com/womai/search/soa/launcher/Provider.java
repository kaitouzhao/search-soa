package com.womai.search.soa.launcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 容器启动.
 * User: 赵立伟
 * Date: 12-11-8
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class Provider {

    private static Log logger = LogFactory.getLog(Provider.class);

    private static volatile boolean running = true;

    private static ApplicationContext ctx;

    public static void main(String[] args) {
        try {
            ctx = new ClassPathXmlApplicationContext(
                    new String[]{
                            "log4j.xml",
                            "applicationContext-aop.xml",
                            "applicationContext-dubbo.xml"
                    }
            );
            if (logger.isInfoEnabled()) {
                logger.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");
            }
        } catch (RuntimeException e) {
            logger.error("Dubbo service server started failed!", e);
            running = false;
            System.exit(1);
        }
        synchronized (Provider.class) {
            while (running) {
                try {
                    Provider.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }

}

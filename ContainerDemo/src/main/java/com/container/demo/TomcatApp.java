package com.container.demo;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author mason
 */
public class TomcatApp {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        Connector connector = tomcat.getConnector();
        connector.setPort(8080);

        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");

        String classPath = System.getProperty("user.dir");
        Context context = tomcat.addContext(host, "/", classPath);

        if (context instanceof StandardContext) {
            StandardContext standardContext=(StandardContext)context;
            standardContext.setDefaultContextXml("classpath:web.xml");
            Wrapper wrapper=tomcat.addServlet("/","HelloServlet",new HelloServlet());
            wrapper.addMapping("/hello");
        }

        tomcat.start();
        tomcat.getServer().await();
    }

}

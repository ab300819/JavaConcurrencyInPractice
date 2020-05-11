package com.container.demo;

import com.container.demo.servlet.HelloServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author mason
 */
public class TomcatApp {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        Path basePath=Paths.get(System.getProperty("java.io.tmpdir"));
        tomcat.setBaseDir(basePath.resolve("tomcat").toString());

        Connector connector = tomcat.getConnector();
        connector.setPort(8080);

        Host host = tomcat.getHost();
        host.setName("localhost");

        String classPath = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        System.out.println(classPath);
        Context context = tomcat.addContext(host, "/", classPath);

        String servletName = HttpServlet.class.getName();
        tomcat.addServlet("/", servletName, new HelloServlet());
        context.addServletMappingDecoded("/go", servletName);

        tomcat.start();
        tomcat.getServer().await();
    }

}

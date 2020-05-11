package com.container.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JettyAppEmbedServlet {

    public static void main(String[] args) throws Exception {
        Server server=new Server(8080);
        ServletHandler handler=new ServletHandler();
        server.setHandler(handler);

        handler.addServletWithMapping(HelloServlet.class,"/*");

        server.start();
        server.join();
    }

    public static class HelloServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().println("<h1>Hello From Hello Servlet</h1>");

        }
    }

}

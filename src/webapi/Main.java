package webapi;

import com.sun.net.httpserver.HttpServer;
import webapi.server.PageProvider;
import webapi.server.TodosHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/pages", new PageProvider());
        server.createContext("/todos", new TodosHandler());
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        System.out.println("Started..");
    }
}

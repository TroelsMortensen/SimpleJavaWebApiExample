package webapi;

import com.sun.net.httpserver.HttpServer;
import webapi.data.DataContext;
import webapi.server.PageProvider;
import webapi.server.TodosHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        DataContext context = new DataContext();
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/pages", new PageProvider());
        server.createContext("/todos", new TodosHandler(context));
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        System.out.println("Started..");
    }
}

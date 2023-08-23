package webapi;

import com.sun.net.httpserver.HttpServer;
import webapi.data.DataContext;
import webapi.server.PageProvider;
import webapi.server.TodosHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server..");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        addHandlers(server);
        server.start();

        System.out.println("Server started at http://localhost:8001/pages/index.html !  <--- click me to open in browser ;)");
    }

    private static void addHandlers(HttpServer server) {
        DataContext context = new DataContext();
        server.createContext("/pages", new PageProvider());
        server.createContext("/api/todos", new TodosHandler(context));
    }
}

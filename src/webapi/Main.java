package webapi;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import webapi.data.DataContext;
import webapi.server.CorsFixer;
import webapi.server.PageProvider;
import webapi.server.TodosHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        DataContext context = new DataContext();

        System.out.println("Starting server..");

        server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);

        addHandler("/pages", new PageProvider());
        addHandler("/api/todos", new TodosHandler(context));

        server.start();
        System.out.println("Server started at http://localhost:8001/pages/index.html !  <--- click me to open in browser ;)");
    }

    private static void addHandler(String url, HttpHandler handler) {
        server.createContext(url, new CorsFixer(handler));
    }
}

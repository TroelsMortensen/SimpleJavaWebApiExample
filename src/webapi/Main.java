package webapi;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import webapi.data.DataContext;
import webapi.server.CorsFixDecorator;
import webapi.server.PageProvider;
import webapi.server.TodosHandler;
import webapi.server.UserHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        DataContext context = new DataContext();

        System.out.println("Starting server..");

        // create http server
        server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);

        // add handlers
        // it is not strictly necessary with the CorsFixDecorator, but makes development easier, if they can open the website from outside of IntelliJ
        server.createContext("/pages", new CorsFixDecorator(new PageProvider()));
        server.createContext("/api/todos", new CorsFixDecorator(new TodosHandler(context)));
        server.createContext("/api/users", new CorsFixDecorator(new UserHandler(context)));

        // start server
        server.start();

        // print out link in console, to be clicked. Will open index page in browser.
        System.out.println("Server started at http://localhost:8001/pages/index.html !  <--- click me to open in browser ;)");
    }
}

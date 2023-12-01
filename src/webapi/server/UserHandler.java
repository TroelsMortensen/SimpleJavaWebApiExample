package webapi.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import webapi.data.DataContext;

import java.io.IOException;

public class UserHandler implements HttpHandler {
    private final DataContext context;

    public UserHandler(DataContext context) {
        this.context = context;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
//        String requestMethod = exchange.getRequestMethod();
//        switch (requestMethod) {
//            case "GET" -> handleGET(exchange);
//            case "POST" -> handlePOST(exchange);
//            default -> throw new RuntimeException("Error here"); // Todo write error back to client
//        }
    }

//    private void handleGET(HttpExchange exchange) {
//        List<User> users = context.getUsers();
//
//    }
}

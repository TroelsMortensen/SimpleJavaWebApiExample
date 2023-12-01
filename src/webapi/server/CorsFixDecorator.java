package webapi.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class CorsFixDecorator implements HttpHandler {

    private final HttpHandler handler;

    public CorsFixDecorator(HttpHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        handler.handle(exchange);
    }
}
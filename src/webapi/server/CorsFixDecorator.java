package webapi.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Request;
import webapi.data.entities.Todo;
import webapi.data.requests.CreateTodoRequest;

import java.io.IOException;
import java.io.OutputStream;

public class CorsFixDecorator implements HttpHandler {

    private final HttpHandler handler;

    public CorsFixDecorator(HttpHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        /**
         * The following is necessary when sending application/json, or other fancy stuff. Not with text/plain
         * When sending application/json, a "pre-flight" check request with OPTIONS is made, to ask the server permission first.
         * The following will respond with "you can do everything".
         * https://stackoverflow.com/questions/29954037/why-is-an-options-request-sent-and-can-i-disable-it
         * If sending text/plain, then this decorator and any CORS checks are unnecessary
         */

        // This has to be set for all requests, because of CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        // this part is for the pre-flight options check
        if(exchange.getRequestMethod().equals("OPTIONS")){
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");

            try (OutputStream outputStream = exchange.getResponseBody()) {
                exchange.sendResponseHeaders(200, 0);
                outputStream.write(new byte[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // forward request to actual handler
        handler.handle(exchange);
    }
}

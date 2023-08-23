package webapi.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class PageProvider implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String requestMethod = exchange.getRequestMethod();
        assertIsGetRequest(requestMethod);

        String resourceName = getResourceName(exchange);
        String content = loadResourceAsString(resourceName);
        sendHtmlPageToClient(exchange, content);

    }

    private String getResourceName(HttpExchange exchange) {
        return exchange.getRequestURI().toString().replace("/pages/", "");
    }

    private void assertIsGetRequest(String requestMethod) {
        if (!"GET".equals(requestMethod)) {
            throw new RuntimeException("Must use GET to interact with pages and page resources");
            // TODO write error back to client
        }
    }

    private String loadResourceAsString(String resourcePath) {
        URL resourceURL = this.getClass().getResource(resourcePath);
        try {
            String pageContent = Files.readString(Path.of(resourceURL.toURI()));
            return pageContent;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO write error back to client
        }
    }

    private void sendHtmlPageToClient(HttpExchange exchange, String content) {
        try {
            OutputStream outputStream = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, content.length());
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO write error back to client
        }
    }

}

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
        // request URI could be: pages/index.html. Assuming it is GET
        String resourceName = exchange.getRequestURI().toString().replace("/pages/", "");
        String content = readResourceFileToString(resourceName);
        sendHtmlPageToClient(exchange, content);

    }


    // this method reads a resource, e.g. an html file, or css, or js. Or image...? Maybe not.
    private String readResourceFileToString(String resourcePath) {
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

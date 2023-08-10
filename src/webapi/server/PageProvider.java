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
        if (!"GET".equals(requestMethod)) {
            throw new RuntimeException("Problem here");
        }

        String resourceName = exchange.getRequestURI().toString().replace("/pages/", "");

        loadAndReturnResource(resourceName, exchange);
    }

    private void loadAndReturnResource(String resourcePath, HttpExchange exchange) {
        URL resourceURL = this.getClass().getResource(resourcePath);
        try {
            OutputStream outputStream = exchange.getResponseBody();

            String indexContent = loadHtmlPage(resourceURL);
            sendHtmlPageToClient(exchange, outputStream, indexContent);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendHtmlPageToClient(HttpExchange exchange, OutputStream outputStream, String indexContent) throws IOException {
        exchange.sendResponseHeaders(200, indexContent.length());
        outputStream.write(indexContent.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String loadHtmlPage(URL resource) throws IOException, URISyntaxException {
        return Files.readString(Path.of(resource.toURI()));
    }
}

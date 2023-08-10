package webapi.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class PageProvider implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        if (!"GET".equals(requestMethod)) {
            throw new RuntimeException("Problem here");
        }
        String resourceName = exchange.getRequestURI().toString().replace("/pages/", "");
        ResponseUtil.loadAndReturnResource(resourceName, exchange);

//        switch (resourceName) {
//            case "index.html":
//                handleGet(exchange, resourceName);
//            default:
//                throw new RuntimeException("No matching route");
//        }
    }

    private void handleGet(HttpExchange exchange, String resourceName) {
        ResponseUtil.loadAndReturnResource(resourceName, exchange);
//        OutputStream outputStream = exchange.getResponseBody();
//        try {
//            URL resource = this.getClass().getResource("index.html");
//
//            String indexContent = Files.readString(Path.of(resource.toURI()));
//
//            exchange.sendResponseHeaders(200, indexContent.length());
//            outputStream.write(indexContent.getBytes());
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }

    }
}

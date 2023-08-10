package webapi.server;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResponseUtil {

    public static void loadAndReturnResource(String resourcePath, HttpExchange exchange) {
//        ResponseUtil.setHttpExchangeResponseHeaders(exchange);
        OutputStream outputStream = exchange.getResponseBody();
        try {
            URL resource = ResponseUtil.class.getResource(resourcePath);

            String indexContent = Files.readString(Path.of(resource.toURI()));

            exchange.sendResponseHeaders(200, indexContent.length());
            outputStream.write(indexContent.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void setHttpExchangeResponseHeaders(HttpExchange httpExchange) {
//        // Set common response headers
//        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
//        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
//        httpExchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
//        httpExchange.getResponseHeaders().add("Access-Control-Allow-Credentials-Header", "*");
//    }
}

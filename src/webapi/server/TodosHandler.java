package webapi.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import webapi.data.DataContext;
import webapi.data.requests.CreateTodoRequest;
import webapi.data.entities.Todo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TodosHandler implements HttpHandler {

    private final DataContext dataContext;

    public TodosHandler(DataContext context) {
        this.dataContext = context;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String requestMethod = exchange.getRequestMethod();
        if ("GET".equals(requestMethod)) {
            handleGET(exchange);
        } else if ("POST".equals(requestMethod)) {
            handlePOST(exchange);
        }
    }

    private void handleGET(HttpExchange exchange) {
        List<Todo> todos = dataContext.getTodos();

        String todosAsJson = new Gson().toJson(todos);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(200, todosAsJson.length());
            outputStream.write(todosAsJson.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handlePOST(HttpExchange exchange) {
        try (OutputStream outputStream = exchange.getResponseBody()) {
            String todoAsJson = new String(exchange.getRequestBody().readAllBytes());
            CreateTodoRequest request = new Gson().fromJson(todoAsJson, CreateTodoRequest.class);

            Todo newTodo = new Todo(request.getTitle(), request.getDescription(), request.getAssigneeName());

            dataContext.addTodo(newTodo);

            String response = "Created!";
            exchange.sendResponseHeaders(201, response.length());
            outputStream.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
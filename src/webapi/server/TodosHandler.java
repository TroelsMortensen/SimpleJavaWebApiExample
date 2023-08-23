package webapi.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import webapi.data.DataContext;
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
        switch (requestMethod) {
            case "GET" -> handleGET(exchange);
            case "POST" -> handlePOST(exchange);
            default -> throw new RuntimeException("Error here"); // Todo write error back to client
        }

    }

    private void handleGET(HttpExchange exchange) {
        // There might be different get methods, or filter arguments. Fix later
        List<Todo> todos = dataContext.getTodos();
        String todosAsXml = serializeToXML(todos);
        returnDataToClient(exchange, todosAsXml);
    }

    private void handlePOST(HttpExchange exchange) {
        try {
            String[] argsInArray = parseArgsFromRequestBody(exchange);

            Todo newTodo = createTodoItemFromArgs(argsInArray);

            dataContext.addTodo(newTodo);

            sendResponseBack(exchange, "Created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponseBack(HttpExchange exchange, String response) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(200, response.length());
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String[] parseArgsFromRequestBody(HttpExchange exchange) throws IOException {
        // request body in format: title=hello world&description=this is xyz&assignee=7
        String argsAsString = new String(exchange.getRequestBody().readAllBytes());
        String[] argsInArray = argsAsString.split("&");
        return argsInArray;
    }

    private Todo createTodoItemFromArgs(String[] argsInArray) {
        String title = argsInArray[0].split("=")[1];
        String description = argsInArray[1].split("=")[1];
        String assignee = argsInArray[2].split("=")[1];
        Todo newTodo = new Todo(title, description, assignee);
        return newTodo;
    }


    private void returnDataToClient(HttpExchange exchange, String todosAsXml) {
        try {
            OutputStream outputStream = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, todosAsXml.length());
            outputStream.write(todosAsXml.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serializeToXML(List<Todo> todos) {
        XStream xstream = new XStream(new DomDriver());

        // renaming "<webapi.data.entities.Todo>" to "<todo>"
        xstream.alias("todo", Todo.class);
        String xml = xstream.toXML(todos);
        return xml;
    }


}

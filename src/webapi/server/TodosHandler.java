package webapi.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import webapi.data.DataContext;
import webapi.data.entities.Todo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TodosHandler implements HttpHandler {

    private DataContext context;

    public TodosHandler(DataContext context) {
        this.context = context;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String requestMethod = exchange.getRequestMethod();
        switch (requestMethod){
            case "GET" : handleGET(exchange); break;
            case "POST" : handlePOST(exchange); break;
            default: throw new RuntimeException("Error here");
        }

    }

    private void handlePOST(HttpExchange exchange) {
        try {
            String argsAsString = new String(exchange.getRequestBody().readAllBytes());
            String[] argsInArray = argsAsString.split("&");

            String title = argsInArray[0].split("=")[1];
            String description = argsInArray[1].split("=")[1];
            String assignee = argsInArray[2].split("=")[1];
            Todo newTodo = new Todo(title, description, assignee);
            context.addTodo(newTodo);

            System.out.println("Todo submitted");

            try {
                OutputStream outputStream = exchange.getResponseBody();
                String response = "Nice";
                exchange.sendResponseHeaders(200, response.length());
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int stopher = 0;
    }

    private void handleGET(HttpExchange exchange) {
        // todo match actions and path
        List<Todo> todos = getDataFromContext();
        String todosAsXml = serializeToXML(todos);
//        test(todosAsXml);
        returnDataToClient(exchange, todosAsXml);
    }

    private void test(String todosAsXml) {
        XMLDecoder d = new XMLDecoder(new ByteArrayInputStream(todosAsXml.getBytes()));
        List<Todo> obj = (List<Todo>) d.readObject();
        d.close();
        int stopher = 0;
    }

    private void returnDataToClient(HttpExchange exchange, String todosAsXml) {
        OutputStream outputStream = exchange.getResponseBody();
        try {
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

        // rename "webapi.data.entities.Todo" to "todo"
        xstream.alias("todo", Todo.class);
        String xml = xstream.toXML(todos);
        return xml;
    }

    private List<Todo> getDataFromContext() {
        return context.getTodos();
    }
}

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

        // rename webapi.data.entities.Todo to todo
        xstream.alias("todo", Todo.class);
        String xml = xstream.toXML(todos);
        return xml;
    }

    private List<Todo> getDataFromContext() {
        return context.getTodos();
    }
}

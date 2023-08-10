package webapi.data;

import webapi.data.entities.Todo;

import java.util.ArrayList;
import java.util.List;

public class DataContext {

    public DataContext() {
        generateExampleData();
    }

    private void generateExampleData() {
        Todo t1 = new Todo("Mow lawn", "Must be done today", "Troels");
        Todo t2 = new Todo("Do dishes", "It's a huge mess in the kitchen", "Anne");
        Todo t3 = new Todo("Walk dog", "He gotta get sniffin' stuff", "Troels");
        Todo t4 = new Todo("Brush teeth", "Pearly whites", "Troels");
        t1.setId(1);
        t2.setId(2);
        t2.setCompleted(true);
        t3.setId(3);
        t4.setId(4);
        todos.add(t1);
        todos.add(t2);
        todos.add(t3);
        todos.add(t4);
    }

    private final List<Todo> todos = new ArrayList<>();

    public List<Todo> getTodos() {
        return todos;
    }

    public void addTodo(Todo newTodo) {
        int newId = calcNewId();
        newTodo.setId(newId);
        todos.add(newTodo);
    }

    private int calcNewId() {
        int newId = 0;
        for (Todo todo : todos) {
            if (todo.getId() > newId) {
                newId = todo.getId();
            }
        }
        newId++;
        return newId;
    }
}

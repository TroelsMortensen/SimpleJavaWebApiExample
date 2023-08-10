package webapi.data;

import webapi.data.entities.Todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataContext {

    private List<Todo> todos = new ArrayList<>(Arrays.asList(
       new Todo(1, "Mow lawn", "Must be done today", false, "Troels"),
       new Todo(2, "Do dishes", "It's a huge mess in the kitchen", false, "Anne"),
       new Todo(3, "Walk dog", "He gotta get sniffin' stuff", false, "Troels"),
       new Todo(4, "Brush teeth", "Pearly whites", true, "Troels")
    ));
    public List<Todo> getTodos() {
        return todos;
    }
}

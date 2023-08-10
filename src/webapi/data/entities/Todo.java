package webapi.data.entities;

import java.io.Serializable;

public class Todo implements Serializable {
    private int id;
    private String title;
    private String description;
    private boolean isCompleted;
    private String assigneeName;

    public Todo() {
    }

    public Todo(int id, String title, String description, boolean isCompleted, String assigneeName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.assigneeName = assigneeName;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
}

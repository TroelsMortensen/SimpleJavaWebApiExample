package webapi.data.entities;

public class CreateTodoRequest {
    private String title;
    private String description;
    private String assigneeName;

    public CreateTodoRequest(String title, String description, String assigneeName) {
        this.title = title;
        this.description = description;
        this.assigneeName = assigneeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
}

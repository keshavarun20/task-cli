package model;

public abstract class Task {
    private Integer id;
    private String title;
    private boolean completed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }

    public boolean isCompleted() {
        return this.completed;
    }
    public void setTitle(String title) {
        if (title == null || title.isEmpty()) return;
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return id + " | " + title + " | " + completed;
    }
}

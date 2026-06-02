package model;

import enums.Status;

public abstract class Task {
    private Integer id;
    private String title;
    private Status status = Status.TODO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) return;
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return status == Status.DONE;
    }

    public void markDone() {
        this.status = Status.DONE;
    }

    @Override
    public String toString() {
        return id + " | " + title + " | " + status;
    }
}

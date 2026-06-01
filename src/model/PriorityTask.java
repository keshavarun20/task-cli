package model;

import enums.Priority;

// PriorityTask — extends Task with an additional priority field
// Inherits title, completed, getters/setters and toString from Task

public class PriorityTask extends Task{
    private Priority priority;

    public Priority getPriority(){
       return this.priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    // Appends priority to parent toString
    @Override
    public String toString() {
        return super.toString() + " | " + priority;
    }
}

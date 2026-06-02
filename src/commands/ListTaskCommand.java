package commands;

import enums.Status;
import interfaces.Command;
import service.TaskService;

public class ListTaskCommand implements Command {

    private final Status status;
    private final TaskService taskService;

    public ListTaskCommand(Status status, TaskService taskService) {
        this.status = status;
        this.taskService = taskService;
    }


    @Override
    public void execute() {
        taskService.listByStatus(status);
    }
}

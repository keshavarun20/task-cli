package commands;

import interfaces.Command;
import service.TaskService;

public class CompleteTaskCommand implements Command {

    private final TaskService taskService;
    private final Integer id;


    public CompleteTaskCommand(TaskService taskService, Integer id) {
        this.taskService = taskService; // stored as instance variable
        this.id = id;
    }

    @Override
    public void execute() {
        taskService.completeTask(id);
    }
}

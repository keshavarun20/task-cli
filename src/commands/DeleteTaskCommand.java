package commands;

import interfaces.Command;
import service.TaskService;

public class DeleteTaskCommand implements Command {

    private final Integer id;
    private final TaskService taskService;

    public DeleteTaskCommand(Integer id, TaskService taskService) {
        this.id = id;
        this.taskService = taskService;
    }

    @Override
    public void execute() {
        taskService.deleteTask(id);
    }
}

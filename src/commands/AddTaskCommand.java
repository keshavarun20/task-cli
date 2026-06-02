package commands;

import interfaces.Command;
import service.TaskService;

public class AddTaskCommand implements Command {

    private final String title;
    private final TaskService taskService;

    public AddTaskCommand(String title, TaskService taskService) {
        this.title = title;
        this.taskService = taskService;
    }


    @Override
    public void execute() {
        taskService.addTask(title);
    }
}

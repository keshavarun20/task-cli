package commands;

import enums.Priority;
import interfaces.Command;
import service.TaskService;

public class SetPriorityCommand implements Command {

    private final Integer id;
    private final TaskService taskService;
    private final Priority priority;

    public SetPriorityCommand(Integer id, TaskService taskService, Priority priority) {
        this.id = id;
        this.taskService = taskService;
        this.priority = priority;
    }


    @Override
    public void execute() {
        taskService.setPriority(id,priority);
    }
}

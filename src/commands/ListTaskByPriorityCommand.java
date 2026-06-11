package commands;

import enums.Priority;
import interfaces.Command;
import model.Task;
import org.jline.terminal.Terminal;
import service.TaskService;
import utils.TaskPrinter;

import java.util.List;

public class ListTaskByPriorityCommand implements Command {

    private final TaskService taskService;
    private final Priority priority;
    private final Terminal terminal;

    public ListTaskByPriorityCommand(TaskService taskService, Priority priority, Terminal terminal) {
        this.taskService = taskService;
        this.priority = priority;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        List<Task> tasks = taskService.listByPriority(priority);
        TaskPrinter.print(tasks,terminal);
    }
}

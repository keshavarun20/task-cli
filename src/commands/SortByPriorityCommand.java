package commands;

import interfaces.Command;
import model.Task;
import org.jline.terminal.Terminal;
import service.TaskService;
import utils.TaskPrinter;

import java.util.List;

public class SortByPriorityCommand implements Command {

    private final TaskService taskService;
    private final Terminal terminal;

    public SortByPriorityCommand(TaskService taskService, Terminal terminal) {
        this.taskService = taskService;
        this.terminal = terminal;
    }


    @Override
    public void execute() {
        List<Task> tasks = taskService.sortByPriority();
        TaskPrinter.print(tasks,terminal);
    }
}

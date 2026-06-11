package commands;

import interfaces.Command;
import model.PriorityTask;
import model.Task;
import service.TaskService;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import utils.TaskPrinter;

import java.util.List;

public class ListTasksCommand implements Command {

    private final TaskService taskService;
    private final Terminal terminal; // 👈 Inject JLine Terminal

    public ListTasksCommand(TaskService taskService, Terminal terminal) {
        this.taskService = taskService;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        List<Task> tasks = taskService.getAllTasks();
        TaskPrinter.print(tasks, terminal);
    }
}

package commands;

import enums.Status;
import interfaces.Command;
import model.Task;
import org.jline.terminal.Terminal;
import service.TaskService;
import utils.TaskPrinter;

import java.util.List;

public class ListTaskByStatusCommand implements Command {

    private final Status status;
    private final TaskService taskService;
    private final Terminal terminal;

    public ListTaskByStatusCommand(Status status, TaskService taskService, Terminal terminal) {
        this.status = status;
        this.taskService = taskService;
        this.terminal = terminal;
    }


    @Override
    public void execute() {
       List<Task> tasks= taskService.listByStatus(status);
       TaskPrinter.print(tasks, terminal);

    }
}

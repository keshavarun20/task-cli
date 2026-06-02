package commands;

import enums.Priority;
import interfaces.Command;
import service.TaskService;
import model.Task;
import model.PriorityTask;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class SetPriorityCommand implements Command {

    private final Integer id;
    private final TaskService taskService;
    private final Priority priority;
    private final Terminal terminal;

    public SetPriorityCommand(Integer id, TaskService taskService, Priority priority, Terminal terminal) {
        this.id = id;
        this.taskService = taskService;
        this.priority = priority;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        // 1. Grab old priority state for the message (Optional check)
        String oldPriority = "NONE";
        Task task = taskService.getTaskById(id); // Adjust based on your actual service method name
        if (task instanceof PriorityTask pt && pt.getPriority() != null) {
            oldPriority = pt.getPriority().name();
        }

        // 2. Perform the update
        taskService.setPriority(id, priority);

        // 3. Print the transition message beautifully
        String updateMessage = new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN))
                .append("[Update] ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                .append("Task #")
                .append(String.valueOf(id))
                .append(" priority changed from ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW))
                .append(oldPriority)
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                .append(" ➔ ")
                .style(AttributedStyle.DEFAULT.foreground(208).bold()) // 208 is Vibrant Orange
                .append(priority.name())
                .toAnsi();

        terminal.writer().println(updateMessage);
        terminal.flush();
    }
}

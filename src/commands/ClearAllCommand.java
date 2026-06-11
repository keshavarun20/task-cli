package commands;

import interfaces.Command;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import service.TaskService;

public class ClearAllCommand implements Command {

    private final TaskService taskService;
    private final Terminal terminal;

    public ClearAllCommand(TaskService taskService, Terminal terminal) {
        this.taskService = taskService;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        taskService.clearAll();
        terminal.writer().println(new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold())
                .append("⚠ All tasks cleared.")
                .toAnsi());
        terminal.flush();
    }
}

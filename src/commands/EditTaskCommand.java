package commands;

import interfaces.Command;
import model.Task;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import service.TaskService;

public class EditTaskCommand implements Command {

    private final TaskService taskService;
    private final String title;
    private final Integer id;
    private final Terminal terminal;

    public EditTaskCommand(TaskService taskService, String title, Integer id, Terminal terminal) {
        this.taskService = taskService;
        this.title = title;
        this.id = id;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        taskService.editTask(id, title);
        terminal.writer().println(new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold())
                .append("✔ Task #")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                .append(String.valueOf(id))
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold())
                .append(" updated to: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                .append(title)
                .toAnsi());
        terminal.flush();
    }
}

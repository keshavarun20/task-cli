package commands;

import interfaces.Command;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import service.TaskService;

public class AddTaskCommand implements Command {

    private final String title;
    private final TaskService taskService;
    private final Terminal terminal;

    public AddTaskCommand(String title, TaskService taskService, Terminal terminal) {
        this.title = title;
        this.taskService = taskService;
        this.terminal = terminal;
    }


    @Override
    public void execute() {
        taskService.addTask(title);

        String successAlert = new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN))
                .append("✔ Success: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                .append("Added task \"")
                .append(title)
                .append("\"")
                .toAnsi();

        terminal.writer().println(successAlert);
        terminal.flush();
    }
}

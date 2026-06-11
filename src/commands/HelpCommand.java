package commands;

import interfaces.Command;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class HelpCommand implements Command {
    
    private final Terminal terminal;

    public HelpCommand(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        String[][] commands = {
                {"add <title>",                    "Add a new task"},
                {"done <id>",                      "Mark a task as done"},
                {"delete <id>",                    "Delete a task"},
                {"edit <id> <new title>",          "Rename a task"},
                {"priority <id> <LOW|MEDIUM|HIGH>","Set task priority"},
                {"sort",                           "Sort tasks by priority (HIGH first)"},
                {"list",                           "List all tasks"},
                {"list done",                      "List completed tasks"},
                {"list todo",                      "List incomplete tasks"},
                {"list <LOW|MEDIUM|HIGH>",         "Filter tasks by priority"},
                {"clear",                          "Clear all tasks"},
                {"help",                           "Show all commands"},
                {"exit",                           "Exit Jodo"}
        };

        terminal.writer().println(new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold())
                .append("\n⚡ Jodo Commands\n")
                .toAnsi());

        for (String[] cmd : commands) {
            String line = new AttributedStringBuilder()
                    .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold())
                    .append(String.format("  %-35s", cmd[0]))
                    .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                    .append(cmd[1])
                    .toAnsi();
            terminal.writer().println(line);
        }

        terminal.writer().println();
        terminal.flush();
    }
}

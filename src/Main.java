import commands.ExitCommand;
import interfaces.Command;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import parser.CommandParser;
import service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Terminal terminal = TerminalBuilder
                    .builder()
                    .system(true)
                    .build();

            StringsCompleter mainCommands = new StringsCompleter("add", "delete", "complete", "priority", "list", "exit");

            StringsCompleter priorityValues = new StringsCompleter("HIGH", "MEDIUM", "LOW");

            ArgumentCompleter autocompleteTree = new ArgumentCompleter(
                    mainCommands,
                    new StringsCompleter(), // Blank completer for IDs (lets user type any number)
                    priorityValues,
                    NullCompleter.INSTANCE
            );

            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(autocompleteTree)
                    .history(new DefaultHistory())
                    .build();

            AttributedStringBuilder header = new AttributedStringBuilder();
            header.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold());
            header.append("\n⚡ TASK MANAGER CLI ⚡\n");
            header.style(AttributedStyle.DEFAULT.foreground(2));
            header.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
            header.append("Type 'list' to view tasks, 'exit' to quit.\n");

            terminal.writer().println(header.toAnsi());
            terminal.flush();

            TaskService taskService = new TaskService();
            CommandParser commandParser = new CommandParser(taskService,terminal);

            taskService.load();

            boolean running = true;

            while (running) {
                String prompt = new AttributedStringBuilder()
                        .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold())
                        .append("task-cli")
                        .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                        .append(" ❯ ")
                        .toAnsi();

                String rawInput = reader.readLine(prompt);

                if (rawInput == null) continue;
                rawInput = rawInput.trim();

                try{

                    Command command = commandParser.parse(rawInput);
                    command.execute();

                    if (command instanceof ExitCommand) {
                        running = false;
                    }

                } catch (IllegalArgumentException e) {
                    // Handle missing arguments or bad command formats gracefully
                    String warningMessage = new org.jline.utils.AttributedStringBuilder()
                            .style(org.jline.utils.AttributedStyle.DEFAULT.foreground(org.jline.utils.AttributedStyle.RED).bold())
                            .append("⚠ Error: ")
                            .style(org.jline.utils.AttributedStyle.DEFAULT.foreground(org.jline.utils.AttributedStyle.WHITE))
                            .append(e.getMessage()) // This prints "Task title is required."
                            .toAnsi();

                    terminal.writer().println(warningMessage);

                }
                terminal.flush();
            }
        } catch (Exception e) {
            System.err.println("Failed to start JLine: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

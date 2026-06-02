package parser;

import enums.Priority;
import interfaces.Command;
import commands.*;
import org.jline.terminal.Terminal;
import service.TaskService;

public class CommandParser {

    private final TaskService taskService;
    private final Terminal terminal;


    public CommandParser(TaskService taskService, Terminal terminal) {
        this.taskService = taskService;
        this.terminal = terminal;
    }

    public Command parse(String input) {
        String trimmedInput = input.trim();
        String[] parts = trimmedInput.split(" ");
        String commandName = parts[0].toLowerCase();

        switch (commandName) {
            case "add":
                return parseAddCommand(trimmedInput);

            case "done":
                return new CompleteTaskCommand(taskService, parseId(parts));

            case "delete":
                return new DeleteTaskCommand(parseId(parts), taskService);

            case "priority":
                return parsePriorityCommand(parts);

            case "list":
                return new ListTasksCommand(taskService,terminal);

            case "exit":
                return new ExitCommand(terminal);

            case "help":
                return new HelpCommand(terminal);

            default:
                return new AddTaskCommand(trimmedInput,taskService,terminal);
        }
    }

    private Command parseAddCommand(String input) {
        if (input.length() <= 4) {
            throw new IllegalArgumentException("Task title is required.");
        }

        String title = input.substring(4).trim();
        return new AddTaskCommand(title, taskService,terminal);
    }

    private Command parsePriorityCommand(String[] parts) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Usage: priority <id> <LOW|MEDIUM|HIGH>");
        }

        int id = Integer.parseInt(parts[1]);
        Priority priority = Priority.valueOf(parts[2].toUpperCase());

        return new SetPriorityCommand( id,taskService, priority,terminal);
    }

    private int parseId(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Task id is required.");
        }

        return Integer.parseInt(parts[1]);
    }
}

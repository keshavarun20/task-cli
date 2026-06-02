package parser;

import enums.Priority;
import interfaces.Command;
import commands.*;
import service.TaskService;

public class CommandParser {

    private final TaskService taskService;

    public CommandParser(TaskService taskService) {
        this.taskService = taskService;
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
                return new ListTasksCommand(taskService);

            case "exit":
                return new ExitCommand();

            default:
                return new AddTaskCommand(trimmedInput,taskService);
        }
    }

    private Command parseAddCommand(String input) {
        if (input.length() <= 4) {
            throw new IllegalArgumentException("Task title is required.");
        }

        String title = input.substring(4).trim();
        return new AddTaskCommand(title, taskService);
    }

    private Command parsePriorityCommand(String[] parts) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Usage: priority <id> <LOW|MEDIUM|HIGH>");
        }

        int id = Integer.parseInt(parts[1]);
        Priority priority = Priority.valueOf(parts[2].toUpperCase());

        return new SetPriorityCommand( id,taskService, priority);
    }

    private int parseId(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Task id is required.");
        }

        return Integer.parseInt(parts[1]);
    }
}

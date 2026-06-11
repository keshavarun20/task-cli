package parser;

import enums.Priority;
import enums.Status;
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
                if (parts.length > 1) {
                    String sub = parts[1].toUpperCase();
                    switch (sub) {
                        case "DONE":
                        case "TODO":
                            return new ListTaskByStatusCommand(Status.valueOf(sub), taskService, terminal);
                        case "HIGH":
                        case "MEDIUM":
                        case "LOW":
                            return new ListTaskByPriorityCommand(taskService,Priority.valueOf(sub), terminal);
                        default:
                            throw new IllegalArgumentException("Usage: list | list done | list todo | list <LOW|MEDIUM|HIGH>");
                    }
                }
                return new ListTasksCommand(taskService, terminal);

            case "exit":
                return new ExitCommand(terminal);

            case "help":
                return new HelpCommand(terminal);

            case "sort":
                return new SortByPriorityCommand(taskService,terminal);

            case "edit":
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Usage: edit <id> <new title>");
                }
                int editId = Integer.parseInt(parts[1]);
                String newTitle = trimmedInput.substring(trimmedInput.indexOf(parts[2])).trim();
                return new EditTaskCommand(taskService, newTitle, editId, terminal);

            case "clear":
                return new ClearAllCommand(taskService, terminal);

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

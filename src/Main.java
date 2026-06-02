import commands.ExitCommand;
import interfaces.Command;
import parser.CommandParser;
import service.TaskService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Task CLI Started");

        Scanner scanner = new Scanner(System.in);
        TaskService taskService = new TaskService();
        CommandParser commandParser = new CommandParser(taskService);

        taskService.load();

        boolean running = true;

        while (running) {
            System.out.println("\n>");
            String input = scanner.nextLine();

            Command command = commandParser.parse(input);
            command.execute();

            if (command instanceof ExitCommand) {
                running = false;
            }
        }

        scanner.close();
    }
}

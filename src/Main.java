import enums.Priority;
import service.TaskService;
import model.Task;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Task CLI Started");
        Scanner scanner = new Scanner(System.in);
        TaskService taskService= new TaskService();

        // Load persisted tasks from file into memory on startup
        taskService.loadTasks();

        while (true) {
            System.out.println("\nEnter task (or type exit):");
            String input = scanner.nextLine();

            // Exit condition — break out of loop
            if (input.equalsIgnoreCase("exit")) break;

            boolean isDone = input.toLowerCase().startsWith("done ");
            boolean isDelete = input.toLowerCase().startsWith("delete ");
            boolean isPriority=input.toLowerCase().startsWith("priority");

            if (isDone) {
                // Extract title after "done " prefix and mark complete
                String title = input.split(" ", 2)[1];
                taskService.completeTask(title);
            } else if (isDelete) {
                // Extract title after "delete " prefix and remove from list
                String title = input.split(" ", 2)[1];
                taskService.deleteTask(title);
            } else if (isPriority) {
                String[] parts = input.split(" ");

                // Last word is always the number (1=LOW, 2=MEDIUM, 3=HIGH)
                String number = parts[parts.length - 1];

                // Strip "priority" prefix and number suffix to isolate the title
                String title = input.replaceFirst("(?i)priority ", "")
                        .replaceAll(" " + number + "$", "")
                        .trim();

                // Convert number to enum — subtract 1 because values() is 0-indexed
                Priority priority = Priority.values()[Integer.parseInt(number) - 1];

                taskService.setPriority(priority, title);
            } else {
                if (input.trim().isEmpty()) {
                    System.out.println("Title is empty");
                    System.out.println();
                } else {
                    taskService.addTask(input);
                }
            }

            // Show updated task list after every action
            printTasks(taskService.getAllTasks());
        }

        // Final task list on exit
        System.out.println("\nAll tasks:");
        printTasks(taskService.getAllTasks());
    }

    // Prints all tasks — kept in Main since it's a UI concern, not service logic
    private static void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

}

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
        taskService.load();

        while (true) {
            System.out.println("\nEnter task (or type exit):");
            String input = scanner.nextLine();

            // Exit condition — break out of loop
            if (input.equalsIgnoreCase("exit")) break;

            boolean handled = false;
            boolean isDone = input.toLowerCase().startsWith("done ");
            boolean isDelete = input.toLowerCase().startsWith("delete ");
            boolean isPriority=input.toLowerCase().startsWith("priority ");
            boolean isList=input.toLowerCase().startsWith("list ");

            if (isDone) {
                // Extract title after "done " prefix and mark complete
                Integer id =Integer.parseInt(input.split(" ", 2)[1]);
                taskService.completeTask(id);
            } else if (isDelete) {
                // Extract title after "delete " prefix and remove from list
                Integer id =Integer.parseInt(input.split(" ", 2)[1]);
                taskService.deleteTask(id);
            } else if (isPriority) {
                String[] parts = input.split(" ");
                Integer id = Integer.parseInt(parts[1]);
                Priority priority = Priority.values()[Integer.parseInt(parts[parts.length - 1]) - 1];
                taskService.setPriority(priority, id);
            } else if (isList) {
                String[] parts = input.split(" ");
                boolean status = parts[1].equalsIgnoreCase("done");
                List<Task> task = taskService.listByStatus(status);
                printTasks(task);
                handled = true;
            } else {
                if (input.trim().isEmpty()) {
                    System.out.println("Title is empty");
                    System.out.println();
                } else {
                    taskService.addTask(input);
                }
            }

            // Only print all tasks if not already handled
            if (!handled) {
                printTasks(taskService.getAllTasks());
            }
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

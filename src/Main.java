import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Task CLI Started");
        Scanner scanner = new Scanner(System.in);
        TaskService taskService = new TaskService();

        // Load persisted tasks from file into memory on startup
        taskService.loadTasks();

        while (true) {
            System.out.println("\nEnter task (or type exit):");
            String input = scanner.nextLine();

            // Exit condition — break out of loop
            if (input.equalsIgnoreCase("exit")) break;

            boolean isDone = input.toLowerCase().startsWith("done ");
            boolean isDelete = input.toLowerCase().startsWith("delete ");

            if (isDone) {
                // Extract title after "done " prefix and mark complete
                String title = input.split(" ", 2)[1];
                taskService.completeTask(title);
            } else if (isDelete) {
                // Extract title after "delete " prefix and remove from list
                String title = input.split(" ", 2)[1];
                taskService.deleteTask(title);
            } else {
                // No prefix — treat input as a new task title
                taskService.addTask(input);
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

// TODO: add priority field to Task (low, medium, high)
// TODO: implement inheritance — PriorityTask extends Task
// TODO: add listByStatus() to TaskService — filter completed/incomplete
// TODO: swap txt file persistence for SQLite
// TODO: add JLine for proper terminal experience
// TODO: add Maven/Gradle for dependency management
}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    // In-memory list of tasks — single source of truth at runtime
    List<Task> tasks = new ArrayList<>();

    // Convenience overload — completed defaults to false
    public void addTask(String title) {
        addTask(title, false);
    }

    // Core addTask — creates Task object, adds to list, persists to file
    public void addTask(String title, boolean completed) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(completed);
        tasks.add(task);
        saveTask();
    }

    // Finds task by title and marks it complete, then persists
    public void completeTask(String title) {
        for (Task t : tasks) {
            if (t.getTitle().equals(title)) {
                t.setCompleted(true);
            }
        }
        saveTask();
    }

    // Finds task by title, stores reference, removes after loop to avoid ConcurrentModificationException
    public void deleteTask(String title) {
        Task toDelete = null;
        for (Task t : tasks) {
            if (t.getTitle().equals(title)) {
                toDelete = t;
            }
        }
        if (toDelete != null) tasks.remove(toDelete);
        saveTask();
    }

    // Returns the full task list — Main handles printing
    public List<Task> getAllTasks() {
        return tasks;
    }

    // Overwrites tasks.txt with current state of tasks list
    // FileWriter outside the loop — file opened once, all tasks written, then closed
    public void saveTask() {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task t : tasks) {
                bWriter.write(t.getTitle() + " | " + t.isCompleted() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads tasks.txt on startup and rebuilds the in-memory tasks list
    // Each line format: "title | completed" — split on " | " to extract fields
    public void loadTasks() {
        try {
            List<String> lines = Files.readAllLines(Path.of("tasks.txt"));
            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                Task task = new Task();
                task.setTitle(parts[0]);
                task.setCompleted(Boolean.parseBoolean(parts[1]));
                tasks.add(task);
            }
        } catch (IOException e) {
            // File doesn't exist yet — first run, nothing to load
            System.out.println("No saved tasks found, starting fresh.");
        }
    }
}

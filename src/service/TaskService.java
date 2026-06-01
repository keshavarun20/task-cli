package service;

import enums.Priority;
import interfaces.Persistable;
import model.PriorityTask;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskService implements Persistable {

    private Integer nextId=1;
    // In-memory list of tasks — single source of truth at runtime
    List<Task> tasks = new ArrayList<>();
    // Convenience overload — completed defaults to false
    public void addTask(String title) {
        addTask(title, false, Priority.LOW);
    }

    // Core addTask — creates Task object, adds to list, persists to file
    public void addTask(String title, boolean completed, Priority priority) {
        PriorityTask task = new PriorityTask();
        task.setId(nextId++);
        task.setTitle(title);
        task.setCompleted(completed);
        task.setPriority(priority);
        tasks.add(task);
        save();
    }

    // Finds a task by title, checks if it's a PriorityTask, then updates its priority
    // Uses instanceof pattern matching to cast safely in one line
    public void setPriority(Priority priority, Integer id){
        for (Task t : tasks) {
            // Only PriorityTask has a priority field — skip plain Tasks
            if (t.getId().equals(id) && t instanceof PriorityTask pt) {
                pt.setPriority(priority);
            }
        }

        save();
    }

    // Finds task by title and marks it complete, then persists
    public void completeTask(Integer id) {
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                t.setCompleted(true);
            }
        }
        save();
    }

    // Finds task by title, stores reference, removes after loop to avoid ConcurrentModificationException
    public void deleteTask(Integer id) {
        Task toDelete = null;
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                toDelete = t;
            }
        }
        if (toDelete != null) tasks.remove(toDelete);
        save();
    }

    // Returns the full task list — Main handles printing
    public List<Task> getAllTasks() {
        return tasks;
    }

    // Overwrites tasks.txt with current state of tasks list
    // FileWriter outside the loop — file opened once, all tasks written, then closed
    @Override
    public void save() {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task t : tasks) {
                if (t instanceof PriorityTask pt) {
                    bWriter.write(t.getId() + " | " + t.getTitle() + " | " + t.isCompleted() + " | " + pt.getPriority() + "\n");
                } else {
                    bWriter.write(t.getId() + " | " + t.getTitle() + " | " + t.isCompleted() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads tasks.txt on startup and rebuilds the in-memory tasks list
    // Each line format: "title | completed" — split on " | " to extract fields
    @Override
    public void load() {
        try {
            List<String> lines = Files.readAllLines(Path.of("tasks.txt"));
            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                PriorityTask task = new PriorityTask();
                task.setId(Integer.parseInt(parts[0]));
                task.setTitle(parts[1]);
                task.setCompleted(Boolean.parseBoolean(parts[2]));
                if (parts.length == 4) {
                    task.setPriority(Priority.valueOf(parts[3]));
                } else {
                    task.setPriority(Priority.LOW); // default
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            // File doesn't exist yet — first run, nothing to load
            System.out.println("No saved tasks found, starting fresh.");
        }

        nextId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0)+1;
    }

    public List<Task> listByStatus(boolean status) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isCompleted() == status) {
                result.add(t);
            }
        }
        return result;
    }
}

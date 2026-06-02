package service;

import enums.Priority;
import enums.Status;
import interfaces.Persistable;
import model.PriorityTask;
import model.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskService implements Persistable {

    private static final Path TASKS_FILE = Path.of("tasks.txt");

    private int nextId = 1;
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(String title) {
        addTask(title,Status.TODO, Priority.LOW);
    }

    public void addTask(String title, Status status, Priority priority) {
        PriorityTask task = new PriorityTask();

        task.setId(nextId++);
        task.setTitle(title);
        task.setStatus(status);
        task.setPriority(priority);

        tasks.add(task);
        save();
    }

    public void setPriority(int id, Priority priority) {
        for (Task task : tasks) {
            if (task.getId() == id && task instanceof PriorityTask priorityTask) {
                priorityTask.setPriority(priority);
                save();
                return;
            }
        }

    }

    public void completeTask(int id) {
        tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .map(task -> {
                    task.markDone();
                    save();
                    return true;
                });
    }

    public void deleteTask(int id) {
        boolean removed = tasks.removeIf(task -> task.getId() == id);

        if (removed) {
            save();
        }

    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> listByStatus(Status status) {
        List<Task> result = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getStatus() == status) {
                result.add(task);
            }
        }

        return result;
    }

    @Override
    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(TASKS_FILE)) {
            for (Task task : tasks) {
                writer.write(formatTask(task));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save tasks: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        tasks.clear();

        if (!Files.exists(TASKS_FILE)) {
            System.out.println("No saved tasks found, starting fresh.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(TASKS_FILE);

            for (String line : lines) {
                Task task = parseTask(line);

                if (task != null) {
                    tasks.add(task);
                }
            }

            updateNextId();
        } catch (IOException e) {
            System.out.println("Failed to load tasks: " + e.getMessage());
        }
    }

    private String formatTask(Task task) {
        if (task instanceof PriorityTask priorityTask) {
            return task.getId()
                    + " | " + task.getTitle()
                    + " | " + task.getStatus()
                    + " | " + priorityTask.getPriority();
        }

        return task.getId()
                + " | " + task.getTitle()
                + " | " + task.getStatus();
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            return null;
        }

        PriorityTask task = new PriorityTask();

        task.setId(Integer.parseInt(parts[0]));
        task.setTitle(parts[1]);
        task.setStatus(Status.valueOf(parts[2].toUpperCase()));

        if (parts.length >= 4) {
            task.setPriority(Priority.valueOf(parts[3]));
        } else {
            task.setPriority(Priority.LOW);
        }

        return task;
    }

    private void updateNextId() {
        nextId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }
}

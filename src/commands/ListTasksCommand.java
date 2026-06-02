package commands;

import interfaces.Command;
import model.PriorityTask;
import model.Task;
import service.TaskService;

import java.util.List;

public class ListTasksCommand implements Command {

    private final TaskService taskService;

    public ListTasksCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void execute() {
        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        for (Task task : tasks) {
            String status = task.isCompleted() ? "DONE" : "TODO";

            if (task instanceof PriorityTask priorityTask) {
                System.out.println(
                        task.getId()
                                + ". [" + status + "] "
                                + task.getTitle()
                                + " | Priority: "
                                + priorityTask.getPriority()
                );
            } else {
                System.out.println(
                        task.getId()
                                + ". [" + status + "] "
                                + task.getTitle()
                );
            }
        }
    }
}

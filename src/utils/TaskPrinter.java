package utils;

import model.PriorityTask;
import model.Task;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.List;

public class TaskPrinter {

    public static void print(List<Task> tasks, Terminal terminal) {
        if (tasks.isEmpty()) {
            terminal.writer().println(new AttributedStringBuilder()
                    .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW))
                    .append("No tasks found.")
                    .toAnsi());
            terminal.flush();
            return;
        }

        // Beautiful colored header
        terminal.writer().println(new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold())
                .append("\n--- Your Task List ---")
                .toAnsi());

        for (Task task : tasks) {
            AttributedStringBuilder lineBuilder = new AttributedStringBuilder();

            // 1. Appending ID (Faint/Gray)
            lineBuilder.style(AttributedStyle.DEFAULT.faint())
                    .append(task.getId() + ". ");

            // 2. Appending Status Flag with Conditional Colors (Green for DONE, Red for TODO)
            if (task.isCompleted()) {
                lineBuilder.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold())
                        .append("[DONE] ");
            } else {
                lineBuilder.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                        .append("[TODO] ");
            }

            // 3. Appending Title (White/Default)
            lineBuilder.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                    .append(task.getTitle());

// 4. Appending Priority with dynamic colors based on its value
            if (task instanceof PriorityTask priorityTask) {
                lineBuilder.style(AttributedStyle.DEFAULT.faint())
                        .append(" | ")
                        .append("Priority: ");

                // Pick the color based on the enum value
                switch (priorityTask.getPriority()) {
                    case HIGH -> lineBuilder.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold());
                    case MEDIUM -> lineBuilder.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold());
                    case LOW -> lineBuilder.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold()); // Sleek cyan for low
                    default -> lineBuilder.style(AttributedStyle.DEFAULT.faint());
                }

                lineBuilder.append(priorityTask.getPriority().toString());
            }

            // Print the styled line
            terminal.writer().println(lineBuilder.toAnsi());
        }

        // Push everything out to the terminal window instantly
        terminal.flush();
    }

}

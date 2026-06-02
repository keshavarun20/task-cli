package commands;

import interfaces.ExitAwareCommand;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class ExitCommand implements ExitAwareCommand {

    private final Terminal terminal;

    public ExitCommand(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        String goodbye = new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append("👋 Goodbye.")
                .toAnsi();

        terminal.writer().println(goodbye);
        terminal.flush();
    }

    @Override
    public boolean shouldExit() {
        return true;
    }
}

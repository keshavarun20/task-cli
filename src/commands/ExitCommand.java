package commands;

import interfaces.ExitAwareCommand;

public class ExitCommand implements ExitAwareCommand {

    @Override
    public void execute() {
        System.out.println("Goodbye.");
    }

    @Override
    public boolean shouldExit() {
        return true;
    }
}

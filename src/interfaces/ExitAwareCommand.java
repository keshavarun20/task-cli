package interfaces;

public interface ExitAwareCommand extends Command {
    boolean shouldExit();
}

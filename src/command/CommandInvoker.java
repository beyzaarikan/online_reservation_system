package command;
import java.util.*;

public class CommandInvoker {
    private Stack<Command> history = new Stack<>();
    
    public void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }
    
    public void undoLastCommand() { // Undo the last executed command
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        } else {
            System.out.println("No commands to undo");
        }
    }
    public void clearHistory() { 
        history.clear();
    }
}
package serverjsh.Commands;

import serverjsh.Errors.MyExceptionBadCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private Map<String, ICommand> commandMap = new HashMap<>();

    public CommandManager() {
        commandMap.put("info", new InfoCommand());
        commandMap.put("tv", new TvCommand());
        commandMap.put("help", new HelpCommand());
    }

    public String PerformAction(CommandPackage cp) throws Exception {

        String command = cp.getCommand();
        if (commandMap.containsKey(command)) {
            ICommand cmd = commandMap.get(command);
            return cmd.Execute(cp);

        } else
            throw new MyExceptionBadCommand("ERROR: Invalid command: " + cp.getCommand(), 0);
    }


}

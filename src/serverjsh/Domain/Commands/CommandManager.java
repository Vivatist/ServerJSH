package serverjsh.Domain.Commands;

import serverjsh.Domain.Exceptions.MyExceptionBadCommand;
import serverjsh.Network.NetworkMessage;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private Map<String, ICommand> commandMap = new HashMap<>();


    public CommandManager() {
        commandMap.put("info", new InfoCommand());
        commandMap.put("sql", new SqlCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("test", new TestCommand());
        commandMap.put("echo", new EchoCommand());
        commandMap.put("settings", new SettingsCommand());
    }


    public NetworkMessage PerformAction(NetworkMessage nm) throws Exception {

        String command = nm.getCommand();
        if (commandMap.containsKey(command)) {
            ICommand cmd = commandMap.get(command);
            return cmd.Execute(nm);

        } else
            throw new MyExceptionBadCommand("ERROR: Invalid command: " + nm.getCommand(), 0);
    }


}

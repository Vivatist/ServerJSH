package serverjsh.Commands;

import serverjsh.Network.NetworkMessage;

public class TestCommand implements ICommand {

    @Override
    public String Execute(NetworkMessage nm) {

        String testText = "test done";

        return testText;

    }
}

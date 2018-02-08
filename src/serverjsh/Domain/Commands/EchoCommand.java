package serverjsh.Domain.Commands;

import serverjsh.Network.NetworkMessage;


public class EchoCommand implements ICommand {

    @Override
    public NetworkMessage Execute(NetworkMessage nm) {
        return nm;
    }
}

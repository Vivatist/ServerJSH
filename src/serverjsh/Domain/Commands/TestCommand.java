package serverjsh.Domain.Commands;


import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

public class TestCommand implements ICommand {

    @Override
    public NetworkMessage Execute(NetworkMessage nm) throws MyExceptionOfNetworkMessage {

        String text = "test done";

        String parameter = nm.getParameter();
        if (parameter != null) {
            switch (parameter) {
                case "arg1": {
                    text += " : test -arg1 done";
                    break;
                }
                case "arg2": {
                    text += " : test -arg2 done";
                    break;
                }
                default: {
                    text += " : test with bad argument done";
                }
            }
        }
        nm.setText(text);
        return nm;

    }
}

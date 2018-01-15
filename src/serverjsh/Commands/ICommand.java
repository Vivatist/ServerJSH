package serverjsh.Commands;


import serverjsh.Errors.MyExceptionBadCommand;
import serverjsh.Errors.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

import java.io.IOException;
import java.text.ParseException;

public interface ICommand {

     String Execute (NetworkMessage nm) throws MyExceptionBadCommand, MyExceptionOfNetworkMessage, IOException, InterruptedException, ParseException;
}

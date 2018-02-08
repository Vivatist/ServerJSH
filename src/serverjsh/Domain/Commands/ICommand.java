package serverjsh.Domain.Commands;


import serverjsh.Domain.Exceptions.MyExceptionBadCommand;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface ICommand {

     NetworkMessage Execute (NetworkMessage nm) throws MyExceptionBadCommand, MyExceptionOfNetworkMessage, IOException, InterruptedException, ParseException, SQLException;
}

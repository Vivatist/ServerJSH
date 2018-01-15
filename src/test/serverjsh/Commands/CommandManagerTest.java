package serverjsh.Commands;

import org.junit.Test;
import serverjsh.Errors.MyExceptionBadCommand;
import serverjsh.Errors.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

import static org.junit.Assert.*;

public class CommandManagerTest {

    @Test
    public void performAction0() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "TeSt", true);
        assertEquals(new CommandManager().PerformAction(nm),"test done");
    }

    @Test(expected = MyExceptionBadCommand.class)
    public void performAction1() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "TEST BAD COMMAND", false);
        new CommandManager().PerformAction(nm);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void performAction2() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "", false);
        new CommandManager().PerformAction(nm);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void performAction3() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", null, false);
        new CommandManager().PerformAction(nm);
    }
}
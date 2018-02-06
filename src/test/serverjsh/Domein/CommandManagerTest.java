package serverjsh.Domein;

import org.junit.Assert;
import org.junit.Test;
import serverjsh.Domein.Commands.CommandManager;
import serverjsh.Domein.Exceptions.MyExceptionBadCommand;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

import static org.junit.Assert.*;

public class CommandManagerTest {

    @Test
    public void performAction0() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "TeSt", true);
        Assert.assertEquals(new CommandManager().PerformAction(nm).getText(),"test done");
    }

    @Test
    public void performAction01() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "TeSt -arg1", true);
        assertEquals(new CommandManager().PerformAction(nm).getText(),"test done : test -arg1 done");
    }

    @Test
    public void performAction02() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "TeSt -arg2", true);
        assertEquals(new CommandManager().PerformAction(nm).getText(),"test done : test -arg2 done");
    }

    @Test
    public void performAction03() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "TeSt -arg1 -arg2", true);
        assertEquals(new CommandManager().PerformAction(nm).getText(),"test done : test -arg1 done");
    }

    @Test
    public void performAction04() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "TeSt -badArgument", true);
        assertEquals(new CommandManager().PerformAction(nm).getText(),"test done : test with bad argument done");
    }

    @Test(expected = MyExceptionBadCommand.class)
    public void performAction1() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "-test bad command", false);
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

    @Test(expected = MyExceptionBadCommand.class)
    public void performAction4() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "()", false);
        new CommandManager().PerformAction(nm);
    }

    @Test(expected = MyExceptionBadCommand.class)
    public void performAction5() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "тест", false);
        new CommandManager().PerformAction(nm);
    }

    @Test(expected = MyExceptionBadCommand.class)
    public void performAction6() throws Exception {
        NetworkMessage nm = new NetworkMessage("Login", "Password", "1test", false);
        new CommandManager().PerformAction(nm);
    }
}
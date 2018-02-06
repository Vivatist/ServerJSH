package serverjsh.Domein;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import serverjsh.Domein.Commands.CommandManager;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class CommandManagerMultiparameters {

    private NetworkMessage valueA;
    private String expected;


    public CommandManagerMultiparameters(NetworkMessage valueA, String expected) {
        this.valueA = valueA;
        this.expected = expected;
    }


    @Parameterized.Parameters(name = "{index}:performAction {0}={1}")
    public static Iterable<Object[]> dataForTest() throws MyExceptionOfNetworkMessage {
        return Arrays.asList(new Object[][]{
                {new NetworkMessage("Login", "Password", "test", false), "test done"},
                {new NetworkMessage("Login", "Password", "TEST", true), "test done"}
        });
    }


    @Test
    public void performAction() throws Exception {
       Assert.assertEquals(expected,new CommandManager().PerformAction(valueA).getText());

    }

    @Test
    public void performAction2() {
    }
}
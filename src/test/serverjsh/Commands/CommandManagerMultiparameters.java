package serverjsh.Commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import serverjsh.Errors.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CommandManagerMultiparameters {

    @Before
    public void setUp() throws Exception {
        cm = new CommandManager();
    }

    @After
    public void tearDown() throws Exception {
        cm = null;
    }

    private CommandManager cm;

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
       assertEquals(expected,new CommandManager().PerformAction(valueA));

    }

    @Test
    public void performAction2() {
    }
}
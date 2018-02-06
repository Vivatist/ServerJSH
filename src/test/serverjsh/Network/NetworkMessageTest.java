package serverjsh.Network;

import org.junit.Test;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;

import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NetworkMessageTest {

    @Test
    public void NetworkMessage0() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", "Password", " TeSt ", true);
        assertEquals(nm1.getText(), "TeSt");
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage1() throws MyExceptionOfNetworkMessage {
        new NetworkMessage("Login", "Password", "", true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage2() throws MyExceptionOfNetworkMessage {
        new NetworkMessage("Login", "Password", " ", true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage3() throws MyExceptionOfNetworkMessage {
        new NetworkMessage("Login", "Password", null, true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage4() throws MyExceptionOfNetworkMessage {
        new NetworkMessage(" ", "Password", "Test", true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage5() throws MyExceptionOfNetworkMessage {
        new NetworkMessage("Login", " ", "Test", true);
    }

//    @Test(expected = MyExceptionOfNetworkMessage.class)
//    public void NetworkMessage6() throws MyExceptionOfNetworkMessage {
//        new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'','error':true}");
//    }
//
//    @Test(expected = MyExceptionOfNetworkMessage.class)
//    public void NetworkMessage7() throws MyExceptionOfNetworkMessage {
//        new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':' ','error':true}");
//    }

    @Test
    public void toJsonAndFromJson() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", "Password", "TeSt", true);
        String strJson = nm1.toJson();
        NetworkMessage nm2 = new NetworkMessage(strJson);
        assertEquals(nm1.getId(), nm2.getId());
        assertEquals(nm1.getCommand(), nm2.getCommand());
        assertEquals(nm1.getLogin(), nm2.getLogin());
        assertEquals(nm1.getPassword(), nm2.getPassword());
        assertEquals(nm1.getText(), nm2.getText());
        assertEquals(nm1.getArguments(), nm2.getArguments());
    }

    @Test
    public void getCommand() throws MyExceptionOfNetworkMessage {
        Iterable<Object[]> params = Arrays.asList(new Object[][]{
                {new NetworkMessage("Login", "Password", "TEST", true), "test"},
                {new NetworkMessage("Login", "Password", " TEST", true), "test"},
                {new NetworkMessage("Login", "Password", "TEST ", true), "test"},
                {new NetworkMessage("Login", "Password", " TEST -аргумент1 -аргумент2", true), "test"},
                {new NetworkMessage("Login", "Password", "test", true), "test"},
                {new NetworkMessage("Login", "Password", " TEST-test", true), "test"},
                {new NetworkMessage("Login", "Password", " t", true), "t"},
                {new NetworkMessage("Login", "Password", "TEST-", true), "test"},
                {new NetworkMessage("Login", "Password", "TEST       -", true), "test"},
                {new NetworkMessage("Login", "Password", "TEST \n- test", true), "test"},
                {new NetworkMessage("Login", "Password", "test --", true), "test"}
        });

        for (Object[] param : params) {
            //Проверяем с набором параметров
            assertEquals(((NetworkMessage) param[0]).getCommand(), param[1]);
        }
    }

    @Test
    public void getParameter() throws MyExceptionOfNetworkMessage {
        Iterable<Object[]> params = Arrays.asList(new Object[][]{
                {new NetworkMessage("Login", "Password", "TEST -arg1 -arg2 -arg3", true), "arg1"},
                {new NetworkMessage("Login", "Password", "TEST -arg1 -3    -{32-43} --100 ----45|3  *4", true), "arg1"},
                {new NetworkMessage("Login", "Password", "TEST -arg1 \n -arg2", true), "arg1"},
                {new NetworkMessage("Login", "Password", " test -execute (10-2) -arg2((subarg1-subarg2)-subarg3)", true), "execute"}

        });

        for (Object[] param : params) {
            //Проверяем с набором параметров
            assertEquals(((NetworkMessage) param[0]).getParameter().toString(), param[1]);
        }

        NetworkMessage nm;
        nm = new NetworkMessage("Login", "Password", " test ", true);
        assertNull(nm.getParameter());

        nm = new NetworkMessage("Login", "Password", " test ( test )", true);
        assertNull(nm.getParameter());
    }

    @Test
    public void getArguments() throws MyExceptionOfNetworkMessage {
        Iterable<Object[]> params = Arrays.asList(new Object[][]{
                {new NetworkMessage("Login", "Password", "test -arg (1,2,3)", true), "1,2,3"},
                {new NetworkMessage("Login", "Password", "test -arg (  1,2,3)", true), "1,2,3"},
                {new NetworkMessage("Login", "Password", "test -arg ((1),(2),(3))", true), "(1),(2),(3)"},
                {new NetworkMessage("Login", "Password", "test ( execute )", true), "execute"}
        });

        for (Object[] param : params) {
            //Проверяем с набором параметров
            assertEquals(((NetworkMessage) param[0]).getArguments().toString(), param[1]);
        }

        NetworkMessage nm = new NetworkMessage("Login", "Password", "test -arg", true);
        assertNull(nm.getArguments());

    }


}
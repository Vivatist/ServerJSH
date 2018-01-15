package serverjsh.Network;

import org.junit.Test;
import serverjsh.Errors.MyExceptionOfNetworkMessage;

import static org.junit.Assert.*;

public class NetworkMessageTest {

    @Test
    public void NetworkMessage0() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", "Password", " TeSt ", true);
        assertEquals(nm1.getText(),"TeSt");
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage1() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", "Password", "", true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage2() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", "Password", " ", true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage3() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", "Password", null, true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage4() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage(" ", "Password", "Test", true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage5() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", " ", "Test", true);
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage6() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'','error':true}");
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void NetworkMessage7() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':' ','error':true}");
    }

    @Test
    public void toJsonAndFromJson() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("Login", "Password", "TeSt", true);
        String strJson = nm1.toJSON();
        System.out.println(strJson);
        NetworkMessage nm2 = new NetworkMessage(strJson);
        assertEquals(nm1.getId(),nm2.getId());
        assertEquals(nm1.getCommand(),nm2.getCommand());
        assertEquals(nm1.getLogin(),nm2.getLogin());
        assertEquals(nm1.getPassword(),nm2.getPassword());
        assertEquals(nm1.getText(),nm2.getText());
        assertEquals(nm1.getArguments(),nm2.getArguments());
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void fromJSON0() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'Test','error':true}");
        nm1.fromJSON("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'','error':true}");
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void fromJSON1() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'Test','error':true}");
        nm1.fromJSON("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':' ','error':true}");
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void fromJSON2() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'Test','error':true}");
        nm1.fromJSON("{'id':' ','login':'Login','password':'Password','text':'Тест','error':true}");
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void fromJSON3() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'Test','error':true}");
        nm1.fromJSON("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':' ','password':'Password','text':'Тест','error':true}");
    }

    @Test(expected = MyExceptionOfNetworkMessage.class)
    public void fromJSON4() throws MyExceptionOfNetworkMessage {
        NetworkMessage nm1 = new NetworkMessage("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':'Login','password':'Password','text':'Test','error':true}");
        nm1.fromJSON("{'id':'53919ae1-1c89-43a7-9ad6-6bd2dd913cf5','login':' Login ','password':' ','text':'Тест','error':true}");
    }

    @Test
    public void getCommand() {

    }

    @Test
    public void getArguments() {
    }


}
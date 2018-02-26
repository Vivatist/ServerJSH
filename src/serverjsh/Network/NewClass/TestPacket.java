package serverjsh.Network.NewClass;

import org.apache.log4j.Logger;

public class TestPacket extends Packet {

    public String Text;

    private static final Logger log = Logger.getLogger(TestPacket.class);

    @Override
    public void Execute() {

        System.out.println("~Test packet execution! Text: " + Text);


    }
}

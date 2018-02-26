package serverjsh.Network.NewClass;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EndSessionPacket extends Packet {

    public String StopText;

    public List<String> al = new ArrayList<>();


    private static final Logger log = Logger.getLogger(EndSessionPacket.class);

    @Override
    public void Execute() {

        System.out.println("~STOP packet execution! Text: " + StopText);

        for(String str: al){
            System.out.println(str);
        }

        System.out.println("end execution Stop packet");


    }
}

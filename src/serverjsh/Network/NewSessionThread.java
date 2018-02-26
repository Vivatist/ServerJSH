package serverjsh.Network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import serverjsh.Network.NewClass.EndSessionPacket;
import serverjsh.Network.NewClass.Packet;

import java.io.*;
import java.net.Socket;

public class NewSessionThread extends Thread {

    private Socket socket;

    BufferedReader in;

    PrintWriter out;

    private static final Logger log = Logger.getLogger(NewSessionThread.class);

    NewSessionThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                .getOutputStream())), true);
    }

    @Override
    public void run() {

        log.debug("~Thread session start!");


        try {
            while (true) {
                log.debug("~while start");
                String jsonText = in.readLine();
                log.debug("~received JSON: " + jsonText);
                if (jsonText == null) {
                    log.debug("~jsonText is NULL");
                    continue;
                }
                log.debug("~Working");
                Gson gson = new Gson();
                try {
                    Packet packet = gson.fromJson(jsonText, EndSessionPacket.class);
                } catch (JsonSyntaxException e) {
                    log.error("~Ошибка JSON ", e.fillInStackTrace());
                    e.printStackTrace();
                }

                //  packet.Execute();
                out.println(jsonText);
                log.debug("~while end");
            }

        } catch (Exception e) {
            log.error("~error of conversion json to class", e.fillInStackTrace());
            e.printStackTrace();
        } finally {
            log.debug("~end thread");
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverjsh.Network;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SessionLauncher implements Runnable{

    private int mPort;

    private static final Logger log = Logger.getLogger(SessionLauncher.class);

   public SessionLauncher(int port){
        this.mPort = port;
    }
    
    @Override
    public void run() {

        try {

            // привинтить сокет на локалхост
            ServerSocket server = new ServerSocket(mPort);

            log.info("Thread of accepting connect is started");

            // слушаем порт
            while (true) {

                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток и увеличиваем счётчик на единицу
                Socket socket = server.accept();
                try {
                    new SessionThread(socket);
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
                log.info(socket.getInetAddress() + ":" + socket.getPort() + " New connect");
            }

        } catch (Exception e) {
           log.error(e);
        }
    }
    
}

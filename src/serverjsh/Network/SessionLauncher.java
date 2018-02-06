/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverjsh.Network;

import serverjsh.Services.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SessionLauncher implements Runnable{

    private int mPort;

   public SessionLauncher(int port){
        this.mPort = port;
    }
    
    @Override
    public void run() {

        try {

            // привинтить сокет на локалхост
            ServerSocket server = new ServerSocket(mPort);

            Log.out("Thread of accepting connect is started",2);

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
                Log.out(socket.getInetAddress() + ":" + socket.getPort() + " New connect", 1);
            }

        } catch (Exception e) {
           e.printStackTrace();
           Log.out(e.toString(),1);
        } // вывод исключений
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverjsh.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class WaitingForConnectThread implements Runnable{
    
    int i = 0;
    int serverPort = 7777;
    
    @Override
    public void run() {

        try {

            // привинтить сокет на локалхост, порт 3128
            ServerSocket server = new ServerSocket(serverPort);

            System.out.println("Thread of accepting connect is started");

            // слушаем порт
            while (true) {

                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток и увеличиваем счётчик на единичку
                //new SessionThread(i, server.accept());
                Socket socket = server.accept();
                try {
                    new SessionThread(socket);
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
                System.out.println("New connect");
                i++;
            }
        } catch (Exception e) {
            System.out.println("init error: " + e);
        } // вывод исключений
    }
    
}

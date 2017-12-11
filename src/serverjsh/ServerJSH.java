package serverjsh;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

class ServerJSH implements Runnable {

//    public class NetworkPackage {
//
//        int UIN;
//        int responseToUIN;
//    }
    Thread t;
    int serverPort = 7777;
    static int i = 0; // счётчик подключений
    static int numERRORS = 0;

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

    public static void main(String args[]) {

        System.out.println("/n---------Server JSH is started----------");

        //Запускаем поток ожидающий запросы от клиентов
        Thread t = new Thread(new ServerJSH());
        t.start();

//------------------------------------------------------------------------------
//                          Г Л А В Н Ы Й  Ц И К Л
//------------------------------------------------------------------------------
        while (true) {

            synchronized (SessionThread.networkPackageList) {
                SessionThread.networkPackageList.keySet().forEach((UUID key) -> {
                    if (SessionThread.networkPackageList.get(key).getServerResponse() == null) {
                        String _strRequest = SessionThread.networkPackageList.get(key).getClientRequest();
                        SessionThread.networkPackageList.get(key).setServerResponse(_strRequest);
                    }
                });
            }

            String date = new SimpleDateFormat("hh.mm.ss:SSSS").format(new Date());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END -> "
                    + date
                    + " map:" + SessionThread.networkPackageList.size()
                    + " s:" + i
                    + " ERR:" + numERRORS);

            //задержка перед повтором главного цикла, чтобы не нагружать процессор
            try {
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
            }
//------------------------------------------------------------------------------
//                    К О Н Е Ц   Г Л А В Н О Г О Ц И К Л А
//------------------------------------------------------------------------------
        }
    }

}

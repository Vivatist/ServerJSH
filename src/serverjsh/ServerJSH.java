package serverjsh;

import java.net.*;

class ServerJSH implements Runnable {

//    public class NetworkPackage {
//
//        int UIN;
//        int responseToUIN;
//    }
    Thread t;
    int serverPort = 7777;

    public void run() {
        try {
            int i = 0; // счётчик подключений

            // привинтить сокет на локалхост, порт 3128
            ServerSocket server = new ServerSocket(serverPort);

            System.out.println("Thread of accepting connect is started");

            // слушаем порт
            while (true) {

                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток и увеличиваем счётчик на единичку
                new NewConnectThread(i, server.accept());

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

        //Главный цикл
        while (true) {

            System.out.println("Loop");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
        }

    }

}

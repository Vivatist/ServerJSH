package serverjsh;

import java.net.*;

class ServerJSH {

    static String commandStr;

    public class NetworkPackage  {

        int UIN;
        int responseToUIN;
        String data;
    }

    public static void main(String args[]) {

        try {
            int i = 0; // счётчик подключений

            // привинтить сокет на локалхост, порт 3128
            ServerSocket server = new ServerSocket(7777);

            System.out.println("server is started");

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

}

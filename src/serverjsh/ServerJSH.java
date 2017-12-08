package serverjsh;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class ServeOneJabber extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServeOneJabber(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Включаем автоматическое выталкивание:
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                .getOutputStream())), true);
        // Если любой из вышеприведенных вызовов приведет к
        // возникновению исключения, то вызывающий отвечает за
        // закрытие сокета. В противном случае, нить
        // закроет его.

        this.start();// вызываем run()
    }

    public void run() {
        try {
            System.out.println("Start thread");
            while (true) {
                String str = in.readLine();
                if (str.equals("END")) {
                    break;
                }
                System.out.println("Echoing: " + str);
                out.println(str);
            }
            System.out.println("closing...");
        } catch (IOException e) {
            System.err.println("IO Exception");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}

class ServerJSH implements Runnable {

//    public class NetworkPackage {
//
//        int UIN;
//        int responseToUIN;
//    }
    Thread t;
    int serverPort = 7777;
    static int i = 0; // счётчик подключений

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
                    new ServeOneJabber(socket);
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

        //Главный цикл
        while (true) {
            String date = new SimpleDateFormat("hh.mm.ss:SSSS").format(new Date());

            for (UUID key : SessionThread.networkPackageList.keySet()) {
                String _strRequest = SessionThread.networkPackageList.get(key).getClientRequest();
                System.out.println("ID = " + key + ", Client request = " + _strRequest);
                SessionThread.networkPackageList.get(key).setServerResponse("Testing server response: " + _strRequest);
            }
            System.out.println(date + " map:" + SessionThread.networkPackageList.size() + " s:" + i + "~~~~~~~~~~~~~~~~~~~~~~~~~END~~~~~~~~~~~~~~~~~~~~~~~");

            for (int i = 0; i < 100_000_000; i++) {
                //Дуроной цикл! удалить
            }
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {

        }

    }

}

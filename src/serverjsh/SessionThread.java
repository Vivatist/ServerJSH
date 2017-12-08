package serverjsh;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

class SessionThread extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    //общая для всех потоков карта Запрос-Ответ
    public static Map<UUID, NetworkPackage> networkPackageList = new ConcurrentHashMap();
    private final Date timeOfStartSession = new Date();

    public SessionThread(Socket s) throws IOException {
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

    @Override
    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("END")) {
                    break;
                }

                //Обрабатываем принятую строку
                NetworkPackage np = new NetworkPackage(str);
                UUID key = UUID.randomUUID();

                //добавляем пакет с запросом в общий лист запросов
                networkPackageList.put(key, np);
               
                NetworkPackage TMPnp;
                
                boolean _flag = false;
                do {
                    TMPnp = networkPackageList.get(key);
                    if (TMPnp.getServerResponse() != null) {
                        _flag = true;
                    }

                } while (!_flag);

                //удаляем пакет из общего списка
                networkPackageList.remove(key);

                str = TMPnp.getClientRequest() + "->" + TMPnp.getServerResponse();

                System.out.println("Echoing: " + str);
                out.println(str);
            }
            System.out.println("closing...");
        } catch (Exception e) {
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


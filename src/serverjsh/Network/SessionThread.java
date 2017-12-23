package serverjsh.Network;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SessionThread extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    //общая для всех потоков очередь Запросов
    public static Map<UUID, NetworkPackage> responseQueue = new ConcurrentHashMap();
    //общая для всех запросов очередь Ответов
    public static Map<UUID, NetworkPackage> requestQueue = new ConcurrentHashMap();
    public static int numERRORS = 0; //счетчик ошибок
    public static int numConnections = 0; // счётчик подключений
    public static int bites = 0; // скорость обмена (байт в секунду)
    
    


  

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
        
        numConnections++;
        
        //запускаем таймер для подсчета скорости обмена
       
        
        this.start();// вызываем run()

    }

    
    
    @Nullable
    public static NetworkPackage getRequest() {
        // вынимаем из очереди запросов первый элемент и возвращаем его, удаляя из очереди
        if (requestQueue.size() > 0) {
            Iterator<Map.Entry<UUID, NetworkPackage>> entries = requestQueue.entrySet().iterator();
            Map.Entry<UUID, NetworkPackage> entry = entries.next();
            NetworkPackage np = entry.getValue();
            requestQueue.remove(np.key);
            return np;
        } else {
            return null;
        }
    }

    
    
    public static void setResponse(NetworkPackage np) {
        //вставляем в очередь ответов новый ответ сервера
        synchronized (responseQueue) {
            responseQueue.put(np.key, np);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                bites = bites + str.length();
                
                if (str.equals("END")) {
                    break;
                }

                //Обрабатываем принятую строку
                NetworkPackage np = new NetworkPackage(str);
                UUID key = np.key;

                //добавляем новый запрос в очередь запросов
                synchronized (requestQueue) {
                    //добавляем пакет с запросом в общий лист запросов
                    requestQueue.put(key, np);
                }

                boolean _flag = false;
                do {
                    if (responseQueue.get(key) != null) {
                        np = responseQueue.get(key);
                        responseQueue.remove(key);
                        _flag = true;
                    }
                } while (!_flag);

          //      str = np.getClientRequest() + "->" + np.getServerResponse();
          //      System.out.println("Echoing: " + str);
                bites = bites + np.getServerResponse().length();
                out.println(np.getServerResponse());
            }
            System.out.println("closing...");
        }catch (SocketException e){
            System.out.println("Connection reset");
        }
        catch (Exception e) {
            numERRORS++;
            System.err.println("IO Exception " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                numERRORS++;
                System.err.println("Socket not closed");
            }
        }
    }
}

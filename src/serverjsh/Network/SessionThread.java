package serverjsh.Network;

import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Класс, объекты которого запускают сетевую сессию в отдельном потоке.
 * @author Andrey Bochkarev
 * @version 1.0
 */
public class SessionThread extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    //общая для всех потоков очередь Запросов
    public static Map<String, NetworkMessage> responseQueue = new ConcurrentHashMap();
    //общая для всех запросов очередь Ответов
    public static Map<String, NetworkMessage> requestQueue = new ConcurrentHashMap();
    public static int numERRORS = 0; //счетчик ошибок
    public static int numConnections = 0; // счётчик подключений
    public static int bites = 0; // скорость обмена (байт в секунду)

    /**
     * Конструктор, инициализирует объект и запускает отдельный поток для сетевой сессии
     * @param s Сокет входящего подключения
     * @throws IOException
     */
    SessionThread(Socket s) throws IOException {
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

    /**
     * Статичный метод возвращает очередной запрос клиентов из очереди
     * @return Сетевой пакет
     */
    @Nullable
    public static NetworkMessage getRequest() {
        // вынимаем из очереди запросов первый элемент и возвращаем его, удаляя из очереди
        if (requestQueue.size() > 0) {
            Iterator<Map.Entry<String, NetworkMessage>> entries = requestQueue.entrySet().iterator();
            Map.Entry<String, NetworkMessage> entry = entries.next();
            NetworkMessage nm = entry.getValue();
            requestQueue.remove(nm.getId());
            return nm;
        } else {
            return null;
        }
    }

    /**
     * Статичный метод добавляет в очередь сетевых пакетов ответ от сервера для дальнейшей отправки клиенту
     * @param nm Сетевой пакет
     */
    public static void setResponse(NetworkMessage nm) {
        //вставляем в очередь ответов новый ответ сервера
        synchronized (responseQueue) {
            responseQueue.put(nm.getId(), nm);
        }
    }

    /**
     * Сессия обмена данными между клиентом и сервером.
     */
    @Override
    public void run() {
        String jsonText = "";
        try {
            while (true) {
                jsonText = in.readLine();
                bites = bites + jsonText.length();

                if (jsonText.equals("END")) {
                    break;
                }

                //Обрабатываем принятую строку
                NetworkMessage nm = new NetworkMessage(jsonText);
                String id = nm.getId();



                //добавляем новый запрос в очередь запросов
                synchronized (requestQueue) {
                    //добавляем пакет с запросом в общий лист запросов
                    requestQueue.put(id, nm);
                }

                boolean _flag = false;
                do {
                    if (responseQueue.get(id) != null) {
                        nm = responseQueue.get(id);
                        responseQueue.remove(id);
                        _flag = true;
                    }
                } while (!_flag);

                //      str = np.getClientRequest() + "->" + np.getServerResponse();
                //      System.out.println("Echoing: " + str);
                bites = bites + nm.toJSON().length();
                out.println(nm.toJSON());
            }
            System.out.println("closing...");
        }

        catch (JsonSyntaxException e ){
            System.err.println("IO Exception " + e);
            System.err.println(jsonText);

        }catch (SocketException e) {
            System.out.println("Connection reset");
        } catch (Exception e) {
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

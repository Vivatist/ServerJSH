package serverjsh.Network;

import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.Nullable;
import serverjsh.Services.Log;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Класс, объекты которого запускают сетевую сессию в отдельном потоке.
 *
 * @author Andrey Bochkarev
 * @version 1.0
 */
public class SessionThread extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    //общая для всех потоков очередь Запросов
    private static final Map<String, NetworkMessage> responseQueue = new ConcurrentHashMap();
    //общая для всех запросов очередь Ответов
    private static final Map<String, NetworkMessage> requestQueue = new ConcurrentHashMap();


    /**
     * Конструктор, инициализирует объект и запускает отдельный поток для сетевой сессии
     *
     * @param s Сокет входящего подключения
     */
    SessionThread(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Включаем автоматическое выталкивание:
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                .getOutputStream())), true);

        this.start();// запускаем поток (вызываем run())

    }

    /**
     * Статичный метод возвращает очередной запрос клиентов из очереди
     *
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
     *
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

                if (jsonText == null) { //Если достигли конца потока - выходим
                    break;
                }


                Log.out(socket.getInetAddress() + ":" + socket.getPort() + " Client sent " + jsonText, 3);
                if (jsonText.equals("END")) { // Если клиент прислал END - выходим
                    break;
                }

                //Обрабатываем принятую строку
                NetworkMessage nm = new NetworkMessage(jsonText);
                String id = nm.getId();
                String text = nm.getText(); //извлекаем текст запроса


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

                text += " -> " + nm.getText();
                Log.out(socket.getInetAddress() + ":" + socket.getPort() + " " + text, 3);

                out.println(nm.toJson());
            }
            Log.out(socket.getInetAddress() + ":" + socket.getPort() + " Closing connect", 1);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.out(socket.getInetAddress() + ":" + socket.getPort() + " JSON error, jsonText [" +jsonText + "] "+ e.toString(), 1);
        } catch (SocketException e) {
            Log.out(socket.getInetAddress() + ":" + socket.getPort() + " Connection reset", 1);
            e.printStackTrace();
        } catch (Exception e) {
            Log.out(socket.getInetAddress() + ":" + socket.getPort() + " Error: " +e.toString(), 1);
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Log.out("Socket not closed " + socket.getInetAddress() + ":" + socket.getPort(), 1);
            }
        }
    }
}

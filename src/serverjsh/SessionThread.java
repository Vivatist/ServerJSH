package serverjsh;

import java.io.*;
import java.net.*;
import java.util.*;



class SessionThread extends Thread {

    private Socket s;
    private int num;
    //общая для всех потоков карта Запрос-Ответ
    public static Map<UUID, NetworkPackage> networkPackageList = new HashMap();
    private final Date timeOfStartSession = new Date();

    

    public SessionThread(int num, Socket s) {
        // копируем данные
        this.num = num;
        this.s = s;

        // и запускаем новый вычислительный поток (см. ф-ю run())
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run() {
        try {
            System.out.println("S <--packet! K");

            // из сокета клиента берём поток входящих данных
            InputStream is = s.getInputStream();
            // и оттуда же - поток данных от сервера к клиенту
            OutputStream os = s.getOutputStream();

            // буфер данных в 64 килобайта
            byte buf[] = new byte[64 * 1024];
            // читаем 64кб от клиента, результат - кол-во реально принятых данных
            int r = is.read(buf);
            // создаём строку, содержащую полученную от клиента информацию
            String str = new String(buf, 0, r);

            NetworkPackage np = new NetworkPackage(str);
            UUID key = UUID.randomUUID();

            //добавляем пакет с запросом в общий лист запросов
            networkPackageList.put(key, np);
           // Thread.sleep((int) (Math.random() * 1000)); //Случайная задержка для теста!!! удалить
            
            NetworkPackage TMPnp;
            TMPnp = networkPackageList.get(key); 
            boolean _flag = false;
            do {
                
                if (TMPnp.getServerResponse() != null){
                    _flag = true;
                    System.out.println("Flag true!");
                } 
            
            } while (!_flag);
            
            //удаляем пакет из общего списка
            networkPackageList.remove(key);
            
            String _str = TMPnp.getClientRequest()+"-"+TMPnp.getServerResponse();
            
            //отправляем ответ: 
            os.write(_str.getBytes());

            // завершаем соединение
            s.close();
            System.out.println("Close " + s);

        } catch (Exception e) {
            System.out.println("init error: " + e);
        } // вывод исключений
    }

}

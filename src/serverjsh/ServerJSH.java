package serverjsh;


import java.text.SimpleDateFormat;
import java.util.*;
import serverjsh.Network.SessionThread;
import serverjsh.Network.WaitingForConnectThread;

class ServerJSH {

    Thread t;
    int serverPort = 7777;
    static int i = 0; // счётчик подключений
    static int numERRORS = 0;


    public static void main(String args[]) {

        System.out.println("/n---------Server JSH is started----------");

        //Запускаем поток ожидающий запросы от клиентов
        Thread t = new Thread(new WaitingForConnectThread());
        t.start();

//------------------------------------------------------------------------------
//                          Г Л А В Н Ы Й  Ц И К Л
//------------------------------------------------------------------------------
        while (true) {
            System.out.println(SessionThread.networkPackageList.size());

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

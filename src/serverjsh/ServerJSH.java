package serverjsh;

import java.text.SimpleDateFormat;
import java.util.*;
import serverjsh.Network.SessionThread;
import serverjsh.Network.NetworkPackage;
import serverjsh.Network.WaitingForConnectThread;


class ServerJSH {

    static int previousBites = 0;
    static int Bites = 0;
    static java.util.Timer timer2 = new java.util.Timer();
    static TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Bites = SessionThread.bites - previousBites;
            previousBites = SessionThread.bites;
            String date = new SimpleDateFormat("hh.mm.ss:SSSS").format(new Date());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END -> "
                    + date
                    + " rqst:" + SessionThread.requestQueue.size()
                    + " rspns:" + SessionThread.responseQueue.size()
                    + " connects:" + SessionThread.numConnections
                    + " bSec:" + Bites
                    + " ERR:" + SessionThread.numERRORS);
        }
    };

    Thread t;
    int serverPort = 7777;

    public static void main(String args[]) {
        System.out.println("/n---------Server JSH is started----------");
        timer2.schedule(timerTask, 0, 1000);

        //Запускаем поток ожидающий запросы от клиентов
        Thread t = new Thread(new WaitingForConnectThread());
        t.start();

//------------------------------------------------------------------------------
//                          Г Л А В Н Ы Й  Ц И К Л
//------------------------------------------------------------------------------
        while (true) {

            NetworkPackage np = SessionThread.getRequest();

            if (np != null) {
                np.setServerResponse(np.getClientRequest());
                SessionThread.setResponse(np);
            }
        }
//------------------------------------------------------------------------------
//                    К О Н Е Ц   Г Л А В Н О Г О Ц И К Л А
//------------------------------------------------------------------------------

    }

}

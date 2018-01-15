package serverjsh;

import java.text.SimpleDateFormat;
import java.util.*;
import serverjsh.Commands.CommandManager;
import serverjsh.Database.Dbconnect;
import serverjsh.Errors.MyExceptionOfNetworkMessage;
import serverjsh.Network.*;


class Main {

    private static int PORT;
    private static int DELAY_LOOP;

    private static int previousBites = 0;

    private static Timer myTimer = new Timer();

    private static TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            int bites = SessionThread.bites - previousBites;
            previousBites = SessionThread.bites;
            String date = new SimpleDateFormat("hh.mm.ss:SSSS").format(new Date());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END -> "
                    + date
                    + " rqst:" + SessionThread.requestQueue.size()
                    + " rspns:" + SessionThread.responseQueue.size()
                    + " connects:" + SessionThread.numConnections
                    + " BSec:" + bites
                    + " ERR:" + SessionThread.numERRORS);
        }
    };


    public static void main(String args[]) throws InterruptedException, MyExceptionOfNetworkMessage {
        System.out.println("\n---------Server JSH is started----------");

        //Загружаем настройки
        Log.setVisibleInConsole(Boolean.parseBoolean(Settings.GetProperty("VISIBLE_LOG", "True")));
        Log.setCOLORED(Boolean.parseBoolean(Settings.GetProperty("COLORED_LOG", "False")));
        PORT = Integer.parseInt(Settings.GetProperty("PORT", "7777"));
        DELAY_LOOP = Integer.parseInt(Settings.GetProperty("DELAY_LOOP", "10"));

        //  myTimer.schedule(timerTask, 0, 1000);

        //Запускаем поток ожидающий запросы от клиентов
        Thread t = new Thread(new WaitingForConnectThread(PORT));
        t.start();

        Log.out("str");
        Dbconnect.CreateDB();


//------------------------------------------------------------------------------
//                          Г Л А В Н Ы Й  Ц И К Л
//------------------------------------------------------------------------------

        CommandManager commandManager = new CommandManager();

        while (true) {

            NetworkMessage networkMessage = SessionThread.getRequest();

            if (networkMessage != null) {
                try {

                    String responseToTheRequest = commandManager.PerformAction(networkMessage);
                    networkMessage.setText(responseToTheRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                    networkMessage.setError(true);
                    networkMessage.setText(e.getMessage());
                }
                SessionThread.setResponse(networkMessage);
            }
            Thread.sleep(DELAY_LOOP);
        }
//------------------------------------------------------------------------------
//                    К О Н Е Ц   Г Л А В Н О Г О   Ц И К Л А
//------------------------------------------------------------------------------

    }

}

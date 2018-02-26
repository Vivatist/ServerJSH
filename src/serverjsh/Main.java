package serverjsh;

import org.apache.log4j.Logger;
import serverjsh.Domain.Commands.CommandManager;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.*;
import serverjsh.Services.Settings;


class Main {

    //private static Timer myTimer = new Timer();

//    private static TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//
//            String date = new SimpleDateFormat("hh.mm.ss:SSSS").format(new Date());
//            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END -> "
//                    + date
//             );
//        }
//    };

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String args[]) throws InterruptedException, MyExceptionOfNetworkMessage {
        System.out.println("\n");
        log.info("-------------------------------------------------");
        log.info("Server JSH is started");

        //Загружаем настройки
        int PORT = Integer.parseInt(Settings.GetProperty("PORT", "7777"));
        int DELAY_LOOP = Integer.parseInt(Settings.GetProperty("DELAY_LOOP", "10"));

        //  myTimer.schedule(timerTask, 0, 1000);

        //Запускаем поток ожидающий запросы от клиентов
        Thread t = new Thread(new NewSessionLauncher(PORT));
        t.start();

//------------------------------------------------------------------------------
//                          Г Л А В Н Ы Й  Ц И К Л
//------------------------------------------------------------------------------

        CommandManager commandManager = new CommandManager();

        while (true) {

            NetworkMessage networkMessage = SessionThread.getRequest();
            if (networkMessage != null) { //если null - очередь пуста
                try {
                    networkMessage = commandManager.PerformAction(networkMessage);
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

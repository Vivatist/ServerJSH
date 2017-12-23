package serverjsh;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


import serverjsh.Commands.CommandManager;
import serverjsh.Commands.CommandPackage;
import serverjsh.Errors.MyExceptionOfCommandPackage;
import serverjsh.Network.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


class Main {

    private static int PORT;
    private static int DELAY_LOOP;

    private static int previousBites = 0;

    private static Timer myTimer = new Timer();

    // Загрузка сохраненных настроек
    private static void LoadSettings(String _settingsFilename) {
        final Properties props = new Properties();
        try {
            //пытаемся прочитать настройки из файла
            FileInputStream input = new FileInputStream(_settingsFilename);
            props.load(input);
            input.close();
            PORT = Integer.parseInt(props.getProperty("PORT"));
            DELAY_LOOP = Integer.parseInt(props.getProperty("DELAY_LOOP"));
            System.out.println("Upload settings complete");
        } catch (Exception ignore) {
            //если файл не существует - создаем и записываем значения по умолчанию
            props.setProperty("PORT", "7777");
            props.setProperty("DELAY_LOOP", "10");

            FileOutputStream output = null;
            try {
                output = new FileOutputStream(_settingsFilename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                props.store(output, "Saved settings");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Making new settings file");
            LoadSettings(_settingsFilename);
        }
    }

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


    public static void main(String args[]) throws InterruptedException {
        System.out.println("\n---------Server JSH is started----------");

        String settingsFilename = "Main.ini";
        System.out.println("Settings file: " + settingsFilename);
        LoadSettings(settingsFilename);

      //  myTimer.schedule(timerTask, 0, 1000);

        //Запускаем поток ожидающий запросы от клиентов
        Thread t = new Thread(new WaitingForConnectThread(PORT));
        t.start();






//------------------------------------------------------------------------------
//                          Г Л А В Н Ы Й  Ц И К Л
//------------------------------------------------------------------------------

        CommandManager commandManager = new CommandManager();

        while (true) {

            NetworkPackage networkPackage = SessionThread.getRequest();

            if (networkPackage != null) {
                try {
                    CommandPackage commandPackage = new CommandPackage(networkPackage.getClientRequest());
                    String responseToTheRequest = commandManager.PerformAction(commandPackage);
                    networkPackage.setServerResponse(responseToTheRequest);
                    //System.out.println(responseToTheRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    networkPackage.setServerResponse(e.getMessage());
                }
                SessionThread.setResponse(networkPackage);
            }
            Thread.sleep(DELAY_LOOP);
        }
//------------------------------------------------------------------------------
//                    К О Н Е Ц   Г Л А В Н О Г О   Ц И К Л А
//------------------------------------------------------------------------------

    }

}

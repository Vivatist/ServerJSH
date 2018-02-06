package serverjsh.Domein.Commands;

import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Services.Log;
import serverjsh.Network.NetworkMessage;
import serverjsh.Services.Settings;

public class LogViewCommand implements ICommand {

    @Override
    public NetworkMessage Execute(NetworkMessage nm) throws MyExceptionOfNetworkMessage {

        String text = "Переключение отображения логов на стороне сервера (аргументы: 1, 2, 3)";
        String parameter = nm.getParameter();
        if (parameter != null) {
            switch (Integer.parseInt(parameter)) {
                case 1: {
                    text = "The display visible level = 1";
                    Settings.SetProperty("VISIBLE_LOG", "1");
                    Log.setVisibleInConsole(2); //сначала выводим в лог, затем отключаем
                    Log.out(text, 2);
                    Log.setVisibleInConsole(1);
                    break;
                }
                case 2: {
                    text = "The display visible level = 2";
                    Settings.SetProperty("VISIBLE_LOG", "2");
                    Log.out(text, 2);
                    Log.setVisibleInConsole(2);
                    break;
                }
                case 3: {
                    text = "The display visible level = 3";
                    System.out.println("afafwdwqf!");
                    Settings.SetProperty("VISIBLE_LOG", "3");
                    Log.out(text, 2);
                    Log.setVisibleInConsole(3);
                    break;
                }
                default: {
                    text = parameter + " is a bad parameter";
                    nm.setError(true);
                }
            }

        }

        nm.setText(text);
        return nm;
    }
}
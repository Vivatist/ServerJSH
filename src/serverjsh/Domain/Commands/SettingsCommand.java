package serverjsh.Domain.Commands;

import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Services.Log;
import serverjsh.Network.NetworkMessage;
import serverjsh.Services.Settings;

import java.io.*;

public class SettingsCommand implements ICommand {

    @Override
    public NetworkMessage Execute(NetworkMessage nm) throws MyExceptionOfNetworkMessage {

        StringBuilder text = new StringBuilder("Управление настройками сервера. View - просмотр файла настроек.\n"
                + "Файл: " + System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + Settings.getSettingsFileName() + "\n");
        System.out.println(nm.getParameter());
        String parameter = nm.getParameter();
        if (parameter != null) {
            switch (parameter) {
                case "view": {
                    String line;
                    try {
                        File file = new File(Settings.getSettingsFileName());
                        //создаем объект FileReader для объекта File
                        FileReader fr = new FileReader(file);
                        //создаем BufferedReader с существующего FileReader для построчного считывания
                        BufferedReader reader = new BufferedReader(fr);
                        // считаем сначала первую строку
                        line = reader.readLine();
                        while (line != null) {
                            text.append(line).append("\n");
                            // считываем остальные строки в цикле
                            line = reader.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.out(e.toString(),1);
                        text.append(e.toString());
                        nm.setError(true);
                    }
                    break;
                }
                case "set": {
                    text.append("Заглушка SET");
                    break;
                }
                default: {
                    text.append(" : test with bad argument done");
                }
            }
        }

        nm.setText(text.toString());
        return nm;

    }

}

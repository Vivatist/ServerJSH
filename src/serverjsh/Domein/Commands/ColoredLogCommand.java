package serverjsh.Domein.Commands;


import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Services.Log;
import serverjsh.Network.NetworkMessage;
import serverjsh.Services.Settings;

public class ColoredLogCommand implements ICommand {

    @Override
    public NetworkMessage Execute(NetworkMessage nm) throws MyExceptionOfNetworkMessage {

        String text = "Цветные логи. Не для всех терминалов (аргументы: On, Off)";

        String parameter = nm.getParameter();
        if (parameter != null) {
            switch (parameter.toLowerCase()) {
                case "on": {
                    text = "The colored log on the server is enabled";
                    Settings.SetProperty("COLORED_LOG", "True");
                    Log.setColored(true);
                    Log.out(text, 2);
                    break;
                }
                case "off": {
                    text = "The colored log on the server is disabled";
                    Log.out(text, 2);
                    Settings.SetProperty("COLORED_LOG", "False");
                    Log.setColored(false);
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

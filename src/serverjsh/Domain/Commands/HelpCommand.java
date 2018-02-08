package serverjsh.Domain.Commands;


import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

public class HelpCommand implements ICommand {

    @Override
    public NetworkMessage Execute(NetworkMessage nm) throws MyExceptionOfNetworkMessage {

        String text;
        text =
                "Команды можно вводить в любом регистре, сервер одинаково\n" +
                        "обработает команду help и HeLp. Некоторве команды\n" +
                        "поддерживают аргументы. Аргументы записываются в скобках\n +" +
                        "через запятую. Для отключения от сервера введите 'END'\n" +
                        "заглавными буквами.\n\n" +

                        "Список доступных команд:\n\n" +

                        "help - выводит подсказку по работе с сервером. В данный момент вы\n" +
                        "наблюдаете результат ее работы.\n\n" +

                        "Info           - выводит информацию. Доступные аргументы:\n" +
                        "   -server     - информация о работе сервера\n" +
                        "   -ruspberry  - информация о платформе\n\n" +

                        "ColoredLog     - задает режим вывода логов\n" +
                        "   -on         - цветной лог (не для всех терминалов!)\n" +
                        "   -off        - одноцветный лог\n\n" +

                        "LogView        - задает отображение лога на сервере\n" +
                        "   -on         - логи выводятся в консоль\n" +
                        "   -off        - логи не отображаются\n\n" +

                        "Sеttings       - работа с настройками сервера\n" +
                        "   -view       - отображает файл настроек\n" +
                        "   -set [имя,значение] - записывает в файл настроек\n" +
                        "               значние [имя] с параметром [параметр]\n\n"
        ;

        nm.setText(text);
        return nm;

    }
}
package serverjsh.Commands;


public class HelpCommand implements ICommand {

    @Override
    public String Execute(CommandPackage cp) {

        String helpText;
        helpText =
                        "Команды можно вводить в любом регистре, сервер одинаково\n" +
                        "обработает команду help и HeLp. Некоторве команды\n" +
                        "поддерживают аргументы. Перед каждым аргументом необходимо\n" +
                        "добавить знак '-' (минус). Для отключения от сервера введите 'END'\n" +
                        "заглавными буквами.\n\n" +

                        "Список доступных команд:\n" +
                        "help - выводит подсказку по работе с сервером. В данный момент вы\n" +
                        "наблюдаете результат ее работы.\n\n" +
                        "info - выводит информацию. Доступные аргументы:\n" +
                        "   server - информация о работе сервера\n" +
                        "   ruspberry - информация о платформе"
        ;

     //   helpText = "1 line\n"+
     //           "2 line\n"+
    //            "3 line\n";



        return helpText;
    }
}
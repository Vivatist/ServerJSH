package serverjsh.Commands;


import serverjsh.Errors.MyExceptionBadCommand;
import serverjsh.Errors.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

public class SqlCommand implements ICommand {

    @Override
    public String Execute(NetworkMessage nm) throws MyExceptionBadCommand, MyExceptionOfNetworkMessage {

        StringBuilder response = new StringBuilder("Работа с базой данных\n");

        for (String arg : nm.getArguments()) {
            int i = arg.indexOf(" ");
            String comandString = arg.substring(0,i);
            System.out.println(comandString);
            switch (arg) {

                case "execute": {

                  // response.append(printHwDetails());

                    break;
                }

                case "server": {
                    response.append("/-server STUB");
                    break;
                }

                default: {
                    throw new MyExceptionBadCommand("ERROR: Bad argument: " + arg, 1);
                }
            }

        }




        return response.toString();

    }
}
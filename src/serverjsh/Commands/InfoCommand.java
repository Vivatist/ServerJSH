package serverjsh.Commands;


import serverjsh.Errors.MyExceptionBadCommand;

public class InfoCommand implements ICommand {

    @Override
    public String Execute(CommandPackage cp) throws MyExceptionBadCommand {

        String response = "INFO STUB";

        for (String arg : cp.getArguments()) {
            switch (arg) {

                case "ruspberry": {
                    response +=  "/-ruspberry STUB";
                    break;
                }

                case "server": {
                    response +=  "/-server STUB";
                    break;
                }

                default: {
                    throw new MyExceptionBadCommand("ERROR: Bad argument: " + arg, 1);
                }
            }

        }
        return response;

    }


}



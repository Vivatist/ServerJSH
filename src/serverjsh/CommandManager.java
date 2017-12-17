package serverjsh;

public class CommandManager {


    static class CommandProcessor {
        public void doCommand(Command command) {
            command.execute();
        }
    }

    private interface Command {

        void execute();
    }



    static class viewconnect implements Command {

        private boolean view;

        public viewconnect(boolean view) {
            this.view = view;
        }

        @Override
        public void execute() {
            System.out.println("param is: " + view);
        }

    }


    static class help implements Command {

        @Override
        public void execute() {
            System.out.println("--------------");
            System.out.println("HELP HELP HELP");
            System.out.println("HELP HELP HELP");
            System.out.println("HELP HELP HELP");
            System.out.println("HELP HELP HELP");
            System.out.println("--------------");
        }

    }



}
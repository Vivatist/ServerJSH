package serverjsh.Network.Exceptions;

public class MyExceptionOfNetworkMessage extends Exception {

    // TODO удалить лишний закомментированный код
    // private int number;
    // private String message;


    // @Override
    // public String getMessage() {
    //     return message;
    // }

    // public int getNumber(){return number;}

    public MyExceptionOfNetworkMessage(String message, int num) {

        super(message);
        //   this.number=num;
        //   this.message = message;

    }
}

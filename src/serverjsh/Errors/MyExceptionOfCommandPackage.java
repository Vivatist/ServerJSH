package serverjsh.Errors;

public class MyExceptionOfCommandPackage extends Exception {

    private int number;
    private String message;


    @Override
    public String getMessage() {
        return message;
    }

    public int getNumber(){return number;}

    public MyExceptionOfCommandPackage(String message, int num){

        super(message);
        this.number=num;
        this.message = message;

    }
}

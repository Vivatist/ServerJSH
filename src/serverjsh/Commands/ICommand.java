package serverjsh.Commands;


import serverjsh.Errors.MyExceptionBadCommand;

public interface ICommand {
   // void Execute();

     String Execute (CommandPackage cp) throws MyExceptionBadCommand;
}

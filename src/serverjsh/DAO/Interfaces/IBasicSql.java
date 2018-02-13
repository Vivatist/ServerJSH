package serverjsh.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IBasicSql {

    //выполняет SQL запрос, возвращает результат в виде текста
   List<String> Exec (String sql) throws SQLException;

//    //возвращает список всех таблиц базы
//    List<String> Tables();
//
//    //возвращает структуру таблицы
//    String TableStructure(String nameTable);
}

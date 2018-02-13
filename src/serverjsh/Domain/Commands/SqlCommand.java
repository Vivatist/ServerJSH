package serverjsh.Domain.Commands;

import org.apache.log4j.Logger;
import serverjsh.DAO.Service.BasicSql;
import serverjsh.DAO.Entity.User;
import serverjsh.DAO.Service.UserService;
import serverjsh.Domain.Exceptions.MyExceptionBadCommand;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;

import java.sql.SQLException;
import java.util.List;


public class SqlCommand implements ICommand {

    private BasicSql basicSql = new BasicSql();

    private static final Logger log = Logger.getLogger(SqlCommand.class);

    @Override
    public NetworkMessage Execute(NetworkMessage nm) throws MyExceptionBadCommand, MyExceptionOfNetworkMessage, SQLException {

        String text = "Работа с базой данных. Примеры:\n" +
                "-Структура базы данных: sql -show\n" +
                "-Структура БД включая все записи: sql -showAll\n" +
                "-Содержимое таблицы: sql -table (user)\n" +
                "-Показать все таблицы: sql -executequery (SHOW TABLES)\n" +
                "-Показать структуру конкретной таблицы: sql -executequery (SHOW COLUMNS FROM users)\n" +
                "-Создать новую таблицу: sql -execute (CREATE TABLE Student (Code INTEGER NOT NULL AUTO_INCREMENT, Name CHAR (30) NOT NULL , Address CHAR (50),Mark DECIMAL))\n" +
                "-Добавить столбец в таблицу: sql -execute (ALTER TABLE users ADD COLUMN name VARCHAR (20))\n" +
                "-Удалить столбец из таблицы: sql -execute (DROP TABLE users)\n" +
                "-Вставить запись в таблицу: sql -execute(INSERT INTO TestAutoInc (name, address, year) VALUES ( 'Петр', 'Ленинград', 1957))\n" +
                "-Вывести все столбцы с отбором: sql -executequery (SELECT code,name FROM student WHERE name = 'Петр')\n";

        String parameter = nm.getParameter();
        if (parameter != null) {
            switch (parameter) {

                case "exec": {
                    text = "";
                    log.debug("SQL запрос: " + nm.getArguments());
                    List<String> resultList = basicSql.Exec(nm.getArguments());
                    for (String line : resultList) {
                        text += "\n" + line;
                    }
                    break;
                }

                case "test": {
                    User user = new User();
                    UserService userService = new UserService();

                    for (int i = 0; i < 10; i++) {
                        user.setId(i);
                        user.setLogin("Admin");
                        user.setPassword("238938");
                        user.setGroupId(100);
                        userService.add(user);
                    }


                    text = "";
                    List<String> lineList = basicSql.Exec("SELECT * FROM users");
                    for (String line : lineList) {
                        text += "\n" + line;
                    }
                    text += "\n";
                    break;
                }

                case "testdel": {
                    User user = new User();
                    UserService userService = new UserService();

                    int i = ((int)(Math.random() * 11));

                    user.setId(i);
                    user.setLogin("Admin");
                    user.setPassword("238938");
                    user.setGroupId(100);
                    userService.remove(user);

                    text = "";
                    List<String> lineList = basicSql.Exec("SELECT * FROM users");
                    for (String line : lineList) {
                        text += "\n" + line;
                    }
                    text += "\n";
                    break;
                }

                case "show": {
                    text = "";
                    List<String> tablesList = basicSql.Exec("SHOW TABLES");
                    for (String table : tablesList) {
                        text += "\n" + table;
                        text += "\n--------------------";
                        List<String> columnList = basicSql.Exec("SHOW COLUMNS FROM " + table.split("\t")[0]);
                        for (String column : columnList) {
                            text += "\n" + column;
                        }
                        text += "\n";
                    }
                    break;
                }

                case "showall": {
                    text = "";
                    List<String> tablesList = basicSql.Exec("SHOW TABLES");
                    for (String table : tablesList) {
                        text += "\n" + table;
                        text += "\n--------------------";
                        List<String> columnList = basicSql.Exec("SHOW COLUMNS FROM " + table.split("\t")[0]);
                        for (String column : columnList) {
                            text += "\n" + column;
                        }
                        text += "\n";

                        List<String> lineList = basicSql.Exec("SELECT * FROM " + table.split("\t")[0]);
                        for (String line : lineList) {
                            text += "\n" + line;
                        }
                        text += "\n";
                    }
                    break;
                }

                default: {
                    throw new MyExceptionBadCommand("ERROR: Bad argument: " + parameter, 1);
                }
            }
        }

        nm.setText(text);
        return nm;

    }
}
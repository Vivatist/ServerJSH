package serverjsh.Domein.Commands;


import serverjsh.DAO.Base;
import serverjsh.Domein.Exceptions.MyExceptionBadCommand;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Services.Log;
import serverjsh.Network.NetworkMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlCommand implements ICommand {

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

                case "execute": {
                    Log.out("SQL запрос: " + nm.getArguments(), 2);
                    try {
                        Base.Execute(nm.getArguments());
                        text = "Запрос выполнен";
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.out(e.toString(), 1);
                        text += e.toString();
                        nm.setError(true);
                    }
                    break;
                }


                case "executequery": {
                    ResultSet rs = null;
                    Log.out("SQL запрос: " + nm.getArguments(), 2);
                    try {
                        rs = Base.ExecuteQuery(nm.getArguments());
                        text = "";
                        // Количество колонок в результирующем запросе
                        int columns = rs.getMetaData().getColumnCount();
                        // Перебор строк с данными
                        while (rs.next()) {
                            for (int i = 1; i <= columns; i++) {
                                text += (rs.getString(i) + "\t");
                            }
                            text += "\n";
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.out(e.toString(), 1);
                        text += e.toString();
                        nm.setError(true);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                    break;
                }


                case "show": { // выводит структуру базы
                    ResultSet resultSet = null;
                    ArrayList<String> tables = new ArrayList<>();
                    try {
                        resultSet = Base.ExecuteQuery("SHOW TABLES");
                        text = "\n";
                        // Перебор строк с данными
                        while (resultSet.next()) {
                            tables.add(resultSet.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.out(e.toString(), 1);
                        text += e.toString();
                        nm.setError(true);
                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                    }
                    try {
                        for (String table : tables) {
                            text += table +
                                    "\n----------------------------------------\n";
                            resultSet = Base.ExecuteQuery("SHOW COLUMNS FROM " + table);
                            // Количество колонок в результирующем запросе
                            int columns = resultSet.getMetaData().getColumnCount();
                            // Перебор строк с данными
                            int num = 1;
                            while (resultSet.next()) {
                                text += num + ".\t";
                                for (int i = 1; i <= columns; i++) {
                                    text += (resultSet.getString(i) + "\t");
                                }
                                text += "\n";
                                num++;
                            }
                            text += "\n";
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.out(e.toString(), 1);
                        text += e.toString();
                        nm.setError(true);
                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                    }
                    break;
                }


                case "showall": { // выводит структуру базы
                    ResultSet resultSet = null;
                    ArrayList<String> tables = new ArrayList<>();
                    try {
                        resultSet = Base.ExecuteQuery("SHOW TABLES");
                        text = "\n";
                        // Перебор строк с данными
                        while (resultSet.next()) {
                            tables.add(resultSet.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.out(e.toString(), 1);
                        text += e.toString();
                        nm.setError(true);
                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                    }
                    try {
                        for (String table : tables) {
                            text += table +
                                    "\n----------------------------------------\n";
                            resultSet = Base.ExecuteQuery("SHOW COLUMNS FROM " + table);
                            // Количество колонок в результирующем запросе
                            int columns = resultSet.getMetaData().getColumnCount();
                            // Перебор строк с данными
                            int num = 1;
                            while (resultSet.next()) {
                                text += num + ".\t";
                                for (int i = 1; i <= columns; i++) {
                                    text += (resultSet.getString(i) + "\t");
                                }
                                text += "\n";
                                num++;
                            }
                            text += "\n";

                            try {
                                resultSet = Base.ExecuteQuery("SELECT * FROM " + table);
                                // Количество колонок в результирующем запросе
                                columns = resultSet.getMetaData().getColumnCount();
                                // Перебор строк с данными
                                int numLine = 1;
                                while (resultSet.next()) {
                                    text += numLine + ".\t";
                                    for (int i = 1; i <= columns; i++) {
                                        text += (resultSet.getString(i) + "\t");
                                    }
                                    text += "\n";
                                    numLine++;
                                }
                                text += "\n";

                            } catch (SQLException e) {
                                e.printStackTrace();
                                Log.out(e.toString(), 1);
                                text += e.toString();
                                nm.setError(true);
                            } finally {
                                if (resultSet != null) {
                                    resultSet.close();
                                }
                            }

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.out(e.toString(), 1);
                        text += e.toString();
                        nm.setError(true);
                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
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
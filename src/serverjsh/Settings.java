package serverjsh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для работы с настройками программы
 *
 * @author Andrey Bochkarev
 * @version 1.0
 */
public class Settings {

    /**
     * Поле - Имя файла с настройками
     */
    private final static String SETTINGS_FILE_NAME = "settings.ini";


    /**
     * Возвращает значение настройки. Если файл с настройками не найден,
     * либо в файле с настроками нет необходимого параметра, то возвращается значение по умолчанию
     * (второй аргумент).
     *
     * @param propName     Имя параметра
     * @param defaultValue Значение параметра по умолчанию
     */
    public static String GetProperty(String propName, String defaultValue) {
        String request;
        final Properties props = new Properties();
        try {
            //пытаемся прочитать настройки из файла
            FileInputStream input = new FileInputStream(SETTINGS_FILE_NAME);
            props.load(input);
            input.close();
            request = props.getProperty(propName);
            if (request == null) //если настройки нет в файле - возвращаем значение по умолчанию
                request = defaultValue;
        } catch (Exception e) {
            //Если файла с настройками нет - возвращаем значение по умолчанию
            request = defaultValue;
        }

        return request;
    }


    /**
     * Записывает в файл значение настройки
     *
     * @param propName Имя параметра
     * @param value Значение параметра
     */
    public static void SetProperty(String propName, String value) {
        final Properties props = new Properties();
        props.setProperty(propName, value);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(SETTINGS_FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            props.store(output, "Saved settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

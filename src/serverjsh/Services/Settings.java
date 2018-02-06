package serverjsh.Services;

import java.io.*;
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
     * Загружает настройки из файла. Если файл не найден - возвращает null;
     */
    private static Properties LoadPropertiesFromFile() {
        Properties properties = new Properties();
        try {
            //пытаемся прочитать настройки из файла
            FileInputStream input = new FileInputStream(SETTINGS_FILE_NAME);
            properties.load(input);
            input.close();
        } catch (Exception e) {
            properties = null;
        }
        return properties;
    }

    public static String getSettingsFileName() {
        return SETTINGS_FILE_NAME;
    }

    /**
     * Сохраняет настройки в файл. Если файл не найден - возвращает null;
     */
    private static boolean SavePropertiesToFile(Properties properties)  {
        FileWriter fileWriter = null;
        boolean result = false;
        try {
            fileWriter = new FileWriter(SETTINGS_FILE_NAME, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert fileWriter != null;
            properties.store(fileWriter, "Updated");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


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
        Properties props = LoadPropertiesFromFile();
       if (props != null) {
            request = props.getProperty(propName);
            if (request == null) //если настройки нет в файле - возвращаем значение по умолчанию
                request = defaultValue;
        } else {
            //Если файла с настройками нет - возвращаем значение по умолчанию
            request = defaultValue;
        }

        return request;
    }


    /**
     * Записывает в файл значение настройки
     *
     * @param propName Имя параметра
     * @param value    Значение параметра
     */
    public static boolean SetProperty(String propName, String value) {
        Properties props = LoadPropertiesFromFile();
        if (props == null) {
            props = new Properties();
        }
        props.setProperty(propName, value);
        return SavePropertiesToFile(props);
    }



    /**
     * Удаляет из файла настройку. Если настройка не найдена возвращает NULL.
     *
     * @param propName Имя параметра
     */
    public static Object Remove(String propName)  {
        Properties props = LoadPropertiesFromFile();
        Object ob = props.remove(propName);
        SavePropertiesToFile(props);
        return ob;
    }
}

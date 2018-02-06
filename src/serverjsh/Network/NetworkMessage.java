package serverjsh.Network;

import com.google.gson.Gson;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс описывающий сетевой пакет данных для обмена между сервером и клиентом. Пересылается
 * в формате JSON. Имеет встроенные методы для конвертации в JSON и обратно.
 *
 * @author Andrey Bochkarev
 * @version 1.0
 */
public class NetworkMessage {

    private String id;
    private String login;
    private String password;
    private String text;
    private Boolean error;
    //регулярка для парсинга строки. Группы: 1.Команда 2.Параметр 3.Аргументы
    final private String mRegexp = "(^[a-zA-Z][a-zA-Z0-9_]*)|(?<=-\\s{0,2})([a-zA-Z0-9_]+)|(?<=\\(\\s{0,3})(.*)(?=\\s{0,3}\\))";


    public void setText(String text) throws MyExceptionOfNetworkMessage {
        if (text == null) {
            throw new MyExceptionOfNetworkMessage("ERROR: An attempt to pass an NULL string", 1);
        }
        if (text.trim().length() == 0) {
            throw new MyExceptionOfNetworkMessage("ERROR: An attempt to pass an empty string", 2);
        }

        this.text = text.trim();

    }

    public void setError(Boolean error) {
        this.error = error;
    }

    private void setId(String id) throws MyExceptionOfNetworkMessage {
        if (id.trim().length() == 0) {
            throw new MyExceptionOfNetworkMessage("ERROR: ID can not be empty", 3);
        }
        this.id = id;
    }

    private void setLogin(String login) throws MyExceptionOfNetworkMessage {
        if ((login.trim().length() == 0)) {
            throw new MyExceptionOfNetworkMessage("ERROR: Login can not be empty", 4);
        }
        this.login = login.trim();
    }

    private void setPassword(String password) throws MyExceptionOfNetworkMessage {
        if ((password.trim().length() == 0)) {
            throw new MyExceptionOfNetworkMessage("ERROR: Password can not be empty", 5);
        }
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getText() {
        return text;
    }

    public Boolean getError() {
        return error;
    }

    /**
     * Конструктор, создает объект класса NetworkMessage, принимает значения полей в формате JSON
     *
     * @param jsonText Текст в формате JSON
     */
    public NetworkMessage(String jsonText) throws MyExceptionOfNetworkMessage {
        fromJson(jsonText);
    }


    /**
     * Конструктор, создает объект класса NetworkMessage.
     *
     * @param login    Логин юзера
     * @param password Пароль юзера
     * @param text     Пересылаемый текст
     * @param error   Признак ошибки. True - ошибка
     */
    public NetworkMessage(String login, String password, String text, Boolean error) throws MyExceptionOfNetworkMessage {
        setId(UUID.randomUUID().toString());
        setLogin(login);
        setPassword(password);
        setText(text);
        setError(error);
    }


    /**
     * Сериализует объект в формат JSON
     *
     * @return возвращает текст в формате JSON
     */
    public String toJson() {
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        return gson.toJson(this);
    }

    /**
     * Метод десериализации объекта из текста формата JSON
     *
     * @param jsonText Текст в формате JSON
     */
    public void fromJson(String jsonText) throws MyExceptionOfNetworkMessage {
        Gson gson = new Gson();
        setId(gson.fromJson(jsonText, NetworkMessage.class).id);
        setLogin(gson.fromJson(jsonText, NetworkMessage.class).login);
        setPassword(gson.fromJson(jsonText, NetworkMessage.class).password);
        setText(gson.fromJson(jsonText, NetworkMessage.class).text);
        setError(gson.fromJson(jsonText, NetworkMessage.class).error);
    }




    /**
     * Извлекает имя команды из текста сетевого пакета
     *
     * @return Имя команды
     *
     */
    public String getCommand() {
        Pattern pattern = Pattern.compile(mRegexp);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if (matcher.group(1) != null)
                return matcher.group(1).toLowerCase().trim();
        }
        return null;
    }

    /**
     * Извлекает список аргументов (разделенных знаком "-") из текста сетевого пакета
     *
     * @return Список, содержащий аргументы
     */
    public String getParameter() {

        Pattern pattern = Pattern.compile(mRegexp);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if (matcher.group(2) != null)
                return matcher.group(2).toLowerCase().trim();
        }
        return null;
    }

    public String getArguments() {
        Pattern pattern = Pattern.compile(mRegexp);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if (matcher.group(3) != null)
                return matcher.group(3).trim();
        }
        return null;
    }


}


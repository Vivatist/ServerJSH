package serverjsh.Services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Класс для обслуживания логирования деятельности программы
 *
 * @author Andrey Bochkarev
 * @version 1.0
 */
public class Log {
    /**
     * ANSI escape коды, для задания цвета вывода
     */
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static Boolean COLORED = false;

    public static void setColored(Boolean COLORED) {
        Log.COLORED = COLORED;
    }

    /**
     * Поле определяющее уровень отображения лога в консоли во время работы сервера
     * 1 - только важные
     * 2 - средний уровень
     * 3 - очень подробный
     */
    private static int VISIBLE_IN_CONSOLE = 1;

    public static void setVisibleInConsole(int priority) {
        VISIBLE_IN_CONSOLE = priority;
    }

    /**
     * Записывает строку в лог, поведение регулируется полями VISIBLE_IN_CONSOLE и COLORED
     *
     * @param str Текст лога
     */
    public static void out(String str, int level) {
        String ColorString = "";
        String ResetColor = "";

        Map<Integer, String> color = new HashMap<>();
        color.put(1, ANSI_BLUE);
        color.put(2, ANSI_GREEN);
        color.put(3, ANSI_CYAN);

        if (level <= VISIBLE_IN_CONSOLE ) {
            if (COLORED) {
                ColorString = color.get(level);
                ResetColor = ANSI_RESET;
            }
            DateFormat dateFormat = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss.SSS]");
            Date date = new Date();
            String logString = dateFormat.format(date) + " " + ColorString + str + ResetColor;
            System.out.println(level + "." + logString);
        }
    }


}

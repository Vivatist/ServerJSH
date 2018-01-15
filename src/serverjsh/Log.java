package serverjsh;

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

    public static void setCOLORED(Boolean COLORED) {
        Log.COLORED = COLORED;
    }

    /**
     * Поле определяющее отображать или нет лог в консоли во время работы сервера
     */
    private static Boolean VISIBLE_IN_CONSOLE = true;

    public static void setVisibleInConsole(Boolean visibleInConsole) {
        VISIBLE_IN_CONSOLE = visibleInConsole;
    }

    /**
     * Записывает строку в лог, повеление регулируется полями VISIBLE_IN_CONSOLE и COLORED
     *
     * @param str Текст лога
     */
    public static void out(String str) {
        String ColorString = "";
        String ResetColor = "";
        if (VISIBLE_IN_CONSOLE = true) {
            if (COLORED == true) {
                ColorString = ANSI_BLUE;
                ResetColor = ANSI_RESET;
            }
            System.out.println(ColorString + str + ResetColor);
        }
    }


}

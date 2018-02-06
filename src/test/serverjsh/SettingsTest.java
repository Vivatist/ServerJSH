package serverjsh;

import org.junit.Test;
import serverjsh.Services.Settings;

import static org.junit.Assert.*;

import java.util.Arrays;

public class SettingsTest {

    private Iterable<Object[]> params = Arrays.asList(new Object[][]{
            {"TestKey1", "Param1"},
            {"TestKey2", "Param2"},
            {"TestKey3", "Param3"}
    });


    // @Ignore
    @Test
    public void setProperty() {
        //Проверяем с набором параметров
        for (Object[] param : params) {
            assertTrue("Save: " + param[0].toString(), Settings.SetProperty(param[0].toString(), param[1].toString()));
        }
        //Очищаем
        for (Object[] param : params) {
            Settings.Remove(param[0].toString());
        }
    }

    // @Ignore
    @Test
    public void remove() {
        //Заполняем
        for (Object[] param : params) {
            Settings.SetProperty(param[0].toString(), param[1].toString());
        }
        //Проверяем с набором параметров
        for (Object[] param : params) {
            assertNotNull("Remove: " + param[0].toString(), Settings.Remove(param[0].toString()));
        }
        assertNull(Settings.Remove("Несуществующий ключ"));
    }


    @Test
    public void getProperty() {
        //Заполняем
        for (Object[] param : params) {
            Settings.SetProperty(param[0].toString(), param[1].toString());
        }
        //Считываем
        for (Object[] param : params) {
            assertEquals(Settings.GetProperty(param[0].toString(), "Default"),param[1].toString());
        }
        //Проверяем дефолтные значения если ключ не найден
        assertEquals(Settings.GetProperty("Несуществующий ключ", "Default"),"Default");
        //Очищаем
        for (Object[] param : params) {
            Settings.Remove(param[0].toString());
        }
    }



}
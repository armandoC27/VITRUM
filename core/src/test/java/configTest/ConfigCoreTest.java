package configTest;

import config.ConfigCore;
import init.InitCore;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/*The following files are used in this class:
 *
 *  1)   C:\Users\<User Name>\.temevi
 *          is used for configuration system
 *
 *  2)   C:\Users\<User Name>\.temevi\default_config.ini
 *          is used for configuration system
 *
 * */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfigCoreTest {

    @Test
    @Order(1)
    public void configOptionNotExistFile() {
        String userDir = System.getProperty("user.home");
        String pluginFolder = userDir + "\\.temevi";

        File config = new File(pluginFolder + "\\default_config.ini");

        config.delete();

        ConfigCore.configOption();

        File file = new File(pluginFolder);
        assertTrue(file.exists()); //controllo che la cartella esista

        //ora controllo anche che i file richiesti esistano
        assertTrue(config.exists());

    }

    @Test
    @Order(2)
    public void initConfigExistFile() {
        String userDir = System.getProperty("user.home");
        String pluginFolder = userDir + "\\.temevi";
        File file = new File(pluginFolder);

        //ora controllo anche che i file richiesti esistano
        File config = new File(pluginFolder + "\\default_config.ini");

        long lastUpdateConfig=config.lastModified();

        //chiamo il metodo e questo, se funziona bene, non deve fare alcuna scrittura su file
        InitCore.initConfig();

        File configNew = new File(pluginFolder + "\\default_config.ini");

        //mi controlla che config non sia stato ricreato
        assertEquals(lastUpdateConfig,configNew.lastModified());

    }

}

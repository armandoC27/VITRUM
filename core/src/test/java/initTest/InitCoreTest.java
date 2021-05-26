package initTest;

import init.InitCore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.File;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InitCoreTest {
    private static String projectFolder;
    private static String projectSDK;
    private static data.TestProjectAnalysis projectAnalysis;
    private static String pathSalvataggio;
    private static String nomeRepo;
    private static String pathComune;

    @BeforeAll
    public static void setUp() {
        pathComune = System.getProperty("user.dir");
        pathComune = pathComune.substring(0, pathComune.length() - 4);
        pathComune += "progettiTest";

        projectFolder = pathComune + "\\jpacman-framework";

        projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        projectAnalysis = new data.TestProjectAnalysis();

        pathSalvataggio = pathComune + "\\fileResults";

        String[] arrayRepo;
        if (projectFolder.contains("/"))
            arrayRepo = projectFolder.split("/");
        else
            arrayRepo = projectFolder.split("\\\\");

        nomeRepo = arrayRepo[arrayRepo.length - 1];

        projectAnalysis.setPluginPath(pathComune + "plugin/lib/libTest");

    }

    @Test
    public void processTestCorrectStructure() {
        int test;
        test = InitCore.process(projectFolder, nomeRepo, projectSDK, projectAnalysis);
        assertEquals(0, test);
    }

    @Test
    public void processTestIncorrectPath() {
        int test;
        test = InitCore.process(pathComune + "\\pathErrato" + "\\jpacman-framework", nomeRepo, projectSDK, projectAnalysis);
        assertEquals(1, test,"With one is not OK.");
    }

    @Test
    public void processTestWithoutTestClasses() {
        int test;
        test = InitCore.process(pathComune + "\\HelloWorld", nomeRepo, projectSDK, projectAnalysis);
        assertEquals(2, test,"With 2 is not OK.");
    }


    @Test
    @Order(1)
    public void initConfigDeleteFiles() {
        String userDir = System.getProperty("user.home");
        String pluginFolder = userDir + "\\.temevi";
        File file = new File(pluginFolder);

        //ora controllo anche che i file richiesti esistano
        File config = new File(pluginFolder + "\\default_config.ini");
        File jacocoProp = new File(pluginFolder + "\\jacoco-agent.properties");

        config.delete();
        jacocoProp.delete();

        InitCore.initConfig();

        assertTrue(file.exists()); //controllo che la cartella esista
        //controllo che il metodo abbia creato i file
        assertTrue(config.exists());
        assertTrue(jacocoProp.exists());
    }

    @Test
    @Order(2)
    public void initConfigExistFiles() {
        String userDir = System.getProperty("user.home");
        String pluginFolder = userDir + "\\.temevi";
        File file = new File(pluginFolder);

        //ora controllo anche che i file richiesti esistano
        File config = new File(pluginFolder + "\\default_config.ini");
        File jacocoProp = new File(pluginFolder + "\\jacoco-agent.properties");

        long lastUpdateConfig=config.lastModified();
        long lastUpdateJacocoProp=jacocoProp.lastModified();

        //chiamo il metodo e questo, se funziona bene, non deve fare alcuna scrittura su file
        InitCore.initConfig();

        File configNew = new File(pluginFolder + "\\default_config.ini");
        File jacocoPropNew = new File(pluginFolder + "\\jacoco-agent.properties");

        //mi controlla che non siano stati ricreati

        assertEquals(lastUpdateConfig,configNew.lastModified());
        assertEquals(lastUpdateJacocoProp,jacocoPropNew.lastModified());

    }
}

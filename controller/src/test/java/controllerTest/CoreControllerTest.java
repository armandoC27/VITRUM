package controllerTest;


import controllerLogic.CoreController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoreControllerTest {
    private static String path;
    private static String destinationPath;
    private static String progDaAnalizzare;
    private static String lib;

    @BeforeAll
    public static void setUp() {
        path = System.getProperty("user.dir");
        path = path.substring(0, path.length() - 10);
        destinationPath = path + "progettiTest\\mainVitrumTest";
        progDaAnalizzare = path + "progettiTest\\jpacman-framework";
        lib = path + "plugin\\lib\\libTest";
    }

    @Test
    public void startVitrumCLITest(){
        File file = new File(destinationPath + "\\resultTest.csv");
        if(file.exists())
            file.delete();

        CoreController cli=new CoreController(false, false, false, 0, 0);
        cli.startVitrumCLI(destinationPath,progDaAnalizzare,lib);

        assertTrue(file.exists());
    }

}

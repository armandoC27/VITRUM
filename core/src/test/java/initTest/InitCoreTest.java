package initTest;

import data.TestClassAnalysis;
import init.InitCore;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;


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
        System.out.println(projectFolder);
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
        System.out.println(pathComune + "\\HelloWorld");
        test = InitCore.process(pathComune + "\\HelloWorld", nomeRepo, projectSDK, projectAnalysis);
        assertEquals(2, test,"With 2 is not OK.");
    }
}

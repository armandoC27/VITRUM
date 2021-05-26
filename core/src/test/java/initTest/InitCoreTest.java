package initTest;

import data.TestClassAnalysis;
import init.InitCore;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class InitCoreTest {
    private static String projectFolder;
    private static String projectSDK;
    private static data.TestProjectAnalysis projectAnalysis;
    private static String pathSalvataggio;
    private static String nomeRepo;


    @BeforeAll
    public static void setUp() {
        projectFolder = "C:\\Users\\saver\\IdeaProjects\\jpacman-framework";
        //plugin
        projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        projectAnalysis = new data.TestProjectAnalysis();
        pathSalvataggio = "C:\\Users\\saver\\Desktop";


        String[] arrayRepo;
        if (projectFolder.contains("/"))
            arrayRepo = projectFolder.split("/");
        else
            arrayRepo = projectFolder.split("\\\\");


        nomeRepo = arrayRepo[arrayRepo.length - 1];

        projectAnalysis.setPluginPath("../../../../../plugin/lib/libTest");

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
        test = InitCore.process("C:\\Users\\pathErrato\\IdeaProjects\\jpacman-framework", nomeRepo, projectSDK, projectAnalysis);
        assertEquals(1, test,"With one is not OK.");
    }

    @Test
    public void processTestWithoutTestClasses() {
        int test;
        test = InitCore.process("C:\\Users\\saver\\IdeaProjects\\VITRUM\\plugin", nomeRepo, projectSDK, projectAnalysis);
        assertEquals(2, test,"With 2 is not OK.");
    }
}

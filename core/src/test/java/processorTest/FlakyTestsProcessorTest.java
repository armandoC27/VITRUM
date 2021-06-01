package processorTest;


import data.FlakyTestsInfo;
import init.InitCore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import processor.FlakyTestsProcessor;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FlakyTestsProcessorTest {
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

        projectAnalysis = new data.TestProjectAnalysis();
        projectAnalysis.setPluginPath(pathComune + "plugin/lib/libTest");

        pathComune += "progettiTest";

        projectFolder = pathComune + "\\jpacman-framework";

        projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        pathSalvataggio = pathComune + "\\fileResults";

        String[] arrayRepo;
        if (projectFolder.contains("/"))
            arrayRepo = projectFolder.split("/");
        else
            arrayRepo = projectFolder.split("\\\\");

        nomeRepo = arrayRepo[arrayRepo.length - 1];

        InitCore.init(projectFolder, nomeRepo, projectSDK, projectAnalysis);
    }

    @Test
    public void calculateCoverage() {
        Vector<FlakyTestsInfo> vectorResult = FlakyTestsProcessor.calculate(projectAnalysis, 10);

        for(FlakyTestsInfo i : vectorResult)
            System.out.println(i.getFlakyMethods());
        assertEquals(6, vectorResult.size());
    }

}

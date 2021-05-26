package storageTest;

import data.TestProjectAnalysis;
import init.InitCore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import processor.CoverageProcessor;
import processor.FlakyTestsProcessor;
import storage.ReportManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ReportManagerTest {
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

        InitCore.process(projectFolder, nomeRepo, projectSDK, projectAnalysis);

        projectAnalysis.setPluginPath(pathComune + "plugin/lib/libTest");
        InitCore.process(projectFolder, nomeRepo, projectSDK, projectAnalysis);

        CoverageProcessor.calculate(projectAnalysis);
        FlakyTestsProcessor.calculate(projectAnalysis,2);

    }

    //TODO: fare un controllo sulla data perché potrebbero essere diverse
    //TODO: manca un pezzo di codice da mettere nel setup il quale è uguale al setUp di mutationCoverage
    @Test
    public void saveReportTest() {
        String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());

        ReportManager.saveReport(projectAnalysis,pathSalvataggio);
        File reportFile = new File(fileName);

        assertTrue(reportFile.exists());

    }

}

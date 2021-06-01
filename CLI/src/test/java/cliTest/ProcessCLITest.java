package cliTest;


import cli.ProcessCLI;
import data.ClassCoverageInfo;
import data.ClassMutationCoverageInfo;
import data.FlakyTestsInfo;
import data.TestProjectAnalysis;
import init.InitCore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProcessCLITest {

    private static ProcessCLI processCLI;
    private static String pathComune,pathSalvataggio;
    private static TestProjectAnalysis projectAnalysis;

    @BeforeAll
    public static void setUp(){
        pathComune = System.getProperty("user.dir");
        pathComune = pathComune.substring(0, pathComune.length() - 3); //questo è diventato 3 poiché siamo nel modulo CLI che è composto soltanto da 3 lettere

        projectAnalysis = new data.TestProjectAnalysis();
        projectAnalysis.setPluginPath(pathComune + "plugin/lib/libTest");

        pathComune += "progettiTest";

        String projectFolder = pathComune + "\\jpacman-framework";

        String projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        pathSalvataggio = pathComune + "\\fileResults";

        String[] arrayRepo;
        if (projectFolder.contains("/"))
            arrayRepo = projectFolder.split("/");
        else
            arrayRepo = projectFolder.split("\\\\");

        String  nomeRepo = arrayRepo[arrayRepo.length - 1];

        processCLI = new ProcessCLI(projectAnalysis);
        InitCore.init(projectFolder, nomeRepo, projectSDK, projectAnalysis);
    }

    @Test
    public void processTestNoFlaky(){
        //controlla che il flaky test sia null poiché ho passato false come parametro
        processCLI.process(pathSalvataggio, false,false,false,3,0);
        FlakyTestsInfo flakyTestsInfo = projectAnalysis.getClassAnalysis().get(0).getFlakyTests();
        assertNull(flakyTestsInfo.getFlakyMethods());
    }

    @Test
    public void processTestNoMutation(){

        processCLI.process(pathSalvataggio, false,false,false,3,10);
        ClassMutationCoverageInfo classMutationCoverageInfo = projectAnalysis.getClassAnalysis().get(0).getMutationCoverage();
        assertEquals(classMutationCoverageInfo.getMutationCoverage(),-1);
    }

    @Test
    public void processTestNoLineCoverage(){

        processCLI.process(pathSalvataggio, false,false,false,3,10);
        ClassCoverageInfo classCoverageInfo = projectAnalysis.getClassAnalysis().get(0).getCoverage();
        assertEquals(classCoverageInfo.getLineCoverage(),-1);
        assertEquals(classCoverageInfo.getBranchCoverage(),-1);
    }

    @Test
    public void processTestFlaky(){
        //controlla che il flaky test sia null poiché ho passato false come parametro
        processCLI.process(pathSalvataggio, true,false,false,3,0);
        FlakyTestsInfo flakyTestsInfo = projectAnalysis.getClassAnalysis().get(0).getFlakyTests();
        assertNotNull(flakyTestsInfo.getFlakyMethods());
    }

    @Test
    public void processTestMutation(){

        processCLI.process(pathSalvataggio, false,true,false,3,10);
        ClassMutationCoverageInfo classMutationCoverageInfo = projectAnalysis.getClassAnalysis().get(0).getMutationCoverage();
        assertNotEquals(classMutationCoverageInfo.getMutationCoverage(),-1);
    }

    @Test
    public void processTestLineCoverage(){

        processCLI.process(pathSalvataggio, false,false,true,3,10);
        ClassCoverageInfo classCoverageInfo = projectAnalysis.getClassAnalysis().get(0).getCoverage();
        assertNotEquals(classCoverageInfo.getLineCoverage(),-1);
        assertNotEquals(classCoverageInfo.getBranchCoverage(),-1);
    }




}

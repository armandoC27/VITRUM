package processorTest;


import data.ClassCoverageInfo;
import init.InitCore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import processor.CoverageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static processor.CoverageProcessor.calculate;


public class CoverageProcessorTest {
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
    }

    @Test
    public void calculateCoverage() {
        Vector<ClassCoverageInfo> vectorResult = CoverageProcessor.calculate(projectAnalysis);

        ArrayList<Double> vectorAspected = new ArrayList<>();
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);
        vectorAspected.add(0.04);
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);

        int index=0;
        for(ClassCoverageInfo i : vectorResult){
            Double resCoverege = i.getBranchCoverage();
            assertEquals(resCoverege, vectorAspected.get(index++));
        }
    }

    @Test
    public void calculateCoverageError() {
        Vector<ClassCoverageInfo> vectorResult = CoverageProcessor.calculate(projectAnalysis);

        ArrayList<Double> vectorAspected = new ArrayList<>();
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);
        vectorAspected.add(0.0);

        int index=0;
        ArrayList<Double> vectorObtained = new ArrayList<>();
        for(ClassCoverageInfo i : vectorResult){
            vectorObtained.add(i.getBranchCoverage());
        }

        assertNotEquals(vectorAspected, vectorObtained);
    }

}

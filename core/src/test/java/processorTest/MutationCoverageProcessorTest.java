package processorTest;


import data.*;
import init.InitCore;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import processor.CoverageProcessor;
import processor.MutationCoverageProcessor;
import processor.SmellynessProcessor;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class MutationCoverageProcessorTest {
    private static String projectFolder;
    private static String projectSDK;
    private static data.TestProjectAnalysis projectAnalysis;
    private static String nomeRepo;
    private static String pathComune;
    private static ClassBean testSuite;
    private static ClassBean productionClass;

    @BeforeAll
    public static void setUp() {
        pathComune = System.getProperty("user.dir");
        pathComune = pathComune.substring(0, pathComune.length() - 4);

        projectAnalysis = new data.TestProjectAnalysis();
        projectAnalysis.setPluginPath(pathComune + "plugin/lib/libTest");

        pathComune += "progettiTest";

        projectFolder = pathComune + "\\jpacman-framework";

        projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        String[] arrayRepo;
        if (projectFolder.contains("/"))
            arrayRepo = projectFolder.split("/");
        else
            arrayRepo = projectFolder.split("\\\\");

        nomeRepo = arrayRepo[arrayRepo.length - 1];

        InitCore.process(projectFolder, nomeRepo, projectSDK, projectAnalysis);

        Vector<PackageBean> testPackages = projectAnalysis.getTestPackages();
        Vector<PackageBean> packages = projectAnalysis.getPackages();
        TestMutationUtilities utils = new TestMutationUtilities();
        Vector<TestClassAnalysis> classAnalyses = new Vector<>();
        ArrayList<ClassBean> classes = utils.getClasses(packages);

        productionClass = classes.get(0);
        testSuite = utils.getTestClassBy(productionClass.getName(), testPackages);
        if (testSuite != null) {
            TestClassAnalysis analysis = new TestClassAnalysis();
            analysis.setName(testSuite.getName());
            analysis.setBelongingPackage(testSuite.getBelongingPackage());
            analysis.setProductionClass(productionClass.getBelongingPackage() + "." + productionClass.getName());
            int loc = CKMetrics.getLOC(testSuite);
            int nom = CKMetrics.getNOM(testSuite);
            int wmc = CKMetrics.getWMC(testSuite);
            int rfc = CKMetrics.getRFC(testSuite);
            ClassCKInfo classInfo = new ClassCKInfo(loc, rfc, nom, wmc);
            projectAnalysis.setLoc(projectAnalysis.getLoc() + loc);
            projectAnalysis.setNom(projectAnalysis.getNom() + nom);
            projectAnalysis.setTestClassesNumber(projectAnalysis.getTestClassesNumber() + 1);
//            analysis.setCkMetrics(classInfo);
//            analysis.setSmells(SmellynessProcessor.calculate(testSuite, productionClass, packages, projectAnalysis));
//            analysis.setCoverage(new ClassCoverageInfo());
//            analysis.setMutationCoverage(new ClassMutationCoverageInfo());
//            analysis.setFlakyTests(new FlakyTestsInfo());
//            classAnalyses.add(analysis);
        }

        projectAnalysis.setClassAnalysis(classAnalyses);

    }

    @Test
    public void calculateMutation() {
        ClassMutationCoverageInfo classMutationCoverageInfo = MutationCoverageProcessor.calculate(testSuite, productionClass, projectAnalysis, 10);

        System.err.println(classMutationCoverageInfo.getMutationCoverage());

        assertEquals(1,1);
    }

}


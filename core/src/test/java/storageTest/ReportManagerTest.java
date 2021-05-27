package storageTest;

import data.*;
import init.InitCore;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import processor.CoverageProcessor;
import processor.FlakyTestsProcessor;
import processor.MutationCoverageProcessor;
import processor.SmellynessProcessor;
import storage.ReportManager;
import utils.VectorFind;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

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

        Vector<PackageBean> testPackages = projectAnalysis.getTestPackages();
        Vector<PackageBean> packages = projectAnalysis.getPackages();
        TestMutationUtilities utils = new TestMutationUtilities();
        Vector<TestClassAnalysis> classAnalyses = new Vector<>();
        ArrayList<ClassBean> classes = utils.getClasses(packages);
        for (ClassBean prodClass : classes) {
            ClassBean testSuite = utils.getTestClassBy(prodClass.getName(), testPackages);
            if (testSuite != null) {
                TestClassAnalysis analysis = new TestClassAnalysis();
                analysis.setName(testSuite.getName());
                analysis.setBelongingPackage(testSuite.getBelongingPackage());
                analysis.setProductionClass(prodClass.getBelongingPackage() + "." + prodClass.getName());
                int loc = CKMetrics.getLOC(testSuite);
                int nom = CKMetrics.getNOM(testSuite);
                int wmc = CKMetrics.getWMC(testSuite);
                int rfc = CKMetrics.getRFC(testSuite);
                ClassCKInfo classInfo = new ClassCKInfo(loc, rfc, nom, wmc);
                projectAnalysis.setLoc(projectAnalysis.getLoc() + loc);
                projectAnalysis.setNom(projectAnalysis.getNom() + nom);
                projectAnalysis.setTestClassesNumber(projectAnalysis.getTestClassesNumber() + 1);
                analysis.setCkMetrics(classInfo);
                analysis.setSmells(SmellynessProcessor.calculate(testSuite, prodClass, packages, projectAnalysis));
                analysis.setCoverage(new ClassCoverageInfo());
                analysis.setMutationCoverage(new ClassMutationCoverageInfo());
                analysis.setFlakyTests(new FlakyTestsInfo());
                classAnalyses.add(analysis);
            }
        }
        projectAnalysis.setClassAnalysis(classAnalyses);

    }

    //TODO: fare un controllo sulla data perché potrebbero essere diverse
    //TODO: manca un pezzo di codice da mettere nel setup il quale è uguale al setUp di mutationCoverage
    @Test
    public void saveReportTest() {
        String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
        File fileResult = new File(pathSalvataggio + "/" + fileName);
        if (fileResult.exists())
            fileResult.delete();

        File resultFilesBefore = new File(pathSalvataggio);

        File[] spaceBefore = resultFilesBefore.listFiles();

        ReportManager.saveReport(projectAnalysis, pathSalvataggio);

        File resultFilesAfter = new File(pathSalvataggio);

        File[] spaceAfter = resultFilesAfter.listFiles();

        System.err.println(spaceAfter.length + "  ===  " + spaceBefore.length);
        assertTrue(spaceAfter.length > spaceBefore.length);

    }

}

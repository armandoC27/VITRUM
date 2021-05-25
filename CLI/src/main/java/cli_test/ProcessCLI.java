package cli_test;

import data.*;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import org.apache.commons.io.FileUtils;
import processor.CoverageProcessor;
import processor.FlakyTestsProcessor;
import processor.MutationCoverageProcessor;
import processor.SmellynessProcessor;
import storage.ReportManager;
import utils.VectorFind;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class ProcessCLI {
    private TestProjectAnalysis project;

    public ProcessCLI(TestProjectAnalysis project) {
        this.project = project;
    }

    public void process(String pathSalvataggio, boolean isFlaky, boolean isMutation, boolean isLC, int numFlaky, int numMutation) {

        //project.setPluginPath("C:\\Users\\Armando\\VITRUM\\plugin\\build\\idea-sandbox\\plugins\\plugin\\lib");
        //System.out.println(project.getConfigPath());


        Vector<PackageBean> packages = project.getPackages();
        Vector<PackageBean> testPackages = project.getTestPackages();
        TestMutationUtilities utils = new TestMutationUtilities();
        ArrayList<ClassBean> classes = utils.getClasses(packages);
        Vector<ClassCoverageInfo> coverageInfos = null;
        Vector<FlakyTestsInfo> flakyInfos = null;
        Vector<TestClassAnalysis> classAnalyses = new Vector<>();

        if (isLC) {
            String configDir = System.getProperty("user.home") + "\\.temevi";
            coverageInfos = CoverageProcessor.calculate(project);
        }
        if (isFlaky) {
            flakyInfos = FlakyTestsProcessor.calculate(project, numFlaky);
        }
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
                project.setLoc(project.getLoc() + loc);
                project.setNom(project.getNom() + nom);
                project.setTestClassesNumber(project.getTestClassesNumber() + 1);
                analysis.setCkMetrics(classInfo);
                analysis.setSmells(SmellynessProcessor.calculate(testSuite, prodClass, packages, project));
                if (coverageInfos != null) {
                    analysis.setCoverage(VectorFind.findCoverageInfo(coverageInfos, testSuite.getName()));
                } else {
                    analysis.setCoverage(new ClassCoverageInfo());
                }
                if (isMutation) {
                    analysis.setMutationCoverage(MutationCoverageProcessor.calculate(testSuite, prodClass, project, numMutation));
                } else if (!isMutation)
                    analysis.setMutationCoverage(new ClassMutationCoverageInfo());

                if (isFlaky)
                    analysis.setFlakyTests(VectorFind.findFlakyInfo(flakyInfos, testSuite.getName()));
                else
                    analysis.setFlakyTests(new FlakyTestsInfo());
                classAnalyses.add(analysis);
            }
        }

        project.setClassAnalysis(classAnalyses);
        ReportManager.saveReport(project, pathSalvataggio);

        try {
            FileUtils.deleteDirectory(new File(System.getProperty("user.home") + "\\.temevi" + "\\htmlCoverage"));
            FileUtils.forceDelete(new File(System.getProperty("user.home") + "\\.temevi" + "\\coverage.csv"));
            FileUtils.forceDelete(new File(System.getProperty("user.home") + "\\.temevi" + "\\jacoco.exec"));
            //TODO vedere perchè non cancella pitreport
            FileUtils.deleteDirectory(new File(System.getProperty("user.home") + "\\.temevi\\pitreport"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // constraints.fill = GridBagConstraints.HORIZONTAL; // natural height, maximum width


    }


}

package cli_test;

import data.*;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import org.apache.commons.io.FileUtils;
import processor.*;
import storage.ReportManager;
import utils.VectorFind;


import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class ProcessCLI {
    public ProcessCLI(TestProjectAnalysis project){
        this.project= project;
    }
    private TestProjectAnalysis project;
    public  void process() {







        // CheckBox ckMetrics COL1 - ROW1 1[x--]



        // CheckBox FlakyTests COL1 - ROW2 2[x--]



        // Label MutationCoverage Timeout COL2 - ROW3 3[-x-]

        // JSpinner MutationCoverageTimeout COL3 - ROW3 3[--x]
        Long val = 10L;//set your own value, I used to check if it works
        Long min = 5L;
        Long max = 1000L;
        Long step = 1L;



        // CheckBox LineBranchCoverage COL1 - ROW4 4[x--]


        // Button editMetricsThresholds COL2 - ROW6 6[-x-]



        String pluginFolder = System.getProperty("user.home") + "\\.temevi";


        // Button runAnalysis COL3 - ROW6 6[--x]




                        String pluginPath = "C:\\Users\\saver\\IdeaProjects\\VITRUM\\plugin\\build\\idea-sandbox\\plugins\\TestFactorsPlugin\\lib" + "\\TestFactorsPlugin\\lib";
                        project.setPluginPath(pluginPath);
                        Vector<PackageBean> packages = project.getPackages();
                        Vector<PackageBean> testPackages = project.getTestPackages();
                        TestMutationUtilities utils = new TestMutationUtilities();
                        ArrayList<ClassBean> classes = utils.getClasses(packages);
                        Vector<ClassCoverageInfo> coverageInfos = null;
                        Vector<FlakyTestsInfo> flakyInfos = null;
                        Vector<TestClassAnalysis> classAnalyses = new Vector<>();
                        /*boolean ok = true;
                        if (lineBranchCoverage.isSelected() || flakyTests.isSelected() || mutationCoverage.isSelected()) {
                            File bytecode = new File(project.getPath() + "\\out");
                            File mavenBytecode = new File(project.getPath() + "\\target\\classes");
                            File mavenTestcode = new File(project.getPath() + "\\target\\test-classes");
                            if (isMaven && (!mavenBytecode.exists() || !mavenTestcode.exists()))
                                ok = false;
                            else if (!isMaven && !bytecode.exists())
                                ok = false;
                        }
                        if (ok) {*/

                        if (/*lineBranchCoverage.isSelected()*/true) {
                            String configDir = System.getProperty("user.home") + "\\.temevi";
                            coverageInfos = CoverageProcessor.calculate(project);
                        }
                        if (/*flakyTests.isSelected()*/true) {
                            flakyInfos = FlakyTestsProcessor.calculate(project, /*(int) ftExecNumber.getValue()*/10);
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
                                if (/*mutationCoverage.isSelected()*/true) {
                                    analysis.setMutationCoverage(MutationCoverageProcessor.calculate(testSuite, prodClass, project, /*(Long) mcTimeout.getValue())*/10L));
                                } else if (/*!mutationCoverage.isSelected()*/false)
                                    analysis.setMutationCoverage(new ClassMutationCoverageInfo());

                                if (/*flakyTests.isSelected()*/true)
                                    analysis.setFlakyTests(VectorFind.findFlakyInfo(flakyInfos, testSuite.getName()));
                                else
                                    analysis.setFlakyTests(new FlakyTestsInfo());
                                classAnalyses.add(analysis);
                            }
                        }

                        project.setClassAnalysis(classAnalyses);
                        ReportManager.saveReport(project);

        try {
            FileUtils.deleteDirectory(new File(System.getProperty("user.home") + "\\.temevi" + "\\htmlCoverage"));
            FileUtils.forceDelete(new File(System.getProperty("user.home") + "\\.temevi" + "\\coverage.csv"));
            FileUtils.forceDelete(new File(System.getProperty("user.home") + "\\.temevi" + "\\jacoco.exec"));
            FileUtils.deleteDirectory(new File(System.getProperty("user.home") + "\\.temevi\\pitreport"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }







        // constraints.fill = GridBagConstraints.HORIZONTAL; // natural height, maximum width




    }



}

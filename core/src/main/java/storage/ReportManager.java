package storage;

import data.*;
import it.unisa.testSmellDiffusion.testSmellRules.TestSmellMetric;
import it.unisa.testSmellDiffusion.utility.FileUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class ReportManager {
    private static final Logger LOGGER = Logger.getLogger("global");

    //flag per capire se il metodo è chiamato dal modulo CLI, in caso affermativo
    // il report viene salvato al percorso indicato nella variabile static "path"
    private static boolean flagCLI = false;
    private static String path;

    public static void saveReport(TestProjectAnalysis proj, String pathPar){
        path = pathPar;
        flagCLI = true;
        saveReport(proj);
    }

        public static void saveReport(TestProjectAnalysis proj){
       // LOGGER.info("Starting report");
        String fileName = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());
        String outputDir = "";
        if(flagCLI){
            fileName= "resultTest.csv";
            outputDir = path;
            flagCLI = false;
        } else {
            outputDir = proj.getPath() + "\\reports";
        }
            String output = "";
            // da chiedere se va bene toglierla in quanto contiene valori globali che sono derivabili
            // proj.getName() + ";" + proj.getLoc() +";" + proj.getNom() + ";" + proj.getRfc() + ";" + proj.getWmc() + ";" + proj.getTestClassesNumber() + "\n";
            output += "testsuite;production;loc;nom;wmc;rfc;lc;bc;mc;ft;";
           TestSmellsMetrics idList = proj.getClassAnalysis().get(0).getSmells().getMetrics();
        for(TestSmellMetric metric : idList.getArMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getEtMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getItMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getGfMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getSeMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getMgMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getFtoMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getLtMetrics()){
            output+=metric.getId() + ";";
        }
        for(TestSmellMetric metric : idList.getRoMetrics()){
            output+=metric.getId() + ";";
        }
        output=output.substring(0,output.length()-1);
        output+="\n";
            for(TestClassAnalysis info : proj.getClassAnalysis()){
                ClassCKInfo ckInfo = info.getCkMetrics();
                ClassCoverageInfo covInfo = info.getCoverage();
                ClassTestSmellsInfo smellsInfo = info.getSmells();
                TestSmellsMetrics list = smellsInfo.getMetrics();
                ClassMutationCoverageInfo mutationInfo = info.getMutationCoverage();
                FlakyTestsInfo flakyTestsInfo = info.getFlakyTests();
                float numFlaky = -1;
                if(flakyTestsInfo.getFlakyMethods() != null)
                    numFlaky = flakyTestsInfo.getFlakyMethods().size();

              output+=info.getBelongingPackage() + "." + info.getName() + ";" + info.getProductionClass() + ";" + ckInfo.getLoc() + ";" + ckInfo.getNom() + ";" + ckInfo.getWmc() + ";" + ckInfo.getRfc() + ";" +
                        covInfo.getLineCoverage() + ";" + covInfo.getBranchCoverage() + ";" + mutationInfo.getMutationCoverage() + ";"
              + numFlaky + ";";

                for(TestSmellMetric metric : list.getArMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getEtMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getItMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getGfMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getSeMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getMgMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getFtoMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getLtMetrics()){
                    output+=metric.getValue() + ";";
                }
                for(TestSmellMetric metric : list.getRoMetrics()){
                    output+=metric.getValue() + ";";
                }
                output=output.substring(0,output.length()-1);

                output+="\n";
            }
         //   LOGGER.info("SONO QUI 3");
            File out = new File(outputDir);
            out.mkdirs();
            FileUtility.writeFile(output, outputDir + "\\" + fileName);
          //  LOGGER.info("FINITO");
    }
}

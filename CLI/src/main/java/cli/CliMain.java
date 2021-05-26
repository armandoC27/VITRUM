package cli;

import init.InitCore;
import org.apache.commons.cli.*;

public class CliMain {
    public static void main(String args[]) {
        int initRes = InitCore.initConfig();

        Options options = new Options();
        options.addOption("flaky", true, "calculate flaky test");
        options.addOption("mutation", true, "mutation test");
        options.addOption("lc", false, "line coverage test");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("help", options);

        boolean isFlaky = false, isMutation = false, isLC = false;
        int numFlaky = 10, numMutation = 10;

        if(cmd.hasOption("flaky")) {
            isFlaky = true;
            String argFlaky = cmd.getOptionValue("flaky");
            if(argFlaky != null) {
                numFlaky = Integer.parseInt(argFlaky);
            }
        }
        if(cmd.hasOption("mutation")) {
            isMutation = true;
            String argMutation = cmd.getOptionValue("mutation");
            if(argMutation != null) {
                numMutation = Integer.parseInt(argMutation);
            }
        }
        if(cmd.hasOption("lc")) {
            isLC = true;
        }

        //TODO se non agginge opzioni (es. -mutation) allora le librerie non servono
        if(args.length < 3 || args.length > 8){
            System.err.println("Errore con i parametri passati!");
            return;
        }

        String projectFolder = args[0];
        //plugin
        String projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        data.TestProjectAnalysis projectAnalysis = new data.TestProjectAnalysis();
        ProcessCLI processCLI = new ProcessCLI(projectAnalysis);
        String pathSalvataggio = args[1];

        String[] arrayRepo;
        if(args[0].contains("/"))
             arrayRepo = args[0].split("/");
        else
            arrayRepo = args[0].split("\\\\");


        String nomeRepo = arrayRepo[arrayRepo.length-1];

        projectAnalysis.setPluginPath(args[2]);

        int res = InitCore.process(projectFolder, nomeRepo, projectSDK, projectAnalysis);
        switch (res) {
            case 1:
                System.err.println("PROJECT'S FOLDER STRUCTURE IS NOT CORRECT. PLEASE USE MAVEN DIRECTORY LAYOUT.");
                break;
            case 2:
                System.err.println("TESTING SOURCE FILES NOT FOUND");
                break;
            default:
                System.out.println("Vitrum test: " +
                        "-flaky:" + isFlaky + " numEsec:" + numFlaky +
                        " -mutation:" + isMutation + " numEsec:" + numMutation +
                        " -lc: " + isLC);
                processCLI.process(pathSalvataggio, isFlaky, isMutation, isLC, numFlaky, numMutation);
                break;
        }
    }
}

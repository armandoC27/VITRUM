package cli;

import data.TestProjectAnalysis;
import init.InitCore;
import org.apache.commons.cli.*;

public class CliMain {
    public static void main(String args[]) {

        CommandLine cmd= initializeOptions(args);

        InitCore.initConfig();

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
        if(cmd.hasOption("mining")){
            String argMining = cmd.getOptionValue("mining");
            if(argMining==null){
                System.err.println("Per poter effettuare il mining è necessario specificare un percorso di un file contenente le url delle repository da analizzare.");
            }
        }


        //TODO se non agginge opzioni (es. -mutation) allora le librerie non servono
        if(args.length < 3 || args.length > 9){
            System.err.println("Errore con i parametri passati!");
            return;
        }

        String projectFolder = args[0];
        //plugin
        String projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();
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

    private static CommandLine initializeOptions(String[] args) {

        Options options = new Options();
        options.addOption("flaky", true, "calculate flaky test");
        options.addOption("mutation", true, "mutation test");
        options.addOption("lc", false, "line coverage test");
        options.addOption("mining",true,"mining on repository versions");

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("help", options);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cmd;
    }
}

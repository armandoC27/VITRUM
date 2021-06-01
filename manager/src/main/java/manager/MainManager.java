package manager;

import cli.CliMain;
import miningVitrum.MyStudyTest;
import org.apache.commons.cli.*;

public class MainManager {

    private static CommandLine cmd;
    private static String destinationPath;
    private static String librariesPath,projectFolder;

    public static void main(String[] args) {

        cmd= initializeOptions(args);
        destinationPath = args[0];

        if(cmd.hasOption("mining")){
            if(args.length !=3){
                System.err.println("Per effettuare il mining servono 3 parametri: destinaionPath, mining option e file delle repositories!");
                return;
            }
            manageMining();
        }else{
            projectFolder = args[1];
            librariesPath="";

            //se non è mining
            if(args.length < 2 || args.length>8){
                System.err.println("I parametri devono essere: destiationPath,projectFolder,librariesPath e le eventuali option come: -flaky, -mutation, -lc ");
                return;
            }
            manageCLI(args);
        }
    }

    private static void manageMining(){

        String reposFile = cmd.getOptionValue("mining"); //prendo il percorso del file delle repositories
        if(reposFile==null){
            System.err.println("Per poter effettuare il mining è necessario specificare un percorso di un file contenente le url delle repository da analizzare.");
        }
        MyStudyTest.startMining(reposFile,destinationPath);
    }

    private static  void manageCLI(String[] args){
        boolean isFlaky = false, isMutation = false, isLC = false;
        int numFlaky = 10, numMutation = 10;

        if(cmd.hasOption("flaky")) {
            isFlaky = true;

            if(!(args[2].contains("\\") || args[2].contains("/"))){
                System.err.println("L'opzione flaky richiede l'inserimento del path delle librerie");
            }
            String argFlaky = cmd.getOptionValue("flaky");
            if(argFlaky != null) {
                numFlaky = Integer.parseInt(argFlaky);
            }
            librariesPath=args[2];
        }

        if(cmd.hasOption("mutation")) {
            isMutation = true;
            if(!(args[2].contains("\\") || args[2].contains("/"))){
                System.err.println("L'opzione mutation richiede l'inserimento del path delle librerie");
            }
            String argMutation = cmd.getOptionValue("mutation");
            if(argMutation != null) {
                numMutation = Integer.parseInt(argMutation);
            }
            librariesPath=args[2];
        }

        if(cmd.hasOption("lc")) {
            isLC = true;
            if(!(args[2].contains("\\") || args[2].contains("/"))){
                System.err.println("L'opzione lineCoverage richiede l'inserimento del path delle librerie");
            }
            librariesPath=args[2];
        }

        CliMain cli=new CliMain(isFlaky,isMutation,isLC,numFlaky,numMutation);
        cli.startVitrumCLI(destinationPath,projectFolder,librariesPath);
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

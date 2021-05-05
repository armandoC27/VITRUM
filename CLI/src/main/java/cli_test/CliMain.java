package cli_test;

import init.Init;
import org.apache.commons.cli.*;

public class CliMain {
    public static void main(String args[]) {
        int initRes = Init.initConfig();

        Options options = new Options();
        options.addOption("flaky", false, "calculate flaky test");
        options.addOption("mutation", false, "mutation test");
        options.addOption("lc", false, "line coverage test");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("help", options);

        boolean isFlaky, isMutation, isLC;
        if(cmd.hasOption("flaky")) {
            isFlaky = true;
        }
        if(cmd.hasOption("mutation")) {
            isMutation = true;
        }
        if(cmd.hasOption("lc")) {
            isLC = true;
        }

        //TODO controllare esistenza args
        String projectFolder = args[0];
        //plugin
        String projectSDK = System.getProperty("java.home"); //"C:\\Program Files (x86)\\Java\\jdk-11";

        data.TestProjectAnalysis projectAnalysis = new data.TestProjectAnalysis();
        ProcessCLI processCLI = new ProcessCLI(projectAnalysis);
        String pathSalvataggio = args[1];

        int res = Init.process(projectFolder, "jpacman-framework", projectSDK, projectAnalysis);
        switch (res) {
            case 1:
                System.err.println("PROJECT'S FOLDER STRUCTURE IS NOT CORRECT. PLEASE USE MAVEN DIRECTORY LAYOUT.");
                break;
            case 2:
                System.err.println("TESTING SOURCE FILES NOT FOUND");
                break;
            default:
                processCLI.process(pathSalvataggio);
                break;
        }
    }
}

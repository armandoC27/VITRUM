package cli;

import data.TestProjectAnalysis;
import init.InitCore;

public class CliMain {

    private static String projectSDK;
    private boolean isFlaky = false, isMutation = false, isLC = false;
    private int numFlaky, numMutation;

    public CliMain(boolean isFlaky, boolean isMutation, boolean isLC, int numFlaky, int numMutation) {
        this.isFlaky = isFlaky;
        this.isMutation = isMutation;
        this.isLC = isLC;
        this.numFlaky = numFlaky;
        this.numMutation = numMutation;

        projectSDK = System.getProperty("java.home");
    }

    public void startVitrumCLI(String destinationPath,String projectPath,String librariesPath){
        InitCore.initConfig();

        TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();
        ProcessCLI processCLI = new ProcessCLI(projectAnalysis);

        projectAnalysis.setPluginPath(librariesPath);

        String[] arrayRepo;
        if(projectPath.contains("/"))
            arrayRepo = projectPath.split("/");
        else
            arrayRepo = projectPath.split("\\\\");


        String nomeRepo = arrayRepo[arrayRepo.length-1];

        InitCore.init(projectPath, nomeRepo, projectSDK, projectAnalysis);

        processCLI.process(destinationPath, this.isFlaky, this.isMutation, this.isLC, this.numFlaky, this.numMutation);
    }

}

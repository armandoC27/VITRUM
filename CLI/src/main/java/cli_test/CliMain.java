package cli_test;
import init.Init;

public class CliMain {
    public static void main(String args[]){
        /*prova*/

         int initRes = Init.initConfig();



        String projectFolder = "C:\\Users\\saver\\IdeaProjects\\jpacman-framework";
        //plugin
        String projectSDK = "C:\\Program Files (x86)\\Java\\jdk-11";
        data.TestProjectAnalysis projectAnalysis = new data.TestProjectAnalysis();
        ProcessCLI processCLI=new ProcessCLI(projectAnalysis);

        int res= Init.process(projectFolder,"jpacman-framework",projectSDK,projectAnalysis);
        switch (res){
            case 1:

                javax.swing.JOptionPane.showMessageDialog(null, "PROJECT'S FOLDER STRUCTURE IS NOT CORRECT. PLEASE USE MAVEN DIRECTORY LAYOUT.");
                break;
            case 2:
                javax.swing.JOptionPane.showMessageDialog(null, "TESTING SOURCE FILES NOT FOUND");
                break;
            default:
                processCLI.process();
                break;
        }
    }
}

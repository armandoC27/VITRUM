package init;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;


import data.TestProjectAnalysis;
import gui.ConfigUI;
import gui.PluginInitGUI;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;

import javax.swing.*;


public class PluginInit extends AnAction {
    private static final Logger LOGGER = Logger.getInstance("global");


    @Override
    public void actionPerformed(AnActionEvent e) {
        int initRes = Init.initConfig();



        Project proj = e.getData(PlatformDataKeys.PROJECT);
        String projectFolder = proj.getBasePath();
        //plugin
        String projectSDK = ProjectRootManager.getInstance(proj).getProjectSdk().getHomePath();
        TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();

        int res= Init.process(projectFolder,proj.getName(),projectSDK,projectAnalysis);
        switch (res){
            case 1:

                JOptionPane.showMessageDialog(null, "PROJECT'S FOLDER STRUCTURE IS NOT CORRECT. PLEASE USE MAVEN DIRECTORY LAYOUT.");
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "TESTING SOURCE FILES NOT FOUND");
                break;
            default:
                PluginInitGUI initGUI = new PluginInitGUI(projectAnalysis);
                break;
        }
    }


}





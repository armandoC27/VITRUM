package init;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;


import data.TestProjectAnalysis;
import gui.PluginInitGUI;

import javax.swing.*;


public class PluginInit extends AnAction {
    private static final Logger LOGGER = Logger.getInstance("global");


    @Override
    public void actionPerformed(AnActionEvent e) {
        int initRes = InitCore.initConfig();



        Project proj = e.getData(PlatformDataKeys.PROJECT);
        String projectFolder = proj.getBasePath();
        //plugin
        String projectSDK = ProjectRootManager.getInstance(proj).getProjectSdk().getHomePath();
        TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();

        int res= InitCore.init(projectFolder,proj.getName(),projectSDK,projectAnalysis);
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





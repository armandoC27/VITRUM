package config;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import gui.ConfigUI;

import javax.swing.*;

public class PluginConfig extends AnAction {
    private static final Logger LOGGER = Logger.getInstance("global");
    @Override
    public void actionPerformed(AnActionEvent e) {
        String pluginFolder = ConfigCore.configOption();

        JFrame frame = new ConfigUI(pluginFolder);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}

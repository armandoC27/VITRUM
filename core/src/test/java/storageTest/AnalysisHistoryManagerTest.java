package storageTest;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.SelectorResolutionResult;
import storage.AnalysisHistoryManager;

import java.util.ArrayList;

public class AnalysisHistoryManagerTest {

@Test
    public void getStoricValues(){
    String pathComune = System.getProperty("user.dir");
    pathComune = pathComune.substring(0, pathComune.length() - 4);
    String pathReports =pathComune+ "progettiTest\\jpacman-framework\\reports";

    ArrayList<Double> storic = new AnalysisHistoryManager().getStoricValues("nl.tudelft.jpacman.board.BoardTest", "loc", pathReports, 06, 2021);

    System.err.println(storic.get(0));

    }
}

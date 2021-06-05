package storageTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.SelectorResolutionResult;
import storage.AnalysisHistoryManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//in questa classe di test si fa riferimento ai file 999999000000.csv e 999999111111.csv presenti nella cartella reports di jpacman-framework che si trova nella directory del progetto "progettiTest"
public class AnalysisHistoryManagerTest {

    private static String pathReports;

    @BeforeAll
    public static void setUp(){
        String pathComune = System.getProperty("user.dir");
        pathComune = pathComune.substring(0, pathComune.length() - 4);
        pathReports =pathComune+ "progettiTest\\jpacman-framework\\reports";
    }

    @Test
    public void getStoricValues(){
    ArrayList<Double> storic = new AnalysisHistoryManager().getStoricValues("nl.tudelft.jpacman.board.BoardTest", "loc", pathReports, 99, 9999);

    assertEquals(storic.get(0),30);
    assertEquals(storic.get(1),52);

    storic = new AnalysisHistoryManager().getStoricValues("nl.tudelft.jpacman.board.BoardFactoryTest", "loc", pathReports, 99, 9999);
    assertEquals(storic.get(0),62);
    assertEquals(storic.get(1),98);

    storic = new AnalysisHistoryManager().getStoricValues("nl.tudelft.jpacman.board.SquareTest", "loc", pathReports, 99, 9999);
    assertEquals(storic.get(0),43);
    assertEquals(storic.get(1),25);

    }

    @Test
    public void getPreviousLineCoverageTest(){
        double lineCov = new AnalysisHistoryManager().getPreviousLineCoverage("nl.tudelft.jpacman.board.BoardTest", pathReports);
        assertEquals(-1.0,lineCov);

        lineCov = new AnalysisHistoryManager().getPreviousLineCoverage("nl.tudelft.jpacman.sprite.SpriteTest", pathReports);
        assertEquals(-1.0,lineCov);
    }

    @Test
    public void getPreviousBranchCoverageTest(){
        double branchCov = new AnalysisHistoryManager().getPreviousBranchCoverage("nl.tudelft.jpacman.board.BoardTest", pathReports);
        assertEquals(-1.0,branchCov);

        branchCov = new AnalysisHistoryManager().getPreviousBranchCoverage("nl.tudelft.jpacman.sprite.SpriteTest", pathReports);
        assertEquals(-1.0,branchCov);
    }
}

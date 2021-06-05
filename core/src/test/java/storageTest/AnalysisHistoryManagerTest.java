package storageTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.SelectorResolutionResult;
import storage.AnalysisHistoryManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*The following files are used in this class:
 *
 *  1)  VITRUM\progettiTest\jpacman-framework
 *       is used to analyze a project
 *
 *  2)  VITRUM\progettiTest\fileResults
 *       is used to save the results of the operation
 *
 * in this class of tests reference is made to the files 999999999000000.csv and 999999999111111.csv
 * present in the reports folder of jpacman-framework which is located in the project directory "progettiTest"
 *
 * */
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

package cliLogicTest;

import cliLogic.MainVitrum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class MainVitrumTest {
    private static String path;
    private static String destinationPath;
    private static String progDaAnalizzare;
    private static String lib;

    @BeforeAll
    public static void setUp() {
        path = System.getProperty("user.dir");
        path = path.substring(0, path.length() - 3);
        destinationPath = path + "progettiTest\\mainVitrumTest";
        progDaAnalizzare = path + "progettiTest\\jpacman-framework";
        lib = path + "plugin\\lib\\libTest";
    }

    @Test
    public void mainCLINoOptions(){
        File file = new File(destinationPath + "\\resultTest.csv");
        if(file.exists())
            file.delete();

        String args[] = new String[]{
                destinationPath,
                        progDaAnalizzare
//                        lib + " " +
//                        "-flaky " +
//                        "11 " +
//                        "-mutation " +
//                        "11 " +
//                        "-lc"
        };

        MainVitrum.main(args);
        assertTrue(file.exists());
    }

    @Test()
    public void mainCLIFlakyNoArg(){
        File file = new File(destinationPath + "\\resultTest.csv");
        if(file.exists())
            file.delete();

        String args[] = new String[]{
                destinationPath,
                progDaAnalizzare,
                lib,
                "-flaky"
//                        "11 " +
//                        "-mutation " +
//                        "11 " +
//                        "-lc"
        };

        MainVitrum.main(args);
        assertNull(MainVitrum.getCmd());
    }

    @Test
    public void mainCLIMutationNoArg(){
        File file = new File(destinationPath + "\\resultTest.csv");
        if(file.exists())
            file.delete();

        String args[] = new String[]{
                destinationPath,
                progDaAnalizzare,
                lib,
//                "-flaky"
//                        "11 "
                        "-mutation "
//                        "11 "
//                        "-lc"
        };

        MainVitrum.main(args);
        assertNull(MainVitrum.getCmd());
    }

    @Test
    public void mainCLIWithFlaky(){
        File file = new File(destinationPath + "\\resultTest.csv");
        if(file.exists())
            file.delete();

        String args[] = new String[]{
                destinationPath,
                progDaAnalizzare,
                lib,
                "-flaky",
                        "11"
//                "-mutation",
//                        "11",
//                        "-lc"
        };

        MainVitrum.main(args);
        assertTrue(MainVitrum.isIsFlaky());
        assertEquals(11, MainVitrum.getNumFlaky());
    }

    @Test
    public void mainCLIWithMutation(){
        File file = new File(destinationPath + "\\resultTest.csv");
        if(file.exists())
            file.delete();

        String args[] = new String[]{
                destinationPath,
                progDaAnalizzare,
                lib,
//                "-flaky",
//                "11"
                "-mutation",
                        "11"
//                        "-lc"
        };

        MainVitrum.main(args);
        assertTrue(MainVitrum.isIsMutation());
        assertEquals(11, MainVitrum.getNumMutation());
    }

    @Test
    public void mainCLIWithLC(){
        File file = new File(destinationPath + "\\resultTest.csv");
        if(file.exists())
            file.delete();

        String args[] = new String[]{
                destinationPath,
                progDaAnalizzare,
                lib,
//                "-flaky",
//                "11"
//                "-mutation",
//                        "11",
                        "-lc"
        };

        MainVitrum.main(args);
        assertTrue(MainVitrum.isIsLC());
    }

    @Test
    public void mainCLIWithMining(){
        File file = new File(destinationPath + "\\javapoet\\resultTest.csv");
        if(file.exists())
            file.delete();

        String args[] = new String[]{
                destinationPath,
                "-mining",
                path + "mining\\file.txt"
        };

        MainVitrum.main(args);

        System.err.println(file.getAbsolutePath());
        assertTrue(file.exists());
    }


}

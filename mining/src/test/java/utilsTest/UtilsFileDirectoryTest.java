package utilsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import utils.UtilsFileDirectory;

import java.io.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtilsFileDirectoryTest {

    @Test
    public void deleteDirectoryTest(){
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);

        File f = new File(pathCommit+"\\progettiTest\\fileResults\\cartellaDelete");
        if(!f.exists()){
            f.mkdir();
        }else{

            f.delete();
        }
        UtilsFileDirectory.deleteDirectory(f);
        assertTrue(!f.exists());




    }

    @Test
    public void addColoumnsCSVTest_test() throws IOException {
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);

        String line = "https://github.com/square/javapoet2.git";
        String baseOutputDirectory = pathCommit+"\\progettiTest\\fileResults\\risultatiFinali\\";

        UtilsFileDirectory.addColumnsCSVTest(line,baseOutputDirectory);

        File f = new File(pathCommit+"\\progettiTest\\fileResults\\risultatiFinali\\javapoet2\\resultTest.csv");

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

        int i = 0;
        String riga;
        for (riga = br.readLine(); riga != null; riga = br.readLine(), i++) {
            if (i == 0) {
                assertTrue(riga.startsWith("NameTag;HashCommit;Date;testsuite;production;loc;nom;wmc;rfc;ar1;et1;it1;gf1;se1;mg1;ro1"));

            } else if(i==1) {

            }
        }

        br.close();
        f.delete();



    }

    @Test
    public void addColoumnsCSVExtendedTest() throws IOException {
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);


        String baseOutputDirectory = pathCommit+"\\progettiTest\\fileResults\\risultatiFinali\\javapoet3";

        UtilsFileDirectory.addColumnsCSVExtended(baseOutputDirectory);

        File f = new File(pathCommit+"\\progettiTest\\fileResults\\risultatiFinali\\javapoet3\\resultTest.csv");

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

        int i = 0;
        String riga;
        for (riga = br.readLine(); riga != null; riga = br.readLine(), i++) {
            if (i == 0) {
                assertTrue(riga.startsWith("testsuite;production;loc;nom;wmc;rfc;lc;bc;mc;ft;ar1;et1;it1;gf1;se1;mg1;ro1"));

            } else if(i==1) {

            }
        }

        br.close();
        f.delete();

    }

    @Test
    public void srcFolderinPathTest(){
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);
        String pathRepo=pathCommit+"\\progettiTest\\jpacman-framework";

        UtilsFileDirectory.srcFolderInPath(pathRepo);

        assertEquals(1,UtilsFileDirectory.paths.size());
    }


}

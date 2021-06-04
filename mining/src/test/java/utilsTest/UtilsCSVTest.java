package utilsTest;


import com.google.common.collect.Lists;
import miningVitrum.DeveloperVisitorTest;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import utils.UtilsCSV;
import utils.UtilsGit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtilsCSVTest {

    @Test
    public void mergeCSVTest_Test(/*String pathDestination, String nomeFile, String hashCommit,
                                  String pathCommit, String projectName*/) throws IOException {

        String nomeFile = "resultTestMerge.csv";
        String hashCommit = "fileResults";
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);
        pathCommit += "progettiTest";


        String pathDestination = pathCommit+ "\\fileResults\\risultatiFinali\\resultTest.csv";

        int numbDestination = NumberOfCharacters(pathDestination);
        int numbMerge = NumberOfCharacters(pathCommit+"\\fileResults\\resultTestMerge.csv");
        UtilsCSV.mergeCSVTest(pathDestination,nomeFile,hashCommit,pathCommit);
        int numActual = NumberOfCharacters(pathDestination);
        System.out.println(numbDestination+" "+numbMerge+" "+numActual);
        assertEquals(numbDestination+numbMerge-1,numActual );


    }

    @Test
    public void  mergeAllTest_Test(){

        List<String> hashCommits= Lists.newArrayList("cartella1", "cartella2");
        String line = "https://github.com/square/javapoet.git";
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);
        pathCommit += "progettiTest\\fileResults";
        String baseOutputFolder = pathCommit+ "\\risultatiFinali\\";

        int numbDestination = NumberOfCharacters(baseOutputFolder+"javapoet\\resultTest.csv");
        int numMerge1=NumberOfCharacters(pathCommit+"\\cartella1\\resultTest.csv");
        int numMerge2=NumberOfCharacters(pathCommit+"\\cartella2\\resultTest.csv");


        UtilsCSV.mergeAllTest(hashCommits,line,pathCommit,baseOutputFolder);

        int numActual= NumberOfCharacters(baseOutputFolder+"javapoet\\resultTest.csv");


        assertEquals(numbDestination+numMerge1+numMerge2-2,numActual);



    }
    @Test
    public void addInfoToCSVTest_test() throws IOException {

        String support="1;2;3;";
        String pathOutput= System.getProperty("user.dir");
        pathOutput = pathOutput.substring(0, pathOutput.length() - 6);
        pathOutput += "progettiTest\\fileResults\\risultatiFinali";

        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter(pathOutput+"\\resultTest1.csv"));

            final String lineSep = System.getProperty("line.separator");

            output.write("ciao;mondo" + lineSep);
            output.write("hello;world" + lineSep);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UtilsCSV.addInfoToCSVTest(pathOutput+"\\resultTest1.csv",support);



        File f = new File(pathOutput+"\\resultTest1.csv");

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

        int i = 0;
        String line;
        for (line = br.readLine(); line != null; line = br.readLine(), i++) {
            if (i == 0) {
                assertTrue(line.startsWith("NameTag;HashCommit;Date;"));

            } else if(i==1) {
                assertTrue(line.startsWith("1;2;3;"));
            }
        }

        br.close();
        f.delete();




    }

    @Test
    public void mergeModulesTest() throws IOException {

        String nomeFile = "resultTest.csv";
        String hashCommit = "fileResults";
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);
        pathCommit += "progettiTest";


        String pathDestination = pathCommit+ "\\fileResults\\risultatiFinali\\resultTest.csv";

        int numbDestination = NumberOfCharacters(pathDestination);
        int numbMerge = NumberOfCharacters(pathCommit+"\\fileResults\\resultTestMerge.csv");
        UtilsCSV.mergeModules(pathDestination,pathCommit+"\\fileResults\\resultTestMerge.csv");
        int numActual = NumberOfCharacters(pathDestination);
        assertEquals(numbDestination+numbMerge-1,numActual );


    }



    private int NumberOfCharacters(String pathDestination) {


            File file = new File(pathDestination);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] byteArray = new byte[(int)file.length()];
        try {
            fis.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = new String(byteArray);
            String[] stringArray = data.split("\r\n");
            return stringArray.length;

    }

}

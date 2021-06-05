package miningVitrumTest;


import miningVitrum.MyStudyTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import utils.UtilsFileDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*The following files are used in this class:
 *
 *  1)  VITRUM\progettiTest\fileResults\javapoet\resultTest.csv
 *       is used verify the operation of MyStudyTest.startMining(fileWithProjects, destinationPath);
 *
 * */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyStudyTest_Test {

    @Test
    public void startMiningTest() {
        String path = System.getProperty("user.dir");
        String fileWithProjects = path + "\\file.txt";

        path = path.substring(0, path.length() - 6);
        String destinationPath = path + "progettiTest\\fileResults";

        MyStudyTest.startMining(fileWithProjects, destinationPath);

        int numOfRows = NumberOfCharacters(destinationPath + "\\javapoet\\resultTest.csv");

        File fileDir = new File(destinationPath + "\\javapoet");
        if (fileDir.exists())
            UtilsFileDirectory.deleteDirectory(fileDir);

        assertEquals(167, numOfRows);
    }

    private int NumberOfCharacters(String pathDestination) {
        File file = new File(pathDestination);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] byteArray = new byte[(int) file.length()];
        try {
            fis.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = new String(byteArray);
        String[] stringArray = data.split("\r\n");
        return stringArray.length;
    }
}

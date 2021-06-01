package utilsTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import utils.UtilsGit;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtilsGitTest {

    @Test
    public void getNameFromGitURTest(){
        String line = "https://github.com/square/javapoet.git";

        assertEquals("javapoet",UtilsGit.getNameFromGitUrl(line));

    }

    @Test
    public void getUrlTagFromGitUrlTest(){
        String line = "https://github.com/square/javapoet.git";

        assertEquals("https://api.github.com/repos/square/javapoet/tags", UtilsGit.getUrlTagsFromGitUrl(line));



    }

    @Test
    @Order(1)
    public void getTagsTest(){
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);


        String baseOutputDirectory = pathCommit+"\\progettiTest\\fileResults\\risultatiFinali\\";

        String line = "https://github.com/square/javapoet.git";

        HashMap<String,String> hash = UtilsGit.getTags(line,baseOutputDirectory);
        assertEquals(hash.get("ffe1e0d82c97e2be0524dcbb42b12ee4de74df54"),"javawriter-2.5.1");

    }

    @Test
    @Order(2)
    public void getHashTagTest(){
        String pathCommit = System.getProperty("user.dir");
        pathCommit = pathCommit.substring(0, pathCommit.length() - 6);


        String baseOutputDirectory = pathCommit+"\\progettiTest\\fileResults\\risultatiFinali\\";

        String line = "https://github.com/square/javapoet.git";

        List<String> lista = UtilsGit.getHashTag(UtilsGit.getTags(line,baseOutputDirectory));

        assertEquals(lista.get(0),"ffe1e0d82c97e2be0524dcbb42b12ee4de74df54");



    }
}

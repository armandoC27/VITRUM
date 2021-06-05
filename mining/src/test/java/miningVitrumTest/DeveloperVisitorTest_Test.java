package miningVitrumTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import miningVitrum.DeveloperVisitorTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.repodriller.domain.ChangeSet;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Developer;
import org.repodriller.domain.Modification;
import org.repodriller.scm.*;
import utils.UtilsGit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
/*The following files are used in this class:
 *
 *  1)  VITRUM\progettiTest\jpacman-framework
 *       is used to analyze a project
 *
 *  2)  C:\Users\<User Name>\AppData\Local\Temp\output\jpacman-framework\6f703ade639d7d2c9a4152e748ad5696595f5226\resultTest.csv
 *      is used to verify the operation of dev.process(s,c,null)
 * */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeveloperVisitorTest_Test {



    @Test
    public void processTest(){
        Commit c = new Commit("6f703ade639d7d2c9a4152e748ad5696595f5226", null, null,Calendar.getInstance(), null, null, null) ;

        String repo= System.getProperty("user.dir");
        repo = repo.substring(0, repo.length() - 6);
        repo += "progettiTest";
        SCM scm = new SCM() {
            @Override
            public long totalCommits() {
                return 0;
            }

            @Override
            public ChangeSet getHead() {
                return null;
            }

            @Override
            public List<ChangeSet> getChangeSets() {
                return null;
            }

            @Override
            public SCMRepository info() {
                return null;
            }

            @Override
            public Commit getCommit(String id) {
                return null;
            }

            @Override
            public String getCommitFromTag(String tag) {
                return null;
            }

            @Override
            public List<Modification> getDiffBetweenCommits(String priorCommit, String laterCommit) {
                return null;
            }

            @Override
            public String blame(String file, String currentCommit, Integer line) {
                return null;
            }

            @Override
            public List<BlamedLine> blame(String file, String commitToBeBlamed, boolean priorCommit) {
                return null;
            }

            @Override
            public void checkout(String id) {

            }

            @Override
            public void reset() {

            }

            @Override
            public List<RepositoryFile> files() {
                return null;
            }

            @Override
            public SCM clone(Path dest) {
                return null;
            }

            @Override
            public void delete() {

            }

            @Override
            public void setDataToCollect(CollectConfiguration config) {

            }
        };

        SCMRepository s = new SCMRepository(scm,null,repo+"\\jpacman-framework",null,null);


        String baseOutputFolder = repo+"\\baseOutputFolder\\";


        String repoDir = "jpacman-framework";
        String line = "https://github.com/SERG-Delft/jpacman-framework";

        HashMap<String, String> hashTags = UtilsGit.getTags(line, baseOutputFolder);

        DeveloperVisitorTest dev= new DeveloperVisitorTest(repoDir,hashTags);

        dev.process(s,c,null);

        int actual = NumberOfCharacters(System.getProperty("java.io.tmpdir")+"\\output\\jpacman-framework\\6f703ade639d7d2c9a4152e748ad5696595f5226\\resultTest.csv");

        assertEquals(7,actual);
       // C:\Users\saver\AppData\Local\Temp\output\jpacman-framework\6f703ade639d7d2c9a4152e748ad5696595f5226


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

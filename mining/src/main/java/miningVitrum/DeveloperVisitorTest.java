package miningVitrum;

import controllerLogic.CoreController;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;
import utils.UtilsCSV;
import utils.UtilsFileDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DeveloperVisitorTest implements CommitVisitor {

    private List<String> hashCommits;
    private HashMap<String, String> hashmapTag;
    private final String projectName;
    private final File pathOutput;
    private final File pathProject;

    public DeveloperVisitorTest(String projectName, HashMap<String, String> hashmapTag) {
        hashCommits = Collections.synchronizedList(new ArrayList<String>());
        this.hashmapTag = hashmapTag;
        this.projectName = projectName;
        pathOutput = UtilsFileDirectory.createTempDirectory("output");
        pathProject = UtilsFileDirectory.createTempDirectory(pathOutput.getName() + "/" + projectName);
    }

    public List<String> getHashCommit() {
        return hashCommits;
    }

    public String getPathCommit() {
        return pathProject.getPath();
    }

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        try {
            hashCommits.add(commit.getHash());

            repo.getScm().checkout(commit.getHash());

            String tempCsvPathPrefix = Paths.get(pathOutput.getName(), pathProject.getName(), commit.getHash()).toString();
            UtilsFileDirectory.createTempDirectory(tempCsvPathPrefix);
            UtilsFileDirectory.srcFolderInPath(repo.getPath());
            ArrayList<String> pathsModuli = UtilsFileDirectory.paths;
            UtilsFileDirectory.addColumnsCSVExtended(pathOutput.getPath() + "/" + pathProject.getName() + "/" + commit.getHash());

            for (String modulo : pathsModuli) {
                try {
                    System.out.println("#### " + repo.getPath() + " Per il commit " + commit.getHash() + " analizzo il modulo " + modulo);
                    int lastIndex = modulo.lastIndexOf("\\");
                    String moduleName = modulo.substring(lastIndex + 1);
                    String tempCsvModule = Paths.get(pathOutput.getName(), pathProject.getName(), commit.getHash(), moduleName).toString();
                    UtilsFileDirectory.createTempDirectory(tempCsvModule);
//                    Process runtimeProcess = Runtime.getRuntime().exec
//                            ("java -jar Vitrum.jar " + modulo + " " + pathOutput.getPath() + "/" + pathProject.getName() + "/" + commit.getHash()
//                                            + "/" + moduleName,
//                                    null,
//                                    new File("/mining"));

                    new CoreController(false, false, false, 0, 0)
                            .startVitrumCLI(pathOutput.getPath() + "/" + pathProject.getName() + "/" + commit.getHash()
                                    + "/" + moduleName, modulo, "");

                    System.out.println("### VITRuM, progetto " + projectName +
                            " commit " + commit.getHash() + "-> START");
                    //int processComplete = runtimeProcess.waitFor(); // value 0 indicates normal termination

//                    if (processComplete == 0) {
                    System.out.println("## VITRuM, progetto " + projectName +
                            " commit " + commit.getHash() + "-> END");
                    UtilsCSV.mergeModules(pathOutput.getPath() + "/" + pathProject.getName() + "/" + commit.getHash() + "/" + "resultTest.csv",
                            pathOutput.getPath() + "/" + pathProject.getName() + "/" + commit.getHash() + "/" + moduleName + "/" + "resultTest.csv");
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pathsModuli.clear();
            String infoAggiuntive = hashmapTag.get(commit.getHash()) + ";" + commit.getHash() + ";" + commit.getDate().getTime() + ";";
            String resultTest =
                    UtilsFileDirectory.createTempFile(tempCsvPathPrefix, "resultTest.csv").getPath();

            UtilsCSV.addInfoToCSVTest(resultTest, infoAggiuntive);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            repo.getScm().reset();
            boolean delete = pathOutput.delete();

            if (delete) {
                System.out.println("### Directory output eliminata:");
            }

        }
    }

}

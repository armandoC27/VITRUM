package utils;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

public class UtilsCSV {

    protected static final String RESULT_TEST_CSV = "resultTest.csv";

    public static void mergeCSVTest(String pathDestination, String nomeFile, String hashCommit,
                                String pathCommit) throws IOException {

        nomeFile = Paths.get(pathCommit, hashCommit, nomeFile).toString();
        System.out.println("#### merge di " + nomeFile);



        BufferedReader br = null;
        final String lineSep = System.getProperty("line.separator");
        File file = new File(nomeFile);
        Writer output = new BufferedWriter(new FileWriter(pathDestination, true));

        try {
            //names don't conflict or just use different folders
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            int i = 0;

            for (line = br.readLine(); line != null; line = br.readLine(), i++) {
                if (i != 0) {

                    String[] lineArray = line.split(";");
                    line = "";
                    for(int y = 0; y < lineArray.length; y++) {
                        if (!(y == 9 || y == 10 || y == 11 || y ==12)) {
                            line += lineArray[y] + ";";
                        }
                    }
                    line=line.substring(0,line.length()-1);

                    output.append(line).append(lineSep);
                } else {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
            output.close();
        }
    }

    public static void mergeAllTest(List<String> hashCommits, String line, String pathCommit, String baseOutputFolder) {
        for (String hashCommit : hashCommits) {
            try {
                String repoName = UtilsGit.getNameFromGitUrl(line);
                String repoDir = Paths.get(baseOutputFolder + repoName).toString();

                UtilsCSV.mergeCSVTest(Paths.get(repoDir, RESULT_TEST_CSV).toString(), RESULT_TEST_CSV,
                        hashCommit, pathCommit);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addInfoToCSVTest(String nomeFile, String support) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        final String lineSep = System.getProperty("line.separator");
        File file = null, file2 = null;
        try {
            file = new File(nomeFile);
            file2 = new File(nomeFile + "1");//so the
            //names don't conflict or just use different folders
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
            String line;
            String addedColumn;
            int i = 0;
            for (line = br.readLine(); line != null; line = br.readLine(), i++) {
                if (i == 0) {
                    addedColumn = "NameTag;HashCommit;Date;";
                } else {
                    addedColumn = support;
                }
                bw.write(addedColumn + line + lineSep);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
            if(file != null) {
                boolean delete = file.delete();

                if(delete){
                    System.out.println("### File eliminato "+nomeFile);
                }
            }

            File fBuffer = new File(nomeFile);

            if(file2!=null){
                boolean b = file2.renameTo(fBuffer);

                if(b){
                    System.out.println("### File rinominato "+fBuffer);
                }
            }
        }
    }

    public static void mergeModules(String pathDestination, String pathSource) throws IOException {
        BufferedReader br = null;
        final String lineSep = System.getProperty("line.separator");
        File file = new File(pathSource);

        if(!file.exists())
            return;

        Writer output = new BufferedWriter(new FileWriter(pathDestination, true));

        try {
            //names don't conflict or just use different folders
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            int i = 0;

            for (line = br.readLine(); line != null; line = br.readLine(), i++) {
                if (i != 0) {
                    output.append(line).append(lineSep);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
            output.close();
        }
    }
}

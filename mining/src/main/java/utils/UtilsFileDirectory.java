package utils;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UtilsFileDirectory {
    public static ArrayList<String> paths = new ArrayList<>();

    public static boolean deleteDirectory(File f) {
        File[] allContents = f.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return f.delete();
    }


    public static void addColumnsCSVTest(String line, String baseOutputDirectory) {
        Writer output = null;
        try {
            String repoDir = UtilsGit.getNameFromGitUrl(line);
            output = new BufferedWriter(new FileWriter(baseOutputDirectory
                    + repoDir + "/resultTest.csv"));

            final String lineSep = System.getProperty("line.separator");

            output.write("NameTag;HashCommit;Date;testsuite;production;loc;nom;wmc;rfc;ar1;et1;it1;gf1;se1;mg1;ro1" + lineSep);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addColumnsCSVExtended(String baseOutputDirectory) {
        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter(baseOutputDirectory
                    +  "/resultTest.csv"));

            final String lineSep = System.getProperty("line.separator");

            output.write("testsuite;production;loc;nom;wmc;rfc;lc;bc;mc;ft;ar1;et1;it1;gf1;se1;mg1;ro1" + lineSep);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File createTempFile(String prefix, String suffix) {
        File parent = new File(System.getProperty("java.io.tmpdir"));

        File temp = new File(parent, Paths.get(prefix,suffix).toString());

        try {
            boolean newFile = temp.createNewFile();
            if(newFile){
                System.out.println("### File temporaneo creato "+temp.getAbsolutePath());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return temp;
    }


    public static File createTempDirectory(String dirname) {
        File parent = new File(System.getProperty("java.io.tmpdir"));

        File temp = new File(parent, dirname);

        boolean mkdir = temp.mkdir();

        if(mkdir){
            System.out.println("### Cartella temporanea creata "+temp);
        }

        return temp;
    }



    public static void srcFolderInPath(String pathRepo) {
        File root = new File(pathRepo);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                if (f.getName().equals("src")) {
                    System.out.println("Sono in " + f.getAbsolutePath());
                    File[] tests = f.listFiles();
                    for (File test : tests) {
                        if (test.getName().equals("test")) {
                            System.out.println("Sono in " + test.getAbsolutePath());
                            System.out.println("Il path contenente la cartella test è " + f.getParent());
                            paths.add(f.getParent());
                        }
                    }
                }
            }
            srcFolderInPath(f.getAbsolutePath());
        }
    }
}

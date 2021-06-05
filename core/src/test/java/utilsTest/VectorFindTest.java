package utilsTest;

import data.ClassCoverageInfo;
import data.FlakyTestsInfo;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import org.junit.jupiter.api.Test;
import utils.VectorFind;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Vector;

public class VectorFindTest {

    @Test
    public void findCoverageInfo(){

        Vector<ClassCoverageInfo> coverageInfos=  new Vector<>();
        ClassCoverageInfo toCompare1=new ClassCoverageInfo("classCovTest","packageTest","prodCassTest",0.5,0.8,0.0,0.0);
        toCompare1.setName("classCovTest");
        coverageInfos.add(toCompare1);

        ClassCoverageInfo toCompare2=new ClassCoverageInfo();
        toCompare2.setName("classCovTest1");
        coverageInfos.add(toCompare2);
        ClassCoverageInfo toCompare3=new ClassCoverageInfo("classCovTest2","packageTest","prodCassTest2",0.2,0.5,0.0,0.0);
        toCompare3.setName("classCovTest2");
        coverageInfos.add(toCompare3);

        ClassCoverageInfo result=VectorFind.findCoverageInfo(coverageInfos,"classCovTest");

        assertEquals(toCompare1.getLineCoverage(),result.getLineCoverage());
        assertEquals(toCompare1.getBranchCoverage(),result.getBranchCoverage());
        assertEquals(toCompare1.getAssertionDensity(),result.getAssertionDensity());
        assertEquals(toCompare1.getName(),result.getName());
    }

    @Test
    public void findFlakyInfo(){

        ArrayList<MethodBean> arrayToCompare=new ArrayList<>();

        Vector<FlakyTestsInfo> flakyInfos=  new Vector<>();
        FlakyTestsInfo toCompare1=new FlakyTestsInfo();
        toCompare1.setTestSuite("suiteFlakTest");
        toCompare1.setFlakyMethods(arrayToCompare);
        flakyInfos.add(toCompare1);

        FlakyTestsInfo toCompare2=new FlakyTestsInfo();
        toCompare2.setTestSuite("suiteFlakTest2");
        flakyInfos.add(toCompare2);

        FlakyTestsInfo toCompare3=new FlakyTestsInfo();
        toCompare3.setTestSuite("suiteFlakTest3");
        flakyInfos.add(toCompare3);

        FlakyTestsInfo result=VectorFind.findFlakyInfo(flakyInfos,"suiteFlakTest2");

        assertEquals(toCompare2.getTestSuite(),result.getTestSuite());
        assertEquals(toCompare2.getFlakyMethods(),result.getFlakyMethods());
    }
}

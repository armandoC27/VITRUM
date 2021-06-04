package storageTest;

import config.TestSmellMetricThresholds;
import config.TestSmellMetricsThresholdsList;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import storage.ConfigFileManager;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfigFileManagerTest {

    private static ArrayList<TestSmellMetricThresholds> metrics= new ArrayList<>();

    @Test
    @Order(1)
    public void readThresholdsTest(){

        File default_conf = new File(System.getProperty("user.home") + "\\.temevi" + "\\default_config.ini");
        assertTrue(default_conf.exists(),"Per avviare questo test deve essere presente il file di configurazione nella cartella .temevi");

        TestSmellMetricsThresholdsList list = new ConfigFileManager().readThresholds(default_conf);
        metrics=list.getThresholds();

        assertNotNull(list);
        TestSmellMetricThresholds metricThresholds=list.getThresholds().get(0);

        assertEquals("Number of Non-Documented Assertions",metricThresholds.getName());
        assertEquals("Number of assert statements without a description",metricThresholds.getDescription());
        assertEquals("NONDA",metricThresholds.getId());
        assertEquals(1.0,metricThresholds.getDetectionThreshold());
        assertEquals(3.0,metricThresholds.getGuardThreshold());

        assertEquals("ASSERTION_ROULETTE",metricThresholds.getBelongingSmells().get(0).toString());

        metricThresholds=list.getThresholds().get(1);
        assertEquals("Average Production Class Methods Calls",metricThresholds.getName());
        assertEquals("Number of production class' methods calls in the test suite, divided by the number of test methods",metricThresholds.getDescription());
        assertEquals("APCMC",metricThresholds.getId());
        assertEquals(1.0,metricThresholds.getDetectionThreshold());
        assertEquals(3.0,metricThresholds.getGuardThreshold());
        assertEquals("EAGER_TEST",metricThresholds.getBelongingSmells().get(0).toString());

        metricThresholds=list.getThresholds().get(2);
        assertEquals("Methods using External Resources",metricThresholds.getName());
        assertEquals("Number of external resources uses made by test methods",metricThresholds.getDescription());
        assertEquals("MEXR",metricThresholds.getId());
        assertEquals(1.0,metricThresholds.getDetectionThreshold());
        assertEquals(3.0,metricThresholds.getGuardThreshold());
        assertEquals("MYSTERY_GUEST",metricThresholds.getBelongingSmells().get(0).toString());

        metricThresholds=list.getThresholds().get(3);
        assertEquals("Number of EXternal resources Existence Assumptions",metricThresholds.getName());
        assertEquals("Number of assumptions made in test methods about the existence of external resources (e.g. Files, Database)",metricThresholds.getDescription());
        assertEquals("NEXEA",metricThresholds.getId());
        assertEquals(1.0,metricThresholds.getDetectionThreshold());
        assertEquals(3.0,metricThresholds.getGuardThreshold());
        assertEquals("RESOURCE_OPTIMISM",metricThresholds.getBelongingSmells().get(0).toString());

        metricThresholds=list.getThresholds().get(4);
        assertEquals("General Fixture Methods Rate",metricThresholds.getName());
        assertEquals("The rate of test methods not using all the set-up variables defined",metricThresholds.getDescription());
        assertEquals("GFMR",metricThresholds.getId());
        assertEquals(1.0,metricThresholds.getDetectionThreshold());
        assertEquals(3.0,metricThresholds.getGuardThreshold());
        assertEquals("GENERAL_FIXTURE",metricThresholds.getBelongingSmells().get(0).toString());

        metricThresholds=list.getThresholds().get(5);
        assertEquals("Methods Testing Other Objects Rate",metricThresholds.getName());
        assertEquals("The rate of methods testing objects which are different from the production class",metricThresholds.getDescription());
        assertEquals("MTOOR",metricThresholds.getId());
        assertEquals(1.0,metricThresholds.getDetectionThreshold());
        assertEquals(3.0,metricThresholds.getGuardThreshold());
        assertEquals("INDIRECT_TESTING",metricThresholds.getBelongingSmells().get(0).toString());

        metricThresholds=list.getThresholds().get(6);
        assertEquals("toString invocations in Equality Checks",metricThresholds.getName());
        assertEquals("The number of toString invocations in equality checks",metricThresholds.getDescription());
        assertEquals("TSEC",metricThresholds.getId());
        assertEquals(1.0,metricThresholds.getDetectionThreshold());
        assertEquals(3.0,metricThresholds.getGuardThreshold());
        assertEquals("SENSITIVE_EQUALITY",metricThresholds.getBelongingSmells().get(0).toString());

    }

    @Test
    @Order(2)
    public void writeThresholdsTest(){
        String pathComune = System.getProperty("user.dir");
        pathComune = pathComune.substring(0, pathComune.length() - 4);
        String pathFile =pathComune+ "progettiTest\\ConfigTest\\myConfig.ini";
        File myconfig=new File(pathFile);
        myconfig.delete();

        new ConfigFileManager().writeThresholds(new File(pathFile), metrics);
        assertTrue(myconfig.exists());
        assertTrue(myconfig.getTotalSpace()>0);
    }

}

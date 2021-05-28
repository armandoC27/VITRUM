package packageProva;


import initTest.InitCoreTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;
import storageTest.ReportManagerTest;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Suite Demo")
@SelectClasses({ReportManagerTest.class, InitCoreTest.class})
public class JunitTestSuite {
}
package packageProva;

import data.*;
import init.InitCore;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.metrics.CKMetrics;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import processor.SmellynessProcessor;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.assertEquals;


@RunWith(JUnitPlatform.class)
public class TestJunit2 {


    @BeforeAll
    public static void setUp() {

        System.out.println("setUp");

    }

    @Test
    public void testSalutationMessage() {
        System.out.println("Inside testSalutationMessage()");

        assertEquals(1,0);
    }
}
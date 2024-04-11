package com.ykotsiuba;

import com.ykotsiuba.configuration.TestFilesGenerator;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void testApp() {
        TestFilesGenerator generator = new TestFilesGenerator();
        for (int i = 0; i < 10; i++) {
            generator.write(100);
        }
    }
}

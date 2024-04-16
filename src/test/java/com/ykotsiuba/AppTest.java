package com.ykotsiuba;

import com.ykotsiuba.configuration.TestFilesGenerator;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void testApp() {
        TestFilesGenerator generator = new TestFilesGenerator();
        for (int i = 0; i < 10; i++) {
            generator.generateRandomJson(100);
        }
    }
}

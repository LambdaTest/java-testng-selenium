package com.lambdatest;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class TunnelTest extends LamdaBaseTest {

    private Process pythonServer;

    @BeforeMethod
    public void startPythonServer() throws IOException, InterruptedException {
        System.out.println("Starting Python HTTP Server...");

        ProcessBuilder processBuilder = new ProcessBuilder("python3", "-m", "http.server");
        processBuilder.redirectErrorStream(true);
        pythonServer = processBuilder.start();
        Thread.sleep(5000);

        System.out.println("Python HTTP Server started on port 8000");
    }

    @Test
    public void localTest() {
        driver.get("http://localhost:8000");
        Assert.assertTrue(driver.getTitle().contains("Directory listing for /"));
    }

    @AfterMethod()
    public void stopPythonServer() {
        if (pythonServer != null && pythonServer.isAlive()) {
            System.out.println("Stopping Python HTTP Server...");
            pythonServer.destroy();
            System.out.println("Python HTTP Server stopped");
        }
    }
}
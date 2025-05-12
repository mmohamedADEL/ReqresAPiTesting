package Listeners;


import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import utilities.helper.LogUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestListenerClass implements org.testng.ITestListener , IInvokedMethodListener {
    @Override
    public void onTestStart(org.testng.ITestResult result) {
        LogUtil.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(org.testng.ITestResult result) {
        LogUtil.info("Test passed: " + result.getName());
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        LogUtil.info("All tests finished: " + context.getName());
    }
    @Override
    public void onTestSkipped(org.testng.ITestResult result) {
        LogUtil.info("Test skipped: " + result.getName());
    }

    @Override
    public  void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogUtil.error("Test failed: " + testResult.getName());
        }
        try {
            File logFile = LogUtil.getLatestFile(LogUtil.LOGS_PATH);
            assert logFile != null;
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            LogUtil.error(e.getMessage());
        }

    }




}

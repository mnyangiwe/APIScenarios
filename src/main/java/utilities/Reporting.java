package utilities;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporting
{
    private ExtentReports report;
    private ExtentTest currentTest;
    private ExtentHtmlReporter htmlReporter;

    public static String reportName="searchIngredientByName";
    private String reportDirectory;

    public Reporting()
    {
        reportDirectory=System.getProperty("user.dir")+"\\Reports\\"+"\\"+reportName+"\\"+getCurTime()+"\\";
    }
    private String getCurTime()
    {
        Date date =new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        return sdf.format(date);
    }
    private void setUp()
    {
        new File(reportDirectory).mkdirs();
        report=new ExtentReports();
        htmlReporter=new ExtentHtmlReporter(reportDirectory+"extentReport.html");
        report.attachReporter(htmlReporter);
        report.setAnalysisStrategy(AnalysisStrategy.TEST);
        report.flush();
    }

    public void createTest()
    {
        try {
            if(report==null)
            {
                setUp();
            }

            if(currentTest==null || !currentTest.getModel().getName().equals(reportName))
            {
                currentTest=report.createTest(reportName);
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected void displayMessage(String TestState,String msg)
    {
        try {
            int continueWithDisplay=0;
            if(currentTest==null)
            {
                createTest();
            }
                switch (TestState.toUpperCase()) {
                    case "PASSED" -> currentTest.pass(msg);
                    case "FAILED" -> currentTest.fail(msg);
                    case "INFOR" -> currentTest.info(msg);
                   // case "JSON" -> currentTest.pass(MarkupHelper.createCodeBlock(msg, CodeLanguage.JSON));
                    case "JSON" -> {
                        currentTest.pass(MarkupHelper.createCodeBlock(msg, CodeLanguage.JSON));
                    }
                    default -> currentTest.skip(msg);
                }
                report.flush();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

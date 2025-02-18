package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentListenerClass implements ITestListener {

	private ExtentSparkReporter htmlReporter;
	private ExtentReports reports;
	private ExtentTest test;

	@Override
	public void onStart(ITestContext context) {
		configureReport();
	}

	private void configureReport() {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportName = "AutomationTestReport-" + timestamp + ".html";
		String reportPath = System.getProperty("user.dir") + "/Reports/" + reportName;

		// Ensure the Reports directory exists
		File reportsDir = new File(System.getProperty("user.dir") + "/Reports");
		if (!reportsDir.exists()) {
			reportsDir.mkdirs();
		}

		htmlReporter = new ExtentSparkReporter(reportPath);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);

		// Add system information/environment info to reports
		reports.setSystemInfo("Machine", "AS-PC-007");
		reports.setSystemInfo("OS", "Windows 10");
		reports.setSystemInfo("Tester name", "Mitra bhanu");

		// Configuration to change look and feel of report
		htmlReporter.config().setDocumentTitle("Extent Listener Report");
		htmlReporter.config().setReportName("Rest Assured Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Name of test method started: " + result.getName());

		// Start capturing console output
		ConsoleOutputCapture.startCapture();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Name of test method successfully executed: " + result.getName());

		test = reports.createTest(result.getName());
		test.log(Status.PASS,
				MarkupHelper.createLabel("Name of the passed test case is: " + result.getName(), ExtentColor.GREEN));

		// Log captured console output
		String consoleOutput = ConsoleOutputCapture.getCapturedOutput();
		if (consoleOutput != null && !consoleOutput.isEmpty()) {
			test.log(Status.PASS, "Console Output: <pre>" + consoleOutput + "</pre>");
		}

		// Stop capturing console output
		ConsoleOutputCapture.stopCapture();
		ConsoleOutputCapture.clearCapturedOutput();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Name of test method failed: " + result.getName());

		test = reports.createTest(result.getName()); // Create entry in HTML report
		test.log(Status.FAIL,
				MarkupHelper.createLabel("Name of the failed test case is: " + result.getName(), ExtentColor.RED));

		// Log captured console output
		String consoleOutput = ConsoleOutputCapture.getCapturedOutput();
		if (consoleOutput != null && !consoleOutput.isEmpty()) {
			test.log(Status.FAIL, "Console Output: <pre>" + consoleOutput + "</pre>");
		}

		// Log the exception message
		Throwable exception = result.getThrowable();
		if (exception != null) {
			test.log(Status.FAIL, exception);
		}

		// Stop capturing console output
		ConsoleOutputCapture.stopCapture();
		ConsoleOutputCapture.clearCapturedOutput();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Name of test method skipped: " + result.getName());

		test = reports.createTest(result.getName());
		test.log(Status.SKIP,
				MarkupHelper.createLabel("Name of the skipped test case is: " + result.getName(), ExtentColor.ORANGE));

		// Stop capturing console output
		ConsoleOutputCapture.stopCapture();
		ConsoleOutputCapture.clearCapturedOutput();
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("On Finished method invoked...");
		reports.flush(); // Ensure information is written to the reporter
	}
}

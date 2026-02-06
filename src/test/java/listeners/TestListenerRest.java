package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utility.ExtentReport;

public class TestListenerRest implements ITestListener {
	
	private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();

	private ExtentReports reports;

	@Override

	public void onStart(ITestContext context) {
		ExtentReport extent = new ExtentReport();
		extent.reportSetUp();
		reports = ExtentReport.reports;
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = reports.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));

	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
		test.get().fail(result.getThrowable());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.ORANGE));

	}

	@Override
	public void onFinish(ITestContext context) {

		reports.flush();
	}
	public static ExtentTest getTest() {
		return test.get();
	}

}

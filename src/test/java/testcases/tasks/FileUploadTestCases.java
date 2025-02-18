package testcases.tasks;

import endpoints.TaskEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;

import java.io.File;

public class FileUploadTestCases {
	private String accessToken;
	private String xTenantId;

	@BeforeClass
	public void setupTestData() {
		ConfigReader configReader = new ConfigReader();
		accessToken = configReader.getProperty("accessToken");
		xTenantId = configReader.getProperty("xTenantId");
	}

	@Test(priority = 1)
	public void uploadFilePositiveCase() {
		String taskId = "15345";
		String projectId = "247";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "File upload failed");
	}

	// Without authorization
	@Test(priority = 2)
	public void uploadFileWithoutAuthorization() {
		String taskId = "15345";
		String projectId = "247";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(null, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "File upload should not be allowed without authorization");
	}

	// Invalid project ID: "12345678"
	@Test(priority = 3)
	public void uploadFileInvalidProjectId12345678() {
		String taskId = "15340";
		String projectId = "12345678";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid project ID");
	}

	// Invalid project ID: "-20"
	@Test(priority = 4)
	public void uploadFileInvalidProjectIdMinus20() {
		String taskId = "15340";
		String projectId = "-20";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid project ID");
	}

	// Invalid project ID: "abc"
	@Test(priority = 5)
	public void uploadFileInvalidProjectIdABC() {
		String taskId = "15340";
		String projectId = "abc";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid project ID");
	}

	// Invalid task ID: "12345678"
	@Test(priority = 6)
	public void uploadFileInvalidTaskId12345678() {
		String taskId = "12345678";
		String projectId = "247";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid task ID");
	}

	// Invalid task ID: "-20"
	@Test(priority = 7)
	public void uploadFileInvalidTaskIdMinus20() {
		String taskId = "-20";
		String projectId = "247";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid task ID");
	}

	// Invalid task ID: "abc"
	@Test(priority = 8)
	public void uploadFileInvalidTaskIdABC() {
		String taskId = "abc";
		String projectId = "247";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid task ID");
	}

	// Invalid tenant ID
	@Test(priority = 9)
	public void uploadFileInvalidTenantId() {
		String taskId = "15340";
		String projectId = "247";
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
        File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, "invalid-tenant-id", taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "File upload should not be allowed with invalid tenant ID");
	}

	// Valid project id, invalid task id
	@Test(priority = 10)
	public void uploadFileValidProjectIdInvalidTaskId() {
		String taskId = "12345678"; // Invalid task ID
		String projectId = "247"; // Valid project ID
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid task ID");
	}

	// Invalid project id, valid task id
	@Test(priority = 11)
	public void uploadFileInvalidProjectIdValidTaskId() {
		String taskId = "15340"; // Valid task ID
		String projectId = "12345678"; // Invalid project ID
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "File upload should not be allowed with invalid project ID");
	}

	// Invalid project id, invalid task id
	@Test(priority = 12)
	public void uploadFileInvalidProjectIdInvalidTaskId() {
		String taskId = "12345678"; // Invalid task ID
		String projectId = "12345678"; // Invalid project ID
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = TaskEndPoints.uploadFile(accessToken, xTenantId, taskId, projectId, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404,
				"File upload should not be allowed with invalid project and task IDs");
	}

}

package testcases.projectboards;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class GetProjectBoardByStatusTestCases {
	public String accessToken;
	public String xTenantId;
	public String projectId = "758";
	public int page = 1;
	public int size = 50;
	public boolean getAll = false;

	@BeforeClass
	public void setupTestData() {
		ConfigReader configReader = new ConfigReader();
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	@Test(priority = 1)
	public void verifyGetProjectBoardByStatus() {
		System.out.println("Test Case: verifyGetProjectBoardByStatus - Start");

		Response response = ProjectEndPoints.getProjectBoardByStatus(projectId, page, size, getAll, accessToken,
				xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project board by status");

		System.out.println("Test Case: verifyGetProjectBoardByStatus - End");
	}

	@Test(priority = 2)
	public void verifyGetDoingTasks() {
		System.out.println("Test Case: verifyGetDoingTasks - Start");

		Response response = ProjectEndPoints.getDoingTasks(projectId, page, size, getAll, accessToken, xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve doing tasks");

		System.out.println("Test Case: verifyGetDoingTasks - End");
	}

	@Test(priority = 3)
	public void verifyGetDoneTasks() {
		System.out.println("Test Case: verifyGetDoneTasks - Start");

		Response response = ProjectEndPoints.getDoneTasks(projectId, page, size, getAll, accessToken, xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve done tasks");

		System.out.println("Test Case: verifyGetDoneTasks - End");
	}

	@Test(priority = 4)
	public void verifyGetProjectBoardByStatusWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetProjectBoardByStatusWithInvalidAccessToken - Start");

		Response response = ProjectEndPoints.getProjectBoardByStatus(projectId, page, size, getAll, "invalid_token",
				xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetProjectBoardByStatusWithInvalidAccessToken - End");
	}

	@Test(priority = 5)
	public void verifyGetProjectBoardByStatusWithInvalidTenantId() {
		System.out.println("Test Case: verifyGetProjectBoardByStatusWithInvalidTenantId - Start");

		Response response = ProjectEndPoints.getProjectBoardByStatus(projectId, page, size, getAll, accessToken, "123");

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyGetProjectBoardByStatusWithInvalidTenantId - End");
	}

	@Test(priority = 6)
	public void verifyGetProjectBoardByStatusWithoutAccessToken() {
		System.out.println("Test Case: verifyGetProjectBoardByStatusWithoutAccessToken - Start");

		Response response = ProjectEndPoints.getProjectBoardByStatus(projectId, page, size, getAll, null, xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetProjectBoardByStatusWithoutAccessToken - End");
	}
}

package testcases.projectboards;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class GetProjectBoardTestCases {
	public String accessToken;
	public String xTenantId;
	public String projectId = "756"; // Replace with a valid project ID
	public int page = 1;
	public int size = 10;
	public boolean getAll = true;

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
	public void verifyGetProjectBoard() {
		System.out.println("Test Case: verifyGetProjectBoard - Start");

		Response response = ProjectEndPoints.getProjectBoard(projectId, page, size, getAll, accessToken, xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project board");

		System.out.println("Test Case: verifyGetProjectBoard - End");
	}

	@Test(priority = 2)
	public void verifyGetProjectBoardWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetProjectBoardWithInvalidAccessToken - Start");

		Response response = ProjectEndPoints.getProjectBoard(projectId, page, size, getAll, "invalid_token", xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetProjectBoardWithInvalidAccessToken - End");
	}

	@Test(priority = 3)
	public void verifyGetProjectBoardWithInvalidTenantId() {
		System.out.println("Test Case: verifyGetProjectBoardWithInvalidTenantId - Start");

		Response response = ProjectEndPoints.getProjectBoard(projectId, page, size, getAll, accessToken, "123");

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyGetProjectBoardWithInvalidTenantId - End");
	}

	@Test(priority = 4)
	public void verifyGetProjectBoardWithoutAccessToken() {
		System.out.println("Test Case: verifyGetProjectBoardWithoutAccessToken - Start");

		Response response = ProjectEndPoints.getProjectBoard(projectId, page, size, getAll, null, xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetProjectBoardWithoutAccessToken - End");
	}
}

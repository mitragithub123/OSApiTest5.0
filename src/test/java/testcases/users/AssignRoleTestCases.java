package testcases.users;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.users.Users;
import utilities.ConfigReader;

public class AssignRoleTestCases {
	private String accessToken;
	private ConfigReader configReader;
	private String xTenantId;

	@BeforeClass
	public void setupTestData() {
		// Initialize ConfigReader
		configReader = new ConfigReader();

		// Perform login and retrieve access token
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");

		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Test Case: Successfully assign a role
	@Test(priority = 1)
	public void assignRoleSuccessfully() {
		Users rolePayload = new Users();
		rolePayload.setRoleName("project_user");
		rolePayload.setUserId("d1c0db22-d545-4c36-b18d-5bd20fa6beb4");
		rolePayload.setIsActive(0);

		Response response = UserEndPoints.assignRole(accessToken, rolePayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to assign role");
		// Add more assertions based on the response structure
	}

	// Test Case: Without authorization
	@Test(priority = 2)
	public void assignRoleWithoutAuthorization() {
		Users rolePayload = new Users();
		rolePayload.setRoleName("project_user");
		rolePayload.setUserId("d1c0db22-d545-4c36-b18d-5bd20fa6beb4"); // Get this from users page.
		rolePayload.setIsActive(0);

		Response response = UserEndPoints.assignRole(null, rolePayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing authorization token");
	}

	// Test Case: Invalid tenant id
	@Test(priority = 3)
	public void assignRoleWithInvalidToken() {
		Users rolePayload = new Users();
		rolePayload.setRoleName("project_user");
		rolePayload.setUserId("d1c0db22-d545-4c36-b18d-5bd20fa6beb4");
		rolePayload.setIsActive(0);

		Response response = UserEndPoints.assignRole(accessToken, rolePayload, "invalid");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for invalid tenant id");
	}
}

package testcases.users;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class GetAllRoleTestCases {
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

	// Test Case: Get all Roles successfully
	@Test(priority = 1)
	public void getAllRolesSuccessfully() {
		Response response = UserEndPoints.getAllRole(accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to get all Roles");
	}

	// Test Case: Without authorization
	@Test(priority = 2)
	public void getAllRolesWithoutAuthorization() {
		Response response = UserEndPoints.getAllRole(null, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing authorization token");
	}

	// Test Case: Invalid access token
	@Test(priority = 3)
	public void getAllRolesWithInvalidToken() {
		Response response = UserEndPoints.getAllRole(accessToken, "invalid");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for invalid access token");
	}
}

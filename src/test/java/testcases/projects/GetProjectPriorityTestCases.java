package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.projects.Projects;
import utilities.ConfigReader;

public class GetProjectPriorityTestCases {
	public String accessToken;
	public String xTenantId;
	public Projects projectRequest;

	@BeforeClass
	public void setupTestData() {
		// Initialize ConfigReader
		ConfigReader configReader = new ConfigReader();

		// Perform login and retrieve access token
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);

		// Initialize project request with valid data
		projectRequest = new Projects();
		// Set projectRequest fields as needed for the test
	}

	// Positive case
	@Test(priority = 1)
	public void verifyGetProjectPriorities() {
		System.out.println("Test Case: verifyGetProjectPriorities - Start");

		// Get project priorities using ProjectEndPoints
		Response response = ProjectEndPoints.getProjectPriorities(projectRequest, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project priorities");

		System.out.println("Test Case: verifyGetProjectPriorities - End");
	}

	// Invalid access token
	@Test(priority = 2)
	public void verifyGetProjectPrioritiesWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetProjectPrioritiesWithInvalidAccessToken - Start");

		// Get project priorities using invalid access token
		Response response = ProjectEndPoints.getProjectPriorities(projectRequest, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetProjectPrioritiesWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 3)
	public void verifyGetProjectPrioritiesWithInvalidTenantId() {
		System.out.println("Test Case: verifyGetProjectPrioritiesWithInvalidTenantId - Start");

		// Get project priorities using invalid tenant id
		Response response = ProjectEndPoints.getProjectPriorities(projectRequest, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyGetProjectPrioritiesWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 4)
	public void verifyGetProjectPrioritiesWithoutAccessToken() {
		System.out.println("Test Case: verifyGetProjectPrioritiesWithoutAccessToken - Start");

		// Get project priorities without access token
		Response response = ProjectEndPoints.getProjectPriorities(projectRequest, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetProjectPrioritiesWithoutAccessToken - End");
	}
}

package testcases.projects;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import payload.projects.Projects;
import utilities.ConfigReader;

public class UpdateProjectClientTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "756"; // Assuming the project ID is known and set here
	private int clientId = 83; // Collect from project settings overview

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
	}

	// Positive case: Verify update project client
	@Test(priority = 1)
	public void verifyUpdateProjectClient() {
		Projects payload = new Projects(clientId);

		System.out.println("Test Case: verifyUpdateProjectClient - Start");

		// Update project client using ProjectEndPoints
		Response response = ProjectEndPoints.updateProjectClient(projectId, payload, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update project client");

		System.out.println("Test Case: verifyUpdateProjectClient - End");
	}

	// Negative case: Verify update project client with invalid project ID
	@Test(priority = 2)
	public void verifyUpdateProjectClientWithInvalidProjectId() {
		String invalidProjectId = "12345";
		Projects payload = new Projects(clientId);

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidProjectId - Start");

		// Update project client using invalid project ID
		Response response = ProjectEndPoints.updateProjectClient(invalidProjectId, payload, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Expected failure with invalid project ID: " + invalidProjectId);

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidProjectId - End");
	}

	// Negative case: Verify update project client with invalid project ID: -20
	@Test(priority = 3)
	public void verifyUpdateProjectClientWithNegativeProjectId() {
		String invalidProjectId = "-20";
		Projects payload = new Projects(clientId);

		System.out.println("Test Case: verifyUpdateProjectClientWithNegativeProjectId - Start");

		// Update project client using invalid project ID
		Response response = ProjectEndPoints.updateProjectClient(invalidProjectId, payload, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Expected failure with invalid project ID: " + invalidProjectId);

		System.out.println("Test Case: verifyUpdateProjectClientWithNegativeProjectId - End");
	}

	// Negative case: Verify update project client with invalid project ID: abc
	@Test(priority = 4)
	public void verifyUpdateProjectClientWithInvalidProjectIdABC() {
		String invalidProjectId = "abc";
		Projects payload = new Projects(clientId);

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidProjectIdABC - Start");

		// Update project client using invalid project ID
		Response response = ProjectEndPoints.updateProjectClient(invalidProjectId, payload, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Expected failure with invalid project ID: " + invalidProjectId);

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidProjectIdABC - End");
	}

	// Negative case: Verify update project client with invalid access token
	@Test(priority = 5)
	public void verifyUpdateProjectClientWithInvalidAccessToken() {
		Projects payload = new Projects(clientId);

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidAccessToken - Start");

		// Update project client using invalid access token
		Response response = ProjectEndPoints.updateProjectClient(projectId, payload, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidAccessToken - End");
	}

	// Negative case: Verify update project client with invalid tenant ID
	@Test(priority = 6)
	public void verifyUpdateProjectClientWithInvalidTenantId() {
		Projects payload = new Projects(clientId);

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidTenantId - Start");

		// Update project client using invalid tenant ID
		Response response = ProjectEndPoints.updateProjectClient(projectId, payload, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant ID");

		System.out.println("Test Case: verifyUpdateProjectClientWithInvalidTenantId - End");
	}

	// Negative case: Verify update project client without access token
	@Test(priority = 7)
	public void verifyUpdateProjectClientWithoutAccessToken() {
		Projects payload = new Projects(clientId);

		System.out.println("Test Case: verifyUpdateProjectClientWithoutAccessToken - Start");

		// Update project client without access token
		Response response = ProjectEndPoints.updateProjectClient(projectId, payload, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyUpdateProjectClientWithoutAccessToken - End");
	}

	// Negative case: Verify update project client with invalid client IDs
	@Test(priority = 8)
	public void verifyUpdateProjectClientWithInvalidClientIds() {
		String[] invalidClientIds = { "1234", "-20", "abc" };

		for (String invalidClientId : invalidClientIds) {
			Projects payload = new Projects(Integer.parseInt(invalidClientId));

			System.out.println("Test Case: verifyUpdateProjectClientWithInvalidClientId - Start");
			System.out.println("Testing with invalid client ID: " + invalidClientId);

			// Update project client using invalid client ID
			Response response = ProjectEndPoints.updateProjectClient(projectId, payload, accessToken, xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid client ID: " + invalidClientId);

			System.out.println("Test Case: verifyUpdateProjectClientWithInvalidClientId - End");
		}
	}

}

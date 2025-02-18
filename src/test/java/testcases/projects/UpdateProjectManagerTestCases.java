package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class UpdateProjectManagerTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "758"; // Assuming the project ID is known and set here
	private int managerId = 192; // Get this value from project settings overview by clicking on
									// "members?page=1&size=50&get_all=false" in networks tab.

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

	// Positive case: Update project manager
	@Test(priority = 1)
	public void verifyUpdateProjectManager() {
		System.out.println("Test Case: verifyUpdateProjectManager - Start");

		// Update project manager using ProjectEndPoints
		Response response = ProjectEndPoints.updateProjectManager(projectId, accessToken, xTenantId, managerId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update project manager");

		System.out.println("Test Case: verifyUpdateProjectManager - End");
	}

	// Invalid project ID
	@Test(priority = 2)
	public void verifyUpdateProjectManagerWithInvalidProjectId() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };
		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidProjectId - Start");

			// Update project manager with invalid project ID
			Response response = ProjectEndPoints.updateProjectManager(invalidProjectId, accessToken, xTenantId,
					managerId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidProjectId - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyUpdateProjectManagerWithInvalidAccessToken() {
		System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidAccessToken - Start");

		// Update project manager with invalid access token
		Response response = ProjectEndPoints.updateProjectManager(projectId, "invalid_token", xTenantId, managerId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 4)
	public void verifyUpdateProjectManagerWithInvalidTenantId() {
		System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidTenantId - Start");

		// Update project manager with invalid tenant id
		Response response = ProjectEndPoints.updateProjectManager(projectId, accessToken, "123", managerId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 5)
	public void verifyUpdateProjectManagerWithoutAccessToken() {
		System.out.println("Test Case: verifyUpdateProjectManagerWithoutAccessToken - Start");

		// Update project manager without access token
		Response response = ProjectEndPoints.updateProjectManager(projectId, null, xTenantId, managerId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyUpdateProjectManagerWithoutAccessToken - End");
	}

	// Invalid manager ID
	@Test(priority = 3)
	public void verifyUpdateProjectManagerWithInvalidManagerId() {
		String[] invalidManagerIds = { "12345", "abc", "-20" };
		System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidManagerId - Start");

		for (String invalidManagerId : invalidManagerIds) {
			try {
				int managerIdInt = Integer.parseInt(invalidManagerId); // Convert String to int
				// Update project manager with invalid manager ID
				Response response = ProjectEndPoints.updateProjectManager(projectId, accessToken, xTenantId,
						managerIdInt);

				// Log request and response details
				System.out.println("Response Status Code: " + response.getStatusCode());
				response.then().log().all();

				// Assert that the response status code is not 200
				Assert.assertNotEquals(response.getStatusCode(), 200,
						"Expected failure with invalid manager ID: " + invalidManagerId);
			} catch (NumberFormatException e) {
				// Handle invalid manager ID that cannot be parsed to int (optional)
				System.out.println("Invalid manager ID: " + invalidManagerId + ". Skipping test case.");
			}
		}

		System.out.println("Test Case: verifyUpdateProjectManagerWithInvalidManagerId - End");
	}

}

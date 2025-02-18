package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class DeleteProjectClientTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "75"; // Assuming the project ID is known and set here
	private String clientId = "83"; // Assuming the client ID is known and set here

	@BeforeClass
	public void setupTestData() {
		ConfigReader configReader = new ConfigReader();

		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Positive case: Verify delete project client
	@Test(priority = 1)
	public void verifyDeleteProjectClient() {
		System.out.println("Test Case: verifyDeleteProjectClient - Start");

		Response response = ProjectEndPoints.deleteProjectClient(projectId, clientId, accessToken, xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete project client");

		System.out.println("Test Case: verifyDeleteProjectClient - End");
	}

	// Invalid client ID cases: 12345, -20, abc
	@Test(priority = 2)
	public void verifyDeleteProjectClientWithInvalidClientIds() {
		String[] invalidClientIds = { "12345", "-20", "abc" };

		for (String invalidClientId : invalidClientIds) {
			System.out.println(
					"Test Case: verifyDeleteProjectClientWithInvalidClientId (" + invalidClientId + ") - Start");

			Response response = ProjectEndPoints.deleteProjectClient(projectId, invalidClientId, accessToken,
					xTenantId);

			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid client ID: " + invalidClientId);

			System.out
					.println("Test Case: verifyDeleteProjectClientWithInvalidClientId (" + invalidClientId + ") - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyDeleteProjectClientWithInvalidAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectClientWithInvalidAccessToken - Start");

		Response response = ProjectEndPoints.deleteProjectClient(projectId, clientId, "invalid_token", xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyDeleteProjectClientWithInvalidAccessToken - End");
	}

	// Invalid tenant ID
	@Test(priority = 4)
	public void verifyDeleteProjectClientWithInvalidTenantId() {
		System.out.println("Test Case: verifyDeleteProjectClientWithInvalidTenantId - Start");

		Response response = ProjectEndPoints.deleteProjectClient(projectId, clientId, accessToken, "123");

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant ID");

		System.out.println("Test Case: verifyDeleteProjectClientWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 5)
	public void verifyDeleteProjectClientWithoutAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectClientWithoutAccessToken - Start");

		Response response = ProjectEndPoints.deleteProjectClient(projectId, clientId, null, xTenantId);

		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyDeleteProjectClientWithoutAccessToken - End");
	}

}

package testcases.projects;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import utilities.ConfigReader;

public class GetAllProjectMemberTestCases {

	private String accessToken;
	private String xTenantId;
	private String projectId = "758"; // Example project ID

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

	// Positive case: Retrieve project members
	@Test(priority = 1)
	public void verifyGetAllProjectMembers() {
		System.out.println("Test Case: verifyGetAllProjectMembers - Start");

		// Define pagination parameters
		int page = 1;
		int size = 50;
		boolean getAll = false;

		// Retrieve project members using ProjectEndPoints
		Response response = ProjectEndPoints.getAllProjectMember(page, size, getAll, accessToken, xTenantId, projectId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve project members");

		System.out.println("Test Case: verifyGetAllProjectMembers - End");
	}

	// Invalid project ID
	@Test(priority = 2)
	public void verifyGetAllProjectMembersWithInvalidProjectId() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };

		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyGetAllProjectMembersWithInvalidProjectId - Start");

			// Define pagination parameters
			int page = 1;
			int size = 50;
			boolean getAll = false;

			// Retrieve project members using invalid project ID in URL
			Response response = ProjectEndPoints.getAllProjectMember(page, size, getAll, accessToken, xTenantId,
					invalidProjectId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyGetAllProjectMembersWithInvalidProjectId - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyGetAllProjectMembersWithInvalidAccessToken() {
		System.out.println("Test Case: verifyGetAllProjectMembersWithInvalidAccessToken - Start");

		// Define pagination parameters
		int page = 1;
		int size = 50;
		boolean getAll = false;

		// Retrieve project members using invalid access token
		Response response = ProjectEndPoints.getAllProjectMember(page, size, getAll, "invalid_token", xTenantId,
				projectId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyGetAllProjectMembersWithInvalidAccessToken - End");
	}

	// Access token null
	@Test(priority = 4)
	public void verifyGetAllProjectMembersWithoutAccessToken() {
		System.out.println("Test Case: verifyGetAllProjectMembersWithoutAccessToken - Start");

		// Define pagination parameters
		int page = 1;
		int size = 50;
		boolean getAll = false;

		// Retrieve project members without access token
		Response response = ProjectEndPoints.getAllProjectMember(page, size, getAll, null, xTenantId, projectId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyGetAllProjectMembersWithoutAccessToken - End");
	}

	// Invalid resource ID
	@Test(priority = 2)
	public void verifyGetAllProjectMembersWithInvalidResourceIds() {
		String[] invalidIds = { "resource1", "resource-2", "invalid_id" };

		for (String invalidId : invalidIds) {
			System.out.println("Test Case: verifyGetAllProjectMembersWithInvalidResourceIds - Start");

			// Define pagination parameters
			int page = 1;
			int size = 50;
			boolean getAll = false;

			// Retrieve project members using invalid ID in URL
			Response response = ProjectEndPoints.getAllProjectMember(page, size, getAll, accessToken, xTenantId,
					invalidId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid resource ID: " + invalidId);

			System.out.println("Test Case: verifyGetAllProjectMembersWithInvalidResourceIds - End");
		}
	}

}

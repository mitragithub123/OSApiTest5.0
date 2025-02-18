package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.projects.Projects;
import utilities.ConfigReader;

public class UpdateProjectByIdTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "758"; // Assuming the project ID is known and set here
	private Faker faker;
	private Projects projectRequest;

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

		// Initialize Faker and project request with valid data
		faker = new Faker();
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setProject_description(faker.lorem().sentence());

	}

	// Positive case
	@Test(priority = 1)
	public void verifyUpdateProjectById() {
		System.out.println("Test Case: verifyUpdateProjectById - Start");

		// Update project by ID using ProjectEndPoints
		Response response = ProjectEndPoints.updateProject(projectId, projectRequest, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update project");

		System.out.println("Test Case: verifyUpdateProjectById - End");
	}

	// Invalid project IDs
	@Test(priority = 2)
	public void verifyUpdateProjectByIdWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };
		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyUpdateProjectByIdWithInvalidProjectId - Start");

			// Update project by ID using invalid project ID
			Response response = ProjectEndPoints.updateProject(invalidProjectId, projectRequest, accessToken,
					xTenantId);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyUpdateProjectByIdWithInvalidProjectId - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyUpdateProjectByIdWithInvalidAccessToken() {
		System.out.println("Test Case: verifyUpdateProjectByIdWithInvalidAccessToken - Start");

		// Update project by ID using invalid access token
		Response response = ProjectEndPoints.updateProject(projectId, projectRequest, "invalid_token", xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyUpdateProjectByIdWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 4)
	public void verifyUpdateProjectByIdWithInvalidTenantId() {
		System.out.println("Test Case: verifyUpdateProjectByIdWithInvalidTenantId - Start");

		// Update project by ID using invalid tenant id
		Response response = ProjectEndPoints.updateProject(projectId, projectRequest, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyUpdateProjectByIdWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 5)
	public void verifyUpdateProjectByIdWithoutAccessToken() {
		System.out.println("Test Case: verifyUpdateProjectByIdWithoutAccessToken - Start");

		// Update project by ID without access token
		Response response = ProjectEndPoints.updateProject(projectId, projectRequest, null, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyUpdateProjectByIdWithoutAccessToken - End");
	}
}

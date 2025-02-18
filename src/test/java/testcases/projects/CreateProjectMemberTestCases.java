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

public class CreateProjectMemberTestCases {
	private String accessToken;
	private String xTenantId;
	private Faker faker;

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

		// Initialize Faker
		faker = new Faker();
	}

	// Positive case
	@Test(priority = 1)
	public void verifyCreateProjectMember() {
		System.out.println("Test Case: verifyCreateProjectMember - Start");

		// Create project member with valid resource IDs
		int[] resourceIds = { 192, 193 };
		Projects project = new Projects(758, resourceIds); // Assuming 758 is the projectId

		Response response = ProjectEndPoints.createProjectMember(project, accessToken, xTenantId, "758");
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to create project member");

		System.out.println("Test Case: verifyCreateProjectMember - End");
	}

	// Invalid resource ID
	@Test(priority = 2)
	public void verifyCreateProjectMemberWithInvalidResourceId() {
		int[] invalidResourceIds = { faker.number().numberBetween(-1000, -1), 1244 }; // Adding valid resource ID 1244

		for (int invalidResourceId : invalidResourceIds) {
			System.out.println("Test Case: verifyCreateProjectMemberWithInvalidResourceId - Start");

			// Create project member with invalid resource ID
			int[] resourceIds = { invalidResourceId };
			Projects project = new Projects(758, resourceIds);

			Response response = ProjectEndPoints.createProjectMember(project, accessToken, xTenantId, "758");
			response.then().log().all();

			System.out.println("Response Status Code: " + response.getStatusCode());
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid resource ID: " + invalidResourceId);

			System.out.println("Test Case: verifyCreateProjectMemberWithInvalidResourceId - End");
		}
	}

	// Invalid project IDs
	@Test(priority = 3)
	public void verifyCreateProjectMemberWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };

		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyCreateProjectMemberWithInvalidProjectIds - Start");

			// Create project member with invalid project ID
			int[] resourceIds = { 192, 193 };
			Projects Projects = new Projects(Integer.parseInt(invalidProjectId), resourceIds);

			Response response = ProjectEndPoints.createProjectMember(Projects, accessToken, xTenantId,
					invalidProjectId);
			response.then().log().all();

			System.out.println("Response Status Code: " + response.getStatusCode());
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyCreateProjectMemberWithInvalidProjectIds - End");
		}
	}

	// Invalid access token
	@Test(priority = 4)
	public void verifyCreateProjectMemberWithInvalidAccessToken() {
		System.out.println("Test Case: verifyCreateProjectMemberWithInvalidAccessToken - Start");

		// Create project member with invalid access token
		int[] resourceIds = { 192, 193 };
		Projects Projects = new Projects(758, resourceIds);

		Response response = ProjectEndPoints.createProjectMember(Projects, "invalid_token", xTenantId, accessToken);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyCreateProjectMemberWithInvalidAccessToken - End");
	}

	// Invalid tenant ID
	@Test(priority = 5)
	public void verifyCreateProjectMemberWithInvalidTenantId() {
		System.out.println("Test Case: verifyCreateProjectMemberWithInvalidTenantId - Start");

		// Create project member with invalid tenant ID
		int[] resourceIds = { 192, 193 };
		Projects Projects = new Projects(758, resourceIds);

		Response response = ProjectEndPoints.createProjectMember(Projects, accessToken, "invalid_tenant_id",
				accessToken);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant ID");

		System.out.println("Test Case: verifyCreateProjectMemberWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 6)
	public void verifyCreateProjectMemberWithoutAccessToken() {
		System.out.println("Test Case: verifyCreateProjectMemberWithoutAccessToken - Start");

		// Create project member without access token
		int[] resourceIds = { 192, 193 };
		Projects Projects = new Projects(758, resourceIds);

		Response response = ProjectEndPoints.createProjectMember(Projects, null, xTenantId, accessToken);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyCreateProjectMemberWithoutAccessToken - End");
	}
}

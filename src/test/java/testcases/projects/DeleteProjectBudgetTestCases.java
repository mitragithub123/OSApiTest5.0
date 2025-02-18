package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class DeleteProjectBudgetTestCases {
	public String accessToken;
	public Faker faker;
	public ConfigReader configReader;
	public String xTenantId;
	private String projectId = "758";

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

		// Initialize Faker
		faker = new Faker();
	}

	// Test Case: Positive Case
	@Test(priority = 1)
	public void verifyDeleteProjectBudget() {
		System.out.println("Test Case: verifyDeleteProjectBudget - Start");

		// Delete project budget
		Response response = ProjectEndPoints.deleteProjectBudget(projectId, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete project budget");

		System.out.println("Test Case: verifyDeleteProjectBudget - End");
	}

	// Test Case: Invalid Project IDs
	@Test(priority = 2)
	public void verifyDeleteProjectBudgetWithInvalidProjectIds() {
		System.out.println("Test Case: verifyDeleteProjectBudgetWithInvalidProjectIds - Start");

		// Non-numeric Project ID
		String[] invalidProjectIds = { "12345", "-20", "abc" };

		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Testing with invalid Project ID: " + invalidProjectId);
			Response response = ProjectEndPoints.deleteProjectBudget(invalidProjectId, accessToken, xTenantId);
			response.then().log().all();
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Project budget should not be deleted with invalid Project ID: " + invalidProjectId);
		}

		System.out.println("Test Case: verifyDeleteProjectBudgetWithInvalidProjectIds - End");
	}

	// Test Case: Invalid Access Token
	@Test(priority = 3)
	public void verifyDeleteProjectBudgetWithInvalidAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectBudgetWithInvalidAccessToken - Start");

		// Delete project budget with invalid access token
		Response response = ProjectEndPoints.deleteProjectBudget(projectId, "invalid_token", xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project budget should not be deleted with invalid access token");

		System.out.println("Test Case: verifyDeleteProjectBudgetWithInvalidAccessToken - End");
	}

	// Test Case: Invalid Tenant ID
	@Test(priority = 4)
	public void verifyDeleteProjectBudgetWithInvalidTenantId() {
		System.out.println("Test Case: verifyDeleteProjectBudgetWithInvalidTenantId - Start");

		// Delete project budget with invalid tenant ID
		Response response = ProjectEndPoints.deleteProjectBudget(projectId, accessToken, "invalid_tenant_id");
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project budget should not be deleted with invalid tenant ID");

		System.out.println("Test Case: verifyDeleteProjectBudgetWithInvalidTenantId - End");
	}

	// Test Case: Access Token Null
	@Test(priority = 5)
	public void verifyDeleteProjectBudgetWithoutAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectBudgetWithoutAccessToken - Start");

		// Delete project budget without access token
		Response response = ProjectEndPoints.deleteProjectBudget(projectId, null, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project budget should not be deleted without access token");

		System.out.println("Test Case: verifyDeleteProjectBudgetWithoutAccessToken - End");
	}
}

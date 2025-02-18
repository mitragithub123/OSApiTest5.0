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

public class UpdateProjectBudgetTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "758"; // Assuming the project ID is known and set here
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
	public void verifyUpdateProjectBudget() {
		System.out.println("Test Case: verifyUpdateProjectBudget - Start");

		// Generate payload with Faker data
		String payload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"INR\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10) // Max tolerance
		);

		// Update project budget using ProjectEndPoints
		Response response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, xTenantId, payload);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update project budget");

		System.out.println("Test Case: verifyUpdateProjectBudget - End");
	}

	// Invalid project IDs
	@Test(priority = 2)
	public void verifyUpdateProjectBudgetWithInvalidProjectIds() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };

		String payload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"INR\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10) // Max tolerance
		);

		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidProjectId - Start");

			// Update project budget using invalid project ID in URL
			Response response = ProjectEndPoints.updateProjectBudget(invalidProjectId, accessToken, xTenantId, payload);

			// Log request and response details
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();

			// Assert that the response status code is not 200
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);

			System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidProjectId - End");
		}
	}

	// Invalid access token
	@Test(priority = 3)
	public void verifyUpdateProjectBudgetWithInvalidAccessToken() {
		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidAccessToken - Start");

		String payload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"INR\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10) // Max tolerance
		);

		// Update project budget using invalid access token
		Response response = ProjectEndPoints.updateProjectBudget(projectId, "invalid_token", xTenantId, payload);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");

		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 4)
	public void verifyUpdateProjectBudgetWithInvalidTenantId() {
		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidTenantId - Start");

		String payload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"INR\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10) // Max tolerance
		);

		// Update project budget using invalid tenant id
		Response response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, "123", payload);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidTenantId - End");
	}

	// Access token null
	@Test(priority = 5)
	public void verifyUpdateProjectBudgetWithoutAccessToken() {
		System.out.println("Test Case: verifyUpdateProjectBudgetWithoutAccessToken - Start");

		String payload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"INR\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10) // Max tolerance
		);

		// Update project budget without access token
		Response response = ProjectEndPoints.updateProjectBudget(projectId, null, xTenantId, payload);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyUpdateProjectBudgetWithoutAccessToken - End");
	}

	// Validate that budget, approved_cost, min_tolerance, and max_tolerance should
	// not be negative or strings.
	@Test(priority = 6)
	public void verifyUpdateProjectBudgetWithNegativeAndStringValues() {
		System.out.println("Test Case: verifyUpdateProjectBudgetWithNegativeAndStringValues - Start");

		// Negative values
		String negativeValuesPayload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"INR\"}",
				-100000, // Negative budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				-1000, // Negative approved cost
				-1, // Negative min tolerance
				-2 // Negative max tolerance
		);

		Response response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, xTenantId,
				negativeValuesPayload);
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200, "Project should not be updated with negative values");

		// String values
		String stringValuesPayload = "{\"budget\": \"one hundred thousand\", \"default_rate\": \"zero\", \"cost_approx\": \"zero\", \"approved_cost\": \"one thousand\", \"min_tolerance\": \"one\", \"max_tolerance\": \"two\", \"currency_code\": \"INR\"}";

		response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, xTenantId, stringValuesPayload);
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200, "Project should not be updated with string values");

		System.out.println("Test Case: verifyUpdateProjectBudgetWithNegativeAndStringValues - End");
	}

	// Validate that min_tolerance should not be greater than max_tolerance.
	@Test(priority = 7)
	public void verifyUpdateProjectBudgetWithInvalidToleranceValues() {
		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidToleranceValues - Start");

		String payload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"INR\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				10, // Min tolerance
				5 // Max tolerance (less than min tolerance)
		);

		Response response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, xTenantId, payload);
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be updated with invalid tolerance values");

		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidToleranceValues - End");
	}

	// Validate that currency_code should not be a positive, negative, or invalid
	// string other than the correct code.
	@Test(priority = 8)
	public void verifyUpdateProjectBudgetWithInvalidCurrencyCode() {
		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidCurrencyCode - Start");

		// Positive number as currency code
		String positiveCurrencyCodePayload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"%d\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10), // Max tolerance
				faker.number().numberBetween(1, 100) // Invalid currency code (positive number)
		);

		Response response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, xTenantId,
				positiveCurrencyCodePayload);
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be updated with positive number as currency code");

		// Negative number as currency code
		String negativeCurrencyCodePayload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"%d\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10), // Max tolerance
				-faker.number().numberBetween(1, 100) // Invalid currency code (negative number)
		);

		response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, xTenantId, negativeCurrencyCodePayload);
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be updated with negative number as currency code");

		// Invalid string as currency code
		String invalidStringCurrencyCodePayload = String.format(
				"{\"budget\": %d, \"default_rate\": %d, \"cost_approx\": %d, \"approved_cost\": %d, \"min_tolerance\": %d, \"max_tolerance\": %d, \"currency_code\": \"%s\"}",
				faker.number().numberBetween(100000, 1000000), // Budget
				faker.number().numberBetween(0, 1000), // Default rate
				faker.number().numberBetween(0, 10000), // Cost approx
				faker.number().numberBetween(1000, 10000), // Approved cost
				faker.number().numberBetween(1, 10), // Min tolerance
				faker.number().numberBetween(1, 10), // Max tolerance
				"INVALID" // Invalid currency code (string)
		);

		response = ProjectEndPoints.updateProjectBudget(projectId, accessToken, xTenantId,
				invalidStringCurrencyCodePayload);
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be updated with invalid string as currency code");

		System.out.println("Test Case: verifyUpdateProjectBudgetWithInvalidCurrencyCode - End");
	}

}

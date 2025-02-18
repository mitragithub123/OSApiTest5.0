package testcases.milestones;

import endpoints.LoginEndPoints;
import endpoints.MilestoneEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class GetMilestoneTestCases {

	public String accessToken;
	public Faker faker;
	public ConfigReader configReader;
	public String xTenantId;

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

	// Positive case
	@Test
	public void testGetMilestonePositiveCase() {
		String milestoneId = "396"; // Valid milestone ID
		Response response = MilestoneEndPoints.getMilestone(accessToken, xTenantId, milestoneId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
	}

	// Without Authorization
	@Test
	public void testGetMilestoneWithoutAuthorization() {
		String milestoneId = "396"; // Valid milestone ID
		Response response = MilestoneEndPoints.getMilestone(null, xTenantId, milestoneId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
	}

	// Invalid Milestone ID: "12345678"
	@Test
	public void testGetMilestoneInvalidId12345678() {
		String milestoneId = "12345678"; // Invalid milestone ID
		Response response = MilestoneEndPoints.getMilestone(accessToken, xTenantId, milestoneId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404");
	}

	// Invalid Milestone ID: "-20"
	@Test
	public void testGetMilestoneInvalidIdMinus20() {
		String milestoneId = "-20"; // Invalid milestone ID
		Response response = MilestoneEndPoints.getMilestone(accessToken, xTenantId, milestoneId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404");
	}

	// Invalid Milestone ID: "abc"
	@Test
	public void testGetMilestoneInvalidIdABC() {
		String milestoneId = "abc"; // Invalid milestone ID
		Response response = MilestoneEndPoints.getMilestone(accessToken, xTenantId, milestoneId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 404");
	}

	// Invalid Tenant ID
	@Test
	public void testGetMilestoneInvalidTenantId() {
		String milestoneId = "396"; // Valid milestone ID
		Response response = MilestoneEndPoints.getMilestone(accessToken, "invalid_tenant_id", milestoneId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
	}
}

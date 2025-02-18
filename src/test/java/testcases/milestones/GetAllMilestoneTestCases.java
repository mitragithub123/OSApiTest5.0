package testcases.milestones;

import endpoints.LoginEndPoints;
import endpoints.MilestoneEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.milestones.Milestones;
import utilities.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class GetAllMilestoneTestCases {

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
	public void testGetAllMilestonesPositiveCase() {
		Milestones milestone = new Milestones();
		Response response = MilestoneEndPoints.getAllMilestone(milestone, accessToken, xTenantId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
	}

	// Without Authorization
	@Test
	public void testGetAllMilestonesWithoutAuthorization() {
		Milestones milestone = new Milestones();
		Response response = MilestoneEndPoints.getAllMilestone(milestone, null, xTenantId, 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
	}

	// Invalid Tenant ID
	@Test
	public void testGetAllMilestonesInvalidTenantId() {
		Milestones milestone = new Milestones();
		Response response = MilestoneEndPoints.getAllMilestone(milestone, accessToken, "invalid_tenant_id", 1, 50,
				false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
	}

	// Invalid Page Number
	@Test
	public void testGetAllMilestonesInvalidPageNumber() {
		Milestones milestone = new Milestones();
		Response response = MilestoneEndPoints.getAllMilestone(milestone, accessToken, xTenantId, -1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422");
	}

}

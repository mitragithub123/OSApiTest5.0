package testcases.milestones;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.MilestoneEndPoints;
import payload.Login;
import utilities.ConfigReader;

public class DeleteMilestoneTestCases {

	private String accessToken;
	private String xTenantId;
	private ConfigReader configReader;

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
	}

	// Positive case: Delete milestone
	@Test(priority = 1)
	public void verifyDeleteMilestone() {
		System.out.println("Access Token123: " + accessToken);

		String milestoneId = "32"; // Replace with an actual milestone ID
		Response response = MilestoneEndPoints.deleteMilestone(accessToken, xTenantId, milestoneId);
		response.then().log().all();
		System.out.println("Access Token: " + accessToken);
		System.out.println("Tenant ID: " + xTenantId);
		System.out.println("Response Body: " + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200, "Milestone deletion failed");
	}

	// Without authorization
	@Test(priority = 2)
	public void verifyDeleteMilestoneWithoutAuthorization() {
		String milestoneId = "32"; // Replace with an actual milestone ID
		Response response = MilestoneEndPoints.deleteMilestone(null, xTenantId, milestoneId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Unauthorized access should fail");
	}

	// Invalid milestone ID: "12345678"
	@Test(priority = 3)
	public void verifyDeleteMilestoneWithInvalidMilestoneIdNumber() {
		String milestoneId = "12345678"; // Invalid milestone ID
		Response response = MilestoneEndPoints.deleteMilestone(milestoneId, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Invalid milestone ID should result in 404");
	}

	// Invalid milestone ID: "-20"
	@Test(priority = 4)
	public void verifyDeleteMilestoneWithNegativeMilestoneId() {
		String milestoneId = "-20"; // Negative milestone ID
		Response response = MilestoneEndPoints.deleteMilestone(milestoneId, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Negative milestone ID should result in 404");
	}

	// Invalid milestone ID: "abc"
	@Test(priority = 5)
	public void verifyDeleteMilestoneWithNonNumericMilestoneId() {
		String milestoneId = "abc"; // Non-numeric milestone ID
		Response response = MilestoneEndPoints.deleteMilestone(milestoneId, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Non-numeric milestone ID should result in 422");
	}

	// Invalid tenant ID
	@Test(priority = 6)
	public void verifyDeleteMilestoneWithInvalidTenantId() {
		String milestoneId = "32"; // Replace with an actual milestone ID
		Response response = MilestoneEndPoints.deleteMilestone(milestoneId, accessToken, "invalidTenantId");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Access with invalid tenant ID should be forbidden");
	}
}

package testcases.users;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.LoginEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.users.Users;
import utilities.ConfigReader;

import java.util.Arrays;

public class CreateResourceSkillTestCases {
	private String accessToken;
	private ConfigReader configReader;
	private String xTenantId;

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

	// Create resource skill
	@Test
	public void createResourceSkill() {
		Users skillPayload = new Users();
		skillPayload.setSkillId(Arrays.asList(1));
		skillPayload.setResourceId(192); // Get it from my profiles page
		Response response = UserEndPoints.createResourceSkill(accessToken, skillPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to create resource skill");
	}

	// Invalid tenant id
	@Test(priority = 2)
	public void createResourceSkillWithInvalidTanantID() {
		Users skillPayload = new Users();
		skillPayload.setSkillId(Arrays.asList(1));
		skillPayload.setResourceId(192); // Get it from my profiles page
		Response response = UserEndPoints.createResourceSkill(accessToken, skillPayload, "invalid");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to create resource skill");
	}

	// Without authorization
	@Test(priority = 3)
	public void createResourceSkillWithoutAuthorization() {
		Users skillPayload = new Users();
		skillPayload.setSkillId(Arrays.asList(1));
		skillPayload.setResourceId(192); // Get it from my profiles page
		Response response = UserEndPoints.createResourceSkill(null, skillPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to create resource skill");
	}

	// Negative Test Case: Missing Required Fields
	@Test(priority = 4)
	public void createResourceSkillWithMissingFields() {
		Users skillPayload = new Users();
		// skillId and resourceId are missing

		Response response = UserEndPoints.createResourceSkill(accessToken, skillPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for missing required fields");
	}

	// Negative Test Case: Invalid Resource ID = -1
	@Test(priority = 5)
	public void createResourceSkillWithInvalidResourceId() {
		Users skillPayload = new Users();
		skillPayload.setSkillId(Arrays.asList(1));
		skillPayload.setResourceId(-1); // Invalid resource ID

		Response response = UserEndPoints.createResourceSkill(accessToken, skillPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for invalid resource ID");
	}
}

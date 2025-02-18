package testcases.users;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.LoginEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.users.Users;
import utilities.ConfigReader;

public class UpdateTenantTestCases {
	private String accessToken;
	private ConfigReader configReader;
	private Faker faker;
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
		faker = new Faker();
	}

	// Update tenant details
	@Test(priority = 1)
	public void updateCurrentTenant() {
		Users userPayload = new Users();
		userPayload.setName(faker.company().name());
		userPayload.setWebsite(faker.internet().url());
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		userPayload.setTenantDescription(faker.lorem().sentence());
		Response response = UserEndPoints.updateTenant(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update tenant details");
	}

	// Invalid tenant id
	@Test(priority = 2)
	public void updateCurrentTenantWithInvalidTanantID() {
		Users userPayload = new Users();
		userPayload.setName(faker.company().name());
		userPayload.setWebsite(faker.internet().url());
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		userPayload.setTenantDescription(faker.lorem().sentence());
		Response response = UserEndPoints.updateTenant(accessToken, userPayload, "invalid");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to update current tenant");
	}

	// Without authorization
	@Test(priority = 3)
	public void updateCurrentTenantWithoutAuthorization() {
		Users userPayload = new Users();
		userPayload.setName(faker.company().name());
		userPayload.setWebsite(faker.internet().url());
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		userPayload.setTenantDescription(faker.lorem().sentence());
		Response response = UserEndPoints.updateTenant(null, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to update current tenant");
	}

}

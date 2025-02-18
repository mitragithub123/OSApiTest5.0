package testcases.users;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

public class GetTenantOwnerTestCases {
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

	// Get current tenant owner
	@Test(priority = 1)
	public void getCurrentTenantOwner() {
		Response response = UserEndPoints.getTenantOwner(accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to get current tenant owner");
	}

	// Invalid tenant id
	@Test(priority = 2)
	public void getCurrentTenantOwnerWithInvalidTanantID() {
		Response response = UserEndPoints.getTenantOwner(accessToken, "invalid");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to get current tenant owner");
	}

	// Without authorization
	@Test(priority = 3)
	public void getCurrentTenantOwnerWithoutAuthorization() {
		Response response = UserEndPoints.getTenantOwner(null, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to get current tenant owner");
	}
}

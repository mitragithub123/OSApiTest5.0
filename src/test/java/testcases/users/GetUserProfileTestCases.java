package testcases.users;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import endpoints.UserEndPoints;
import utilities.ConfigReader;
import endpoints.LoginEndPoints;
import payload.Login;

public class GetUserProfileTestCases {

	private String accessToken;
	private ConfigReader configReader;

	@BeforeClass
	public void setupTestData() {
		// Initialize ConfigReader
		configReader = new ConfigReader();

		// Perform login and retrieve access token
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Test case: Get user profile with valid authorization
	@Test(priority = 1)
	public void testGetUserProfilePositiveCase() {
		Response response = UserEndPoints.getUserProfile(accessToken);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve user profile");
	}

	// Test case: Get user profile without authorization
	@Test(priority = 2)
	public void testGetUserProfileWithoutAuthorization() {
		Response response = UserEndPoints.getUserProfile(null);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for missing authorization");
	}


}

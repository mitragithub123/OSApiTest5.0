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

public class InviteNewUserVerifyTestCases {
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

	// Verify invite token after sign up
	@Test(priority = 1)
	public void verifyInviteTokenAfterSignUp() {
		Users userPayload = new Users();
		userPayload.setInviteToken("4d3ff84560384228aa9f3e7f8b494191");
		Response response = UserEndPoints.inviteNewUserVerify(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Failed to verify invite new user");
	}

	// Verify invite token before sign up
	@Test(priority = 2)
	public void verifyInviteTokenBeforeSignUp() {
		Users userPayload = new Users();
		userPayload.setInviteToken("a642b0e59ebf4ee5b02532eea0ce9217");
		Response response = UserEndPoints.inviteNewUserVerify(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to verify invite new user");
	}

	// Verify wrong invite token
	@Test(priority = 3)
	public void verifyWrongInviteToken() {
		Users userPayload = new Users();
		userPayload.setInviteToken("a642b0e59ebf4ee5b02532eea0ce9217");
		Response response = UserEndPoints.inviteNewUserVerify(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Failed to verify invite new user");
	}

	// Without authorization
	@Test(priority = 4)
	public void verifyInviteTokenWithOutAuthorization() {
		Users userPayload = new Users();
		userPayload.setInviteToken("a642b0e59ebf4ee5b02532eea0ce9217");
		Response response = UserEndPoints.inviteNewUserVerify(null, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to verify invite new user");
	}

	// Invalid tenant id
	@Test(priority = 5)
	public void verifyInviteTokenWithOutTenantId() {
		Users userPayload = new Users();
		userPayload.setInviteToken("a642b0e59ebf4ee5b02532eea0ce9217");
		Response response = UserEndPoints.inviteNewUserVerify(accessToken, userPayload, "invalid");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Failed to verify invite new user");
	}

}

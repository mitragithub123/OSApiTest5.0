package testcases.users;

import com.github.javafaker.Faker;
import endpoints.LoginEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import payload.users.Users;
import utilities.ConfigReader;

public class InviteNewUserTestCases {
	private String accessToken;
	private Faker faker;
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

		faker = new Faker();
	}
	
	// Positive case
	@Test(priority = 1)
	public void inviteUserPositiveCase() {
		Users userPayload = new Users();
		userPayload.setInviteEmail(faker.internet().emailAddress());
		userPayload.setInviteRole(getRandomRole());
		userPayload.setInviteFirstName(faker.name().firstName());
		userPayload.setInviteLastName(faker.name().lastName());
		userPayload.setTenantId(119);

		Response response = UserEndPoints.inviteNewUser(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to invite new user");
	}

	public String getRandomRole() {
		String[] roles = { "admin", "user" };
		Random random = new Random();
		return roles[random.nextInt(roles.length)];
	}

	// Without authorization
	@Test(priority = 2)
	public void inviteUserWithoutAuth() {
		Users userPayload = new Users();
		userPayload.setInviteEmail(faker.internet().emailAddress());
		userPayload.setInviteRole(getRandomRole());
		userPayload.setInviteFirstName(faker.name().firstName());
		userPayload.setInviteLastName(faker.name().lastName());
		userPayload.setTenantId(119);

		Response response = UserEndPoints.inviteNewUser(null, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Invitation should not be allowed without authorization");
	}

	// Invalid email format
	@Test(priority = 3)
	public void inviteUserInvalidEmail() {
		Users userPayload = new Users();
		userPayload.setInviteEmail("invalid-email-format");
		userPayload.setInviteRole(getRandomRole());
		userPayload.setInviteFirstName(faker.name().firstName());
		userPayload.setInviteLastName(faker.name().lastName());
		userPayload.setTenantId(119);

		Response response = UserEndPoints.inviteNewUser(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Invitation should fail with invalid email format");
	}

	// Missing required fields email id
	@Test(priority = 4)
	public void inviteUserMissingRequiredFields() {
		Users userPayload = new Users();
		userPayload.setInviteRole(getRandomRole());
		userPayload.setInviteFirstName(faker.name().firstName());
		userPayload.setInviteLastName(faker.name().lastName());

		Response response = UserEndPoints.inviteNewUser(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Invitation should fail with missing required fields");
	}

	// Invalid role
	@Test(priority = 5)
	public void inviteUserInvalidRole() {
		Users userPayload = new Users();
		userPayload.setInviteEmail(faker.internet().emailAddress());
		userPayload.setInviteRole("invalidRole"); // Invalid role
		userPayload.setInviteFirstName(faker.name().firstName());
		userPayload.setInviteLastName(faker.name().lastName());
		userPayload.setTenantId(119);

		Response response = UserEndPoints.inviteNewUser(accessToken, userPayload, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Invitation should fail with invalid role");
	}

	// Invalid tenant id
	@Test(priority = 6)
	public void inviteUserInvalidTenantId() {
		Users userPayload = new Users();
		userPayload.setInviteEmail(faker.internet().emailAddress());
		userPayload.setInviteRole(getRandomRole());
		userPayload.setInviteFirstName(faker.name().firstName());
		userPayload.setInviteLastName(faker.name().lastName());
		userPayload.setTenantId(119);

		Response response = UserEndPoints.inviteNewUser(accessToken, userPayload, "invalid");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to invite new user");
	}
}

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

public class UpdateUserProfileTestCases {
	private String accessToken;
	private Faker faker;
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

		// Initialize Faker
		faker = new Faker();
	}

	// Positive case
	@Test(priority = 1)
	public void testUpdateUserProfilePositiveCase() {
		Users user = new Users();
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setPhoneNumber(faker.phoneNumber().cellPhone());
		Response response = UserEndPoints.updateUserProfile(accessToken, user);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to update user profile");
	}

	// Without authorization token
	@Test(priority = 2)
	public void testUpdateUserProfileNoAuth() {
		Users user = new Users();
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setPhoneNumber(faker.phoneNumber().cellPhone());
		Response response = UserEndPoints.updateUserProfile(null, user);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to update user profile");
	}

	// With empty data
	@Test(priority = 3)
	public void testUpdateUserProfileWithEmptyData() {
		Users user = new Users();
		user.setFirstName(null);
		user.setLastName(null);
		user.setPhoneNumber(null);
		Response response = UserEndPoints.updateUserProfile(accessToken, user);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Failed to update user profile");
	}

	// With invalid phone number format
	@Test(priority = 4)
	public void testUpdateUserProfileInvalidPhoneNumber() {
		Users user = new Users();
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setPhoneNumber("invalid-phone-number");
		Response response = UserEndPoints.updateUserProfile(accessToken, user);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for invalid phone number format");
	}
}

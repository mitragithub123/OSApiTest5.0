package testcases.users;

import endpoints.LoginEndPoints;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import payload.Login;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;

import java.io.File;

public class UpdateProfilePhotoTestCases {
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

	// Positive case: valid file upload
	@Test(priority = 1)
	public void uploadProfilePhotoPositiveCase() {
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = UserEndPoints.updateProfilePhoto(accessToken, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Profile photo upload failed");
	}

	// Without authorization token
	@Test(priority = 2)
	public void uploadProfilePhotoWithoutAuthorization() {
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = UserEndPoints.updateProfilePhoto(null, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401,
				"Profile photo upload should not be allowed without authorization");
	}

	// Invalid file type
	@Test(priority = 3)
	public void uploadProfilePhotoInvalidFileType() {
		String filePath = System.getProperty("user.dir") + "\\testdata\\Offline.docx";
		File file = new File(filePath);

		Response response = UserEndPoints.updateProfilePhoto(accessToken, file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 400,
				"Profile photo upload should not be allowed with invalid file type");
	}

	// No file uploaded
	@Test(priority = 4)
	public void uploadProfilePhotoNoFile() {
		File file = null;
		try {
			Response response = UserEndPoints.updateProfilePhoto(accessToken, file);
			response.then().log().all();
			Assert.fail("Expected an exception due to no file being provided");
		} catch (Exception e) {
			Assert.assertTrue(e instanceof NullPointerException || e.getMessage().contains("No file provided"),
					"Expected a NullPointerException or a message indicating no file was provided");
		}
	}

	// Invalid token
	@Test(priority = 5)
	public void uploadProfilePhotoInvalidToken() {
		String filePath = System.getProperty("user.dir") + "\\testdata\\nature-image-for-website.jpg";
		File file = new File(filePath);

		Response response = UserEndPoints.updateProfilePhoto("invalid-token", file);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401,
				"Profile photo upload should not be allowed with invalid token");
	}
}

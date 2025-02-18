package testcases.tasks;

import com.github.javafaker.Faker;
import endpoints.LoginEndPoints;
import endpoints.TaskEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import utilities.ConfigReader;

public class GetRecentProjectsTestCases {
	public String accessToken;
	public Faker faker;
	public ConfigReader configReader;
	public String xTenantId;

	@BeforeClass
	public void setupTestData() {
		configReader = new ConfigReader();

		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);

		faker = new Faker();
	}

	// Positive case
	@Test(priority = 1)
	public void getRecentProjectsPositiveCase() {
		Response response = TaskEndPoints.getRecentProjects(accessToken, xTenantId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve recent projects");
	}

	// Without authorization
	@Test(priority = 2)
	public void getRecentProjectsWithoutAuthorization() {
		Response response = TaskEndPoints.getRecentProjects(null, xTenantId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401,
				"Recent projects should not be retrieved without authorization");
	}

	// Invalid tenant ID
	@Test(priority = 3)
	public void getRecentProjectsInvalidTenantId() {
		Response response = TaskEndPoints.getRecentProjects(accessToken, "invalid-tenant-id");
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401,
				"Recent projects should not be retrieved with invalid tenant ID");
	}
}

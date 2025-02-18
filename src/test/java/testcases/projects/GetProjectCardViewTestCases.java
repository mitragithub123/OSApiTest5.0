package testcases.projects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.projects.Projects;
import utilities.ConfigReader;

public class GetProjectCardViewTestCases {
	public String accessToken;
	public Faker faker;
	public Projects projectRequest;
	public ConfigReader configReader;
	public String xTenantId;

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

		// Initialize Faker
		faker = new Faker();
	}

	// Positive case
	@Test(priority = 1)
	public void verifyGetProjectCardView() {
		System.out.println("Test Case: verifyGetProjectCardView - Start");

		// Generate ProjectRequest object with fake data
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date(getPastDateString());
		projectRequest.setEnd_date(getFutureDateString());
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		System.out.println("Request Payload: " + projectRequest.toString());

		// Get project card view using ProjectEndPoints
		Response response = ProjectEndPoints.getProjectCardView(projectRequest, accessToken, xTenantId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to get project card view");

		System.out.println("Test Case: verifyGetProjectCardView - End");
	}

	// Invalid Authorization Token
	@Test(priority = 2)
	public void verifyGetProjectCardViewWithInvalidToken() {
		System.out.println("Test Case: verifyGetProjectCardViewWithInvalidToken - Start");

		// Generate ProjectRequest object with fake data
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date(getPastDateString());
		projectRequest.setEnd_date(getFutureDateString());
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		// Get project card view using ProjectEndPoints with invalid token
		Response response = ProjectEndPoints.getProjectCardView(projectRequest, "invalid_token", xTenantId);
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project card view should not be retrieved with invalid token");

		System.out.println("Test Case: verifyGetProjectCardViewWithInvalidToken - End");
	}

	// Invalid x-tenant-id Header
	@Test(priority = 3)
	public void verifyGetProjectCardViewWithoutTenantId() {
		System.out.println("Test Case: verifyGetProjectCardViewWithoutTenantId - Start");

		// Generate ProjectRequest object with fake data
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date(getPastDateString());
		projectRequest.setEnd_date(getFutureDateString());
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		// Get project card view using ProjectEndPoints without x-tenant-id header
		Response response = ProjectEndPoints.getProjectCardView(projectRequest, accessToken, "123");
		response.then().log().all();

		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project card view should not be retrieved without x-tenant-id");

		System.out.println("Test Case: verifyGetProjectCardViewWithoutTenantId - End");
	}

	// Helper method to get a future date as string in "yyyy-MM-dd" format
	private String getFutureDateString() {
		LocalDate futureDate = LocalDate.now().plusDays(faker.number().numberBetween(1, 30));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return futureDate.format(formatter);
	}

	// Helper method to get a past date as string in "yyyy-MM-dd" format
	private String getPastDateString() {
		LocalDate pastDate = LocalDate.now().minusDays(new Faker().number().numberBetween(1, 365));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return pastDate.format(formatter);
	}
}
package testcases.projects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.LoginEndPoints;
import io.restassured.response.Response;
import payload.Login;
import payload.projects.Projects;
import endpoints.ProjectEndPoints;
import utilities.ConfigReader;

public class CreateProjectTestCases {
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
	public void verifyCreateProject() {
		System.out.println("Test Case: verifyCreateProject - Start");

		// Generate ProjectRequest object with fake data
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date(getPastDateString()); // Use the method to get a past date
		projectRequest.setEnd_date(getFutureDateString()); // Ensure to set an end date if required
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		System.out.println("Request Payload: " + projectRequest.toString());

		// Create project using ProjectsEndPoints
		Response response = ProjectEndPoints.createProject(projectRequest, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to create project");

		System.out.println("Test Case: verifyCreateProject - End");
	}

	// Empty project name
	@Test(priority = 2)
	public void verifyCreateProjectWithEmptyProjectName() {
		System.out.println("Test Case: verifyCreateProjectWithEmptyProjectName - Start");

		// Generate ProjectRequest object with empty project name
		projectRequest = new Projects();
		projectRequest.setProject_name("");
		projectRequest.setStart_date(getPastDateString());
		projectRequest.setEnd_date(getFutureDateString());
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		System.out.println("Request Payload: " + projectRequest.toString());

		// Create project using ProjectsEndPoints
		Response response = ProjectEndPoints.createProject(projectRequest, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200, "Project should not be created with empty project name");

		System.out.println("Test Case: verifyCreateProjectWithEmptyProjectName - End");
	}

	// Invalid start date
	@Test(priority = 3)
	public void verifyCreateProjectWithInvalidStartDate() {
		System.out.println("Test Case: verifyCreateProjectWithInvalidStartDate - Start");

		// Generate ProjectRequest object with invalid start date format
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date("invalid-date-format");
		projectRequest.setEnd_date(getFutureDateString());
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		System.out.println("Request Payload: " + projectRequest.toString());

		// Create project using ProjectsEndPoints
		Response response = ProjectEndPoints.createProject(projectRequest, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be created with invalid start date format");

		System.out.println("Test Case: verifyCreateProjectWithInvalidStartDate - End");
	}

	// End date before start date
	@Test(priority = 4)
	public void verifyCreateProjectWithEndDateBeforeStartDate() {
		System.out.println("Test Case: verifyCreateProjectWithEndDateBeforeStartDate - Start");

		// Generate ProjectRequest object with end date before start date
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date(getFutureDateString());
		projectRequest.setEnd_date(getPastDateString());
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		System.out.println("Request Payload: " + projectRequest.toString());

		// Create project using ProjectsEndPoints
		Response response = ProjectEndPoints.createProject(projectRequest, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be created with end date before start date");

		System.out.println("Test Case: verifyCreateProjectWithEndDateBeforeStartDate - End");
	}

	// Without authorization
	@Test(priority = 5)
	public void verifyCreateProjectWithoutAuthorizationToken() {
		System.out.println("Test Case: verifyCreateProjectWithoutAuthorizationToken - Start");

		// Generate ProjectRequest object with fake data
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date(getPastDateString());
		projectRequest.setEnd_date(getFutureDateString());
		projectRequest.setProject_priority(faker.options().option("Low", "Medium", "High"));
		projectRequest.setProject_description(faker.lorem().sentence());

		System.out.println("Request Payload: " + projectRequest.toString());

		// Create project without authorization token
		Response response = ProjectEndPoints.createProject(projectRequest, null, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be created without authorization token");

		System.out.println("Test Case: verifyCreateProjectWithoutAuthorizationToken - End");
	}

	// Invalid project priority
	@Test(priority = 6)
	public void verifyCreateProjectWithInvalidProjectPriority() {
		System.out.println("Test Case: verifyCreateProjectWithInvalidProjectPriority - Start");

		// Generate ProjectRequest object with invalid project priority
		projectRequest = new Projects();
		projectRequest.setProject_name(faker.company().name());
		projectRequest.setStart_date(getPastDateString());
		projectRequest.setEnd_date(getFutureDateString());
		projectRequest.setProject_priority("InvalidPriority");
		projectRequest.setProject_description(faker.lorem().sentence());

		System.out.println("Request Payload: " + projectRequest.toString());

		// Create project using ProjectsEndPoints
		Response response = ProjectEndPoints.createProject(projectRequest, accessToken, xTenantId);
		response.then().log().all();

		System.out.println("Response Status Code: " + response.getStatusCode());
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Project should not be created with invalid project priority");

		System.out.println("Test Case: verifyCreateProjectWithInvalidProjectPriority - End");
	}

	// Helper method to get a future date as string in "yyyy-MM-dd" format
	private String getFutureDateString() {
		LocalDate futureDate = LocalDate.now().plusDays(faker.number().numberBetween(1, 30)); // Generate future date
																								// within next 30 days
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return futureDate.format(formatter);
	}

	// Helper method to get a past date as string in "yyyy-MM-dd" format
	private String getPastDateString() {
		LocalDate pastDate = LocalDate.now().minusDays(new Faker().number().numberBetween(1, 365)); // Generate past
																									// date within the
																									// last year
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return pastDate.format(formatter);
	}
}

package testcases.milestones;

import io.restassured.response.Response;
import payload.Login;
import payload.milestones.Milestones;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.LoginEndPoints;
import endpoints.MilestoneEndPoints;
import utilities.ConfigReader;

public class CreateMilestoneTestCases {

	private String accessToken;
	private String xTenantId;
	public Faker faker;
	public ConfigReader configReader;

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

	// Positive case: Create milestone
	@Test(priority = 1)
	public void verifyCreateMilestone() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");
		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Milestone creation failed");
	}

	// Without authorization
	@Test(priority = 2)
	public void verifyCreateMilestoneWithoutAuthorization() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, null, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Unauthorized access should fail");
	}

	// Invalid tenant ID
	@Test(priority = 3)
	public void verifyCreateMilestoneWithInvalidTenantId() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, "invalidTenantId");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 401, "Access with invalid tenant ID should be forbidden");
	}

	// Invalid project ID: "12345678"
	@Test(priority = 4)
	public void verifyCreateMilestoneWithInvalidProjectIdNumber() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("12345678"); // Invalid project ID
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Invalid project ID should result in 404");
	}

	// Invalid project ID: "-20"
	@Test(priority = 5)
	public void verifyCreateMilestoneWithNegativeProjectId() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("-20"); // Negative project ID
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404, "Invalid project ID should result in 404");
	}

	// Invalid project ID: "abc"
	@Test(priority = 6)
	public void verifyCreateMilestoneWithNonNumericProjectId() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("abc");
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422, "Invalid project ID should result in 422");

	}

	// Empty milestone title
	@Test(priority = 7)
	public void verifyCreateMilestoneWithEmptyMilestoneTitle() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		milestone.setMilestoneTitle(""); // Empty milestone title
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 400, "Empty milestone title should result in 400");
	}

	// End date before start date
	@Test(priority = 8)
	public void verifyCreateMilestoneWithEndDateBeforeStartDate() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-28T04:43:46.337Z");
		milestone.setEndDate("2024-07-25T04:43:46.337Z"); // End date before start date
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422,
				"End date before start date should result in 422 Unprocessable Entity");
	}

	// Negative estimated hours
	@Test(priority = 9)
	public void verifyCreateMilestoneWithZeroEstimatedHours() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(-12); // Negative estimated hours
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422,
				"Zero estimated hours should result in 422 Unprocessable Entity");
	}

	// Missing required fields
	@Test(priority = 10)
	public void verifyCreateMilestoneWithMissingRequiredFields() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		// Missing milestoneTitle, description, startDate, endDate, estimatedHour
		milestone.setAssignTo("fabc8c7d-19bf-40ca-8dd3-1c53f835bef2");

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422,
				"Missing required fields should result in 422 Unprocessable Entity");
	}

	// Invalid AssignTo user ID
	@Test(priority = 11)
	public void verifyCreateMilestoneWithInvalidAssignToUserId() {
		Milestones milestone = new Milestones();
		milestone.setProjectId("758");
		milestone.setMilestoneTitle(faker.lorem().word());
		milestone.setDescription(faker.lorem().sentence());
		milestone.setStartDate("2024-07-25T04:43:46.337Z");
		milestone.setEndDate("2024-07-28T04:43:46.337Z");
		milestone.setEstimatedHour(faker.number().numberBetween(1, 2));
		milestone.setAssignTo("invalidUserId"); // Invalid AssignTo user ID

		Response response = MilestoneEndPoints.createMilestone(milestone, accessToken, xTenantId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 422,
				"Invalid AssignTo user ID should result in 422 Unprocessable Entity");
	}

}

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

public class GetMyTaskTestCases {
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

	// Positive case for each day_wise value
	@Test(priority = 1)
	public void getMyTasksPositiveCaseAssignToMe() {
		Response response = TaskEndPoints.getMyTasks(accessToken, xTenantId, "assign_to_me", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks for 'assign_to_me'");
	}

	@Test(priority = 2)
	public void getMyTasksPositiveCaseToday() {
		Response response = TaskEndPoints.getMyTasks(accessToken, xTenantId, "today", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks for 'today'");
	}

	@Test(priority = 3)
	public void getMyTasksPositiveCaseOverdue() {
		Response response = TaskEndPoints.getMyTasks(accessToken, xTenantId, "overdue", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks for 'overdue'");
	}

	@Test(priority = 4)
	public void getMyTasksPositiveCaseUnscheduled() {
		Response response = TaskEndPoints.getMyTasks(accessToken, xTenantId, "unscheduled", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks for 'unscheduled'");
	}

	@Test(priority = 5)
	public void getMyTasksPositiveCaseDelegated() {
		Response response = TaskEndPoints.getMyTasks(accessToken, xTenantId, "delegated", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve tasks for 'delegated'");
	}

	// Without authorization
	@Test(priority = 6)
	public void getMyTasksWithoutAuthorization() {
		Response response = TaskEndPoints.getMyTasks(null, xTenantId, "assign_to_me", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Tasks should not be retrieved without authorization");
	}

	// Invalid tenant ID
	@Test(priority = 7)
	public void getMyTasksInvalidTenantId() {
		Response response = TaskEndPoints.getMyTasks(accessToken, "invalid-tenant-id", "assign_to_me", 1, 50, false);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 401, "Tasks should not be retrieved with invalid tenant ID");
	}
}

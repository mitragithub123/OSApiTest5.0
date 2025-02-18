package testcases.projects;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Login;
import utilities.ConfigReader;

public class DeleteProjectMemberTestCases {
	private String accessToken;
	private String xTenantId;
	private String projectId = "758"; // Assuming the project ID is known and set here
	private String memberId = "224"; // Collect from teams page.

	@BeforeClass
	public void setupTestData() {
		ConfigReader configReader = new ConfigReader();
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Positive case
	@Test(priority = 1)
	public void verifyDeleteProjectMember() {
		System.out.println("Test Case: verifyDeleteProjectMember - Start");
		Response response = ProjectEndPoints.deleteProjectMember(projectId, memberId, accessToken, xTenantId);
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete project member");
		System.out.println("Test Case: verifyDeleteProjectMember - End");
	}

	// Invalid project id
	@Test(priority = 2)
	public void verifyDeleteProjectMemberWithInvalidProjectId() {
		String[] invalidProjectIds = { "12345", "-20", "abc" };

		for (String invalidProjectId : invalidProjectIds) {
			System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidProjectId for Project ID: "
					+ invalidProjectId + " - Start");
			Response response = ProjectEndPoints.deleteProjectMember(invalidProjectId, memberId, accessToken,
					xTenantId);
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid project ID: " + invalidProjectId);
			System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidProjectId for Project ID: "
					+ invalidProjectId + " - End");
		}
	}

	// Invalid member id
	@Test(priority = 3)
	public void verifyDeleteProjectMemberWithInvalidMemberId() {
		String[] invalidMemberIds = { "99999", "-1", "abc" };

		for (String invalidMemberId : invalidMemberIds) {
			System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidMemberId for Member ID: "
					+ invalidMemberId + " - Start");
			Response response = ProjectEndPoints.deleteProjectMember(projectId, invalidMemberId, accessToken,
					xTenantId);
			System.out.println("Response Status Code: " + response.getStatusCode());
			response.then().log().all();
			Assert.assertNotEquals(response.getStatusCode(), 200,
					"Expected failure with invalid member ID: " + invalidMemberId);
			System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidMemberId for Member ID: "
					+ invalidMemberId + " - End");
		}
	}

	// Invalid access token
	@Test(priority = 4)
	public void verifyDeleteProjectMemberWithInvalidAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidAccessToken - Start");
		Response response = ProjectEndPoints.deleteProjectMember(projectId, memberId, "invalid_token", xTenantId);
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid access token");
		System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidAccessToken - End");
	}

	// Invalid tenant id
	@Test(priority = 5)
	public void verifyDeleteProjectMemberWithInvalidTenantId() {
		System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidTenantId - Start");
		Response response = ProjectEndPoints.deleteProjectMember(projectId, memberId, accessToken, "123");
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");
		System.out.println("Test Case: verifyDeleteProjectMemberWithInvalidTenantId - End");
	}

	// No access token
	@Test(priority = 6)
	public void verifyDeleteProjectMemberWithoutAccessToken() {
		System.out.println("Test Case: verifyDeleteProjectMemberWithoutAccessToken - Start");
		Response response = ProjectEndPoints.deleteProjectMember(projectId, memberId, null, xTenantId);
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");
		System.out.println("Test Case: verifyDeleteProjectMemberWithoutAccessToken - End");
	}
}

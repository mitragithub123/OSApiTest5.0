package testcases.projects;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.LoginEndPoints;
import endpoints.ProjectEndPoints;
import io.restassured.response.Response;
import payload.Login;
import utilities.ConfigReader;

import java.util.Arrays;
import java.util.List;

public class MultiUpdateProjectMembersTestCases {
	private String accessToken;
	private String xTenantId;
	private int projectId = 758;
	private List<Integer> memberIds = Arrays.asList(192, 193);

	@BeforeClass
	public void setupTestData() {
		// Initialize ConfigReader
		ConfigReader configReader = new ConfigReader();

		// Perform login and retrieve access token
		String email = configReader.getProperty("email");
		String password = configReader.getProperty("password");
		xTenantId = configReader.getProperty("xTenantId");
		Login loginPayload = new Login(email, password);
		accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);
	}

	// Positive case: Verify multi-update project members
	@Test(priority = 1)
	public void verifyMultiUpdateProjectMembers() {
		System.out.println("Test Case: verifyMultiUpdateProjectMembers - Start");

		// Multi-update project members using ProjectEndPoints
		Response response = ProjectEndPoints.multiUpdateProjectMembers(memberIds, projectId, accessToken, xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is 200
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to multi-update project members");

		System.out.println("Test Case: verifyMultiUpdateProjectMembers - End");
	}

	// Negative case: Verify multi-update project members with invalid project ID
	@Test(priority = 2)
	public void verifyMultiUpdateProjectMembersWithInvalidProjectId() {
		int invalidProjectId = -20;

		System.out.println("Test Case: verifyMultiUpdateProjectMembersWithInvalidProjectId - Start");

		// Multi-update project members using invalid project ID
		Response response = ProjectEndPoints.multiUpdateProjectMembers(memberIds, invalidProjectId, accessToken,
				xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200,
				"Expected failure with invalid project ID: " + invalidProjectId);

		System.out.println("Test Case: verifyMultiUpdateProjectMembersWithInvalidProjectId - End");
	}

	// Negative case: Verify multi-update project members without access token
	@Test(priority = 3)
	public void verifyMultiUpdateProjectMembersWithoutAccessToken() {
		System.out.println("Test Case: verifyMultiUpdateProjectMembersWithoutAccessToken - Start");

		// Multi-update project members without access token
		Response response = ProjectEndPoints.multiUpdateProjectMembers(memberIds, projectId, "invalid_token",
				xTenantId);

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure without access token");

		System.out.println("Test Case: verifyMultiUpdateProjectMembersWithoutAccessToken - End");
	}

	// Negative case: Verify multi-update project members with invalid tenant id
	@Test(priority = 4)
	public void verifyMultiUpdateProjectMembersWithInvalidTenantId() {
		System.out.println("Test Case: verifyMultiUpdateProjectMembersWithInvalidTenantId - Start");

		// Multi-update project members with invalid tenant id
		Response response = ProjectEndPoints.multiUpdateProjectMembers(memberIds, projectId, accessToken, "123");

		// Log request and response details
		System.out.println("Response Status Code: " + response.getStatusCode());
		response.then().log().all();

		// Assert that the response status code is not 200
		Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure with invalid tenant id");

		System.out.println("Test Case: verifyMultiUpdateProjectMembersWithInvalidTenantId - End");
	}
}

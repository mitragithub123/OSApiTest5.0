package testcases.registereduser;

import org.testng.Assert;
import org.testng.annotations.Test;
import endpoints.RegisteredUserEndPoints;
import io.restassured.response.Response;

public class GetPreRegisterTestCases {
	// Positive case
	@Test(priority = 1)
	public void getPreRegister() {
		Response response = RegisteredUserEndPoints.getpreRegisterUser();
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to get pre register");
	}
}

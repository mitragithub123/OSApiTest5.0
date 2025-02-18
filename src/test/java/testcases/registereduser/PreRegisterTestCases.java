package testcases.registereduser;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import endpoints.RegisteredUserEndPoints;
import io.restassured.response.Response;
import payload.preregister.PreRegister;

public class PreRegisterTestCases {
	private Faker faker;
	@BeforeClass
	public void setupTestData() {
		faker = new Faker();
	}

	// Positive case
	@Test(priority = 1)
	public void preRegister() {
		PreRegister registerPayload = new PreRegister();
		registerPayload.setEmail(faker.internet().emailAddress());
		//registerPayload.setIsConverted(0);
		Response response = RegisteredUserEndPoints.preRegisterUser(registerPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to pre register");
	}

}

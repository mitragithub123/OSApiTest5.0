package endpoints;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import payload.preregister.PreRegister;

public class RegisteredUserEndPoints {
	public static Response preRegisterUser(PreRegister payload) {
        return given()
            .header("Content-Type", "application/json")
            .contentType("application/json")
            .body(payload)
            .when()
            .post(Routes.preRegisterUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getpreRegisterUser() {
        return given()
            .header("Content-Type", "application/json")
            .contentType("application/json")
            .when()
            .get(Routes.getPreRegisterUrl)
            .then()
            .extract()
            .response();
    }
}

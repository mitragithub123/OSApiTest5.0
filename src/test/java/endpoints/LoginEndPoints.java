package endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import payload.Login;

public class LoginEndPoints {
    private static String accessToken;

    // Method to perform login and retrieve access token
    public static String performLoginAndGetToken(Login loginPayload) {
        try {
            Response loginResponse = RestAssured.given()
                    .contentType("application/x-www-form-urlencoded")
                    .accept("application/json")
                    .formParam("username", loginPayload.getUsername())
                    .formParam("password", loginPayload.getPassword())
                    .post(Routes.loginUrl);

            // Log request details
            System.out.println("Login Request:");
            System.out.println("URL: " + Routes.loginUrl);
            System.out.println("Request Body: username=" + loginPayload.getUsername() + "&password=" + loginPayload.getPassword());

            // Log response details
            System.out.println("Login Response:");
            System.out.println("Status Code: " + loginResponse.getStatusCode());
            System.out.println("Response Body: " + loginResponse.asString());

            // Check if login was successful
            if (loginResponse.getStatusCode() == 200) {
                // Extract and return access token from login response
                String accessToken = loginResponse.then().extract().path("access_token");
                return accessToken;
            } else {
                // Handle unsuccessful login (you can throw an exception or return null)
                System.out.println("Login failed. Status code: " + loginResponse.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            // Exception handling
            System.err.println("Exception during login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}


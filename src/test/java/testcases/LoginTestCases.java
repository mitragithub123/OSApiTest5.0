package testcases;

import org.testng.annotations.Test;
import org.testng.Assert;

import endpoints.LoginEndPoints;
import payload.Login;

public class LoginTestCases {
	
	//Successful login
    @Test(priority = 1)
    public void verifySuccessfulLogin() {
        System.out.println("Test Case: verifySuccessfulLogin - Start");

        // Create a Login object with correct credentials
        Login loginPayload = new Login("owner23@email.com", "Owner@132");

        // Perform login and retrieve access token
        String accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);

        // Log the access token retrieved
        if (accessToken != null) {
            System.out.println("Access Token: " + accessToken);
        } else {
            System.out.println("Login failed. Access Token is null.");
        }

        System.out.println("Test Case: verifySuccessfulLogin - End");
    }
    
    //Incorrect password
    @Test(priority = 2)
    public void verifyLoginWithIncorrectPassword() {
        System.out.println("Test Case: verifyLoginWithIncorrectPassword - Start");

        // Create a Login object with correct email and incorrect password
        Login loginPayload = new Login("owner23@email.com", "IncorrectPassword");

        // Perform login and retrieve access token
        String accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);

        // Assert that login fails (access token should be null or empty)
        Assert.assertNull(accessToken, "Login should fail with incorrect password");

        System.out.println("Test Case: verifyLoginWithIncorrectPassword - End");
    }
    
    //Incorrect email
    @Test(priority = 3)
    public void verifyLoginWithIncorrectEmail() {
        System.out.println("Test Case: verifyLoginWithIncorrectEmail - Start");

        // Create a Login object with incorrect email and correct password
        Login loginPayload = new Login("nonexistent@email.com", "Owner@132");

        // Perform login and retrieve access token
        String accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);

        // Assert that login fails (access token should be null or empty)
        Assert.assertNull(accessToken, "Login should fail with incorrect email");

        System.out.println("Test Case: verifyLoginWithIncorrectEmail - End");
    }
    
    //Empty credentials
    @Test(priority = 4)
    public void verifyLoginWithEmptyCredentials() {
        System.out.println("Test Case: verifyLoginWithEmptyCredentials - Start");

        // Create a Login object with empty email and password
        Login loginPayload = new Login("", "");

        // Perform login and retrieve access token
        String accessToken = LoginEndPoints.performLoginAndGetToken(loginPayload);

        // Assert that login fails (access token should be null or empty)
        Assert.assertNull(accessToken, "Login should fail with empty credentials");

        System.out.println("Test Case: verifyLoginWithEmptyCredentials - End");
    }
}

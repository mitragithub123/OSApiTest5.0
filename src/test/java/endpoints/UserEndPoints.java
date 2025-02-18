package endpoints;
import static io.restassured.RestAssured.given;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import payload.users.Users;

public class UserEndPoints {
	public static Response getUserProfile(String accessToken) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .get(Routes.getUserProfileUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response updateUserProfile(String accessToken, Users userProfile) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("Content-Type", "application/json")
            .contentType("application/json")
            .body(userProfile)
            .when()
            .put(Routes.updateUserProfileUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response updateProfilePhoto(String accessToken, File file) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .multiPart("file", file)
                .when()
                .post(Routes.updateProfilePhotoUrl)
                .then()
                .extract()
                .response();
    }
	
	public static Response inviteNewUser(String accessToken, Users payload, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .contentType("application/json")
            .body(payload)
            .when()
            .post(Routes.inviteUserUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response inviteNewUserVerify(String accessToken, Users payload, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .contentType("application/json")
            .body(payload)
            .when()
            .post(Routes.inviteUserVerifyUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getTenant(String accessToken, String xTenantId) {
        return given()
        	.header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .contentType("application/json")
            .when()
            .get(Routes.getTenantUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response updateTenant(String accessToken, Users payload, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .contentType("application/json")
            .body(payload)
            .when()
            .put(Routes.updateTenantUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getTenantOwner(String accessToken, String xTenantId) {
        return given()
        	.header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .contentType("application/json")
            .when()
            .get(Routes.getTenantOwnerUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response createResourceSkill(String accessToken, Users payload, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .body(payload)
            .when()
            .post(Routes.createResourceSkillUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getAllSkills(String accessToken, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .when()
            .get(Routes.getAllSkillsUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getAllTenants(String accessToken, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .when()
            .get(Routes.getAllTenantUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response assignRole(String accessToken, Users payload, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .body(payload)
            .when()
            .post(Routes.assignRoleUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getAllRole(String accessToken, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .when()
            .get(Routes.getAllRoleUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response deleteRole(String accessToken, Users payload, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .body(payload)
            .when()
            .post(Routes.deleteRoleUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getCountry(String accessToken, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .when()
            .get(Routes.getCountryUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getCurrency(String accessToken, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .when()
            .get(Routes.getCurrencyUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getTimezone(String accessToken, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .when()
            .get(Routes.getTimezoneUrl)
            .then()
            .extract()
            .response();
    }
}

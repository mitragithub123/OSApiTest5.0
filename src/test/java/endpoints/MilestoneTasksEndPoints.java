package endpoints;

import io.restassured.response.Response;
import payload.milestones_tasks.MilestoneTasks;

import static io.restassured.RestAssured.given;

public class MilestoneTasksEndPoints {
	public static Response createMilestoneTask(String accessToken, String xTenantId, MilestoneTasks milestoneTask) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .body(milestoneTask)
            .when()
            .post(Routes.createMilestoneTaskUrl)
            .then()
            .extract()
            .response();
    }
	
	public static Response getAllMilestoneTasks(String accessToken, String xTenantId) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .when()
            .get(Routes.getAllMilestoneTasksUrl)
            .then()
            .extract()
            .response();
    }
	
	 public static Response getMilestoneTask(String accessToken, String xTenantId, String milestoneId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .when()
	            .get(Routes.getMilestoneTasksUrl.replace("{milestoneId}", milestoneId))
	            .then()
	            .extract()
	            .response();
	    }
	 
	 public static Response updateMilestoneTask(String accessToken, String xTenantId, String id, MilestoneTasks milestoneTask) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .contentType("application/json")
	            .body(milestoneTask)
	            .when()
	            .put(Routes.updateMilestoneTaskUrl.replace("{id}", id))
	            .then()
	            .extract()
	            .response();
	    }
	 
	 public static Response deleteMilestoneTask(String accessToken, String xTenantId, String milestoneId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .when()
	            .delete(Routes.deleteMilestoneTaskUrl.replace("{milestoneId}", milestoneId))
	            .then()
	            .extract()
	            .response();
	    }
}
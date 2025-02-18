package endpoints;

import io.restassured.response.Response;
import payload.milestones.Milestones;
import static io.restassured.RestAssured.given;

public class MilestoneEndPoints {
	 public static Response createMilestone(Milestones milestonePayload, String accessToken, String xTenantId) {
	        return given()
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .header("Content-Type", "application/json")
	                .body(milestonePayload)
	                .when()
	                .post(Routes.createMilestoneUrl)
	                .then()
	                .extract()
	                .response();
	    }
	 
	 public static Response getAllMilestone(Milestones milestonePayload, String accessToken, String xTenantId, int page, int size, boolean getAll) {
		 String url = Routes.getAllMilestoneUrl + "?page=" + page + "&size=" + size + "&get_all=" + getAll;
		 return given()
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .header("Content-Type", "application/json")
	                .body(milestonePayload)	        
	                .when()
	                .get(url)
	                .then()
	                .extract()
	                .response();
	 }
	 
	 public static Response getMilestone(String accessToken, String xTenantId, String milestoneId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .header("Content-Type", "application/json")
	            .when()
	            .get(Routes.getMilestoneUrl, milestoneId)
	            .then()
	            .extract()
	            .response();
	    }
	 
	 public static Response updateMilestone(String milestoneId, Milestones milestone, String accessToken, String xTenantId) {
	        return given()
	                .pathParam("milestoneId", milestoneId)
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .contentType("application/json")
	                .body(milestone)
	                .when()
	                .put(Routes.updateMilestoneUrl)
	                .then()
			        .extract()
			        .response();
	    }
	 
	 public static Response deleteMilestone(String accessToken, String xTenantId, String milestoneId) {
		    return given()
		        .header("Authorization", "Bearer " + accessToken)
		        .header("x-tenant-id", xTenantId)
		        .header("Content-Type", "application/json")
		        .when()
		        .delete(Routes.deleteMilestoneUrl, milestoneId)
		        .then()
		        .extract()
		        .response();
		}
	 
	 public static Response convertTaskToMilestone(String accessToken, String xTenantId, int taskId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .header("Content-Type", "application/json")
	            .body("{ \"task_id\": " + taskId + " }")
	            .when()
	            .post(Routes.convertTaskToMilestoneUrl)
	            .then()
	            .extract()
	            .response();
	    }




}
	 


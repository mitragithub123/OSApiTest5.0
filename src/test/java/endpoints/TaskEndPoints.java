package endpoints;

import io.restassured.response.Response;
import payload.tasks.Tasks;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.List;

public class TaskEndPoints {
	
	public static Response createTask(Tasks taskPayload, String accessToken, String xTenantId) {
		String correctedUrl = Routes.createTaskUrl.replace("http://", "https://");
		Response response = given()
		    .header("Authorization", "Bearer " + accessToken)
		    .header("x-tenant-id", xTenantId)
		    .header("Content-Type", "application/json")
		    .body(taskPayload)
		    .when()
		    .post(correctedUrl)
		    .then()
		    .extract()
		    .response();
		return response;
    }
	
	public static Response getAllTasks(String accessToken, String xTenantId, int page, int size, boolean getAll) {
        return given()
            .header("Authorization", "Bearer " + accessToken)
            .header("x-tenant-id", xTenantId)
            .header("Content-Type", "application/json")
            .when()
            .get(Routes.getAllTasksUrl + "?page=" + page + "&size=" + size + "&get_all=" + getAll)
            .then()
            .extract()
            .response();
    }
	
	public static Response getMyTasks(String accessToken, String xTenantId, String dayWise, int page, int size, boolean getAll) {
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .header("x-tenant-id", xTenantId)
                .queryParam("day_wise", dayWise)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("get_all", getAll)
                .when()
                .get(Routes.getMyTasksUrl)
                .then()
                .extract()
                .response();
    }
	
	public static Response getRecentProjects(String accessToken, String xTenantId) {
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .header("x-tenant-id", xTenantId)
                .when()
                .get(Routes.getRecentProjectsUrl)
                .then()
                .extract()
                .response();
    }
	
	 public static Response searchTasks(String accessToken, String xTenantId, String projectId, String query, int page, int size, boolean getAll) {
	        return given()
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .queryParam("project_id", projectId)
	                .queryParam("query", query)
	                .queryParam("page", page)
	                .queryParam("size", size)
	                .queryParam("get_all", getAll)
	                .when()
	                .get(Routes.searchTasksUrl)
	                .then()
	                .extract()
	                .response();
	    }
	 
	 public static Response getTaskTree(String accessToken, String xTenantId, int page, int size, boolean getAll) {
	        return given()
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .queryParam("page", page)
	                .queryParam("size", size)
	                .queryParam("get_all", getAll)
	                .when()
	                .get(Routes.getTaskTreeUrl)
	                .then()
	                .extract()
	                .response();
	    }
	 
	 public static Response getSubTasks(String accessToken, String xTenantId, String taskId, int page, int size, boolean getAll) {
	        return given()
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .pathParam("taskId", taskId)
	                .queryParam("page", page)
	                .queryParam("size", size)
	                .queryParam("get_all", getAll)
	                .when()
	                .get(Routes.getSubTasksUrl)
	                .then()
	                .extract()
	                .response();
	    }
	 
	 public static Response getTask(String accessToken, String xTenantId, String taskId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .header("Content-Type", "application/json")
	            .when()
	            .get(Routes.getTaskUrl, taskId)
	            .then()
	            .extract()
	            .response();
	    }
	 
	 public static Response updateTask(String accessToken, String xTenantId, String taskId, Tasks taskPayload) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .header("Content-Type", "application/json")
	            .body(taskPayload)
	            .when()
	            .put(Routes.updateTaskUrl, taskId)
	            .then()
	            .extract()
	            .response();
	    }
	 
	 public static Response deleteTask(String accessToken, String xTenantId, String taskId) {
		 return given()
		            .header("Authorization", "Bearer " + accessToken)
		            .header("x-tenant-id", xTenantId)
		            .header("Content-Type", "application/json")
		            .when()
		            .delete(Routes.deleteTaskUrl, taskId)
		            .then()
		            .extract()
		            .response();
	    }
	 
	 public static Response uploadFile(String accessToken, String xTenantId, String taskId, String projectId, File file) {
	        return given()
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .pathParam("taskId", taskId)
	                .multiPart("file", file)
	                .queryParam("project_id", projectId)
	                .when()
	                .post(Routes.fileUploadUrl)
	                .then()
	                .extract()
	                .response();
	    }
	 
	 public static Response groupUpdateStatus(List<Integer> taskIds, String taskState, String accessToken, String xTenantId) {
	        Tasks payload = new Tasks();
	        payload.setTask_ids(taskIds);
	        payload.setTask_state(taskState);
	        
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .header("Content-Type", "application/json")
	            .body(payload)
	            .when()
	            .post(Routes.groupUpdateStatusUrl)
	            .then()
	            .extract()
	            .response();
	    }

	    
	    public static Response groupUpdateAssignedTo(List<Integer> taskIds, Integer assignedTo, String accessToken, String xTenantId) {
	    	Tasks payload = new Tasks();
	        payload.setTask_ids(taskIds);
	        payload.setAssigned_to(assignedTo);

	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .header("Content-Type", "application/json")
	            .body(payload)
	            .when()
	            .post(Routes.groupUpdateAssignedToUrl)
	            .then()
	            .extract()
	            .response();
	    }

	    
	    public static Response groupUpdateDelete(List<Integer> taskIds, String accessToken, String xTenantId) {
	    	Tasks payload = new Tasks();
	        payload.setTask_ids(taskIds);

	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .header("Content-Type", "application/json")
	            .body(payload)
	            .when()
	            .post(Routes.groupUpdateDeleteUrl)
	            .then()
	            .extract()
	            .response();
	    }


}

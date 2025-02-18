package endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.projects.Projects;
import static io.restassured.RestAssured.given;

public class ProjectEndPoints {
	
	public static Response createProject(Projects projectRequest, String accessToken, String xTenantId) {
		Response response = RestAssured.given()
				.header("x-tenant-id", xTenantId)
				.header("Authorization", "Bearer " + accessToken)
				.contentType("application/json")
				.accept("application/json")
				.body(projectRequest)
				.post(Routes.createProjectUrl);

		return response;
	}
	
	 public static Response getProjectCardView(Projects projectRequest, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .queryParam("page", 2)
	                .queryParam("size", 50)
	                .queryParam("get_all", false)
	                .get(Routes.getProjectCardViewUrl);

	        return response;
	    }
	 
	 public static Response getProjectPriorities(Projects projectRequest, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .get(Routes.getProjectPriorityUrl);

	        return response;
	    }
	 
	 public static Response getProjectById(String projectId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .get(Routes.getProjectIdUrl + "/" + projectId);

	        return response;
	    }
	 
	 public static Response updateProject(String projectId, Projects projectRequest, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .body(projectRequest)
	                .put(Routes.updateProjectIdUrl + "/" + projectId);

	        return response;
	    }
	 
	 public static Response deleteProjectById(String projectId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .delete(Routes.deleteProjectIdUrl + "/" + projectId);

	        return response;
	    }
	 
	 public static Response getProjectManager(String projectId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .pathParam("projectId", projectId)
	                .get(Routes.getProjectManagerUrl);

	        return response;
	    }
	 
	 public static Response updateProjectManager(String projectId, String accessToken, String xTenantId, int managerId) {
	        String requestBody = "{ \"manager_id\": " + managerId + " }";
	        
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .pathParam("projectId", projectId)
	                .body(requestBody)
	                .patch(Routes.updateProjectManagerUrl);

	        return response;
	    }
	 
	 public static Response deleteProjectManager(String projectId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .delete(Routes.deleteProjectManagerUrl.replace("{projectId}", projectId));

	        return response;
	    }
	 
	 public static Response getProjectBudget(String projectId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .pathParam("projectId", projectId)
	                .get(Routes.getProjectBudgetUrl);

	        return response;
	    }
	 
	 public static Response updateProjectBudget(String projectId, String accessToken, String xTenantId, String payload) {
	        RequestSpecification request = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .pathParam("projectId", projectId)
	                .body(payload);

	        Response response = request.patch(Routes.updateProjectBudgetUrl);
	        return response;
	    }
	 
	 public static Response deleteProjectBudget(String projectId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .pathParam("projectId", projectId)
	                .delete(Routes.deleteProjectBudgetUrl);

	        return response;
	    }
	 
	 public static Response createProjectMember(Projects memberRequest, String accessToken, String xTenantId, String projectId) {
		    Response response = RestAssured.given()
		            .header("x-tenant-id", xTenantId)
		            .header("Authorization", "Bearer " + accessToken)
		            .contentType("application/json")
		            .accept("application/json")
		            .pathParam("projectId", projectId)  // Add path parameter here
		            .body(memberRequest)
		            .post(Routes.createProjectMemberUrl);

		    return response;
		}
	 
	 public static Response getAllProjectMember(int page, int size, boolean getAll, String accessToken, String xTenantId, String projectId) {
	        Projects requestPayload = new Projects(page, size, getAll);

	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .get(Routes.getAllProjectMemberUrl.replace("{projectId}", projectId));

	        return response;
	    }
	 
	 public static Response deleteProjectMember(String projectId, String memberId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .delete(Routes.deleteProjectMemberUrl
	                        .replace("{projectId}", projectId)
	                        .replace("{memberId}", memberId));

	        return response;
	    }
	 
	 public static Response multiUpdateProjectMembers(List<Integer> memberIds, int projectId, String accessToken, String xTenantId) {
	        Projects payload = new Projects(memberIds, projectId);

	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .body(payload)
	                .delete(Routes.multiUpdateProjectMembersUrl);

	        return response;
	    }
	 
	 public static Response getProjectClient(String projectId, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .get(Routes.getProjectClientUrl.replace("{projectId}", projectId));

	        return response;
	    }
	 
	 public static Response updateProjectClient(String projectId, Projects payload, String accessToken, String xTenantId) {
	        Response response = RestAssured.given()
	                .header("x-tenant-id", xTenantId)
	                .header("Authorization", "Bearer " + accessToken)
	                .contentType("application/json")
	                .accept("application/json")
	                .body(payload)
	                .patch(Routes.updateProjectClientUrl.replace("{projectId}", projectId));

	        return response;
	    }
	 
	 public static Response deleteProjectClient(String projectId, String clientId, String accessToken, String xTenantId) {
		    Map<String, Object> bodyParams = new HashMap<>();
		    bodyParams.put("id", clientId);

		    Response response = RestAssured.given()
		            .header("x-tenant-id", xTenantId)
		            .header("Authorization", "Bearer " + accessToken)
		            .contentType("application/json")
		            .accept("application/json")
		            .pathParam("projectId", projectId)
		            .body(bodyParams)
		            .delete(Routes.deleteProjectClientUrl);

		    return response;
		}
	 
	 public static Response getProjectBoard(String projectId, int page, int size, boolean getAll, String accessToken, String xTenantId) {
	        return given()
	                .baseUri(Routes.baseUrl)  // Use the base URL from Routes class
	                .basePath(Routes.getProjectBoardTodoStatusUrl)  // Use the endpoint path from Routes class
	                .pathParam("projectId", projectId)
	                .queryParam("page", page)
	                .queryParam("size", size)
	                .queryParam("get_all", getAll)
	                .header("Authorization", "Bearer " + accessToken)
	                .header("x-tenant-id", xTenantId)
	                .header("Content-Type", "application/json")
	                .header("Accept", "application/json")
	                .when()
	                .get();
	    }
	 
	 public static Response getProjectBoardByStatus(String projectId, int page, int size, boolean getAll, String accessToken, String xTenantId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .queryParam("page", page)
	            .queryParam("size", size)
	            .queryParam("get_all", getAll)
	            .when()
	            .get(Routes.getProjectBoardTodoStatusUrl, projectId);
	    }
	 
	 public static Response getDoingTasks(String projectId, int page, int size, boolean getAll, String accessToken, String xTenantId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .queryParam("page", page)
	            .queryParam("size", size)
	            .queryParam("get_all", getAll)
	            .when()
	            .get(Routes.getProjectBoardDoingTasksUrl, projectId);
	    }

	    public static Response getDoneTasks(String projectId, int page, int size, boolean getAll, String accessToken, String xTenantId) {
	        return given()
	            .header("Authorization", "Bearer " + accessToken)
	            .header("x-tenant-id", xTenantId)
	            .queryParam("page", page)
	            .queryParam("size", size)
	            .queryParam("get_all", getAll)
	            .when()
	            .get(Routes.getProjectBoardDoneTasksUrl, projectId);
	    }

}

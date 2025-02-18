package endpoints;

public class Routes {
	public static String baseUrl = "https://qa-osbaseleaf-api.andolasoft.co.in";

	// Login
	public static String loginUrl = baseUrl + "/v1/auth/jwt/login";

	// Project
	public static String createProjectUrl = baseUrl + "/projectsapi/projects/";
	public static String getProjectCardViewUrl = baseUrl + "/projectsapi/projects/cardview";

	public static String getProjectPriorityUrl = baseUrl + "/projectsapi/projects/priority";

	public static String getProjectIdUrl = baseUrl + "/projectsapi/projects";
	public static String updateProjectIdUrl = baseUrl + "/projectsapi/projects";
	public static String deleteProjectIdUrl = baseUrl + "/projectsapi/projects";

	public static String getProjectManagerUrl = baseUrl + "/projectsapi/projects/{projectId}/manager";
	public static String updateProjectManagerUrl = baseUrl + "/projectsapi/projects/{projectId}/manager";
	public static String deleteProjectManagerUrl = baseUrl + "/projectsapi/projects/{projectId}/manager";

	public static String getProjectBudgetUrl = baseUrl + "/projectsapi/projects/{projectId}/budget";
	public static String updateProjectBudgetUrl = baseUrl + "/projectsapi/projects/{projectId}/budget";
	public static String deleteProjectBudgetUrl = baseUrl + "/projectsapi/projects/{projectId}/budget";

	public static String createProjectMemberUrl = baseUrl + "/projectsapi/projects/{projectId}/members";
	public static String getAllProjectMemberUrl = baseUrl + "/projectsapi/projects/{projectId}/members";
	public static String deleteProjectMemberUrl = baseUrl + "/projectsapi/projects/{projectId}/members/{memberId}";
	public static String multiUpdateProjectMembersUrl = baseUrl + "/projectsapi/projects/members/multiupdate";

	public static String getProjectClientUrl = baseUrl + "/projectsapi/projects/756/client";
	public static String updateProjectClientUrl = baseUrl + "/projectsapi/projects/756/client";
	public static String deleteProjectClientUrl = baseUrl + "/projectsapi/projects/{projectId}/client";

	// Project Board
	public static String getProjectBoardUrl = baseUrl + "/projectsapi/board/board/{projectId}/tasks";
	public static String getProjectBoardTodoStatusUrl = baseUrl + "/projectsapi/board/board/{projectId}/todo/tasks";
	public static String getProjectBoardDoingTasksUrl = baseUrl + "/projectsapi/board/board/{projectId}/doing/tasks";
	public static String getProjectBoardDoneTasksUrl = baseUrl + "/projectsapi/board/board/{projectId}/done/tasks";

	// Tasks
	public static String createTaskUrl = baseUrl + "/tasksapi/tasks/";

	public static String getAllTasksUrl = baseUrl + "/tasksapi/tasks/";
	public static String getMyTasksUrl = baseUrl + "/tasksapi/tasks/mytasks/";
	public static String getRecentProjectsUrl = baseUrl + "/tasksapi/tasks/project/mytask";
	public static String searchTasksUrl = baseUrl + "/tasksapi/tasks/search";
	public static String getTaskTreeUrl = baseUrl + "/tasksapi/tasks/tree";
	public static String getSubTasksUrl = baseUrl + "/tasksapi/tasks/{taskId}/subtasks";

	public static String getTaskUrl = baseUrl + "/tasksapi/tasks/{taskId}";
	public static String updateTaskUrl = baseUrl + "/tasksapi/tasks/{taskId}";
	public static String deleteTaskUrl = baseUrl + "/tasksapi/tasks/{taskId}";

	public static String fileUploadUrl = baseUrl + "/tasks/{taskId}/file_upload";

	public static String groupUpdateStatusUrl = baseUrl + "/tasksapi/tasks/group_update?action=status";
	public static String groupUpdateAssignedToUrl = baseUrl + "/tasksapi/tasks/group_update?action=assigned_to";
	public static String groupUpdateDeleteUrl = baseUrl + "/tasksapi/tasks/group_update?action=delete";

	// Milestones
	public static String createMilestoneUrl = baseUrl + "/milestonesapi/milestones/";
	public static String getAllMilestoneUrl = baseUrl + "/milestonesapi/milestones/";
	public static String getMilestoneUrl = baseUrl + "/milestonesapi/milestones/{milestoneId}";
	public static String updateMilestoneUrl = baseUrl + "/milestonesapi/milestones/{milestoneId}";
	public static String deleteMilestoneUrl = baseUrl + "/milestonesapi/milestones/{milestoneId}";
	public static String convertTaskToMilestoneUrl = baseUrl + "/milestonesapi/milestones/convert_to_milestone";

	// Milesone_tasks
	public static String createMilestoneTaskUrl = baseUrl + "/milestones_tasksapi/milestone_tasks/";
	public static String getAllMilestoneTasksUrl = baseUrl
			+ "/milestones_tasksapi/milestone_tasks/?page=1&size=50&get_all=false";
	public static String getMilestoneTasksUrl = baseUrl + "/milestones_tasksapi/milestone_tasks/{milestoneId}";
	public static String updateMilestoneTaskUrl = baseUrl + "/milestones_tasksapi/milestone_tasks/{id}";
	public static String deleteMilestoneTaskUrl = baseUrl + "/milestones_tasksapi/milestone_tasks/{milestoneId}";

	// Users
	public static String getUserProfileUrl = baseUrl + "/usersapi/users/profile";
	public static String updateUserProfileUrl = baseUrl + "/usersapi/users/profile";
	public static String updateProfilePhotoUrl = baseUrl + "/usersapi/users/profilephoto";
	
	public static String inviteUserUrl = baseUrl + "/usersapi/users/invite";
	public static String inviteUserVerifyUrl = baseUrl + "/usersapi/users/invite/verify";
	
	public static String getTenantUrl = baseUrl + "/usersapi/users/tenant";
	public static String updateTenantUrl = baseUrl + "/usersapi/users/tenant";
	public static String getTenantOwnerUrl = baseUrl + "/usersapi/users/tenantowner";
	
	public static String createResourceSkillUrl = baseUrl + "/usersapi/users/skills";
	public static String getAllSkillsUrl = baseUrl + "/usersapi/users/skills?page=1&size=50&get_all=false";
	public static String getAllTenantUrl = baseUrl + "/usersapi/users/tenants?page=1&size=50&get_all=false";
	
	public static String assignRoleUrl = baseUrl + "/usersapi/users/role";
	public static String getAllRoleUrl = baseUrl + "/usersapi/users/role?page=1&size=50&get_all=false";
	public static String deleteRoleUrl = baseUrl + "/usersapi/users/role";
	
	public static String getCountryUrl = baseUrl + "/usersapi/users/countries";
	public static String getCurrencyUrl = baseUrl + "/usersapi/users/currencies";
	public static String getTimezoneUrl = baseUrl + "/usersapi/users/timezones";
	
	// Registered user
	public static String preRegisterUrl = baseUrl + "/registeredusersapi/pre-register";
	public static String getPreRegisterUrl = baseUrl + "/registeredusersapi/pre-register/?page=1&size=50&get_all=false";
	
	
	

}

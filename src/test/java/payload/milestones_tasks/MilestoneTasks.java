package payload.milestones_tasks;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MilestoneTasks {
	@JsonProperty("project_id")
	private String projectId;

	@JsonProperty("milestone_id")
	private String milestoneId;

	@JsonProperty("task_id")
	private String taskId;

	private int id;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(String milestoneId) {
		this.milestoneId = milestoneId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

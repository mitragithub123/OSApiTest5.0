package payload.milestones;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Milestones {

	@JsonProperty("project_id")
	private String projectId;

	@JsonProperty("milestone_title")
	private String milestoneTitle;

	private String description;

	@JsonProperty("start_date")
	private String startDate;

	@JsonProperty("end_date")
	private String endDate;

	@JsonProperty("estimated_hour")
	private int estimatedHour;

	@JsonProperty("assign_to")
	private String assignTo;

	@JsonProperty("task_id")
	private int taskId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMilestoneTitle() {
		return milestoneTitle;
	}

	public void setMilestoneTitle(String milestoneTitle) {
		this.milestoneTitle = milestoneTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getEstimatedHour() {
		return estimatedHour;
	}

	public void setEstimatedHour(int estimatedHour) {
		this.estimatedHour = estimatedHour;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}

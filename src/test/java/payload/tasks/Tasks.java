package payload.tasks;

import java.util.List;

public class Tasks {
	private String project_id;
	private String task_title;
	private String task_type;
	private String task_description;
	private String taskPriority;
	private List<Integer> task_ids;
	private Integer assigned_to;
	private String task_state;

	// Constructors
	public Tasks() {
	}

	public Tasks(String project_id, String task_title, String task_type, String task_description, String taskPriority) {
		this.project_id = project_id;
		this.task_title = task_title;
		this.task_type = task_type;
		this.task_description = task_description;
		this.taskPriority = taskPriority;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getTask_title() {
		return task_title;
	}

	public void setTask_title(String task_title) {
		this.task_title = task_title;
	}

	public String getTask_type() {
		return task_type;
	}

	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}

	public String getTask_description() {
		return task_description;
	}

	public void setTask_description(String task_description) {
		this.task_description = task_description;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public List<Integer> getTask_ids() {
		return task_ids;
	}

	public void setTask_ids(List<Integer> task_ids) {
		this.task_ids = task_ids;
	}

	public Integer getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(Integer assigned_to) {
		this.assigned_to = assigned_to;
	}

	public String getTask_state() {
		return task_state;
	}

	public void setTask_state(String task_state) {
		this.task_state = task_state;
	}

}

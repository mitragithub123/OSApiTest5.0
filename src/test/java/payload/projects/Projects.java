package payload.projects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

// POJO class
public class Projects {
	private String project_name;
	private String start_date;
	private String project_priority;
	private String project_description;
	private String end_date;
	private int[] resource_ids;
	private List<Integer> member_ids;
	private int project_id;
	private String client_name;
	private String client_contact;
	private String client_email;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Projects(int id) {
		this.id = id;
	}

	public Projects(String client_name, String client_contact, String client_email) {
		this.client_name = client_name;
		this.client_contact = client_contact;
		this.client_email = client_email;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_contact() {
		return client_contact;
	}

	public void setClient_contact(String client_contact) {
		this.client_contact = client_contact;
	}

	public String getClient_email() {
		return client_email;
	}

	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}

	public Projects(List<Integer> member_ids, int project_id) {
		this.member_ids = member_ids;
		this.project_id = project_id;
	}

	public List<Integer> getMember_ids() {
		return member_ids;
	}

	public void setMember_ids(List<Integer> member_ids) {
		this.member_ids = member_ids;
	}

	@JsonProperty("page")
	private int page;

	@JsonProperty("size")
	private int size;

	@JsonProperty("get_all")
	private boolean getAll;

	public Projects(int page, int size, boolean getAll) {
		this.page = page;
		this.size = size;
		this.getAll = getAll;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isGetAll() {
		return getAll;
	}

	public void setGetAll(boolean getAll) {
		this.getAll = getAll;
	}

	public Projects() {
		// Default constructor
	}

	public Projects(int project_id, int[] resource_ids) {
		this.project_id = project_id;
		this.resource_ids = resource_ids;
	}

	public String getProject_name() {
		return project_name;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int[] getResource_ids() {
		return resource_ids;
	}

	public void setResource_ids(int[] resource_ids) {
		this.resource_ids = resource_ids;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String dateAndTime) {
		this.start_date = dateAndTime;
	}

	public String getProject_priority() {
		return project_priority;
	}

	public void setProject_priority(String project_priority) {
		this.project_priority = project_priority;
	}

	public String getProject_description() {
		return project_description;
	}

	public void setProject_description(String project_description) {
		this.project_description = project_description;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}

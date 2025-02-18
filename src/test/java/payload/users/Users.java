package payload.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {
	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("invite_email")
	private String inviteEmail;

	@JsonProperty("invite_role")
	private String inviteRole;

	@JsonProperty("invite_first_name")
	private String inviteFirstName;

	@JsonProperty("invite_last_name")
	private String inviteLastName;

	@JsonProperty("tenant_id")
	private int tenantId;

	@JsonProperty("invite_token")
	private String inviteToken;

	@JsonProperty("name")
	private String name;

	@JsonProperty("website")
	private String website;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("tenant_description")
	private String tenantDescription;

	@JsonProperty("skill_id")
	private List<Integer> skillId;

	@JsonProperty("resource_id")
	private int resourceId;

	@JsonProperty("role_name")
	private String roleName;

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("is_active")
	private int isActive;
	
	@JsonProperty("role_id")
	private int roleId;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getInviteEmail() {
		return inviteEmail;
	}

	public void setInviteEmail(String inviteEmail) {
		this.inviteEmail = inviteEmail;
	}

	public String getInviteRole() {
		return inviteRole;
	}

	public void setInviteRole(String inviteRole) {
		this.inviteRole = inviteRole;
	}

	public String getInviteFirstName() {
		return inviteFirstName;
	}

	public void setInviteFirstName(String inviteFirstName) {
		this.inviteFirstName = inviteFirstName;
	}

	public String getInviteLastName() {
		return inviteLastName;
	}

	public void setInviteLastName(String inviteLastName) {
		this.inviteLastName = inviteLastName;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getInviteToken() {
		return inviteToken;
	}

	public void setInviteToken(String inviteToken) {
		this.inviteToken = inviteToken;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTenantDescription() {
		return tenantDescription;
	}

	public void setTenantDescription(String tenantDescription) {
		this.tenantDescription = tenantDescription;
	}

	public List<Integer> getSkillId() {
		return skillId;
	}

	public void setSkillId(List<Integer> skillId) {
		this.skillId = skillId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	

}

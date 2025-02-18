package payload.preregister;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreRegister {
	@JsonProperty("email")
	private String email;

	@JsonProperty("is_converted")
	private int isConverted;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsConverted() {
		return isConverted;
	}

	public void setIsConverted(int isConverted) {
		this.isConverted = isConverted;
	}
}

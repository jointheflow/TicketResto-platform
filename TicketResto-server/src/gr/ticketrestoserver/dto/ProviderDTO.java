package gr.ticketrestoserver.dto;

public class ProviderDTO {
	public long id;
	public String email;
	public TokenDTO token;
	public String description;
	public String address;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public TokenDTO getToken() {
		return token;
	}
	public void setToken(TokenDTO token) {
		this.token = token;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}

package gr.ticketrestoserver.dto;

import java.util.List;

public class CustomerDTO {
	public long id;
	public String email;
	public TokenDTO token;
	public List<RestoDTO> resti;
	
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
	public List<RestoDTO> getResti() {
		return resti;
	}
	public void setResti(List<RestoDTO> resti) {
		this.resti = resti;
	}
	
	
}

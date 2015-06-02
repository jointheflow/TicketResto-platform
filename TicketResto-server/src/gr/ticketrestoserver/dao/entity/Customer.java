package gr.ticketrestoserver.dao.entity;



import com.google.appengine.api.datastore.Key;



public class Customer {

	private Key id;
	
	public void setId(Key id) {
		this.id = id;
	}


	private String email;
	

	private String password;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Key getId() {
		return id;
	}
	
	
	
}

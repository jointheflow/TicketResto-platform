package gr.ticketrestoserver.entity;


import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class Customer {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Unique
	@Persistent
	private String email;
	
	@Persistent
	private String password;

	
	@NotPersistent
	private AuthToken token;
	
	@NotPersistent
	private List<Resto> resti;
	
	
	
	public AuthToken getToken() {
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

	public List<Resto> getResti() {
		return resti;
	}

	public void setResti(List<Resto> resti) {
		this.resti = resti;
	}

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

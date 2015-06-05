package gr.ticketrestoserver.dao.entity;



import java.util.Date;


import com.google.appengine.api.datastore.Key;


public class AuthToken {
	
	
	
	private Key tokenId;

	
	private String userEmail;
	
	
	private Date expiration;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setTokenId(Key tokenId) {
		this.tokenId = tokenId;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Key getTokenId() {
		return tokenId;
	}
	
	
	
}

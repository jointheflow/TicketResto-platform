package gr.ticketrestoserver.dao.entity;


import com.google.appengine.api.datastore.Key;



public class Provider {

	
	private Key id;
	

	private String name;
	
	
	private String email;
	
	
	private String password;
	
	
	private String address;
	
	
	private String cap;
	
	//comune
	
	private String district;
	
	//provincia
	
	private String region;
	
	//nazione
	
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}
	
	
	
	
	
}

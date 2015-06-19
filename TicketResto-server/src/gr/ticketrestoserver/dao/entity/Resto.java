package gr.ticketrestoserver.dao.entity;

import java.util.Date;

import com.google.appengine.api.datastore.Key;



public class Resto {
	
	
	private Key id;
	
	
	private Provider provider;
	
	
	
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	
	private Date expirationDate;
	
	
	private Double amount;
	
	
	private String providerSignature;
	
	
	
	public String getProviderSignature() {
		return providerSignature;
	}

	public void setProviderSignature(String providerSignature) {
		this.providerSignature = providerSignature;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Key getId() {
		return id;
	}

	public void setCustomer(Customer customer) {
		
		this.customer=customer;
	}

	public void setId(Key id) {
		this.id = id;
	}

	
	
	
}

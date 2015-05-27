package gr.ticketrestoserver.dao.entity;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Owned;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Resto {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	//@Persistent
	@Persistent(defaultFetchGroup="true")
	@Unowned
	private Provider provider;
	
	
	//@Persistent
	@Persistent(defaultFetchGroup="true")
	@Unowned
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	@Persistent
	private Date expirationDate;
	
	@Persistent
	private Double amount;
	
	@Persistent
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

	
	
	
}

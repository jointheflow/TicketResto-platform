package gr.ticketrestoserver.entity;

import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Uniques;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
@Uniques({@Unique(name="PROVIDER_CUSTOMER", members={"provider","customer"})})
public class Resto {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	@Column(name="id_provider")
    private Provider provider;
	
	@Persistent
	@Column(name="id_customer")
	private Customer customer;
	
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	@Persistent
	private Date expirationDate;
	
	@Persistent
	private Double amount;
	
	
}

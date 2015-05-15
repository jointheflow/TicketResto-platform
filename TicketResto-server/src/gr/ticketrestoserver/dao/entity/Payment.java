package gr.ticketrestoserver.dao.entity;

import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class Payment {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	@Column(name="idProvider")
    private Provider provider;
	
	@Persistent
	@Column(name="idCustomer")
	private Customer customer;
	
	
	@Persistent
	private Date paymentDate;
	
	@Persistent
	private Double amount;
	
	@Persistent
	private Double cashAmount;
	
	@Persistent
	private Double ticketAmount;
	
	@Persistent
	private Double restoAmount;

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

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Double getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(Double ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public Double getRestoAmount() {
		return restoAmount;
	}

	public void setRestoAmount(Double restoAmount) {
		this.restoAmount = restoAmount;
	}

	public Key getId() {
		return id;
	}
	
	

	

}

package gr.ticketrestoserver.entity;

import java.util.Date;

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
	private Key providerId;
	
	@Persistent
	private Key customerId;
	
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

	public Key getProviderId() {
		return providerId;
	}

	public void setProviderId(Key providerId) {
		this.providerId = providerId;
	}

	public Key getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Key customerId) {
		this.customerId = customerId;
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

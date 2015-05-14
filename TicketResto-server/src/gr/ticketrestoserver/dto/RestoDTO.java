package gr.ticketrestoserver.dto;

import java.util.Date;
//RestoDTO is a child of CustomerDTO and lives only with a Customer
public class RestoDTO {
	public long id;
	public Date expirationDate;
	public Double amount;
	public ProviderDTO provider;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public ProviderDTO getProvider() {
		return provider;
	}
	public void setProvider(ProviderDTO provider) {
		this.provider = provider;
	}
	
}

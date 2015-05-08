package gr.ticketrestoserver.entity;

import java.util.List;

public class CustomerAndRestoCollection {
	private Customer customer;
	private List<Resto> resti;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Resto> getResti() {
		return resti;
	}
	public void setResti(List<Resto> resti) {
		this.resti = resti;
	}
}

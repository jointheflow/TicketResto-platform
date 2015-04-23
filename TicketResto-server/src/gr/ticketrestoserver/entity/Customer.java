package gr.ticketrestoserver.entity;


import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

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

	
	//@Element(column="CUSTOMER_ID")
	//@Persistent
	//@Persistent(mappedBy="parentCustomer")
	//private List<Resto> resti;
	
			
	
	
	/*public void fetchResti(List<Resto> resti) {
		this.resti = resti;
	}
*/
	/*Add the resto parameter if does not exists a resto defined with a provider. Otherwhise remove the resto found and add th resto passed
	 * as parameter*/
	/*public void updateRestoOfProvider(Resto resto) {
		if (resti == null) resti = new ArrayList<Resto>();
		Resto restoCurrent = getRestoOfProvider(resto.getProvider());
		if (restoCurrent == null)
			resti.add(resto);
		else {
			resti.remove(restoCurrent);
			resti.add(resto);
			
		}
	}
	*/
	/*public List<Resto> getResti() {
		
		return resti;
	}	
*/
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

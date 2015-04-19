package gr.ticketrestoserver.entity;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jdo.annotations.Element;
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

	@Persistent
	@Element(column="CUSTOMER_ID")
    private Set<Resto> resti = new HashSet<Resto>();
	
			
	/*Return a Resto instance of a provider passed as parameter if exits in the resti Set. Null otherwhise*/
	public Resto getRestoOfProvider(Provider provider) {
		Iterator<Resto> i = (Iterator<Resto>) resti.iterator();
		while (i.hasNext()) {
			Resto resto = (Resto) i.next();
			if (resto.getProvider().getId()==provider.getId())
				return resto;
			
		}
		return null;
	}
	
	/*Add the resto parameter if does not exists a resto defined with a provider. Otherwhise remove the resto found and add th resto passed
	 * as parameter*/
	public void updateRestoOfProvider(Resto resto) {
		Resto restoCurrent = getRestoOfProvider(resto.getProvider());
		if (restoCurrent == null)
			resti.add(resto);
		else {
			resti.remove(restoCurrent);
			resti.add(resto);
			
		}
			
				
		
	}
	
	public Set<Resto> getResti() {
		
		return resti;
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


	public Key getId() {
		return id;
	}
	
	
	
}

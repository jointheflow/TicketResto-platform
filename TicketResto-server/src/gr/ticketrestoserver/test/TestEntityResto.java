package gr.ticketrestoserver.test;

import static org.junit.Assert.*;


import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.entity.Customer;

import gr.ticketrestoserver.entity.Provider;
import gr.ticketrestoserver.entity.Resto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestEntityResto {
	private final LocalServiceTestHelper helper =  
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());  
	
	
	@Before  
    public void setUp() {  
        helper.setUp();          
    }  
	  
	@After  
    public void tearDown() {  
        helper.tearDown();  
    }  

	
	@Test
	public void test() {
		
		//set up a provider
		Key idProvider = null;
		
		Provider provider = new Provider();
		provider.setEmail("provider@gmail.com");
		provider.setPassword("1234qwer");
		idProvider = RestoDAO.addProvider(provider);
		assertTrue(idProvider != null);
		System.out.println("provider "+idProvider+" added!");

		
		//set up a resto		
		Resto resto_new = new Resto();
		resto_new.setAmount(new Double(222));
		//set the resto's provider
		resto_new.setProvider(provider);
		
		
		//add resto to the customer
		Key idCustomer = null;
		
		Customer customer = new Customer();
		customer.setEmail("gr@gmail.com");
		customer.setPassword("1234qwer");
		customer.updateRestoOfProvider(resto_new);
		idCustomer = RestoDAO.addCustomer(customer);
		assertTrue(idCustomer != null);
		assertTrue(customer.getResti().size()>0);
		System.out.println("customer "+idCustomer+" added!");
		
		/*
		Set resti = customer.getResti();
		System.out.println("resti "+ resti );
		Iterator i = (Iterator)resti.iterator();
		
		while (i.hasNext()) {
			Resto r = (Resto) i.next();
			System.out.println(r.getAmount());
			System.out.println(r.getId());
			System.out.println(r.getProvider().getEmail());
			System.out.println(r.getProvider().getId().getId());
			assertTrue(r.getProvider() == provider);
		}
		*/
		//idProvider = RestoDAO.addProvider(provider);
		
		//System.out.println("provider "+idProvider+" added!");
		
		
		
	}

}
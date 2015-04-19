package gr.ticketrestoserver.test;

import static org.junit.Assert.*;



import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.entity.Customer;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestEntityCustomer {
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
		
		//add  customer
		Key idCustomer = null;
		
		Customer customer = new Customer();
		customer.setEmail("gr@gmail.com");
		customer.setPassword("1234qwer");
		
		idCustomer = RestoDAO.addCustomer(customer);
		assertTrue(idCustomer != null);
		System.out.println("customer "+idCustomer+" added!");
				
		
		//add the same customer with same email
		Key idCustomer_2 = null;
		
		Customer customer_2 = new Customer();
		customer_2.setEmail("gr@gmail.com");
		customer_2.setPassword("1234qwer");
		
		idCustomer_2 = RestoDAO.addCustomer(customer_2);
		//assertTrue(idCustomer_2 == null);
		System.out.println("customer_2 "+idCustomer_2+" added!");
		
		
	}

}

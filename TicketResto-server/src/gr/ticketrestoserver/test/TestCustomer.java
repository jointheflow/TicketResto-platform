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

public class TestCustomer {
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
		
		
		Key idCustomer = null;
		
		Customer customer = new Customer();
		customer.setEmail("gr@gmail.com");
		customer.setPassword("1234qwer");
		
		idCustomer = RestoDAO.addCustomer(customer);
		assert(idCustomer != null);
	}

}

package gr.ticketrestoserver.test;

import static org.junit.Assert.*;
import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.entity.Customer;
import gr.ticketrestoserver.entity.Payment;
import gr.ticketrestoserver.entity.Provider;

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
		System.out.println("customer "+idCustomer+" added!");
		
		Key idProvider = null;
		
		Provider provider = new Provider();
		provider.setEmail("provider@gmail.com");
		provider.setPassword("1234qwer");
		
		idProvider = RestoDAO.addProvider(provider);
		assert(idProvider != null);
		System.out.println("provider "+idProvider+" added!");
		
		Key idPayment = null;
		
		Payment payment = new Payment();
		payment.setCustomer(customer);
		payment.setProvider(provider);
		payment.setAmount(new Double(200));
		payment.setCashAmount(new Double(150));
		payment.setTicketAmount(new Double(50));
		
		idPayment = RestoDAO.addPayment(payment);
		assert(idPayment != null);
		System.out.println("payment "+idPayment+" added!");
	}

}

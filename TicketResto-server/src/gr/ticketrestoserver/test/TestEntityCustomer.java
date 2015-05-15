package gr.ticketrestoserver.test;

import static org.junit.Assert.*;
import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.exception.MandatoryFieldException;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.dao.exception.WrongUserOrPasswordException;







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
		
		try {
			idCustomer = RestoDAO.addCustomer(customer);
			assertTrue(idCustomer != null);
			System.out.println("customer "+idCustomer+" added!");
		} catch (UniqueConstraintViolationExcpetion e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		
		//try to add the same customer with same email
		Key idCustomer_2 = null;
		
		Customer customer_2 = new Customer();
		customer_2.setEmail("gr@gmail.com");
		customer_2.setPassword("1234qwer");
		
		try {
			idCustomer_2 = RestoDAO.addCustomer(customer_2);
		} catch (UniqueConstraintViolationExcpetion e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			assertTrue(idCustomer_2 == null);
			System.out.println("customer_2 "+idCustomer_2+" not added!");
			
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//get a user with correct email and password
		Customer customer_3;
		try {
			customer_3 = RestoDAO.getCustomerByEmail("gr@gmail.com", "1234qwer");
			assertTrue(customer_3 != null);
		} catch (WrongUserOrPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//get a user with wrong email and correct password
		Customer customer_4= null;
		try {
			customer_4 = RestoDAO.getCustomerByEmail("grrrrr@gmail.com", "1234qwer");
			
		} catch (WrongUserOrPasswordException e) {
			// TODO Auto-generated catch block
			assertTrue(customer_4 == null);
			e.printStackTrace();
		}
		
		
		//get a user with correct email and wrong password
		Customer customer_5= null;
		try {
			customer_5 = RestoDAO.getCustomerByEmail("gr@gmail.com", "345qwer");
			
		} catch (WrongUserOrPasswordException e) {
			// TODO Auto-generated catch block
			assertTrue(customer_5 == null);
			e.printStackTrace();
		}
		
		
	}

}

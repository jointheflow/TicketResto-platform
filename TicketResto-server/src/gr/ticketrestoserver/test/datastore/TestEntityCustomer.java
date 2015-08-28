package gr.ticketrestoserver.test.datastore;

import static org.junit.Assert.*;

import javax.jdo.JDOObjectNotFoundException;

import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.exception.MandatoryFieldException;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.dao.exception.WrongUserOrPasswordException;









import org.gianluca.logbook.dao.googledatastore.entity.RestoDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestEntityCustomer {
	private final LocalServiceTestHelper helper =  
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy());  
	  
	
	
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
		Key idCustomer_1 = null;
		
		Customer customer = new Customer();
		customer.setEmail("gr@gmail.com");
		customer.setPassword("1234qwer");
		
		try {
			idCustomer_1 = RestoDAO.addCustomer(customer);
			assertTrue(idCustomer_1 != null);
			System.out.println("customer "+idCustomer_1+" added!");
		} catch (UniqueConstraintViolationExcpetion e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//add  customer
		Key idCustomer_same_email = null;
		
		Customer customer_same_email = new Customer();
		customer_same_email.setEmail("same_email@gmail.com");
		customer_same_email.setPassword("1234qwer");
		
		try {
			idCustomer_same_email = RestoDAO.addCustomer(customer);
			assertTrue(idCustomer_same_email != null);
			System.out.println("customer "+idCustomer_same_email+" added!");
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
			System.out.println("customer found!"+ customer_3.getId());
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
		
	
		//update a customer with different email
		Customer customer_6=null;
		try {
			customer_6 = RestoDAO.getCustomerByEmail("gr@gmail.com", "1234qwer");
		} catch (WrongUserOrPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(customer_6 != null);
		
		Long id= customer_6.getId().getId();
		customer_6.setEmail("gr_6@gmail.com");
		customer_6.setPassword("1234qwer_6");
		
		try {
			RestoDAO.updateCustomer(id, customer_6);
		} catch (UniqueConstraintViolationExcpetion | MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			customer_6 = RestoDAO.getCustomerByEmail("gr_6@gmail.com", "1234qwer_6");
		} catch (WrongUserOrPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(customer_6 != null);
		
		
		//delete customer
		Long idDeleted = idCustomer_1.getId();
		RestoDAO.deleteCustomerById(idDeleted);
		Customer deletedCustomer = null;
		try {
			deletedCustomer = RestoDAO.getCustomerById(idDeleted);
		}catch (JDOObjectNotFoundException e) {
			assertTrue(deletedCustomer == null);
			
		}
		
	}

}

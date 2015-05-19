package gr.ticketrestoserver.test;

import static org.junit.Assert.*;
import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.entity.Provider;
import gr.ticketrestoserver.dao.entity.Resto;
import gr.ticketrestoserver.dao.exception.MandatoryFieldException;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.dao.exception.WrongUserOrPasswordException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestEntityResto {
	
	
	private String testProviderEmailA="testProviderA@gmail.com";
	private String testProviderEmailB="testProviderB@gmail.com";
			
	private String testCustomerEmail="testCustomer@gmail.com";
	
	private Double resto_pre_amount= new Double(222);
	
	private final LocalServiceTestHelper helper =  
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());  
	
	
	@Before  
    public void setUp() {  
        helper.setUp();
        //configuring providers and customer in the datastore
        //set up providers
  		
        Key idProviderA = null;
  		Provider testProviderA = new Provider();
  		testProviderA.setEmail(testProviderEmailA);
  		testProviderA.setPassword("1234qwer");
  		testProviderA.setName("ProviderA");
  		try {
			idProviderA = RestoDAO.addProvider(testProviderA);
		} catch (MandatoryFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UniqueConstraintViolationExcpetion e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
  		System.out.println("providerA "+idProviderA+" set!"+testProviderA);
        
  		Key idProviderB = null;
  		Provider testProviderB = new Provider();
  		testProviderB.setEmail(testProviderEmailB);
  		testProviderB.setPassword("1234qwer");
  		testProviderB.setName("ProviderB");
  		try {
			idProviderB = RestoDAO.addProvider(testProviderB);
		} catch (MandatoryFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UniqueConstraintViolationExcpetion e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
  		System.out.println("providerB "+idProviderB+" set!"+testProviderB);
  		
  		//set up a customer with a resto of provider B already in place
		Key idCustomer = null;	
		Customer testCustomer = new Customer();
		testCustomer.setEmail(testCustomerEmail);
		testCustomer.setPassword("1234qwer");
		try {
			idCustomer = RestoDAO.addCustomer(testCustomer);
		} catch (UniqueConstraintViolationExcpetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("customer "+idCustomer+" added!"+testCustomer);
		
		
		//set up a resto		
		Key idResto = null;
		Resto resto_new = new Resto();
		resto_new.setAmount(resto_pre_amount);
		//set the resto's provider and customer
		resto_new.setProvider(testProviderB);
		resto_new.setCustomer(testCustomer);
		//persists resto_new
		idResto = RestoDAO.updateResto(resto_new);
		System.out.println("resto id:"+idResto+" provider id:"+resto_new.getProvider().getId()+" customer id:"+resto_new.getCustomer().getId()+" updated!"+resto_new);
		
  		
    }  
	  
	@After  
    public void tearDown() {  
        helper.tearDown();  
    }  

	
	
	
	@Test
	public void testNewRestoProviderA() {
		//retrieve the providerA by email
		Provider providerA = RestoDAO.getProviderByEmail(testProviderEmailA);
		assertTrue(providerA!=null);
		System.out.println("Find provider by email "+testProviderEmailA +" "+ providerA);
		
		//retrieve the customer by email
		Customer customer=null;
		try {
			customer = RestoDAO.getCustomerByEmail(testCustomerEmail, "1234qwer");
		} catch (WrongUserOrPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(customer != null);
		System.out.println("Find customer by email "+testCustomerEmail+" "+ customer);
		
		//add resto to the customer for providerA.
		//prior fecth a resto if exists
		Resto resto = RestoDAO.getResto(customer.getId().getId(), providerA.getId().getId());
		//resto should not exists
		assertTrue(resto==null);
		
		//create new resto
		if (resto==null){
			resto = new Resto();
			resto.setAmount(new Double(125));
			resto.setCustomer(customer);
			resto.setProvider(providerA);
			//update customer
			RestoDAO.updateResto(resto);
			assertTrue(resto.getAmount().equals(new Double(125)));
			System.out.println("resto id:"+resto.getId()+" provider id:"+resto.getProvider().getId()+" customer id:"+resto.getCustomer().getId()+" updated!"+resto);
		}
		
		
		
	}
	
	@Test
	public void testUpdateRestoProviderB() {
		//retrieve the providerB by email
		Provider providerB = RestoDAO.getProviderByEmail(testProviderEmailB);
		assertTrue(providerB!=null);
		System.out.println("Find provider by email "+testProviderEmailB +" "+ providerB);
		
		//retrieve the customer by email
		Customer customer=null;
		try {
			customer = RestoDAO.getCustomerByEmail(testCustomerEmail, "1234qwer");
		} catch (WrongUserOrPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(customer != null);
		System.out.println("Find customer by email "+testCustomerEmail+" "+ customer);
		
		//check resto prior update
		Resto resto_pre = RestoDAO.getResto(customer.getId().getId(), providerB.getId().getId());
		//resto must exist
		assertTrue(resto_pre.getAmount()==resto_pre_amount);
		assertTrue(resto_pre.getProvider().getId()==providerB.getId());
		assertTrue(resto_pre.getCustomer().getId()==customer.getId());
		
		//update resto
		resto_pre.setAmount(new Double(125));
		RestoDAO.updateResto(resto_pre);
		assertTrue(resto_pre.getAmount().equals(new Double(125)));
		System.out.println("resto id:"+resto_pre.getId()+" provider id:"+resto_pre.getProvider().getId()+" customer id:"+resto_pre.getCustomer().getId()+" updated!"+resto_pre);
		
		
	}
	

}

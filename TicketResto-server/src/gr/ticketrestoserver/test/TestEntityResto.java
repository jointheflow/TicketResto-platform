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
  		idProviderA = RestoDAO.addProvider(testProviderA);
  		System.out.println("providerA "+idProviderA+" set!"+testProviderA);
        
  		Key idProviderB = null;
  		Provider testProviderB = new Provider();
  		testProviderB.setEmail(testProviderEmailB);
  		testProviderB.setPassword("1234qwer");
  		idProviderB = RestoDAO.addProvider(testProviderB);
  		System.out.println("providerB "+idProviderB+" set!"+testProviderB);
  		
  		//set up a customer with a resto of provider B already in place
		Key idCustomer = null;
		
		Customer testCustomer = new Customer();
		testCustomer.setEmail(testCustomerEmail);
		testCustomer.setPassword("1234qwer");
		//set up a resto		
		Resto resto_new = new Resto();
		resto_new.setAmount(resto_pre_amount);
		//set the resto's provider
		resto_new.setProvider(testProviderB);
		
		//add resto to the customer
		testCustomer.updateRestoOfProvider(resto_new);
		idCustomer = RestoDAO.addCustomer(testCustomer);
		System.out.println("customer "+idCustomer+" added!"+testCustomer);
  		
    }  
	  
	@After  
    public void tearDown() {  
        helper.tearDown();  
    }  

	
	
	public void test() {
		/*
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
		
		
		//set up a customer
		Key idCustomer = null;
		
		Customer customer = new Customer();
		customer.setEmail("gr@gmail.com");
		customer.setPassword("1234qwer");
		
		//add resto to customer
		customer.updateRestoOfProvider(resto_new);
		idCustomer = RestoDAO.addCustomer(customer);
		assertTrue(idCustomer != null);
		assertTrue(customer.getResti().size()>0);
		System.out.println("customer "+idCustomer+" added!");
		
		
		
		//retrieve the provider
		*/
	}
	@Test
	public void testNewRestoProviderA() {
		//retrieve the providerA by email
		Provider providerA = RestoDAO.getProviderByEmail(testProviderEmailA);
		assertTrue(providerA!=null);
		System.out.println("Find provider by email "+testProviderEmailA +" "+ providerA);
		
		//retrieve the customer by email
		Customer customer = RestoDAO.getCustomerByEmail(testCustomerEmail);
		assertTrue(customer != null);
		System.out.println("Find customer by email "+testCustomerEmail+" "+ customer);
		
		//add resto to the customer for providerA
		Resto resto = new Resto();
		resto.setAmount(new Double(125));
		resto.setProvider(providerA);
		
		customer.updateRestoOfProvider(resto);
		
		//update customer
		RestoDAO.updateCustomer(customer);
		
		//assert
		assertTrue(customer.getResti().size()>0);
	}
	
	@Test
	public void testUpdateRestoProviderB() {
		//retrieve the providerA by email
		Provider providerB = RestoDAO.getProviderByEmail(testProviderEmailB);
		assertTrue(providerB!=null);
		System.out.println("Find provider by email "+testProviderEmailB +" "+ providerB);
		
		//retrieve the customer by email
		Customer customer = RestoDAO.getCustomerByEmail(testCustomerEmail);
		assertTrue(customer != null);
		System.out.println("Find customer by email "+testCustomerEmail+" "+ customer);
		
		//check resto prior update
		Resto resto_pre = customer.getRestoOfProvider(providerB);
		assertTrue(resto_pre.getAmount()==resto_pre_amount);
		
	}
	

}

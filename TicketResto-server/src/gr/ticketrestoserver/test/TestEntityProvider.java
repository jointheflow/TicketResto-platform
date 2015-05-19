package gr.ticketrestoserver.test;

import static org.junit.Assert.*;
import gr.ticketrestoserver.dao.RestoDAO;




import gr.ticketrestoserver.dao.entity.Provider;
import gr.ticketrestoserver.dao.exception.MandatoryFieldException;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestEntityProvider {
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
	public void test()  {
		
		//add a provider
		Key idProvider = null;
		
		Provider provider = new Provider();
		provider.setEmail("pr@gmail.com");
		provider.setPassword("1234qwer");
		provider.setAddress("Via Sacco e Vanzetti 85");
		provider.setCap("00172");
		provider.setName("La fabbrica del Caffè snc");
		
		try {
			idProvider = RestoDAO.addProvider(provider);
			
		} catch (MandatoryFieldException | UniqueConstraintViolationExcpetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(idProvider != null);
		System.out.println("provider "+idProvider+" added!");
				
		
		//add provider with same email
		Key idProvider_2 = null;
		
		Provider provider_2 = new Provider();
		provider_2.setEmail("pr@gmail.com");
		provider_2.setPassword("1234qwer");
		provider_2.setAddress("Via Sacco e Vanzetti 85");
		provider_2.setCap("00172");
		provider_2.setName("La fabbrica del Caffè snc");
		
		try {
			idProvider_2 = RestoDAO.addProvider(provider_2);
		} catch (MandatoryFieldException | UniqueConstraintViolationExcpetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(idProvider_2 == null);
		System.out.println("provider "+idProvider+" not added!");
		
	}

}

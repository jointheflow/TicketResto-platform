package gr.ticketrestoserver.test;

import static org.junit.Assert.*;
import gr.ticketrestoserver.dao.RestoDAO;




import gr.ticketrestoserver.entity.Provider;

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
	public void test() {
		
		
		Key idProvider = null;
		
		Provider provider = new Provider();
		provider.setEmail("gr@gmail.com");
		provider.setPassword("1234qwer");
		provider.setAddress("Via Sacco e Vanzetti 85");
		provider.setCap("00172");
		provider.setName("La fabbrica del Caffè snc");
		
		idProvider = RestoDAO.addProvider(provider);
		assertTrue(idProvider != null);
		System.out.println("provider "+idProvider+" added!");
				
		
	}

}

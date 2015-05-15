package gr.ticketrestoserver.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.dao.entity.AuthToken;
import gr.ticketrestoserver.dao.exception.InvalidTokenException;
import gr.ticketrestoserver.dao.exception.InvalidTokenForUserException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestEntityAuthToken {

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
	public void test() throws ParseException {
		//add tokenA for userA@gmail.com with infinite expiration
		AuthToken tokenA = new AuthToken();
		tokenA.setUserEmail("userA@gmail.com");
		String target = "24/04/2030";
	    DateFormat df = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
	    Date infiniteExpiration =  df.parse(target); 
		tokenA.setExpiration(infiniteExpiration);
		
		RestoDAO.addAuthToken(tokenA);
		assertTrue(tokenA.getExpiration()!= null);
		
		//CHECK an INVALID TOKEN
		
		try {
			//Key fakeToken = Key.
			Key fakeKey = KeyFactory.createKey("AuthToken", 6666);
			RestoDAO.checkAuthToken(fakeKey, "userB@Gmail.com");
		
		}catch(InvalidTokenException e) {
			System.out.println("CHECK an INVALID TOKEN");
			assertTrue(e!=null);
			
		} catch (InvalidTokenForUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//CHECK a VALID TOKEN with an INAVLID USER
		try {
			RestoDAO.checkAuthToken(tokenA.getTokenId(), "userB@Gmail.com");
		
		}catch(InvalidTokenForUserException e) {
			System.out.println("CHECK a VALID TOKEN with an INAVLID USER");
			assertTrue(e!=null);
		} catch (InvalidTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//add tokenB for userB@gmail.com with expirated date
		AuthToken tokenB = new AuthToken();
		tokenB.setUserEmail("userB@gmail.com");
		String expiredDate = "24/04/1974";
	    DateFormat df_e = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
	    Date expired =  df_e.parse(expiredDate); 
		tokenB.setExpiration(expired);
		
		RestoDAO.addAuthToken(tokenB);
		assertTrue(tokenB.getExpiration()!= null);
		
		
		//CHECK Valid Token with EXPIRATIN DATE
		try {
			RestoDAO.checkAuthToken(tokenB.getTokenId(), "userB@gmail.com");
		
		}catch(InvalidTokenForUserException e) {
			e.printStackTrace();
		} catch (InvalidTokenException e) {
			// TODO Auto-generated catch block
			System.out.println("CHECK Valid Token with EXPIRATIN DATE");
			assertTrue(e!=null);
		}
		//fail("Not yet implemented");
	}

}

package gr.ticketrestoserver.servlet;


import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.entity.Provider;
import gr.ticketrestoserver.dao.entity.Resto;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class TicketRestoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	/*	
		String testProviderEmailA="testProviderA@gmail.com";
		String testProviderEmailB="testProviderB@gmail.com";
				
		String testCustomerEmail="testCustomer@gmail.com";
		
		Double resto_pre_amount= new Double(222);
		
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
			//Key idCustomer = null;
			
			Customer testCustomer = new Customer();
			testCustomer.setEmail(testCustomerEmail);
			testCustomer.setPassword("1234qwer");
			//set up a resto		
			Resto resto_new = new Resto();
			resto_new.setAmount(resto_pre_amount);
			//set the resto's provider
			resto_new.setProvider(testProviderB);
			
			
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		*/
	}
}

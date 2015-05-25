package gr.ticketrestoserver.test.rest;

import static org.junit.Assert.*;
import gr.ticketrestoserver.dao.RestoDAO;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

public class TestRestoUpdateUseCase {
	//set test constants
	private Long customerId;
	private String customerEmail="customer1@usecase.org";
	private String customerPassword = "customer1pwd";
	private String providerEmail="provider1@usecase.org";
	private String providerPassword = "provider1pwd";
	private String providerName = "PROVIDER1 NAME";
	
	private String customerSignupRequest ="http://localhost:8888/app/customer/signup";//?email="+customerEmail+"&password="+customerPassword"
	private String customerLoginRequest="http://localhost:8888/app/customer/login?email="+customerEmail+"&password="+customerPassword;
	private String providerSignupRequest="http://localhost:8888/app/provider/signup";//+?email="+providerEmail+"&password="+providerPassword;
	private String providerLoginRequest="http://localhost:8888/app/provider/login?email="+providerEmail+"&password="+providerPassword;
	
	@Before
	public void setUp() throws Exception {
		//create new customer and provider
		Client customerClient = new Client(Protocol.HTTP);
		Request customerRequest = new Request(Method.POST, customerSignupRequest);
		//create a post entity for Representation
		Form fParam = new Form();
		fParam.add("email", customerEmail);
		fParam.add("password", customerPassword);
		customerRequest.setEntity(fParam.getWebRepresentation());
		
		Response customerResponse = customerClient.handle(customerRequest);
		
		JSONObject jsonobj = new JsonRepresentation(customerResponse.getEntityAsText()).getJsonObject();
		System.out.println(jsonobj.toString());
		//gett the customerID
		customerId = (Long)jsonobj.get("id");
		System.out.println(customerId);
	}

	@After
	public void tearDown() throws Exception {
		//remove new customer and provider
		RestoDAO.deleteCustomerById(customerId);
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		try {
			//get id from a client
			
			Client customerClient = new Client(Protocol.HTTP);
			Request customerRequest = new Request(Method.GET, customerLoginRequest);
			Response customerResponse = customerClient.handle(customerRequest);
			JSONObject jsonobj = new JsonRepresentation(customerResponse.getEntityAsText()).getJsonObject();
			System.out.println(jsonobj.toString());
			
			//get clientID
			
			
			//get info from provider
			
			//get providerID, email, authtoken
			
			
			//set new resto
			
			
			
			//update resto
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(new JsonRepresentation(customerResponse.getEntityAsText()) );
	}

}

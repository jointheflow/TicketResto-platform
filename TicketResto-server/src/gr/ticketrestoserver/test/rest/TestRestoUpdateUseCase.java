package gr.ticketrestoserver.test.rest;

import static org.junit.Assert.*;


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

public class TestRestoUpdateUseCase {
	//set test constants
	private Long customerId;
	private Long customerToken;
	private String customerEmail="customer1@usecase.org";
	private String customerPassword = "customer1pwd";
	
	private Long providerId;
	private Long providerToken;
	private String providerEmail="provider1@usecase.org";
	private String providerPassword = "provider1pwd";
	private String providerName = "PROVIDER1 NAME";
	
	private String customerSignupRequest ="http://localhost:8888/app/customer/signup";//?email="+customerEmail+"&password="+customerPassword"
	private String customerLoginRequest="http://localhost:8888/app/customer/login?email="+customerEmail+"&password="+customerPassword;
	private String customerDeleteRequest="http://localhost:8888/app/customer/delete";//?email="+customerEmail+"&token="+customerPassword;
	private String providerSignupRequest="http://localhost:8888/app/provider/signup";//+?email="+providerEmail+"&password="+providerPassword;
	private String providerLoginRequest="http://localhost:8888/app/provider/login?email="+providerEmail+"&password="+providerPassword;
	private String providerDeleteRequest="http://localhost:8888/app/provider/delete";//?email="+customerEmail+"&token="+customerPassword;
	
	private String restoUpdateRequest="http://localhost:8888/app/provider/resto/update";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("-->Start setUP");
		
		/*----START Creation of a  new customer-----*/ 
		System.out.println("Creating a new customer");
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
		//get the customerID
		customerId = (Long)jsonobj.get("id");
		customerToken = jsonobj.getJSONObject("token").getLong("id");
		System.out.println("Customer created "+ customerId);
		/*----END Creation of a new customer----*/
		
		/*----START creation  of new provider ----*/
		System.out.println("Creating a new provider");
		Client providerClient = new Client(Protocol.HTTP);
		Request providerRequest = new Request(Method.POST, providerSignupRequest);
		//create a post entity for Representation
		Form fParam_prov = new Form();
		fParam_prov.add("email", providerEmail);
		fParam_prov.add("password", providerPassword);
		fParam_prov.add("name", providerName);	
		providerRequest.setEntity(fParam_prov.getWebRepresentation());
		Response providerResponse = providerClient.handle(providerRequest);
		JSONObject jsonobj_prov = new JsonRepresentation(providerResponse.getEntityAsText()).getJsonObject();
		System.out.println(jsonobj_prov.toString());
		//gett the customerID
		providerId = (Long)jsonobj_prov.get("id");
		providerToken = jsonobj_prov.getJSONObject("token").getLong("id");
		System.out.println("Provider created "+providerId);
		/*----END creation of a new provider-----*/
		
		System.out.println("-->End Setup");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("-->Start tearDown");
		
		/*-----START removing the created customer-----*/
		System.out.println("Removing customer "+customerId);
		Client customerClient = new Client(Protocol.HTTP);
		customerDeleteRequest = customerDeleteRequest+"?email="+customerEmail+"&id="+customerId.toString()+"&token="+customerToken.toString();
		Request customerRequest = new Request(Method.DELETE, customerDeleteRequest);
		Response customerResponse = customerClient.handle(customerRequest);
		JSONObject jsonobj = new JsonRepresentation(customerResponse.getEntityAsText()).getJsonObject();
		System.out.println(jsonobj.toString());
		System.out.println("Customer removed");
		/*----END removing the created customer ------*/
		
		/*----START removing the  created  provider -----*/
		System.out.println("Removing provider "+providerId);
		Client providerClient = new Client(Protocol.HTTP);
		providerDeleteRequest = providerDeleteRequest+"?email="+providerEmail+"&id="+providerId.toString()+"&token="+providerToken.toString();
		Request providerRequest = new Request(Method.DELETE, providerDeleteRequest);
		Response providerResponse = providerClient.handle(providerRequest);
		JSONObject jsonobj_prov = new JsonRepresentation(providerResponse.getEntityAsText()).getJsonObject();
		System.out.println(jsonobj_prov.toString());
		System.out.println("Provider removed");
		/*----END removing the created provider ------ */
		
		
		
		//remove resto
		
		//remove all token of provider and customer
		
		System.out.println("-->End tearDown");
	}

	@Test
	public void test() {
		System.out.println("-->Start test");
		//fail("Not yet implemented");
		try {
			//get id from a client
			System.out.println("Doing customer login "+customerLoginRequest);
			Client customerClient = new Client(Protocol.HTTP);
			Request customerRequest = new Request(Method.GET, customerLoginRequest);
			Response customerResponse = customerClient.handle(customerRequest);
			JSONObject jsonobj_cust = new JsonRepresentation(customerResponse.getEntityAsText()).getJsonObject();
			System.out.println(jsonobj_cust.toString());
			System.out.println("Customer login executed");
			
			//get clientID
			
			
			//get info from provider
			
			//get providerID, email, authtoken
			System.out.println("Doing provider login "+providerLoginRequest);
			Client providerClient = new Client(Protocol.HTTP);
			Request providerRequest = new Request(Method.GET, providerLoginRequest);
			Response providerResponse = providerClient.handle(providerRequest);
			JSONObject jsonobj_prov = new JsonRepresentation(providerResponse.getEntityAsText()).getJsonObject();
			System.out.println(jsonobj_prov.toString());
			System.out.println("Provider login executed");
			
			
			//set new resto of 100,00
			//customerId, providerId, providerToken, providerEmail, restoValue
			System.out.println("Doing new resto");
			Client restoClient = new Client(Protocol.HTTP);
			Request restoRequest = new Request(Method.POST, restoUpdateRequest);
			
			Form fParam_prov = new Form();
			fParam_prov.add("customerId", new Long(jsonobj_cust.getLong("id")).toString());
			fParam_prov.add("providerId", new Long(jsonobj_prov.getLong("id")).toString());
			fParam_prov.add("providerToken", new Long(jsonobj_prov.getJSONObject("token").getLong("id")).toString());	
			fParam_prov.add("providerEmail", jsonobj_prov.getString("email"));
			fParam_prov.add("restoValue", "100.00");
			restoRequest.setEntity(fParam_prov.getWebRepresentation());
			Response restoResponse = restoClient.handle(restoRequest);
			JSONObject jsonobj_resto = new JsonRepresentation(restoResponse.getEntityAsText()).getJsonObject();
			System.out.println(jsonobj_resto.toString());
			System.out.println("New resto done");
								
			//update resto with 150,00
			System.out.println("Updating resto");
			fParam_prov = new Form();
			fParam_prov.add("customerId", new Long(jsonobj_cust.getLong("id")).toString());
			fParam_prov.add("providerId", new Long(jsonobj_prov.getLong("id")).toString());
			fParam_prov.add("providerToken", new Long(jsonobj_prov.getJSONObject("token").getLong("id")).toString());	
			fParam_prov.add("providerEmail", jsonobj_prov.getString("email"));
			fParam_prov.add("restoValue", "150.00");
			restoRequest.setEntity(fParam_prov.getWebRepresentation());
			restoResponse = restoClient.handle(restoRequest);
			JSONObject jsonobj_resto_upd = new JsonRepresentation(restoResponse.getEntityAsText()).getJsonObject();
			System.out.println(jsonobj_resto_upd.toString());
			System.out.println("Update resto done");
				
			
			System.out.println("-->End test");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(new JsonRepresentation(customerResponse.getEntityAsText()) );
	}

}

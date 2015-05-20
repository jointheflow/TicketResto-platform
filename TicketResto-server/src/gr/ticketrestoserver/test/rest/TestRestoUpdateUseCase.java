package gr.ticketrestoserver.test.rest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

public class TestRestoUpdateUseCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		String customerLoginRequest="http://localhost:8888/app/customer/login?email=test1&password=test1";
		Client customerClient = new Client(Protocol.HTTP);
		Request customerRequest = new Request(Method.GET, customerLoginRequest);
		Response customerResponse = customerClient.handle(customerRequest);
		
		System.out.println(customerResponse.getEntityAsText());
	}

}

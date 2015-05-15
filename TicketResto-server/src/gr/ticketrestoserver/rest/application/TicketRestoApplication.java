package gr.ticketrestoserver.rest.application;



import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class TicketRestoApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
      
    	// Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());
        
        // Defines a route for the resource Customer  
         
        router.attach("/customer/login", gr.ticketrestoserver.rest.resource.CustomerLoginResource.class);
        router.attach("/customer/signup", gr.ticketrestoserver.rest.resource.CustomerSignupResource.class);
        
        //router.attach("/media", MediaResource.class);
        // Defines a route for the resource "item"  
        //router.attach("/items/{itemName}", ItemResource.class);  
  
        return router;

       
    }
}

package gr.ticketrestoserver.rest.resource;



import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.dao.exception.WrongUserOrPasswordException;
import gr.ticketrestoserver.entity.Customer;




import java.util.logging.Logger;



import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.json.*;




public class CustomerResource<K> extends ServerResource{
	private static final Logger log = Logger.getLogger(CustomerResource.class.getName());
	
	/*Create a new Customer*/
	@Post
	public Representation POST(Representation entity)
	        throws ResourceException {
	    
        // retrieves customer parameters  
        // "name=value"  
		Form form = new Form(entity); 
		try {
			 
	        for (Parameter parameter : form) {
	        	log.info("parameter " + parameter.getName());
	   		  	log.info("/" + parameter.getValue());
	        	
	        } 
	        
	        //get parameters
	        String customerEmail = form.getFirstValue("email");
	        //TODO md5 conversion
	        String customerPwd = form.getFirstValue("password");
	        
	        //create customer entity
	        Customer customer = new Customer();
	        customer.setEmail(customerEmail);
	        customer.setPassword(customerPwd);
	        
	        RestoDAO.addCustomer(customer);
	        	        	        
			JsonRepresentation representation= new JsonRepresentation(customer);
			setStatus(Status.SUCCESS_CREATED);
			representation.setIndenting(true);
			return representation;
			
				
		}catch (RuntimeException r){
			setStatus(Status.SERVER_ERROR_INTERNAL);
			ErrorResource error = new ErrorResource();
			error.setErrorCode(ErrorResource.IO_ERROR);
			error.setErrorMessage(r.getMessage());
			JsonRepresentation errorRepresentation = new JsonRepresentation(error);
			return errorRepresentation;
			
		} catch (UniqueConstraintViolationExcpetion e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ErrorResource error = new ErrorResource();
			error.setErrorCode(ErrorResource.UNIQUE_CONSTRAINT_VIOLATION);
			error.setErrorMessage(e.getMessage());
			JsonRepresentation errorRepresentation = new JsonRepresentation(error);
			return errorRepresentation;
			
		}  
   }
	
		/*Return json rapresentation of the customer resource*/
		/*private JSONObject toJSON(Customer customer) throws JSONException {
		
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("email", customer.getEmail());
			jsonobj.put("password", customer.getPassword());
			jsonobj.put("id", customer.getId().toString());
			
		return jsonobj;
	}*/
		/*Get customer basing on email and password parameters, also return all Resto associated */
		@Get("json")
		public Representation GET(){
			//create json response
			JsonRepresentation representation = null;
			try {
				
				String p_email=this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("email");
				String p_password= this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("password");
				
				log.info("start GET for Customer");
			
				//get customer
				Customer customer = RestoDAO.getCustomerByEmail(p_email, p_password);
				
				
				//get all Resto of customer
				RestoDAO.
				
				if (customer !=null)	{
					representation= new JsonRepresentation(customer);
					representation.setIndenting(true);
				}
				return representation;
				
			
			}catch (WrongUserOrPasswordException e) {
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.WRONG_USER_OR_PASSWORD);
				error.setErrorMessage(e.getMessage());
				JsonRepresentation errorRepresentation = new JsonRepresentation(error);
				return errorRepresentation;
				
			}	finally {
				log.info("end  GET for Customer");
				
			}
			
			
		}

	
}

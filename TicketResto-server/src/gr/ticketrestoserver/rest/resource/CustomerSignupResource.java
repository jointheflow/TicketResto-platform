package gr.ticketrestoserver.rest.resource;

import gr.ticketrestoserver.dao.entity.AuthToken;
import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.exception.MandatoryFieldException;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.dto.CustomerDTO;
import gr.ticketrestoserver.dto.TokenDTO;
import gr.ticketrestoserver.helper.UtilHelper;

import java.util.logging.Logger;

import org.gianluca.logbook.dao.googledatastore.entity.RestoDAO;
import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.json.*;




public class CustomerSignupResource<K> extends ServerResource{
	private static final Logger log = Logger.getLogger(CustomerSignupResource.class.getName());
	
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
	        
	        
	        //create a token for next operation
			AuthToken token = new AuthToken();
			//set an infinite expiration 
			token.setExpiration(UtilHelper.getExpiration30Minute());
			token.setUserEmail(customer.getEmail());
			//persist token 
			RestoDAO.addAuthToken(token);
			
			
			//create dto token object
			TokenDTO tokenDto= new TokenDTO();
			tokenDto.expiration = token.getExpiration();
			tokenDto.id = token.getTokenId().getId();
			tokenDto.email = token.getUserEmail();
			
	        
	        
	        //convert in a dto object
			CustomerDTO customerDto = new CustomerDTO();
			customerDto.email= customer.getEmail();
			customerDto.id = customer.getId().getId();
			customerDto.token = tokenDto;
	        
	        
	        	        	        
			JsonRepresentation representation= new JsonRepresentation(customerDto);
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
			
		} catch (MandatoryFieldException e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ErrorResource error = new ErrorResource();
			error.setErrorCode(ErrorResource.MANDATORY_FIELD_MISSING);
			error.setErrorMessage(e.getMessage());
			JsonRepresentation errorRepresentation = new JsonRepresentation(error);
			return errorRepresentation;
		}  
   }
	
		
	
}

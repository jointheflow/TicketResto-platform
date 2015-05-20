package gr.ticketrestoserver.rest.resource;

import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.dao.entity.AuthToken;
import gr.ticketrestoserver.dao.entity.Provider;
import gr.ticketrestoserver.dao.exception.MandatoryFieldException;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.dto.ProviderDTO;
import gr.ticketrestoserver.dto.TokenDTO;
import gr.ticketrestoserver.helper.UtilHelper;

import java.util.logging.Logger;

import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.json.*;




public class ProviderSignupResource<K> extends ServerResource{
	private static final Logger log = Logger.getLogger(ProviderSignupResource.class.getName());
	
	/*Create a new Provider*/
	@Post
	public Representation POST(Representation entity)
	        throws ResourceException {
	    
        // retrieves provider parameters  
        // "name=value"  
		Form form = new Form(entity); 
		try {
			 
	        for (Parameter parameter : form) {
	        	log.info("parameter " + parameter.getName());
	   		  	log.info("/" + parameter.getValue());
	        	
	        } 
	        
	        //get parameters
	        String providerEmail = form.getFirstValue("email");
	        //TODO md5 conversion
	        String providerPwd = form.getFirstValue("password");
	        String providerName = form.getFirstValue("name");
	        
	        //TODO complete with all mandatory field
	        /*String providerAddress = form.getFirstValue("address");
	        String providerCap = form.getFirstValue("cap");
	        */
	       
	        //create provider entity
	        Provider provider = new Provider();
	        provider.setEmail(providerEmail);
	        provider.setPassword(providerPwd);
	        provider.setName(providerName);
	        //add providrer
	        RestoDAO.addProvider(provider);
	        
	        
	        //create a token for next operation
			AuthToken token = new AuthToken();
			//set a 30minute expiration 
			token.setExpiration(UtilHelper.getExpiration30Minute());
			token.setUserEmail(provider.getEmail());
			//persist token 
			RestoDAO.addAuthToken(token);
			
			
			//create dto token object
			TokenDTO tokenDto= new TokenDTO();
			tokenDto.expiration = token.getExpiration();
			tokenDto.id = token.getTokenId().getId();
			tokenDto.email = token.getUserEmail();
			
	        
	        
	        //convert in a dto object
			ProviderDTO providerDto = new ProviderDTO();
			providerDto.email= provider.getEmail();
			providerDto.id = provider.getId().getId();
			providerDto.description= provider.getName();
			providerDto.token = tokenDto;
	        
	        
	        	        	        
			JsonRepresentation representation= new JsonRepresentation(providerDto);
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

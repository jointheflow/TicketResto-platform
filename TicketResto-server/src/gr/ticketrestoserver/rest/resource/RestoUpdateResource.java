package gr.ticketrestoserver.rest.resource;

import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.entity.Provider;
import gr.ticketrestoserver.dao.entity.Resto;
import gr.ticketrestoserver.dao.exception.InvalidTokenForUserException;
import gr.ticketrestoserver.dto.ProviderDTO;
import gr.ticketrestoserver.dto.RestoDTO;

import java.util.logging.Logger;

import org.gianluca.logbook.dao.exception.InvalidTokenException;
import org.gianluca.logbook.dao.googledatastore.entity.RestoDAO;
import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.json.*;




public class RestoUpdateResource<K> extends ServerResource{
	private static final Logger log = Logger.getLogger(RestoUpdateResource.class.getName());
	
	/*Update a Resto*/
	@Post
	public Representation POST(Representation entity)
	        throws ResourceException {
	    
        // retrieves resto parameters  
        // "name=value"  
		Form form = new Form(entity); 
		try {
			 
	        for (Parameter parameter : form) {
	        	log.info("parameter " + parameter.getName());
	   		  	log.info("/" + parameter.getValue());
	        	
	        } 
	        
	        //get parameters
	        String p_customerId = form.getFirstValue("customerId");
	        String p_providerId = form.getFirstValue("providerId");
	        String p_providerEmail = form.getFirstValue("providerEmail");
	        String p_token= form.getFirstValue("providerToken");
	        String p_value = form.getFirstValue("restoValue");
	        
	        //check if token exists  and is valid
	        RestoDAO.checkAuthToken(new Long(p_token), p_providerEmail);
	            
	        
	        Resto resto = RestoDAO.getResto(new Long(p_customerId), new Long(p_providerId));
	        //check if resto exists. If not, create!
	        if (resto == null) {
	        	Customer customer = RestoDAO.getCustomerById(new Long(p_customerId));
		        Provider provider = RestoDAO.getProviderById(new Long(p_providerId));
	        	resto = new Resto();
	        	resto.setCustomer(customer);
	        	resto.setProvider(provider);
	        }
	        
	        //update resto with new value
	        resto.setAmount(new Double(p_value));
	        RestoDAO.updateResto(resto);
	        
	        //provider DTO
	        ProviderDTO providerDto = new ProviderDTO();
	        providerDto.id = new Long(p_providerId);
	        providerDto.description = p_providerEmail;
	       // providerDto.email = p_providerEmail;
	        
	        //return DTO
	        RestoDTO restoDto = new RestoDTO();
	        restoDto.setAmount(resto.getAmount());
	        restoDto.setId(resto.getId().getId());
	        restoDto.setProvider(providerDto);
	        
				        	        
			JsonRepresentation representation= new JsonRepresentation(restoDto);
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
					
		
		} catch (InvalidTokenForUserException e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ErrorResource error = new ErrorResource();
			error.setErrorCode(ErrorResource.TOKEN_ERROR);
			error.setErrorMessage(e.getMessage());
			JsonRepresentation errorRepresentation = new JsonRepresentation(error);
			return errorRepresentation;
			
		} catch (InvalidTokenException e) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			ErrorResource error = new ErrorResource();
			error.setErrorCode(ErrorResource.TOKEN_ERROR);
			error.setErrorMessage(e.getMessage());
			JsonRepresentation errorRepresentation = new JsonRepresentation(error);
			return errorRepresentation;
		}  
   }
	
		
	
}

package gr.ticketrestoserver.rest.resource;

import gr.ticketrestoserver.dao.RestoDAO;
import gr.ticketrestoserver.dao.exception.InvalidTokenException;
import gr.ticketrestoserver.dao.exception.InvalidTokenForUserException;
import gr.ticketrestoserver.dto.ProviderDTO;

import java.util.logging.Logger;

import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Status;
import org.restlet.ext.json.*;




public class ProviderDeleteResource<K> extends ServerResource{
	private static final Logger log = Logger.getLogger(ProviderDeleteResource.class.getName());
	
	
		/*Delete provider basing on id*/
		@Delete("json")
		public Representation delete(){
			//create json response
			JsonRepresentation representation = null;
			try {
				//get parameter
				String p_id=this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("id");				
				String p_email= this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("email");
				String p_token = this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("token");
				
				log.info("start DELETE for Provider");
				log.info("p_email:"+p_email);
				log.info("p_password:"+p_token);
				log.info("p_token:"+p_token);
				
				//check if token exists
		        RestoDAO.checkAuthToken(new Long(p_token), p_email);
		        
				//delete provider
				RestoDAO.deleteProviderById(new Long(p_id));
				
				
				//create provider DTO
				ProviderDTO providerDto = new ProviderDTO();
				
				providerDto.email= "DELETED";
				providerDto.id = -1;
					
				
				representation= new JsonRepresentation(providerDto);
				representation.setIndenting(true);
			
				return representation;
				
			
			} catch (NumberFormatException e) {
				setStatus(Status.SERVER_ERROR_INTERNAL);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.IO_ERROR);
				error.setErrorMessage(e.getMessage());
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

			}finally {
				log.info("end  DELETE for Provider");
				
			}
			
			
		}

	
}

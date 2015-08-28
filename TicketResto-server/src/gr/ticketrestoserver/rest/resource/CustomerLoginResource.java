package gr.ticketrestoserver.rest.resource;

import gr.ticketrestoserver.dao.entity.AuthToken;
import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.entity.Provider;
import gr.ticketrestoserver.dao.entity.Resto;
import gr.ticketrestoserver.dao.exception.WrongUserOrPasswordException;
import gr.ticketrestoserver.dto.CustomerDTO;
import gr.ticketrestoserver.dto.ProviderDTO;
import gr.ticketrestoserver.dto.RestoDTO;
import gr.ticketrestoserver.dto.TokenDTO;
import gr.ticketrestoserver.helper.UtilHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.gianluca.logbook.dao.googledatastore.entity.RestoDAO;
import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Status;
import org.restlet.ext.json.*;




public class CustomerLoginResource<K> extends ServerResource{
	private static final Logger log = Logger.getLogger(CustomerLoginResource.class.getName());
	
	
		/*Get customer basing on email and password parameters, also return all Resto associated */
		@Get("json")
		public Representation login(){
			//create json response
			JsonRepresentation representation = null;
			try {
				//get parameter
				String p_email=this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("email");
				String p_password= this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("password");
				
				log.info("start GET for Customer");
				log.info("p_email:"+p_email);
				log.info("p_password:"+p_password);
				//get customer
				Customer customer = RestoDAO.getCustomerByEmail(p_email, p_password);
				
				
				//create an auth Token
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
				
				//get all resti of customer and put in the DTO List
				List<Resto> resti = RestoDAO.getResto(customer.getId().getId());
				List<RestoDTO> restiDto = new ArrayList<RestoDTO>();
				for (int i=0; i<resti.size(); i++) {
					RestoDTO restoDto = new RestoDTO();
					restoDto.amount = resti.get(i).getAmount();
					restoDto.expirationDate = resti.get(i).getExpirationDate();
					restoDto.id = resti.get(i).getId().getId();
					Provider provider = resti.get(i).getProvider();
					ProviderDTO providerDto = new ProviderDTO();
					providerDto.email = provider.getEmail();
					providerDto.description = provider.getName();
					providerDto.address = provider.getAddress();
					providerDto.id = provider.getId().getId();
					restoDto.provider = providerDto;
					//add resto
					restiDto.add(restoDto);					
					
				}
				
				//create customer DTO
				CustomerDTO customerDto = new CustomerDTO();
				
				customerDto.email= customer.getEmail();
				customerDto.id = customer.getId().getId();
				customerDto.resti = restiDto;
				customerDto.token = tokenDto;
					
				if (customer !=null)	{
					representation= new JsonRepresentation(customerDto);
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

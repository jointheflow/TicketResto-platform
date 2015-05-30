package gr.ticketrestoserver.dao;




import java.util.Date;
import java.util.List;


import gr.ticketrestoserver.dao.entity.AuthToken;
import gr.ticketrestoserver.dao.entity.Customer;
import gr.ticketrestoserver.dao.entity.Provider;
import gr.ticketrestoserver.dao.entity.Resto;
import gr.ticketrestoserver.dao.exception.InvalidTokenException;
import gr.ticketrestoserver.dao.exception.InvalidTokenForUserException;
import gr.ticketrestoserver.dao.exception.MandatoryFieldException;
import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.dao.exception.WrongUserOrPasswordException;
import gr.ticketrestoserver.helper.DAOHelper;

import javax.jdo.FetchGroup;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;



public class RestoDAO {
	
	//TODO add check on mandatory field (ex. email, pwd) and manage exceptions
	public static Key addCustomer(Customer customer) throws UniqueConstraintViolationExcpetion, MandatoryFieldException {
		Key customerId=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
            checkMandatoryConstraintCustomer(customer);
        	checkUniqueConstraintCustomer(pm, customer, null);
        	pm.makePersistent(customer);
        	tx.commit();
        	
            customerId= customer.getId();
        
		} finally {
            if (tx.isActive()) tx.rollback();
			pm.close();
        }
        return customerId;
	}
	
	//Update a customer basing on key id
	public static void updateCustomer(Long id, Customer new_customer) throws UniqueConstraintViolationExcpetion, MandatoryFieldException {
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
        	checkMandatoryConstraintCustomer(new_customer);
        	checkUniqueConstraintCustomer(pm, new_customer, id);
        	//find the customer object from datastore by id
        	Customer customer = pm.getObjectById(Customer.class, id);
        	if (customer !=null){
        		customer.setEmail(new_customer.getEmail());
        		customer.setPassword(new_customer.getPassword());
        		
        	}
        	
            tx.commit();
            
       	} finally {
       		if (tx.isActive()) tx.rollback();
            pm.close();
        }
        
	}
	
	//check unique constraint (email on Customer entity). If there is one occurrence, Id parameter is check against
	//id of first occurrence. If they are equals the check is removed because the email is referencing
	//the same entity (may be an update)
	private static void checkUniqueConstraintCustomer(PersistenceManager pm, Customer customer, Long id) throws UniqueConstraintViolationExcpetion{
		Query query = pm.newQuery(Customer.class);
		query.setFilter("email == email_p");
		query.declareParameters("String email_p");
		@SuppressWarnings("unchecked")
		List<Customer> result = (List<Customer>)query.execute(customer.getEmail());
		if (!result.isEmpty() && 
				id != null &&
					((Customer) result.get(0)).getId().getId() != id.longValue()) 
				throw new UniqueConstraintViolationExcpetion("Customer with email "+customer.getEmail()+ " already exists!");
		
	}
	
	//check mandatory field constraint (email and password  on Customer entity)
	private static void checkMandatoryConstraintCustomer(Customer customer) throws MandatoryFieldException {
		if (customer.getEmail() == null || customer.getPassword()==null)
				throw new MandatoryFieldException("email and password are mandatory!");
		
	}
	
	//check mandatory field constraint (email and password  on Customer entity)
	private static void checkMandatoryConstraintProvider(Provider provider) throws MandatoryFieldException {
		if (provider.getEmail() == null || provider.getPassword()==null || provider.getName()==null)
				throw new MandatoryFieldException("email, password and name are mandatory!");
		
	}
	
	//TODO add check on mandatory field (ex. email, pwd) and manage exceptions
	public static Key addProvider(Provider provider) throws MandatoryFieldException, UniqueConstraintViolationExcpetion {
		Key providerId=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
        	checkMandatoryConstraintProvider(provider);
        	checkUniqueConstraintProvider(pm, provider);
        	pm.makePersistent(provider);
            providerId= provider.getId();
            tx.commit();
		} finally {
			if (tx.isActive()) tx.rollback();
            pm.close();
        }
        return providerId;
	}

	//check unique constraint (email on Provider entity)
		private static void checkUniqueConstraintProvider(PersistenceManager pm, Provider provider) throws UniqueConstraintViolationExcpetion{
			Query query = pm.newQuery(Provider.class);
			query.setFilter("email == email_p");
			query.declareParameters("String email_p");
			@SuppressWarnings("unchecked")
			List<Provider> result = (List<Provider>)query.execute(provider.getEmail());
			if (!result.isEmpty())
				throw new UniqueConstraintViolationExcpetion("Provider with email "+provider.getEmail()+ " already exists!");
		
		}
	
		
	public static Key updateResto(Resto resto) {
		Key restoId=null;
		Resto restoObj = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        
		try {
			tx.begin();
        	//if resto has not an id, make it persistent because is a new resto
        	if (resto.getId() ==null) {
        		pm.makePersistent(resto);
        		restoId=resto.getId();
        	}
        	else {
	        	//retrieve resto instance by using the provider-customer key
	        	restoObj = pm.getObjectById(Resto.class, resto.getId());
	        	//if not exists make persistence the new instance
	        	//if (restoObj == null)       	    			
	            	//pm.makePersistent(resto);
	        	//set new value
	        	restoObj.setAmount(resto.getAmount());
	        	
        	}
        	tx.commit();
    			
        } finally {
        	if (tx.isActive()) tx.rollback();
            pm.close();
        }
        return restoId;
	}
	
	
	public static Customer getCustomerByEmail(String email, String password) throws WrongUserOrPasswordException {
		Customer customer = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(Customer.class);
			query.setFilter("email == email_p");
			query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<Customer> result = (List<Customer>)query.execute(email);
			
			if (!result.isEmpty()) {
				customer = result.get(0);
				if (!(customer.getPassword().equals(password)))  throw new WrongUserOrPasswordException("Wrong email user or password");
			}else {
				throw new WrongUserOrPasswordException("Wrong email user or password");
				
			}
			
			tx.commit();
		 } finally {
			 if (tx.isActive()) tx.rollback();
	         pm.close();
	        }
	     return customer;
		
	}
	
	public static Provider getProviderByEmail(String email, String password) throws WrongUserOrPasswordException {
		Provider provider = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(Provider.class);
			query.setFilter("email == email_p");
			query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<Provider> result = (List<Provider>)query.execute(email);
			
			if (!result.isEmpty()) {
				provider = result.get(0);
				if (!(provider.getPassword().equals(password)))  throw new WrongUserOrPasswordException("Wrong email user or password");
			}else {
				throw new WrongUserOrPasswordException("Wrong email user or password");
			}
			tx.commit();
		 } finally {
			 if (tx.isActive()) tx.rollback();
	         pm.close();
	        }
	        return provider;
	}
	
	
	public static Provider getProviderById(Long id) {
		Provider provider = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			provider = pm.getObjectById(Provider.class, id);
			tx.commit();
		 } finally {
			 if (tx.isActive()) tx.rollback();
	            pm.close();
	        }
	        return provider;
	}
	
	public static Customer getCustomerById(Long id) {
		Customer customer = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			customer = pm.getObjectById(Customer.class, id);
			tx.commit();
		 } finally {
			 if (tx.isActive()) tx.rollback();
	         pm.close();
	     }
	     return customer;
	}
	
	/*Delete customer and all resto asssociated with the user*/
	public static Customer deleteCustomerById(Long id) {
		Customer customer = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			//delete customer
			customer = pm.getObjectById(Customer.class, id);
			String email = customer.getEmail();
			pm.deletePersistent(customer);
			
			//delete resti
			Query query = pm.newQuery(Resto.class);
			query.setFilter("customer == customer_p ");
			query.declareParameters(Long.class.getName()+" customer_p");
			//query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<Resto> restiList = (List<Resto>)query.execute(id);
			pm.deletePersistentAll(restiList);
			
			//delete token of the user basing email
			Query query_t = pm.newQuery(AuthToken.class);
			
			query_t.setFilter("userEmail == email_p ");
			query_t.declareParameters("String email_p");
			//query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<AuthToken> tokenList = (List<AuthToken>)query_t.execute(email);
			pm.deletePersistentAll(tokenList);
			
			tx.commit();
		} finally {
			if (tx.isActive()) tx.rollback();
            pm.close();
		}
	     return null;
	}
	
	
	public static Provider deleteProviderById(Long id) {
		Provider provider = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			//delete provider
			provider= pm.getObjectById(Provider.class, id);
			String email = provider.getEmail();
			pm.deletePersistent(provider);
			
			//delete token of the user basing email
			Query query_t = pm.newQuery(AuthToken.class);
			
			query_t.setFilter("userEmail == email_p ");
			query_t.declareParameters("String email_p");
			//query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<AuthToken> tokenList = (List<AuthToken>)query_t.execute(email);
			pm.deletePersistentAll(tokenList);
			tx.commit();
		 } finally {
			 if (tx.isActive()) tx.rollback();
	            pm.close();
	     }
	     return null;
	}
	
	public static Resto getResto(Long customerId, Long providerId) {
		Resto resto = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.getFetchPlan().setGroup(FetchGroup.ALL);
			Query query = pm.newQuery(Resto.class);
			//query.setFilter("customer == customer_p && provider == provider_p");
			query.setFilter("provider == provider_p && customer == customer_p ");
			query.declareParameters(Long.class.getName() + " provider_p, "+Long.class.getName()+" customer_p");
			//query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			//List<Resto> result = (List<Resto>)query.execute(customer, provider);
			List<Resto> result = (List<Resto>)query.execute(providerId, customerId);
			if (!result.isEmpty())
				resto = result.get(0);
			tx.commit();
			
		 } finally {
			 if (tx.isActive()) tx.rollback();
			 pm.close();
	        }
	        return resto;
		
		
	}
	
	public static List<Resto> getResto(Long customerId) {
		List<Resto> result;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.getFetchPlan().setGroup(FetchGroup.ALL);
			Query query = pm.newQuery(Resto.class);
			
			query.setFilter("customer == customer_p ");
			query.declareParameters(Long.class.getName()+" customer_p");
			//query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<Resto> tmpResult = (List<Resto>)query.execute(customerId);
			//restoList = (List<Resto>)query.execute(customer.getId());
			result = tmpResult;
			tx.commit();
		 } finally {
			 if(tx.isActive()) tx.rollback();
	         pm.close();
	         
	     }
		return result;   
			
	}
	public static List<AuthToken> getTokenOfUser(String userEmail) {
		List<AuthToken> result;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.getFetchPlan().setGroup(FetchGroup.ALL);
			Query query_t = pm.newQuery(AuthToken.class);
			
			query_t.setFilter("userEmail == email_p ");
			query_t.declareParameters("String email_p");
			//query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<AuthToken> tokenList = (List<AuthToken>)query_t.execute(userEmail);
			result = tokenList;
			tx.commit();
		 } finally {
			 if (tx.isActive()) tx.rollback();
	         pm.close();
	         
	     }
		return result;   
			
	}
	
	
	public static Key addAuthToken(AuthToken token) {
		Key tokenId = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
        	pm.makePersistent(token);
            tokenId= token.getTokenId();
            tx.commit();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (tx.isActive()) tx.rollback();
            pm.close();
        }
       
        return tokenId;
	}
	
	/*Check if token exists, is not expired and is associated to the userEmail*/
	public static void checkAuthToken(Long tokenId, String userEmail) throws InvalidTokenException, InvalidTokenForUserException {
		AuthToken token=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		Transaction tx= pm.currentTransaction();
		try {
			tx.begin();
			token = pm.getObjectById(AuthToken.class, tokenId);
			
			
			
			if (!token.getUserEmail().equals(userEmail)) throw new InvalidTokenForUserException("Token invalid "+tokenId);
			
			if (new Date().after(token.getExpiration())) throw new InvalidTokenException("Token is expired "+tokenId);
			tx.commit();
		}catch (JDOObjectNotFoundException e)  {
		
			throw new InvalidTokenException("Token invalid "+tokenId);
		
		}finally {
			if (tx.isActive()) tx.rollback();
			pm.close();
			
		}
	}
	
	
}

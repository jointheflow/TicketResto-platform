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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

import com.google.appengine.api.datastore.Transaction;

import static com.google.appengine.api.search.Query.Builder;




public class RestoDAO {
	
	/*Customer is saved in the datastore as the following:
	 * Entity --> Customer
	 * 				key: "customerRootKey"/customerKey
	 * 				email: <email>
	 * 				password: <password>
	 * */
	//TODO add check on mandatory field (ex. email, pwd) and manage exceptions
	public static Key addCustomer(Customer customer) throws UniqueConstraintViolationExcpetion, MandatoryFieldException {
		Key customerId = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			checkUniqueConstraintCustomer(datastore, customer.getEmail());
			checkMandatoryConstraintCustomer(customer);
			Key customersRootKey = KeyFactory.createKey("customersRoot", "customersRootKey");
	        
			Entity e_customer = new Entity("Customer", customersRootKey);
		
			e_customer.setProperty("email", customer.getEmail());
			e_customer.setProperty("password", customer.getPassword());
		
		
			datastore.put(e_customer);
			customerId = e_customer.getKey();
			tx.commit();
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		}
		
        	
        return customerId;
	}
	
	//Update a customer basing on key id
	public static void updateCustomer(Long id, Customer new_customer) throws UniqueConstraintViolationExcpetion, MandatoryFieldException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
        
		try {
			
        	checkMandatoryConstraintCustomer(new_customer);
        	//checkUniqueConstraintCustomer(pm, new_customer, id);
        	//find the customer object from datastore by id
        	com.google.appengine.api.search.Query.Builder query = com.google.appengine.api.search.Query.newBuilder(); 
        	
        	
        	query.addKindBuilder().setName("Greeting");
        	query.setFilter(makeFilter(
        	    "__key__", PropertyFilter.Operator.HAS_ANCESTOR, makeValue(guestbookKey)).build());
        	query.addOrder(makeOrder("date", PropertyOrder.Direction.DESCENDING));
        	query.setLimit(10);

        	RunQueryRequest.Builder queryRequest = RunQueryRequest.newBuilder().setQuery(query);
        	List<EntityResult> result =
        	    datastore.runQuery(queryRequest.build()).getBatch().getEntityResultList();
        	
        	
        	
        	
        	
            
       	} finally {
       		
        }
        
	}
	
	
	/*Check if customer email already exists*/
	private static void checkUniqueConstraintCustomer(DatastoreService p_datastore, String p_email) throws UniqueConstraintViolationExcpetion{
		Filter emailExistsFilter = new FilterPredicate ("email", FilterOperator.EQUAL, p_email);
		Query q = new Query("Customer").setFilter(emailExistsFilter);
		
		PreparedQuery pq = p_datastore.prepare(q);
		if (pq.countEntities(withLimit(1)) > 0) 
			throw new UniqueConstraintViolationExcpetion("Customer with email "+p_email+ " already exists!");
	
	}
	
	//check unique constraint (email on Customer entity). If there is one occurrence, Id parameter is check against
	//id of first occurrence. If they are equals the check is removed because the email is referencing
	//the same entity (may be an update)
	/*private static void checkUniqueConstraintCustomer(PersistenceManager pm, Customer customer, Long id) throws UniqueConstraintViolationExcpetion{
		
		
		
		
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
	*/
	
	
	//check mandatory field constraint (email and password  on Customer entity)
	private static void checkMandatoryConstraintCustomer(Customer customer) throws MandatoryFieldException {
		if (customer.getEmail() == null || customer.getPassword()==null)
				throw new MandatoryFieldException("email and password are mandatory!");
		
	}
	
	
	/*
	//check mandatory field constraint (email and password  on Customer entity)
	private static void checkMandatoryConstraintProvider(Provider provider) throws MandatoryFieldException {
		if (provider.getEmail() == null || provider.getPassword()==null || provider.getName()==null)
				throw new MandatoryFieldException("email, password and name are mandatory!");
		
	}
	*/
	
	/*
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
*/
	
	/*
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
	
		*/
	
	/*
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
	
	*/
	
	
	
	public static Customer getCustomerByEmail(String p_email, String p_password) throws WrongUserOrPasswordException {
		Customer customer = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			Filter emailExistsFilter = new FilterPredicate ("email", FilterOperator.EQUAL, p_email);
			Query q = new Query("Customer").setFilter(emailExistsFilter);		
			PreparedQuery pq = datastore.prepare(q);
			
			if (pq.countEntities(withLimit(1)) > 0) {
				
				//TODO instantiate Customer
				customer = new Customer();
				for (Entity result : pq.asIterable()) {
					customer.setEmail((String) result.getProperty("email"));
					customer.setPassword((String) result.getProperty("password"));
					customer.setId((Key) result.getKey());
				}
				
				//check if password is correct
				if (!(customer.getPassword().equals(p_password)))  throw new WrongUserOrPasswordException("Wrong email user or password");
				
			}else {
					throw new WrongUserOrPasswordException("Wrong email user or password");
			}
				
			tx.commit();
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		}
		
	     return customer;
		
	}
	
	
	
	/*
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
	*/
	
	/*
	
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
	*/
	
	/*
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
	*/
	
	
	
	/*Delete customer and all resto asssociated with the user*/
	/*
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
	
	*/
	
	/*
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
	*/
	
	
	/*
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
	*/
	
	/*
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
	
	*/
	
	
	/*
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
	*/
	
	/*
	
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
	*/
	
	
	/*Check if token exists, is not expired and is associated to the userEmail*/
	/*
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
	*/
	
}

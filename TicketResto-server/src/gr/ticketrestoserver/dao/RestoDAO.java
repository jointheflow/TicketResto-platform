package gr.ticketrestoserver.dao;




import java.util.List;

import gr.ticketrestoserver.dao.exception.UniqueConstraintViolationExcpetion;
import gr.ticketrestoserver.entity.Customer;
import gr.ticketrestoserver.entity.Payment;
import gr.ticketrestoserver.entity.Provider;
import gr.ticketrestoserver.entity.Resto;
import gr.ticketrestoserver.helper.DAOHelper;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;



public class RestoDAO {
	
	
	public static Key addCustomer(Customer customer) {
		Key customerId=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        try {
            checkUniqueConstraintCustomer(pm, customer);
        	pm.makePersistent(customer);
            customerId= customer.getId();
        } catch (UniqueConstraintViolationExcpetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            pm.close();
        }
        return customerId;
	}
	
	
	public static void updateCustomer(Customer customer) {
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        try {
            pm.makePersistent(customer);
            
            
       	} finally {
            pm.close();
        }
        
	}
	
	//check unique constraint (email on Customer entity)
	private static void checkUniqueConstraintCustomer(PersistenceManager pm, Customer customer) throws UniqueConstraintViolationExcpetion{
		Query query = pm.newQuery(Customer.class);
		query.setFilter("email == email_p");
		query.declareParameters("String email_p");
		@SuppressWarnings("unchecked")
		List<Customer> result = (List<Customer>)query.execute(customer.getEmail());
		if (!result.isEmpty())
			throw new UniqueConstraintViolationExcpetion("Customer with email "+customer.getEmail()+ " already exists!");
	
	}
	
	
	
	public static Key addProvider(Provider provider) {
		Key providerId=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        try {
        	checkUniqueConstraintProvider(pm, provider);
        	pm.makePersistent(provider);
            providerId= provider.getId();
        } catch (UniqueConstraintViolationExcpetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	
	public static Key addPayment(Payment payment) {
		Key PaymentId=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        try {
            pm.makePersistent(payment);
            PaymentId= payment.getId();
        } finally {
            pm.close();
        }
        return PaymentId;
	}
	
	
	public static Key updateResto(Resto resto) {
		Key restoId=null;
		Resto restoObj = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        try {
        	//if resto has not an id make it persistent
        	if (resto.getId() ==null) {
        		pm.makePersistent(resto);
        		restoId=resto.getId();
        	}
        	else {
	        	//retrieve resto instance by using the provider-customer key
	        	restoObj = pm.getObjectById(Resto.class, resto.getId());
	        	//if not exists make persistence the new instance
	        	if (restoObj == null)       	    			
	            	pm.makePersistent(resto);
	        	
	        	restoId= restoObj.getId();
        	}
        	
    			
        } finally {
            pm.close();
        }
        return restoId;
	}
	
	
	public static Customer getCustomerByEmail(String email) {
		Customer customer = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		try {
			
			Query query = pm.newQuery(Customer.class);
			query.setFilter("email == email_p");
			query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<Customer> result = (List<Customer>)query.execute(email);
			if (!result.isEmpty()) {
				customer = result.get(0);
			}
		 } finally {
	            pm.close();
	        }
	        return customer;
		
	}
	
	public static Provider getProviderByEmail(String email) {
		Provider provider = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		try {
			Query query = pm.newQuery(Provider.class);
			query.setFilter("email == email_p");
			query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			List<Provider> result = (List<Provider>)query.execute(email);
			if (!result.isEmpty())
				provider = result.get(0);
		 } finally {
	            pm.close();
	        }
	        return provider;
	}
	
	public static Resto getResto(Customer customer, Provider provider) {
		Resto resto = null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
		try {
			pm.getFetchPlan().setGroup(FetchGroup.ALL);
			Query query = pm.newQuery(Resto.class);
			//query.setFilter("customer == customer_p && provider == provider_p");
			query.setFilter("provider == provider_p && customer == customer_p ");
			query.declareParameters(Key.class.getName() + " provider_p, "+Key.class.getName()+" customer_p");
			//query.declareParameters("String email_p");
			@SuppressWarnings({ "unchecked"})
			//List<Resto> result = (List<Resto>)query.execute(customer, provider);
			List<Resto> result = (List<Resto>)query.execute(provider.getId(), customer.getId());
			if (!result.isEmpty())
				resto = result.get(0);
			
			
		 } finally {
	            pm.close();
	        }
	        return resto;
		
		
	}
	
	
}

package gr.ticketrestoserver.dao;



import java.util.List;

import gr.ticketrestoserver.entity.Customer;
import gr.ticketrestoserver.entity.Payment;
import gr.ticketrestoserver.entity.Provider;
import gr.ticketrestoserver.entity.Resto;
import gr.ticketrestoserver.helper.DAOHelper;


import javax.jdo.PersistenceManager;





import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;



public class RestoDAO {
	
	
	public static Key addCustomer(Customer customer) {
		Key customerId=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        try {
            pm.makePersistent(customer);
            customerId= customer.getId();
        } finally {
            pm.close();
        }
        return customerId;
	}
	
	public static Key addProvider(Provider provider) {
		Key providerId=null;
		PersistenceManager pm = DAOHelper.getPersistenceManagerFactory().getPersistenceManager();
        try {
            pm.makePersistent(provider);
            providerId= provider.getId();
        } finally {
            pm.close();
        }
        return providerId;
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
        	//retrieve resto instance by using the provider-customer key
        	Query query = pm.newQuery(Resto.class);
    		query.setFilter("provider == provider_param && customer == customer_param");
    		query.declareParameters("Provider provider_param, Customer customer_param");

    		//query.setOrdering("id DESC");
    		@SuppressWarnings("unchecked")
    		List<Resto> result = (List<Resto>)query.execute(resto.getProvider(), resto.getCustomer());
    		
    		if (!result.isEmpty()) {
    			restoObj = (Resto )result.get(0);
    			//change the value of amount
    			restoObj.setAmount(resto.getAmount());
    			restoId = restoObj.getId();
    		}
    		else {
    			//if not exists make persistence the new instance
            	pm.makePersistent(resto);
                restoId= resto.getId();
    			
    		}
    		
        	
        	
            
        } finally {
            pm.close();
        }
        return restoId;
	}
}

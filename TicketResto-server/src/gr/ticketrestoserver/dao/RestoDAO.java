package gr.ticketrestoserver.dao;



import gr.ticketrestoserver.entity.Customer;
import gr.ticketrestoserver.entity.Payment;
import gr.ticketrestoserver.entity.Provider;
import gr.ticketrestoserver.helper.DAOHelper;

import javax.jdo.PersistenceManager;




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
}

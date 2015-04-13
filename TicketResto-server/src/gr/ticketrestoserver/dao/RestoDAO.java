package gr.ticketrestoserver.dao;



import gr.ticketrestoserver.entity.Customer;
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

}

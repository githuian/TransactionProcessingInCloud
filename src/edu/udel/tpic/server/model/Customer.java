package edu.udel.tpic.server.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.udel.tpic.server.dao.EntityDAO;

public class Customer {

	private static final Logger logger = Logger.getLogger(EntityDAO.class
			.getCanonicalName());
	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	public static boolean createCustomer(String userName, String passWord,
			String secNumber, String firstName, String lastName,
			String address, String phoneNumber, String email,String postCode) {
		Entity cutomer = getCustomer(userName);
		Date date = new Date();
		if (cutomer == null) {
			logger.log(Level.INFO, "Creating entity");
			cutomer = new Entity("Customer", userName);
			cutomer.setProperty("userName", userName);
			cutomer.setProperty("passWord", passWord);
			cutomer.setProperty("secNumber", secNumber);
			cutomer.setProperty("firstName", firstName);
			cutomer.setProperty("lastName", lastName);
			cutomer.setProperty("address", address);
			cutomer.setProperty("phoneNumber", phoneNumber);
			cutomer.setProperty("email", email);
			cutomer.setProperty("postCode", postCode);
			cutomer.setProperty("createdDate", dateFormat.format(date));
		} else {
			return false;
		}
		logger.log(Level.INFO, "Saving entity");
		Key key = cutomer.getKey();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.put(cutomer);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return false;
			} else {
				EntityDAO.addToCache(key, cutomer);
			}
		}

		return true;
	}

	public static boolean updateCustomer(String userName, String passWord,
			String secNumber, String firstName, String lastName,
			String address, String phoneNumber, String email,String postCode) {
		Entity cutomer = getCustomer(userName);
		Date date = new Date();
		if (cutomer == null) {
			return false;
		} else {
			if (userName != null && !"".equals(userName))
				cutomer.setProperty("userName", userName);
			if (userName != null && !"".equals(passWord))
				cutomer.setProperty("passWord", passWord);
			if (userName != null && !"".equals(secNumber))
				cutomer.setProperty("secNumber", secNumber);
			if (userName != null && !"".equals(firstName))
				cutomer.setProperty("firstName", firstName);
			if (userName != null && !"".equals(lastName))
				cutomer.setProperty("lastName", lastName);
			if (userName != null && !"".equals(address))
				cutomer.setProperty("address", address);
			if (userName != null && !"".equals(phoneNumber))
				cutomer.setProperty("phoneNumber", phoneNumber);
			if (userName != null && !"".equals(email))
				cutomer.setProperty("email", email);
			if (userName != null && !"".equals(postCode))
				cutomer.setProperty("postCode", postCode);
			cutomer.setProperty("updatedDate", dateFormat.format(date));
		}

		logger.log(Level.INFO, "Saving entity");
		Key key = cutomer.getKey();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.put(cutomer);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return false;
			} else {
				EntityDAO.addToCache(key, cutomer);
			}
		}
		return true;
	}

	public static Entity getCustomer(String userName) {
		Key key = KeyFactory.createKey("Customer", userName);
		return EntityDAO.findEntity(key);
	}

	public static Iterable<Entity> getAccountsForCustomer(String userName) {
		 Query query = new Query("BankAccount");
		 Key ancestorKey = KeyFactory.createKey("Customer", userName);
		    query.setAncestor(ancestorKey);
		    Filter filter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestorKey);
		    query.setFilter(filter);
		 //   query.addSort("accountNumber", SortDirection.ASCENDING);
		    PreparedQuery pq = datastore.prepare(query);
		    return pq.asIterable();	
	}
	
	public static boolean deleteCustomer(String userName) {
		logger.log(Level.INFO, "Deleting customer");
		Transaction txn = datastore.beginTransaction();
		Key key = KeyFactory.createKey("Customer", userName);
		Iterable<Entity> accounts =  BankAccount
				.getAccountsForCustomer(userName);
		Iterator it = accounts.iterator();
		int count = 0;
		while(it.hasNext()){
			it.next();
			count ++;
		}
		if (count !=0) {
			return false;
		}
		try {
			datastore.delete(key);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return false;
			} else {
				EntityDAO.deleteFromCache(key);
			}
		}
		return true;
	}

}

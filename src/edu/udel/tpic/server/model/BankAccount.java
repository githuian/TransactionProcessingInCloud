package edu.udel.tpic.server.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;

import edu.udel.tpic.server.dao.EntityDAO;
import edu.udel.tpic.server.util.JsonUtil;

/**
 * This class handles CRUD operations related to BankAccount entity.
 * 
 * 
 */

public class BankAccount {
	private static final Logger logger = Logger.getLogger(EntityDAO.class
			.getCanonicalName());
	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	public static boolean createAccount(String userName, String accountNumber,
			double initBalance) {
		Date date = new Date();
		Entity customer = Customer.getCustomer(userName);
		Entity account = getSingleBankAccount(accountNumber);
		if (account == null) {
			account = new Entity("BankAccount", customer.getKey());
			account.setProperty("accountNumber", accountNumber);
			account.setProperty("balance", initBalance);
			account.setProperty("createdDate", dateFormat.format(date));
		} 
		else 
			return false;
		
		logger.log(Level.INFO, "Creating account");
		Key key = account.getKey();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.put(account);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			//	TransactionLog.createlog(accountNumber, "createaccount",
			//			"create action rollback", false);
				return false;
			} else {
				EntityDAO.addToCache(key, account);
			}
		}
		TransactionLog.createlog(accountNumber, "createaccount", "create success", true);
		return true;
	}

	public static double getBalance(String accountNumber) {
		double balance = -1;
		Entity account = getSingleBankAccount(accountNumber);
		if (account != null) {
			balance = (Double) account.getProperty("balance");
	//		Key key = account.getKey();
			TransactionLog.createlog(accountNumber, "Balance", "balance amout:"
					+ balance, true);
		}
		return balance;
	}

	public static boolean deposit(String accountNumber, double amount) {
		logger.log(Level.INFO, "Depositting");
		double balance = 0;
		Entity account = getSingleBankAccount(accountNumber);
		if (account == null)
			return false;
		balance = getBalance(accountNumber);
		if(balance == -1)
			return false;
		balance += amount;
		account.setProperty("balance", balance);
		Key key = account.getKey();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.put(account);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				TransactionLog.createlog(accountNumber, "Deposit",
						"deposit rollback, deposit amout:" + amount, false);
				return false;
			} else {
				EntityDAO.addToCache(key, account);	
			}
		}
		TransactionLog.createlog(accountNumber, "Deposit", "deposit amout:"
				+ amount, true);
		return true;

	}

	public static boolean debit(String accountNumber, double amount) {
		logger.log(Level.INFO, "Debitting");
		double balance = 0;
		Entity account = getSingleBankAccount(accountNumber);
		if(account == null)
			return false;
		balance = getBalance(accountNumber);
		balance -= amount;
		if (balance < 0)
			return false;
		account.setProperty("balance", balance);
		Transaction txn = datastore.beginTransaction();
			Key key = account.getKey();
			try {
				datastore.put(account);
				txn.commit();
			} finally {
				if (txn.isActive()) {
					txn.rollback();
					TransactionLog.createlog(accountNumber, "Debit",
							"debit rollback, deposit amout:" + amount, false);
					return false;
				} else {
					EntityDAO.addToCache(key, account);
					
				}
			}
			TransactionLog.createlog(accountNumber, "Debit", "debit amout:"
					+ amount, true);
		return true;
	}

	public static Iterable<Entity> getAccountsForCustomer(String userName) {
		Key ancestorKey = KeyFactory.createKey("Customer", userName);
		return EntityDAO.listChildren("BankAccount", ancestorKey);
	}

	/**
	 * get Item with item name
	 * 
	 * @param itemName
	 *            : get itemName
	 * @return item entity
	 */
	public static Entity getSingleBankAccount(String accountNumber) {
		Query query = new Query("BankAccount");
		Filter accountFilter = new Query.FilterPredicate("accountNumber",
				FilterOperator.EQUAL, accountNumber);
		query.setFilter(accountFilter);
		List<Entity> results = EntityDAO.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());
		if (!results.isEmpty()) {
			return (Entity) results.remove(0);
		}
		return null;
	}

	public static boolean deleteBankAccount(String accountNumber) {
		Transaction txn = datastore.beginTransaction();
		double balance = getBalance(accountNumber);
		if (balance > 0)
			return false;
		if (balance < 0)
			return false;
		if (balance == 0) {
			logger.log(Level.INFO, "Deleting BankAccount");
			Key key = KeyFactory.createKey("Customer", accountNumber);
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
		}
		return true;
	}



}

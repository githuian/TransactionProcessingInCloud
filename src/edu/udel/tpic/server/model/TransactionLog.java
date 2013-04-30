package edu.udel.tpic.server.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import edu.udel.tpic.server.dao.EntityDAO;

public class TransactionLog {

	private static DateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm:ss");

	public static void createlog(String accountNumber, String action,
			String description, boolean result) {
		 Entity account = BankAccount.getSingleBankAccount(accountNumber);
		 if(account!=null){
		Date date = new Date();
		Entity log = new Entity("Log",account.getKey());
		log.setProperty("accountNumber",accountNumber);
		log.setProperty("action", action.toLowerCase());
		log.setProperty("description", description);
		log.setProperty("result", result);
		log.setProperty("transactiontime", dateFormat.format(date));
		EntityDAO.persistEntity(log);
	 }
	}

	public static Iterable<Entity> getLogsForBankAccount(String accountNumber,
			String actiontype, String start, String end) throws ParseException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key ancestorKey = KeyFactory.createKey("BankAccount", accountNumber);	
		Query query = new Query("Log");		
		Filter finalFilter;	
		
		Filter accountMatch = new FilterPredicate("accountNumber",
					FilterOperator.EQUAL, accountNumber);
		
	Filter startdate = new FilterPredicate("transactiontime",
				FilterOperator.GREATER_THAN_OR_EQUAL, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(start+" 00:00:00"));
		
	//Filter enddate = new FilterPredicate("transactiontime",
	//		FilterOperator.LESS_THAN_OR_EQUAL, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(end+" 00:00:00"));			
		
	Filter actionType = new FilterPredicate("action",
				FilterOperator.EQUAL, actiontype);		
		
	if("".equals(actiontype)||"all".equalsIgnoreCase(actiontype))
		//finalFilter =accountMatch;
		finalFilter = new CompositeFilter(CompositeFilterOperator.AND,
			Arrays.asList(accountMatch,startdate));
		else 
		finalFilter = new CompositeFilter(CompositeFilterOperator.AND,
				Arrays.asList(accountMatch,actionType,startdate));		
		
	//	query.setAncestor(ancestorKey);
		query.setFilter(finalFilter);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
		
	}

}

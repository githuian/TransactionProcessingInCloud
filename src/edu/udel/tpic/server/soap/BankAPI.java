package edu.udel.tpic.server.soap;



import javax.jws.WebMethod;
import javax.jws.WebService;

import java.lang.Iterable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.appengine.api.datastore.Entity;

import edu.udel.tpic.server.model.BankAccount;
import edu.udel.tpic.server.model.Customer;
import edu.udel.tpic.server.model.Item;
import edu.udel.tpic.server.model.Product;
import edu.udel.tpic.server.model.TransactionLog;
import edu.udel.tpic.server.util.JsonUtil;

@WebService
public class BankAPI {	
	private static final Logger logger = Logger.getLogger(BankAPI.class.getCanonicalName());
  	
  @WebMethod  
  public boolean createCustomer(String customerName,String passWord,String secNumber,String firstName,String lastName,String address,String phoneNumber,String email,String postCode) {
    logger.log(Level.INFO,"creating customer");    
    if( Customer.createCustomer(customerName, passWord, secNumber, firstName, lastName, address, phoneNumber, email,postCode))
      return true;
    else
      return false;
  }
  
    
  @WebMethod  
  public boolean createBankAccount(String customerName, String accountNumber, String initBalance){
    logger.log(Level.INFO,"creating or updating item");    
    double amount = (Double.parseDouble(initBalance));
    if( BankAccount.createAccount(customerName, accountNumber, amount))
      return true;
    else
      return false;
  }  
  
  @WebMethod  
  public boolean deleteCustomer(String userName){
    logger.log(Level.INFO,"deleting customer");	  
    if(Customer.deleteCustomer(userName))
    	return true;
    else 
    	return false;
  }  
    
  @WebMethod  
  public boolean deleteBankAccount(String accountNumber){
    logger.log(Level.INFO,"deleting account");	  
	if(BankAccount.deleteBankAccount(accountNumber))
		return true;
	else 
		return false;
  }
  
  @WebMethod  
  public double getBalance(String accountNumber){
    logger.log(Level.INFO,"deleting account");	  
	return BankAccount.getBalance(accountNumber);
  }
  
  
  @WebMethod
  public boolean deposit(String accountNumber,String amount){
	  logger.log(Level.INFO,"deposit");	  
	  double depositAmount = (Double.parseDouble(amount));
		if(BankAccount.deposit(accountNumber, depositAmount))
			return true;
		else 
			return false;
  }
  
  @WebMethod
  public boolean debit(String accountNumber,String amount){    
	  logger.log(Level.INFO,"debit");
	  double debitAmount = (Double.parseDouble(amount));
			if(BankAccount.debit(accountNumber, debitAmount))
				return true;
			else 
				return false;
  }
  
  @WebMethod
  public String getTransactionLog(String accountNumber,String actionType,String start,String end) throws ParseException{    
	  logger.log(Level.INFO,"debit");	  
	  Iterable<Entity> entities = null;    
	    entities = TransactionLog.getLogsForBankAccount(accountNumber,actionType,start,end);    
	    return JsonUtil.writeJSON(entities);
  }
  
  

}

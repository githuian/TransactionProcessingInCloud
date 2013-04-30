package edu.udel.tpic.server.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import edu.udel.tpic.server.dao.EntityDAO;

public class Address {
	public static Entity createOrUpdateCardHoder(String customerName,String passWord,String unit,
			 String street,String city,String state,String postal ) {
		    Entity address = getAddress(customerName);
		    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  		Date date = new Date();
		  	if (address == null) {	  		
		  		address = new Entity("Address", customerName);
		  		address.setProperty("unit", unit);
		  		address.setProperty("street", street);
		  		address.setProperty("city", city);
		  		address.setProperty("state", state);
		  		address.setProperty("postal", postal);
		  		address.setProperty("createdDate", dateFormat.format(date));
		  	}
		  	else {
		  	if(unit!=null && !"".equals(unit)) address.setProperty("unit", unit);
		  	if(street!=null && !"".equals(street)) address.setProperty("street", street);
		  	if(city!=null && !"".equals(city)) address.setProperty("city", city);
		  	if(state!=null && !"".equals(state))	address.setProperty("state", state);
		     if(postal!=null && !"".equals(postal))	address.setProperty("postal", postal);
		     address.setProperty("updatedDate", dateFormat.format(date));
		  	}
		  	return address;
		  }
	 
	 public static Entity getAddress(String userName){
		 Key key = KeyFactory.createKey("Address",userName);
		  	return EntityDAO.findEntity(key);
	 }

}

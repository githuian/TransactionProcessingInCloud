package edu.udel.tpic.server.dao;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

import edu.udel.tpic.server.Util;

public class EntityDAO {
	 private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());
	 private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();  

	 /**
	  * 
	  * @param entity  : entity to be persisted
	  */
	   public static void persistEntity(Entity entity) {
	   	logger.log(Level.INFO, "Saving entity");
	   	datastore.put(entity);  	
	   }

	 	/**
	 	 * Delete the entity from persistent store represented by the key
	 	 * @param key : key to delete the entity from the persistent store
	 	 */
	   public static void deleteEntity(Key key) {
	     logger.log(Level.INFO, "Deleting entity");
	     datastore.delete(key);  	
	   }
	   
	   /**
	    * Delete list of entities given their keys
	    * @param keys
	    */
	   public static void deleteEntity(final List<Key> keys){
	     datastore.delete(new Iterable<Key>() {
	       public Iterator<Key> iterator() {
	 	    return keys.iterator();
	 	  }
	     });    
	   }

	 	/**
	 	 * Search and return the entity from datastore.
	 	 * @param key : key to find the entity
	 	 * @return  entity
	 	 */
	  
	   public static Entity findEntity(Key key) {
	   	logger.log(Level.INFO, "Search the entity");
	   	try {	  
	   	  return datastore.get(key);
	   	} catch (EntityNotFoundException e) {
	   	  return null;
	   	}
	   }
	  

	 	/***
	 	 * Search entities based on search criteria
	 	 * @param kind
	 	 * @param searchBy
	 	 *            : Searching Criteria (Property)
	 	 * @param searchFor
	 	 *            : Searching Value (Property Value)
	 	 * @return List all entities of a kind from the cache or datastore (if not
	 	 *         in cache) with the specified properties
	 	 */
	   public static Iterable<Entity> listEntities(String kind, String searchBy, String searchFor) {
	   	logger.log(Level.INFO, "Search entities based on search criteria");
	   	Query q = new Query(kind);
	   	if (searchFor != null && !"".equals(searchFor)) {
	   	  q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
	   	}
	   	PreparedQuery pq = datastore.prepare(q);
	   	return pq.asIterable();
	   }
	   
	   
	   /**
	    * Search entities based on ancestor
	    * @param kind
	    * @param ancestor
	    * @return
	    */
	   public static Iterable<Entity> listChildren(String kind, Key ancestor) {
	   	logger.log(Level.INFO, "Search entities based on parent");
	   	Query q = new Query(kind);
	   	q.setAncestor(ancestor);
	   	q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
	   	PreparedQuery pq = datastore.prepare(q);
	   	return pq.asIterable();
	   }
	   
	   /**
	    * 
	    * @param kind
	    * @param ancestor
	    * @return
	    */
	   public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
	   	logger.log(Level.INFO, "Search entities based on parent");
	   	Query q = new Query(kind);
	   	q.setAncestor(ancestor).setKeysOnly();
	   	q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
	   	PreparedQuery pq = datastore.prepare(q);
	   	return pq.asIterable();
	   }
	   
	   public static DatastoreService getDatastoreServiceInstance(){
			  return datastore;
		  }
}

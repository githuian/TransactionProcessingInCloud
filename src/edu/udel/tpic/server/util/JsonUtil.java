package edu.udel.tpic.server.util;

/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import edu.udel.tpic.server.dao.EntityDAO;


/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 * 
 */
public class JsonUtil {

  private static final Logger logger = Logger.getLogger(JsonUtil.class.getCanonicalName());
 

	/**
	 * List the entities in JSON format
	 * 
	 * @param entities  entities to return as JSON strings
	 */
  public static String writeJSON(Iterable<Entity> entities) {
    logger.log(Level.INFO, "creating JSON format object");
  	StringBuilder sb = new StringBuilder();
  	
  	int i = 0;
  	sb.append("{\"data\": [");
  	for (Entity result : entities) {
  	  Map<String, Object> properties = result.getProperties();
  	  sb.append("{");
  	  if (result.getKey().getName() == null)
  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
  	  else
  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
  
  	  for (String key : properties.keySet()) {
  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
  	  }
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	  sb.append("},");
  	  i++;
  	}
  	if (i > 0) {
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	}  
  	sb.append("]}");
  	return sb.toString();
  }
  
  /**
	 * Retrieves Parent and Child entities into JSON String
	 * 
	 * @param entities
	 *            : List of parent entities
	 * @param childKind
	 *            : Entity type for Child
	 * @param fkName
	 *            : foreign-key to the parent in the child entity
	 * @return JSON string
	 */
  public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName) {
  	logger.log(Level.INFO, "creating JSON format object for parent child relation");
  	StringBuilder sb = new StringBuilder();
  	int i = 0;
  	sb.append("{\"data\": [");
  	for (Entity result : entities) {
  	  Map<String, Object> properties = result.getProperties();
  	  sb.append("{");
  	  if (result.getKey().getName() == null)
  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
  	  else
  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
  	  for (String key : properties.keySet()) {
  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
  	  }
  	  Iterable<Entity> child = EntityDAO.listEntities(childKind, fkName,
  	  String.valueOf(result.getKey().getId()));
  	  for (Entity en : child) {
  		for (String key : en.getProperties().keySet()) {
  		  sb.append("\"" + key + "\" : \"" + en.getProperties().get(key)+ "\",");
  		}
  	  }
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	  sb.append("},");
  	  i++;
  	}
  	if (i > 0) {
  	  sb.deleteCharAt(sb.lastIndexOf(","));
  	}  
  	sb.append("]}");
  	return sb.toString();
  }
 
  
	/**
	 * Utility method to send the error back to UI
	 * @param data
	 * @param resp
	 * @throws IOException 
	 */
  public static String getErrorMessage(Exception ex) throws IOException{
    return "Error:"+ex.toString();
  }
 
  /**
   * get DatastoreService instance
   * @return DatastoreService instance
   */
 
}

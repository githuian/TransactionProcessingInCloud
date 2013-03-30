package edu.udel.tpic.server.soap;

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

import javax.jws.WebMethod;
import javax.jws.WebService;

import java.lang.Iterable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.appengine.api.datastore.Entity;

import edu.udel.tpic.server.Util;
import edu.udel.tpic.server.model.Item;
import edu.udel.tpic.server.model.Product;

@WebService
public class EntityAPI {	
	private static final Logger logger = Logger.getLogger(EntityAPI.class.getCanonicalName());
  	
  @WebMethod  
  public boolean createOrUpdateProduct(String name,String description) {
    logger.log(Level.INFO,"creating product");    
    if( Product.createOrUpdateProduct(name, description) != null)
      return true;
    else
      return false;
  }
  
    
  @WebMethod  
  public boolean createOrUpdateItem(String productName, String itemName, String price){
    logger.log(Level.INFO,"creating or updating item");    
    if( Item.createOrUpdateItem(productName,itemName,price)!=null )
      return true;
    else
      return false;
  }  
  
  @WebMethod  
  public String deleteProduct(String name){
    logger.log(Level.INFO,"deleting product");	  
    return Product.deleteProduct(name);
  }  
    
  @WebMethod  
  public String deleteItem(String name){
    logger.log(Level.INFO,"deleting item");	  
	return Item.deleteItem(name);
  }
  
  @WebMethod
  public String getAllProducts(String kind){
    Iterable<Entity> entities = null;    
    entities = Product.getAllProducts(kind);    
    return Util.writeJSON(entities);
  }
  
  @WebMethod
  public String getProduct(String name){    
    Entity product = Product.getProduct(name);
    if (product != null) {
      Set<Entity> resultSet = new HashSet<Entity>();
      resultSet.add(product); 
      return Util.writeJSON(resultSet);
    }
    return null;
  }
    
  @WebMethod
  public String getAllItems(String kind){
    Iterable<Entity> entities = null;    
    entities = Item.getAllItems(kind);    
    return Util.writeJSON(entities); 
  }
  
  @WebMethod
  public String getItem(String name){
    Iterable<Entity> entities = null;    
    entities = Item.getItem(name);    
    return Util.writeJSON(entities); 
  }
  
  @WebMethod
  public String getItemsForProduct(String kind,String product){
    Iterable<Entity> entities = null;    
    entities = Item.getItemsForProduct(kind,product);    
    return Util.writeJSON(entities); 
  } 
}

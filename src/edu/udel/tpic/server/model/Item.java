package edu.udel.tpic.server.model;



import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

import edu.udel.tpic.server.Util;
import edu.udel.tpic.server.dao.EntityDAO;


/**
 * This class handles CRUD operations related to Item entity.
 * 
 *
 */

public class Item {

  /**
   * Create or update Item for a particular product. Product entity has one to many
   * relation-ship with Item entity
   * 
   * @param productName
   *          : product name for which the item is created.
   * @param itemName
   *          : item name
   * @param price
   *          : price of the item
   * @return
   */
  public static Entity createOrUpdateItem(String productName, String itemName,String price) {
    Entity product = Product.getProduct(productName);
    Entity item = getSingleItem(itemName);
    if(item == null){
      item = new Entity("Item",product.getKey());
      item.setProperty("name", itemName);
      item.setProperty("product", productName);
      item.setProperty("price", price);
    }
    else{
      if (price != null && !"".equals(price)) {
        item.setProperty("price", price);
      }           
    }
    EntityDAO.persistEntity(item);
    return item;
  }

  /**
   * get All the items in the list
   * 
   * @param kind
   *          : item kind
   * @return all the items
   */
  public static Iterable<Entity> getAllItems(String kind) {
  	Iterable<Entity> entities = EntityDAO.listEntities(kind, null, null);
  	return entities;
  }

  /**
   * Get the item by name
   * 
   * @param itemName
   *          : item name
   * @return Item Entity
   */
  public static Iterable<Entity> getItem(String itemName) {
  	Iterable<Entity> entities = EntityDAO.listEntities("Item", "name", itemName);
  	return entities;
  }

  /**
   * Get all the items for a product
   * 
   * @param kind
   *          : item kind
   * @param productName
   *          : product name
   * @return: all items of type product
   */
  public static Iterable<Entity> getItemsForProduct(String kind, String productName) {
    Key ancestorKey = KeyFactory.createKey("Product", productName);
    return EntityDAO.listChildren("Item", ancestorKey);
  }

  /**
   * get Item with item name
   * @param itemName: get itemName
   * @return  item entity
   */
  public static Entity getSingleItem(String itemName) {
    Query query = new Query("Item");
    query.addFilter("name", FilterOperator.EQUAL, itemName);
    List<Entity> results = EntityDAO.getDatastoreServiceInstance().prepare(query).asList(FetchOptions.Builder.withDefaults());
    if (!results.isEmpty()) {
      return (Entity)results.remove(0);
    }
    return null;
  }
  
  public static String deleteItem(String itemKey) {
    Entity entity = getSingleItem(itemKey);    
    if(entity != null){
    	EntityDAO.deleteEntity(entity.getKey());
      return("Item deleted successfully.");
    }
    else
      return("Item not found");      
  }
}

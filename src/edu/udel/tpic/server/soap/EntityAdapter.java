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


import edu.udel.tpic.server.soap.jaxws.*;


public class EntityAdapter {

  private EntityAPI entityAPI = new EntityAPI();

  /**
   * Generate the Response Object corresponding to the argument in the request object
   * @param request
   * @return returns response object
   */
  
  public CreateOrUpdateProductResponse createOrUpdateProduct(CreateOrUpdateProduct request){
    String name = request.getArg0();
    String description = request.getArg1();
    boolean status = entityAPI.createOrUpdateProduct(name,description);
    CreateOrUpdateProductResponse response = new CreateOrUpdateProductResponse();
    response.setReturn(status);
    return response;
  }
  
  public CreateOrUpdateItemResponse createOrUpdateItem(CreateOrUpdateItem request){
    String productName = request.getArg0();
    String itemName = request.getArg1();
    String price = request.getArg2();
    boolean status = entityAPI.createOrUpdateItem(productName,itemName,price);
    CreateOrUpdateItemResponse response = new CreateOrUpdateItemResponse();
    response.setReturn(status);
    return response;
  }
  
  public DeleteProductResponse deleteProduct(DeleteProduct request){
    String name = request.getArg0();
    String status = entityAPI.deleteProduct(name);
    DeleteProductResponse response = new DeleteProductResponse();
    response.setReturn(status);
    return response;
  }
  
  public DeleteItemResponse deleteItem(DeleteItem request){
    String name = request.getArg0();
    String status = entityAPI.deleteItem(name);
    DeleteItemResponse response = new DeleteItemResponse();
    response.setReturn(status);
    return response;
  }  
  
  public GetProductResponse getProduct(GetProduct request){
    String name = request.getArg0();
    String productJSON = entityAPI.getProduct(name);
    GetProductResponse response = new GetProductResponse();
    response.setReturn(productJSON);
    return response;
  }
  
  public GetAllProductsResponse getAllProducts(GetAllProducts request){
    String kind = request.getArg0();
    String productsJSON = entityAPI.getAllProducts(kind);
    GetAllProductsResponse response = new GetAllProductsResponse();
    response.setReturn(productsJSON);
    return response;
  }
  
  public GetItemResponse getItem(GetItem request){
    String name = request.getArg0();
    String itemJSON = entityAPI.getItem(name);
    GetItemResponse response = new GetItemResponse();
    response.setReturn(itemJSON);
    return response;
  }
  
  public GetItemsForProductResponse getItemsForProduct(GetItemsForProduct request){
    String kind = request.getArg0();
    String productName = request.getArg1();
    String itemsJSON = entityAPI.getItemsForProduct(kind,productName);
    GetItemsForProductResponse response = new GetItemsForProductResponse();
    response.setReturn(itemsJSON);
    return response;
  }
  
  public GetAllItemsResponse getAllItems(GetAllItems request){
    String kind = request.getArg0();
    String itemsJSON = entityAPI.getAllItems(kind);
    GetAllItemsResponse response = new GetAllItemsResponse();
    response.setReturn(itemsJSON);
    return response;
  }
}
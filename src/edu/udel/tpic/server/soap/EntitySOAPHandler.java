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

import java.util.Iterator;
import javax.xml.bind.JAXB;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SAAJResult;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import edu.udel.tpic.server.soap.jaxws.*;

public class EntitySOAPHandler {
  
  private static final String NAMESPACE_URI = "http://soap.server.tpic.udel.edu/";
  private static final QName CREATE_OR_UPDATE_PRODUCT_QNAME = new QName(NAMESPACE_URI,"createOrUpdateProduct");
  private static final QName CREATE_OR_UPDATE_ITEM_QNAME = new QName(NAMESPACE_URI,"createOrUpdateItem");
  private static final QName DELETE_PRODUCT_QNAME = new QName(NAMESPACE_URI,"deleteProduct");
  private static final QName DELETE_ITEM_QNAME = new QName(NAMESPACE_URI,"deleteItem");
  private static final QName GET_PRODUCT_QNAME = new QName(NAMESPACE_URI,"getProduct");
  private static final QName GET_ALL_PRODUCTS_QNAME = new QName(NAMESPACE_URI,"getAllProducts");
  private static final QName GET_ITEM_QNAME = new QName(NAMESPACE_URI,"getItem");
  private static final QName GET_ITEMS_FOR_PRODUCT_QNAME = new QName(NAMESPACE_URI,"getItemsForProduct");
  private static final QName GET_ALL_ITEMS_QNAME = new QName(NAMESPACE_URI,"getAllItems");
  
  
  private MessageFactory messageFactory;
  private EntityAdapter greeterAdapter;

  public EntitySOAPHandler() throws SOAPException {
    messageFactory = MessageFactory.newInstance();
    greeterAdapter = new EntityAdapter();
  }

  @SuppressWarnings("rawtypes")
  public SOAPMessage handleSOAPRequest(SOAPMessage request) throws SOAPException {
    SOAPBody soapBody = request.getSOAPBody();
    Iterator iterator = soapBody.getChildElements();
    Object responsePojo = null;
    while (iterator.hasNext()) {
      Object next = iterator.next();
      if (next instanceof SOAPElement) {
        SOAPElement soapElement = (SOAPElement) next;
        QName qname = soapElement.getElementQName();
        
          if(CREATE_OR_UPDATE_PRODUCT_QNAME.equals(qname)) {
        	  responsePojo = handleCreateOrUpdateProductRequest(soapElement);
        	  break;
          } else if(CREATE_OR_UPDATE_ITEM_QNAME.equals(qname)) {
        	  responsePojo = handleCreateOrUpdateItemRequest(soapElement);
        	  break;
          } else if(DELETE_PRODUCT_QNAME.equals(qname)) {
        	  responsePojo = handleDeleteProductRequest(soapElement);
        	  break;
          } else if(DELETE_ITEM_QNAME.equals(qname)) {
        	  responsePojo = handleDeleteItemRequest(soapElement);
        	  break;
          } else if(GET_PRODUCT_QNAME.equals(qname)){
        	  responsePojo = handleGetProductRequest(soapElement);
        	  break;
          } else if(GET_ALL_PRODUCTS_QNAME.equals(qname)){
            responsePojo = handleGetAllProductsRequest(soapElement);
            break;
          } else if(GET_ITEM_QNAME.equals(qname)){
            responsePojo = handleGetItemRequest(soapElement);
            break;
          } else if(GET_ITEMS_FOR_PRODUCT_QNAME.equals(qname)){
            responsePojo = handleGetItemsForProductRequest(soapElement);
            break;
          } else if(GET_ALL_ITEMS_QNAME.equals(qname)){
            responsePojo = handleGetAllItemsRequest(soapElement);
            break;
          }
      }
    }
    SOAPMessage soapResponse = messageFactory.createMessage();
    soapBody = soapResponse.getSOAPBody();
    if (responsePojo != null) {
      JAXB.marshal(responsePojo, new SAAJResult(soapBody));
    } else {
      SOAPFault fault = soapBody.addFault();
      fault.setFaultString("Unrecognized SOAP request.");
    }
    return soapResponse;
  }
  
  private Object handleCreateOrUpdateProductRequest(SOAPElement soapElement){
	  CreateOrUpdateProduct createProductRequest = JAXB.unmarshal(new DOMSource(soapElement), CreateOrUpdateProduct.class);
	  return greeterAdapter.createOrUpdateProduct(createProductRequest);
  }
  
  private Object handleCreateOrUpdateItemRequest(SOAPElement soapElement){
	  CreateOrUpdateItem createOrUpdateItemRequest = JAXB.unmarshal(new DOMSource(soapElement), CreateOrUpdateItem.class);
	  return greeterAdapter.createOrUpdateItem(createOrUpdateItemRequest);
  }
  
  private Object handleDeleteProductRequest(SOAPElement soapElement){
	  DeleteProduct deleteProductRequest = JAXB.unmarshal(new DOMSource(soapElement), DeleteProduct.class);
	  return greeterAdapter.deleteProduct(deleteProductRequest);
  }
  
  private Object handleDeleteItemRequest(SOAPElement soapElement){
	  DeleteItem deleteItemRequest = JAXB.unmarshal(new DOMSource(soapElement), DeleteItem.class);
	  return greeterAdapter.deleteItem(deleteItemRequest);
  }
  
  private Object handleGetProductRequest(SOAPElement soapElement){
	  GetProduct getProductRequest = JAXB.unmarshal(new DOMSource(soapElement), GetProduct.class);
	  return greeterAdapter.getProduct(getProductRequest);
  }
  
  private Object handleGetAllProductsRequest(SOAPElement soapElement){
    GetAllProducts getAllProductsRequest = JAXB.unmarshal(new DOMSource(soapElement), GetAllProducts.class);
    return greeterAdapter.getAllProducts(getAllProductsRequest);
  }
  
  private Object handleGetItemRequest(SOAPElement soapElement){
    GetItem request = JAXB.unmarshal(new DOMSource(soapElement), GetItem.class);
    return greeterAdapter.getItem(request);
  }
  
  private Object handleGetItemsForProductRequest(SOAPElement soapElement){
    GetItemsForProduct request = JAXB.unmarshal(new DOMSource(soapElement), GetItemsForProduct.class);
    return greeterAdapter.getItemsForProduct(request);
  }
  
  private Object handleGetAllItemsRequest(SOAPElement soapElement){
    GetAllItems request = JAXB.unmarshal(new DOMSource(soapElement), GetAllItems.class);
    return greeterAdapter.getAllItems(request);
  }


}
package edu.udel.tpic.server;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import edu.udel.tpic.server.dao.EntityDAO;
import edu.udel.tpic.server.model.Customer;
import edu.udel.tpic.server.util.JsonUtil;

/**
 * This servlet responds to the request corresponding to product entities. The servlet
 * manages the Product Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class CustomerServlet extends HttpServlet {

  private static final Logger logger = Logger.getLogger(ProductServlet.class.getCanonicalName());
  /**
   * Get the entities in JSON format.
   */

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
		doPost(req,resp);
  }
  /**
   * Create the entity and persist it.
   */
  protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    logger.log(Level.INFO, "Creating Product");
    PrintWriter out = resp.getWriter();
    String userName = req.getParameter("username");
    String secNumber = req.getParameter("secnumber");
    String passWord = req.getParameter("password");
    String lastName = req.getParameter("lastname");
    String firstName = req.getParameter("firstname");
    String address = req.getParameter("address");
    String phoneNumber = req.getParameter("phonenumber");
    String email = req.getParameter("email"); 
    try {
    	if(Customer.createCustomer(userName, passWord,secNumber,firstName,lastName,address,phoneNumber,email))
    		out.println("true");
    } catch (Exception e) {
      String msg = JsonUtil.getErrorMessage(e);
      out.print("false");
    }
  }
  
  protected void doUpdate(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	    logger.log(Level.INFO, "Creating Product");
	    PrintWriter out = resp.getWriter();
	    String userName = req.getParameter("username");
	    String secNumber = req.getParameter("secnumber");
	    String passWord = req.getParameter("password");
	    String lastName = req.getParameter("lastname");
	    String firstName = req.getParameter("firstname");
	    String address = req.getParameter("address");
	    String phoneNumber = req.getParameter("phonenumber");
	    String email = req.getParameter("email");     
	    try {	    	
	     if(Customer.updateCustomer(userName, passWord,secNumber,firstName,lastName,address,phoneNumber,email))
	     {
	    	 out.println("success");
	     }  
	    } catch (Exception e) {
	    	 out.println("fail");
	    }
	  }

  /**
   * Delete the product entity
   */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String userkey = req.getParameter("username");
    PrintWriter out = resp.getWriter();
    try{    	
    	out.println(Customer.deleteCustomer(userkey));
    } catch(Exception e) {
    	out.println(JsonUtil.getErrorMessage(e));
    }    
  }
  
  protected void doQuery(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	  String property = req.getParameter("property");
	  PrintWriter out = resp.getWriter();
	//  out.println(property);
       if("accountnumber".equalsIgnoreCase(property)){  
    	if(req.getSession().getAttribute("username")!=null){
	    String userName = (String) req.getSession().getAttribute("username");
	//    out.println(userName);
	    String accountNumber = "";
	    try{    	
	    	Iterable<Entity> accounts = Customer.getAccountsForCustomer(userName);
	    	int count =0;
	      for(Entity account:accounts){
	    	 accountNumber = (String) account.getProperty("accountNumber");
	    	  out.println("<option value="+accountNumber+">"+accountNumber+"</option>");
	    	  count++;
	      }
	   if(count ==0)   out.println("<option>No accounts under this user</option>");
	    } catch(Exception e) {
	    	out.println("<option>No accounts under this user</option>");
	    }    
	  }
       }
       else 
    	   out.println("<option>Your session is timeout</option>");
  }
  


  /**
   * Redirect the call to doDelete or doPut method
   */
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action");
    if (action.equalsIgnoreCase("delete")) {
      doDelete(req, resp);
      return;
    } else if (action.equalsIgnoreCase("create")) {
      doCreate(req, resp);
      return;
    }
    else if (action.equalsIgnoreCase("update")) {
        doUpdate(req, resp);
        return;
      }
    else if (action.equalsIgnoreCase("query")) {
        doQuery(req, resp);
        return;
      }
  }

}

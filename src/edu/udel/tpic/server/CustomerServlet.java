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
 * This servlet responds to the request corresponding to product entities. The
 * servlet manages the Product Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class CustomerServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(ProductServlet.class
			.getCanonicalName());

	/**
	 * Get the entities in JSON format.
	 */

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * Create the entity and persist it.
	 */
	protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.log(Level.INFO, "Creating Product");
		PrintWriter out = resp.getWriter();
		// if(req.getParameter("username")==null||req.getParameter("secnumber")==null||req.getParameter("password")==null
		// ||req.getParameter("lastname") ==null
		// ||req.getParameter("firstname")==null||req.getParameter("address")==null
		// ||req.getParameter("phonenumber")==null||req.getParameter("email")==null
		// ){
		String userName = req.getParameter("username");
		String secNumber = req.getParameter("secnumber");
		String passWord = req.getParameter("password");
		String lastName = req.getParameter("lastname");
		String firstName = req.getParameter("firstname");
		String address = req.getParameter("address");
		String phoneNumber = req.getParameter("phonenumber");
		String email = req.getParameter("email");
		try {
			if (Customer.createCustomer(userName, passWord, secNumber,
					firstName, lastName, address, phoneNumber, email))
				out.println("<p id=\"create-user-message\">success;</p>");
			else
				out.println("<p id=\"create-user-message\">fail;User exists or inner error</p>");
		} catch (Exception e) {
			out.print("<p id=\"create-user-message\">fail;" + e.getMessage()
					+ "</p>");
		}
		// }
		// else{
		// if( req.getParameter("username")==null)
		// out.println("<p id=\"create-user-message\">1fail;Please check parameters</p>");
		// if(req.getParameter("secnumber")==null)
		// out.println("<p id=\"create-user-message\">2fail;Please check parameters</p>");
		// if(req.getParameter("password")==null)
		// out.println("<p id=\"create-user-message\">3fail;Please check parameters</p>");
		// if(req.getParameter("lastname") ==null )
		// out.println("<p id=\"create-user-message\">4fail;Please check parameters</p>");
		// if( req.getParameter("firstname")==null)
		// out.println("<p id=\"create-user-message\">5fail;Please check parameters</p>");
		// if( req.getParameter("address")==null)
		// out.println("<p id=\"create-user-message\">6fail;Please check parameters</p>");
		// if( req.getParameter("phonenumber")==null)
		// out.println("<p id=\"create-user-message\">7fail;Please check parameters</p>");
		// if(req.getParameter("email")==null)
		// out.println("<p id=\"create-user-message\">8fail;Please check parameters</p>");
		// out.println("<p id=\"create-user-message\">fail;Please check parameters</p>");

		// }
	}

	protected void doUpdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.log(Level.INFO, "Creating Product");
		PrintWriter out = resp.getWriter();
		String seesionName = (String) req.getSession().getAttribute("username");
		if (seesionName != null && "admin".equalsIgnoreCase(seesionName)) {
			if (req.getParameter("username") == null
					|| req.getParameter("secnumber") == null
					|| req.getParameter("password") == null
					|| req.getParameter("lastname") == null
					|| req.getParameter("firstname") == null
					|| req.getParameter("address") == null
					|| req.getParameter("phonenumber") == null
					|| req.getParameter("email") == null) {
				String userName = req.getParameter("username");
				String secNumber = req.getParameter("secnumber");
				String passWord = req.getParameter("password");
				String lastName = req.getParameter("lastname");
				String firstName = req.getParameter("firstname");
				String address = req.getParameter("address");
				String phoneNumber = req.getParameter("phonenumber");
				String email = req.getParameter("email");
				try {
					if (Customer.updateCustomer(userName, passWord, secNumber,
							firstName, lastName, address, phoneNumber, email)) {
						out.println("<p id=\"create-user-message\">success;</p>");
					}
				} catch (Exception e) {
					out.print("<p id=\"create-user-message\">fail;"
							+ e.getMessage() + "</p>");
				}
			} else
				out.println("<p id=\"create-user-message\">fail;Please check parameters</p>");
		} else
			out.println("<p id=\"create-user-message\">fail;Please login as admin</p>");
	}

	/**
	 * Delete the product entity
	 */
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userkey = req.getParameter("username");
		PrintWriter out = resp.getWriter();
		String seesionName = (String) req.getSession().getAttribute("username");
		if (seesionName != null && "admin".equalsIgnoreCase(seesionName)) {
	    if(Customer.getCustomer(userkey)!=null){		
		try {
			if(Customer.deleteCustomer(userkey))
				out.println("<p id=\"delete-user-message\">success;The user has been deleted</p>");	
			else
				out.println("<p id=\"delete-user-message\">fail;User account is not empty,</p>");
		} catch (Exception e) {
			out.println("<p id=\"delete-user-message\">fail;"+e.getMessage()+"</p>");
		}
	    }
	    else out.println("<p id=\"delete-user-message\">fail;User not exists</p>");
		}
		else 
			out.println("<p id=\"delete-user-message\">fail;Please login as admin</p>");
	}

	protected void doQuery(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String property = req.getParameter("property");
		PrintWriter out = resp.getWriter();
		// out.println(property);
		if ("accountnumbers".equalsIgnoreCase(property)) {
			if (req.getSession().getAttribute("username") != null) {
				String userName = (String) req.getSession().getAttribute(
						"username");
				if("admin".equalsIgnoreCase(userName))
					userName = req.getParameter("username");
				// out.println(userName);
				String accountNumber = "";
				try {
					Iterable<Entity> accounts = Customer
							.getAccountsForCustomer(userName);
					int count = 0;
					for (Entity account : accounts) {
						accountNumber = (String) account
								.getProperty("accountNumber");
						out.println("<option value=" + accountNumber + ">"
								+ accountNumber + "</option>");
						count++;
					}
					if (count == 0)
						out.println("<option>No accounts under this user</option>");
				} catch (Exception e) {
					out.println("<option>No accounts under this user</option>");
				}
			} else
				out.println("<option>Your session is timeout</option>");
		}

		if ("queryuser".equalsIgnoreCase(property)) {
			if (req.getSession().getAttribute("username") != null
					&& "admin".equalsIgnoreCase((String) req.getSession()
							.getAttribute("username"))) {
				String queryUserName = req.getParameter("username");
				try {
					if (Customer.getCustomer(queryUserName) != null)
						out.println("<p id=\"queryuser-message\">success;</p>");
					else
						out.println("<p id=\"queryuser-message\">fail;User not exists</p>");
				} catch (Exception e) {
					out.println("<p id=\"queryuser-message\">fail;"
							+ e.getMessage() + "</p>");
				}
			} else
				out.println("<p id=\"queryuser-message\">fail;Sesson time out or you are not admin user</p>");
		}

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
		} else if (action.equalsIgnoreCase("update")) {
			doUpdate(req, resp);
			return;
		} else if (action.equalsIgnoreCase("query")) {
			doQuery(req, resp);
			return;
		}
	}

}

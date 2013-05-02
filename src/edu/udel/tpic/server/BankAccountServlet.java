package edu.udel.tpic.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import edu.udel.tpic.server.dao.EntityDAO;
import edu.udel.tpic.server.model.BankAccount;
import edu.udel.tpic.server.model.Customer;
import edu.udel.tpic.server.model.TransactionLog;
import edu.udel.tpic.server.util.JsonUtil;

/**
 * This servlet responds to the request corresponding to product entities. The
 * servlet manages the Product Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class BankAccountServlet extends HttpServlet {

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
		PrintWriter out = resp.getWriter();
		if (req.getParameter("accountnumber") != null & req.getParameter("username") != null
				& req.getParameter("initamount") != null) {	
			String userName = req.getParameter("username");
			String accountNumber = req.getParameter("accountnumber");
			double initBalance = Double.parseDouble(req
					.getParameter("initamount"));
			String seesionName = (String) req.getSession().getAttribute("username");
			if (seesionName!= null && "admin".equalsIgnoreCase(seesionName)) {
				try {
				if(Customer.getCustomer(userName)!=null){	
					if (BankAccount.createAccount(userName, accountNumber,
							initBalance))
						out.println("<p id=\"create-account-message\">success;Your account has been created</p>");
					else 
						out.println("<p id=\"create-account-message\">fail;Account exists</p>");
				}
				else
				out.println("<p id=\"create-account-message\">fail;Customer not exists</p>");
				} catch (Exception e) 
				{
					out.print("<p id=\"create-account-message\">fail;"+e.getMessage()+"</p>");
				}
			} else
				out.println("<p id=\"create-account-message\">fail;admin session timeout</p>");
		} else{
			out.println("<p id=\"create-account-message\">fail;Please check your parameters</p>");
		}
	}

	protected void doUpdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		if (req.getParameter("property") != null
				&& req.getParameter("accountnumber") != null
				& req.getParameter("amount") != null) {
			String property = req.getParameter("property");
			if ("deposit".equals(property)) {
				String accountNumber = req.getParameter("accountnumber");
				double amount = Double.parseDouble(req.getParameter("amount"));
				if (accountNumber.length() != 16) {
					out.println("AccountNumber must be 16 digits");
					return;
				}
				if (req.getSession().getAttribute("username") != null) {
					try {
						if (BankAccount.deposit(accountNumber, amount))
							out.println("You have depositted " + amount);
					} catch (Exception e) {
						out.print("Error has happened,Please try later");
					}
				} else
					out.println("Session timeout. Please login");
			}

			if ("debit".equals(property)) {
				String accountNumber = req.getParameter("accountnumber");
				double amount = Double.parseDouble(req.getParameter("amount"));
				if (accountNumber.length() != 16) {
					out.println("AccountNumber must be 16 digits");
					return;
				}
				if (req.getSession().getAttribute("username") != null) {
					try {
						if (BankAccount.debit(accountNumber, amount))
							out.println("You have debitted " + amount);
						else
							out.println("Insufficient balance or System error");
					} catch (Exception e) {
						out.print("Error has happened,Please try later");
					}
				} else
					out.println("Session timeout. Please login");
			}

		} else
			out.println("Please check your parameters");

	}

	/**
	 * Delete the product entity
	 */
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String accountNumber = req.getParameter("accountnumber");
		PrintWriter out = resp.getWriter();
		String seesionName = (String) req.getSession().getAttribute("username");
		if (seesionName != null && "admin".equalsIgnoreCase(seesionName)) {
	    if(BankAccount.getSingleBankAccount(accountNumber)!=null){		
		try {
			if(BankAccount.deleteBankAccount(accountNumber))
				out.println("<p id=\"delete-account-message\">success;This account has been deleted</p>");	
			else
				out.println("<p id=\"delete-account-message\">fail;inner error</p>");
		} catch (Exception e) {
			out.println("<p id=\"delete-account-message\">fail;"+e.getMessage()+"</p>");
		}
	    }
	    else out.println("<p id=\"delete-account-message\">fail;Account not exists</p>");
		}
		else 
			out.println("<p id=\"delete-account-message\">fail;Please login as admin</p>");
	}

	protected void doQuery(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String property = req.getParameter("property");
		PrintWriter out = resp.getWriter();

		if ("balance".equalsIgnoreCase(property)) {
			if (req.getSession().getAttribute("username") != null) {
				String accountNumber = req.getParameter("accountnumber");
				// out.println(userName);
				try {
					out.println("$" + BankAccount.getBalance(accountNumber));
				} catch (Exception e) {
					out.println("We can't get your balance");
				}
			} else
				out.println("Your session is time out. Please log in again");
		}

		if ("transaction".equalsIgnoreCase(property)) {
			if (req.getParameter("accountnumber")!=null&&req.getParameter("start")!=null&&req.getParameter("end")!=null) {
				String accountNumber = req.getParameter("accountnumber");
				String start = req.getParameter("start");
				String end = req.getParameter("end");
				String actiontype = "";
				if(req.getParameter("actiontype")!=null)
					actiontype = req.getParameter("actiontype");
				// out.println(userName);
				try {
				out.println(new SimpleDateFormat("MM/dd/yyyy").parse(start).toString());
			//		out.println(end);
					Iterable<Entity> logs = TransactionLog
							.getLogsForBankAccount(accountNumber, actiontype, start, end);
					int count = 0;
					String action;
					String description;
					boolean result;
					String transactiontime;
					for (Entity log : logs) {
						action = (String) log.getProperty("action");
						description = (String) log.getProperty("description");
						result = (Boolean) log.getProperty("result");
						transactiontime = (String) log
								.getProperty("transactiontime");
						out.println("<tr><td>" 
								+accountNumber+"</td><td>"
								+ action + "</td><td>" 
								+ description+ "</td><td>" 
								+ result+"</td><td>"
								+ transactiontime+"</td></tr>");
						count++;
					}
					if (count == 0)
						out.println("<tr><td colspan='5'>No Result</td></tr>");
				} catch (Exception e) {
					out.println("<tr><td colspan='5'>"+e.getMessage()+"</td></tr>");
				}
			} else
				out.println("<tr><td colspan='5'>Please check your paramters.</td></tr>");
		}

	}

	/**
	 * Redirect the call to doDelete or doPut method
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String action = req.getParameter("action");
		if ("".equals(action) || action == null) {
			out.println("Need action paramter");
			return;
		}
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

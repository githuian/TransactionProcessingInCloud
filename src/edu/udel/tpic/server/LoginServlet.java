package edu.udel.tpic.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;

import edu.udel.tpic.server.model.Customer;
import edu.udel.tpic.server.util.JsonUtil;

public class LoginServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			   throws ServletException, IOException {		
		  String action = req.getParameter("action");
		    if (action.equalsIgnoreCase("login")) {
		      doLogIn(req, resp);
		      return;
		    } else if (action.equalsIgnoreCase("logout")) {
		      doLogOut(req, resp);
		      return;
		    }
	}
	
	protected void doLogOut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		HttpSession session = req.getSession();
		session.invalidate();
	}
	
	 protected void doLogIn(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		 resp.setContentType("text/html; charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			String userName = req.getParameter("username");
			String passWord = req.getParameter("password");
			String fullName ="";
			Entity cardHolder = Customer.getCustomer(userName);
			PrintWriter out = resp.getWriter();
			Entity loginResponse = new Entity("loginreponse");
			if(cardHolder!=null && passWord.equals(cardHolder.getProperty("passWord")))
			{
				HttpSession session = req.getSession();
				session.setAttribute("username", userName);
				session.setAttribute("firstname", cardHolder.getProperty("firstName"));
				session.setAttribute("lastname", cardHolder.getProperty("lastName"));
				fullName = cardHolder.getProperty("firstName")+" " +cardHolder.getProperty("lastName");
				if("admin".equalsIgnoreCase(userName))
				out.println("<p id=\"login\">success:admin:"+fullName+"</p>");
				else
					out.println("<p id=\"login\">success:ordinary:"+fullName+"</p>");
			}
			else{
				out.println("<p id=\"login\">fail:The username or password is not correct</p>");
			}
			out.flush();	 
	 }
	
	
	
	
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			   throws ServletException, IOException {
		doPost(req,resp);
	}
}

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Banking on the Cloud</title>
 <link href="http://www.gstatic.com/codesite/ph/17444577587916266307/css/ph_core.css" rel="stylesheet" type="text/css" />
 <link href="http://code.google.com/css/codesite.pack.04102009.css" rel="stylesheet" type="text/css" />
 <link href= "css/style.css" rel="stylesheet" type="text/css" />
 <link href= "css/jquery-ui-1.10.2.custom.min.css" rel="stylesheet" type="text/css" />
  <script language="javascript" src='script/jquery-1.8.js'></script>
 <!--  <script language="javascript" src='script/ajax.util.js'></script> -->
  <script language="javascript" src='script/ajax.tpic.js'></script>
    <script language="javascript" src='script/jquery-ui-1.10.2.custom.min.js'></script>
</head>

<%

String userName = "";
if(session.getAttribute("username") != null)
{
	userName = (String) session.getAttribute("username");
	if("admin".equalsIgnoreCase(userName)) session.invalidate();
}
%>
<body>
<input type="text" value="<%=userName%>" id="session-username" hidden name="session-username" />
  <!-- content -->  
  <div  id="gc-pagecontent" >
 <div><div> <h1 class="page_title">The Bank on the Cloud(Ordinary user)</h1></div>
 <div id ="logout" style="float:right"><a href="#" onClick="logout();">Logout</a></div></div>
 <!-- tabs --> 
 <div id="tabs" class="gtb">
      <a id="home" href="#home" class="tab">Home</a>	   
	  <a id="balance" href="#balance" class="tab controltab">Balance</a> 
	  <a id="deposit" href="#deposit" class="tab controltab">Deposit</a>	
	   <a id="debit" href="#debit" class="tab controltab">Debit</a>	  
	   <a id="transaction" href="#transaction" class="tab controltab">Transaction Log</a>	      
	  <div class="gtbc"></div>
  </div>
  
  <!-- home page content -->
  <div class="g-unit" id="home-tab">
  <div id="login-div">
  <p>Welcome to the Bank on the Cloud!</p>
  <p>Please Login!</p> 
 <form name="loginform" method="post" id="loginform">
UserName: <input name="userLogin" type="text" id="userLogin" value=""/><br>
PassWord: <input name="pswLogin" type="password" id="pswLogin" value=""/><br>
<input type="button" name="Submit" onClick="login();" value="Login"/> 
<input type="reset" name="Reset" value="Reset"/>
</form>
</div>
  <div id="loginmessage"></div>
   </div>  

  
   <!-- ******************************************* product ******************************************* -->
  <div class="g-unit" id="balance-tab">
  	<!-- balance container --> 
	  	<h2>Check Balance</h2>
	  	<form name="balance-query-form" id="balance-query-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
	  	<tbody>
	  	<tr >
		 <td>Account </td>
          <td><select class="select-list" id="balance-accounts-list" name="balance-accounts-list"></select></td>
		 </tr>
		<tr>
           <td>Balance</td>
           <td><input type="text" readonly="readonly" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="50" name="balance-amount" id="balance-amount" /></td>
         </tr>
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" name="Query" value="Query" onclick="getBalance();" />
          <input type="reset" name="Reset" value="Reset"/>
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>

  </div>
  
  
  
   <!-- ******************************************* item ******************************************* -->
  <div class="g-unit" id="deposit-tab">
	  	<h2>Deposit</h2>
	  	<form name="deposit-form" id="deposit-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
	  	<tbody>
	  	<tr >
		 <td>Account </td>
          <td><select class="select-list" id="deposit-accounts-list" name="deposit-accounts-list"></select></td>
		 </tr>
		<tr>
           <td>Amount</td>
           <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="50" name="deposit-amount" id="deposit-amount" /></td>
         </tr>
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" name="Deposit" value="Deposit" onclick="setDeposit();" />
          <input type="reset" name="Reset" value="Reset"/>
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>
  <div id="deposit-message"></div>
	  	
  </div>  
  

 
 <div class="g-unit" id="debit-tab">
     	<h2>Deposit</h2>
	  	<form name="debit-form" id="debit-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
	  	<tbody>
	  	<tr >
		 <td>Account </td>
          <td><select class="select-list" id="debit-accounts-list" name="debit-accounts-list"></select></td>
		 </tr>
		<tr>
           <td>Amount</td>
           <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="50" name="debit-amount" id="debit-amount" /></td>
         </tr>
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" name="Debit" value="Debit" onclick="setDebit();" />
          <input type="reset" name="Reset" value="Reset"/>
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>
  <div id="debit-message"></div>
  </div>  
  
   <div class="g-unit" id="transaction-tab">

	   <!-- search container -->
	   	<div class="gsc-search-box" id="log-search-form">
	   	<!-- section title -->
	   	<h2>Transaction Logs</h2>
	   	<form name="transaction-search-form" id="transaction-search-form">
	   	  <select class="select-list"  id="transaction-accounts-list" name="transaction-accounts-list"></select>
	   	  <select id="actiontype-list" name="actiontype-list">
	   	  <option value="All">All</option>
	   	  <option value="balance">Balance</option>
	   	  <option value="deposit">Deposit</option>
	   	  <option value="debit">Debit</option>	   	  
	   	  </select>
	          StartDate: <input type="text" id="startdatepicker" />
	          EndDate: <input type="text" id="enddatepicker" />
	         <input type="button" name="Query" onclick="showLogs();" value="Query" />  
          </form>  
	   	</div>
	   <!-- list container -->
	   
	   	<div class="results" style="border:0;" id="log-list">
           <table width="100%" cellspacing="0" cellpadding="2" border="0" style="border-collapse:collapse;">
             <thead>
               <tr>
                 <th scope="col">AccountNumber</th>
                 <th scope="col">Action</th>
                 <th scope="col">Description</th>
                 <th scope="col">Result</th>  
                 <th scope="col">TransactionTime</th>                     
               </tr>
             </thead>
             <tbody id="log-list-tbody"></tbody>
           </table>
	  	</div>

  </div>  
  
</div>
  
<script type="text/javascript">

<% 
if(session.getAttribute("username")!=null&&!"".equalsIgnoreCase((String)session.getAttribute("username")))
{
String	fullName = session.getAttribute("firstname") +" " + session.getAttribute("lastname");
out.println("$('div#loginmessage').html(\"Welcome,"+fullName+"\");");
	out.println("$('div#login-div').hide();");
    out.println("$('div#loginmessage').show();");
    out.println("$('.controltab').show();");
    out.println("$('#logout').show();");
}
else 
{
out.println("$('div#login-div').show();");
out.println("$('div#loginmessage').hide();");
out.println("$('.controltab').hide();");
out.println("$('#logout').hide();");
}
%>
 $(window).load(function () {
   init();
});

</script>
</body>
</html>

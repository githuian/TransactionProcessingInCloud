<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Banking on the Cloud</title>
 <link href="http://www.gstatic.com/codesite/ph/17444577587916266307/css/ph_core.css" rel="stylesheet" type="text/css" />
 <link href="http://code.google.com/css/codesite.pack.04102009.css" rel="stylesheet" type="text/css" />
 <link href= "css/style.css" rel="stylesheet" type="text/css" />
  <script language="javascript" src='script/jquery-1.8.js'></script>
 <!--  <script language="javascript" src='script/ajax.util.js'></script> -->
  <script language="javascript" src='script/ajax.tpic.js'></script>
</head>

<%
String fullName = "";
String userName = "";
if(session.getAttribute("username") != null)
{
	userName = (String) session.getAttribute("username");
    fullName = session.getAttribute("firstname") +" " + session.getAttribute("lastname");
}
%>
<body>
<input type="text" value="<%=userName%>" id="session-username" hidden name="session-username" />
  <!-- content -->  
  <div  id="gc-pagecontent" >
 <div><div> <h1 class="page_title">The Bank on the Cloud</h1></div><div id ="logout" style="float:right">
 <%
if(session.getAttribute("username") != null){
out.println("<a href='#' onClick='logout();'>Logout</a>");
}
 %></div></div>
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
  <div id="loginmessage"><b>Welcome <%=fullName%></b></div>
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
          <td><select id="accounts-list" name="account"></select></td>
		 </tr>
		<tr>
           <td>Balance</td>
           <td><span class="readonly"><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="50" name="balance" id="balance" /></span></td>
         </tr>
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" class="query" title="Query" value="Query" onclick="balance();" />
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>

  </div>
  
  
  
   <!-- ******************************************* item ******************************************* -->
  <div class="g-unit" id="deposit-tab">
    <div class ="message" id="item-show-message" style="display:none">
  </div>
	   <!-- search container -->
	   	<div class="gsc-search-box" id="item-search-ctr">
	   	<!-- section title -->
	   	<h2>All Items</h2>
	   	<form name="item-search-form" id="item-search-form">
	   	   <input type="text" name="q" id="q" class="gsc-input"/> 
	   	   <select id="by" name="item-searchby" class="gsc-input">
              <option value="name">Item</option>
              <option value="product">Product</option>
            </select> 
            <input type="button" value="Search" onclick="search('item')" class="gsc-search-button" />
            <input type="button" value="Add" onclick="add('item')" class="gsc-search-button" />
            <input type="reset" id="item-search-reset" class="cancel" title="Reset" value="Reset" style="visibility: hidden"/>
          </form>  
	   	</div>
	   <!-- list container -->
	   	<div class="results" style="border:0;" id="item-list-ctr">
           <table width="100%" cellspacing="0" cellpadding="2" border="0" style="border-collapse:collapse;">
             <thead>
               <tr>
                 <th scope="col">Item Name</th>
                 <th scope="col">Selling Price</th>
                 <th scope="col">Product</th>
                 <th scope="col">Action</th>                      
               </tr>
             </thead>
             <tbody id="item-list-tbody"></tbody>
           </table>
	  	</div>
	  	<!-- create container -->
	  	<div class="create-ctr" id="item-create-ctr">
	  	<h2>Create Item</h2>
	  	<form name="item-create-form" id="item-create-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
           <tr> 
           	 <td>Item Name</td>
             <td ><span class="readonly"><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="2048" name="name" id="name" /></span></td>
           </tr>
           <tr>
             <td>Selling Price</td>
             <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="2048" name="price" id="price" /></td>
           </tr>
           <tr>
             <td>Product</td>
             <td><select id="item-product-list" name="product"></select></td>
           </tr>
            <tr>
              <td>&nbsp;</td>
              <td> 
                  <input type="button" class="save" title="Save" value="Save" onclick="formValidate('item')" />
                  <input type="button" class="cancel" title="Cancel" value="Cancel" onclick="cancel('item')" />
                  <input type="reset" id="item-reset" class="cancel" title="Reset" value="Reset" style="visibility: hidden"/>
              </td>
            </tr>
			 </table>
			 </form>
	  	</div>
	  	<!-- create conatiner ends here -->
  </div>  
  

 
 <div class="g-unit" id="debit-tab">
    <div class ="message" id="item-show-message" style="display:none">
  </div>
	   <!-- search container -->
	   	<div class="gsc-search-box" id="item-search-ctr">
	   	<!-- section title -->
	   	<h2>All Items</h2>
	   	<form name="item-search-form" id="item-search-form">
	   	   <input type="text" name="q" id="q" class="gsc-input"/> 
	   	   <select id="by" name="item-searchby" class="gsc-input">
              <option value="name">Item</option>
              <option value="product">Product</option>
            </select> 
            <input type="button" value="Search" onclick="search('item')" class="gsc-search-button" />
            <input type="button" value="Add" onclick="add('item')" class="gsc-search-button" />
            <input type="reset" id="item-search-reset" class="cancel" title="Reset" value="Reset" style="visibility: hidden"/>
          </form>  
	   	</div>
	   <!-- list container -->
	   	<div class="results" style="border:0;" id="item-list-ctr">
           <table width="100%" cellspacing="0" cellpadding="2" border="0" style="border-collapse:collapse;">
             <thead>
               <tr>
                 <th scope="col">Item Name</th>
                 <th scope="col">Selling Price</th>
                 <th scope="col">Product</th>
                 <th scope="col">Action</th>                      
               </tr>
             </thead>
             <tbody id="item-list-tbody"></tbody>
           </table>
	  	</div>
	  	<!-- create container -->
	  	<div class="create-ctr" id="item-create-ctr">
	  	<h2>Create Item</h2>
	  	<form name="item-create-form" id="item-create-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
           <tr> 
           	 <td>Item Name</td>
             <td ><span class="readonly"><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="2048" name="name" id="name" /></span></td>
           </tr>
           <tr>
             <td>Selling Price</td>
             <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="2048" name="price" id="price" /></td>
           </tr>
           <tr>
             <td>Product</td>
             <td><select id="item-product-list" name="product"></select></td>
           </tr>
            <tr>
              <td>&nbsp;</td>
              <td> 
                  <input type="button" class="save" title="Save" value="Save" onclick="formValidate('item')" />
                  <input type="button" class="cancel" title="Cancel" value="Cancel" onclick="cancel('item')" />
                  <input type="reset" id="item-reset" class="cancel" title="Reset" value="Reset" style="visibility: hidden"/>
              </td>
            </tr>
			 </table>
			 </form>
	  	</div>
	  	<!-- create conatiner ends here -->
  </div>  
  
   <div class="g-unit" id="transaction-tab">
    <div class ="message" id="item-show-message" style="display:none">
  </div>
	   <!-- search container -->
	   	<div class="gsc-search-box" id="item-search-ctr">
	   	<!-- section title -->
	   	<h2>All Items</h2>
	   	<form name="item-search-form" id="item-search-form">
	   	   <input type="text" name="q" id="q" class="gsc-input"/> 
	   	   <select id="by" name="item-searchby" class="gsc-input">
              <option value="name">Item</option>
              <option value="product">Product</option>
            </select> 
            <input type="button" value="Search" onclick="search('item')" class="gsc-search-button" />
            <input type="button" value="Add" onclick="add('item')" class="gsc-search-button" />
            <input type="reset" id="item-search-reset" class="cancel" title="Reset" value="Reset" style="visibility: hidden"/>
          </form>  
	   	</div>
	   <!-- list container -->
	   	<div class="results" style="border:0;" id="item-list-ctr">
           <table width="100%" cellspacing="0" cellpadding="2" border="0" style="border-collapse:collapse;">
             <thead>
               <tr>
                 <th scope="col">Item Name</th>
                 <th scope="col">Selling Price</th>
                 <th scope="col">Product</th>
                 <th scope="col">Action</th>                      
               </tr>
             </thead>
             <tbody id="item-list-tbody"></tbody>
           </table>
	  	</div>
	  	<!-- create container -->
	  	<div class="create-ctr" id="item-create-ctr">
	  	<h2>Create Item</h2>
	  	<form name="item-create-form" id="item-create-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
           <tr> 
           	 <td>Item Name</td>
             <td ><span class="readonly"><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="2048" name="name" id="name" /></span></td>
           </tr>
           <tr>
             <td>Selling Price</td>
             <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="2048" name="price" id="price" /></td>
           </tr>
           <tr>
             <td>Product</td>
             <td><select id="item-product-list" name="product"></select></td>
           </tr>
            <tr>
              <td>&nbsp;</td>
              <td> 
                  <input type="button" class="save" title="Save" value="Save" onclick="formValidate('item')" />
                  <input type="button" class="cancel" title="Cancel" value="Cancel" onclick="cancel('item')" />
                  <input type="reset" id="item-reset" class="cancel" title="Reset" value="Reset" style="visibility: hidden"/>
              </td>
            </tr>
			 </table>
			 </form>
	  	</div>
	  	<!-- create conatiner ends here -->
  </div>  
  
</div>
  
<script type="text/javascript">

<% 
if(session.getAttribute("username")!=null)
{
	out.println("$('div#login-div').hide();");
    out.println("$('div#loginmessage').show();");
    out.println("$('.controltab').show();");
}
else 
{
out.println("$('div#login-div').show();");
out.println("$('div#loginmessage').hide();");
out.println("$('.controltab').hide();");
}
%>

 $(window).load(function () {
   init();
});

</script>
</body>
</html>

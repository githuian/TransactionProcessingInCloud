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
  <script language="javascript" src='script/ajax.admin.js'></script>
 <!--   <script language="javascript" src='script/jquery-ui-1.10.2.custom.min.js'></script> -->

</head>

<%
String userName = "";
if(session.getAttribute("username") != null)
{
	userName = (String) session.getAttribute("username");
	if(!"admin".equalsIgnoreCase(userName))
		session.invalidate();
    
}
%>
<body>
  <!-- content -->  
  <div  id="gc-pagecontent" >
 <div><div> <h1 class="page_title">The Bank on the Cloud (Administration user)</h1></div>
 <div id ="logout" style="float:right"><a href="#" onClick="logout();">Logout</a></div></div>
 <!-- tabs --> 
 <div id="tabs" class="gtb">
      <a id="home" href="#home" class="tab">Home</a>	   
	  <a id="createuser" href="#createuser" class="tab controltab">Create User</a> 
	  <a id="deleteuser" href="#deleteuser" class="tab controltab">Delete User</a>	
	   <a id="createaccount" href="#createaccount" class="tab controltab">Create Account</a>	  
	   <a id="deleteaccount" href="#deleteaccount" class="tab controltab">Delete Account</a>	      
	  <div class="gtbc"></div>
  </div>
  
  <!-- home page content -->
  <div class="g-unit" id="home-tab">
  <div id="login-div">
  <p>Welcome to the Administration system on the Cloud!</p>
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
  <div class="g-unit" id="createuser-tab">
  	<!-- balance container --> 
  	 <div id="createuser-form-message"></div> 
	  	<h2>Create New Customer</h2>
	  	<form name="create-user-form" id="create-user-form">
	  	<table style="width: 450px;" cellspacing="0" cellpadding="0">
	  	<tr><th colspan=2 align="center"><font size="large"></font>New Customer Form</font></th>
	  	</tr>
	  	<tbody>
	  	<tr >
		 <td class="create-user-row">UserName </td>
          <td class="create-user-items"><input type="text"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="create-username" id="create-username" /></td>
		 </tr>
		<tr>
           <td class="create-user-row">PassWord</td>
           <td class="create-user-items"><input type="password"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="first-password" id="first-password" /></td>
         </tr>
         	<tr>
           <td class="create-user-row">PassWord</td>
           <td class="create-user-items"><input type="password"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="second-password" id="second-password" /></td> 
         </tr>
         	<tr>
           <td class="create-user-row">LastName</td>
           <td class="create-user-items"><input type="text"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="firstname" id="firstname" /></td>
         </tr>
         	<tr>
           <td class="create-user-row">FirstName</td>
           <td class="create-user-items"><input type="text"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="lastname" id="lastname" /></td>
         </tr>
           	<tr>
           <td class="create-user-row">SecurityNumber</td>
           <td class="create-user-items"><input type="text"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="secnumber" id="secnumber" /></td>
         </tr>
         	<tr class="create-user-row">
           <td>Address</td>
           <td class="create-user-items"><input type="text"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="100" name="address" id="address" /></td>
         </tr>
         	<tr>
           <td class="create-user-row">PhoneNumber</td>
           <td class="create-user-items"><input type="text"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="phonenumber" id="phonenumber" /></td>
         </tr>
          	<tr>
           <td class="create-user-row">Email</td>
           <td class="create-user-items"><input type="text"  style="width: 200px;" autocomplete="off" class="gsc-input" maxlength="50" name="email" id="email" /></td>
         </tr>
         
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" name="Submit" value="Submit" onclick="createUser();" />
          <input type="reset" name="Reset" value="Reset"/>
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>
 <div id="createuser-message"></div> 
  </div>
  
  
  
   <!-- ******************************************* item ******************************************* -->
  <div class="g-unit" id="deleteuser-tab">
	  	<h2>Delete User</h2>
	  	<form name="deleteuser-form" id="deleteuser-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
	  	<tbody>
	  	<tr >
		 <td>UserName </td>
          <td><input type="text"  style="width: 150px;" autocomplete="off" class="gsc-input" maxlength="50" name="delete-username" id="delete-username" /></td>   
		 </tr>
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" name="Delete" value="Delete" onclick="deleteUser();" />
          <input type="reset" name="Reset" value="Reset"/>
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>
  <div id="deleteuser-message"></div>  	
  </div>  
  
 <div class="g-unit" id="createaccount-tab">
     	<h2>Create Account</h2>
	  	<form name="debit-form" id="debit-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
	  	<tbody>
	  	<tr >
		 <td>UserName </td>
          <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="50" name="create-account-username" id="create-account-username" /></td>
		 </tr>
		<tr>
           <td>AccountNumber</td>
           <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="50" name="create-accountnumber" id="create-accountnumber" /></td>
         </tr>
         	<tr>
           <td>InitialAccount</td>
           <td><input type="text" style="width: 185px;" autocomplete="off" class="gsc-input" maxlength="50" name="create-initialamount" id="create-initialamount" /></td>
         </tr>
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" name="Create" value="Create" onclick="createAccount();" />
          <input type="reset" name="Reset" value="Reset"/>
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>
  <div id="createaccount-message"></div>
  </div>  
  
   <div class="g-unit" id="deleteaccount-tab">

	   <!-- search container -->
	   	<div class="gsc-search-box" id="log-search-form">
	   	<!-- section title -->
	   	<h2>Delete Account</h2>
	   	<form name="deposit-form" id="deposit-form">
	  	<table width="200" cellspacing="0" cellpadding="0">
	  	<tbody>
	  	<tr >
		 <td>UserName</td>
          <td><input type="text"  style="width: 150px;" autocomplete="off"  onkeyup="getAccounts();" class="gsc-input" maxlength="50" name="deleteaccount-username" id="deleteaccount-username" /></td>   
		 </tr>
	  	<tr >
		 <td>AccountNumber </td>
          <td><select class="select-list" id="delete-accountnumber" name="delete-accountnumber"></select></td>   
		 </tr>
        <tr>
          <td>&nbsp;</td>
          <td> 
          <input type="button" name="Delete" value="Delete" onclick="deleteAccount();" />
          <input type="reset" name="Reset" value="Reset"/>
         </td>
        </tr>
	  	</tbody>
  </table>
  </form>
    <div id="deleteaccount-message"></div>
	   	</div>
  </div>  
  
</div>
  
<script type="text/javascript">

<% 
if(session.getAttribute("username")!=null&&!"".equals((String) session.getAttribute("username")))
{
	String	fullName = session.getAttribute("firstname") +" " + session.getAttribute("lastname");
	out.println("$('div#login-div').hide();");
	out.println("$('div#loginmessage').html(\"Welcome,"+fullName+"\");");
    out.println("$('div#loginmessage').show();");
    out.println("$('.controltab').show();");
    out.println("$('.controltab').show();");
    out.println("$('#logout').show();");
}
else 
{
out.println("$('div#login-div').show();");
out.println("$('div#loginmessage').hide();");
out.println("$('div#loginmessage').html('');");
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

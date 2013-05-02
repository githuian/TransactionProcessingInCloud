var HOME='home';
var CREATEUSER ='createuser';
var DELETEUSER ='deleteuser';
var CREATEACCOUNT = 'createaccount';
var DELETEACCOUNT = 'deleteaccount';

	
var init = function() {
	//showing the home tab on initializing
	showTab(HOME);
	//adding event listeners to the tabs
	$('#tabs a').click(function(event) {
		showTab(event.currentTarget.id);
	});
};

var param=function(name,value){
	this.name=name;
	this.value=value;
};

var showTab = function(entity) {
	//remove the active class from all the tabs
	$('.tab').removeClass("active");
	//setting the active class to the selected tab
	$('#'+entity).addClass("active");
	//hiding all the tabs
	$('.g-unit').hide();
	//showing the selected tab
	$('#' + entity + '-tab').show();

	if(entity!=HOME)
		$('#'+entity+'-search-reset').click();
	if(entity==CREATEUSER){

	}
	if(entity==DELETEUSER){
//		$('#deposit-amount').val('');
//		getAccounts();
	}
	if(entity==CREATEACCOUNT){
//		$('#debit-amount').val('');
//		$('#debit-message').html('');
	//	alert("test");
	//	getAccounts();
	}
	if(entity==DELETEACCOUNT){
//		$('#debit-amount').val('');
	//	alert("test");
//		getAccounts();
	}
	};	

var login= function()
{	
	var parameter=new Array();
    var username =$('#userLogin').val();
    var password =$('#pswLogin').val();
	parameter[parameter.length]=new param('username',username);
	parameter[parameter.length]=new param('password',password);
	parameter[parameter.length]=new param('action','login');
	$.ajax({
		url : "/login",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
			 var $response=$(data);
		//	 alert($response.filter('#login').text());
			 var repdata = $response.filter('#login-message').text().split(";");
		 
	  if(repdata[0]=="success"){
	//	  alert($response.filter('#login').text());
	       if(repdata[1]=="admin"){
	       	$('div#loginmessage').html("<p><b>Welcome,"+repdata[2]+"</b></p>");
	       	$('div#loginmessage').show();
			$('a.controltab').show();
			$('#logout').show();
			$('div#login-div').hide();
			$('#userLogin').val('');
			$('#pswLogin').val('');
	       }
	          else 
	      {  	
	        	  alert("You are not admin user");
	        	  $('#userLogin').val('');
	  			  $('#pswLogin').val('');
	      
	      }
			}
			else {			
				$('div#loginmessage').html("<p>"+repdata[1]+"</p>");		
				$('#userLogin').val('');
				$('#pswLogin').val('');
			}
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {		
		},
	}).done(function(html){	
	});		
};

var logout = function()
{
	var parameter=new Array();
	parameter[parameter.length]=new param('action','logout');
	$.ajax({
		url : "/login",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
			$('.controltab').hide();
			showTab(HOME);	
			$('div#login-div').show();
			$('#logout').hide();
			$('div#loginmessage').hide();
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {	
		},
	}).done(function(html){
	});	
};

var createUser = function()
{
	var firstpassword = $('#first-password').val();
	var secondpassword = $('#second-password').val();
	if(firstpassword != secondpassword)
   {
      $('#createuser-form-message').html("<font color='red'>Two passwords are not the same.</font>");
	  return false;
	}
	var username = $('#create-username').val();
	var password = $('#first-password').val();
	var lastname = $('#lastname').val();
	var firstname = $('#firstname').val();
	var address = $('#address').val();
	var secnumber = $('#secnumber').val();
	var email = $('#email').val();
	var phonenumber = $('#phonenumber').val();
	var parameter=new Array();	
	
	parameter[parameter.length]=new param('username',username);
	parameter[parameter.length]=new param('password',password);
	parameter[parameter.length]=new param('lastname',lastname);
	parameter[parameter.length]=new param('firstname',firstname);
	parameter[parameter.length]=new param('address',address);
	parameter[parameter.length]=new param('secnumber',secnumber);
	parameter[parameter.length]=new param('email',email);
	parameter[parameter.length]=new param('phonenumber',phonenumber);	
	parameter[parameter.length]=new param('action','create');
	$.ajax({
		url : "/customer",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
			 var $response=$(data);
			 var repdata = $response.filter('#create-user-message').text().split(";");
			 if(repdata[0]=="success"){
				 $('#createuser-message').html("<font color='red'>Succeed!</font>");
				 $('#create-username').val('');
				$('#first-password').val('');
				 $('#lastname').val('');
				 $('#firstname').val('');
				 $('#address').val('');
				$('#secnumber').val('');
				 $('#email').val('');
				$('#phonenumber').val('');
	
			 }
			 else 
				 {
				 $('#createuser-message').html("<font color='red'>fail,"+repdata[1]+"</font>"); 
				 }
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {	
		},
	}).done(function(html){	
	});	
};

/*
var queryUser = function(username)
{
	
	var parameter=new Array();
	parameter[parameter.length]=new param('action','query');
	parameter[parameter.length]=new param('property','queryuser');
	parameter[parameter.length]=new param('username',username);
	alert(username);
	$.ajax({
		url : "/customer",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
			 var $response=$(data);
			 var repdata = $response.filter('#queryuser-message').text().split(";");
			 if(repdata[0]=="success"){
				$('#deleteuser-message').html(repdata[1]);
				 return true;
			 }
			 else 
			{
				 $('#deleteuser-message').html(repdata[1]);
				 return false;
			}	 
		},
		error:function(req, status, error){
			alert("error")
			return false;
		},
		complete:function(data) {	
		},
	}).done(function(html){
	});	
};
*/



var deleteUser = function()
{
	var username = $('#delete-username').val();
	var parameter=new Array();
	parameter[parameter.length]=new param('action','delete');
	parameter[parameter.length]=new param('username',username);
	$.ajax({
		url : "/customer",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
			 var $response=$(data);
			 var repdata = $response.filter('#delete-user-message').text().split(";");
			 if(repdata[0]=="success"){
				$('#deleteuser-message').html(repdata[1]);
			 }
			 else 
			{
				 $('#deleteuser-message').html(repdata[1]);
			}	 
		},
		error:function(req, status, error){
			$('#deleteuser-message').html("System error,Please try later");
		},
		complete:function(data) {	
		},
	}).done(function(html){
	});	
};

var createAccount = function()
{
	var username = $('#create-account-username').val();
	var accountnumber = $('#create-accountnumber').val().replace(/[^0-9]/g,'');
	var initamount = $('#create-initialamount').val();
	if(accountnumber.length!='16'){
		$('#createaccount-message').html("Account number must be 16 digits");
		$('#create-accountnumber').focus();
		return;
	}
	var parameter=new Array();
	parameter[parameter.length]=new param('action','create');
	parameter[parameter.length]=new param('username',username);
	parameter[parameter.length]=new param('accountnumber',accountnumber);
	parameter[parameter.length]=new param('initamount',initamount);
	$.ajax({
		url : "/bankaccount",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
			 var $response=$(data);
			 var repdata = $response.filter('#create-account-message').text().split(";");
			 if(repdata[0]=="success"){
				$('#createaccount-message').html(repdata[1]);
			 }
			 else 
			{
				 $('#createaccount-message').html(repdata[1]);
			}	 
		},
		error:function(req, status, error){
			$('#createaccount-message').html("System error,Please try later");
		},
		complete:function(data) {	
		},
	}).done(function(html){
	});	
};


var getAccounts = function()
{
	var username = $('#deleteaccount-username').val();
	var parameter=new Array();
	parameter[parameter.length]=new param('property','accountnumbers');
	parameter[parameter.length]=new param('username',username);
	parameter[parameter.length]=new param('action','query');
	$.ajax({
		url : "/customer",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
		$('.select-list').html(data);
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {	
		},
	}).done(function(html){	
	});	
};


var deleteAccount = function()
{
	var accountnumber = $('#delete-accountnumber').val().replace(/[^0-9]/g,'');
	if(accountnumber.length!='16'){
		$('#deleteaccount-message').html("Account number must be 16 digits");
		$('#delete-accountnumber').focus();
		return;
	}
	var parameter=new Array();
	parameter[parameter.length]=new param('action','delete');
	parameter[parameter.length]=new param('accountnumber',accountnumber);
	$.ajax({
		url : "/bankaccount",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
			 var $response=$(data);
			 var repdata = $response.filter('#delete-account-message').text().split(";");
			 if(repdata[0]=="success"){
				$('#deleteaccount-message').html(repdata[1]);
				getAccounts();
			 }
			 else 
			{
				 $('#deleteaccount-message').html(repdata[1]);
			}	 
		},
		error:function(req, status, error){
			$('#deleteaccount-message').html("System error,Please try later");
		},
		complete:function(data) {	
		},
	}).done(function(html){
	});	
};










var HOME='home';
var BALANCE ='balance';
var DEPOSIT ='deposit';
var DEBIT = 'debit';
var TRANSACTION = 'transaction';

$(function() {
    $("#startdatepicker" ).datepicker();
  });

$(function() {
    $("#enddatepicker" ).datepicker();
  });

	
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
//	alert(entity);
	//hiding the create block
//	showHideCreate(entity, false);
	if(entity!=HOME)
		$('#'+entity+'-search-reset').click();
	if(entity==BALANCE){
		$('#balance-amount').val('');
		getAccounts();
	}
	if(entity==DEPOSIT){
		$('#deposit-amount').val('');
		getAccounts();
	}
	if(entity==DEBIT){
		$('#debit-amount').val('');
		$('#debit-message').html('');
	//	alert("test");
		getAccounts();
	}
	if(entity==TRANSACTION){
		$('#debit-amount').val('');
	//	alert("test");
		getAccounts();
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
			 var repdata = $response.filter('#login-message').text().split(";");
			if(repdata[0]=="success"){
			$('div#loginmessage').show();
		 	$('div#loginmessage').html("<p><b>Welcome,"+repdata[2]+"</b></p>");
			$('a.controltab').show();
			$('div#login-div').hide();
			$('#userLogin').val('');
			$('#logout').show();
			$('#pswLogin').val('');
			$('#session-username').val(username);
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

var getAccounts = function()
{
	var parameter=new Array();
	parameter[parameter.length]=new param('property','accountnumbers');
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


var getBalance = function()
{	
	var accountnumber =$('#balance-accounts-list').val();
	var parameter=new Array();
//var username =$('#session-username').val();
//	if(username==""){
	//	alert("Timeout,Please login again");
//		showTab(HOME);
//		return;
//	}
	parameter[parameter.length]=new param('accountnumber',accountnumber);
	parameter[parameter.length]=new param('property','balance');
	parameter[parameter.length]=new param('action','query');
	$.ajax({
		url : "/bankaccount",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
		$('#balance-amount').val(data);
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {	
		},
	}).done(function(html){	
	});	
};

var setDeposit = function()
{	
	var accountnumber = $('#deposit-accounts-list').val();
	var depositamount = $('#deposit-amount').val();
	var parameter=new Array();
	parameter[parameter.length]=new param('accountnumber',accountnumber);
	parameter[parameter.length]=new param('property','deposit');
	parameter[parameter.length]=new param('amount',depositamount);
	parameter[parameter.length]=new param('action','update');
	$.ajax({
		url : "/bankaccount",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
		$('#deposit-message').html(data);
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {	
		},
	}).done(function(html){	
	});	
};

var setDebit = function()
{	
	var accountnumber = $('#debit-accounts-list').val();
	var debitamount = $('#debit-amount').val();
	var parameter=new Array();
	var username =$('#session-username').val();
	parameter[parameter.length]=new param('accountnumber',accountnumber);
	parameter[parameter.length]=new param('property','debit');
	parameter[parameter.length]=new param('amount',debitamount);
	parameter[parameter.length]=new param('action','update');
	$.ajax({
		url : "/bankaccount",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
		$('#debit-message').html(data);
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {	
		},
	}).done(function(html){	
	});	
};

var showLogs = function()
{	
	var accountnumber = $('#transaction-accounts-list').val();
	var actiontype = $('#actiontype-list').val();
	var start = $('#startdatepicker').val();
	var end = $('#enddatepicker').val();
	var parameter=new Array();
	parameter[parameter.length]=new param('accountnumber',accountnumber);
	parameter[parameter.length]=new param('property','transaction');
	parameter[parameter.length]=new param('actiontype',actiontype);
	parameter[parameter.length]=new param('start',start);
	parameter[parameter.length]=new param('end',end);
	parameter[parameter.length]=new param('action','query');
	$.ajax({
		url : "/bankaccount",
		type : "post",
		dataType: "html",
		data:parameter,
		beforeSend : function() {
		},
		success:function(data) {
		$('#log-list-tbody').html(data);
		},
		error:function(req, status, error){
			alert("Error,Please try later ");
		},
		complete:function(data) {	
		},
	}).done(function(html){	
	});	
};









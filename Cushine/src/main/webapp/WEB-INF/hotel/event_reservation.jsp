<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="tag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>

<title>Event Reservation</title>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
 <meta name="viewport" content="width=700">				
		<!-- media-queries.js (fallback) -->
		<!--[if lt IE 9]>
			<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>			
		<![endif]-->
		<!-- html5.js -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link href="css/eStyle.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="css/non-responsive.css">
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<!-- Google Map API Key Source -->
<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
<!-- Google Map  Source -->
<script type="text/javascript" src="js/gmaps.js"></script>
<script type="text/javascript" src="js/customjs.js"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="js/jquery.mCustomScrollbar.min.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="css/datepicker.css">
<style type="text/css">
.loggedin {
	color: rgb(0, 150, 250) !important;
	font-weight: bold;
}
</style>



<script type="text/javascript">



function getUser() {
$.ajax({

	type: "GET",
    url: "getEventUserForEdit.htm",
 
    success: function(data) {var response=jQuery.parseJSON(data); 
	var html='';
	
	   $(response).each(function(i,res) {
		
		html+='<div class="form-group">';
			
		html+='<label for="inputEmail3" class="col-md-2 control-label">UserName</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="userName" value="'+res.userName+'" class="form-control" id="userName"  readonly="true" required="true">';
		html+='</div>';
     html+='</div>';
     html+='<input type="hidden" name="userId" value="'+res.userId+'" >';
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Password</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="password" name="password" value="'+res.password+'" class="form-control" id="mypassword" >';
		html+='</div>';
     html+='</div>';	
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Confirm Password</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="password" name="confirmPassWord" value="'+res.confirmPassWord+'" class="form-control" id="confirmPassWord"  required="true">';
		html+='</div>';
     html+='</div>';	
      html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Title</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<select name="title" class="form-control"   >';
	
		if(res.title === 'Mr'){
			html+='<option value="'+res.title+'"  selected="selected">'+res.title+'</option>';
		}else{
			html+='<option value="'+res.title+'">Mr</option>';
		}
		
		if(res.title === 'Miss'){
			html+='<option value="'+res.title+'"  selected="selected">'+res.title+'</option>';
		}else{
			html+='<option value="Miss">Miss</option>';
		}
		
		if(res.title === 'Mrs'){
			html+='<option value="'+res.title+'"  selected="selected">'+res.title+'</option>';
		}else{
			html+='<option value="Mrs">Mrs</option>';
		}
	
		
		html+='</select>';
		html+='</div>';
     html+='</div>';	
    
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">First Name</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="firstName" value="'+res.firstName+'" class="form-control" id="inputEmail3"" >';
		html+='</div>';
     html+='</div>';	
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Last Name</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="lastName" value="'+res.lastName+'" class="form-control" id="inputEmail3">';
		html+='</div>';
     html+='</div>';	
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Street</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="streetName" value="'+res.streetName+'" class="form-control" id="inputEmail3"  >';
		html+='</div>';
     html+='</div>';	
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Pincode</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="postalCode" value="'+res.postalCode+'" class="form-control" id="inputEmail3">';
		html+='</div>';
     html+='</div>';	
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">City</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="city" value="'+res.city+'" class="form-control" id="inputEmail3"  >';
		html+='</div>';
     html+='</div>';	
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Country</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<select name="country" class="form-control"  >';
		
		if(  res.country === 'USA'){
			html+='<option value="'+res.country+'" selected="selected">'+res.country+'</option>';
		}else{
			html+='<option value="USA">USA</option>';
		}
		
		if(  res.country === 'Germany'){
			html+='<option value="'+res.country+'" selected="selected">'+res.country+'</option>';
		}else{
			html+='<option value="Germany">Germany</option>';
		}
		
		if( res.country === 'UK'){
			html+='<option value="'+res.country+'" selected="selected">'+res.country+'</option>';
		}else{
			html+='<option value="UK">UK</option>';
		}
		
		if( res.country  === 'France'){
			html+='<option value="'+res.country+'" selected="selected">'+res.country+'</option>';
		}else{
			html+='<option value="France">France</option>';
		}
		
		if( res.country === 'Spain' ){
			html+='<option value="'+res.country+'" selected="selected">'+res.country+'</option>';
		}else{
			html+='<option value="Spain">Spain</option>';
		}
		
		if( res.country === 'Others'){
			html+='<option value="'+res.country+'" selected="selected">'+res.country+'</option>';
		}else{
			html+='<option value="Others">Others</option>';
		}
		
		html+='</select>';
		html+='</div>';
     html+='</div>';	
		html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Date Of Birth</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="dob" value="'+res.dob+'" class="form-control" id="mydatePickerVal" onclick="getDatepick()" >';
		html+='</div>';
     html+='</div>';	
		html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Email</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="email" value="'+res.email+'" class="form-control" id="inputEmail3" readonly="true">';
		html+='</div>';
     html+='</div>';	 
			html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Phone</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="phoneNumber" value="'+res.phoneNumber+'" class="form-control" id="inputEmail3" >';
		html+='</div>';
     html+='</div>';	 
		html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Language</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<select name="language" class="form-control" id="inputEmail3"  >';
			
		if('English' === res.language){
			html+='<option value="'+res.language+'" selected="selected">'+res.language+'</option>';
		}else{
			html+='<option value="English">English</option>';
		}
		if('Germany' === res.language){
			html+='<option value="'+res.language+'" selected="selected">'+res.language+'</option>';
		}else{
			html+='<option value="Germany">Germany</option>';				
		}
		if('Deutsche' === res.language){
			html+='<option value="'+res.language+'" selected="selected">'+res.language+'</option>';
		}else{
			html+='<option value="Deutsche">Deutsche</option>';				
		}
		
		
		html+='</select>';
		
		html+='</div>';
     html+='</div>';	
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Join Date</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<input type="text" name="dateJoin" value="'+res.dateJoin+'" class="form-control" id="inputEmail3" readonly="true" >';
		html+='</div>';
     html+='</div>';	 
     html+='<div class="form-group">';
		
		html+='<label for="inputEmail3" class="col-md-2 control-label">Notification Duration</label>';
		html+='<div class="col-sm-9 col-xs-12">';
		html+='<select name="notificationDuration" class="form-control" id="inputEmail3"  >';
		
		if(res.notificationDuration === '24 Hours'){
			html += '<option value="'+res.notificationDuration+'" selected="selected">'+res.notificationDuration+'</option>';
		}else{
			html+='<option value="24 Hours">24 Hours</option>';
		}
		if(res.notificationDuration === '48 Hours'){
			html += '<option value="'+res.notificationDuration+'" selected="selected">'+res.notificationDuration+'</option>';
		}else{
			html+='<option value="48 Hours">48 Hours</option>';
		}
		if(res.notificationDuration === '72 Hours'){
			html += '<option value="'+res.notificationDuration+'" selected="selected">'+res.notificationDuration+'</option>';
		}else{
			html+='<option value="72 Hours">72 Hours</option>';
		} 
		
		
		html+='</select>';
		
		html+='</div>';
     html+='</div>';	
			
		
	});
	  
	
	 $('#myProfileFromAjax').html(html);
	$('#myprofile').show();
	 document.getElementById('fade').style.display='block';
    }
});

}
function getForm(){
    
	return false;
  }
function getAdminRegisterForm(){
	 return false;
}

function closeAdminLogPage(){  
    	$('#emailOrUsernme').val('');
   	    $('#adminPwd').val('');
   		$('#myadminlogin').hide();
   		document.getElementById('fade').style.display = 'none';                   	
    }
  function getRegisterForm(){
	    
  	return false;
    }
  
  function getMyProfileForm(){
	    
  	return false;
    }
  function getForgotPassForm(){
	  return false;  
  }
function getDatepick(){
$('#mydatePickerVal').datepicker({
	autoclose : true,
	format : "dd/mm/yyyy"
});
$('#mydatePickerVal').datepicker('show');
}
	
	
	
	$(document).ready(function() {
		$('#my_adminregistration').hide();
		$('#datepick').datepicker({
			autoclose : true,
			format : "dd/mm/yyyy"
		});

		$('#datepicker').datepicker({
			autoclose : true,
			format : "dd/mm/yyyy"
		});
		/* $("select#pickHotel").change(function() {
			var pickHotel = $('#pickHotel').val();
			document.location.href = "pickHotel.htm?name=" + pickHotel;
		}); */
		var invalidLogin = $("#invalidLogin").val();
		//alert(invalidLogin);
		if (invalidLogin != '') {
			$('#mylogin').show();
			document.getElementById('fade').style.display = 'block';

		}

		var invalidServLogin = $("#invalidServLogin").val();
		//alert(invalidServLogin);
		if (invalidServLogin != '') {
			$('#emailOrUsernme').val('');
			$('#adminPwd').val('');
			$('#myadminlogin').show();
			document.getElementById('fade').style.display = 'block';

		}
		var invalidForgot = $("#invalidForgot").val();
		//alert(invalidForgot);
		if (invalidForgot != '') {
			$('#myforgot').show();

		}
		var adminerrors = $('#adminerrors').val();
		if (adminerrors != '') {
			$('#my_adminregistration').show();
			$("ul li").removeClass("selected");

		}
		var adminexistpopup = $("#adminexistpopup").val();
		if (adminexistpopup != '') {
			$('#my_adminregistration').show();
			$("ul li").removeClass("selected");
		}

		var admailexistpopup = $("#admailexistpopup").val();
		if (admailexistpopup != '') {
			$('#my_adminregistration').show();
			$("ul li").removeClass("selected");
		}

		var loggedin = $("#loggedin").val();

		if (loggedin != '') {
			$('#blackuser').hide();
			$('#greenuser').show();
		} else {
			$('#blackuser').show();
			$('#greenuser').hide();
		}

	});// document.ready function close.
</script>
<script type="text/javascript">
	$(document).ready(function() {

		var mode = $("#mode").val();
		//alert(mode);
		if (mode == 'edit') {
			$('#myprofile').show();
			//$('#my_registration').show();
		}

		var headerpopup = $('#headerpopup').val();
		if (headerpopup != '') {
			$('#my_registration').show();
			var loginpopup = $("#loginpopup").val();
			if (loginpopup == 'Please enter the Login details') {
				$('#mylogin').show();
				$("ul li").removeClass("selected");

			}

		}

		/* var invalidLogin = $("#invalidLogin").val();
		if (invalidLogin != '') {
			$('#mylogin').show();
			$("ul li").removeClass("selected");

		} */

		var signUp = $("#signUp").val();
		//alert("signup"+signUp);
		if (signUp != '') {
			$("#alertMsgPopUp").show();
			$("#alertPopSpanId").empty();
			$("#alertPopSpanIdGreen").append(signUp);

		}

		var userexistpopup = $("#userexistpopup").val();
		if (userexistpopup != '') {
			$('#my_registration').show();
			$("ul li").removeClass("selected");
		}

		var emailexistpopup = $("#emailexistpopup").val();
		if (emailexistpopup != '') {
			$('#my_registration').show();
			$("ul li").removeClass("selected");
		}

		var pemailexistpopup = $("#pemailexistpopup").val();
		if (pemailexistpopup != '') {
			$('#myprofile').show();
			$("ul li").removeClass("selected");
		}

		var mailsent = $('#mailsent').val();
		//alert("mailsent"+mailsent);
		if (mailsent == 'User Activation Link  Is Sent To Your Mail') {
			$("#alertMsgPopUp").show();
			$("#alertPopSpanId").empty();
			$("#alertPopSpanIdGreen").append(mailsent);
		}
		var accountinactivepop = $("#accountinactivepop").val();
		//alert(accountinactivepop);
		if (accountinactivepop != '') {

			document.getElementById('fade').style.display = 'block';
			$("#alertLogPopSpanId").empty();
			$("#alertLogPopSpanIdRed").append(accountinactivepop);
			$("#alertLogPopUp").show();
			$('#mylogin').show();
		}
		var updateUser = $("#updateUser").val();
		//alert(updateUser);
		if (updateUser != '') {
			$('#myprofile').show();
			$('ul li').removeClass("selected");
			var hideMessage = function() {
				$('#showUpdate').hide();
			};
			setTimeout(hideMessage, 2000);
		}
	});
	function getServiceProviderLogin() {
		$('#emailOrUsernme').val('');
		$('#adminPwd').val('');
		$('#myadminlogin').show();
		document.getElementById('fade').style.display = 'block';
	}
	function hidepwd() {
		$("#pwd").hide();
	}

	function hidename() {
		$("#firstName").hide();
	}

	function hideemailId() {
		$("#email").hide();
	}

	function hidelastname() {
		$("#lastName").hide();
	}

	function hidestreetname() {
		$("#streetName").hide();
	}
	function hideusername() {
		$("#userName").hide();
	}

	function hidelanguages() {
		$("#languages").hide();
	}

	function hidetitle() {
		$("#titles").hide();
	}
	function hideConfirmpassword() {
		$("#confirmpassword").hide();
	}

	function hidecontactnumber() {
		$("#contactNumber").hide();
	}

	function hidedateofbirth() {
		$("#dateOfBirth").hide();
	}
	function hidecity() {
		$("#city").hide();
	}

	function hidecountry() {
		$("#country").hide();
	}

	function hidenotify() {
		$("#notifyduration").hide();
	}

	function hidepostalcode() {
		$("#postalcode").hide();
	}

	function forgotPassWord() {
		//alert("coming");
		$('#mylogin').hide();
		$('#myadminlogin').hide();
		$('#inputEmail3').val('');
		$('#forgorStatusMsg').text('');
		$("#invalidForgot").val('');
		$('#myforgot').show();
	}
	function getEventDetails() {
		$
				.ajax({
					url : "getEventContactDetail.htm",
					type : "GET",
					cache : false,

					success : function(response) {
						alert(response);

						var brands = $.parseJSON(response);

						var htmlval = '';
						$(brands)
								.each(
										function(i, client) {

											htmlval += '<tr>'
													+ '<td><strong >Restaurant:</strong>&nbsp;&nbsp;</td>'
													+ '<td><strong>'
													+ client.eventOrgName
													+ '</strong></td>'
													+ '</tr>';
											htmlval += '<tr>';
											htmlval += '<td>Address</td>'
													+ '<td>'
													+ client.eventOrgAddress
													+ '</td>' + '</tr>';
											htmlval += '<tr>';
											htmlval += '<td>PostalCode</td>'
													+ '<td>'
													+ client.eventOrgPostalCode
													+ '</td>' + '</tr>';
											htmlval += '<tr>';
											htmlval += '<td>Phone</td>'
													+ '<td>'
													+ client.eventOrgPhoneNumber
													+ '</td>' + '</tr>';
											htmlval += '<tr>';
											htmlval += '<td>Email</td>'
													+ '<td>'
													+ client.eventOrgEmail
													+ '</td>' + '</tr>';
											htmlval += '<tr>';
											htmlval += '<td>Website</td>'
													+ '<td>'
													+ client.eventOrgWebiste
													+ '</td>' + '</tr>';
											//htmlval+='<iframe src=https://www.google.com/maps/embed/v1/place?key=AIzaSyCqOFwiSrw2ZtqU-523kQ2ftKHpi2tSDCc&q='+client.eventOrgName,client.eventOrgAddress client.eventOrgPostalCode client.eventOrgPostalCode+'></iframe>';
											/* htmlval+='<iframe ';
											htmlval+='src=https://www.google.com/maps/embed/v1/place?key=AIzaSyCqOFwiSrw2ZtqU-523kQ2ftKHpi2tSDCc&q=';
											htmlval+=client.eventOrgName,client.eventOrgAddress client.eventOrgPostalCode client.eventOrgPostalCode;
											htmlval+='width="120%" height="230" frameborder="0"';
											htmlval+='style="border: 0; margin: 10px 0px 0px;">';
											htmlval+='</iframe>'; */
										});
						$('#addressdetails').html(htmlval);
						$('#hotel_details').show();

					}

				});

	}
</script>

<!-- myreservation popup starts -->
<script type="text/javascript">

function downloadLocationInfo() {
	alert('download pdf');
	document.location.href = "downloadLocationInfoPDF.htm";
}

function getMyReservations() {
	 //alert('response first::');
	   $.ajax({
			url : "getMyEventUserReservations.htm",
			type : 'POST',
			success : function(data) {
				//alert('response second ::'+data);
				var response = jQuery.parseJSON(data);
				
				var html = '';
				$('#reservationHistory').empty();
				html += '<div class="reservation-data-section">';
				html += '<div class="col-xs-5 col-md-5">';
				html += '<strong>User Details</strong>';
				html += '</div>';
				html += '<div class="col-xs-5 col-md-5">';
				html += '<strong>Reservation Details</strong>';
				html += '</div>';
				html += '<div class="col-xs-2 col-md-2">';
				html += '</div>';
				html += '</div>';
				
				for (i in response) {
					//alert('response third::'+response[i].status);
					html += '<div class="reservation-data-section">';
					html += '<div class="col-xs-5 col-md-5">';
					html += '<p>';
					html += '<strong>'+response[i].userName+'</strong><br />';
					html += response[i].email +'<br />';
					html += 'Ph : '+response[i].eventOrgPhoneNumber + '<br />';
					html += '</p>';
					html += '</div>';
					html += '<div class="col-xs-5 col-md-5">';
					html += '<p>';
					html += '<strong>EventOrganisationName:</strong> ' +response[i].eventOrgName + '<br / >'; 
					html += 'Reference No : ' + response[i].referenceNumber + '<br / >';
					html += 'Start Time : '+response[i].checkInTime+'<br / >';
					html += 'End Time : '+response[i].checkOutTime+'<br />';
					html += 'Period : '+response[i].numberOfDays+'<br / >';
					html += '</p>';
					html += '</div>';
					/* html += '<div class="col-xs-2 col-md-2">';
					html += '</div>';
					html += '</div>'; */
					if(response[i].status == 'active' && response[i].arrived == false){
						//alert('response third::+active false');
						 html += '<div class="col-xs-2 col-md-2">';
    					 html += '<button class="btn btn-default btn-save bookingID" id="cancelBtn" type="button">';
    					 html += '<input type="hidden" id = "bookingId" value="'+response[i].reserveId+'"/>';
    					 html += 'Cancel';
    					 html += '</button>';
    					 
    					 html += '<button class="btn btn-default btn-success btn-change " type="button" id="changeBtn" onclick= "changeEventReservtn('+response[i].reserveId+','+response[i].referenceNumber+')">';
    					 html += '<input type="hidden"  value="'+response[i].reserveId+'"/>';
    					 html += 'Change</button>';
    					 html += '</div>';
					 }else if( response[i].status == 'active' && response[i].arrived == true) {
						//alert('response third:: active true');
						 html += '<div class="col-xs-2 col-md-2">';
						 html += '<div class="flag_icon"><span class="glyphicon glyphicon-ok green" aria-hidden="true"></span></div>';
						 html += '<div class="flag_icon">';
						 html += '<button class="btn btn-default btn-icon" type="button">';
						 html += '<span class="glyphicon glyphicon-trash arrivedResrvtn" aria-hidden="true">';
						 html += '<input type="hidden" id="deleteBtn" value="'+response[i].reserveId+'"/>';
						 html += '</span>';
						 html += '</button>';
						 html += '</div>';
						 html += '</div>';
					 }else if(response[i].status == 'cancel'){
						//alert('response third:: cancel');
						 html += '<div class="col-xs-2 col-xs-2">';
						 html += '<div class="flag_icon"><span class="red">Cancelled</span>';
						 html += '</div>';
						 
						 html += '<div class="flag_icon">';
						 html += '<button class="btn btn-default btn-icon" type="button">';
						 html += '<span class="glyphicon glyphicon-trash notVisitedReservtn" aria-hidden="true">';
						 html += '<input type="hidden" id="refNo" value="'+response[i].referenceNumber+'"/>';
						 html += '<input type="hidden" id="resvId" value="'+response[i].reserveId+'"/>';
						 html += '</span>';
						 html += '</button>';
						 html += '</div>';
						 html += '</div>';
					 }else if ( response[i].status == 'not visited'){
						//alert('response third:: not visited');
						 html += '<div class="col-xs-2 col-md-2">';
						 html += '<div class="flag_icon"><span class="glyphicon glyphicon-minus red" aria-hidden="true"></span></div>';
						 html += '<div class="flag_icon">';
						 html += '<button class="btn btn-default btn-icon" type="button">';
						 html += '<span class="glyphicon glyphicon-trash notVisitedReservtn" aria-hidden="true">';
						 html += '<input type="hidden" id="refNo" value="'+response[i].referenceNumber+'"/>';
						 html += '<input type="hidden" id="resvId" value="'+response[i].reserveId+'"/>';
						 html += '</span>';
						 html += '</button>';
						 html += '</div>';
						 html += '</div>';
					 }
					html += '</div>';
				}
				//alert('hotelinfo');
				$.ajax({
       			url   : 'bookingEventUserHistory.htm', 
       			type  : 'POST',
       			success : function(data){
       				//alert('hotelinfo data' +data);
       				 var response  = jQuery.parseJSON(data);
       				 for(i in response){
       					    
	       					 html += '<div class="reservation-data-section">';
	       				     html += '<div class="col-xs-5 col-md-5">';
	      				     html += '<p>';
	      					 html += '<strong>'+response[i].userName+'</strong><br />';
							 html += response[i].email +'<br />';
							 html += 'Ph : '+response[i].phoneNumber + '<br />';
	      					 html += '<br />';
          					 html += '</p>';
          					 html += '</div>';
          					 html += '<div class="col-xs-5 col-md-5">';
          					 html += '<p>';
          					 html += '<strong>HotelName</strong> : ' +response[i].hotelName + '<br / >';
          					 html += 'Reference Number: '+response[i].reservationNumber+'';
          					 html += '<br /> ';
          					 html += 'Category Name :'+response[i].categoryName+', Room NO :'+response[i].roomId+'';
          					 html += '<br /> ';
          					 html += 'Check In :'+response[i].checkInDt+'';
          					 html += '<br /> ';
          					 html += 'Check Out :'+response[i].checkOutDt+'';
          					 html += '<br /> ';
          					 html += 'Period :'+response[i].numberOfDays+' Night(s)';
          					 html += '</p>';
          					 html += '</div>';
          					 
          					 if(response[i].status == 'active' && response[i].arrivedVal == '0'){
          						 html += '<div class="col-xs-2 col-md-2">';
              					 html += '<button class="btn btn-default btn-save hotelBookingID" id="cancelBtn" type="button">';
              					 html += '<input type="hidden" id = "bookingId" value="'+response[i].bookingId+'"/>';
              					 html += 'Cancel';
              					 html += '</button>';
              					 
              					 html += '<button class="btn btn-default btn-success btn-change " type="button" id="changeBtn" onclick= "changeReservtn('+response[i].bookingId+','+response[i].reservationNumber+')">';
              					 html += '<input type="hidden"  value="'+response[i].bookingId+'"/>';
              					 html += 'Change</button>';
              					 html += '</div>';
          					 }else if( response[i].status == 'active' && response[i].arrivedVal == '1'){
          						 html += '<div class="col-xs-2 col-md-2">';
          						 html += '<div class="flag_icon"><span class="glyphicon glyphicon-ok green" aria-hidden="true"></span></div>';
          						 html += '<div class="flag_icon">';
          						 html += '<button class="btn btn-default btn-icon" type="button">';
          						 html += '<span class="glyphicon glyphicon-trash arrivedHotelResrvtn" aria-hidden="true"></span>';
          						 html += '<input type="hidden" id="deleteBtn" value="'+response[i].bookingId+'"/>';
          						 html += '</button>';
          						 html += '</div>';
          						 html += '</div>';
          					 }else if(response[i].status == 'cancel'){
          						 html += '<div class="col-xs-2 col-xs-2">';
          						 html += '<div class="flag_icon"><span class="red">Cancelled</span>';
          						 html += '</div>';
          						 
          						 html += '<div class="flag_icon">';
          						 html += '<button class="btn btn-default btn-icon" type="button">';
          						 html += '<span class="glyphicon glyphicon-trash notVisitedReservtnHotel " aria-hidden="true">';
          						 html += '<input type="hidden" id="deleteBtn" value="'+response[i].bookingId+','+response[i].reservationNumber+'"/>';
          						 html += '</span>';
          						 html += '</button>';
          						 html += '</div>';
          						 html += '</div>';
          					 }else if ( response[i].status == 'not visited'){
          						 html += '<div class="col-xs-2 col-md-2">';
          						 html += '<div class="flag_icon"><span class="glyphicon glyphicon-minus red" aria-hidden="true"></span></div>';
          						 html += '<div class="flag_icon">';
          						 html += '<button class="btn btn-default btn-icon" type="button">';
          						 html += '<span class="glyphicon glyphicon-trash notVisitedReservtnHotel" aria-hidden="true">';
          						 html += '<input type="hidden" id="deleteBtn" value="'+response[i].bookingId+','+response[i].reservationNumber+'"/>';
          						 html += '</span>';
          						 html += '</button>';
          						 html += '</div>';
          						 html += '</div>';
          					 }
          					html += '</div>';
       				}// for loop close.
       				 $('#reservationHistory').html(html);
						//$('#deleteReservtn_popUp').hide();
						//$('#myeventreservation_details').show();
       			}//success close.
       		});
				$('#reservationHistory').html(html);
				$('#deleteReservtn_popUp').hide();
				$('#myeventreservation_details').show();
			}
		});
  }



jQuery(document).on('click', ".bookingID", function(){
		var bookingID = jQuery(this).find('input').val();
		 
		$.ajax({
			url   : 'cancelEventReservation.htm?bookingID='+bookingID,
			type  : 'POST',
			success : function(data){
				getMyReservations();
				$('#statusInformtn').html(data);
	 			$('#statusInformtn').show().fadeOut(3000);
			}
		}); //ajax function close.
		
});

jQuery(document).on('click', ".arrivedResrvtn", function(){
	 var deleteRcrdId = jQuery(this).find('input').val();
	
	$.ajax({
			url   : 'deleteEventReservation.htm?deleteRcrdId='+deleteRcrdId,
			type  : 'POST',
			success : function(data){
				getMyReservations();
				$('#statusInformtn').html(data);
	 				$('#statusInformtn').show().fadeOut(3000);
			}
		}); //ajax function close.
	 
});

//dltReservtn
jQuery(document).on('click', "#dltReservtn", function(){
	 var deleteRcrdId = jQuery(this).find('input').val();
     	
	 $.ajax({
			url   : 'deleteEventReservation.htm?deleteRcrdId='+deleteRcrdId,
			type  : 'POST',
			success : function(data){
			 var statusInformtn = data;
			getMyReservations();
			$('#statusInformtn').html(statusInformtn);
		 	$('#statusInformtn').show().fadeOut(3000);
			}
		});   //ajax function close.
});

//delete reservation. 
jQuery(document).on('click', ".deleteRecrdID", function(){
	 var deleteRcrdId = jQuery(this).find('input').val();
	 $.ajax({
			url   : 'deleteEventReservation.htm?deleteRcrdId='+deleteRcrdId,
			type  : 'POST',
			success : function(data){
				getMyReservations();
				$('#statusInformtn').html(data);
	 			$('#statusInformtn').show().fadeOut(3000);
			}
		}); //ajax function close.
});
jQuery(document).on('click', ".notVisitedReservtn", function(){
		 var delteReservtnNum = $("#refNo").val();
		 var deleteRcrdId = $("#resvId").val();
	 var btnsDiv = '';
	 var html = '';
			 html += 'on clicking delete, reservation with reference number  '+delteReservtnNum+',  ';
			 html += 'will be cleared from your reservation history.';
			 $('#deleteReservtnText').html(html);
			 
			 btnsDiv += '<div class="form-group" style="padding: 10px;">';
			 
			 btnsDiv += '<div class="col-sm-6" >';
			 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="dltReservtn">';
			 btnsDiv += '<input type="hidden" value="'+deleteRcrdId+'"/>';
			 btnsDiv += 'Delete</button>';
			 btnsDiv += '</div>';
			 
			 btnsDiv += '<div class="col-sm-6" >';
			 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="cancelReservationBtn">';
			 //btnsDiv += '<input type="hidden" value="'+hotelId+'"/>';
			 btnsDiv += 'Cancel</button>';
			 btnsDiv += '</div>';
			 btnsDiv += '</div>';
			 $('#deleteAndCancelBtns').html(btnsDiv);
			
			 $('#myeventreservation_details').hide();
			 $('#deleteReservtn_popUp').show();
			 document.getElementById('fade').style.display='block';
	 
});


	
	function changeEventReservtn(reserveId,referenceNumber){
		$.ajax({
			url    : 'isSameEventOrg.htm?bookingId='+reserveId,
			type   : 'POST',
			success : function(data){
				var eventOrgId = jQuery.parseJSON(data);
				 
				var  html = '';
				var btnsDiv = '';
				 html += 'you are about to change your reservation : Reference number: '+referenceNumber+',  ';
				 html += 'please on clicking on any available reservation field your current reservation will be ';
				 html += 'transferred to selected reservation field after clicking on change button.';
				 $('#changeReservtnText').html(html);
				 
				 btnsDiv += '<div class="form-group" style="padding: 10px;">';
				 
				 btnsDiv += '<div class="col-sm-6" >';
				 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="changeEventReservation">';
				 btnsDiv += '<input type="hidden" value="'+eventOrgId+'"/>';
				 btnsDiv += 'Change</button>';
				 btnsDiv += '</div>';
				 
				 btnsDiv += '<div class="col-sm-6" >';
				 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="cancelReservationBtn">';
				 //btnsDiv += '<input type="hidden" value="'+hotelId+'"/>';
				 btnsDiv += 'Cancel</button>';
				 btnsDiv += '</div>';
				 btnsDiv += '</div>';
				 $('#changeAndCancelBtns').html(btnsDiv);
				
				 $('#myeventreservation_details').hide();
				 $('#changeReservtn_popUp').show();
				 document.getElementById('fade').style.display='block';
			
			}
		});
	}
	jQuery(document).on('click', "#changeEventReservation", function(){
		var eventOrgId = jQuery(this).find('input').val();
		changeEventOrganisation(eventOrgId);
	});
	function changeEventOrganisation(eventOrgId){
		document.location.href = "changeEvnetUser.htm?eventOrgId="+eventOrgId;
	}
	
	   jQuery(document).on('click', ".notVisitedReservtnHotel", function(){
    	   alert('hotel not visit');
      	 var deleteRcrdInfo = jQuery(this).find('input').val();
      	 var deleteArray = deleteRcrdInfo.split(',');
      	 var deleteRcrdId = deleteArray[0];//record Id 
      	 var delteReservtnNum = deleteArray[1];//reservation Num
      	 var btnsDiv = '';
      	 var html = '';
			 html += 'on clicking delete, reservation with reference number  '+delteReservtnNum+',  ';
			 html += 'will be cleared from your reservation history.';
			 $('#deleteReservtnText').html(html);
			 
			 btnsDiv += '<div class="form-group" style="padding: 10px;">';
			 
			 btnsDiv += '<div class="col-sm-6" >';
			 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="dltHotelReservtn">';
			 btnsDiv += '<input type="hidden" value="'+deleteRcrdId+'"/>';
			 btnsDiv += 'Delete</button>';
			 btnsDiv += '</div>';
			 
			 btnsDiv += '<div class="col-sm-6" >';
			 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="cancelReservationBtn">';
			 //btnsDiv += '<input type="hidden" value="'+hotelId+'"/>';
			 btnsDiv += 'Cancel</button>';
			 btnsDiv += '</div>';
			 btnsDiv += '</div>';
			 $('#deleteAndCancelBtns').html(btnsDiv);
			
			 $('#myeventreservation_details').hide();
			 $('#deleteReservtn_popUp').show();
			 document.getElementById('fade').style.display='block';
      	 
       });
       //dltReservtn
       jQuery(document).on('click', "#dltHotelReservtn", function(){
      	 var deleteRcrdId = jQuery(this).find('input').val();
          	
      	 $.ajax({
				url   : 'deleteReservation.htm?deleteRcrdId='+deleteRcrdId,
				type  : 'POST',
				success : function(data){
				 var statusInformtn = data;
				 getMyReservations();
				$('#statusInformtn').html(statusInformtn);
		 			$('#statusInformtn').show().fadeOut(3000);
				}
			});   //ajax function close.
       });
       //delete reservation. 
       jQuery(document).on('click', ".deleteHotelRecrdID", function(){
      	 var deleteRcrdId = jQuery(this).find('input').val();
      	 $.ajax({
 				url   : 'deleteReservation.htm?deleteRcrdId='+deleteRcrdId,
 				type  : 'POST',
 				success : function(data){
 					getMyReservations();
 					$('#statusInformtn').html(data);
		 			$('#statusInformtn').show().fadeOut(3000);
 				}
 			}); //ajax function close.
       });
              
       jQuery(document).on('click', ".arrivedHotelResrvtn", function(){
      	 var deleteRcrdId = jQuery(this).find('input').val();
      	
      	$.ajax({
  				url   : 'deleteReservation.htm?deleteRcrdId='+deleteRcrdId,
  				type  : 'POST',
  				success : function(data){
  					bookingHistry();
  					$('#statusInformtn').html(data);
		 				$('#statusInformtn').show().fadeOut(3000);
  				}
  			}); //ajax function close.
      	 
       });
       
       //cancel reservation.
       jQuery(document).on('click', ".hotelBookingID", function(){
 			var bookingID = jQuery(this).find('input').val();
 			 
 			$.ajax({
 				url   : 'cancelReservation.htm?bookingID='+bookingID,
 				type  : 'POST',
 				success : function(data){
 					getMyReservations();
 					$('#statusInformtn').html(data);
		 			$('#statusInformtn').show().fadeOut(3000);
 				}
 			}); //ajax function close.
 			
       });
       function changeReservtn(bookingId,reservationNumber){
			
			$.ajax({
				url    : 'isSameHotel.htm?bookingId='+bookingId,
				type   : 'POST',
				success : function(data){
					var hotelId = jQuery.parseJSON(data);
					 
					var  html = '';
					var btnsDiv = '';
					 html += 'you are about to change your reservation : Reference number: '+reservationNumber+',  ';
					 html += 'please on clicking on any available reservation field your current reservation will be ';
					 html += 'transferred to selected reservation field after clicking on change button.';
					 $('#changeReservtnText').html(html);
					 
					 btnsDiv += '<div class="form-group" style="padding: 10px;">';
					 
					 btnsDiv += '<div class="col-sm-6" >';
					 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="changeReservation">';
					 btnsDiv += '<input type="hidden" value="'+hotelId+'"/>';
					 btnsDiv += 'Change</button>';
					 btnsDiv += '</div>';
					 
					 btnsDiv += '<div class="col-sm-6" >';
					 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="cancelReservationBtn">';
					 //btnsDiv += '<input type="hidden" value="'+hotelId+'"/>';
					 btnsDiv += 'Cancel</button>';
					 btnsDiv += '</div>';
					 btnsDiv += '</div>';
					 $('#changeAndCancelBtns').html(btnsDiv);
					
					 $('#myeventreservation_details').hide();
					 $('#changeReservtn_popUp').show();
					 document.getElementById('fade').style.display='block';
				
				}
			});
		}//changeReservtn close
	
		function changeHotel(hotelId){
			document.location.href = "changeHotel.htm?hotelId="+hotelId;
		}
		jQuery(document).on('click', "#changeReservation", function(){
			var hotelId = jQuery(this).find('input').val();
			changeHotel(hotelId);
		});
	

function closeWindow() {
	document.location.href = 'pickEventOrganizer.htm?name=The Pride Hotel';
}

</script>






<!-- whitelist user script starts -->
<script type="text/javascript">
	function getWhiteList() {
		//alert('whitlist info');
		$.ajax({
			url : "getEventWhiteListDet.htm",
			type : 'GET',
			success : function(data) {
				var response = jQuery.parseJSON(data);

				var html = '';
				$('#whitelistdetails').empty();
				for (i in response) {
					html += '<div class="col-xs-7 col-md-7">';
					html += '<p>';
					html += '<strong>Name of Restaurant</strong><br />';
					html += '' + response[i].eventOrgName + '';
					html += '<br />';
					html += '' + response[i].eventOrgAddress + '';
					html += '<br />';
					html += '' + response[i].eventOrgPhoneNumber + ','
							+ response[i].eventOrgCity + ','
							+ response[i].eventOrgPostalCode + '';
					html += '<br />';

					html += '</p>';
					html += '</div>';
					html += '<div class="col-xs-5 col-md-5">';
					html += '<p>';
					html += 'Since : ' + response[i].strtDate + '';
					html += '<br /> ';
					html += '</p>';
					html += '</div>';
				}
				$('#whitelistdetails').html(html);
				$('#mywhite_list').show();
			}

		});

	}
	function getBlackList() {
		//alert('blacl list info');
		$.ajax({
			url : "getEventBlackListDet.htm",
			type : 'GET',
			success : function(data) {
				var response = jQuery.parseJSON(data);
				var html = '';
				$('#blacklistdetails').empty();
				for (i in response) {
					html += '<div class="col-xs-7 col-md-7">';
					html += '<p>';
					html += '<strong>Name of Restaurant</strong><br />';
					html += '' + response[i].eventOrgName + '';
					html += '<br />';
					html += '' + response[i].eventOrgAddress + '';
					html += '<br />';
					html += '' + response[i].eventOrgPhoneNumber + ','
							+ response[i].eventOrgCity + ','
							+ response[i].eventOrgPostalCode + '';
					html += '<br />';

					html += '</p>';
					html += '</div>';
					html += '<div class="col-xs-5 col-md-5">';
					html += '<p>';
					html += 'Since : ' + response[i].strtDate + '';
					html += '<br /> ';
					html += '</p>';
					html += '</div>';
				}
				$('#blacklistdetails').html(html);
				$('#myblack_list').show();
			}
		});

	}

	function validateEmail(sEmail) {
		var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (filter.test(sEmail)) {
			return true;
		} else {
			return false;
		}
	}
	function getEmailVisible() {
		//alert('email list info');
		$
				.ajax({
					url : "getEventUserEmailListDet.htm",
					type : 'GET',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						//$('#emailreservation').empty();

						for (i in response) {
							html += '<div class="reservation-data-section">';
							html += '<div class="col-xs-5 col-md-5">';
							html += '<p>';
							html += '<strong>Name of Restaurant</strong><br />';
							html += '' + response[i].eventOrgName + '';
							html += '<br />';
							html += '' + response[i].eventOrgAddress + '';
							html += '<br />';
							html += '' + response[i].eventOrgPhoneNumber + ','
									+ response[i].eventOrgCity + ','
									+ response[i].eventOrgPostalCode + '';
							html += '<br />';

							html += '</p>';
							html += '</div>';
							html += '<div class="col-xs-4 col-md-4">';
							html += '<p>';
							html += 'Since : ' + response[i].strtDate + '';
							html += '<br /> ';
							html += '</p>';
							html += '</div>';

							html += '<div class="col-xs-3 col-md-3">';
							html += '<button class="btn btn-default" type="button" onclick="getStopSharing()"> Stop Sharing </button>';

							html += '</div>';
							html += '<br /> ';
							html += '</div>';

						}// for loop close.

						$('#emailreservation').html(html);
						$('#myemail_list').show();
					}

				});
	}
</script>
<script type="text/javascript">
	function getStopSharing() {

		$.ajax({
			url : "getEventUserOrgUpdateEmail.htm",
			type : 'GET',
			success : function(data) {
				var response = data;

				$('#emailreservation').empty();

				if (response.emailShare = "inactive") {

					$('#myemail_list').hide();
					$("#alertMsgPopUp3").show();
					$("#alertPopSpanId").empty();
					$("#alertPopSpanIdGreen").append(emailShare);

				} else {
					$('#myemail_list').show();
				}

			}
		});
	}
</script>
<!-- guest reservation validation -->
<script type="text/javascript">
	$(document).ready(function(){
		var validateGuestReservation = jQuery("#guestReservationForm").validate({
			rules: {
				userName : {required : true, minlength : 3, maxlength : 20, letters : true},
				email : {required : true,},
				phone : {required : true, minlength : 10, /* maxlength : 10, */ phoneFormat:true,},
				guestSelectSeats : {min : 1,},
			},
			messages: {
				userName: "Username is required!",
				email : "Enter valid Email!",
				phone : "Enter valid Phone Number!",
				guestSelectSeats : "Select atleast one seat!",
			},
			
				errorElement : "span",
				errorClass : "help-inline-error",
				submitHandler : function(form) {
					
					var userName = $("#guestUser").val();
					var email = $("#guestEmail").val();
					var phone = $("#guestPhone").val();
					var notes = $("#guestNotes").val();
					var slctdSeats = $("#noOfGuestSeats").val();
					
					var json = '{"userName":"' + userName + '","email":"' + email
					+ '","phone":"' + phone + '","slctdSeats":"' + slctdSeats
					+ '","notes":"' + notes + '","scheduleId":"' + globalScheduleId
					+ '","strtTime":"' + gStrtTme + '","endTime":"' + gEndTme
					+ '","duration":"' + gDuration + '","availSeats":"'
					+ gAvailSeats + '","eventName":"' + gEventName + '"}';
					
					var parseJson = JSON.parse(json);
					var duration = gDuration.slice(0, 1);
			
					$.ajax({
						url : "saveGuestReservation.htm",
						type : 'POST',
						data : {
							json : json
						},
						success : function(data) {
							var response = jQuery.parseJSON(data);
							var html1 = '';
							var html2 = '';
							var html3 = '';

							html1 += '<div class="col-xs-12">';
							html1 += '<div class="alert alert-success" role="alert" style="padding:5px 12px;margin-bottom:5px;">';
							html1 += '<strong><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> Success!</strong> You successfully made reservation. </div>';
							html1 += '<p class="center">Reference Number : <b>'
									+ response + '</b></p></div>';

							html2 += '<div class="col-xs-1">';
							html2 += '<span class="glyphicon glyphicon-time" aria-hidden="true"></span></div>';
							html2 += '<div class="col-xs-11"><p>';
							html2 += gStrtTme + '<br />';
							html2 += gEndTme;
							html2 += '<span style="float:right;">' + duration
									+ ' hours</span><br/>';
							html2 += slctdSeats + ' Seat(s)';
							html2 += '</p></div>';

							html3 += '<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>';
							html3 += '<div class="col-xs-11"><p>';
							html3 += userName + ' <br/>';
							html3 += email + ' <br/>';
							html3 += '+' + phone + '</p></div>';

							$("#refConfirm").html(html1);
							$("#timeConfirm").html(html2);
							$("#userConfirm").html(html3);

							$("#myreservation_guest").hide();
							$("#myreservation_confirm").show();
							document.getElementById('fade').style.display = 'block';

						}
					});
				}
		});
		
		$.validator.addMethod("phoneFormat",
			    function(value, element) {
			        return value.match(/^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/);
			    },
			    "Invalid format, valid formats are 123456789,(123).456.7899,(123)-456-7899");
		
		$.validator.addMethod("letters", function(value,element) {
			return this.optional(element) || value == value.match(/^[a-zA-Z0-9_]+$/);
		},"Letters and underscores only!");
		
		
		var userReservationValidation = jQuery("#userReservationForm").validate({
			rules : {
				userSelectSeats : {min : 1,},
			},
			
			messages : {
				userSelectSeats : "Select atleast one seat!",
			},
			
			errorElement : "span",
			errorClass : "help-inline-error",
			submitHandler : function(form) {
				var notes = $("#notes").val();
				var slctdSeats = $("#noOfSeats").val();
				var emailShare = $("#emailShare").val();

				var json = '{"slctdSeats":"' + slctdSeats + '","notes":"' + notes
						+ '","scheduleId":"' + globalScheduleId + '","strtTime":"'
						+ gStrtTme + '","endTime":"' + gEndTme + '","duration":"'
						+ gDuration + '","availSeats":"' + gAvailSeats
						+ '","eventName":"' + gEventName + '","emailShare":"'
						+ emailShare + '"}';
				var parseJson = JSON.parse(json);
				var duration = gDuration.slice(0, 1);
				
				$.ajax({
							url : "saveEvntUsrReservation.htm",
							type : 'POST',
							data : {
								json : json
							},
							success : function(data) {
								var response = jQuery.parseJSON(data);
								var html1 = '';
								var html2 = '';
								var html3 = '';

								html1 += '<div class="col-xs-12">';
								html1 += '<div class="alert alert-success" role="alert" style="padding:5px 12px;margin-bottom:5px;">';
								html1 += '<strong><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> Success!</strong> You successfully made reservation. </div>';
								html1 += '<p class="center">Reference Number : <b>'
										+ response.refNumber + '</b></p></div>';

								html2 += '<div class="col-xs-1">';
								html2 += '<span class="glyphicon glyphicon-time" aria-hidden="true"></span></div>';
								html2 += '<div class="col-xs-11"><p>';
								html2 += gStrtTme + '<br />';
								html2 += gEndTme;
								html2 += '<span style="float:right;">' + duration
										+ ' hours</span><br/>';
								html2 += slctdSeats + ' Seat(s)';
								html2 += '</p></div>';

								html3 += '<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>';
								html3 += '<div class="col-xs-11"><p>';
								html3 += response.userName + ' <br/>';
								html3 += response.email + ' <br/>';
								html3 += '+' + response.contactNumber + '</p></div>';

								$("#refConfirm").html(html1);
								$("#timeConfirm").html(html2);
								$("#userConfirm").html(html3);

								$("#myreservation").hide();
								$("#myreservation_confirm").show();
								document.getElementById('fade').style.display = 'block';

							}
						});
			}
		});
	});
	
	
	
</script>

<!--  logic for event_reservation starts here  -->
<script>
	var currentDt = getCurrentDate();
	var clickDt = currentDt;
	var globalScheduleId;
	var gStrtTme;
	var gEndTme;
	var gDuration;
	var gAvailSeats;
	var gEventName;
	var clickedTime = '';
	var clickedEvent = '';
	var arrayLength = 0;
	
	function getCurrentDate() {
		//current date
		var currentDate = new Date;
		var Day = currentDate.getDate();
		if (Day < 10) {
			Day = '0' + Day;
		} //end if
		var Month = currentDate.getMonth() + 1;
		if (Month < 10) {
			Month = '0' + Month;
		} //end if
		var Year = currentDate.getFullYear();
		var fullDate = Day + '-' + Month + '-' + Year;
		return fullDate;
	}

	
	function primaryDates(date) {
		//alert('primary dates');
		var monthArray = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July',
				'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ];

		$
				.ajax({
					url : "getPrimaryDates.htm?date=" + date,
					type : 'POST',
					success : function(data) {
						var response = JSON.parse(data);
						var html = '';
						var seatCount = $('#seatCount').val();
						if (seatCount == '') {
							seatCount = 0;
						}
						
						var keys = Object.keys(response);
						arrayLength = keys.length;

						html += '<div class="piece">';
						html += '<div class="small_square">';

						var firstDate= null;
						for (var i = 0, key = Object.keys(response), ii = key.length; i < ii; i++) {
							
							if(currentDt==key[i]) {
								var monthNum = key[i].slice(3, 5);
								monthNum = (monthNum - 1);
								jQuery.each(monthArray, function(i, mnthName) {
									if (monthNum == i) {
										html += '<span class="month_title">'
												+ mnthName + '</span>';
									}
								});
						
							}else if(date!=key[i]) {
								firstDate= key[i].slice(0,2);
							}
							
							if(firstDate==01) {
								var monthNum = key[i].slice(3, 5);
								monthNum = (monthNum - 1);
								jQuery.each(monthArray, function(i, mnthName) {
									if (monthNum == i) {
										html += '<span class="month_title">'
												+ mnthName + '</span>';
									}
								});
							}
							
							if(firstDate==15) {
								var monthNum = key[i].slice(3, 5);
								monthNum = (monthNum - 1);
								jQuery.each(monthArray, function(i, mnthName) {
									if (monthNum == i) {
										html += '<span class="month_title">'
												+ mnthName + '</span>';
									}
								});
							}
							
							var haveResrvtn = isHaveReservation(response[key[i]]);
							var minSeats = isHaveOneSeat(response[key[i]]);
							var countSeats = isHaveCountSeats(response[key[i]],
									seatCount);

							if (currentDt == key[i]) {
								if(key[i] == clickDt) {
									html += '<span class="cal_date current_date mark_date hiddenSpan">'
										+ key[i].slice(0, 2) + '';
									html += '<input type = "hidden" value="'+key[i]+'" />';
									html += '</span>';
								}else {
									html += '<span class="cal_date current_date hiddenSpan">'
										+ key[i].slice(0, 2) + '';
									html += '<input type = "hidden" value="'+key[i]+'" />';
									html += '</span>';
								}
								
							} else if (haveResrvtn == true) {
								if(key[i] == clickDt){
									html += '<span class="cal_date loggedin mark_date hiddenSpan">'
										+ key[i].slice(0, 2) + '';
									html += '<input type = "hidden" value="'+key[i]+'" />';
									html += '</span>';
								}else {
									html += '<span class="cal_date loggedin hiddenSpan">'
										+ key[i].slice(0, 2) + '';
									html += '<input type = "hidden" value="'+key[i]+'" />';
									html += '</span>';
								}
								
							} else if (countSeats == true) {
								if (seatCount > 0) {
									if(key[i] == clickDt) {
										html += '<span class="cal_date green mark_date hiddenSpan">'
											+ key[i].slice(0, 2) + '';
										html += '<input type = "hidden" value="'+key[i]+'" />';
										html += '</span>';
									}else {
										html += '<span class="cal_date green hiddenSpan">'
											+ key[i].slice(0, 2) + '';
										html += '<input type = "hidden" value="'+key[i]+'" />';
										html += '</span>';
									}									
								} 
								
								else if((clickedTime != '') || (clickedEvent != '')){
									if((clickedTime != '') && (clickedEvent != '')) {
										var boolTimeEvent = haveScheduleForTimeEvent(response[key[i]],clickedTime,clickedEvent);
										if(boolTimeEvent == true){
											if(key[i] == clickDt) {
												html += '<span class="cal_date green mark_date hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}else {
												html += '<span class="cal_date green hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}											
										}else {
											if(key[i] == clickDt) {
												html += '<span class="cal_date mark_date hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}else {
												html += '<span class="cal_date hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}
										}
									}
									else if(clickedEvent != ''){
										var boolEvent = haveScheduleForEvent(response[key[i]],clickedEvent);
										if(boolEvent == true){
											if(key[i] == clickDt) {
												html += '<span class="cal_date green mark_date hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}else {
												html += '<span class="cal_date green hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}
											
										}else {
											if(key[i] == clickDt) {
												html += '<span class="cal_date mark_date hiddenSpan" >'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}else {
												html += '<span class="cal_date hiddenSpan" >'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}
											
										}
									}else if(clickedTime != ''){
										var boolTime = haveScheduleForTime(response[key[i]],clickedTime);
										if(boolTime == true){
											if(key[i] == clickDt) {
												html += '<span class="cal_date green mark_date hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}else {
												html += '<span class="cal_date green hiddenSpan">'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}
											
										}else {
											if(key[i] == clickDt) {
												html += '<span class="cal_date mark_date hiddenSpan" >'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}else {
												html += '<span class="cal_date hiddenSpan" >'
													+ key[i].slice(0, 2) + '';
												html += '<input type = "hidden" value="'+key[i]+'" />';
												html += '</span>';
											}											
										}
									}
								}
								
								else if (minSeats == true) {
									if(key[i] == clickDt) {
										html += '<span class="cal_date green mark_date hiddenSpan">'
											+ key[i].slice(0, 2) + '';
										html += '<input type = "hidden" value="'+key[i]+'" />';
										html += '</span>';
									}else {
										html += '<span class="cal_date green hiddenSpan">'
											+ key[i].slice(0, 2) + '';
										html += '<input type = "hidden" value="'+key[i]+'" />';
										html += '</span>';
									}									
								}else {
									if(key[i] == clickDt) {
										html += '<span class="cal_date mark_date hiddenSpan" >'
											+ key[i].slice(0, 2) + '';
										html += '<input type = "hidden" value="'+key[i]+'" />';
										html += '</span>';
									}else {
										html += '<span class="cal_date hiddenSpan" >'
											+ key[i].slice(0, 2) + '';
										html += '<input type = "hidden" value="'+key[i]+'" />';
										html += '</span>';
									}									
								}

							} else {
								if(key[i] == clickDt) {
									html += '<span class="cal_date mark_date hiddenSpan" >'
										+ key[i].slice(0, 2) + '';
									html += '<input type = "hidden" value="'+key[i]+'" />';
									html += '</span>';
								}else {
									html += '<span class="cal_date hiddenSpan" >'
										+ key[i].slice(0, 2) + '';
									html += '<input type = "hidden" value="'+key[i]+'" />';
									html += '</span>';
								}								
							}
						}
						html += '</div></div>';
						$("#primaryDates").html(html);
					}
				});
	}

	function getEventWidget(date) {
		$
				.ajax({
					url : "getEventWidgetData.htm?date=" + date,
					type : 'POST',
					success : function(data) {
						var response = JSON.parse(data);
						var map = {
							'00:00' : 'zero',
							'00:30' : 'thirty',
							'01:00' : 'one',
							'01:30' : 'onethirty',
							'02:00' : 'two',
							'02:30' : 'twothirty',
							'03:00' : 'three',
							'03:30' : 'threethirty',
							'04:00' : 'four',
							'04:30' : 'fourthirty',
							'05:00' : 'five',
							'05:30' : 'fivethirty',
							'06:00' : 'six',
							'06:30' : 'sixthirty',
							'07:00' : 'seven',
							'07:30' : 'seventhirty',
							'08:00' : 'eight',
							'08:30' : 'eightthirty',
							'09:00' : 'nine',
							'09:30' : 'ninethirty',
							'10:00' : 'ten',
							'10:30' : 'tenthirty',
							'11:00' : 'eleven',
							'11:30' : 'eleventhirty',
							'12:00' : 'twelve',
							'12:30' : 'twelvethirty',
							'13:00' : 'thirteen',
							'13:30' : 'thirteenthirty',
							'14:00' : 'fourteen',
							'14:30' : 'fourteenthirty',
							'15:00' : 'fifteen',
							'15:30' : 'fifteenthirty',
							'16:00' : 'sixteen',
							'16:30' : 'sixteenthirty',
							'17:00' : 'seventeen',
							'17:30' : 'seventeenthirty',
							'18:00' : 'eighteen',
							'18:30' : 'eighteenthirty',
							'19:00' : 'nineteen',
							'19:30' : 'nineteenthirty',
							'20:00' : 'twenty',
							'20:30' : 'twentythirty',
							'21:00' : 'twentyone',
							'21:30' : 'twentyonethirty',
							'22:00' : 'twentytwo',
							'22:30' : 'twentytwothirty',
							'23:00' : 'twentythree',
							'23:30' : 'twentythreethirty'
						};

						var minEvent = {
							'00:00' : '',
							'00:30' : '',
							'01:00' : '',
							'01:30' : '',
							'02:00' : '',
							'02:30' : '',
							'03:00' : '',
							'03:30' : '',
							'04:00' : '',
							'04:30' : '',
							'05:00' : '',
							'05:30' : '',
							'06:00' : '',
							'06:30' : '',
							'07:00' : '',
							'07:30' : '',
							'08:00' : '',
							'08:30' : '',
							'09:00' : '',
							'09:30' : '',
							'10:00' : '',
							'10:30' : '',
							'11:00' : '',
							'11:30' : '',
							'12:00' : '',
							'12:30' : '',
							'13:00' : '',
							'13:30' : '',
							'14:00' : '',
							'14:30' : '',
							'15:00' : '',
							'15:30' : '',
							'16:00' : '',
							'16:30' : '',
							'17:00' : '',
							'17:30' : '',
							'18:00' : '',
							'18:30' : '',
							'19:00' : '',
							'19:30' : '',
							'20:00' : '',
							'20:30' : '',
							'21:00' : '',
							'21:30' : '',
							'22:00' : '',
							'22:30' : '',
							'23:00' : '',
							'23:30' : ''
						};

						var html = '';

						for (var i = 0, key = Object.keys(response), ii = key.length; i < ii; i++) {
							if (i < 4) {

								if (i == 0) {
									html += '<div class="item active">';
									html += '<div class="carousel-caption">';
								}

								if(clickedEvent == key[i]) {
									html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
									html += '<div class="cell cell_byobject cell_header sticker mark_date hiddenEvent">';
									html += '<span><input type="hidden" value="'+key[i]+'" />'+key[i]+'</span></div>';
								}else {
									html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
									html += '<div class="cell cell_byobject cell_header sticker hiddenEvent">';
									html += '<span><input type="hidden" value="'+key[i]+'" />'+key[i]+'</span></div>';
								}
										
								for (var j = 0, jkeys = Object.keys(map), jj = jkeys.length; j < jj; j++) {
									var val = null;
									
									if((clickedTime != '') || (clickedEvent != '')) {
										if((clickedTime != '') && (clickedEvent != '')){
											if(clickedEvent == key[i]){
												if(clickedTime == jkeys[j]) {
													val = contains(response[clickedEvent],
															clickedTime);
												}
											}
										}else if(clickedEvent != '') {
											if(clickedEvent == key[i]) {
												val = contains(response[clickedEvent],
														jkeys[j]);
											}
										}else if(clickedTime != '') {
											if(clickedTime == jkeys[j]){
												val = contains(response[key[i]],
														clickedTime);
											}
										}									
									}else {
										val = contains(response[key[i]],
												jkeys[j]);
									}
									
									if (val != null) {
										
										/* if(clickedTime == ''){
											minEvent[jkeys[j]] = val;
										} */
										minEvent[jkeys[j]] = val;
										
										if (val.availableSeats > 0) {

											if (val.haveReservation == true) {
												html += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html += '<p class="merge_padding">';
												html += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html += '</p></div>';
											} else {
												html += '<div class="cell eventreserved-'+val.divCount+' booked">';
												html += '<p class="merge_padding">';
												html += '<a href="javascript:void(0)" onclick="showReservationPopup('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html += '</p></div>';
											}
										} else {
											if (val.haveReservation == true) {
												html += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html += '<p class="merge_padding">';
												html += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html += '</p></div>';
											} else {
												html += '<div class="cell eventbooked-'+val.divCount+'">';
												html += '<p class="merge_padding">X</p>';
												html += '</div>';
											}
										}
										if (val.divCount > 1) {
											j = j + ((val.divCount) - 1);
										}
									} else {
										html += '<div class="cell storeclose"></div>';
									}
								}
								html += '</div>';

								if (i == 3) {
									html += '</div></div>';
								}
							}
							if (i >= 4 && i < 8) {

								if (i == 4) {
									html += '<div class="item">';
									html += '<div class="carousel-caption">';
								}

								if(clickedEvent == key[i]) {
									html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
									html += '<div class="cell cell_byobject cell_header sticker mark_date hiddenEvent">';
									html += '<span><input type="hidden" value="'+key[i]+'" />'+key[i]+'</span></div>';
								}else {
									html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
									html += '<div class="cell cell_byobject cell_header sticker hiddenEvent">';
									html += '<span><input type="hidden" value="'+key[i]+'" />'+key[i]+'</span></div>';
								}

										
								for (var j = 0, jkeys = Object.keys(map), jj = jkeys.length; j < jj; j++) {
									var val=null;
									
									if((clickedTime != '') || (clickedEvent != '')) {
										if((clickedTime != '') && (clickedEvent != '')) {
											if(clickedEvent == key[i]) {
												if(clickedTime == jkeys[j]) {
													val = contains(response[clickedEvent],
															clickedTime);
													
												}
											}
										}else if(clickedEvent != '') {
											if(clickedEvent == key[i]) {
												val = contains(response[clickedEvent],
														jkeys[j]);
											}
										}else if(clickedTime != '') {
											if(clickedTime == jkeys[j]){
												val = contains(response[key[i]],
														clickedTime);
											}
										}
									}else {
										val = contains(response[key[i]],
												jkeys[j]);
									}
									
									if (val != null) {
										
										/* if(clickedTime == ''){
											minEvent[jkeys[j]] = val;
										} */
										minEvent[jkeys[j]] = val;
										
										if (val.availableSeats > 0) {

											if (val.haveReservation == true) {
												html += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html += '<p class="merge_padding">';
												html += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html += '</p></div>';
											} else {
												html += '<div class="cell eventreserved-'+val.divCount+' booked">';
												html += '<p class="merge_padding">';
												html += '<a href="javascript:void(0)" onclick="showReservationPopup('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html += '</p></div>';
											}

										} else {
											if (val.haveReservation == true) {
												html += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html += '<p class="merge_padding">';
												html += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html += '</p></div>';
											} else {
												html += '<div class="cell eventbooked-'+val.divCount+'">';
												html += '<p class="merge_padding">X</p>';
												html += '</div>';
											}
										}
										if (val.divCount > 1) {
											j = j + ((val.divCount) - 1);
										}
									} else {
										html += '<div class="cell storeclose"></div>';
									}
								}
								html += '</div>';
							}
							if (i == key.length) {
								html += '</div></div>';
							}
						}
						for (keyVal in minEvent) {
							if (minEvent[keyVal] != '') {
								var id = '#' + map[keyVal];
								$(id).addClass("green");
							}
						}
						

						$("#dateButton").html(date);
						$("#eventWidget").html(html);
						$('.ajax_loader_inner').removeClass();

					}
				});
	}

	function getWidgetByEventId(clickedDt, eventId, guideId, clickDt) {

		$
				.ajax({
					url : "loadDataByClickedDt.htm?clickedDt=" + clickedDt
							+ "&eventId=" + eventId + "&guideId=" + guideId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var k = 0;
						var map = {
							'00:00' : 'zero',
							'00:30' : 'thirty',
							'01:00' : 'one',
							'01:30' : 'onethirty',
							'02:00' : 'two',
							'02:30' : 'twothirty',
							'03:00' : 'three',
							'03:30' : 'threethirty',
							'04:00' : 'four',
							'04:30' : 'fourthirty',
							'05:00' : 'five',
							'05:30' : 'fivethirty',
							'06:00' : 'six',
							'06:30' : 'sixthirty',
							'07:00' : 'seven',
							'07:30' : 'seventhirty',
							'08:00' : 'eight',
							'08:30' : 'eightthirty',
							'09:00' : 'nine',
							'09:30' : 'ninethirty',
							'10:00' : 'ten',
							'10:30' : 'tenthirty',
							'11:00' : 'eleven',
							'11:30' : 'eleventhirty',
							'12:00' : 'twelve',
							'12:30' : 'twelvethirty',
							'13:00' : 'thirteen',
							'13:30' : 'thirteenthirty',
							'14:00' : 'fourteen',
							'14:30' : 'fourteenthirty',
							'15:00' : 'fifteen',
							'15:30' : 'fifteenthirty',
							'16:00' : 'sixteen',
							'16:30' : 'sixteenthirty',
							'17:00' : 'seventeen',
							'17:30' : 'seventeenthirty',
							'18:00' : 'eighteen',
							'18:30' : 'eighteenthirty',
							'19:00' : 'nineteen',
							'19:30' : 'nineteenthirty',
							'20:00' : 'twenty',
							'20:30' : 'twentythirty',
							'21:00' : 'twentyone',
							'21:30' : 'twentyonethirty',
							'22:00' : 'twentytwo',
							'22:30' : 'twentytwothirty',
							'23:00' : 'twentythree',
							'23:30' : 'twentythreethirty'
						};

						var minEvent = {
							'00:00' : '',
							'00:30' : '',
							'01:00' : '',
							'01:30' : '',
							'02:00' : '',
							'02:30' : '',
							'03:00' : '',
							'03:30' : '',
							'04:00' : '',
							'04:30' : '',
							'05:00' : '',
							'05:30' : '',
							'06:00' : '',
							'06:30' : '',
							'07:00' : '',
							'07:30' : '',
							'08:00' : '',
							'08:30' : '',
							'09:00' : '',
							'09:30' : '',
							'10:00' : '',
							'10:30' : '',
							'11:00' : '',
							'11:30' : '',
							'12:00' : '',
							'12:30' : '',
							'13:00' : '',
							'13:30' : '',
							'14:00' : '',
							'14:30' : '',
							'15:00' : '',
							'15:30' : '',
							'16:00' : '',
							'16:30' : '',
							'17:00' : '',
							'17:30' : '',
							'18:00' : '',
							'18:30' : '',
							'19:00' : '',
							'19:30' : '',
							'20:00' : '',
							'20:30' : '',
							'21:00' : '',
							'21:30' : '',
							'22:00' : '',
							'22:30' : '',
							'23:00' : '',
							'23:30' : ''
						};

						var monthArray = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May',
								'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov',
								'Dec' ];

						var dateSet = Object.keys(response);
						var html2 = '';
						var count = 0;

						for (var i = 0, keys = Object.keys(response), ii = keys.length; i < ii; i++) {
							var list = response[keys[i]];
							if (i < 7) {
								if (i == 0) {
									html2 += '<div class="item active">';
									html2 += '<div class="carousel-caption">';
								}

								html2 += '<div class="col-xs-3 col-xs-3-dateline nopadding book_info">';
								html2 += '<div class="cell cell_header cell_byobject">'
										+ keys[i] + '</div>';

								for (var j = 0, jkeys = Object.keys(map), jj = jkeys.length; j < jj; j++) {
									var val = null;
									
									if(clickedTime != '') {
										if(clickedTime == jkeys[j]) {
											val = contains(response[keys[i]],
													clickedTime);
										}										
									}else {
										val = contains(response[keys[i]],
												jkeys[j]);
									}
									
									if (val != null) {
										
										/* if(clickedTime == ''){
											minEvent[jkeys[j]] = val;
										} */
										minEvent[jkeys[j]] = val;
										
										if (val.availableSeats > 0) {

											if (val.haveReservation == true) {
												html2 += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html2 += '<p class="merge_padding">';
												html2 += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html2 += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html2 += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html2 += '</p></div>';
											} else {
												html2 += '<div class="cell eventreserved-'+val.divCount+' booked">';
												html2 += '<p class="merge_padding">';
												html2 += '<a href="javascript:void(0)" onclick = "showReservationPopup('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html2 += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html2 += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html2 += '</p></div>';
											}

										} else {
											if (val.haveReservation == true) {
												html2 += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html2 += '<p class="merge_padding">';
												html2 += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html2 += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html2 += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html2 += '</p></div>';
											} else {
												html2 += '<div class="cell eventbooked-'+val.divCount+'">';
												html2 += '<div class="cell_event_addition-info">';
												html2 += '</div>';
												html2 += '<p class="merge_padding">';
												html2 += 'X <br/>';
												html2 += '</p>';
												html2 += '</div>';
											}
										}
										j = j + ((val.divCount) - 1);
									} else {
										html2 += '<div class="cell storeclose"></div>';
									}

								}
								html2 += '</div>';
							}

							if (i == 6) {
								html2 += '</div>';
								html2 += '</div>'; //item active close
							}

							if (i == 7) {
								html2 += '<div class="item">';
								html2 += '<div class="carousel-caption">';
							}

							if (i >= 7 && i < 14) {
								html2 += '<div class="col-xs-3 col-xs-3-dateline nopadding book_info">';
								html2 += '<div class="cell cell_header cell_byobject">'
										+ keys[i] + '</div>';

								for (var j = 0, jkeys = Object.keys(map), jj = jkeys.length; j < jj; j++) {
									var val = null;
									
									if(clickedTime != '') {
										if(clickedTime == jkeys[j]) {
											val = contains(response[keys[i]],
													clickedTime);
										}										
									}else {
										val = contains(response[keys[i]],
												jkeys[j]);
									}
									
									if (val != null) {
										
										/* if(clickedTime == ''){
											minEvent[jkeys[j]] = val;
										} */
										minEvent[jkeys[j]] = val;
										
										if (val.availableSeats > 0) {

											if (val.haveReservation == true) {
												html2 += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html2 += '<p class="merge_padding">';
												html2 += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html2 += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html2 += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html2 += '</p></div>';
											} else {
												html2 += '<div class="cell eventreserved-'+val.divCount+' booked">';
												html2 += '<p class="merge_padding">';
												html2 += '<a href="javascript:void(0)" onclick = "showReservationPopup('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html2 += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html2 += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html2 += '</p></div>';
											}
										} else {
											if (val.haveReservation == true) {
												html2 += '<div class="cell event-mybook-'+val.divCount+' booked">';
												html2 += '<p class="merge_padding">';
												html2 += '<a href="javascript:void(0)" onclick="showBlueReservation('
														+ val.scheduleId
														+ ')">';
												if (val.availableSeats > 2) {
													html2 += '>2 <br/>'
															+ val.guideName
															+ '</a>';
												} else {
													html2 += val.availableSeats
															+ '<br/>'
															+ val.guideName
															+ '</a>';
												}
												html2 += '</p></div>';
											} else {
												html2 += '<div class="cell eventbooked-'+val.divCount+'">';
												html2 += '<div class="cell_event_addition-info">';
												html2 += '</div>';
												html2 += '<p class="merge_padding">';
												html2 += 'X <br/>';
												html2 += '</p>';
												html2 += '</div>';
											}
										}
										j = j + ((val.divCount) - 1);
									} else {
										html2 += '<div class="cell storeclose"></div>';
									}
								}
								html2 += '</div>';
							}
							if (i == keys.length) {
								html2 += '</div>';
								html2 += '</div>';
							}
						}
						for (keyVal in minEvent) {
							if (minEvent[keyVal] != '') {
								var id = '#' + map[keyVal];
								$(id).addClass("green");
							}
						}

						$('#dateButton').html(clickDt);
						$('#eventWidget').html(html2);
						$('.ajax_loader_inner').removeClass();
					}
				});
	}

	jQuery(document).on('click', ".hiddenSpan", function() {
		var clickedDt = jQuery(this).find('input').val();
		var eventId = $('#selectTour').val();
		var guideId = '0';
		clickDt = clickedDt;
		primaryDates(currentDt);
		$('.timescale').removeClass('green');
		if (eventId == 0) {
			getEventWidget(clickedDt);
		} else {
			clickedDt = clickedDt.replace('-', '/');
			clickedDt = clickedDt.replace('-', '/');
			getWidgetByEventId(clickedDt, eventId, guideId, clickDt);
		}

	});
	
	jQuery(document).on('click', ".hiddenEvent", function() {
		var selectedEvent = jQuery(this).find('input').val();
		clickedEvent = selectedEvent;
		var clickedDt = clickDt.replace('-', '/');
		clickedDt = clickedDt.replace('-', '/');
		var eventId = $('#selectTour').val();
		var guideId = '0';
		primaryDates(currentDt);
		$('.timescale').removeClass('green');
		if(eventId == 0){
			getEventWidget(clickDt);
		}else {
			getWidgetByEventId(clickedDt, eventId, guideId, clickDt);
		}		
		
	});
	
	jQuery(document).on('click', ".timescale", function() {
		var selectedTime = jQuery(this).find('input').val();
		clickedTime = selectedTime;
		var clickedDt = clickDt.replace('-', '/');
		clickedDt = clickedDt.replace('-', '/');
		var eventId = $("#selectTour").val();
		var guideId = '0';
		primaryDates(currentDt);
		$('.timescale').removeClass('green');
		if(eventId == 0){
			getEventWidget(clickDt);
		}else {
			getWidgetByEventId(clickedDt, eventId, guideId, clickDt);
		}
		
	});
	
	
	jQuery(document).on('keypress', "#seatCount", function(e) {
		if(e.which == 13 || ((e.which >= 48) && (e.which <= 57))) {
			
		}else {
			e.preventDefault();
		}
	});

	function countChange() {
		var eventId = $('#selectTour').val();
		var guideId = '0';
		var clickedDt = clickDt.replace('-', '/');
		clickedDt = clickedDt.replace('-', '/');
		primaryDates(currentDt);
		$('.timescale').removeClass('green');
		if (eventId == 0) {
			getEventWidget(clickDt);
		}

		else {
			getWidgetByEventId(clickedDt, eventId, guideId, clickDt);
		}
	}

	function loadTours() {
		$.ajax({
			url : 'selectTour.htm',
			type : 'POST',
			success : function(data) {
				var response = jQuery.parseJSON(data);
				var html = '';

				html += '<option value="0">Select Tour</option>';
				for (i in response) {
					html += '<option value='+response[i].eventId+'>'
							+ response[i].eventName + '</option>';
				}
				$('#selectTour').html(html);
			}
		});
	}

	function changeEvent() {
		clickedEvent = '';
		var eventId = $('#selectTour').val();
		var guideId = '0';
		var clickedDt = clickDt.replace('-', '/');
		clickedDt = clickedDt.replace('-', '/');
		primaryDates(currentDt);
		$('.timescale').removeClass('green');
		if (eventId == 0) {
			getEventWidget(clickDt);
		}

		else {
			getWidgetByEventId(clickedDt, eventId, guideId, clickDt);
		}

		//document.location.href = "eventUserByObject.htm?clickedDt="+clickDt+"&eventId="+eventId ;
	}

	//display reservation popup options...........
	function showReservationPopup(scheduleId) {
		var isLoggedIn = $("#loggedin").val();
		if (isLoggedIn != '') {
			$
					.ajax({
						url : "getEventReservedUsers.htm?scheduleId="
								+ scheduleId,
						type : 'POST',
						success : function(data) {
							var response = jQuery.parseJSON(data);
							var html = '';
							for (key in response) {
								var parsedKey = JSON.parse(key);
								globalScheduleId = parsedKey.scheduleId;
								gStrtTme = parsedKey.evntStrtTme;
								gEndTme = parsedKey.evntEndTme;
								gDuration = parsedKey.duration;
								gAvailSeats = parsedKey.availableSeats;
								gEventName = parsedKey.eventName;
								html += '<div class="col-xs-1">';
								html += '<span class="glyphicon glyphicon-time" aria-hidden="true"></span></div>';
								html += '<div class="col-xs-11"><p>';
								html += parsedKey.evntStrtTme + '<br />';
								html += parsedKey.evntEndTme + '</p></div>';
								html += '<div class="col-xs-1">';
								html += '<span class="glyphicons glyphicon-times" aria-hidden="true">#</span></div>';
								html += '<div class="col-xs-11">';
								html += '<p>' + parsedKey.eventName + '</p>';
								html += '<select class="form-control" id="noOfSeats" name="userSelectSeats">';
								html += '<option value="0">Select Seat(s)</option>';
								var seats = getIncrementalArray(parsedKey.availableSeats);
								for (var i = 1; i <= seats.length; i++) {
									if (i < 10) {
										html += '<option value="'+i+'">&nbsp;&nbsp;'
												+ i + ' Seat(s)</option>';
									} else {
										html += '<option value="'+i+'">' + i
												+ ' Seat(s)</option>';
									}

								}
								html += '</select>';
								html += '<div id="seatsStatusDiv" align="center">';
								html += '<span id="seatsStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span></div></div>';
							}
							
							
							
							$("#selectSeats").html(html);
							$("#myreservation").show();
							document.getElementById('fade').style.display = 'block';
						}
					});
		} else {
			showOptionsPopup(scheduleId);
		}

	}

	function showGuestReservationPopup(scheduleId) {

		$
				.ajax({
					url : "getEventReservedUsers.htm?scheduleId=" + scheduleId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						for (key in response) {
							var parsedKey = JSON.parse(key);
							globalScheduleId = parsedKey.scheduleId;
							gStrtTme = parsedKey.evntStrtTme;
							gEndTme = parsedKey.evntEndTme;
							gDuration = parsedKey.duration;
							gAvailSeats = parsedKey.availableSeats;
							gEventName = parsedKey.eventName;
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicon glyphicon-time" aria-hidden="true"></span></div>';
							html += '<div class="col-xs-11"><p>';
							html += parsedKey.evntStrtTme + '<br />';
							html += parsedKey.evntEndTme + '</p></div>';
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicons glyphicon-times" aria-hidden="true">#</span></div>';
							html += '<div class="col-xs-11">';
							html += '<p>' + parsedKey.eventName + '</p>';
							html += '<select class="form-control" id="noOfGuestSeats" name="guestSelectSeats">';
							html += '<option value="0">Select Seats</option>';
							var seats = getIncrementalArray(parsedKey.availableSeats);
							for (var i = 1; i <= seats.length; i++) {
								if (i < 10) {
									html += '<option value="'+i+'">&nbsp;&nbsp;'
											+ i + ' Seat(s)</option>';
								} else {
									html += '<option value="'+i+'">' + i
											+ ' Seat(s)</option>';
								}

							}
							html += '</select>';
							html += '<div id="guestSeatsStatusDiv" align="center">';
							html += '<span id="guestSeatsStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span></div></div>';
						}

						$("#selectSeatsGuest").html(html);
						$("#myreservation_guest").show();
						document.getElementById('fade').style.display = 'block';
					}
				});
	}
	

	function showBlueReservation(scheduleId) {
		$
				.ajax({
					url : "getBlueReservedUsers.htm?scheduleId=" + scheduleId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						var html1 = '';
						for (key in response) {
							var parsedKey = JSON.parse(key);
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicon glyphicon-time" aria-hidden="true"></span></div>';
							html += '<div class="col-xs-11">';
							html += '<p> Start time : ' + parsedKey.evntStrtTme
									+ '<br/>';
							html += ' End time : ' + parsedKey.evntEndTme
									+ '</p></div>';
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicons glyphicon-times" aria-hidden="true">#</span></div>';
							html += '<div class="col-xs-11">';
							html += '<p> Tour name : ' + parsedKey.eventName
									+ '</p>';
							for (i in response[key]) {
								html += '<p> Booked Seats : '
										+ response[key][i].noOfPeople
										+ ' Seat(s)</p>';

								html1 += '<div class="form-group">';
								html1 += '<div class="col-xs-1">';
								html1 += '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></div>';
								html1 += '<div class="col-xs-11">';
								html1 += '<textarea readonly class="form-control" id="inputEmail3" rows="1">'
										+ response[key][i].notes
										+ '</textarea></div></div>';
							}
							html += '</div>';
						}
						$("#blueSeats").html(html);
						$("#blueNote").html(html1);
						$("#blue_myreservation").show();
						document.getElementById('fade').style.display = 'block';
					}
				});

	}

	function showOptionsPopup(scheduleId) {
		globalScheduleId = scheduleId;
		$("#myreservation_options").show();
		document.getElementById('fade').style.display = 'block';
	}

	function showLogin() {
		$("#myreservation_options").hide();
		$("#mylogin").show();
		document.getElementById('fade').style.display = 'block';
	}

	function guestPopup() {
		$("#myreservation_options").hide();
		showGuestReservationPopup(globalScheduleId);
	}

	function validatePhone(phneNum) {
		//var a = document.getElementById(txtPhone).value;
		var filter = /^[0-9-+]+$/;
		if (filter.test(phneNum)) {
			return true;
		} else {
			return false;
		}
	}

	function validateEmail(sEmail) {
		var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (filter.test(sEmail)) {
			return true;
		} else {
			return false;
		}

	}

	function isHaveReservation(arr) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].haveReservation == true) {
				return true;
			}
		}
		return false;
	}

	function isHaveOneSeat(arr) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].availableSeats > 0) {
				return true;
			}
		}
		return false;
	}

	function isHaveCountSeats(arr, seatCount) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].availableSeats >= seatCount) {
				return true;
			}
		}
		return false;
	}

	function contains(arr, str) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].evntStrtTme == str) {
				return arr[i];
			}
		}
		return null;
	}
	
	function haveScheduleForTimeEvent(arr,time,eventName) {
		for(var i=0;i<arr.length;i++) {
			if((arr[i].evntStrtTme == time) && (arr[i].eventName == eventName) && (arr[i].availableSeats > 0)){
				return true;
			}
		}
		return false;
	}
	
	function haveScheduleForEvent(arr,eventName) {
		for(var i=0;i<arr.length;i++) {
			if((arr[i].eventName == eventName) && (arr[i].availableSeats > 0)){
				return true;
			}
		}
		return false;
	}
	
	function haveScheduleForTime(arr,time) {
		for(var i=0;i<arr.length;i++) {
			if((arr[i].evntStrtTme == time) && (arr[i].availableSeats > 0)){
				return true;
			}
		}
		return false;
	}

	function getIncrementalArray(noOfSeats) {
		var arr = new Array;
		for (var i = 1; i <= noOfSeats; i++) {
			arr.push((i));
		}
		return arr;
	}
	
	function saveTheGuestReservation() {
		return false;
	}
	
	function saveTheUserReservation() {
		return false;
	}

	function resetPage() {
		document.location.href = "returnToUserEventReservation.htm";
	}
	
	jQuery(document).on('click',".hiddenDateButton",function() {
		//alert("clicked");		
		loadTours()
		getEventWidget(clickDt);
		clickedTime = '';
		clickedEvent = '';
		$(".timescale").css("background","none");		
		$("#seatCount").val('');
		primaryDates(currentDt);
	});

	function emailIsShared() {
		var emailShare = document.getElementById("emailShare").value = "Y";
		return emailShare;
	}

	function displayTimeScale() {
		var html = '';
		
		html += '<div class="cell timescale" id="zero"><span class="timevalue">00:00<input type="hidden" value="00:00" /></span></div>';
		html += '<div class="cell timescale" id="thirty"><span class="timevalue">00:30<input type="hidden" value="00:30" /></span></div>';
		html += '<div class="cell timescale" id="one"><span class="timevalue">01:00<input type="hidden" value="01:00" /></span></div>';
		html += '<div class="cell timescale" id="onethirty"><span class="timevalue">01:30<input type="hidden" value="01:30" /></span></div>';
		html += '<div class="cell timescale" id="two"><span class="timevalue">02:00<input type="hidden" value="02:00" /></span></div>';
		html += '<div class="cell timescale" id="twothirty"<span class="timevalue">02:30<input type="hidden" value="02:30" /></span></div>';
		html += '<div class="cell timescale" id="three"><span class="timevalue">03:00<input type="hidden" value="03:00" /></span></div>';
		html += '<div class="cell timescale" id="threethirty"><span class="timevalue">03:30<input type="hidden" value="03:30" /></span></div>';
		html += '<div class="cell timescale" id="four"><span class="timevalue">04:00<input type="hidden" value="04:00" /></span></div>';
		html += '<div class="cell timescale" id="fourthirty"><span class="timevalue">04:30<input type="hidden" value="04:30" /></span></div>';
		html += '<div class="cell timescale" id="five"><span class="timevalue">05:00<input type="hidden" value="05:00" /></span></div>';
		html += '<div class="cell timescale" id="fivethirty"><span class="timevalue">05:30<input type="hidden" value="05:30" /></span></div>';
		html += '<div class="cell timescale" id="six"><span class="timevalue">06:00<input type="hidden" value="06:00" /></span></div>';
		html += '<div class="cell timescale" id="sixthirty"><span class="timevalue">06:30<input type="hidden" value="06:30" /></span></div>';
		html += '<div class="cell timescale" id="seven"><span class="timevalue">07:00<input type="hidden" value="07:00" /></span></div>';
		html += '<div class="cell timescale" id="seventhirty"><span class="timevalue">07:30<input type="hidden" value="07:30" /></span></div>';
		html += '<div class="cell timescale" id="eight"><span class="timevalue">08:00<input type="hidden" value="08:00" /></span></div>';
		html += '<div class="cell timescale" id="eightthirty"><span class="timevalue">08:30<input type="hidden" value="08:30" /></span></div>';
		html += '<div class="cell timescale" id="nine"><span class="timevalue">09:00<input type="hidden" value="09:00" /></span></div>';
		html += '<div class="cell timescale" id="ninethirty"><span class="timevalue">09:30<input type="hidden" value="09:30" /></span></div>';
		html += '<div class="cell timescale" id="ten"><span class="timevalue">10:00<input type="hidden" value="10:00" /></span></div>';
		html += '<div class="cell timescale" id="tenthirty"><span class="timevalue">10:30<input type="hidden" value="10:30" /></span></div>';
		html += '<div class="cell timescale" id="eleven"><span class="timevalue">11:00<input type="hidden" value="11:00" /></span></div>';
		html += '<div class="cell timescale" id="eleventhirty"><span class="timevalue">11:30<input type="hidden" value="11:30" /></span></div>';
		html += '<div class="cell timescale" id="twelve"><span class="timevalue">12:00<input type="hidden" value="12:00" /></span></div>';
		html += '<div class="cell timescale" id="twelvethirty"><span class="timevalue">12:30<input type="hidden" value="12:30" /></span></div>';
		html += '<div class="cell timescale" id="thirteen"><span class="timevalue">13:00<input type="hidden" value="13:00" /></span></div>';
		html += '<div class="cell timescale" id="thirteenthirty"><span class="timevalue">13:30<input type="hidden" value="13:30" /></span></div>';
		html += '<div class="cell timescale" id="fourteen"><span class="timevalue">14:00<input type="hidden" value="14:00" /></span></div>';
		html += '<div class="cell timescale" id="fourteenthirty"><span class="timevalue">14:30<input type="hidden" value="14:30" /></span></div>';
		html += '<div class="cell timescale" id="fifteen"><span class="timevalue">15:00<input type="hidden" value="15:00" /></span></div>';
		html += '<div class="cell timescale" id="fifteenthirty"><span class="timevalue">15:30<input type="hidden" value="15:30" /></span></div>';
		html += '<div class="cell timescale" id="sixteen"><span class="timevalue">16:00<input type="hidden" value="16:00" /></span></div>';
		html += '<div class="cell timescale" id="sixteenthirty"><span class="timevalue">16:30<input type="hidden" value="16:30" /></span></div>';
		html += '<div class="cell timescale" id="seventeen"><span class="timevalue">17:00<input type="hidden" value="17:00" /></span></div>';
		html += '<div class="cell timescale" id="seventeenthirty"><span class="timevalue">17:30<input type="hidden" value="17:30" /></span></div>';
		html += '<div class="cell timescale" id="eighteen"><span class="timevalue">18:00<input type="hidden" value="18:00" /></span></div>';
		html += '<div class="cell timescale" id="eighteenthirty"><span class="timevalue">18:30<input type="hidden" value="18:30" /></span></div>';
		html += '<div class="cell timescale" id="nineteen"><span class="timevalue">19:00<input type="hidden" value="19:00" /></span></div>';
		html += '<div class="cell timescale" id="nineteenthirty"><span class="timevalue">19:30<input type="hidden" value="19:30" /></span></div>';
		html += '<div class="cell timescale" id="twenty"><span class="timevalue">20:00<input type="hidden" value="20:00" /></span></div>';
		html += '<div class="cell timescale" id="twentythirty"><span class="timevalue">20:30<input type="hidden" value="20:30" /></span></div>';
		html += '<div class="cell timescale" id="twentyone"><span class="timevalue">21:00<input type="hidden" value="21:00" /></span></div>';
		html += '<div class="cell timescale" id="twentyonethirty"><span class="timevalue">21:30<input type="hidden" value="21:30" /></span></div>';
		html += '<div class="cell timescale" id="twentytwo"><span class="timevalue">22:00<input type="hidden" value="22:00" /></span></div>';
		html += '<div class="cell timescale" id="twentytwothirty"><span class="timevalue">22:30<input type="hidden" value="22:30" /></span></div>';
		html += '<div class="cell timescale" id="twentythree"><span class="timevalue">23:00<input type="hidden" value="23:00" /></span></div>';
		html += '<div class="cell timescale" id="twentythreethirty"><span class="timevalue">23:30<input type="hidden" value="23:30" /></span></div>';

		$('#timeScale').html(html);
	}

	$(document).ready(function() {
		clickedTime = '';
		clickedEvent = '';
		loadTours();
		primaryDates(currentDt);
		getEventWidget(currentDt);
		displayTimeScale();
		
		
		var total_left=0;
        var click_count=0;
        var scroll_limit=32;
        var total_scroll=0;
        $(document).on('click','.carousel-control.right',function()
        { 
          if(total_scroll >= arrayLength){
          $(this).attr("disabled","disabled");
          } else{
             total_left+=295;
             $(".carousel-caption1").css("margin-left",-total_left);
             click_count+=11;
             total_scroll=scroll_limit+click_count;
            
             return false;
          }
        });
        //Scroll right
        $(document).on('click','.carousel-control.left',function(){
         
         $(".carousel-control.right").attr("disabled",'enabled');
           if(total_left==0)
           return false;
            total_left-=295;
            console.log(total_left);
            $(".carousel-caption1").css("margin-left",-total_left);
             click_count-=11;
             total_scroll=scroll_limit+click_count;
             return false;
        });

	});
</script>
<!-- whitelist user script ends -->
</head>


<div id="alertMsgPopUp" class="white_content"
	style="display: none; z-index: 999; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Alert Message</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('alertMsgPopUp').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="alertPopSpanIdRed" style="color: red; font-weight: bold;"></span>
		<span id="alertPopSpanIdGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="alertLogPopUp" class="white_content"
	style="display: none; z-index: 999; width: 467px; margin: 0px 555px 0px;">
	<h3 align="center">Alert Message</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('alertLogPopUp').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="alertLogPopSpanIdRed" style="color: red; font-weight: bold;"></span>
		<span id="alertLogPopSpanIdGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>

<div id="alertMsgPopUp3" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Alert Message</h3>
	<a href="javascript:void(0)" class="close_popup" onclick="document.getElementById('alertMsgPopUp3').style.display='none';document.getElementById('fade').style.display='none'">
		<!-- 	onclick="document.getElementById('alertMsgPopUp').style.display='none';document.getElementById('fade').style.display='none'"> -->
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>

	<!-- popup for showing avalable hotels  -->
	<div style="padding: 20px 100px;">
		<span id="alertPopSpanIdRed" style="color: red; font-weight: bold;"></span>
		<span id="alertPopSpanIdGreen"
			style="color: green; font-weight: bold;"></span>
		<div style="padding: 20px 100px;">
			<span id="alertPopSpanIdRed" style="color: red; font-weight: bold;"></span>
			<span id="alertPopSpanIdGreen"
				style="color: green; font-weight: bold;"></span>
		</div>

		<font size="3" color="green">email sharing is successfully
			stopped</font>

	</div>
</div>

<body style="margin: 20px 0px;">
<div id="valLogged"></div>
	<%-- <input type="hidden" value="${sessionScope.loggedIn}" id="loggedin"></input>
	<input type="hidden" value="${sessionScope.providerLoggedIn}" id="userloggedin"></input> --%>
	<input type="hidden" value="${signUp}" id="signUp">
	<input type="hidden" value="${invalidServLogin}" id="invalidServLogin">
	<input type="hidden" value="${user_not_unique}" id="userexistpopup">
	<input type="hidden" value="${email_not_unique}" id="emailexistpopup">
	<input type="hidden" value="${pemail_not_unique}" id="pemailexistpopup">

	<input type="hidden" value="${sessionScope.loggedIn}" id="loggedin"></input>
	<input type="hidden" value="${loginhide}" id="loginhide">
	<input type="hidden" value="${invalidLogin}" id="invalidLogin">
	<input type="hidden" value="${guestmode}" id="guestmode">
	<input type="hidden" value="${reservemode }" id="reservemode">
	<input type="hidden" id="history" value="${succuessVal}">
	<input type="hidden" value="${updateFailure}" id="updateFailure">
	<input type="hidden" value="${isTokenValid}" id="isTokenValid">
	<input type="hidden" value="${isTokenInValid}" id="isTokenInValid">
	<input type="hidden" value="${logoutsuccess}" id="logoutsuccess">
	<input type="hidden" value="${account_inactive}"
		id="accountinactivepop">
	<input type="hidden" value="${admin_not_unique}" id="adminexistpopup">
	<input type="hidden" value="${admail_not_unique}" id="admailexistpopup">
	<input type="hidden" value="${loginhide}" id="loginhide">
	<input type="hidden" value="${invalidForgot}" id="invalidForgot">
	<input type="hidden" value="${loginsuccess}" id="loginsuccess">
	<input type="hidden" value="${signUp}" id="signUp">
	<input type="hidden" value="${user_not_unique}" id="userexistpopup">
	<input type="hidden" value="${email_not_unique}" id="emailexistpopup">
	<input type="hidden" value="${pemail_not_unique}" id="pemailexistpopup">
	<input type="hidden" value="${mail_sent}" id="mailsent"></input>
	<input type="hidden" value="${sessionScope.loggedIn}" id="loggedin"></input>
	<input type="hidden" value="${loginhide}" id="loginhide">
	<input type="hidden" value="${invalidLogin}" id="invalidLogin">
	<input type="hidden" value="${loginDetails}" id="logindetails">

	<input type="hidden" value="${updateUser}" id="updateUser">
	<input type="hidden" value="${mode}" id="mode">

	<!-- Main COntents -->
	<div class="col-xs-12">
		<!-- widget starts -->
		<div class="col-xs-7 dummyfull">
			<div class="widget_wrapper">
				<!-------------------------------- MODAL Login --------------------------------------------->
				<div id="mylogin" class="white_content">
					<input type="hidden" value="${tokenerror}" id="loginpopup">
					<h3>Login Form</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('mylogin').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<span style="color: red; padding-left: 110px; font-weight: bold;">${invalidLogin}</span>
						<span style="color: red; padding-left: 40px; font-weight: bold;" id="invalidmessage"></span>
					<div style="padding: 20px 100px;">
						<spring:form  commandName="userLogin" onsubmit="return  getForm()"
							id="myLoginForm" class="form-horizontal" >
							<%-- <spring:form class="form-horizontal"> --%>
							<div class="form-group">
								<div class="col-sm-12">
									<spring:input path="email" class="form-control" id="hideEmail"
										placeholder="Email or username"></spring:input>
									<spring:errors style="color: red" path="email" id="emailId" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<spring:password path="password" class="form-control"
										id="hidePwd" placeholder="Password"></spring:password>
									<spring:errors style="color: red" path="password" id="pwd" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<button type="submit" class="btn btn-default btn-save">Log
										in</button>

								</div>
							</div>
						</spring:form>
					</div>
					<p class="center already_register_tab">
						You have forgotten your password, <a href="#"
							onclick="forgotPassWord()">click here</a>
					</p>
					<div style="padding: 0px 30px 15px 30px;">
						<b>Not yet registered</b>
						<div class="checkbox">
							<a href="javascript:void(0)"
								onclick="document.getElementById('mylogin').style.display='none';document.getElementById('my_registration').style.display='block';document.getElementById('fade').style.display='block'">
								<span class="glyphicon glyphicon-expand" aria-hidden="true"></span>
								Yes, I would like to register
							</a>
						</div>
						<ul class="register_note">
							<li>- Einmalige Registrierung für alle angeschlossen
								Anbieter</li>
							<li>- Übersciht aller vergangen und zukünftigen
								Reservierungen</li>
							<li>- Storno via one click</li>
							<li>- Sie entscheiden welcher Anieter Ihre E-mail Addresse
								Sehen kann</li>
							<li>- Hinweis bei Buchungüberlappungen</li>
						</ul>
					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-- ------------------------------------   END   ---------------------------------------------->
				<div id="myadminlogin" class="white_content">
					<input type="hidden" value="${errorDet}" id="errorDet">
					<h3>Admin Login</h3>
					<a href="#" class="close_popup" onclick="closeAdminLogPage()"> <span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<span style="color: red; padding-left: 110px; font-weight: bold;">${invalidServLogin}</span>
					 <span style="color: red; padding-left: 40px; font-weight: bold;" id="invalidLoginmessage"></span>
			<div style="padding: 20px 100px;">
						<spring:form  id="myAdminLoginForm" commandName="adminLogin"
							 class="form-horizontal" >
							<%-- <spring:form class="form-horizontal"> --%>
							<div class="form-group">
								<div class="col-sm-12">
									<spring:input path="email" class="form-control"
										id="emailOrUsernme" placeholder="Email or username"></spring:input>
									<spring:errors style="color: red" path="email" id="emailId" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<spring:password path="password" class="form-control"
										id="adminPwd" placeholder="Password"></spring:password>
									<spring:errors style="color: red" path="password" id="pwd" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<button type="submit" class="btn btn-default btn-save">Log
										in</button>

								</div>
							</div>
						</spring:form>
					</div>

					<p class="center already_register_tab">
						You have forgotten your password, <a href="javascript:void(0)"
							id="forgotAdminPopUp" onclick="forgotPassWord()">click here</a>

					</p>
					<div style="padding: 0px 30px 15px 30px;">
						<b>Not yet registered</b>
						<div class="checkbox">
							<a href="javascript:void(0)"
								onclick="document.getElementById('myadminlogin').style.display='none';document.getElementById('my_adminregistration').style.display='block';document.getElementById('fade').style.display='block'">
								<span class="glyphicon glyphicon-expand" aria-hidden="true"></span>
								Yes, I would like to register
							</a>
						</div>
						<ul class="register_note">
							<li>- Einmalige Registrierung f�r alle angeschlossen
								Anbieter</li>
							<li>- �bersciht aller vergangen und zuk�nftigen
								Reservierungen</li>
							<li>- Storno via one click</li>
							<li>- Sie entscheiden welcher Anieter Ihre E-mail Addresse
								Sehen kann</li>
							<!-- <li>-	Hinweis bei Buchung�berlappungen</li> -->
						</ul>
					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-- ------------------------------------   END of Admin Login  ---------------------------------------------->
				<!-------------------------------- ADMIN REGISTRATION MODAL --------------------------------------------->
				<div id="my_adminregistration" class="white_content"
					style="left: 10%; width: 80%;">
					<input type="hidden" value="${adminerrors}" id="adminerrors">
					<h3>Admin Registration</h3>
					<h4 class="center form-subtitle">Please Fill Up the Below
						Forms</h4>

					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('my_adminregistration').style.display='none';document.getElementById('fade').style.display='none';"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<span style="color: red; padding-left: 206px; font-weight: bold;">${admin_not_unique}</span>
						<span style="color: red; margin-left: -22px; font-weight: bold;">${admail_not_unique}</span>
						<spring:form  id="adminregform"
							class="form-horizontal" commandName="registerAdmin" onsubmit="return getAdminRegisterForm()">

							<!-- 	<form class="form-horizontal" id="regform"> -->
							<div class="form_scroller">
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Language</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="language"
											name="language" id="language" style="color:#000;"
											items="${languages}" onchange="hidelanguages()">
											<!--  <option value="Select Language">Select Language</option>
                                                                <option value="English">English</option>
                                                                <option value="Germany">Germany</option>
                                                                <option value="Deutch">Deutch</option> -->
										</spring:select>
										<spring:errors style="color: red" path="language"
											id="languages"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Title</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="title" name="title"
											id="title" items="${title}" onchange="hidetitle()">

										</spring:select>
										<spring:errors style="color:red" path="title" id="titles"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">First
										Name </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control"
											path="firstName" id="fname" name="fname"
											placeholder="First Name" onchange="hidename()"></spring:input>
										<spring:errors style="color: red" path="firstName"
											id="firstName"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Last
										Name </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control" path="lastName"
											id="lname" name="lname" placeholder="Last Name"
											onchange="hidelastname()"></spring:input>
										<spring:errors style="color: red" path="lastName"
											id="lastName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Street
										Name </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control" id="street"
											path="streetName" name="street" placeholder="Street Name"
											onchange="hidestreetname()"></spring:input>
										<spring:errors style="color: red" path="streetName"
											id="streetName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Postal
										Code </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control"
											path="postalCode" id="postcode" name="postcode"
											placeholder="Postal Code" onchange="hidepostalcode()"></spring:input>
										<spring:errors style="color: red" path="postalCode"
											id="postalcode"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">City</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control" path="city"
											id="City" name="City" placeholder="City"
											onchange="hidecity()"></spring:input>
										<spring:errors style="color: red" path="city" id="city"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Country</label>
									<div class="col-sm-9 col-xs-12">
										<spring:select class="form-control" path="country"
											name="countryid" id="countryid" onchange="hidecounty()"
											style="color:#000;" items="${countryList}">
										</spring:select>
										<spring:errors style="color: red" path="country" id="country"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Date
										Of Birth</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input path="dob" class="form-control" name="dob"
											placeholder="dd/mm/yyyy" onchange="hidedateofbirth()"
											id="datepick"></spring:input>

										<spring:errors style="color: red" path="dob" id="dateOfBirth"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Email</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="email" class="form-control" path="email"
											id="e_mail" name="e_mail" placeholder="Email Address"
											onchange="hideemailId()"></spring:input>
										<spring:errors style="color: red" path="email" id="email"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Contact</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control"
											path="phoneNumber" id="contact_no" name="contact_no"
											placeholder="Phone Number" onchange="hidecontactnumber()"></spring:input>
										<spring:errors style="color: red" path="phoneNumber"
											id="contactNumber"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">UserName</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input path="userName" class="form-control" id="uname"
											name="uname" placeholder="Username" onchange="hideusername()"></spring:input>

										<spring:errors style="color: red" path="userName"
											id="userName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Password</label>
									<div class="col-sm-9 col-xs-12">
										<spring:password path="password" class="form-control"
											showPassword="true" id="adminregpassword" placeholder="Password"
											onchange="hidepwd()"></spring:password>
										<spring:errors style="color: red" path="password" id="pwd"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Confirm
										Password </label>
									<div class="col-sm-9 col-xs-12">
										<spring:password path="confirmPassWord" class="form-control"
											showPassword="true" id="cpwd" name="cpwd"
											placeholder="Confirm Password"
											onchange="hideConfirmpassword()"></spring:password>
										<spring:errors style="color: red" path="confirmPassWord"
											id="confirmpassword"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Notify
										Period </label>
									<div class="col-sm-9 col-xs-12">
										<spring:select class="form-control"
											path="notificationDuration" name="notify" id="notify"
											onchange="hidenotify()" style="color:#000;"
											items="${notificationPeriod}">
											<!-- <option value="">Select Notification Period</option>
                                                                <option value="24 hrs">24 hrs</option>
                                                                <option value="48 hrs">48 hrs</option>
                                                                <option value="72 hrs">72 hrs</option> -->
										</spring:select>
										<spring:errors style="color: red" path="notificationDuration"
											id="notifyduration"></spring:errors>
									</div>
								</div>
								<spring:hidden path="isSP" value="Y" />
							</div>
							<div style="padding: 0px 40px;">
								<div class="form-group">
									<div class="col-md-offset-2">
										<button type="submit" class="btn btn-default btn-save"
											style="width: auto;">Complete Registration</button>
									</div>
								</div>
							</div>

						</spring:form>

					</div>

					<hr />
					<p style="padding: 0px 80px 15px 80px; font-size: 13px;">Durch
						klicken auf â€š Registrieung abschlieÃŸenâ€˜ stimmen sie
						den Nutzungbedinungen und der DatenschutzerklÃ¤rung von world of
						Reservation zu</p>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-- /End of Admin registration modal -->

				<!-- ============================= DELETE RESERVATION MODAL START =============================================-->
				<div id="deleteReservtn_popUp" class="white_content">
					<h3>Delete Reservation</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('deleteReservtn_popUp').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<form class="form-horizontal">

							<div class="reservation-data-section">

								<div class="col-xs-11">
									<span style="font-size: 16px; font-weight: 700;"
										id="deleteReservtnText"></span>
								</div>
							</div>

							<div class="clearfix"></div>
							<div style="padding: 0px 100px;"
								onclick="document.getElementById('deleteReservtn_popUp').style.display='none';document.getElementById('fade').style.display='none'"
								id="deleteAndCancelBtns"></div>
						</form>

					</div>
				</div>
				<div id="fade" class="black_overlay"></div>

<!-- ============================= CHANGE RESERVATION MODAL START =============================================-->
	<div id="changeReservtn_popUp" class="white_content">
			<h3>Change Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('changeReservtn_popUp').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					
					<div class="reservation-data-section">
					
						<div class="col-xs-11">
						<span style="font-size: 16px;font-weight: 700;" id="changeReservtnText"></span>
						</div>
					</div>
					
				<div class="clearfix"></div>
				   <div style="padding:0px 100px;" onclick = "document.getElementById('changeReservtn_popUp').style.display='none';document.getElementById('fade').style.display='none'" id="changeAndCancelBtns">
					
				  </div>
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!-- ============================= CHANGE RESERVATION MODAL END =============================================-->			
			



				<!-------------------------------- MODAL Forget password --------------------------------------------->
				<div id="forgetpass" class="white_content">
					<h3>Forget Password</h3>
					<p class="center">Please Provide your registered Email Address</p>
					<hr />
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('forgetpass').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 20px 100px;">
						<form class="form-horizontal" name="passreset" id="passreset"
							method="post">
							<div class="form-group">
								<div class="col-xs-12">
									<input type="email" class="form-control" name="e_mail"
										id="e_mail" placeholder="Email Address">
								</div>
							</div>

							<div class="form-group">
								<div class="col-xs-12">
									<button type="submit" class="btn btn-default btn-save"
										style="width: auto;">Reset Password</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-- ------------------------------------   END   ---------------------------------------------->
				<input type="hidden" value="${loginhide}" id="loginhide"> <input
					type="hidden" value="${invalidForgot}" id="invalidForgot">
				<div id="myforgot" class="white_content">


					<%-- <span style="color: red;padding-left: 110px;font-weight: bold;">${invalidLogin}</span> --%>

					<h3>Forgot Password</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myforgot').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					  <span style="color: red; padding-left: 40px; font-weight: bold;" id="invalidForgotmessage"></span>
     			<span style="color: red; padding-left: 110px; font-weight: bold;" id="forgorStatusMsg">${invalidForgot}</span>
    			 <div style="padding: 20px 100px;">
     				 <spring:form commandName="userLogin"
       							id="myForgotForm" class="form-horizontal" onsubmit="return getForgotPassForm()">

							<div class="form-group">
								<div class="col-sm-12">
									<spring:input path="email" class="form-control"
										id="inputEmail3" placeholder="Email(or)Username"
										onchange="hideemail()"></spring:input>
									<spring:errors style="color: red" path="email" id="emailId" />
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-12">
									<button type="submit" class="btn btn-default btn-save">SendPassword</button>

								</div>
							</div>
						</spring:form>
					</div>


				</div>

				<div id="fade" class="black_overlay"></div>
				<!-- ------------------------------------   END   ---------------------------------------------->
				<!-------------------------------- REGISTRATION MODAL --------------------------------------------->
				<div id="my_registration" class="white_content"
					style="left: 10%; width: 80%;">
					<input type="hidden" value="${errors}" id="headerpopup">
					<h3>Registration</h3>
					<h4 class="center form-subtitle">Please Fill Up the Below
						Forms</h4>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('my_registration').style.display='none';document.getElementById('fade').style.display='none';"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<span style="color: red; padding-left: 206px; font-weight: bold;">${user_not_unique}</span>
						<span style="color: red; margin-left: -22px; font-weight: bold;">${email_not_unique}</span>
						<span style="color: red; padding-left: 40px; font-weight: bold;" id="invalidRegmessage"></span>
						<spring:form  id="myRegForm" onsubmit="return getRegisterForm()"
							class="form-horizontal"  commandName="registerUser">

							<!-- 	<form class="form-horizontal" id="regform"> -->
							<div class="form_scroller">
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Language</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="language"
											name="language" id="language" style="color:#000;"
											items="${languages}" onchange="hidelanguages()">
											<!--  <option value="Select Language">Select Language</option>
                                                                <option value="English">English</option>
                                                                <option value="Germany">Germany</option>
                                                                <option value="Deutch">Deutch</option> -->
										</spring:select>
										<spring:errors style="color: red" path="language"
											id="languages"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Title</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="title" name="title"
											id="title" items="${title}" onchange="hidetitle()">

										</spring:select>
										<spring:errors style="color:red" path="title" id="titles"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">First
										Name </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control"
											path="firstName" id="fname" name="fname"
											placeholder="First Name" onchange="hidename()"></spring:input>
										<spring:errors style="color: red" path="firstName"
											id="firstName"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Last
										Name </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control" path="lastName"
											id="lname" name="lname" placeholder="Last Name"
											onchange="hidelastname()"></spring:input>
										<spring:errors style="color: red" path="lastName"
											id="lastName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Street
										Name </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control" id="street"
											path="streetName" name="street" placeholder="Street Name"
											onchange="hidestreetname()"></spring:input>
										<spring:errors style="color: red" path="streetName"
											id="streetName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Postal
										Code </label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control"
											path="postalCode" id="postcode" name="postcode"
											placeholder="Postal Code" onchange="hidepostalcode()"></spring:input>
										<spring:errors style="color: red" path="postalCode"
											id="postalcode"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">City</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control" path="city"
											id="City" name="City" placeholder="City"
											onchange="hidecity()"></spring:input>
										<spring:errors style="color: red" path="city" id="city"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Country</label>
									<div class="col-sm-9 col-xs-12">
										<spring:select class="form-control" path="country"
											name="countryid" id="countryid" onchange="hidecounty()"
											style="color:#000;" items="${countryList}">
										</spring:select>
										<spring:errors style="color: red" path="country" id="country"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Date
										Of Birth</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input path="dob" class="form-control" name="dob"
											placeholder="dd/mm/yyyy" onchange="hidedateofbirth()"
											id="datepicker"></spring:input>

										<spring:errors style="color: red" path="dob" id="dateOfBirth"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Email</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="email" class="form-control" path="email"
											id="e_mail" name="e_mail" placeholder="Email Address"
											onchange="hideemailId()"></spring:input>
										<spring:errors style="color: red" path="email" id="email"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Contact</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" class="form-control"
											path="phoneNumber" id="contact_no" name="contact_no"
											placeholder="Phone Number" onchange="hidecontactnumber()"></spring:input>
										<spring:errors style="color: red" path="phoneNumber"
											id="contactNumber"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">UserName</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input path="userName" class="form-control" id="uname"
											name="uname" placeholder="Username" onchange="hideusername()"></spring:input>

										<spring:errors style="color: red" path="userName"
											id="userName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Password</label>
									<div class="col-sm-9 col-xs-12">
										<spring:password path="password" class="form-control"
											showPassword="true" id="regpassword" placeholder="Password"
											onchange="hidepwd()"></spring:password>
										<spring:errors style="color: red" path="password" id="pwd"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Confirm
										Password </label>
									<div class="col-sm-9 col-xs-12">
										<spring:password path="confirmPassWord" class="form-control"
											showPassword="true" id="cpwd" name="cpwd"
											placeholder="Confirm Password"
											onchange="hideConfirmpassword()"></spring:password>
										<spring:errors style="color: red" path="confirmPassWord"
											id="confirmpassword"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Notify
										Period </label>
									<div class="col-sm-9 col-xs-12">
										<spring:select class="form-control"
											path="notificationDuration" name="notify" id="notify"
											onchange="hidenotify()" style="color:#000;"
											items="${notificationPeriod}">
											<!-- <option value="">Select Notification Period</option>
                                                                <option value="24 hrs">24 hrs</option>
                                                                <option value="48 hrs">48 hrs</option>
                                                                <option value="72 hrs">72 hrs</option> -->
										</spring:select>
										<spring:errors style="color: red" path="notificationDuration"
											id="notifyduration"></spring:errors>
									</div>
								</div>
							</div>
							<div style="padding: 0px 40px;">
								<div class="form-group">
									<div class="col-md-offset-2">
										<button type="submit" class="btn btn-default btn-save"
											style="width: auto;">Complete Registration</button>
									</div>
								</div>
							</div>

						</spring:form>

					</div>
					<hr />
					<p style="padding: 0px 80px 15px 80px; font-size: 13px;">Durch
						klicken auf ‚ Registrieung abschließen‘ stimmen sie den
						Nutzungbedinungen und der Datenschutzerklärung von world of
						Reservation zu</p>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-- /End registration modal -->
				<!-- Contact data Modal-->
				<!-------------------------------- MYCONTACT DATA MODAL --------------------------------------------->
				<div id="myquickreservation" class="white_content">
					<h3>Quick reservation</h3>
					<h4 class="center form-subtitle">Please Fill Up the Below
						Forms</h4>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myquickreservation').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<form class="form-horizontal">
							<div class="form_scroller">
								<div class="form-group">
									<div class="col-xs-12">
										<input type="text" class="form-control" id="inputEmail3"
											placeholder="First Name">
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<input type="text" class="form-control" id="inputEmail3"
											placeholder="Last Name">
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<input type="email" class="form-control" id="inputEmail3"
											placeholder="Email Address">
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<input type="text" class="form-control" id="inputEmail3"
											placeholder="Contact Number">
									</div>
								</div>

							</div>
							<div style="padding: 0px 100px;">
								<div class="form-group">
									<div class="col-xs-12">
										<a href="javascript:void(0)" class="btn btn-default btn-save"
											onclick="document.getElementById('myquickreservation').style.display='none'; document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">Proceed
											as Guest<br /> Data will not be saved.
										</a>

									</div>
								</div>
							</div>
						</form>
					</div>
					<p class="center already_register_tab">
						Already Registered, <a href="#" id="link_login">Login Here</a>
					</p>
					<div style="padding: 0px 30px 15px 30px;">
						<b>Not yet registered</b>
						<div class="checkbox">
							<a href="javascript:void(0)"
								onclick="document.getElementById('myquickreservation').style.display='none'; document.getElementById('my_registration').style.display='block';document.getElementById('fade').style.display='block'">
								<span class="glyphicon glyphicon-expand" aria-hidden="true"></span>
								Yes, I would like to register
							</a>
						</div>
						<ul class="register_note">
							<li>- Einmalige Registrierung für alle angeschlossen
								Anbieter</li>
							<li>- Übersciht aller vergangen und zukünftigen
								Reservierungen</li>
							<li>- Storno via one click</li>
							<li>- Sie entscheiden welcher Anieter Ihre E-mail Addresse
								Sehen kann</li>
							<li>- Hinweis bei Buchungüberlappungen</li>
						</ul>
					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-- /End contact data modal -->
				<!-------------------------------- MYRESERVATION DATA MODAL --------------------------------------------->
				<div id="myreservation" class="white_content">
					<h3>Reservation</h3>
					<a href="javascript:void(0)" class="close_popup timeOutDisable"
						onclick="document.getElementById('myreservation').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<form class="form-horizontal" id="userReservationForm" onsubmit="return saveTheUserReservation()">
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
									<p>
										${sessionScope.event.eventOrgName} <br />
										${sessionScope.event.eventOrgAddress}<br />
										${sessionScope.event.eventOrgPhoneNumber}
									</p>
								</div>
							</div>
							<div class="reservation-data-section" id="selectSeats"></div>
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
									<p>
										${sessionScope.userId.userName}<br />
										${sessionScope.userId.email}<br />
										${sessionScope.userId.contactNumber}
									</p>
								</div>
							</div>
							<div class="col-xs-12" style="padding: 20px 20px 6px 20px;">
								<div class="form-group">
									<div class="col-xs-1">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									</div>
									<div class="col-xs-11">
										<textarea class="form-control" id="notes" rows="1" maxlength="100"></textarea>
									</div>
								</div>
								<p style="padding-left: 40px;">
									<input type="hidden" id="emailShare" value="N" />
									<button id="share-email" type="button"
										class="btn btn-default btn-share" data-toggle="tooltip"
										data-placement="top" title="Share Email Address"
										onclick="emailIsShared()">
										<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
									</button>
									&nbsp;&nbsp; Agree to share Email address with service
									provider.
								</p>
							</div>
							<!--ends col-xs-12 -->
							<div class="clearfix"></div>
							<div class="button_padding">
								<div class="form-group">
									<div class="col-xs-12">
										<button type="submit" class="btn btn-default btn-save"
											>Reserve</button>
									</div>
								</div>
							</div>
						</form>
						<div id="hideTimer">
							<p class="center"
								style="padding: 10px 0px 0px 0px; margin-bottom: 2px;">
								Reservation Process Continues for next <span id="log">60</span>
								Secs
							</p>
						</div>
						<div id="flash3" class="center"></div>

					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-------------------------------- RESERVATION CONFIRMATION DATA MODAL --------------------------------------------->
				<div id="myreservation_confirm" class="white_content">
					<h3>Reservation Confirmation</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="resetPage()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<form class="form-horizontal">
							<div class="reservation-data-section" id="refConfirm"></div>
							<div class="reservation-data-section">
								<div class="col-xs-4" style="text-align: right;">
									Sponsor By <img src="images/pepsi.jpg"
										class="img-responsive hotel_logo"
										style="margin: -25px 250px 0px; height: 50px; width: 130px;" />
								</div>
								<div class="col-xs-8"></div>
							</div>
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
									<p>
										${sessionScope.event.eventOrgName} <br />
										${sessionScope.event.eventOrgAddress}<br />
										${sessionScope.event.eventOrgPhoneNumber}
									</p>
								</div>
							</div>
							<div class="reservation-data-section" id="timeConfirm"></div>
							<div class="reservation-data-section" id="userConfirm"></div>

							<div class="clearfix"></div>
							<div style="padding: 20px 100px;">
								<div class="form-group">
									<a href="#" class="btn btn-default ">I</a> <a href="#"
										class="btn btn-default">F</a> <a href="#"
										class="btn btn-default ">F</a> <a href="exportEventPDF.htm"
										class="btn btn-default">Export</a>

								</div>
							</div>
						</form>

					</div>
				</div>
				<div id="fade" class="black_overlay"></div>


				<!-------------------------------- -------------MYRESERVATION GUESt DATA MODAL --------------------------------------------->
				<div id="myreservation_guest" class="white_content">
					<h3>Reservation</h3>
					<a href="javascript:void(0)" class="close_popup timeOutDisable"
						onclick="document.getElementById('myreservation_guest').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<div class="ajax_container_inner">
							<div id="loader"></div>
							<form class="form-horizontal" id="guestReservationForm" onsubmit="return saveTheGuestReservation()">
								<div class="reservation-data-section">
									<div class="col-xs-1">
										<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
									</div>
									<div class="col-xs-11">
										<p>
											${sessionScope.event.eventOrgName} <br />
											${sessionScope.event.eventOrgAddress}<br />
											${sessionScope.event.eventOrgPhoneNumber}
										</p>
									</div>
								</div>
								<div class="reservation-data-section" id="selectSeatsGuest">
									<!-- <div class="col-xs-1">
										<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
										</div>
										<div class="col-xs-11">
										<p>
										Donnerstag 14 julie, 2014 - 17:00<br/>
										Donnerstag 14 julie, 2014 - 21:00
										</p>
										</div>
											<div class="col-xs-1">
										<span class="glyphicons glyphicon-times" aria-hidden="true">#</span>
										</div>
										<div class="col-xs-11">
										<p>Bunktertour Heinwald</p>
										<select name="" class="form-control">
											<option value="">1 Platze</option>
											<option value="">2 Platze</option>
											<option value="">2 Platze + 1 Waitlist</option>
											<option value="">2 Platze + 2 Waitlist</option>
											<option value="">2 Platze + 3 Waitlist</option>
											<option value="">2 Platze + 4 Waitlist</option>
										</select>
										</div> -->
								</div>

								<div class="reservation-data-section">
									<div class="col-xs-1">
										<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
									</div>
									<div class="col-xs-11">
										<input type="text" class="form-control margin-bottom-10" name="userName"
											placeholder="User Name" id="guestUser">
										<div id="userNameStatusDiv" align="center">
											<span id="useNameStatus"
												style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
										</div>
										<input type="email" class="form-control margin-bottom-10" name="email"
											placeholder="Email" id="guestEmail">
										<div id="EmailStatusDiv" align="center">
											<span id="EmailStatus"
												style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
										</div>
										<input type="text" class="form-control margin-bottom-10" name="phone"
											placeholder="Phone" id="guestPhone">
										<div id="PhoneStatusDiv" align="center">
											<span id="PhoneStatus"
												style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
										</div>
									</div>
								</div>
								<div class="col-xs-12" style="padding: 20px 20px 6px 20px;">
									<div class="form-group">
										<div class="col-xs-1">
											<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										</div>
										<div class="col-xs-11">
											<textarea class="form-control" name="notes" id="guestNotes" rows="1" maxlength="100"></textarea>
										</div>
									</div>

								</div>
								<div class="clearfix"></div>
								<div style="padding: 0px 100px;" class="button_padding">
									<div class="form-group">
										<div class="col-xs-12">
											<button type="submit" class="btn btn-default btn-save"
												id="reserveSeats">Reserve
												Now</button>
										</div>
									</div>
								</div>
								
							</form>
						</div>
						<div id="hideTimer1">
							<p class="center"
								style="padding: 10px 0px 0px 0px; margin-bottom: 2px;">
								Reservation Process Continues for next <span id="log1">60</span>
								Secs
							</p>
						</div>
						<div id="flash4" class="center"></div>

					</div>
				</div>

				<div id="fade" class="black_overlay"></div>


				<!-------------------------------- BLUE MYRESERVATION DATA MODAL --------------------------------------------->
				<div id="blue_myreservation" class="white_content">
					<h3>My Reservation</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('blue_myreservation').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<form class="form-horizontal">
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
									<p>
										${sessionScope.event.eventOrgName} <br />
										${sessionScope.event.eventOrgAddress}<br />
										${sessionScope.event.eventOrgPhoneNumber}
									</p>
								</div>
							</div>
							<div class="reservation-data-section" id="blueSeats">
								<!-- <div class="col-xs-1">
										<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
										</div>
										<div class="col-xs-11">
										<p>
										Donnerstag 14 julie, 2014 - 17:00<br/>
										Donnerstag 14 julie, 2014 - 21:00
										</p>
										</div>
											<div class="col-xs-1">
										<span class="glyphicons glyphicon-times" aria-hidden="true">#</span>
										</div>
										<div class="col-xs-11">
										<p>Bunktertour Heinwald</p>
										<input type="text" id="showSeats" />
										<select name="" class="form-control">
											<option value="">1 Platze</option>
											<option value="">2 Platze</option>
											<option value="">2 Platze + 1 Waitlist</option>
											<option value="">2 Platze + 2 Waitlist</option>
											<option value="">2 Platze + 3 Waitlist</option>
											<option value="">2 Platze + 4 Waitlist</option>
										</select>
										</div> -->
							</div>
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
									<p>
										${sessionScope.userId.userName}<br />
										${sessionScope.userId.email}<br />
										+${sessionScope.userId.phoneNumber}
									</p>
								</div>
							</div>
							<div class="col-xs-12" style="padding: 20px 20px 6px 20px;"
								id="blueNote">
								<div class="form-group">
									<div class="col-xs-1">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									</div>
									<div class="col-xs-11">
										<textarea class="form-control" id="inputEmail3" rows="1"></textarea>
									</div>
								</div>
							</div>
							<!--ends col-xs-12 -->
							<div class="clearfix"></div>
						</form>

					</div>
				</div>
				<div id="fade" class="black_overlay"></div>


				<!--------------------------------  RESERVATION OPTION  DATA MODAL --------------------------------------------->
				<div id="myreservation_options" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>Reservation</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myreservation_options').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<form class="form-horizontal">
						<div class="reservation-data-section">
							<h5 class="center">Reservation System</h5>
							<div class="line clearfix"></div>
							<div class="options_wrapper">
								<div class="col-xs-3"></div>
								<div class="col-xs-3 col-xs-3 col-xs-12">

									<a href="javascript:void(0)"
										class="btn btn-default btn-lg btn-green" onclick="showLogin()"
										style="float: right;">Login</a>
								</div>
								<div class="col-xs-3 col-xs-3  col-xs-12">
									<a href="javascript:void(0)"
										class="btn btn-default btn-lg btn-green"
										onclick="guestPopup()">Quick Reservation</a>
								</div>
								<div class="col-xs-3"></div>
							</div>
						</div>
					</form>
				</div>
				<div id="fade" class="black_overlay"></div>


				<!-------------------------------- MYRESERVATION DETAILS DATA MODAL --------------------------------------------->
				<div id="myeventreservation_details" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>My Reservation Details</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="resetPage()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>

					<div id="statusDiv" align="center">
						<span id="statusInformtn"
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
					</div>

					<div class="user_data_wrapper" style="padding: 0px">

						<form class="form-horizontal" id="reservationHistory"></form>
					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-------------------------------- WHITELIST DETAILS DATA MODAL --------------------------------------------->
				<div id="mywhite_list" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>My White List Category</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('mywhite_list').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form action="getEventWhiteListDet.htm" class="form-horizontal"
							id="mywhite_list">
							<div class="reservation-data-section">

								<div id="whitelistdetails"></div>
								<div id="startDate"></div>
							</div>

						</form>

					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-------------------------------- BLACK LIST DETAILS DATA MODAL --------------------------------------------->
				<div id="myblack_list" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>My Black List Category</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myblack_list').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form action="getBlackListDet.htm" class="form-horizontal"
							id="myblack_list">
							<div class="reservation-data-section">
								<div id="blacklistdetails"></div>
								<div id="stratDate"></div>


							</div>

						</form>

					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-------------------------------- EMAIL LIST DETAILS DATA MODAL --------------------------------------------->
				<div id="myemail_list" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>My Email List</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myemail_list').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form class="form-horizontal">
							<div id="emailreservation"></div>
						</form>

					</div>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!--------------------------------  RESERVATION OPTION  DATA MODAL --------------------------------------------->
				<div id="myreservation_options" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>Reservation</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myreservation_options').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<form class="form-horizontal">
						<div class="reservation-data-section">
							<h5 class="center">Reservation System</h5>
							<div class="line clearfix"></div>
							<div class="options_wrapper">
								<div class="col-xs-3"></div>
								<div class="col-xs-3 col-xs-3 col-xs-12">

									<a href="javascript:void(0)"
										class="btn btn-default btn-lg btn-green"
										onclick="document.getElementById('myreservation_options').style.display='none';document.getElementById('mylogin').style.display='block';document.getElementById('fade').style.display='block'"
										style="float: right;">Login</a>
								</div>
								<div class="col-xs-3 col-xs-3  col-xs-12">
									<a href="javascript:void(0)"
										class="btn btn-default btn-lg btn-green"
										onclick="document.getElementById('myreservation_options').style.display='none';document.getElementById('myquickreservation').style.display='block';document.getElementById('fade').style.display='block'">Quick
										Reservation</a>
								</div>
								<div class="col-xs-3"></div>
							</div>
						</div>
					</form>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-------------------------------- Hotel Details DATA MODAL --------------------------------------------->
				<div id="hotel_details" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>Contact Details</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('hotel_details').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<form class="form-horizontal">
						<div class="reservation-data-section">
							<div class="col-xs-10 col-xs-10">
								<table>
									<div id="addressdetails"></div>
								</table>

								<core:if test="${event ne null}">
									<iframe
										src="https://www.google.com/maps/embed/v1/place?key=AIzaSyCqOFwiSrw2ZtqU-523kQ2ftKHpi2tSDCc
          					  						&q=${event.eventOrgName}, ${event.eventOrgAddress} ${sessionScope.event.eventOrgPostalCode} ${sessionScope.event.eventOrgCity}"
										width="120%" height="230" frameborder="0"
										style="border: 0; margin: 10px 0px 0px;"></iframe>
								</core:if>
							</div>

							<div class="col-xs-2">
								<button class="btn btn-default btn-success" type="button" onclick="downloadLocationInfo()">Download</button>
								<button class="btn btn-default" style="margin-top: 10px;">Timings</button>
							</div>
							<div class="map" id="map" style="width: 100%; height: 250px;"></div>
						</div>
					</form>
				</div>
				<div id="fade" class="black_overlay"></div>
				<!-------------------------------- MY PROFILE DETAILS DATA MODAL --------------------------------------------->
				<div id="myprofile" class="white_content" style="left:10%;width:80%;">
			<h3>My Profile</h3>
			<span style="color: red; padding-left: 40px; font-weight: bold;" id="invalidUpdatemessage"></span>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myprofile').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<form  id="myProfileForm" class="form-horizontal" onsubmit="return getMyProfileForm()">
						<div class="reservation-data-section">
						<h5>My Profile Details</h5>
				<div class="profile_scroller">
							<div id="myProfileFromAjax"></div>
					</div>
						<div class="line"></div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label"></label>
							<div class="col-xs-9">
							  <button type="submit" class="btn btn-default btn-save" style="width:auto;" >Update</button>
							</div>
						</div>
						<!--ends-->				
					</form>
				</div>
			</div>
			<div id="fade" class="black_overlay"></div>

			<!-- =========================================  Main html code body=============================================================================-->
			<div class="col-xs-3 hotel_title nopadding">
				<img src="images/logo1.png" class="img-responsive hotel_logo"
					style="margin-bottom: 5px;" />
			</div>
			<div class="col-xs-6" style="padding: 15px 20px 20px 35px;">
				<core:if test="${event ne null}">
					<b> ${event.eventOrgName}</b>
					<br />${event.eventOrgAddress} ${sessionScope.event.eventOrgPostalCode} ${sessionScope.event.eventOrgCity}
                                   
                                    </core:if>
			</div>
			<div class="col-xs-3" style="padding: 35px 0px 0px;">
				<div class="iconset">
					<a href="javascript:void(0)" class="btn-group"
						onclick="getEventDetails()" class="btn-blank"><img
						src="images/more-24.png" /></a>
					<!-- Single button -->
					<div class="btn-group">
						<button type="button"
							class="btn btn-default dropdown-toggle btn-blank"
							data-toggle="dropdown" aria-expanded="false">
							<img src="images/germany.png" />
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><img src="images/english.png"
									style="padding-right: 6px;" />- English</a></li>
						</ul>
					</div>
					<!--ends-->

					<div class="btn-group">
						<button type="button"
							class="btn btn-default dropdown-toggle btn-blank"
							data-toggle="dropdown" aria-expanded="false" id="greenuser">
							<img src="images/green-user.png" />
						</button>
						<ul class="dropdown-menu" role="menu" style="left: -100px;">
							<li><a href="javascript:void(0)" onclick="getUser()">My
									Profile</a></li>
							<li><a href="javascript:void(0)"
								onclick="getMyReservations()">My Reservations</a></li>
							<li><a href="javascript:void(0)" onclick="getWhiteList()"
								class="circle">White List Category</a></li>
							<li><a href="javascript:void(0)" onclick="getBlackList()"
								class="circle">Black List Category</a></li>
							<li><a href="javascript:void(0)" onclick="getEmailVisible()"
								class="circle">E-mail Visible </a></li>
							<div class="divider"></div>
							<li><a href="logout.htm">Logout</a></li>
						</ul>
					</div>
					<!--ends-->

					<!-- Single button -->
					<div class="btn-group">
						<button type="button"
							class="btn btn-default dropdown-toggle btn-blank"
							data-toggle="dropdown" aria-expanded="false" id="blackuser">
							<img src="images/user.png" />
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="javascript:void(0)"
								onclick="document.getElementById('mylogin').style.display='block';document.getElementById('fade').style.display='block'">Login</a></li>
							<li><a href="javascript:void(0)"
								onclick="document.getElementById('my_registration').style.display='block';document.getElementById('fade').style.display='block'">Registration</a></li>
							<li><a href="javascript:void(0)"
								onclick="document.getElementById('myquickreservation').style.display='block';document.getElementById('fade').style.display='block'">As
									Guest</a></li>
						</ul>
					</div>
					<!--ends-->
					<!-- Single button -->

					<a class="btn-blank"><img src="images/fullscreen.png" id="fs" /></a>
				</div>
			</div>
			<div class="clearfix"></div>

			<div class="clearfix"></div>
			<div class="col-xs-2 nopadding">
				<button type="button" class="btn btn-default btn-current-date hiddenDateButton"
					ondblclick="resetPage()">
					<span id="dateButton">
						<!-- 06-06-2015 -->
					</span>
				</button>
			</div>
			<div class="col-xs-8">
				<select class="selectpicker form-control" name="" id="selectTour"
					onchange="changeEvent()">

				</select>
			</div>

			<div class="col-xs-2 nopadding">
				<input type="number" min="0" placeholder="Seats"
					class="form-control" id="seatCount" onchange="countChange()" />
			</div>
			<div class="clearfix"></div>
			<div class="ajax_container">
				<div class="ajax_loader"></div>
				<div class="col-xs-12 nomargin_lf_rt date_wrapper">
					<div id="thumbcarousel1" class="carousel slide"
						data-ride="carousel">
						<!-- Wrapper for slides -->
						<div class="carousel-caption1" id="primaryDates"></div>
					</div>
					<!-- Controls -->
					<a class="left carousel-control" href="#" role="button"
						data-slide="prev"> <span
						class="glyphicon glyphicon-triangle-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a> <a class="right carousel-control" href="#" role="button"
						data-slide="next"> <span
						class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
				<!-- ends date list -->
				<!-- Object or week list and data -->
				<div class="clearfix"></div>
				<div class="col-xs-12 nomargin info_wrapper">
					<div class="timeline timeline_object" id="timeScale"></div>
					<div class="ajax_container_inner">
						<div class="ajax_loader_inner"></div>
						<div id="thumbcarousel2" class="carousel carousel_info slide"
							data-ride="carousel">
							<!--input type="text"  class="no_of_cols" value="3"/-->
							<!-- Wrapper for slides -->
							<div class="carousel-inner" role="listbox" id="eventWidget">

							</div>
						</div>

						<!-- Controls -->
						<a class="myleft carousel-control" href="#thumbcarousel2"
							role="button" data-slide="prev"> <span
							class="glyphicon glyphicon-triangle-left glyphicon glyphicon-triangle-myleft"
							aria-hidden="true"></span> <span class="sr-only">Previous</span>
						</a> <a class="myright carousel-control" href="#thumbcarousel2"
							role="button" data-slide="next"> <span
							class="glyphicon glyphicon-triangle-right glyphicon glyphicon-triangle-myright"
							aria-hidden="true"></span> <span class="sr-only">Next</span>
						</a>
					</div>

				</div>
			</div>
			<!-- ends-->
		</div>


		<ul class="footer_menu">
			<li><a href="#">AGB</a></li>
			<li><a href="#">Impressum</a></li>
			<li><a href="javascript:void(0)"
				onclick="getServiceProviderLogin()"
				style="font-style: italic; color: cornflowerblue;">Admin</a></li>
			<li><a href="#">Über Reservat</a></li>
			<li><a href="#">Help</a></li>
		</ul>
	</div>
	</div>
	<!-- ends-->
	<!--Main COntent ends -->
	</div>
</body>
</html>
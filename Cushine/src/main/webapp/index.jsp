<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Cushina</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" href="css/non-responsive.css">
<link rel="stylesheet" href="css/datepicker.css">

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script type="text/javascript" src="js/customjs.js"></script>
<script type="text/javascript" src="js/date.js"></script>
<script src="js/bootstrap-datepicker.js"></script>


</head>
<!-- ----------------------------------- Custom Popup Divs------------------------------------- -->
<div id="logoutSuccessPopup" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Logout Success!</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('logoutSuccessPopup').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="logoutSuccessPopupRed" style="color: red; font-weight: bold;"></span>
		<span id="logoutSuccessPopupGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="isTokenValidAlready" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Already active!</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('isTokenValidAlready').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="isTokenValidAlreadyRed" style="color: red; font-weight: bold;"></span>
		<span id="isTokenValidAlreadyGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="isTokenValid" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Account activated</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('isTokenValid').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="isTokenValidRed" style="color: red; font-weight: bold;"></span>
		<span id="isTokenValidGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="isTokenInValid" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Activation link expired!</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('isTokenInValid').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="isTokenInValidRed" style="color: red; font-weight: bold;"></span>
		<span id="isTokenInValidGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="mailIsSent" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Activation link expired!</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('mailIsSent').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="mailIsSentRed" style="color: red; font-weight: bold;"></span>
		<span id="mailIsSentGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="tokenNotFound" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Outdated link!</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('tokenNotFound').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="tokenNotFoundRed" style="color: red; font-weight: bold;"></span>
		<span id="tokenNotFoundGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<!-- ------------------------------------End of Custom Popup Divs------------------------------>
<body>
<script>
	$(document).ready(function() {		
		<%
		if(request.getParameter("logoutsuccess") != null) {
			%>
			$("#logoutSuccessPopupGreen").empty();
			$("#logoutSuccessPopupGreen").append("You have logged out of your account successfully");
			$("#logoutSuccessPopup").show();
		<% }
		else if(request.getParameter("isTokenValidAlready") != null) {
		%>
			$("#isTokenValidAlreadyGreen").empty();
			$("#isTokenValidAlreadyGreen").append("Your account has already been activated. Please login using "
					+ "your login credentials.");
			$("#isTokenValidAlready").show();
		<% }
		
		else if(request.getParameter("isTokenValid") != null) {
		%>
			$("#isTokenValidGreen").empty();
			$("#isTokenValidGreen").append("Your account is successfully activated!");
			$("#isTokenValid").show();
		<% }	
		else if(request.getParameter("isTokenInValid") != null) {
			String token = request.getParameter("token");
			pageContext.setAttribute("token", token);
		%>
			$("#isTokenInValidRed").empty();
			$("#isTokenInValidRed").append("Your activation link is expired!"
					+ "<br / >"
					+ "<a href=\"resendMail.htm?token=${token}\">Resend Activation Link </a>");			
			$("#isTokenInValid").show();
		<% }
		else if(request.getParameter("mail_sent") != null) {
		%>
			$("#mailIsSentGreen").empty();
			$("#mailIsSentGreen").append("Your new activation link is sent to your mail!");
			$("#mailIsSent").show();
		<% }
		else if(request.getParameter("tokenNotFound") != null) {
		%>
			$("#tokenNotFoundRed").empty();
			$("#tokenNotFoundRed").append("You have clicked an outdated link!");
			$("#tokenNotFound").show();
		<% }
		%>
	});
	
</script>

<h1><font color="green">Hotel reservation</font></h1>
<h2><font color="pink">Please select hotel from below table</font></h2>
<table bgcolor="cyan" border="1px">
	<tr>
		<td>Hotel Name</td>
		<td>Hotel address</td>
		<td>phone</td>
	</tr>
	<tr bgcolor="pink">
		<td><font color="red"><a href="pickHotel.htm?name=Hotel Alpha">Hotel Alpha</a></font></td>
		<td><font color="red"><a href="pickHotel.htm?name=Hotel Alpha">Tennisclub herchiu c.v,  Munchen</a></font></td>
		<td><font color="red"><a href="pickHotel.htm?name=Hotel Alpha">55-0086</a></font></td>
	</tr>
	
	<tr bgcolor="pink">
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Krishna">Hotel Taj Krishna</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Krishna">Tennisclub , Frankfurt</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Krishna">55-2386</a></font></td>
	</tr>
	
	<!-- <tr bgcolor="pink">
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Kakathiya">Hotel Kakathiya</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Kakathiya">6 -3 -1187, Begumpet, Hyderabad</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Kakathiya">56-0016</a></font></td>
	</tr>
	
	<tr bgcolor="pink">
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Banjara">Hotel Taj Banjara</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Banjara">Road No.1, Banjara Hills, Hyderabad</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Banjara">406-666-9999</a></font></td>
	</tr> -->
</table>
<h1><font color="green">Event reservation</font></h1>
<h2><font color="pink">Please select EventOrganizer from below table</font></h2>
<table bgcolor="cyan" border="1px">
	<tr>
		<td>EventOrganization Name</td>
		<td> Address</td>
		<td>phone</td>
	</tr>
	<tr bgcolor="pink">
		<td><font color="red"><a href="pickEventOrganizer.htm?name=The Pride Hotel">The Pride Hotel</a></font></td>
		<td><font color="red"><a href="pickEventOrganizer.htm?name=The Pride Hotel">Tennisclub herchiu c.v,  Munchen</a></font></td>
		<td><font color="red"><a href="pickEventOrganizer.htm?name=The Pride Hotel">55-0086</a></font></td>
	</tr>
	
	<tr bgcolor="pink">
		<td><font color="green"><a href="pickEventOrganizer.htm?name=Hotel Swagath">Hotel Swagath</a></font></td>
		<td><font color="green"><a href="pickEventOrganizer.htm?name=Hotel Swagath">Tennisclub , Frankfurt</a></font></td>
		<td><font color="green"><a href="pickEventOrganizer.htm?name=Hotel Swagath">55-2386</a></font></td>
	</tr>
	
	<!-- <tr bgcolor="pink">
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Kakathiya">Hotel Kakathiya</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Kakathiya">6 -3 -1187, Begumpet, Hyderabad</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Kakathiya">56-0016</a></font></td>
	</tr>
	
	<tr bgcolor="pink">
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Banjara">Hotel Taj Banjara</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Banjara">Road No.1, Banjara Hills, Hyderabad</a></font></td>
		<td><font color="green"><a href="pickHotel.htm?name=Hotel Taj Banjara">406-666-9999</a></font></td>
	</tr> -->
</table>
</body>
</html>
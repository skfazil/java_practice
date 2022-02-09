<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="tag"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Hotel Reservation</title>	
		<meta name="viewport" content="width=700">	
		<!-- media-queries.js (fallback) -->
		<!--[if lt IE 9]>
			<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>			
		<![endif]-->
		<!-- html5.js -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	    <link rel="stylesheet" href="css/bootstrap.min.css"/>
		<link href="css/style.css" rel="stylesheet">
		<!--link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'-->
		<link rel="stylesheet" href="css/non-responsive.css">
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	   <script type="text/javascript" src="js/bootstrap.min.js"></script>
	   <script type="text/javascript" src="js/jquery.validate.js"></script>
	    <!-- Google Map API Key Source -->
		<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<!-- Google Map  Source -->
		<script type="text/javascript" src="js/gmaps.js"></script>
	   <script type="text/javascript" src="js/customjs.js"></script>
	   <style type="text/css">
			#datepicker {
				background: url(../images/cal.png) right no-repeat;
				background-position: 290px;
			}
			
			.green {
				color: #b5e51d !important;
				font-weight: bold;
				text-shadow: 0px 0px 0px #444;
			}		
			.gray {
				color: #808080 !important;
				font-weight: bold;
				text-shadow: 0px 0px 0px #444;
				
			}
			.current_dt{
			color: #FF0000 !important;
				font-weight: bold;
				text-shadow: 0px 0px 0px #444;
				
			}
			
		</style>
		<script type="text/javascript">
		   
	  	 $(document).ready(function(){
	   		
	   		$('#noOfDays').change(function(){
	   			var category = $('#category').val();
	   			var roomNo = $('#roomNo').val();
	   			var noOfDays  = $('#noOfDays').val();
	   			if(category != 0 && roomNo != 0 && noOfDays != 0){
	   				document.location.href="getRoomsAvailByDayCount.htm?category="+category+"&roomNo="+roomNo+"&noOfDays="+noOfDays;
	   			}
	   		});
	   		
	   		$('#roomNo').change(function(){
          		 var selectedCategory = $("#category").val();
            	 var currDate = $('#currDate').val();
          		 var roomNo = $('#roomNo').val();
              	if(selectedCategory != 0 && currDate != '' && roomNo != 0){
              		document.location.href="reservationByRoom.htm?cat="+selectedCategory+"&roomNo="+roomNo;
              	}
          	});
	   		
	   		var loggedin = $("#loggedin").val();
	         if(loggedin != '') {
		         $('#blackuser').hide();
		         $('#greenuser').show();
	         }else {
		       
		         $('#blackuser').show();
		         $('#greenuser').hide();
	         }
		}); 
	  	 
	  	function getRooms(){
        	  var selectedCategory = $("#category").val();
        	  var currDate = $('#currDate').val();
        	  if(selectedCategory != 0 && currDate != ''){
        			document.location.href = "roomAvailInfoByCategoryType.htm?type="+selectedCategory+"&currDarte="+currDate;
           	}
          }
	  	
	   </script>
		
	   </head>
	   <body style="margin:20px 0px;">
	   <input type="hidden" value="${sessionScope.loggedIn}" id="loggedin"></input>
<!-- Main COntents -->
<div class="col-xs-12">
	<!-- widget starts -->
	<div class="col-xs-7 dummyfull" style="width:767px;">
		<div class="widget_wrapper">
		<!-------------------------------- MODAL Login --------------------------------------------->
			<div id="mylogin" class="white_content">
			<h3>Login Form</h3>

			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('mylogin').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:20px 100px;">
				<form class="form-horizontal">
				  <div class="form-group">
					<div class="col-xs-12">
					  <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
					</div>
				  </div>
				    <div class="form-group">
					<div class="col-xs-12">
					  <input type="email" class="form-control" id="inputEmail3" placeholder="Password">
					</div>
				  </div>
				  <div class="form-group">
					<div class="col-xs-12">
					  <button type="submit" class="btn btn-default btn-save">Log in</button>
					</div>
				  </div>
				</form>
			</div>
			<p class="center already_register_tab">
			Sie haben Ihr passwort vergessen, <a href="#">klicken sie hier</a>
			</p>
					<div style="padding:0px 30px 15px 30px;">
					<b>Not yet registered</b>
						<div class="checkbox">
							<a href = "javascript:void(0)" onclick = "document.getElementById('mylogin').style.display='none';document.getElementById('my_registration').style.display='block';document.getElementById('fade').style.display='block'">
							<span class="glyphicon glyphicon-expand" aria-hidden="true"></span>  Yes, I would like to register</a>
						</div>
							<ul class="register_note">
								<li>-	Einmalige Registrierung für alle angeschlossen Anbieter</li>
								<li>-	Übersciht aller vergangen und zukünftigen Reservierungen </li>
								<li>-	Storno via one click</li>
								<li>-	Sie entscheiden welcher Anieter Ihre E-mail Addresse Sehen kann</li>
								<li>-	Hinweis bei Buchungüberlappungen</li>
							</ul>
					</div>
			</div>
			<div id="fade" class="black_overlay"></div>
		<!-- ------------------------------------   END   ---------------------------------------------->
			<!-------------------------------- REGISTRATION MODAL --------------------------------------------->
			<div id="my_registration" class="white_content" style="left: 10%; width: 80%;">
			<h3>Registration</h3>
			<h4 class="center form-subtitle">Please Fill Up the Below Forms</h4>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('my_registration').style.display='none';document.getElementById('fade').style.display='none';"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal" id="regform">
					<div class="form_scroller">
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Language</label>
							<div class="col-xs-9">
							  <select class="form-control" name="language" id="language" style="color:#000;">
								<option value ="">Select Language</option>
								<option value ="0">English</option>
								<option value ="0">Germany</option>
								<option value ="0">Deutch</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Title</label>
							<div class="col-xs-9">
							  <select class="form-control" name="language">
								<option value ="0">Select Title</option>
								<option value ="0">Mr</option>
								<option value ="0">Miss</option>
								<option value ="0">Mrs</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">First Name</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="fname" name="fname" placeholder="First Name">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Last Name</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="lname" name="lname" placeholder="Last Name">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Street Name</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="street" name="street" placeholder="Street Name">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Postal Code</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="postcode" name="postcode" placeholder="Postal Code">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">City</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="City" name="City" placeholder="City">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Country</label>
							<div class="col-xs-9 col-xs-12">
							 <select class="form-control" name="country" id="country">
								<option value ="0">Select Country</option>
								<option value ="0">USA</option>
								<option value ="0">UK</option>
								<option value ="0">Germany</option>
								<option value ="0">France</option>
								<option value ="0">Spain</option>
								<option value ="0">Others</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Date Of Birth</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="dob" name="dob" placeholder="Date Of Birth">
							</div>
						</div>
						
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Email</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="email" class="form-control" id="e_mail" name="e_mail" placeholder="Email Address">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Contact</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="contact_no" name="contact_no" placeholder="Contact Number">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Username</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="text" class="form-control" id="uname" name="uname" placeholder="Username">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Password</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="password" class="form-control" id="pwd" name="pwd" placeholder="Password">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Confirm Password</label>
							<div class="col-xs-9 col-xs-12">
							  <input type="password" class="form-control" id="cpwd" name="cpwd" placeholder="Confirm Password">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-xs-2 control-label">Notify Period</label>
							<div class="col-xs-9 col-xs-12">
							  <select class="form-control" name="notify" id="notify" style="color:#000;" >
								<option value ="">Select Notification Period</option>
								<option value ="0">24 hrs</option>
								<option value ="0">48 hrs</option>
								<option value ="0">72 hrs</option>
							  </select>
							</div>
						</div>
					</div>
				   <div style="padding:0px 40px;">
					<div class="form-group">
						<div class="col-xs-offset-2" >
						  <button type="submit" class="btn btn-default btn-save" style="width:auto;">Complete Registration</button>
						</div>
				  </div>
				  </div>
				</form>
			</div>
			<hr/>
			<p style="padding:0px 80px 15px 80px;font-size:13px;">Durch klicken auf ‚ Registrieung abschließen‘ stimmen sie den Nutzungbedinungen und der Datenschutzerklärung von world of Reservation zu</p>
			</div>
			<div id="fade" class="black_overlay"></div>
			<!-- /End registration modal -->
			<!-- Contact data Modal-->
			<!-------------------------------- MYCONTACT DATA MODAL --------------------------------------------->
			<div id="myquickreservation" class="white_content">
			<h3>Quick reservation</h3>
			<h4 class="center form-subtitle">Please Fill Up the Below Forms</h4>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myquickreservation').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					<div class="form_scroller">
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="First Name">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="Last Name">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="email" class="form-control" id="inputEmail3" placeholder="Email Address">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="Contact Number">
							</div>
						</div>

					</div>
				   <div style="padding:0px 100px;">
					<div class="form-group">
						<div class="col-xs-12" >
						<a href = "javascript:void(0)"  class="btn btn-default btn-save" onclick = "document.getElementById('myquickreservation').style.display='none'; document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">Proceed as Guest<br/>
						  Data will not be saved.</a>

						</div>
				  </div>
				  </div>
				</form>
			</div>
			<p class="center already_register_tab">Already Registered, <a href="#" id="link_login" >Login Here</a></p>
			<div style="padding:0px 30px 15px 30px;">
			<b>Not yet registered</b>
				<div class="checkbox">
				<a href = "javascript:void(0)" onclick = "document.getElementById('myquickreservation').style.display='none'; document.getElementById('my_registration').style.display='block';document.getElementById('fade').style.display='block'">
							<span class="glyphicon glyphicon-expand" aria-hidden="true"></span>  Yes, I would like to register</a>
				</div>
			<ul class="register_note">
				<li>-	Einmalige Registrierung für alle angeschlossen Anbieter</li>
				<li>-	Übersciht aller vergangen und zukünftigen Reservierungen </li>
				<li>-	Storno via one click</li>
				<li>-	Sie entscheiden welcher Anieter Ihre E-mail Addresse Sehen kann</li>
				<li>-	Hinweis bei Buchungüberlappungen</li>
			</ul>
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
			<!-- /End contact data modal -->
<!-------------------------------- MYRESERVATION DATA MODAL --------------------------------------------->
			<div id="myreservation" class="white_content">
			<h3>Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myreservation').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					<div class="reservation-data-section">
					<div class="col-xs-1">
					<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
					</div>
					<div class="col-xs-11">
					<p>
					Couching Simone, Garrison<br/>
					Woodway 115-445, Prisbane, GB Sooulfolk<br/>
					49-99-999-9999
					</p>
					</div>
					</div>
										<div class="reservation-data-section">
										<div class="col-xs-1">
										<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
										</div>
										<div class="col-xs-11">
										<p>
										Checkin Date :  18th April, 2015<br/>
										<select name="" class="form-control">
											<option value="">19 April,2015</option>
											<option value="">20 April,2015</option>
											<option value="">21 April,2015</option>
										</select>
										<div id="change_option">You've Changed the Selected Date</div>
										</p>
										</div>
										</div>
					<div class="reservation-data-section">
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11">
							<p>
							David Adams<br/>
							davidadamas@gmail.com<br/>
							+49-888-8888 
							</p>
						</div>
					</div>
					<div class="col-xs-12" style="padding:20px 20px 6px 20px;">
						<div class="form-group">
							<div class="col-xs-1">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</div>
							<div class="col-xs-11">
							  <textarea class="form-control" id="inputEmail3" rows="1"></textarea>
							</div>
						</div>
					<p style="padding-left:40px;">
					<button id="share-email" type="button" class="btn btn-default btn-share" data-toggle="tooltip" data-placement="top" title="Share Email Address"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></button>&nbsp;&nbsp;  Agree to share Email address with service provider.</p>
					</div><!--ends col-xs-12 -->
					<div class="clearfix"></div>
				   <div class="button_padding">
					<div class="form-group">
						<div class="col-xs-6" >
						  <a href="javascript:void(0)" class="btn btn-default btn-save" onclick="document.getElementById('myreservation').style.display='none';document.getElementById('myreservation_confirm').style.display='block';document.getElementById('fade').style.display='block'">Reserve table</a>
						</div>
						<div class="col-xs-6" >
						  <button type="submit" class="btn btn-default btn-save">Cancel</button>
						</div>
				  </div>
				  </div>
				</form>
				<p class="center" style="padding:10px 0px 0px 0px;margin-bottom:2px;" >Reservation Process Continues for next <span id="log">60</span> Secs</p> 
				<div id="flash" class="center" ></div>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- RESERVATION CONFIRMATION DATA MODAL --------------------------------------------->
			<div id="myreservation_confirm" class="white_content">
			<h3>Reservation Confirmation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myreservation_confirm').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
						<div class="reservation-data-section">

						<div class="col-xs-12">
								<div class="alert alert-success" role="alert" style="padding:5px 12px;margin-bottom:5px;">
								  <strong><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> Success!</strong> You successfully made reservation.
								</div>
						<p class="center">Reference Number : <b>NHDJU6438</b></p>
						</div>
						</div>
					<div class="reservation-data-section">
						<div class="col-xs-4" style="text-align:right;">
							Sponsor By
						</div>
						<div class="col-xs-8"></div>
					</div>
					<div class="reservation-data-section">
					<div class="col-xs-1">
					<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
					</div>
					<div class="col-xs-11">
					<p>
					Couching Simone, Garrison<br/>
					Woodway 115-445, Prisbane, GB Sooulfolk<br/>
					49-99-999-9999
					</p>
					</div>
					</div>
										<div class="reservation-data-section">
										<div class="col-xs-1">
										<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
										</div>
										<div class="col-xs-11">
										<p>
										Donnarster 14 July 2015 - ab 17:00<br/>
										Donnarster 14 July 2015 - bis 19:00
										<span style="float:right;">2 hours</span><br/>
										Table 2 (2)<br/>
										2 Palatz
										</p>
										</div>
										</div>
					<div class="reservation-data-section">
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11">
							<p>
							David Adams<br/>
							davidadamas@gmail.com<br/>
							+49-888-8888 
							</p>
						</div>
					</div>

					<div class="clearfix"></div>
				   <div style="padding:20px 100px;">
					<div class="form-group">
						  <a href="#" class="btn btn-default ">I</a>
						  <a href="#" class="btn btn-default">F</a>
						  <a href="#" class="btn btn-default ">F</a>
						  <a href="#" class="btn btn-default">Export</a>

				  </div>
				  </div>
				</form>

			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- MYRESERVATION DETAILS DATA MODAL --------------------------------------------->
			<div id="myreservation_details" class="white_content" style="left:8%;width:85%;">
			<h3>My Reservation Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myreservation_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
				<form class="form-horizontal">
					<div class="reservation-data-section">

					<div class="col-xs-5 col-xs-5">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num, city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>
						Object Number [Obj Category]<br/>
						Check In : Mon,10.04.15, 12:00<br/>
						Check Out : Tue,11.04.15, 12:00<br/>
						Period : 1 Night
						</p>
					</div>
					<div class="col-xs-2 col-xs-2"><button class="btn btn-default btn-save">Cancel</button>
					<button class="btn btn-default btn-success btn-change">Change</button>

					</div>
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<div class="col-xs-5 col-xs-5">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-2 col-xs-2"><button class="btn btn-default btn-save" disabled>Cancel</button>
					<button class="btn btn-default btn-change" disabled>Change</button>
					</div>
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<div class="col-xs-5 col-xs-5">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-2 col-xs-2">
						<div class="flag_icon"><span class="red">Cancelled</span></div>
						<div class="flag_icon"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></div>
						
					</div>
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<h5>Permanent Reservation History</h5>
					<div class="col-xs-5 col-xs-5">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-2 col-xs-2">
						<div class="flag_icon"><span class="glyphicon glyphicon-ok green" aria-hidden="true"></span></div>
						<div class="flag_icon"><span class="glyphicon glyphicon-minus red" aria-hidden="true"></span></div>
						<div class="flag_icon"><button class="btn btn-default btn-icon"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></div>
					</div>
					</div>
					<!--ends-->				
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- WHITELIST DETAILS DATA MODAL --------------------------------------------->
			<div id="mywhite_list" class="white_content" style="left:8%;width:85%;">
			<h3>My White List Category</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('mywhite_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
				<form class="form-horizontal">
					<div class="reservation-data-section">
					<div class="col-xs-7 col-xs-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>since: 01.01.2014<br/></p>
					</div>
					
					</div>
					<!--ends-->
					
					<div class="reservation-data-section">
					
					<div class="col-xs-7 col-xs-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>since: 01.01.2014<br/></p>
					</div>
					</div>
					<!--ends-->				
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- BLACK LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="myblack_list" class="white_content" style="left:8%;width:85%;">
			<h3>My Black List Category</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myblack_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
				<form class="form-horizontal">
					<div class="reservation-data-section">
					<div class="col-xs-7 col-xs-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>since: 01.01.2014<br/></p>
					</div>
					
					</div>
					<!--ends-->
					
					<div class="reservation-data-section">
					
					<div class="col-xs-7 col-xs-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>since: 01.01.2014<br/></p>
					</div>
					</div>
					<!--ends-->				
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- EMAIL LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="myemail_list" class="white_content" style="left:8%;width:85%;">
			<h3>My Email List</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myemail_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
				<form class="form-horizontal">
					<div class="reservation-data-section">
					<div class="col-xs-5 col-xs-5">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-4 col-xs-4">
						<p>Sharing since: 01.01.2014<br/></p>
					</div>
					<div class="col-xs-3 col-xs-3">
						<button class="btn btn-default">Stop Sharing</button>
					</div>
					
					</div>
					<!--ends-->
					
					<div class="reservation-data-section">
					
					<div class="col-xs-5 col-xs-5">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-4 col-xs-4">
						<p>Sharing since: 01.01.2014<br/></p>
					</div>
					<div class="col-xs-3 col-xs-3">
						<button class="btn btn-default">Stop Sharing</button>
					</div>
					</div>
					<!--ends-->				
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!--------------------------------  RESERVATION OPTION  DATA MODAL --------------------------------------------->
			<div id="myreservation_options" class="white_content" style="left:8%;width:85%;">
			<h3>Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myreservation_options').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
				<form class="form-horizontal">
						<div class="reservation-data-section">
						<h5 class="center">Reservation System</h5>
						<div class="line clearfix"></div>
						<div class="options_wrapper">
						<div class="col-xs-3"></div>
							<div class="col-xs-3 col-xs-3 col-xs-12">
						
							<a href = "javascript:void(0)" class="btn btn-default btn-lg btn-green" onclick = "document.getElementById('myreservation_options').style.display='none';document.getElementById('mylogin').style.display='block';document.getElementById('fade').style.display='block'" style="float:right;">Login</a>
							</div>
							<div class="col-xs-3 col-xs-3  col-xs-12">
							<a href = "javascript:void(0)" class="btn btn-default btn-lg btn-green" onclick = "document.getElementById('myreservation_options').style.display='none';document.getElementById('myquickreservation').style.display='block';document.getElementById('fade').style.display='block'">Quick Reservation</a>
							</div>
						<div class="col-xs-3"></div>
						</div>
						</div>
				</form>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- Hotel Details DATA MODAL --------------------------------------------->
			<div id="hotel_details" class="white_content" style="left:8%;width:85%;">
			<h3>Contact Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('hotel_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
				<form class="form-horizontal">
						<div class="reservation-data-section">
							<div class="col-xs-10 col-xs-10">
								<p>
								<strong>Restaurant "Pegasus"</strong><br/>
								Address : Woodway 115-445, Prisbane,Munich,Germany<br/>
								Email   : pegaus@werb.de<br/>
								Phone   : +49-99-999-9907,+49-99-999-9908,+49-99-999-9909<br/>
								Website : <a href="#">www.hotel.com</a>
								</p>
							</div>
							<div class="col-xs-2">
							<button class="btn btn-default btn-success">Download</button>
							<button class="btn btn-default" style="margin-top:10px;">Timings</button>
							</div>
							<div class="map" id="map" style="width:100%;height:250px;"></div>
						</div>
				</form>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- MY PROFILE DETAILS DATA MODAL --------------------------------------------->
			<div id="myprofile" class="white_content" style="left:10%;width:80%;">
			<h3>My Profile</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myprofile').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<form class="form-horizontal">
						<div class="reservation-data-section">
						<h5>My Profile Details</h5>
<div class="profile_scroller">
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Username</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="paulwalker">
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Password</label>
							<div class="col-xs-9">
							  <input type="password" class="form-control" id="inputEmail3" value="12345566">
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Confirm Password</label>
							<div class="col-xs-9">
							  <input type="password" class="form-control" id="inputEmail3" value="12345566">
							</div>
						</div>
							<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Title</label>
							<div class="col-xs-9">
							  <select class="form-control" name="nametitle">
								<option value ="">Select Title</option>
								<option value ="0" selected >Mr</option>
								<option value ="0">Mrs</option>
								<option value ="0">Miss</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">First Name</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="Paul">
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Last Name</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="walker">
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Street</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="1st cross warner troad">
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Pincode</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="82382">
							</div>
						</div>
								<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">City</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="Munich">
							</div>
						</div>
								<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Country</label>
							<div class="col-xs-9">
							  <select class="form-control" name="language">
								<option value ="">Select Country</option>
								<option value ="0" selected >Germany</option>
								<option value ="0">France</option>
								<option value ="0">Spain</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Date Of Birth</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="12-09-1990">
							</div>
						</div>
						
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Email</label>
							<div class="col-xs-9">
							  <input type="text" class="form-control" id="inputEmail3" value="paul@gmail.com">
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Phone</label>
							<div class="col-xs-9">
							  <input type="email" class="form-control" id="inputEmail3" value="+49-88-8888" >
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Language</label>
							<div class="col-xs-9">
							<select class="form-control" name="language">
								<option value ="0">Select Language</option>
								<option value ="0">English</option>
								<option value ="0">Germany</option>
								<option value ="0">Deutch</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Join Date</label>
							<div class="col-xs-9">
								<input type="text" class="form-control selectpicker" value="01-04-2015" />
							</div>
						</div>	
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Remainder</label>
							<div class="col-xs-9">
								<select class="form-control" name="language">
								<option value ="0">Select Remainder</option>
								<option value ="0">24 hrs</option>
								<option value ="0" selected>48 hrs</option>
								<option value ="0">72 hrs</option>
							  </select>
							</div>
						</div>	
</div>
						<div class="line"></div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label"></label>
							<div class="col-xs-9">
							  <button type="submit" class="btn btn-default btn-save" style="width:auto;">Update</button>
							</div>
						</div>
						<!--ends-->				
					</form>
				</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!-- =========================================Main html code body==============================================================================================-->
			<div class="col-xs-3 hotel_title nopadding">
					<img src="images/logo1.png" class="img-responsive hotel_logo" style="margin-bottom:5px;" />
			</div>
			<div class="col-xs-6" style="padding:20px 20px 20px 35px;">
			<b>Hotal Alpha</b><br/>Tennisclub herchiu c.v, 55-0086, Munchen
			</div>
			<div class="col-xs-3" style="padding:35px 0px 0px;">				
				<div class="iconset">
					<a href = "javascript:void(0)" class="btn-group" onclick = "document.getElementById('hotel_details').style.display='block';document.getElementById('fade').style.display='block'" class="btn-blank" ><img src="images/more-24.png" /></a>
					<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false">
							<img src="images/germany.png" /> 
						  </button>
						  <ul class="dropdown-menu" role="menu">
							<li><a href="#"><img src="images/english.png" style="padding-right:6px;" />- English</a></li>
			
						  </ul>
						</div>
						<!--ends-->
						<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false" id="blackuser">
							<img src="images/user.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu">
							<li><a href = "javascript:void(0)" onclick = "document.getElementById('mylogin').style.display='block';document.getElementById('fade').style.display='block'">Login</a></li>
							<li><a href = "javascript:void(0)" onclick = "document.getElementById('my_registration').style.display='block';document.getElementById('fade').style.display='block'">Registration</a></li>
							<li><a href = "javascript:void(0)" onclick = "document.getElementById('myquickreservation').style.display='block';document.getElementById('fade').style.display='block'">As Guest</a></li>
			
						  </ul>
						</div>
						<!--ends-->
						<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false" id="greenuser">
							<img src="images/green-user.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu" style="left:-100px;">
							<li><a href="javascript:void(0)" onclick="document.getElementById('myprofile').style.display='block';document.getElementById('fade').style.display='block'">My Profile</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('myreservation_details').style.display='block';document.getElementById('fade').style.display='block'">My Reservations</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('mywhite_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">White List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('myblack_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">Black List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('myemail_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">E-mail Visible </a></li>
							<div class="divider"></div>
							<li><a href = "#" >Logout</a></li>
			
						  </ul>
						</div>
						<!--ends-->
					<a class="btn-blank" ><img src="images/fullscreen.png" id="fs" /></a>
				</div>
			</div>
			<div class="clearfix"></div>
		
			<div class="clearfix"></div>
				<core:if test="${not empty currentDate}">
				<div class="col-xs-3 nopadding">
					<button class="btn btn-default btn-current-date"
						value="${currentDate}" id="currentDate">${currentDate}
					</button>
				</div>
				<input type="hidden" id="currDate" value="${currentDate}">
				</core:if>
				
				<div class="col-xs-3">
					<select class="selectpicker form-control" name="" id="category" onchange="getRooms()">
						 <option value="0">Select Room</option>
                               <core:forEach var="categoryType" items="${categoryTypeList}">
                               <core:choose>
                               	<core:when test="${categoryTypeId eq categoryType.categoryId}">
                               		<option value="${categoryType.categoryId}" selected="selected">${categoryType.categoryName}</option>
                               	</core:when>
                               	<core:otherwise>
                               		<option value="${categoryType.categoryId}">${categoryType.categoryName}</option>
                               	</core:otherwise>
                               </core:choose>
                               </core:forEach>
					</select>
				</div>
				<div class="col-xs-4 nopadding">
					<select class="selectpicker form-control" name="" id="roomNo">
						<option value="0">Select Room</option>
						<core:forEach var="roomsList" items="${roomsList}">
							<core:choose>
								<core:when test="${selectedRoomNO eq roomsList}">
									<option value="${roomsList}" selected="selected">${roomsList}</option>
								</core:when>
								<core:otherwise>
									<option value="${roomsList}">${roomsList}</option>
								</core:otherwise>
							</core:choose>
						</core:forEach>
					</select>
				</div>
				<spring:form commandName="reservtnByNoOfDays">
				<div class="col-xs-2" style="padding-right:0px;">
					<spring:input path="noOfDays" class="form-control" Placeholder=" Days"  id="noOfDays"/>
				</div>
				</spring:form>
			<div class="clearfix"></div>
			<div class="ajax_container">
			<div class="ajax_loader"></div>
				<div class="col-xs-12 nomargin_lf_rt date_wrapper">
							<div id="thumbcarousel1" class="carousel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							   <div class="carousel-caption1">
									  <div class="piece">
											<div class="small_square">
											 
											 	<!-- dispalying month -->
												<jsp:useBean id="dateVal" class="java.util.Date" />
												<fmt:formatDate value="${dateVal}" pattern="MMMM"
													var="currDate" />
												<h3 class="month_title">${currDate}</h3>
												
												<!-- iterate peace -->
												<core:forEach var="datesList" items="${availDatesByroom}">
												<jsp:useBean id="date" class="java.util.Date" />
													<fmt:formatDate value="${date}" pattern="d"
														var="currentDate" />
													<core:set var="colrCode" value="A"></core:set>
													<core:choose>
														<core:when test="${colrCode eq datesList.statusCode}">
														
														<fmt:formatDate value="${datesList.date}" pattern="d"
																var="dateval" />
															<core:choose>
																<core:when test="${currentDate eq dateval}">
																	<span class="cal_date current_date"> <fmt:formatDate
																		value="${datesList.date}" pattern="d" var="datevalue" />
																		<core:set value="${datesList.date}" var="date"></core:set>
																		<a
																		href="<core:url value="getSelectedDateAvailRooms.htm?date=${date}"></core:url>"
																		class="cal_date current_date">${datevalue}</a>
																	</span>
																</core:when>
																<core:otherwise>
																	<span class="cal_date green"> <fmt:formatDate
																		value="${datesList.date}" pattern="d" var="datevalue" />
																		<core:set value="${datesList.date}" var="date"></core:set>
																		<a
																		href="<core:url value="getSelectedDateAvailRooms.htm?date=${date}"></core:url>"
																		class="cal_date green">${datevalue}</a>
																	</span>
																</core:otherwise>
															</core:choose>
													
														</core:when>
														<core:otherwise>
															<fmt:formatDate value="${datesList.date}" pattern="d"
																var="dateval" />
																<core:choose>
																	<core:when test="${currentDate eq dateval}">
																		<span class="cal_date current_date"> <fmt:formatDate
																			value="${datesList.date}" pattern="d" var="datevalue" />
																			<core:set value="${datesList.date}" var="date"></core:set>
																			<a
																			href="<core:url value="getSelectedDateAvailRooms.htm?date=${date}"></core:url>"
																			class="cal_date current_date">${datevalue}</a>
																		</span>
																	</core:when>
																	<core:otherwise>
																		<span class="cal_date gray"> <fmt:formatDate
																			value="${datesList.date}" pattern="d" var="datevalue" />
																			<core:set value="${datesList.date}" var="date"></core:set>
																			<a
																			href="<core:url value="getSelectedDateAvailRooms.htm?date=${date}"></core:url>"
																			class="cal_date gray">${datevalue}</a>
																		</span>
																	</core:otherwise>
																</core:choose>
																
															<%-- 	<span class="cal_date gray"> <fmt:formatDate
																	value="${datesList.date}" pattern="d" var="datevalue" />
																		<core:set value="${datesList.date}" var="date"></core:set>
																		<a
																		href="<core:url value="getSelectedDateAvailRooms.htm?date=${date}"></core:url>"
																		class="cal_date gray">${datevalue}</a>
																	</span> --%>
														</core:otherwise>
													</core:choose>
												</core:forEach>
											</div>
									</div>
									</div>
							  </div>

							  <!-- Controls -->
							  <a class="left carousel-control" href="#" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-triangle-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a>
							  <a class="right carousel-control" href="#" role="button" data-slide="next">
								<span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							  </a>
							</div>
				
				</div><!-- ends date list -->
		<!-- Object or week list and data -->
		<div class="clearfix"></div>
				<div class="col-xs-12 nomargin info_wrapper">
							<div class="ajax_container_inner">
							<div class="ajax_loader_inner"></div>
								<div id="thumbcarousel2" class="carousel carousel_info_hotel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							  <div class="carousel-inner" role="listbox">
									<div class="item active">
									  <div class="carousel-caption">
									<core:set value="green" var="bgColor"></core:set>
									  <core:forEach items="${availRoomsByDateWise}" var="dateList">
									  	<core:choose>
									  		<core:when test="${bgColor eq dateList.colourCode}">
									  			<div class="col-xs-3  col-xs-3-fixed nopadding book_info">
												<div class="cell booked"><a href="javascript:void(0)" class="room_info" 
												onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">${dateList.roomAvailDate}</a></div>
											</div>
									  		</core:when>
									  		<core:otherwise>
									  			<div class="col-xs-3  col-xs-3-fixed nopadding book_info">
												<div class="cell storeclose"><a href="javascript:void(0)" class="room_info" 
												onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">${dateList.roomAvailDate}</a></div>
											</div>
									  		</core:otherwise>
									  	</core:choose>
									  </core:forEach>
									  </div>
									</div><!-- ends item-->
							  </div>

							  <!-- Controls -->
							  <a class="myleft carousel-control" href="#thumbcarousel2" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-triangle-left glyphicon glyphicon-triangle-myleft" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a>
							  <a class="myright carousel-control" href="#thumbcarousel2" role="button" data-slide="next">
								<span class="glyphicon glyphicon-triangle-right glyphicon glyphicon-triangle-myright" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							  </a>
							</div>
				
				</div>
				</div>
		<!-- ends-->
		</div>
	
	
		<ul class="footer_menu">
		<li><a href="#">AGB</a></li>
		<li><a href="#">Impressum</a></li>
		<li><a href="#">Admin</a></li>
		<li><a href="#">Über Reservat</a></li>
		<li><a href="#">Help</a></li>
		</ul>
	</div>


	<!-- ends-->
	<div class="col-xs-5">
	<h5>How it Works ?</h5>
	<div class="media">
      <div class="media-left">
        <a href="#">
          <img class="media-object" data-src="holder.js/64x64" alt="64x64" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNjQiIGhlaWdodD0iNjQiIHZpZXdCb3g9IjAgMCA2NCA2NCIgcHJlc2VydmVBc3BlY3RSYXRpbz0ibm9uZSI+PGRlZnMvPjxyZWN0IHdpZHRoPSI2NCIgaGVpZ2h0PSI2NCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjE0LjUiIHk9IjMyIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+NjR4NjQ8L3RleHQ+PC9nPjwvc3ZnPg==" data-holder-rendered="true" style="width: 64px; height: 64px;">
        </a>
      </div>
      <div class="media-body">
        <h4 class="media-heading" id="media-heading">Media heading<a class="anchorjs-link" href="#media-heading"><span class="anchorjs-icon"></span></a></h4>
        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
      </div>
    </div>
	<div class="media">
      <div class="media-left">
        <a href="#">
          <img class="media-object" data-src="holder.js/64x64" alt="64x64" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNjQiIGhlaWdodD0iNjQiIHZpZXdCb3g9IjAgMCA2NCA2NCIgcHJlc2VydmVBc3BlY3RSYXRpbz0ibm9uZSI+PGRlZnMvPjxyZWN0IHdpZHRoPSI2NCIgaGVpZ2h0PSI2NCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjE0LjUiIHk9IjMyIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+NjR4NjQ8L3RleHQ+PC9nPjwvc3ZnPg==" data-holder-rendered="true" style="width: 64px; height: 64px;">
        </a>
      </div>
      <div class="media-body">
        <h4 class="media-heading" id="media-heading">Media heading<a class="anchorjs-link" href="#media-heading"><span class="anchorjs-icon"></span></a></h4>
        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
      </div>
    </div>
	<div class="media">
      <div class="media-left">
        <a href="#">
          <img class="media-object" data-src="holder.js/64x64" alt="64x64" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNjQiIGhlaWdodD0iNjQiIHZpZXdCb3g9IjAgMCA2NCA2NCIgcHJlc2VydmVBc3BlY3RSYXRpbz0ibm9uZSI+PGRlZnMvPjxyZWN0IHdpZHRoPSI2NCIgaGVpZ2h0PSI2NCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjE0LjUiIHk9IjMyIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+NjR4NjQ8L3RleHQ+PC9nPjwvc3ZnPg==" data-holder-rendered="true" style="width: 64px; height: 64px;">
        </a>
      </div>
      <div class="media-body">
        <h4 class="media-heading" id="media-heading">Media heading<a class="anchorjs-link" href="#media-heading"><span class="anchorjs-icon"></span></a></h4>
        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
      </div>
    </div>
	<div class="media">
      <div class="media-left">
        <a href="#">
          <img class="media-object" data-src="holder.js/64x64" alt="64x64" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNjQiIGhlaWdodD0iNjQiIHZpZXdCb3g9IjAgMCA2NCA2NCIgcHJlc2VydmVBc3BlY3RSYXRpbz0ibm9uZSI+PGRlZnMvPjxyZWN0IHdpZHRoPSI2NCIgaGVpZ2h0PSI2NCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjE0LjUiIHk9IjMyIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+NjR4NjQ8L3RleHQ+PC9nPjwvc3ZnPg==" data-holder-rendered="true" style="width: 64px; height: 64px;">
        </a>
      </div>
      <div class="media-body">
        <h4 class="media-heading" id="media-heading">Media heading<a class="anchorjs-link" href="#media-heading"><span class="anchorjs-icon"></span></a></h4>
        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
      </div>
    </div>
	</div>
	<!--ends-->
</div>
<!--Main COntent ends -->
</div>
</body>
</html>
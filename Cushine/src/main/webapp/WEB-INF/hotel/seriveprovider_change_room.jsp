<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="tag"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta charset="utf-8">
		<meta name="viewport" content="width=700">
		<title>Service Provider View</title>	
		<!--meta name="viewport" content="width=device-width, initial-scale=1.0"-->				
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
		<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css">
		<!--script src="//code.jquery.com/jquery-1.11.3.min.js"></script-->
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	   <script type="text/javascript" src="js/bootstrap.min.js"></script>
	   	<script type="text/javascript" src="js/jquery.validate.js"></script>
	    <!-- Google Map API Key Source -->
		<!-- <script src="http://maps.google.com/maps/api/js?sensor=false"></script> -->
		<!-- Google Map  Source -->
	   	<!-- <script type="text/javascript" src="js/gmaps.js"></script> -->
		<script src="js/customjs.js"></script>
		<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
	   <style>
	   .over {
		  opacity:1;
		  background:orange;
		  border:2px orange dashed;
		}
		.over:hover{
		border: 2px #CCC dashed;
		}
		.padded {
			padding:15px;	
			
		}
		[draggable] {
		  -moz-user-select: none;
		  -khtml-user-select: none;
		  -webkit-user-select: none;
		  user-select: none;
		  -khtml-user-drag: element;
		  -webkit-user-drag: element;
		  cursor:move;
		  text-shadow:none;
		  color:#444;
		}
		@-webkit-keyframes wobble  {
		  0%  { -webkit-transform:  rotate(0deg); }
		  20%  { -webkit-transform:  rotate(8deg); }
		  50%  { -webkit-transform:  rotate(-8deg); }
		  100%  { -webkit-transform:  rotate(0deg); }
		}
		.wobble { -webkit-animation: wobble 2s infinite; }
		</style>
</head>
<body style="margin:20px 0px;">
<!-- Main COntents -->
<div class="col-xs-12">
	<!-- widget starts -->
	<div class="col-xs-7 dummyfull">
		<div class="widget_wrapper">
		<!-------------------------------- MODAL Login --------------------------------------------->
			<div id="mylogin" class="white_content">
			<h3>Login Form</h3>
			<p class="center">Lorem ipsum dolor sit amet, consectetur adipiscing elit,</p>
			<hr/>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('mylogin').style.display='none';document.getElementById('fade').style.display='none'">Close</a>
			<div style="padding:0px 100px;">
				<form class="form-horizontal">
				  <div class="form-group">
					<div class="col-sm-12">
					  <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
					</div>
				  </div>
				    <div class="form-group">
					<div class="col-sm-12">
					  <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
					</div>
				  </div>
				  <div class="form-group">
					<div class="col-sm-12">
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
							<label>
							  <input type="checkbox"> Yes, I would like to register
							</label>
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
			<div id="myregistration" class="white_content">
			<h3>Registration</h3>
			<h4 class="center form-subtitle">Please Fill Up the Below Forms</h4>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myregistration').style.display='none';document.getElementById('fade').style.display='none'">Close</a>
			<div style="padding:0px">
				<form class="form-horizontal">
					<div class="form_scroller">
						<div class="form-group">
							<div class="col-sm-12">
							  <select class="form-control" name="language">
								<option value ="0">Select Language</option>
								<option value ="0">English</option>
								<option value ="0">Germany</option>
								<option value ="0">Deutch</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="First Name">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="Last Name">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="email" class="form-control" id="inputEmail3" placeholder="Email Address">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="Contact Number">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="email" class="form-control" id="inputEmail3" placeholder="Username">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="password" class="form-control" id="inputEmail3" placeholder="Password">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="password" class="form-control" id="inputEmail3" placeholder="Confirm Password">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="password" class="form-control" id="inputEmail3" placeholder="Confirm Password">
							</div>
						</div>
					</div>
				   <div style="padding:0px 100px;">
					<div class="form-group">
						<div class="col-sm-12" >
						  <button type="submit" class="btn btn-default btn-save">Complete Registration</button>
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
			<div id="mycontactdata" class="white_content">
			<h3>Contact Data</h3>
			<h4 class="center form-subtitle">Please Fill Up the Below Forms</h4>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('mycontactdata').style.display='none';document.getElementById('fade').style.display='none'">Close</a>
			<div style="padding:0px">
				<form class="form-horizontal">
					<div class="form_scroller">
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="First Name">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="Last Name">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="email" class="form-control" id="inputEmail3" placeholder="Email Address">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							  <input type="text" class="form-control" id="inputEmail3" placeholder="Contact Number">
							</div>
						</div>

					</div>
				   <div style="padding:0px 100px;">
					<div class="form-group">
						<div class="col-sm-12" >
						  <button type="submit" class="btn btn-default btn-save">Proceed as Guest<br/>
						  Data will not be saved.</button>
						</div>
				  </div>
				  </div>
				</form>
			</div>
			<p class="center already_register_tab">Already Registered, <a href="#" id="link_login" >Login Here</a></p>
			<div style="padding:0px 30px 15px 30px;">
			<b>Not yet registered</b>
				<div class="checkbox">
					<label>
					  <input type="checkbox"> Yes, I would like to register
					</label>
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
<!-------------------------------- -------------MYRESERVATION DATA MODAL --------------------------------------------->
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
										Couching Simone, Garrison<br/>
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
							<p style="padding-bottom:10px;">
							David Adams<br/>
							davidadamas@gmail.com<br/>
							+49-888-8888 
							</p>
						<button class="btn btn-default btn-blank"><img src="images/whitelist.png" /></button>
						<button class="btn btn-default btn-blank"><img src="images/blacklist.png" /></button>
						<button class="btn btn-default btn-blank"><img src="images/payment.png" /></button>
						<button class="btn btn-default btn-blank"><img src="images/arrived.png" /></button>
						</div>
					</div>
					<div class="col-xs-12" style="padding:20px 20px 6px 20px;">
						<div class="form-group">
							<div class="col-sm-1">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</div>
							<div class="col-xs-11">
							  <textarea class="form-control" id="inputEmail3" rows="1"></textarea>
							</div>
						</div>
					
					</div><!--ends col-xs-12 -->
					<div class="clearfix"></div>
				   <div style="padding:0px 100px;">
					<div class="form-group">
						<div class="col-sm-6" >
						  <button type="submit" class="btn btn-default btn-save">Reserve table</button>
						</div>
						<div class="col-sm-6" >
						  <button type="submit" class="btn btn-default btn-save">Cancel</button>
						</div>
				  </div>
				  </div>
				</form>
				<div id="flash" class="center" ></div>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!-------------------------------- -------------ADD USeR TO LIST DATA MODAL --------------------------------------------->
			<div id="add_user_to_list" class="white_content">
			<h3>Add User to Whitelist</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('add_user_to_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					<div class="col-xs-12" style="padding:20px 20px 6px 20px;">
						<div class="form-group">
							<div class="col-xs-12">
							<label>Enter Email Address</label>
							  <input type="email" class="form-control" id="inputEmail3" placeholder="xxx@xxx.xom" />
							</div>
						</div>
						<div class="radio">
							<label>
							  <input type="radio" name="groupname" value="black"> Blacklist
							</label>
						  </div>
						<div class="radio">
							<label>
							  <input type="radio" name="groupname" value="white"> Whitelist
							</label>
						  </div>
					</div><!--ends col-xs-12 -->
					<div class="clearfix"></div>
					
				   <div style="padding:20px 20px;">
					<div class="form-group">
						<div class="col-sm-3 col-xs-12" >
						  <button type="submit" class="btn btn-default btn-save">ADD</button>
						</div>
				  </div>
				  </div>
				</form>
				<div id="flash" class="center" ></div>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-- ================================  MANUAL RESERVATION FOR SERVICE PROVIDER MODAL ==========================================-->
			<div id="myreservation_manual" class="white_content">
			<h3>Manual Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myreservation_manual').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
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
										1st April, 2015<br/>
										<select name="" class="form-control">
											<option value="">19 April,2015</option>
											<option value="">20 April,2015</option>
											<option value="">21 April,2015</option>
										</select>
										<div id="change_option">Room 1 number has been slected to reserve from 01.10.2012 to 03.10.2012</div>
										</p>
										</div>
										</div>
					<div class="reservation-data-section">
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11">
						<input type="text" class="form-control margin-bottom-10" placeholder="Name" />
						<input type="text" class="form-control margin-bottom-10" placeholder="Email" />
						<input type="text" class="form-control margin-bottom-10" placeholder="Phone" />
						<button class="btn btn-default btn-blank"><img src="images/whitelist.png" /></button>
						<button class="btn btn-default btn-blank"><img src="images/blacklist.png" /></button>
						<button class="btn btn-default btn-blank"><img src="images/payment.png" /></button>
						<button class="btn btn-default btn-blank"><img src="images/arrived.png" /></button>
						</div>
					</div>
					<div class="col-xs-12" style="padding:10px 20px 6px 20px;">
						<div class="form-group">
							<div class="col-sm-1">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</div>
							<div class="col-xs-11">
							  <textarea class="form-control" id="inputEmail3" rows="1"></textarea>
							</div>
						</div>
					
					</div><!--ends col-xs-12 -->
					<div class="clearfix"></div>
				   <div style="padding:0px 100px;">
					<div class="form-group">
						<div class="col-sm-6" >
						  <button type="submit" class="btn btn-default btn-save">Reserve table</button>
						</div>
						<div class="col-sm-6" >
						  <button type="submit" class="btn btn-default btn-save">Cancel</button>
						</div>
				  </div>
				  </div>
				</form>
				<p class="center" style="padding:0px 0px 0px 0px;" >Reservation Process Continues for next <span id="log">60</span> Secs</p> 
				<div id="flash" class="center" ></div>
				
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
					<div class="col-xs-7 col-md-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-md-5">
						<p>since: 01.01.2014<br/></p>
					</div>
					
					</div>
					<!--ends-->
					
					<div class="reservation-data-section">
					
					<div class="col-sm-7 col-md-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-sm-5 col-md-5">
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
					<div class="col-xs-7 col-md-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-md-5">
						<p>since: 01.01.2014<br/></p>
					</div>
					
					</div>
					<!--ends-->
					
					<div class="reservation-data-section">
					
					<div class="col-sm-7 col-md-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-sm-5 col-md-5">
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
					<div class="col-xs-5 col-md-5">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-4 col-md-4">
						<p>Sharing since: 01.01.2014<br/></p>
					</div>
					<div class="col-xs-3 col-md-3">
						<button class="btn btn-default">Stop Sharing</button>
					</div>
					
					</div>
					<!--ends-->
					
					<div class="reservation-data-section">
					
					<div class="col-sm-5 col-md-5">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-sm-4 col-md-4">
						<p>Sharing since: 01.01.2014<br/></p>
					</div>
					<div class="col-xs-3 col-md-3">
						<button class="btn btn-default">Stop Sharing</button>
					</div>
					</div>
					<!--ends-->				
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

					<div class="col-xs-5 col-md-5">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num, city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-md-5">
						<p>
						Object Number [Obj Category]<br/>
						Check In : Mon,10.04.15, 12:00<br/>
						Check Out : Tue,11.04.15, 12:00<br/>
						Period : 1 Night
						</p>
					</div>
					<div class="col-xs-2 col-md-2"><button class="btn btn-default btn-save">Cancel</button>
					<button class="btn btn-default btn-success btn-change">Change</button>

					</div>
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<div class="col-xs-5 col-md-5">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-md-5">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-2 col-md-2"><button class="btn btn-default btn-save" disabled>Cancel</button>
					<button class="btn btn-default btn-change" disabled>Change</button>
					</div>
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<div class="col-xs-5 col-md-5">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-md-5">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-2 col-md-2">
						<div class="flag_icon"><span class="red">Cancelled</span></div>
						<div class="flag_icon"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></div>
						
					</div>
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<h5>Permanent Reservation History</h5>
					<div class="col-xs-5 col-md-5">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-md-5">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-2 col-md-2">
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
<!-------------------------------- ADMIN RESERVATION DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_myreservation_details" class="white_content" style="left:8%;width:85%;">
			<h3>Customer Reservation Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_myreservation_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
			<div class="col-md-12" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
				<form class="form-horizontal">
					<div class="reservation-data-section">
					<div class="col-xs-6 col-md-6">
						<p>
						<strong>Name of Cutomer</strong><br/>
						+49-99-999-9999<br/>
						customer@gmail.com
						</p>
					</div>
					<div class="col-xs-6 col-md-6">
						<p>
						Object Number [Obj Category]<br/>
						Check In : Mon,10.04.15, 12:00<br/>
						Check Out : Tue,11.04.15, 12:00<br/>
						Period : 1 Night
						</p>
					</div>
					
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<div class="col-xs-6 col-md-6">
						<strong>Name Of Customer</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-6 col-md-6">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<div class="col-xs-6 col-md-6">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-6 col-md-6">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
				
					</div>
					<!--ends-->				
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- CUSTOMERS LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_customer_list" class="white_content" style="left:8%;width:85%;">
			<h3>Customes List</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_customer_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
			<div class="col-md-12" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
				<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
								<div class="btn-group">
								  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									Export As
								  </button>
								  <ul class="dropdown-menu">
									<li><a href="#">Pdf</a></li>
									<li><a href="#">CSV</a></li>

								  </ul>
								</div>
				
				</div>
			</div>
			<div class="clearfix"></div>
				<form class="form-horizontal">
					<div class="reservation-data-section">
					<!-- whitelist -->
							<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
         <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelle
        </a>
		<a class="right">asdfsdf@gmail.com</a>
		<a class="right" style="padding-right:20px;">+49-956858-47</a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
        <div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
      </div>
    </div>
  </div>
    <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="true" aria-controls="collapseOne">
         <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelle
        </a>
		
		<a class="right">asdfsdf@gmail.com</a>
		<a class="right" style="padding-right:20px;">+49-956858-47</a>
      </h4>
    </div>
    <div id="collapse1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
        <div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
      </div>
    </div>
  </div>
    <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse2" aria-expanded="true" aria-controls="collapseOne">
         <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelles
        </a>
		<a class="right">asdfsdf@gmail.com</a>
		<a class="right" style="padding-right:20px;">+49-956858-47</a>
      </h4>
    </div>
    <div id="collapse2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
        <div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingTwo">
      <h4 class="panel-title">
        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
         <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Michael Jordon 
        </a>
		<a class="right">Miachael@gmail.com</a>
		<a class="right"  style="padding-right:20px;">+09-09-2015</a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
        <div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingThree">
      <h4 class="panel-title">
        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
         <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Heribert hercuss
        </a>
		<a class="right" >asdfsdf@gmail.com</a>
		<a class="right"  style="padding-right:20px;">asdfsdf@gmail.com</a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
      <div class="panel-body">
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
			<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
      </div>
    </div>
  </div>
</div>
							<!-- end list-->
				
					</div>
					<!--ends-->				
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- ADMIN CUSTOMERS WHITE LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_white_list" class="white_content" style="left:8%;width:85%;">
			<h3>White List Customers</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_white_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
					<div class="reservation-data-section">
					<!-- whitelist -->
					<div class="col-md-12" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
									<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
									  <div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingOne1">
										  <h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne11" aria-expanded="true" aria-controls="collapseOne">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelle
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right">asdfsdf@gmail.com</a>
											<a class="right" style="padding-right:20px;">+49-956858-47</a>
										  </h4>
										</div>
										<div id="collapseOne11" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">
										  <div class="panel-body">
											<div class="col-md-12">Since : 02-09-15</div>
										  </div>
										</div>
									  </div>
										<div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingOne">
										  <h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="true" aria-controls="collapseOne">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelle
											</a>
											
											<a class="right">asdfsdf@gmail.com</a>
											<a class="right" style="padding-right:20px;">+49-956858-47</a>
										  </h4>
										</div>
										<div id="collapse1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
										  <div class="panel-body">
											<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
												<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
												<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
												<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
										  </div>
										</div>
									  </div>
										<div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingOne">
										  <h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" aria-expanded="true" aria-controls="collapseOne">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelles
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right">asdfsdf@gmail.com</a>
											<a class="right" style="padding-right:20px;">+49-956858-47</a>
										  </h4>
										</div>
										<div id="collapse2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
										  <div class="panel-body">
											<div class="col-md-6">Since : 02-09-15</div>
										  </div>
										</div>
									  </div>
									  <div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingTwo">
										  <h4 class="panel-title">
											<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Michael Jordon 
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right">Miachael@gmail.com</a>
											<a class="right"  style="padding-right:20px;">+09-09-2015</a>
										  </h4>
										</div>
										<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
										  <div class="panel-body">
											<div class="col-md-6">Since : 02-09-15</div>
										  </div>
										</div>
									  </div>
									  <div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingThree">
										  <h4 class="panel-title">
											<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Heribert hercuss
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right" >asdfsdf@gmail.com</a>
											<a class="right"  style="padding-right:20px;">asdfsdf@gmail.com</a>
										  </h4>
										</div>
										<div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
										  <div class="panel-body">
												<div class="col-md-6">Since : 02-09-15</div>
										  </div>
										</div>
									  </div>
									</div>	
							
					<!-- end list-->
					</div>
					<!--ends-->				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- ADMIN CUSTOMERS Email LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_email_list" class="white_content" style="left:8%;width:85%;">
			<h3>Customers Email List</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_email_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
					<div class="reservation-data-section">
					<!-- whitelist -->
					<div class="col-md-12 nopadding" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
							<div class="col-md-12 listpad">
								<div class="col-md-4 nopadding">Adman Herlary</div>
								<div class="col-md-4">adman@hotmail.com</div>
								<div class="col-md-4">+77-327928289</div>
							</div>
							<div class="col-md-12 listpad">
								<div class="col-md-4 nopadding">Herlary wildtone</div>
								<div class="col-md-4">adman@hotmail.com</div>
								<div class="col-md-4">+77-327928289</div>
							</div>
							<div class="col-md-12 listpad">
								<div class="col-md-4 nopadding">George hangman</div>
								<div class="col-md-4">adman@hotmail.com</div>
								<div class="col-md-4">+77-327928289</div>
							</div>
												<div class="col-md-12 listpad">
								<div class="col-md-4 nopadding">Ad M jacky</div>
								<div class="col-md-4">adman@hotmail.com</div>
								<div class="col-md-4">+77-327928289</div>
							</div>
					<!-- end list-->
					</div>
					<!--ends-->				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- ADMIN Add to white list DATA MODAL --------------------------------------------->
			<div id="admin_addwhite_list" class="white_content" style="left:8%;width:85%;">
			<h3>Add to White List Category</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_addwhite_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px;height:200px;">
					<div class="reservation-data-section">
					<!-- whitelist -->
	
					<form name="" class="form-horizontal">
						<div class="form-group">
						 <label for="inputEmail3" class="col-md-3 control-label">Room Category</label>
							<div class="col-md-8">
								<select class="form-control" name="language">
								<option value="0">Select Category</option>
								<option value="0">Single</option>
								<option value="0" selected="">Double</option>
								<option value="0">Suite</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-md-3 control-label">Room Number</label>
							<div class="col-md-8">
								<select class="form-control" name="language">
								<option value="0">Select Remainder</option>
								<option value="0">Room 1</option>
								<option value="0" selected="">Room 2</option>
								<option value="0">Room 3</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-md-3 control-label"></label>
							<div class="col-md-8">
								<button class="btn btn-primary btn-default">Add</button>
							</div>
						</div>
					</form>
					<!-- end list-->
					</div>
					<!--ends-->				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- ADMIN CUSTOMERS BLACK LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_black_list" class="white_content" style="left:8%;width:85%;">
			<h3>Black List Customers</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_black_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
					<div class="reservation-data-section">
					<!-- whitelist -->
					<div class="col-md-12" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
									<div class="panel-group" id="accordion1" role="tablist" aria-multiselectable="true">
									  <div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingOne1">
										  <h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1" aria-expanded="true" aria-controls="collapseOne">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelle
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right">asdfsdf@gmail.com</a>
											<a class="right" style="padding-right:20px;">+49-956858-47</a>
										  </h4>
										</div>
										<div id="collapseOne1" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">
										  <div class="panel-body">
											<div class="col-md-6">Since : 02-09-15</div>
										  </div>
										</div>
									  </div>
										<div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingOne">
										  <h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="true" aria-controls="collapseOne">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelle
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right">asdfsdf@gmail.com</a>
											<a class="right" style="padding-right:20px;">+49-956858-47</a>
										  </h4>
										</div>
										<div id="collapse1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
										  <div class="panel-body">
											<div class="col-md-12">Since : 02-09-15</div>
										  </div>
										</div>
									  </div>
										<div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingOne">
										  <h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" aria-expanded="true" aria-controls="collapseOne">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sharen Michelles
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right">asdfsdf@gmail.com</a>
											<a class="right" style="padding-right:20px;">+49-956858-47</a>
										  </h4>
										</div>
										<div id="collapse2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
										  <div class="panel-body">
											<div class="col-md-12">Since : 02-09-15</div>
										  </div>
										</div>
									  </div>
									  <div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingTwo">
										  <h4 class="panel-title">
											<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Michael Jordon 
											</a>
											<a class="right marginleft10" href="#">Remove</a>
											<a class="right">Miachael@gmail.com</a>
											<a class="right"  style="padding-right:20px;">+09-09-2015</a>
										  </h4>
										</div>
										<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
										  <div class="panel-body">
											<div class="col-md-6">Since :02-09-15</div>
										  </div>
										</div>
									  </div>
									  <div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingThree">
										  <h4 class="panel-title">
											<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
											 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Heribert hercuss
											</a>
											<a class="right" >asdfsdf@gmail.com</a>
											<a class="right"  style="padding-right:20px;">asdfsdf@gmail.com</a>
										  </h4>
										</div>
										<div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
										  <div class="panel-body">
												<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
												<div class="col-md-6">WhiteList - Touren</div><div class="col-md-6">02-09-15</div>
										  </div>
										</div>
									  </div>
									</div>	
							
					<!-- end list-->
					</div>
					<!--ends-->				
			</div>
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
						  <label for="inputEmail3" class="col-sm-2 control-label">Username</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="paulwalker">
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-9">
							  <input type="password" class="form-control" id="inputEmail3" value="12345566">
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-sm-2 control-label">Confirm Password</label>
							<div class="col-sm-9">
							  <input type="password" class="form-control" id="inputEmail3" value="12345566">
							</div>
						</div>
							<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">Title</label>
							<div class="col-sm-9">
							  <select class="form-control" name="nametitle">
								<option value ="">Select Title</option>
								<option value ="0" selected >Mr</option>
								<option value ="0">Mrs</option>
								<option value ="0">Miss</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">First Name</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="Paul">
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">Last Name</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="walker">
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">Street</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="1st cross warner troad">
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">Pincode</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="82382">
							</div>
						</div>
								<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">City</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="Munich">
							</div>
						</div>
								<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">Country</label>
							<div class="col-sm-9">
							  <select class="form-control" name="language">
								<option value ="">Select Country</option>
								<option value ="0" selected >Germany</option>
								<option value ="0">France</option>
								<option value ="0">Spain</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-sm-2 control-label">Date Of Birth</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="12-09-1990">
							</div>
						</div>
						
						<div class="form-group">
						 <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-9">
							  <input type="text" class="form-control" id="inputEmail3" value="paul@gmail.com">
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-sm-2 control-label">Phone</label>
							<div class="col-sm-9">
							  <input type="email" class="form-control" id="inputEmail3" value="+49-88-8888" >
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-sm-2 control-label">Language</label>
							<div class="col-sm-9">
							<select class="form-control" name="language">
								<option value ="0">Select Language</option>
								<option value ="0">English</option>
								<option value ="0">Germany</option>
								<option value ="0">Deutch</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-sm-2 control-label">Join Date</label>
							<div class="col-sm-9">
								<input type="text" class="form-control selectpicker" value="01-04-2015" />
							</div>
						</div>	
						<div class="form-group">
						 <label for="inputEmail3" class="col-sm-2 control-label">Remainder</label>
							<div class="col-sm-9">
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
						 <label for="inputEmail3" class="col-sm-2 control-label"></label>
							<div class="col-sm-9">
							  <button type="submit" class="btn btn-default btn-save" style="width:auto;">Update</button>
							</div>
						</div>
						<!--ends-->				
					</form>
				</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- Hotel Details DATA MODAL --------------------------------------------->
			<div id="hotel_details" class="white_content" style="left:8%;width:85%;">
			<h3>Contact Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('hotel_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
				<form class="form-horizontal">
						<div class="reservation-data-section">
							<div class="col-xs-10 col-md-10">
								<p>
								<strong>Restaurant "Pegasus"</strong><br/>
								Address : Woodway 115-445, Prisbane,Munich,Germany<br/>
								Email   : pegaus@werb.de<br/>
								Phone   : +49-99-999-9907,+49-99-999-9908,+49-99-999-9909<br/>
								Website : <a href="#">www.hotel.com</a>
								</p>
							</div>
							<div class="col-md-2">
							<button class="btn btn-default btn-success">Download</button>
							<button class="btn btn-default" style="margin-top:10px;">Timings</button>
							</div>
							<div class="map" id="map" style="width:100%;height:250px;"></div>
						</div>
				</form>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-- =========================================Main html code body==============================================================================================-->
			<div class="col-xs-3 hotel_title nopadding">
					<img src="images/logo_positiv(hotel).jpg" class="img-responsive hotel_logo" style="margin-bottom:5px;" />
			</div>
			<div class="col-xs-6 hotel_title" style="padding:20px 20px 20px 35px;">
					<b>Hotal Alpha</b><br/> Tennisclub herchiu c.v 55-0086 Munchen
			</div>
			<div class="col-xs-3" style="padding:35px 0px 0px;">
				<div class="iconset">
						<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false">
							<img src="images/brownpat.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu">
							<li><a href="javascript:void(0)"><img src="images/brownpat.png" style="padding-right:6px;" /></a></li>
							<li><a href="permanentRreservation.htm"><img src="images/bluepat.png" style="padding-right:6px;" /></a></li>
							<li><a href="serviceProviderView.htm"><img src="images/greenpat.png" style="padding-right:6px;" /></a></li>
						  </ul>
						</div>
						<!--ends-->
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
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false">
							<img src="images/green-user.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu" style="left:-55px;">
						  <li><a href="javascript:void(0)" onclick="document.getElementById('myprofile').style.display='block';document.getElementById('fade').style.display='block'">My Profile</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('myreservation_details').style.display='block';document.getElementById('fade').style.display='block'">My Reservations</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('mywhite_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">White List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('myblack_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">Black List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="document.getElementById('myemail_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">E-mail Visible </a></li>
							<div class="divider"></div>
							<li><a href = "#">Logout</a></li>
							<!--li><a href = "javascript:void(0)" onclick = "document.getElementById('mylogin').style.display='block';document.getElementById('fade').style.display='block'">Login</a></li>
							<li><a href = "javascript:void(0)" onclick = "document.getElementById('myregistration').style.display='block';document.getElementById('fade').style.display='block'">Registration</a></li>
							<li><a href = "javascript:void(0)" onclick = "document.getElementById('mycontactdata').style.display='block';document.getElementById('fade').style.display='block'">As Guest</a></li-->
						  </ul>
						</div>
						<!--ends-->
						<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false">
							<img src="images/red_user.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu" style="left:-140px;">
						  <li><a href = "javascript:void(0)" onclick = "document.getElementById('admin_customer_list').style.display='block';document.getElementById('fade').style.display='block'">Customers List</a></li>
						  <li><a href = "javascript:void(0)" onclick ="document.getElementById('admin_myreservation_details').style.display='block';document.getElementById('fade').style.display='block'">Customers Reservation</a></li>
						  <li><a href = "javascript:void(0)" onclick = "document.getElementById('admin_white_list').style.display='block';document.getElementById('fade').style.display='block'">White List Customers</a></li>
						  <li><a href = "javascript:void(0)" onclick = "document.getElementById('admin_black_list').style.display='block';document.getElementById('fade').style.display='block'">Black List Customers</a></li>
						  <li><a href = "javascript:void(0)" onclick = "document.getElementById('admin_email_list').style.display='block';document.getElementById('fade').style.display='block'">Email Of Customers</a></li>
						  <li><a href = "javascript:void(0)" onclick = "document.getElementById('admin_addwhite_list').style.display='block';document.getElementById('fade').style.display='block'">Add to White List Category</a></li>
							
			
						  </ul>
						</div>
						<!--ends-->
					<a class="btn-blank"><img src="images/fullscreen.png"  id="fs" /></a>
				</div>
			</div>
			<div class="clearfix"></div>
			<!--div class="line"></div-->
			<div class="col-xs-12 nopadding" style="display:none;">	
						<div class="iconset100">
							<a href = "javascript:void(0)" onclick ="document.getElementById('myreservation_details').style.display='block';document.getElementById('fade').style.display='block'"><img src="images/arrows.png" style="padding-right:6px;" /></a>
							<a href = "javascript:void(0)" onclick ="document.getElementById('myprofile').style.display='block';document.getElementById('fade').style.display='block'"><img src="images/user.png" style="padding-right:6px;" /></a>
							<!--a href = "javascript:void(0)" onclick ="document.getElementById('myreservation_options').style.display='block';document.getElementById('fade').style.display='block'"><img src="images/user.png" style="padding-right:6px;" /></a-->
							<a href = "javascript:void(0)" onclick ="document.getElementById('mywhite_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">W</a>
							<a href = "javascript:void(0)" onclick ="document.getElementById('myblack_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">B</a>
							<a href = "javascript:void(0)" onclick ="document.getElementById('myemail_list').style.display='block';document.getElementById('fade').style.display='block'" class="circle">E</a>
							<a href = "view_users.html" ><img src="images/user.png" /></a>
							<a href = "view_users.html" ><img src="images/arrows.png" /></a>
							<a href = "whitelist.html"><img src="images/whitelist.png" /></a>
							<a href = "blacklist.html"><img src="images/blacklist.png" /></a>
							<a href = "email_list.html"><img src="images/mails.png" /></a>
							<a href="javascript:void(0)" onclick="document.getElementById('add_user_to_list').style.display='block';document.getElementById('fade').style.display='block'"><img src="images/add_wlu.png" /></a>
						<form class="navbar-form navbar-right" role="search">
							<div class="form-group">
							  <input type="text" class="form-control input-sm" placeholder="Search">
							</div>
							<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
							<a href="#" class="btn btn-default"><span class="glyphicon glyphicon-export" aria-hidden="true"></span></a>
						  </form>
						
						</div>
			</div>
			<div class="clearfix"></div>
				<div class="col-xs-3 col-sm-3 col-xs-12 nopadding"><button class="btn btn-default btn-current-date">06-06-2015</button></div>
				<div class="col-xs-3 col-sm-3 col-xs-12">
					<select class="selectpicker form-control" name="">
						<option value="0">Select Room</option>
						<option value="1">Single Rooms</option>
						<option value="1">Double Rooms</option>				
						<option value="2">Triple Rooms</option>
						<option value="3">Suite</option>
					</select>
				</div>
				<div class="col-xs-4 col-sm-4 col-xs-12  nopadding">
					<select class="selectpicker form-control" name="">
						<option value="0">Select Room</option>
						<option value="1">Room 1</option>
						<option value="1">Room 2</option>				
						<option value="2">Room 3</option>
						<option value="3">Room 4</option>
					</select>
				</div>
				<div class="col-xs-2 col-sm-2 col-xs-12" style="padding-right:0px;">
					<input type="number" class="form-control" name="" value="" Placeholder=" Days" value="1" />
				</div>
			<div class="clearfix"></div>
			<div class="ajax_container">
			<div class="ajax_loader"></div>
				<div class="col-xs-12 nomargin_lf_rt date_wrapper">
							<div id="thumbcarousel1" class="carousel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							   <div class="carousel-caption1">
										<div class="piece">
												<div class="small_square">
												 <h3 class="month_title">July</h3>
												<span class="cal_date current_date" date-value="18-07-2015">18</span>
												<span class="cal_date" date-value="19-07-2015">19</span>
												<span class="cal_date" date-value="20-07-2015">20</span>
												<span class="cal_date" date-value="21-07-2015">21</span>
												<span class="cal_date" date-value="22-07-2015">22</span>
												<span class="cal_date green" date-value="23-07-2015">23</span>
												<span class="cal_date green" date-value="24-07-2015">24</span>
												<span class="cal_date green" date-value="25-07-2015">25</span>
												<span class="cal_date green" date-value="26-07-2015">26</span>
												<span class="cal_date" date-value="27-07-2015">27</span>
												<span class="cal_date" date-value="28-07-2015">28</span>
												<span class="cal_date green" date-value="29-07-2015">29</span>
												<span class="cal_date green" date-value="30-07-2015">30</span>
												<span class="cal_date">01</span>
												<span class="cal_date">02</span>
												<span class="cal_date">03</span>
												<span class="cal_date green">04</span>
												<span class="cal_date green">05</span>
												<span class="cal_date">06</span>
												<span class="cal_date green">07</span>
												<span class="cal_date green">08</span>
												<span class="cal_date green">09</span>
												<span class="cal_date green">09</span>
												<span class="cal_date green">10</span>
												<span class="cal_date">11</span>
												<span class="cal_date">12</span>
												<span class="cal_date">13</span>
												<span class="cal_date green">14</span>
												<span class="cal_date green">15</span>
												<span class="cal_date">16</span>
												<span class="cal_date green">17</span>
												<span class="cal_date green">18</span>
												<span class="cal_date green">19</span>
												<span class="cal_date green">20</span>
												<span class="cal_date green">21</span>
												<span class="cal_date">11</span>
												<span class="cal_date">12</span>
												<span class="cal_date">13</span>
												<span class="cal_date green">14</span>
												<span class="cal_date green">15</span>
												<span class="cal_date">16</span>
												<span class="cal_date green">17</span>
												<span class="cal_date green">18</span>
												<span class="cal_date green">19</span>
												<span class="cal_date green">20</span>
												<span class="cal_date green">21</span>
												<span class="cal_date">22</span>
												<span class="cal_date">23</span>
												<span class="cal_date">24</span>
												<span class="cal_date green">25</span>
												<span class="cal_date green">26</span>
												<span class="cal_date">27</span>
												<span class="cal_date green">28</span>
												<span class="cal_date green">29</span>
												<span class="cal_date green">30</span>
												<span class="cal_date green">01</span>
												<span class="cal_date green">02</span>
												</div>
										</div>
								</div>
							</div>
							  <!-- Controls -->
							  <a class="left carousel-control" href="#">
								<span class="glyphicon glyphicon-triangle-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a>
							  <a class="right carousel-control" href="#">
								<span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							  </a>	
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
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved"  >
												<div class="cell_room_addition-info">
												<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
												<span class="glyphicon right" aria-hidden="true">P</span>
												</div>
													<a href="#" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 1<br/>
													[Single][3]
													</a>
													<div class="room_shift" draggable="true">
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</div>
													
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
												<div class="cell_room_addition-info">
													<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>
													<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 2<br/>
													[Single][3]
													</a>
													<div class="room_shift shift_border">
													<span class="customer_name">DROP HERE</span>
													</div>
													
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Room 3<br/>
													[Single][3]
													</a>
													<div class="room_shift" draggable="true">
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</div>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
												<div class="cell_room_addition-info">
													<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
													<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Room 4<br/>
													[Single][3]
													</a>
													<div class="room_shift shift_border">
														<span class="customer_name">DROP HERE</span>
													</div>
													
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
												<div class="cell_room_addition-info">
													<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
													<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 5<br/>
													[Single][3]
													
													<div class="room_shift" draggable="true">
														<span class="customer_name">Paul Walker</span>
														<span class="customer_phone">+49-888-88888</span>
													</div>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Rooms 6<br/>
													[Suite][4]									
													</a>
	
														<div class="room_shift shift_border">
																<span class="customer_name">DROP HERE</span>
														</div>
													
														
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Room 7<br/>
													[double]<br/>
													4
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Room 8<br/>
													[Single][3]
													</a>
														<div class="room_shift shift_border">
															<span class="customer_name">DROP HERE</span>
														</div>
													
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Room 9<br/>
													[Single][2]
														<div class="room_shift" draggable="true">
														<span class="customer_name">James Walker</span>
														<span class="customer_phone">+49-888-88888</span>
														</div>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Room 10<br/>
													[Single][3]
													</a>
														<div class="room_shift shift_border" draggable="true">
														<span class="customer_name">DROP HERE</span>
														
														</div>
													
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
												<div class="cell_room_addition-info">
													<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
														<span class="glyphicon right" aria-hidden="true">R</span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 11<br/>
													[Single][1]
													<div class="room_shift" draggable="true">
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</div>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation_manual').style.display='block';document.getElementById('fade').style.display='block'">
													Room 12<br/>
													[Single][6]
													</a>
													<div class="room_shift shift_border">
													<span class="customer_name">DROP HERE</span>
													</div>
												</div>
											</div>										
									  </div>
									</div><!-- ends item-->
									<div class="item">
									  <div class="carousel-caption">
												<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
												<div class="cell_room_addition-info">
												<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
												<span class="glyphicon right" aria-hidden="true">P</span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 13<br/>
													[Single]<br/>
													3
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
												<div class="cell_room_addition-info">
													<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>
													<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 14<br/>
													[Single]<br/>
													3
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 15<br/>
													[Single]<br/>
													3
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
												<div class="cell_room_addition-info">
													<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
													<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 16<br/>
													[Single]<br/>
													3
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
												<div class="cell_room_addition-info">
													<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
													<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 17<br/>
													[Single]<br/>
													3
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 18<br/>
													[Suite]<br/>
													4
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 19<br/>
													[double]<br/>
													4
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 20<br/>
													[Single]<br/>
													3
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 21<br/>
													[Single]<br/>
													2
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info_green" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 22<br/>
													[Single]<br/>
													3
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin reserved">
												<div class="cell_room_addition-info">
													<!--span class="glyphicon glyphicon-ok left" aria-hidden="true"></span-->
														<span class="glyphicon right" aria-hidden="true">R</span>
												</div>
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 23<br/>
													[Single]<br/>
													1
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</a>
												</div>
											</div>
											<div class="col-xs-3 col-sm-3 col-xs-3 nopadding book_info">
												<div class="cell_room_admin booked">
													<a href="javascript:void(0)" class="room_info" onclick="document.getElementById('myreservation').style.display='block';document.getElementById('fade').style.display='block'">
													Room 24<br/>
													[Single]<br/>
													6
													<span class="customer_name">Paul Walker</span>
													<span class="customer_phone">+49-888-88888</span>
													</a>
												</div>
											</div>										
										
																	
									  </div>
									</div>
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
	
	</div>
		<ul class="footer_menu">
		<li><a href="#">Footer1</a></li>
		<li><a href="#">Admin</a></li>
		<li><a href="#">Footer2</a></li>
		<li><a href="#">Help</a></li>
	</div>

	<!-- ends-->
	<div class="col-xs-5 col-sm-3 col-xs-12">
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
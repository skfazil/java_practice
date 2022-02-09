<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="tag"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html> 
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Event Service Provider View</title>	
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
		<link href="css/eStyle.css" rel="stylesheet">
		<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css">
		<!--link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900' rel='stylesheet' type='text/css'-->
		<link rel="stylesheet" href="css/non-responsive.css">
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	   <script type="text/javascript" src="js/bootstrap.min.js"></script>
	   	<script type="text/javascript" src="js/jquery.validate.js"></script>
	    <!-- Google Map API Key Source -->
		<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<!-- Google Map  Source -->
	   	<script type="text/javascript" src="js/gmaps.js"></script>
		<script src="js/eCustomjs.js"></script>
		<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
		<script src="js/scroll.js"></script>
		
		
			<script type="text/javascript">
		  $(document).ready(function () {
			  var mode = $("#mode").val();
				if (mode != '') {
					$('#myprofile').show();
				}
				
				
				 $('#adminWhiteList').click(function(){
			      		adminWhiteList();
			      	 });
				 
				 $('#whiteLstGOBtn').click(function(){
				
		     		 var userName = $('#whiteListInput').val();
		     		 
		     		 $.ajax({
		     			 url   : 'getEventWhitCustmrRecrd.htm?userName='+userName,
		     			 type  : 'POST',
		     			 success : function(data){
		     				 var response = jQuery.parseJSON(data);
		     				 var html = '';
		     				 if(response.statusMsg == 'no customer exists in white list'){
		     					 if(userName.length == 0){
		     						//$('#adminWhteLstExprtBtn').prop('disabled', true);
		     						var msg = 'please enter customer name';
		     						$('#deleteWhteUsrStatusMsg').html(msg);
						 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
						 			$('#whiteListInput').val('');
						 			adminWhiteList();
						 			$('#adminWhtLst').show();
		     					 }else{
		     						$('#adminWhteLstExprtBtn').prop('disabled', true);
		     						 var msg = 'no customer exists in white list';
		     						$('#deleteWhteUsrStatusMsg').html(msg);
						 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
						 			$('#whiteListInput').val('');
						 			$('#adminWhtLst').hide();
		     					 }
		     					 event.preventdefault();
		     				 }else{
		     					if(response.userId != null){
		     						html += '<div class="panel panel-default">';
	         						if(i ==0){
	         							html += '<div class="panel-heading" role="tab" id="headingOne1">';
	         						}else{
	         							html += '<div class="panel-heading" role="tab" id="headingOne">';
	         						}
		         					html += '<h4 class="panel-title">';
		         					var colpse = 'collapse'+i;
		         					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		         					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response.userName+'';
		         					html += '</a>';
		         					
		         					html += '<a class="right btn btn-default rmveWhteCustmr" type="button" id="" style=" margin-left: 20px; margin-top: -10px;"  value="'+response.userId+'">';
		         					html += '<input type="hidden" value="'+response.userId+'"/>';
		         					html += 'Remove</a>';
		         					
		         					html += '<a class="right">'+response.email+'</a>';
		         					html += '<a class="right" style="padding-right:20px;">'+response.phoneNumber+'</a>';
		         					html += '</h4>';
		         					html += '</div>';
		         					if(i == 0){
	             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
	             					}else{
	             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
	             					}
		         					html += '<div class="panel-body">';
		         					html += '<div class="col-md-12">Since : '+response.startDate+'</div>';
		         					html += '</div>';
		         					html += '</div>';
		         					html += '</div>';
		     					}else if(response.guestId != null){
		     						html += '<div class="panel panel-default">';
	         						if(i ==0){
	         							html += '<div class="panel-heading" role="tab" id="headingOne1">';
	         						}else{
	         							html += '<div class="panel-heading" role="tab" id="headingOne">';
	         						}
		         					html += '<h4 class="panel-title">';
		         					var colpse = 'collapse'+i;
		         					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		         					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response.userName+'';
		         					html += '</a>';
		         					
		         					html += '<a class="right btn btn-default rmveWhteGuestCustmr" style=" margin-left: 20px; margin-top: -10px;" value="'+response.guestId+'">';
		         					html += '<input type="hidden" value="'+response.guestId+'"/>';
		         					html += 'Remove</a>';
		         					
		         					html += '<a class="right">'+response.email+'</a>';
		         					html += '<a class="right" style="padding-right:20px;">'+response.phoneNumber+'</a>';
		         					html += '</h4>';
		         					html += '</div>';
		         					if(i == 0){
	             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
	             					}else{
	             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
	             					}
		         					html += '<div class="panel-body">';
		         					html += '<div class="col-md-12">Since : '+response.startDate+'</div>';
		         					html += '</div>';
		         					html += '</div>';
		         					html += '</div>';
		     					}
		     					
		             				$('#adminWhtLst').html(html);
		             				$('#adminWhtLst').show();
		             				$('#admin_white_list').show();
		     					
		             				//enable Export Btn.
		             				$('#adminWhteLstExprtBtn').prop('disabled', false);
		     				 }
		     				 
		     			 }
		     		 });
		     	 });
				 
				 
				 jQuery(document).on('click', ".rmveWhteCustmr", function(){
		         	 var userId = jQuery(this).find('input').val();
		         	
		         	 $.ajax({
		         		url  : "removeEventWhteCustmrFrmLst.htm",
		         		type : 'GET',
		         		data : {userId:userId},
		         		success : function(data){
		         			if(data === 'true'){
		         				var msg = 'customer has been deleted from white list';
		         				$('#deleteWhteUsrStatusMsg').html(msg);
					 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
					 			adminWhiteList();
		         			}else{
		         				var msg = 'customer has failed to deleted from white list';
		         				$('#deleteWhteUsrStatusMsg').html(msg);
					 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
					 			adminWhiteList();
		         			}
		         		}
		         	 });
		     	 });
				 
				 
				 jQuery(document).on('click', ".rmveWhteGuestCustmr", function(){
		         	 var userId = jQuery(this).find('input').val();
		         	 
		         	 $.ajax({
		             		url  : "removeEventWhteGuestUsrFrmLst.htm.htm",
		             		type : 'GET',
		             		data : {userId:userId},
		             		success : function(data){
		             			if(data === 'true'){
		             				var msg = 'customer has been deleted from white list';
		             				$('#deleteWhteUsrStatusMsg').html(msg);
						 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
						 			adminWhiteList();
		             			}else{
		             				var msg = 'customer has failed to deleted from white list';
		             				$('#deleteWhteUsrStatusMsg').html(msg);
						 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
						 			adminWhiteList();
		             			}
		             		}
		             	 });
		     	 });
				 
				 
				 $('#whteUsrCSVBtn').click(function(){
		     		 var userName = $('#whiteListInput').val();
		     		 if(userName !=''){
		     			 document.location.href = "exportEventWhiteCustmrCSV.htm?userName="+userName;
			          	 }else{
			          		document.location.href = "exportAllEventWhiteCustmrCSV.htm";
			          	 }
		     	 });
				 
				 

				 
					$('#adminBlckList').click(function(){
			       	 adminBlackList();
			        });
					
					 $('#blckGOBtn').click(function(){
			      		
			     		 var userName = $('#blckLstSearchInput').val();
			     		
			     		 $.ajax({
			     			url   : 'getEventBlackCustmrRecrd.htm?userName='+userName,
			     			type  : 'POST',
			     			success : function(data){
			     				var response =  jQuery.parseJSON(data);
			     				var html = '';
			     				if(response.statusMsg == 'no customer exists in black list'){
			     					if(userName.length == 0){
			     						//$('#adminBlackLstExprtBtn').prop('disabled', true);
			     						var msg = 'please enter customer name';
			     						$('#deleteBlackUsrStatusMsg').html(msg);
			     						$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
			     						 adminBlackList();
			     						$('#blckCustmrsLst').show(); 
			     					}else{
			     					$('#adminBlackLstExprtBtn').prop('disabled', true);
			     						var msg = 'no customer exists in black list';
			     						$('#deleteBlackUsrStatusMsg').html(msg);
			     						$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
			     						$('#blckLstSearchInput').val('');
			     						$('#blckCustmrsLst').hide(); 
			     					}
			     					 event.preventdefault();
			     				 }else{
			         				 if(response.userId  != null){
				         					html += '<div class="panel panel-default">';
			         						if(i ==0){
			         							html += '<div class="panel-heading" role="tab" id="headingOne1">';
			         						}else{
			         							html += '<div class="panel-heading" role="tab" id="headingOne">';
			         						}
			             					html += '<h4 class="panel-title">';
			             					var colpse = 'collaps'+i;
			             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
			             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response.userName+'';
			             					html += '</a>';
			             					
			             					html += '<a class="right btn btn-default rmveBlackCustmr" style="margin-left: 20px;margin-top: -10px;" href="#" id="blckUsrId" value="'+response.userId+'">';
			             					html += '<input type="hidden" value="'+response.userId+'"/>';
			             					html += 'Remove</a>';
			             					
			             					html += '<a class="right">'+response.email+'</a>';
			             					html += '<a class="right" style="padding-right:20px;">'+response.phoneNumber+'</a>';
			             					html += '</h4>';
			             					html += '</div>';
			             					if(i == 0){
			             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
			             					}else{
			             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
			             					}
			             					html += '<div class="panel-body">';
			             					html += '<div class="col-md-6">Since : '+response.strtDate+'</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
			         					 }else if(response.guestId != null) {
			         						html += '<div class="panel panel-default">';
			         						if(i ==0){
			         							html += '<div class="panel-heading" role="tab" id="headingOne1">';
			         						}else{
			         							html += '<div class="panel-heading" role="tab" id="headingOne">';
			         						}
			             					html += '<h4 class="panel-title">';
			             					var colpse = 'collaps'+i;
			             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
			             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response.userName+'';
			             					html += '</a>';
			             					
			             					html += '<a class="right btn btn-default rmveBlackGuestCustmr" style="margin-left: 20px;margin-top: -10px;"  href="#"  id="blckGustUsrId" value="'+response.guestId+'">';
			             					html += '<input type="hidden" value="'+response.guestId+'"/>';
			             					html += 'Remove</a>';
			             					
			             					html += '<a class="right">'+response.email+'</a>';
			             					html += '<a class="right" style="padding-right:20px;">'+response.phoneNumber+'</a>';
			             					html += '</h4>';
			             					html += '</div>';
			             					if(i == 0){
			             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
			             					}else{
			             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
			             					}
			             					html += '<div class="panel-body">';
			             					html += '<div class="col-md-6">Since : '+response.strtDate+'</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
			         					 }
			         				$('#blckCustmrsLst').html(html);
			         				$('#blckCustmrsLst').show(); 
			         				$('#admin_black_list').show(); 
			         				//enable Export Btn...
			         				$('#adminBlackLstExprtBtn').prop('disabled', false);
			     				 }
			     				
			     				
			     			}
			     			
			     		});
			     	 });
			     	 
			     	 $('#blckUsrCSVBtn').click(function(){
			     		var userName = $('#blckLstSearchInput').val();
			     		 if(userName !=''){
			     			document.location.href = "exportEventBlckCustmrCSV.htm?userName="+userName;
			          	 }else{
			          		document.location.href = "exportAllEventBlckCustmrCSV.htm";
			          	 }
			     	 });
			     	 
			     	jQuery(document).on('click', ".rmveBlackCustmr", function(){
			        	 var userId = jQuery(this).find('input').val();
			        	 
			        	 $.ajax({
			            		url   : 'removeEventBlackUser.htm',
			            		type  : 'GET',
			            		data  :  {userId:userId},
			            		success : function(data){
			            			if(data === 'true'){
			            				var msg = 'customer has been deleted from Black list';
			            				$('#deleteBlackUsrStatusMsg').html(msg);
							 			$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
							 			adminBlackList();
			            			}else{
			            				var msg = 'customer has failed to deleted from Black list';
			            				$('#deleteBlackUsrStatusMsg').html(msg);
							 			$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
							 			adminBlackList();
			            			}
			            		}
			        	});
			        	 
			    	});
			    	
			    	jQuery(document).on('click', ".rmveBlackGuestCustmr", function(){
			        	 var userId = jQuery(this).find('input').val();
			        	//alert('guest id'+userId);
			        	 $.ajax({
			        		url   : 'removeEventBlackGuestUser.htm',
			        		type  : 'GET',
			        		data  :  {userId:userId},
			        		success : function(data){
			        			
			        			if(data === 'true'){
			        				var msg = 'customer has been deleted from Black list';
			        				$('#deleteBlackUsrStatusMsg').html(msg);
						 			$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
						 			adminBlackList();
			        			}else{
			        				var msg = 'customer has failed to deleted from Black list';
			        				$('#deleteBlackUsrStatusMsg').html(msg);
						 			$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
						 			adminBlackList();
			        			}
			        			
			        		}
			        	 });
			    	});
					 

					 $('#custmrsEmailList').click(function(){
						 customerEmailList();
			         });
					 
					 
					 $('#custmrEmailListSearchBtn').click(function(){
			        	 
			        	$('#custmrEmailLstExprtBtn').prop('disabled', false);
			        	 var custmrName = $('#custmrEmailListId').val();
			        	 $.ajax({
			        		url   : 'getEventCustomerInfo.htm?custmrName='+custmrName,
			        		type : 'POST',
			        		success : function(data){
			        			var response = jQuery.parseJSON(data);
			        			var html = '';
			        			
			        			if(response.status != "no record with this user name" ){
			        				html += '<div class="col-md-12 listpad" id="custmrEmailList">';
			        				html += '<div class="col-md-4 nopadding">'+response.userName+'</div>';
			            			html += '<div class="col-md-4">'+response.email+'</div>';
			            			html += '<div class="col-md-4">'+response.phoneNumber+'</div>';
			            			html += '</div>';
			            		
			            			$('#custmrEmailList').html(html);
			            			$('#custmrEmailList').show();
			            			$('#admin_email_list').show();
			        			}else{
			        				if(custmrName.lenght == 0 || custmrName == ''){
			        					var msg = 'please enter customer name to search';
			        					$('#adminEmailLstStatusMsg').html(msg);
			        					$('#adminEmailLstMsgStatusDiv').show().fadeOut(4000);
			        					customerEmailList();
			        					$('#custmrEmailList').show();
			        				}else{
			        					var msg  = "customer name is not in list";
			        					 $('#custmrEmailLstExprtBtn').prop('disabled', true);
			        					$('#adminEmailLstStatusMsg').html(msg);
			        					$('#adminEmailLstMsgStatusDiv').show().fadeOut(4000);
			        					$('#custmrEmailListId').val('');
			        					$('#custmrEmailList').hide();
			        				}
			        				
			        			}	
			        			
			        		}
			        	 });
			         });
					 
					   $('#custmrEmailCsvBtn').click(function(){
			          	 var custmrName = $('#custmrEmailListId').val();
			          	 //document.location.href = "exportEventCustmrEmailCSV.htm?un="+custmrName;
			          	 if(custmrName !=''){
			          		 document.location.href = "exportEventCustmrEmailCSV.htm?un="+custmrName;
			          	 }else{
			          		document.location.href = "exportAllEventCustmrEmailCSV.htm";
			          	 }
			          	 
			           });
					 
					   

						
					 $('#adminCustmrList').click(function(){
						 adminCustomerList();
			         });
					 
					 var userName = '';
			     	 $('#searchBtn').click(function(event){
			     		 var custmrName = $('#custmrName').val();
			     		$.ajax({
			     			url : 'getEventCustomerInfo.htm?custmrName='+custmrName,
			     			type : 'POST',
			     			success : function(data, status, xhr){
			     				var response = jQuery.parseJSON(data);
			     				if( response.status != "no record with this user name" ){
			     					var html = '';
			     					html += '<div class="panel panel-default staff-container">';
			        				html += '<div class="panel-heading" role="tab" id="headingOne">';
			        				
			        				var colapse = 'collapse'+i;
			        				html += '<h4 class="panel-title">';
			        				html += ' <a data-toggle="collapse" data-parent="#accordion" class = "usrBookedRecrdId"  href="#'+colapse+'" aria-expanded="true" aria-controls="collapseOne" >';
			        				html += ' <input type="hidden" value="'+response.userId+','+colapse+'" id="customerName">';
			        				html += ' <span class="glyphicon glyphicon-user" aria-hidden="true">';
			        				html += '</span> ';
			        				html += ''+response.userName+'';
			        				html += '</a>';
			        				html += '<a class="right" id="mailId">';
			        				html += ''+response.email+'';
			        				html += '</a>';
			        				html += '<a class="right" style="padding-right:20px;" id="phneNumbr">';
			        				html += ''+response.phoneNumber+'';
			        				html += '</a>';
			        				html += '</h4>';
			        				html += '</div>';
			        				
			        				html += '<div id="'+colapse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">';
			        			    html += '<div class="panel-body" id="reservtn_toggle">';
			        			    
			        			    html += '</div>';
			        			    html += '</div>';
			        				
			        				html += '</div>';
			        				
			        			$('#custmrReservtnDataSection').html(html);
			        			$('#custmrReservtnDataSection').show();
			        			$('#admin_customer_list').show();
			        			$('#custmtrLstExprtBtn').prop('disabled', false);
			     				}else{
			     					var status  = "customer name is not in list";
			     					if(custmrName.length == 0){
			     						//$('#custmtrLstExprtBtn').prop('disabled', true);
			     						var msg = 'please enter customer name to search';
			     						
			     						$('#adminCustmrsMsg').html(msg);
							 			$('#adminCustmrsMsgDiv').show().fadeOut(4000);
							 			adminCustomerList();
							 			$('#custmrReservtnDataSection').show();
			     					}else{
			     					  $('#custmtrLstExprtBtn').prop('disabled', true);
			     						$('#adminCustmrsMsg').html(status);
							 			$('#adminCustmrsMsgDiv').show().fadeOut(4000);
							 			$('#custmrName').val('');
							 			$('#custmrReservtnDataSection').hide();
			     					}
			         					
			     				}
			     			}
			     		});
			     	}); 
			     	 
			     	 $('#custmrLstCSV').click(function(){
			     		 var userName = $('#custmrName').val();
			     		
			     		if(userName !=''){
			     			 document.location.href = "exportEventCustomerLstCSV.htm?userName="+userName;
			          	 }else{
			          		document.location.href = "exportAllEventCustomerLstCSV.htm";
			          	 }
			     	 });
			     	 
			     	 
			     	  //sohowing customer reservation details on popup.
			         $('#custmrReservtns').click(function(){
			        	 customerReservationList();
			         });
			     	  
			         //customer reservation details search option.
			         $('#custmrReservtnGOBtn').click(function(){
			        	 var cstmrName = $('#custmrReservtnInputVal').val();
			        	
			        	 $.ajax({
			        		 url   : 'getEventsCustomerReservtnDetails.htm?cstmrName='+cstmrName,
			        		 type  : 'POST',
			        		 success : function(data){
			        			 var response = jQuery.parseJSON(data);
			        			 var html = '';
			        			 
			        			 if(response == 'user name is not exits in list'){
			        				 if(cstmrName.length == 0){
			        					// $('#CustmrResrvtnExprtBtn').prop('disabled', true);
			        					 var msg = 'please enter customer name to search';
			        					 $('#adminReservtnMsg').html(msg);
			        					 $('#adminReservtnMsgDiv').show().fadeOut(4000);
			        					 customerReservationList();
			        					 $('#custmrHstryRecrds').show();
			        				 }else{
			        				   $('#CustmrResrvtnExprtBtn').prop('disabled', true);
			        					 var msg = 'Customer name is not exists in list';
			        					 $('#adminReservtnMsg').html(msg);
			        					 $('#adminReservtnMsgDiv').show().fadeOut(4000);
			        					 $('#custmrReservtnInputVal').val('');
			        					 $('#custmrHstryRecrds').hide();
			        				 }
			        			 }else{
			        				 for(i in response){
			                				if(response[i].userId != null){
			                					html += '<div class="reservation-data-section" id="custmrRecord">';
				                				html += '<div class="col-xs-6 col-md-6" >';
				                				html += '<p>';
				                				html += '<strong>'+response[i].userName+'</strong><br/>';
				                				html += ''+response[i].phoneNumber+'<br/>';
				                				html += ''+response[i].email+'';
				                				html += '</p>';
				                				html += '</div>';
				                				
				                				html += '<div class="col-xs-6 col-md-6">';
				                				html += '<p>';
				                				html += ''+response[i].referenceNumber+'<br/>';
				                				html += 'Start Time  : '+response[i].startTime+'<br/>';
				                				html += 'End Time : '+response[i].endTime+'<br/>';
				                				html += 'Period : '+ response[i].duration+' Hour(s)';
				                				html += '</p>';
				                				html += '</div>';
				                				html += '</div>';
			                				}else {
			                					html += '<div class="reservation-data-section" id="guestRecord">';
				                				html += '<div class="col-xs-6 col-md-6" >';
				                				html += '<p>';
				                				html += '<strong>'+response[i].userName+'</strong><br/>';
				                				html += ''+response[i].phoneNumber+'<br/>';
				                				html += ''+response[i].email+'';
				                				html += '</p>';
				                				html += '</div>';
				                				
				                				html += '<div class="col-xs-6 col-md-6">';
				                				html += '<p>';
				                				html += ''+response[i].referenceNumber+'<br/>';
				                				html += 'Start Time  : '+response[i].startTime+'<br/>';
				                				html += 'End Time  : '+response[i].endTime+'<br/>';
				                				html += 'Period : '+response[i].duration+' Hour(s)';
				                				html += '</p>';
				                				html += '</div>';
				                				html += '</div>';
			                				}//else close.
			                				
			                			}//for function close.
			                			$('#custmrHstryRecrds').html(html);
			                			 $('#custmrHstryRecrds').show();
			                			$('#admin_myreservation_details').show();
			                			
			                			//enable Export Btn.
			                			$('#CustmrResrvtnExprtBtn').prop('disabled', false);
			        			 }
			        			 
			        		 }
			        	 });
			         });
			        
			         $('#custmrReservtnCSV').click(function(){
			        	var userName = $('#custmrReservtnInputVal').val();
			        	
			        	if(userName !=''){
			        		document.location.href = "exportEventCustmrReservntHstryCSV.htm?un="+userName;
			          	 }else{
			          		document.location.href = "exportAllEventCustmrReservntHstryCSV.htm";
			          	 }
			         });
			     	  

			 		$('#addToWhitLstCat').click(function(){
			     		 $('#roomNum').val('');
			     		 $.ajax({
			     			 url   : 'getEventAdminCatgryLst.htm',
			     			 type  : 'POST',
			     			 success : function(data){
			     				 var response = jQuery.parseJSON(data);
			     				 var html = '';
			     				 html +=  '<option value="0">Select Tour</option>';
			     				 for(i in response){
			     					 html += '<option value="'+response[i].eventId+'">'+response[i].eventName+'</option>'
			     				 }
			     				 $('#roomCategryLst').html(html);
			     				 $('#admin_addwhite_list').show();
			     				document.getElementById('fade').style.display='block';
			     			 }
			     		 });
			     	 });
			 		
			 		
			 		 $('#roomCategryLst').change(function() {
			 			
			      		 var catVal  = $('#roomCategryLst').val();
			      		 
			      		 $.ajax({
			      			url  : 'getEventAdminRoomsLst.htm?catVal='+catVal,
			      			type  : 'POST',
			      			success : function(data){
			      				var response = jQuery.parseJSON(data);
			      				console.log('jsonww :'+JSON.stringify(data));
			      				 
			      				  var html = '';
			      				  html +=  '<option value="0">Select Guide</option>';
			      				  for(i in response){
			      					html += '<option value="'+response[i].guideName+'">'+response[i].guideName+'</option>'
			      				 } 
			      				  $('#roomNum').html(html);
			      			}
			      		 });
			      	 });
			 		 
			 		 
			 		 
			 		 $('#adminAddBtn').click(function(){
			      		 var guideName = $('#roomNum').val();
			      		 var eventId  = $('#roomCategryLst').val();
			      		 $.ajax({
			      			 url   :  'addEventTOWhteLst.htm?eventId='+eventId+'&guideName='+guideName,
			      			 type  : 'POST',
			      			 success : function(data){
			      				 var response = data;
			      				 if(response == guideName){
			      					 var status = 'Event Guide : '+guideName+' added to white list category successfully';
			      					 $('#addRoomToWhteStstusMsg').html(status);
			      					 $('#addRoomToWhteStstusMsg').show().fadeOut(4000);
			      					
			      				 }
			      			 }
			      		 });
			      	 });
		  });//document ready close
		function getProfile() {
            document.location.href = "getEventUserProfile.htm";
        }
		 function getMyReservations() {
			 //alert('response first::');
			   $.ajax({
					url : "getMyEventReservations.htm",
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
                			url   : 'bookingEventHistory.htm', 
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
			document.location.href = "changeEvnetOrg.htm?eventOrgId="+eventOrgId;
		}
		function changeHotel(hotelId){
			document.location.href = "changeHotel.htm?hotelId="+hotelId;
		}
		jQuery(document).on('click', "#changeReservation", function(){
			var hotelId = jQuery(this).find('input').val();
			changeHotel(hotelId);
		});
       
     /*   jQuery(document).on('click', "#cancelReservationBtn", function(){
    	   document.location.href = "returnToServiceEvent.htm";

       }); */
       
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
		
		</script>
		
		<!-- whitelist and blacklist user script starts -->
	<script>
		function getServiceWhiteList() {
		
			$.ajax({
				url : "getEventWhiteListCategoryCustmrs.htm",
				type : 'POST',
				success : function(data) {
					var response = jQuery.parseJSON(data);
					var html = '';
					$('#whitelistdetails').empty();
					 for (i in response) {
						html += '<div class="col-xs-7 col-md-7">';
						html += '<p>';
						html += '<strong>Name of Restaurant</strong><br />';
						html += '' + response[i].hotelName + '';
						html += '<br />';
						html += '' + response[i].hotelAddress + ','+response[i].city+ '';
						html += '<br />';
						html += '' + response[i].phoneNumber +'';
						html += '<br />';
						html += '</p>';
						html += '</div>';
						html += '<div class="col-xs-5 col-md-5">';
						html += '<p>';
						html += 'Since : ' + response[i].startDate + '';
						html += '<br /> ';
						html += '</p>';
						html += '</div>';
					} 
					$('#whitelistdetails').html(html);
					$('#mywhite_list').show();
				}

			});

		}
		 function getServiceBlackList() {
			
				$.ajax({					
					url : "getEventBlackListCategoryCustmrs.htm",
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						$('#blacklistdetails').empty();
						for (i in response) {
							html += '<div class="col-xs-7 col-md-7">';
							html += '<p>';
							html += '<strong>Name of Restaurant</strong><br />';
							html += '' + response[i].hotelName + '';
							html += '<br />';
							html += '' + response[i].hotelAddress + ','+response[i].city+ '';
							html += '<br />';
							html += '' + response[i].phoneNumber +'';
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
		 
		  function	getEmailVisible() {	 
			   
			   $.ajax({
			          url: "getEventEmailListDet.htm",
			          type: 'GET',         
			          success: function(data){         
			          var response  = jQuery.parseJSON(data); 
			          var html = '';
			          $('#emailVisibleForm').empty();
			          
			          for(i in response){
			        	  html += '<div class="reservation-data-section">';
			          html += '<div class="col-xs-5 col-md-5">';
			          html += '<p>';
			          html += '<strong>Name of Restaurant</strong><br />';
			          html += ''+response[i].eventOrgName+'';
			          html += '<br />';
			          html += ''+response[i].eventOrgAddress+'';
			          html += '<br />';
			          html += ''+response[i].eventOrgPhoneNumber+','+response[i].eventOrgCity+','+response[i].eventOrgPostalCode+'';
			          html += '<br />';
			         
			          html += '</p>';
			          html += '</div>';
			          html += '<div class="col-xs-4 col-md-4">';
			          html += '<p>';
			          html += 'Since : '+response[i].strtDate+'';
			          html += '<br /> ';              
			          html += '</p>';
			          html += '</div>';
			        
			          html += '<div class="col-xs-3 col-md-3">';
			          html += '<button class="btn btn-default" type="button" onclick="getStopSharing()"> Stop Sharing </button>';        
			                
			          html += '</div>';
			          html += '<br /> ';  
			          html+='</div>'; 
			            
			         
			         }// for loop close.
			        
			     	  $('#emailVisibleForm').html(html);
			     	  $('#myemail_list').show();
			         }
			     
			      });	
			  }
		   
		   function getStopSharing() { 
				 
				 $.ajax({
			            url: "getEventOrgUpdateEmail.htm",
			            type: 'GET',	           
			            success: function(data){
			            	var response=data;
			            	 $('#emailreservation').empty();
			            	 if(response.emailShare="inactive"){ 
			            	$('#myemail_list').hide();
			            	$("#alertMsgPopUp3").show();
		                    $("#alertPopSpanId").empty();
		                    $("#alertPopSpanIdGreen").append(emailShare);
			            	}
			            	 else{
			            		$('#myemail_list').show();
			            	} 
			            }
			        });	
			 }
		   
		   function adminWhiteList(){

         		 $('#whiteListInput').val('');
         		 $('#adminWhteLstExprtBtn').prop('enabled', true);
         		 
         		 $.ajax({
         			url  : 'eventWhiteListAdmin.htm',
         			type : 'POST',
         			success : function(data){
         				var response =  jQuery.parseJSON(data);
         				var html = '';
         				if(response == 'there is no Info about white customer exits in table'){
         					alert('there is no Info about white customer exits in list');
         				}else{

	             				for(i in response){
	             					if(response[i].userId != null){
	             						html += '<div class="panel panel-default">';
	             						if(i ==0){
	             							html += '<div class="panel-heading" role="tab" id="headingOne1">';
	             						}else{
	             							html += '<div class="panel-heading" role="tab" id="headingOne">';
	             						}
		             					html += '<h4 class="panel-title">';
		             					var colpse = 'collapse'+i;
		             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response[i].userName+'';
		             					html += '</a>';
		             					
		             					html += '<a class="right btn btn-default rmveWhteCustmr" type="button" id="" style=" margin-left: 20px; margin-top: -10px;"  value="'+response[i].userId+'">';
		             					html += '<input type="hidden" value="'+response[i].userId+'"/>';
		             					html += 'Remove</a>';
		             					
		             					html += '<a class="right">'+response[i].email+'</a>';
		             					html += '<a class="right" style="padding-right:20px;">'+response[i].phoneNumber+'</a>';
		             					html += '</h4>';
		             					html += '</div>';
		             					if(i == 0){
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
		             					}else{
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
		             					}
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-12">Since : '+response[i].startDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					}else if(response[i].guestId != null){
	             						html += '<div class="panel panel-default">';
	             						if(i ==0){
	             							html += '<div class="panel-heading" role="tab" id="headingOne1">';
	             						}else{
	             							html += '<div class="panel-heading" role="tab" id="headingOne">';
	             						}
		             					html += '<h4 class="panel-title">';
		             					var colpse = 'collapse'+i;
		             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response[i].userName+'';
		             					html += '</a>';
		             					
		             					html += '<a class="right btn btn-default rmveWhteGuestCustmr" style=" margin-left: 20px; margin-top: -10px;" value="'+response[i].guestId+'">';
		             					html += '<input type="hidden" value="'+response[i].guestId+'"/>';
		             					html += 'Remove</a>';
		             					
		             					html += '<a class="right">'+response[i].email+'</a>';
		             					html += '<a class="right" style="padding-right:20px;">'+response[i].phoneNumber+'</a>';
		             					html += '</h4>';
		             					html += '</div>';
		             					if(i == 0){
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
		             					}else{
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
		             					}
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-12">Since : '+response[i].startDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					}
	             				}
	             				$('#adminWhtLst').html(html);
	             				$('#admin_white_list').show();
	             				document.getElementById('fade').style.display='block';
         				}
         					
         			}
         		 });
			   }
		   
		   function adminBlackList(){
				 
				 $('#blckLstSearchInput').val('');
            	 $('#adminBlackLstExprtBtn').prop('enabled', true);
            	 
            	$.ajax({
            		url : 'eventBlackListAdmin.htm',
            		type : 'POST',
            		success : function(data){
            			var response =  jQuery.parseJSON(data);
            			
            			if(response == 'there is no Info about black customer exits in list'){
         					alert('there is no Info about white customer exits in list');
         					
         				}else{
         					var html = '';
	             				for(i in response){
	             					 if(response[i].userId  != null){
	             						html += '<div class="panel panel-default">';
	             						if(i ==0){
	             							html += '<div class="panel-heading" role="tab" id="headingOne1">';
	             						}else{
	             							html += '<div class="panel-heading" role="tab" id="headingOne">';
	             						}
		             					html += '<h4 class="panel-title">';
		             					var colpse = 'collaps'+i;
		             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response[i].userName+'';
		             					html += '</a>';
		             					
		             					html += '<a class="right btn btn-default rmveBlackCustmr" style="margin-left: 20px;margin-top: -10px;" href="#" id="blckUsrId" value="'+response[i].userId+'">';
		             					html += '<input type="hidden" value="'+response[i].userId+'"/>';
		             					html += 'Remove</a>';
		             					
		             					html += '<a class="right">'+response[i].email+'</a>';
		             					html += '<a class="right" style="padding-right:20px;">'+response[i].phoneNumber+'</a>';
		             					html += '</h4>';
		             					html += '</div>';
		             					if(i == 0){
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
		             					}else{
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
		             					}
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-6">Since : '+response[i].strtDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					 }else if(response[i].guestId != null) {
	             						html += '<div class="panel panel-default">';
	             						if(i ==0){
	             							html += '<div class="panel-heading" role="tab" id="headingOne1">';
	             						}else{
	             							html += '<div class="panel-heading" role="tab" id="headingOne">';
	             						}
		             					html += '<h4 class="panel-title">';
		             					var colpse = 'collaps'+i;
		             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response[i].userName+'';
		             					html += '</a>';
		             					
		             					html += '<a class="right btn btn-default rmveBlackGuestCustmr" style="margin-left: 20px;margin-top: -10px;"  href="#"  id="blckGustUsrId" value="'+response[i].guestId+'">';
		             					html += '<input type="hidden" value="'+response[i].guestId+'"/>';
		             					html += 'Remove</a>';
		             					
		             					html += '<a class="right">'+response[i].email+'</a>';
		             					html += '<a class="right" style="padding-right:20px;">'+response[i].phoneNumber+'</a>';
		             					html += '</h4>';
		             					html += '</div>';
		             					if(i == 0){
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';	
		             					}else{
		             						html += '<div id="'+colpse+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">';
		             					}
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-6">Since : '+response[i].strtDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					 }
	             				 }
	             				$('#blckCustmrsLst').html(html);
	             				$('#admin_black_list').show(); 
	             				document.getElementById('fade').style.display='block';
         				}
            			
            		}
            	});
			   }
		   
		   function adminEmailList(){}
		   function adminCustomerList(){
			   $('#custmrName').val('');
	        	 $('#custmtrLstExprtBtn').prop('enabled', true);
	        	 $.ajax({
	        		url   : 'eventCustmrList.htm',
	        		type  : 'POST',
	        		success : function(data){
	        			var response =  jQuery.parseJSON(data);
	        			var html = '';
	        			for(i in response){
	        				html += '<div class="panel panel-default staff-container">';
	        				html += '<div class="panel-heading" role="tab" id="headingOne">';
	        				if(i == 1){}
	        				var colapse = 'collapse'+i;
	        				html += '<h4 class="panel-title">';
	        				html += ' <a data-toggle="collapse" data-parent="#accordion" class = "usrBookedRecrdId"  href="#'+colapse+'" aria-expanded="true" aria-controls="collapseOne" id="">';
	        				html += ' <input type="hidden" value="'+response[i].userId+','+colapse+'" id="customerName">';
	        				html += ' <span class="glyphicon glyphicon-user" aria-hidden="true">';
	        				html += '</span> ';
	        				html += ''+response[i].userName+'';
	        				html += '</a>';
	        				html += '<a class="right" id="mailId">';
	        				html += ''+response[i].email+'';
	        				html += '</a>';
	        				html += '<a class="right" style="padding-right:20px;" id="phneNumbr">';
	        				html += ''+response[i].phoneNumber+'';
	        				html += '</a>';
	        				html += '</h4>';
	        				html += '</div>';
	        				
	        				html += '</div>';
	        			}
	        			$('#custmrReservtnDataSection').html(html);
	        			$('#admin_customer_list').show();
	        			
	        			document.getElementById('fade').style.display='block';
	        		}
	        	 });
			   
		   }
		   function customerReservationList(){
			   
			   $('#custmrReservtnInputVal').val('');
	        	 $('#CustmrResrvtnExprtBtn').prop('enabled', true);

	        	 $.ajax({
	        		url   : 'eventcustmrsBookingHistory.htm',
	        		type  : 'POST',
	        		success : function(data){
	        			var response = jQuery.parseJSON(data);
	        			var html = '';
	        			
	        			if(response == "there is no customer history exists for this hotel"){
	        				var msg = 'there is no customer history exists for this hotel';
	        				$('#adminReservtnMsg').html(msg);
	        				$('#adminReservtnMsgDiv').show().fadeOut(4000);
	        			}else{
	        				for(i in response){
	            				
	            				if(response[i].userId != null){
	            					html += '<div class="reservation-data-section" id="custmrRecord">';
	                				html += '<div class="col-xs-6 col-md-6" >';
	                				html += '<p>';
	                				html += '<strong>'+response[i].userName+'</strong><br/>';
	                				html += ''+response[i].phoneNumber+'<br/>';
	                				html += ''+response[i].email+'';
	                				html += '</p>';
	                				html += '</div>';
	                				
	                				html += '<div class="col-xs-6 col-md-6">';
	                				html += '<p>';
	                				html += ''+response[i].referenceNumber+'<br/>';
	                				html += 'Start Time  : '+response[i].startTime+'<br/>';
	                				html += 'End Time : '+response[i].endTime+'<br/>';
	                				html += 'Period : '+ response[i].duration+' Hour(s)';
	                				html += '</p>';
	                				html += '</div>';
	                				html += '</div>';
	            				}else {
	            					html += '<div class="reservation-data-section" id="guestRecord">';
	                				html += '<div class="col-xs-6 col-md-6" >';
	                				html += '<p>';
	                				html += '<strong>'+response[i].userName+'</strong><br/>';
	                				html += ''+response[i].phoneNumber+'<br/>';
	                				html += ''+response[i].email+'';
	                				html += '</p>';
	                				html += '</div>';
	                				
	                				html += '<div class="col-xs-6 col-md-6">';
	                				html += '<p>';
	                				html += ''+response[i].referenceNumber+'<br/>';
	                				html += 'Start Time  : '+response[i].startTime+'<br/>';
	                				html += 'End Time  : '+response[i].endTime+'<br/>';
	                				html += 'Period : '+response[i].duration+' Hour(s)';
	                				html += '</p>';
	                				html += '</div>';
	                				html += '</div>';
	            				}//else close.
	            				
	            			}//for function close.
	            			$('#custmrHstryRecrds').html(html);
	            			$('#admin_myreservation_details').show();
	            			document.getElementById('fade').style.display='block';
	        			}
	        			
	        		}
	        	 });
		   }
		   function customerEmailList(){
				 $('#custmrEmailLstExprtBtn').prop('enabled', true);
		        	$('#custmrEmailListId').val('');
		        	  $.ajax({
		        		  url   : 'eventAdminCustmrList.htm',
		            		type  : 'POST',
		            		success : function(data){
		            			var response =  jQuery.parseJSON(data);
		            			var html = '';
		            			for(i in response){
		            				html += '<div class="col-md-12 listpad" id="custmrEmailList">';
		            				html += '<div class="col-md-4 nopadding">'+response[i].userName+'</div>';
		                			html += '<div class="col-md-4">'+response[i].email+'</div>';
		                			html += '<div class="col-md-4">'+response[i].phoneNumber+'</div>';
		                			html += '</div>';
		            			}
		            			
		            			$('#custmrEmailList').html(html);
		            			$('#admin_email_list').show();
		            			document.getElementById('fade').style.display='block';
		            		} 
		        	  });
		   }
		   
		</script>
		
		
		
		
		<script>
		var currDt = getCurrentDate();
		var onLoadFinish=false;
		function onLoad() {
			primaryDates(currDt);
			getStrtAndEndTime();
			getEventWidget(currDt);
			var fmtDt = formatDate(currDt);
		}
		
		function getCurrentDate(){
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
             var fullDate =  Day + '-' + Month + '-' + Year;
             return fullDate;
		 }
		
		function formatDate(date) {
			date = date.replace('-','/');
			date = date.replace('-','/');			
			return date;
		}
		
		function isHaveReservation(arr) {
			 for(var i=0;i<arr.length;i++) {
				 if(arr[i].haveReservation == true) {
					 return true;
				 }
			 }
			 return false;
		 }
		 
		function isHaveOneSeat(arr) {
			for(var i=0;i<arr.length;i++) {
				 if(arr[i].availableSeats > 0) {
					 return true;
				 }
			 }
			 return false;
		}
		
		function isHaveCountSeats(arr,seatCount) {
			for(var i=0;i<arr.length;i++) {
				 if(arr[i].availableSeats >= seatCount) {
					 return true;
				 }
			 }
			 return false;
		}
		
		function contains(arr , str) {
			 for(var i=0;i<arr.length;i++) {
				 if(arr[i].evntStrtTme == str) {
					 return arr[i];
				 }
			 }
			 return null;
		 }
		
		function resetPage() {
			document.location.href = "toChngeEvntReservation.htm";
		}
		
		function getStrtAndEndTime(){			 
			 
				var html = '';
				
				html += '<div class="cell timescale" id="zero">00:00</div>';
				html += '<div class="cell timescale" id="thirty">00:30</div>';
				html += '<div class="cell timescale" id="one">01:00</div>';
				html += '<div class="cell timescale" id="onethirty">01:30</div>';
				html += '<div class="cell timescale" id="two">02:00</div>';
				html += '<div class="cell timescale" id="twothirty">02:30</div>';
				html += '<div class="cell timescale" id="three">03:00</div>';
				html += '<div class="cell timescale" id="threethirty">03:30</div>';
				html += '<div class="cell timescale" id="four">04:00</div>';
				html += '<div class="cell timescale" id="fourthirty">04:30</div>';
				html += '<div class="cell timescale" id="five">05:00</div>';
				html += '<div class="cell timescale" id="fivethirty">05:30</div>';
				html += '<div class="cell timescale" id="six">06:00</div>';
				html += '<div class="cell timescale" id="sixthirty">06:30</div>';
				html += '<div class="cell timescale" id="seven">07:00</div>';
				html += '<div class="cell timescale" id="seventhirty">07:30</div>';		 				
				html += '<div class="cell timescale" id="eight">08:00</div>';
				html += '<div class="cell timescale" id="eightthirty">08:30</div>';
				html += '<div class="cell timescale" id="nine">09:00</div>';
				html += '<div class="cell timescale" id="ninethirty">09:30</div>';
				html += '<div class="cell timescale" id="ten">10:00</div>';
				html += '<div class="cell timescale" id="tenthirty">10:30</div>';
				html += '<div class="cell timescale" id="eleven">11:00</div>';
				html += '<div class="cell timescale" id="eleventhirty">11:30</div>';
				html += '<div class="cell timescale" id="twelve">12:00</div>';
				html += '<div class="cell timescale" id="twelvethirty">12:30</div>';
				html += '<div class="cell timescale" id="thirteen">13:00</div>';
				html += '<div class="cell timescale" id="thirteenthirty">13:30</div>';
				html += '<div class="cell timescale" id="fourteen">14:00</div>';
				html += '<div class="cell timescale" id="fourteenthirty">14:30</div>';
				html += '<div class="cell timescale" id="fifteen">15:00</div>';
				html += '<div class="cell timescale" id="fifteenthirty">15:30</div>';
				html += '<div class="cell timescale" id="sixteen">16:00</div>';
				html += '<div class="cell timescale" id="sixteenthirty">16:30</div>';
				html += '<div class="cell timescale" id="seventeen">17:00</div>';
				html += '<div class="cell timescale" id="seventeenthirty">17:30</div>';
				html += '<div class="cell timescale" id="eighteen">18:00</div>';
				html += '<div class="cell timescale" id="eighteenthirty">18:30</div>';
				html += '<div class="cell timescale" id="nineteen">19:00</div>';
				html += '<div class="cell timescale" id="nineteenthirty">19:30</div>';		 				
				html += '<div class="cell timescale" id="twenty">20:00</div>';
				html += '<div class="cell timescale" id="twentythirty">20:30</div>';
				html += '<div class="cell timescale" id="twentyone">21:00</div>';
				html += '<div class="cell timescale" id="twentyonethirty">21:30</div>';
				html += '<div class="cell timescale" id="twentytwo">22:00</div>';
				html += '<div class="cell timescale" id="twentytwothirty">22:30</div>';
				html += '<div class="cell timescale" id="twentythree">23:00</div>';
				html += '<div class="cell timescale" id="twentythreethirty">23:30</div>';
				
				$('#showStrtAndEndTimeDiv').html(html); 
				
				
			}
		
		function primaryDates(date) {
			var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
			
			$.ajax({
	 			url : "getPrimaryDates.htm?date="+date,
	 			type : 'POST',
	 			success : function(data) {
	 				var response = JSON.parse(data);
	 				var html = '';
	 				var seatCount = $('#seatCount').val();
	 	 			if(seatCount == '') {
	 	 				seatCount = '0';
	 	 			}
	 				
	 				html += '<div class="piece">';
	 				html += '<div class="small_square">';
	 				
	 				for(var i=0,key=Object.keys(response),ii=key.length;i<ii;i++) {
	 					if(i==0 || i==14) {
	 						var monthNum = key[i].slice(3,5);
              			 monthNum = (monthNum-1) ;
              			 jQuery.each( monthArray, function( i, mnthName ) {
                         	 	if(monthNum == i){
                         	 		html += '<span class="month_title">'+mnthName+'</span>';
                         	 	}
                         	});
	 					}
	 					var haveResrvtn = isHaveReservation(response[key[i]]);
	 					var minSeats = isHaveOneSeat(response[key[i]]);
	 					var countSeats = isHaveCountSeats(response[key[i]], seatCount);
	 					
	 					if(currDt == key[i]) {
		 					html += '<span class="cal_date current_date hiddenSpan">'+key[i].slice(0,2)+'';
	 						html += '<input type = "hidden" value="'+key[i]+'" />';
	 						html += '</span>';
	 					}
	 					else if(countSeats == true) {
	 						if(seatCount > 0) {
	 							html += '<span class="cal_date green hiddenSpan">'+key[i].slice(0,2)+'';
		 						html += '<input type = "hidden" value="'+key[i]+'" />';
		 						html += '</span>';
	 						}
	 						else if(minSeats == true) {
	 							html += '<span class="cal_date green hiddenSpan">'+key[i].slice(0,2)+'';
		 						html += '<input type = "hidden" value="'+key[i]+'" />';
		 						html += '</span>';
	 						}
	 						
	 					}
	 					else {
	 						html += '<span class="cal_date hiddenSpan" >'+key[i].slice(0,2)+'';
	 						html += '<input type = "hidden" value="'+key[i]+'" />';
	 						html += '</span>';
	 					}
	 				}
	 				html += '</div></div>';
	 				$("#datesListDiv").html(html);
	 			}
	 		});
		}
		
		function getEventWidget(date) {
			$.ajax({
	 			url : "getEventWidgetData.htm?date="+date,
	 			type : 'POST',
	 			success : function(data) {
	 				var response = JSON.parse(data);
	 				var map = {'00:00':'zero','00:30':'thirty','01:00':'one','01:30':'onethirty','02:00':'two','02:30':'twothirty','03:00':'three','03:30':'threethirty',
							 '04:00':'four','04:30':'fourthirty','05:00':'five','05:30':'fivethirty','06:00':'six','06:30':'sixthirty','07:00':'seven','07:30':'seventhirty',
							 '08:00':'eight','08:30':'eightthirty','09:00':'nine','09:30':'ninethirty','10:00':'ten','10:30':'tenthirty','11:00':'eleven','11:30':'eleventhirty',
							 '12:00':'twelve','12:30':'twelvethirty','13:00':'thirteen','13:30':'thirteenthirty','14:00':'fourteen','14:30':'fourteenthirty','15:00':'fifteen',
							 '15:30':'fifteenthirty','16:00':'sixteen','16:30':'sixteenthirty','17:00':'seventeen','17:30':'seventeenthirty','18:00':'eighteen','18:30':'eighteenthirty',
							 '19:00':'nineteen','19:30':'nineteenthirty','20:00':'twenty','20:30':'twentythirty','21:00':'twentyone','21:30':'twentyonethirty','22:00':'twentytwo',
							 '22:30':'twentytwothirty','23:00':'twentythree','23:30':'twentythreethirty'};
					
					var minEvent = {'00:00':'','00:30':'','01:00':'','01:30':'','02:00':'','02:30':'','03:00':'','03:30':'','04:00':'','04:30':'','05:00':'','05:30':'','06:00':'',
							'06:30':'','07:00':'','07:30':'','08:00':'','08:30':'','09:00':'','09:30':'','10:00':'','10:30':'','11:00':'','11:30':'','12:00':'','12:30':'','13:00':'',
							'13:30':'','14:00':'','14:30':'','15:00':'','15:30':'','16:00':'','16:30':'','17:00':'','17:30':'','18:00':'','18:30':'','19:00':'','19:30':'','20:00':'',
							'20:30':'','21:00':'','21:30':'','22:00':'','22:30':'','23:00':'','23:30':''};
										
	 				var html1 = '';
	 				var html2 = '';
	 				var guideName=null;
	 				var eventName=null;
	 				var strDate=null;
					var endDate=null;
	 	 			
	 				//drag data widget starts here
	 				for(var i=0,key=Object.keys(response),ii=key.length;i<ii;i++) {
	 					if(i<1) {
	 						
	 						if(i==0) {
	 							html1 += '<div class="item active">';
	 			 				html1 += '<div class="carousel-caption">';
	 						}
	 						
	 						html1 += '<div class="col-xs-12 nopadding book_info fixed_event">';
	 						html1 += '<div class="cell cell_byobject_change cell_header">'+key[i]+'</div>';
	 						//alert("evenName--->"+key[i]);
	 						eventName=key[i];
	 						for(var j=0,jkeys = Object.keys(map), jj=jkeys.length;j<jj;j++) {
								var val = contains(response[key[i]],jkeys[j]);
								if(val != null) {
									minEvent[jkeys[j]]=val;
									if(val.availableSeats > 0) {
										html1 += '<div class="cell eventreserved-'+val.divCount+' booked">';
										html1 += '<div class="room_shift shift_border ">';
										html1 += '<input type = "hidden" value="'+val.scheduleId+'"/>';
										html1 += '<p class="merge_padding">';
										if(val.availableSeats > 2) {
											html1 += '>2 <br/>'+val.guideName ;
										}else {
											html1 += val.availableSeats+'<br/>'+val.guideName ;
										}
										html1 += '</p></div>';
										html1 += '</div>';
									}else {
										//var drag = JSON.stringify(val);
										//alert('drag :'+drag);
										//alert("val-->"+response[j]);
										html1 += '<div class="cell eventbooked-'+val.divCount+'">';
										html1 += '<div class="room_shift draggableInfo" draggable="true" >';
										html1 += '<input type = "hidden"  value="'+val.scheduleId+','+val.eventOrgId+','+val.eventId+','+val.availableSeats+'" />';
										html1 += '<p class="merge_padding">';
										html1 += 'X <br/>';
										html1 += ''+val.guideName+'';
										html1 += '</p>';
										html1 += '</div></div>';
									}
									if(val.divCount>1) {
										j=j+((val.divCount)-1);
									}	
										
								}else {
									html1 += '<div class="cell storeclose"></div>';
								}							
							}
	 						html1 += '</div>';
	 						
	 						if(i==0) {
	 		 					html1 += '</div></div>';
	 		 				}
	 					}
	 					if(i>=1 && i < key.length) {
	 						
	 						//if(i==1){
	 							html1 += '<div class="item">';
	 							html1 += '<div class="carousel-caption">';
	 						//}
	 						
	 						html1 += '<div class="col-xs-12  nopadding book_info fixed_event">';
	 						html1 += '<div class="cell cell_header cell_byobject_change">'+key[i]+'</div>';
	 						//alert("evenName--->"+key[i]);
	 						eventName=key[i];
	 						for(var j=0,jkeys = Object.keys(map), jj=jkeys.length;j<jj;j++) {
								var val = contains(response[key[i]],jkeys[j]);
								if(val != null) {
									minEvent[jkeys[j]]=val;
									if(val.availableSeats > 0) {
										html1 += '<div class="cell eventreserved-'+val.divCount+' booked">';
										html1 += '<div class="room_shift shift_border">';
										html1 += '<input type="hidden" value="'+val.scheduleId+'"/>';
										html1 += '<p class="merge_padding">';
										if(val.availableSeats > 2) {
											html1 += '>2 <br/>'+val.guideName ;
										}else {
											html1 += val.availableSeats+'<br/>'+val.guideName;
										}
										html1 += '</p></div>';
										html1 += '</div>';
									}else {
										html1 += '<div class="cell eventbooked-'+val.divCount+'">';
										html1 += '<div class="room_shift draggableInfo" draggable="true">';
										html1 += '<input type="hidden" value="'+val.scheduleId+','+val.eventOrgId+','+val.eventId+','+val.availableSeats+'" />';
										html1 += '<p class="merge_padding">';
										html1 += 'X <br/>';
										html1 += ''+val.guideName+'';
										html1 += '</p>';
										html1 += '</div></div>';
									}
									if(val.divCount>1) {
										j=j+((val.divCount)-1);
									}
								}else {
									html1 += '<div class="cell storeclose"></div>';
								}							
							}
	 						html1 += '</div>';
	 					}
	 					//if(i == key.length) {
	 						html1 += '</div></div>';
	 					//} 				
	 			}
	 			//drag data widget ends here
	 			
	 			//drop data widget starts here
	 			for(var i=0,key=Object.keys(response),ii=key.length;i<ii;i++) {
		 					if(i<3) {
		 						
		 						if(i==0) {
		 							html2 += '<div class="item active">';
		 			 				html2 += '<div class="carousel-caption">';
		 						}
		 						
		 						html2 += '<div class="col-xs-4 nopadding book_info">';
		 						html2 += '<div class="cell cell_byobject_change cell_header">'+key[i]+'</div>';
		 						eventName=key[i];
		 						for(var j=0,jkeys = Object.keys(map), jj=jkeys.length;j<jj;j++) {
									var val = contains(response[key[i]],jkeys[j]);
									if(val != null) {
										minEvent[jkeys[j]]=val;
										if(val.availableSeats > 0) {	
											strDate=new Date(val.startTime).toString();
											endDate=new Date(val.endTime).toString();
											html2 += '<div class="cell eventreserved-'+val.divCount+' booked">';
											html2 += '<div class="room_shift shift_border dropableInfo">';
											html2 += '<input type="hidden" value="'+val.scheduleId+','+val.eventOrgId+','+val.eventId+','+val.availableSeats+','+val.maxCapacity+','+strDate+','+endDate+','+val.guideName+','+eventName+'" />';
											html2 += '<p class="merge_padding">';
											if(val.availableSeats > 2) {
												html2 += '>2 <br/>'+val.guideName ;
											}else {
												html2 += val.availableSeats+'<br/>'+val.guideName ;
											}
											html2 += '</p></div>';
											html2 += '</div>';
										}else {
											html2 += '<div class="cell eventbooked-'+val.divCount+'">';
											html2 += '<div class="room_shift" draggable="true" >';
											html2 += '<input type="hidden" value="'+val.scheduleId+','+val.eventOrgId+','+val.eventId+','+val.availableSeats+','+val.maxCapacity+'"/>';
											html2 += '<p class="merge_padding">';
											html2 += 'X <br/>';
											html2 += val.guideName ;
											html2 += '</p>';
											html2 += '</div></div>';
										}
										if(val.divCount>1) {
											j=j+((val.divCount)-1);
										}				
									}
									else {
										html2 += '<div class="cell storeclose"></div>';
									}							
								}
		 						html2 += '</div>';
		 						
		 						if(i==2) {
		 		 					html2 += '</div></div>';
		 		 				}
		 					}
		 					if(i>=3 && i<6) {
		 						
		 						if(i==3){
		 							html2 += '<div class="item">';
		 							html2 += '<div class="carousel-caption">';
		 						}
		 						
		 						html2 += '<div class="col-xs-4 nopadding book_info">';
		 						html2 += '<div class="cell cell_byobject_change cell_header">'+key[i]+'</div>';
		 						eventName=key[i];
		 						for(var j=0,jkeys = Object.keys(map), jj=jkeys.length;j<jj;j++) {
									var val = contains(response[key[i]],jkeys[j]);
									
									
									if(val != null) {
										minEvent[jkeys[j]]=val;
										if(val.availableSeats > 0) {	
											strDate=new Date(val.startTime).toString();
											endDate=new Date(val.endTime).toString();
											
											//alert("strDate--->"+strDate);
											//alert("endDate--->"+endDate);
											html2 += '<div class="cell eventreserved-'+val.divCount+' booked">';
											html2 += '<div class="room_shift shift_border dropableInfo">';
											html2 += '<input type="hidden" value="'+val.scheduleId+','+val.eventOrgId+','+val.eventId+','+val.availableSeats+','+val.maxCapacity+','+strDate+','+endDate+','+val.guideName+','+eventName+'" />';
											html2 += '<p class="merge_padding">';
											if(val.availableSeats > 2) {
												html2 += '>2 <br/>'+val.guideName ;
											}else {
												html2 += val.availableSeats+'<br/>'+val.guideName ;
											}
											html2 += '</p></div>';
											html2 += '</div>';
																				
										}else {
											html2 += '<div class="cell eventbooked-'+val.divCount+'">';
											html2 += '<div class="room_shift" draggable="true">';
											html2 += '<input type="hidden" value="'+val.scheduleId+','+val.eventOrgId+','+val.eventId+','+val.availableSeats+','+val.maxCapacity+'" />';
											html2 += '<p class="merge_padding">';
											html2 += 'X <br/>';
											html2 += val.guideName ;
											html2 += '</p>';
											html2 += '</div></div>';
										}
										if(val.divCount>1) {
											j=j+((val.divCount)-1);
										}
									}
									else {
										html2 += '<div class="cell storeclose"></div>';
									}							
								}
		 						html2 += '</div>';
		 					}
		 					if(i == key.length) {
		 						html2 += '</div></div>';
		 					} 				
		 			}
	 			//drop data widget ends here
	 				for(keyVal in minEvent) {
	 					if(minEvent[keyVal] != '') {
	 						var id = '#'+map[keyVal] ;		 						
	 						$(id).addClass("green");
	 					}
	 				}
	 				
	 				$("#dateButton").html(date);
	 				$("#dragWidget").html(html1);
	 				$("#dropWidget").html(html2);
	 				$('.ajax_loader_inner').removeClass();
	 				
	 				handlerEventFunctn();
	 			}
	 		});
			onLoadFinish=true;
		}
		
		function getWidgetDataByEventGuide(clickedDt,frmtdDt) {
			 
			 var eventId = $('#selectTour').val();
			 if(eventId == null) {
				 eventId = '0';
			 }
			 var guideId = $('#availGuides').val();
			 
			 
			 $.ajax({
					url : "loadDataByClickedDt.htm?clickedDt="+clickedDt+"&eventId="+eventId+"&guideId="+guideId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var k=0;
						var map = {'00:00':'zero','00:30':'thirty','01:00':'one','01:30':'onethirty','02:00':'two','02:30':'twothirty','03:00':'three','03:30':'threethirty',
								 '04:00':'four','04:30':'fourthirty','05:00':'five','05:30':'fivethirty','06:00':'six','06:30':'sixthirty','07:00':'seven','07:30':'seventhirty',
								 '08:00':'eight','08:30':'eightthirty','09:00':'nine','09:30':'ninethirty','10:00':'ten','10:30':'tenthirty','11:00':'eleven','11:30':'eleventhirty',
								 '12:00':'twelve','12:30':'twelvethirty','13:00':'thirteen','13:30':'thirteenthirty','14:00':'fourteen','14:30':'fourteenthirty','15:00':'fifteen',
								 '15:30':'fifteenthirty','16:00':'sixteen','16:30':'sixteenthirty','17:00':'seventeen','17:30':'seventeenthirty','18:00':'eighteen','18:30':'eighteenthirty',
								 '19:00':'nineteen','19:30':'nineteenthirty','20:00':'twenty','20:30':'twentythirty','21:00':'twentyone','21:30':'twentyonethirty','22:00':'twentytwo',
								 '22:30':'twentytwothirty','23:00':'twentythree','23:30':'twentythreethirty'};
						
						var minEvent = {'00:00':'','00:30':'','01:00':'','01:30':'','02:00':'','02:30':'','03:00':'','03:30':'','04:00':'','04:30':'','05:00':'','05:30':'','06:00':'',
								'06:30':'','07:00':'','07:30':'','08:00':'','08:30':'','09:00':'','09:30':'','10:00':'','10:30':'','11:00':'','11:30':'','12:00':'','12:30':'','13:00':'',
								'13:30':'','14:00':'','14:30':'','15:00':'','15:30':'','16:00':'','16:30':'','17:00':'','17:30':'','18:00':'','18:30':'','19:00':'','19:30':'','20:00':'',
								'20:30':'','21:00':'','21:30':'','22:00':'','22:30':'','23:00':'','23:30':''};
						
						var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
						
						var dateSet = Object.keys(response);
						var html2 = '';
						var count = 0;
						
		 				for(var i=0 , keys=Object.keys(response) , ii=keys.length; i<ii ; i++) {
		 					var list = response[keys[i]];
		 					if(i<7) {
		 						if(i==0) {
		 							html2 += '<div class="item active">';
									html2 += '<div class="carousel-caption">';
		 						}
		 						
		 						html2 += '<div class="col-xs-3 col-xs-3-dateline nopadding book_info">';
								html2 += '<div class="cell cell_header cell_byobject">'+keys[i]+'</div>';
								
								for(var j=0,jkeys = Object.keys(map), jj=jkeys.length;j<jj;j++) {
									var val = contains(list,jkeys[j]);									
									if(val != null) {
										minEvent[jkeys[j]]=val;
										if(val.availableSeats > 0) {
											html2 += '<div class="cell eventreserved-'+val.divCount+' booked">';
											html2 += '<p class="merge_padding">';
											html2 += '<a href="javascript:void(0)" onclick = "showReservationPopup('+val.scheduleId+')">';
											if(val.availableSeats > 2) {
												html2 += '>2 <br/>'+val.guideName+'</a>';
											}else {
												html2 += val.availableSeats+'<br/>'+val.guideName+'</a>';
											}
											html2 += '</p></div>';								
										}else {
											html2 += '<div class="cell eventbooked-'+val.divCount+'">';
											html2 += '<div class="cell_event_addition-info">';
											html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
											html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
											html2 += '</div>';
											html2 += '<p class="merge_padding">';
											html2 += '<a href ="javascript:void(0)"  onclick = "getEventReservedUsers('+val.scheduleId+')">';
											html2 += 'X <br/>';
											html2 += val.guideName ;
											html2 += '</a>';
											html2 += '</p>';
											html2 += '</div>';
										}
										j=j+((val.divCount)-1);
									}
									else {
										html2 += '<div class="cell storeclose"></div>';
									}
								
								}
								html2 += '</div>';
		 					}
		 					
		 					if(i==6) {
		 						html2 += '</div>';
								html2 += '</div>'; //item active close
		 					}
		 					
		 					if(i==7) {
		 						html2 += '<div class="item">';
								html2 += '<div class="carousel-caption">';
		 					}
		 					
		 					if(i>=7 && i<14) {
		 						html2 += '<div class="col-xs-3 col-xs-3-dateline nopadding book_info">';
								html2 += '<div class="cell cell_header cell_byobject">'+keys[i]+'</div>';
								
								for(var j=0,jkeys = Object.keys(map), jj=jkeys.length;j<jj;j++) {
									var val = contains(list,jkeys[j]);
									if(val != null) {
										minEvent[jkeys[j]]=val;
										if(val.availableSeats > 0) {
											html2 += '<div class="cell eventreserved-'+val.divCount+' booked">';
											html2 += '<p class="merge_padding">';
											html2 += '<a href="javascript:void(0)" onclick="document.getElementById(\'myreservation\').style.display=\'block\';document.getElementById(\'fade\').style.display=\'block\'">';
											if(val.availableSeats > 2) {
												html2 += '>2 <br/>Guide 3</a>';
											}else {
												html2 += val.availableSeats+'<br/>Guide 3</a>';
											}
											html2 += '</p></div>';								
										}else {
										html2 += '<div class="cell eventbooked-'+val.divCount+'">';
										html2 += '<div class="cell_event_addition-info">';
										html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
										html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
										html2 += '</div>';
										html2 += '<p class="merge_padding">';
										html2 += '<a href ="javascript:void(0)"  onclick = "getEventReservedUsers('+val.scheduleId+')">';
										html2 += 'X <br/>';
										html2 += 'Anmol';
										html2 += '</a>';
										html2 += '</p>';
										html2 += '</div>';
										}
										j=j+((val.divCount)-1);
									}
									else {
										html2 += '<div class="cell storeclose"></div>';
									}
								}
								html2 += '</div>';
		 						}
		 					if(i == keys.length) {
		 						html2 += '</div>';
								html2 += '</div>'; 
		 						}	 					 				
							}
		 				for(keyVal in minEvent) {
		 					if(minEvent[keyVal] != '') {
		 						var id = '#'+map[keyVal] ;		 						
		 						$(id).addClass("green");
		 					}
		 				}


		 				$('#dateButton').html(frmtdDt);
	 					$('#carousalDatesLst').html(html2);
	 					$('.ajax_loader_inner').removeClass();
					}
				 });
		 }
		
		//on date click functionality-------------------------------
		 jQuery(document).on('click', ".hiddenSpan", function() {
			 var clickedDt = jQuery(this).find('input').val();
			 var eventId = $('#selectTour').val();
			 if(eventId == null) {
				 eventId = '0';
			 }
			 
	 		$('.timescale').removeClass('green');
	 		//primaryDates(currDt);
	 		getEventWidget(clickedDt); 
			 
		 });
		
		function  selectTour(){
			 $.ajax({
				url     : 'selectTour.htm',
				type    : 'POST',
				success : function(data){
					var response = jQuery.parseJSON(data);
					var html = '';
					html += '<option value="0">Select Tour</option>';
					for(i in response){						
						html += '<option value='+response[i].eventId+'>'+response[i].eventName+'</option>';						
					}
					 $('#selectTour').html(html);
					 $('#availGuides').html('<option value="0">Select Guide</option>');
					 
					
				}
			 });
		 }
		
		function changeEvent() {
 			var eventId =  $('#selectTour').val();
 			var html = '<option value="0">Select Guide</option>';
 			if(parseInt(eventId) != 0){
 				availGuidesBsdOnTour(eventId);
 			}else{
 				$('#availGuides').html(html);
 			}			
 			
 		 }
		
		function availGuidesBsdOnTour(tourVal){
			 $('#selectTour').val();
			 
			 $.ajax({
				 url     : 'getGuidesLst.htm?tourVal='+tourVal,
				 type    : 'POST',
				 success :  function(data){
					 var html = '';
					 var response = jQuery.parseJSON(data);
					 
					  html += '<option value="0">Select Guide</option>';
						for(i in response){
							html += '<option value='+response[i].guideId+'>'+response[i].guideName+'</option>';
						} 
						
						$('#availGuides').html(html);
				 }
			 });
			 
			 //availGuides
		 }
		
		function countChange() {
			primaryDates(currDt);
		}
		
			$(document).ready(function() {
				onLoad();
				selectTour();
			});
		</script>
		
		
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

<div id="noReservations" class="white_content"
	style="display: none; z-index: 1002; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">No Reservations!</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('noReservations').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="noReservationsRed" style="color: red; font-weight: bold;"></span>
		<span id="noReservationsGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="fade" class="black_overlay"></div>

<div id="alertMsgPopUp3" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Alert Message</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('alertMsgPopUp3').style.display='none';document.getElementById('fade').style.display='none'">
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

<input type="hidden" value="${mode}" id="mode">
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
					<div class="col-xs-12">
					  <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
					</div>
				  </div>
				    <div class="form-group">
					<div class="col-xs-12">
					  <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
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
							<div class="col-xs-12">
							  <select class="form-control" name="language">
								<option value ="0">Select Language</option>
								<option value ="0">English</option>
								<option value ="0">Germany</option>
								<option value ="0">Deutch</option>
							  </select>
							</div>
						</div>
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
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="email" class="form-control" id="inputEmail3" placeholder="Username">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="password" class="form-control" id="inputEmail3" placeholder="Password">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="password" class="form-control" id="inputEmail3" placeholder="Confirm Password">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
							  <input type="password" class="form-control" id="inputEmail3" placeholder="Confirm Password">
							</div>
						</div>
					</div>
				   <div style="padding:0px 100px;">
					<div class="form-group">
						<div class="col-xs-12" >
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
<!-------------------------------- -------------RESERVATION DATA DETAILS MODAL --------------------------------------------->
			<div id="reservation_details" class="white_content">
			<h3>Reservation Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('reservation_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="eventreservation_scroller" >
				<form class="form-horizontal">
					<div class="reservation-data-section">
					<div class="col-xs-1">
					<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
					</div>
					<div class="col-xs-11">
					<p>
					Couching Simone, Garrison (Laurer Backer)<br/>
					Donnerstag 14 julie, 2014 - 17:00<br/>
					Donnerstag 14 julie, 2014 - 21:00
					</p>
					</div>
					</div>
										<div class="reservation-data-section">
										<div class="col-xs-1">1</div>
										<div class="col-xs-8">
													<p>
													Davidadam@web.be<br/>
													+49-28372983893
													</p>
														<button class="btn btn-default btn-blank"><img src="images/whitelist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/blacklist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/payment.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/arrived.png" /></button>
														 <textarea class="form-control" id="inputEmail3" rows="1"></textarea>
										</div>
										<div class="col-xs-3">
											<button class="btn btn-default btn-xs btn-danger">X</button>
											<button class="btn btn-default  btn-xs btn-primary">Save</button>
										</div>
										</div>
										<!-- ends -->
										<div class="reservation-data-section">
										<div class="col-xs-1">2</div>
										<div class="col-xs-8">
													<p>
													Davidadam@web.be<br/>
													+49-28372983893
													</p>
														<button class="btn btn-default btn-blank"><img src="images/whitelist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/blacklist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/payment.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/arrived.png" /></button>
														 <textarea class="form-control" id="inputEmail3" rows="1"></textarea>
										</div>
										<div class="col-xs-3">
											<button class="btn btn-default btn-danger btn-xs">X</button>
											<button class="btn btn-default btn-primary btn-xs ">Save</button>
										</div>
										</div>
										<!-- /ends-->
										<div class="reservation-data-section">
										<div class="col-xs-1">3</div>
										<div class="col-xs-8">
													<p>
													Davidadam@web.be<br/>
													+49-28372983893
													</p>
														<button class="btn btn-default btn-blank"><img src="images/whitelist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/blacklist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/payment.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/arrived.png" /></button>
														 <textarea class="form-control" id="inputEmail3" rows="1"></textarea>
										</div>
										<div class="col-xs-3">
											<button class="btn btn-default btn-danger btn-xs">X</button>
											<button class="btn btn-default btn-primary btn-xs">Save</button>
										</div>
										</div>
										<div class="reservation-data-section">
										<div class="col-xs-1">4</div>
										<div class="col-xs-8">
													<p>
													Davidadam@web.be<br/>
													+49-28372983893
													</p>
														<button class="btn btn-default btn-blank"><img src="images/whitelist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/blacklist.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/payment.png" /></button>
														<button class="btn btn-default btn-blank"><img src="images/arrived.png" /></button>
														 <textarea class="form-control" id="inputEmail3" rows="1"></textarea>
										</div>
										<div class="col-xs-3">
											<button class="btn btn-default btn-danger btn-xs">X</button>
											<button class="btn btn-default btn-primary btn-xs ">Save</button>
										</div>
										</div>
					<div class="clearfix"></div>
				</form>
				<!--p class="center" style="padding:10px 0px 0px 0px;margin-bottom:2px;" >Reservation Process Continues for next <span id="log">60</span> Secs</p--> 
				<!--div id="flash" class="center" ></div-->
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>			
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
										</div>
										</div>
					<div class="reservation-data-section">
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11">
							<input type="text" class="form-control margin-bottom-10" placeholder="Name">
							<input type="text" class="form-control margin-bottom-10" placeholder="Email">
							<input type="text" class="form-control margin-bottom-10" placeholder="Phone">
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
					
					</div><!--ends col-xs-12 -->
					<div class="clearfix"></div>
				   <div style="padding:0px 100px;" class="button_padding">
					<div class="form-group">
						<div class="col-xs-6" >
						  <button type="submit" class="btn btn-default btn-save">Reserve table</button>
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
						<div class="col-xs-3 col-xs-12" >
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
							<div class="col-xs-1">
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
						<div class="col-xs-6" >
						  <button type="submit" class="btn btn-default btn-save">Reserve table</button>
						</div>
						<div class="col-xs-6" >
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
				<form class="form-horizontal" id="mywhite_list">
					<div class="reservation-data-section">
					<div id="whitelistdetails"></div>
								<div id="startDate"></div>
					<!-- <div class="col-xs-7 col-xs-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>since: 01.01.2014<br/></p>
					</div> -->
					
					</div>
					<!--ends-->
					
					<!-- <div class="reservation-data-section">
					
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
					</div> -->
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
				<form class="form-horizontal" id="myblack_list">
					<div class="reservation-data-section">
					<div id="blacklistdetails"></div>
					<div id="stratDate"></div>
					<!-- <div class="col-xs-7 col-xs-7">
						<p>
						<strong>Name of Restaurant</strong><br/>
						Street name & Num,<br/> city name, Country<br/>
						+49-99-999-9999
						</p>
					</div>
					<div class="col-xs-5 col-xs-5">
						<p>since: 01.01.2014<br/></p>
					</div> -->
					
					</div>
					<!--ends-->
					
					<!-- <div class="reservation-data-section">
					
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
					</div> -->
					<!--ends-->				
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- EMAIL LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="myemail_list" class="white_content" style="left:8%;width:85%;">
			<h3>My Email List</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myemail_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div id="statusDiv" align="center"><span id="isEmailShared" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
						</div>
			<div class="user_data_wrapper" style="padding:0px">
				<form class="form-horizontal" id="emailVisibleForm">
					<!-- <div class="reservation-data-section">
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
					ends
					
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
					ends	 -->			
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- MYRESERVATION DETAILS DATA MODAL --------------------------------------------->
			<div id="myeventreservation_details" class="white_content"
					style="left: 8%; width: 85%;" >
					<h3>My Reservation Details</h3>
					<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myeventreservation_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
						
						<div id="statusDiv" align="center"><span id="statusInformtn" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
						</div>
						
					<div class="user_data_wrapper" style="padding: 0px">

						<form class="form-horizontal" id="reservationHistory">
							
						</form>
					</div>
				</div>
			<div id="fade" class="black_overlay"></div>
			
	<!-- ============================= DELETE RESERVATION MODAL START =============================================-->
	<div id="deleteReservtn_popUp" class="white_content">
			<h3>Delete Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('deleteReservtn_popUp').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					
					<div class="reservation-data-section">
					
						<div class="col-xs-11">
						<span style="font-size: 16px;font-weight: 700;" id="deleteReservtnText"></span>
						</div>
					</div>
					
				<div class="clearfix"></div>
				   <div style="padding:0px 100px;" onclick = "document.getElementById('deleteReservtn_popUp').style.display='none';document.getElementById('fade').style.display='none'" id="deleteAndCancelBtns"></div>
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>		
			
			
			
<!-------------------------------- ADMIN RESERVATION DETAILS DATA MODAL --------------------------------------------->
				<div id="admin_myreservation_details" class="white_content" style="left:8%;width:85%;">
			<h3>Customer Reservation Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_myreservation_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div id="adminReservtnMsgDiv" 
						style="margin: 0px 230px 0px; color: green; font-weight: 600; font-size: 18px;">
						<span id="adminReservtnMsg"></span>
				</div>
			
			<div class="user_data_wrapper" style="padding:0px">
			<div class="col-md-12" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control input-medium search-query" placeholder="Search for..." id="custmrReservtnInputVal">
					  <span class="input-group-btn"> 
						<button class="btn btn-default" type="button" id="custmrReservtnGOBtn">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
					  id="CustmrResrvtnExprtBtn">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#" id="custmrReservtnCSV">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
				<form class="form-horizontal" id="custmrHstryRecrds"> 
					<!--ends-->
							
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- CUSTOMERS LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_customer_list" class="white_content" style="left:8%;width:85%;">
			<h3>Customes List</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_customer_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
		     <div id="adminCustmrsMsgDiv" 
						style="margin: 0px 230px 0px; color: green; font-weight: 600; font-size: 18px;">
						<span id="adminCustmrsMsg"></span>
				</div>	
			<div class="user_data_wrapper" style="padding:0px">
			<div class="col-md-12" style="margin-bottom:10px;">
				
				<!-- <form class="form-search form-inline"> -->
				<div class="col-md-10 nopadding">
				<div class="input-group">
					  <input type="text" class="form-control input-medium search-query" placeholder="Search for..." id="custmrName">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="submit" id="searchBtn">Go!</button>
					  </span>
					</div>
					</div>
					<!-- </form> -->
				
				<div class="col-md-2">
								<div class="btn-group">
								  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
								  id="custmtrLstExprtBtn">
									Export As
								  </button>
								  <ul class="dropdown-menu">
									<li><a href="#" id="custmrLstpdf">Pdf</a></li>
									<li><a href="#" id="custmrLstCSV">CSV</a></li>

								  </ul>
								</div>
				
				</div>
			</div>
			<div class="clearfix"></div>
				<form class="form-horizontal">
					<div class="reservation-data-section" >
					<!-- whitelist -->
							<div class="panel-group"  role="tablist" aria-multiselectable="true" id="custmrReservtnDataSection">
							<div id="statusMsgDiv" align="center"><span id="errorStatusInfo" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
							</div>
							
							<!--  panel end -->
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
		<div id="deleteWhteUsrStatusMsgDiv" 
						style="margin: 0px 230px 0px; color: green; font-weight: 600; font-size: 18px;">
						<span id="deleteWhteUsrStatusMsg"></span>
				</div>	
			
			<div class="user_data_wrapper" style="padding:0px">
					<div class="reservation-data-section">
					<!-- whitelist -->
					<div class="col-md-12" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for..." id="whiteListInput">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button" id="whiteLstGOBtn">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
					  id="adminWhteLstExprtBtn">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#" id="whteUsrCSVBtn">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
					<div class="panel-group"  role="tablist" aria-multiselectable="true" id="adminWhtLst">
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
			<div id="adminEmailLstMsgStatusDiv" 
						style="margin: 0px 230px 0px; color: green; font-weight: 600; font-size: 18px;">
						<span id="adminEmailLstStatusMsg"></span>
				</div>	
			
			<div class="user_data_wrapper" style="padding:0px">
					<div class="reservation-data-section">
					<!-- whitelist -->
					<div class="col-md-12 nopadding" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search with user name" id="custmrEmailListId">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button" id="custmrEmailListSearchBtn">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="custmrEmailLstExprtBtn">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#" id="custmrEmailCsvBtn">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix" ></div>
							<div class="col-md-12 listpad" id="custmrEmailList">
								<!-- <div class="col-md-4 nopadding">Adman Herlary</div>
								<div class="col-md-4">adman@hotmail.com</div>
								<div class="col-md-4">+77-327928289</div> -->
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
					<!-- status message -->
					<div id="statusMsgDiv" align="center"><span id="addRoomToWhteStstusMsg" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
					</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-md-3 control-label">Select Tour</label>
							<div class="col-md-8">
								<select class="form-control" name="language" id="roomCategryLst">
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-md-3 control-label">Select Guide</label>
							<div class="col-md-8">
								<select class="form-control" name="language" id="roomNum">
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-md-3 control-label"></label>
							<div class="col-md-8">
								<button class="btn btn-primary btn-default" type = "button" id="adminAddBtn">Add</button>
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
				<div id="deleteBlackUsrStatusMsgDiv" style="margin: 0px 230px 0px; color: green; font-weight: 600; font-size: 18px;">
					<span id="deleteBlackUsrStatusMsg"></span>
				</div>
				
			<div class="user_data_wrapper" style="padding:0px">
					<div class="reservation-data-section">
					<!-- whitelist -->
					<div class="col-md-12" style="margin-bottom:10px;">
				<div class="col-md-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for..." id="blckLstSearchInput">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button" id="blckGOBtn">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-md-2">
					<!-- Single button -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
					  id="adminBlackLstExprtBtn">
						Export As
					  </button>
					  <ul class="dropdown-menu">
						<li><a href="#">Pdf</a></li>
						<li><a href="#" id="blckUsrCSVBtn">CSV</a></li>

					  </ul>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
				<div class="panel-group"  role="tablist" aria-multiselectable="true" id="blckCustmrsLst">
				</div>	
							
					<!-- end list-->
					</div>
					<!--ends-->				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- MY PROFILE DETAILS DATA MODAL --------------------------------------------->
			<div id="myprofile" class="white_content" style="left:10%;width:80%;">
			<h3>Admin Profile</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myprofile').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<spring:form class="form-horizontal" commandName="getSPUser" action="updateServiceProfile.htm">
						<div class="reservation-data-section">
						<h5>My Profile Details</h5>
				<div class="profile_scroller">
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Username</label>
							<div class="col-xs-9">
							  <spring:input  path="userName" class="form-control"
											id="inputEmail3" placeholder="Username" readonly="true"></spring:input>
										<spring:errors style="color: red" path="userName"></spring:errors>
							</div>
							<spring:hidden path="userId"/>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Password</label>
							<div class="col-xs-9">
							 <spring:password path="password" class="form-control"
											showPassword="true" id="inputEmail3" onchange="hidepwd"></spring:password>
										<spring:errors style="color: red" path="password" id="pwd"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Confirm Password</label>
							<div class="col-xs-9">
							 <spring:password path="confirmPassWord" class="form-control"
											showPassword="true" id="inputEmail3"
											onchange="hideConfirmpassword()"></spring:password>
										<spring:errors style="color: red" path="confirmPassWord"
											id="confirmpassword"></spring:errors>
							</div>
						</div>
							<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Title</label>
							<div class="col-xs-9">
							 <spring:select class="form-control" path="title" name="title"
											id="title" items="${title}">
										</spring:select>
										<spring:errors style="color:red" path="title"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">First Name</label>
							<div class="col-xs-9">
							  <spring:input path="firstName" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="firstName"
											id="firstName"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Last Name</label>
							<div class="col-xs-9">
							 	<spring:input path="lastName" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="lastName"
											id="lastName"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Street</label>
							<div class="col-xs-9">
							 <spring:input path="streetName" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="streetName"
											id="streetName"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Pincode</label>
							<div class="col-xs-9">
								<spring:input path="postalCode" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="postalCode"
											id="postalcode"></spring:errors>
							</div>
						</div>
								<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">City</label>
							<div class="col-xs-9">
							  <spring:input path="city" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="city" id="city"></spring:errors>
							</div>
						</div>
								<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Country</label>
							<div class="col-xs-9">
							  	<spring:select class="form-control" path="country"
											name="countryid" id="countryid" style="color:#000;"
											items="${countryList}">
										</spring:select>
										<spring:errors style="color: red" path="country" id="country"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						  <label for="inputEmail3" class="col-xs-2 control-label">Date Of Birth</label>
							<div class="col-xs-9">
							  <spring:input path="dob" class="form-control"
											name="dob" placeholder="dd/mm/yyyy"
											onchange="hidedateofbirth()" id="datepick"></spring:input>

										<spring:errors style="color: red" path="dob"
											id="dateOfBirth"></spring:errors>
							</div>
						</div>
						
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Email</label>
							<div class="col-xs-9">
							  <spring:input path="email" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="email" id="email"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Phone</label>
							<div class="col-xs-9">
							 <spring:input type="text" class="form-control"
											path="phoneNumber" id="contact_no" name="contact_no"
											placeholder="Phone Number" onchange="hidecontactnumber()"></spring:input>
										<spring:errors style="color: red" path="phoneNumber"
											id="contactNumber"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Language</label>
							<div class="col-xs-9">
							<spring:select class="form-control" path="language"
											name="language" id="language" style="color:#000;"
											items="${languages}"></spring:select>
										<spring:errors style="color: red" path="language"></spring:errors>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Join Date</label>
							<div class="col-xs-9">
								<spring:input path="dateJoin" class="form-control"
											id="inputEmail3" readonly="true"></spring:input>
							</div>
						</div>	
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-2 control-label">Remainder</label>
							<div class="col-xs-9">
								<spring:select class="form-control"
											path="notificationDuration" name="notify" id="notify"
											style="color:#000;" items="${notificationPeriod}">
										</spring:select>
										<spring:errors style="color: red" path="notificationDuration"
											id="notifyduration"></spring:errors>
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
					</spring:form>
				</div>
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
<!-- =========================================Main html code body==============================================================================================-->
			<div class="col-xs-3 hotel_title nopadding">
					<img src="images/logo1.png" class="img-responsive hotel_logo" style="margin-bottom:5px;" />
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
							<!-- <li><a href="#"><img src="images/brownpat.png" style="padding-right:6px;" /></a></li> -->
							<li><a href="returnToServiceEvent.htm"><img src="images/greenpat.png" style="padding-right:6px;" /></a></li>
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
						  <li><a href="javascript:void(0)" onclick ="getProfile()">My Profile</a></li>
							<li><a href = "javascript:void(0)" onclick ="getMyReservations()">My Reservations</a></li>
							<li><a href = "javascript:void(0)" onclick ="getServiceWhiteList()" class="circle">White List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="getServiceBlackList()" class="circle">Black List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="getEmailVisible()" class="circle">E-mail Visible </a></li>
							<div class="divider"></div>
							<li><a href = "logout.htm">Logout</a></li>
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
						  <li><a href = "javascript:void(0)" id="adminCustmrList">Customers List</a></li>
						  <li><a href = "javascript:void(0)" id ="custmrReservtns">Customers Reservation</a></li>
						  <li><a href = "javascript:void(0)" id ="adminWhiteList">White List Customers</a></li>
						  <li><a href = "javascript:void(0)" id ="adminBlckList">Black List Customers</a></li>
						  <li><a href = "javascript:void(0)" id = "custmrsEmailList">Email Of Customers</a></li>
						  <li><a href = "javascript:void(0)" id = "addToWhitLstCat">Add to White List Category</a></li>
							
			
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
				<div class="col-xs-2 nopadding"><button type="button" class="btn btn-default btn-current-date" ondblclick="resetPage()" id="dateButton"><!-- 06-06-2015 --></button></div>
				<div class="col-xs-4">
					<select class="selectpicker form-control" name="" id="selectTour" onchange="changeEvent()">
						<!-- <option value="0">Select Tour</option>
						<option value="1">Tour 1</option>
						<option value="1">Tour 2</option>				
						<option value="2">Tour 3</option>
						<option value="3">All Tours</option> -->
					</select>
				</div>
				<div class="col-xs-4 nopadding">
					<select class="selectpicker form-control" id="availGuides">
						<!-- <option value="0">Select Guide</option>
						<option value="1">Guide 1</option>
						<option value="1">Guide 2</option>				
						<option value="2">Guide 3</option>
						<option value="3">Guide 4</option> -->
					</select>
				</div>
				<div class="col-xs-2" style="padding-right:0px;">
					<input type="number" min="1" class="form-control" Placeholder="Count" id="seatCount" onchange="countChange()" />
				</div>
			<div class="clearfix"></div>
			<div class="ajax_container">
				<div class="col-xs-12 nomargin_lf_rt date_wrapper">
							<div id="thumbcarousel3" class="carousel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							   <div class="carousel-caption1" id="datesListDiv">
										<!-- <div class="piece">
												<div class="small_square">
										 <span class="month_title">July</span>
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
												<span class="month_title">July</span>
												<span class="cal_date">01</span>
												<span class="cal_date">02</span>
												<span class="cal_date">03</span>
												<span class="cal_date green">04</span>
												<span class="cal_date green">05</span>
												<span class="cal_date">06</span>
												<span class="cal_date green">07</span>
												<span class="cal_date green">08</span>
												<span class="cal_date green">09</span>
												
												<span class="cal_date green">10</span>
												<span class="cal_date">11</span>
												<span class="cal_date">12</span>
												<span class="cal_date">13</span>
												<span class="cal_date green">14</span>
												<span class="cal_date green">15</span>
												<span class="month_title">Aug</span>
												<span class="cal_date">16</span>
												<span class="cal_date green">17</span>
												<span class="cal_date green">18</span>
												<span class="cal_date green">19</span>
												<span class="cal_date green">20</span>
												<span class="cal_date green">21</span>
												<span class="cal_date">21</span>
												<span class="cal_date">23</span>
												<span class="cal_date">24</span>
												<span class="cal_date green">25</span>
												<span class="cal_date green">26</span>
												<span class="cal_date">27</span>
												<span class="cal_date green">28</span>
												<span class="cal_date green">29</span>
												<span class="cal_date green">30</span>
												<span class="month_title">Sep</span>
												<span class="cal_date green">01</span>
												<span class="cal_date green">02</span>
												<span class="cal_date">03</span>
												<span class="cal_date">04</span>
												<span class="cal_date">05</span>
												<span class="cal_date green">06</span>
												<span class="cal_date green">07</span>
												<span class="cal_date">08</span>
												<span class="cal_date">09</span>
												<span class="cal_date">10</span>
												<span class="cal_date">11</span>
												<span class="cal_date">12</span>
												<span class="cal_date">13</span>
												<span class="cal_date">14</span>
												<span class="month_title">Sep</span>
												<span class="cal_date">15</span>
												<span class="cal_date">16</span>
												<span class="cal_date">17</span>
												<span class="cal_date">18</span>
												<span class="cal_date">19</span>
												<span class="cal_date">20</span>
												<span class="cal_date">21</span>
												<span class="cal_date">22</span>
												<span class="cal_date">23</span>
												<span class="cal_date">24</span>
													<span class="cal_date">25</span>
													<span class="cal_date">26</span>
													<span class="cal_date">27</span>
												<span class="cal_date green">28</span>
												<span class="cal_date green">29</span>
												<span class="cal_date green">30</span>
												<span class="month_title">Oct</span>
												<span class="cal_date green">01</span>
												<span class="cal_date green">02</span>
												<span class="cal_date">03</span>
												<span class="cal_date">04</span>
												<span class="cal_date">05</span>
												<span class="cal_date green">06</span>
												<span class="cal_date green">07</span>
												<span class="cal_date">08</span>
												<span class="cal_date">09</span>
												<span class="cal_date">10</span>
												<span class="cal_date">11</span>
												<span class="cal_date">12</span>
												<span class="cal_date">13</span>
												<span class="cal_date">14</span>
												<span class="month_title">Oct</span>
												<span class="cal_date">15</span>
												<span class="cal_date">16</span>
												<span class="cal_date">17</span>
												</div>
										</div> -->
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
				</div><!-- ends date list -->
		<!-- Object or week list and data -->
		<div class="clearfix"></div>
				<div class="col-xs-12 nomargin info_wrapper">
							<div class="ajax_container_inner">
							<div class="ajax_loader_inner"></div>
							<div id="selected_tour" class="col-xs-3 nopadding book_info" >
							<div id="thumbcarousel1" class="carousel carousel_info slide" data-ride="carousel" style="padding:0px;" >
								<div class="carousel-inner" role="listbox" id="dragWidget">
									
							  </div>
							  </div>
							  <!-- Controls -->
							  <a class="myleft carousel-control" href="#thumbcarousel1" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-triangle-left glyphicon glyphicon-triangle-myleft" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a>
							  <a class="myright_event_change carousel-control" href="#thumbcarousel1" role="button" data-slide="next" >
								<span class="glyphicon glyphicon-triangle-right glyphicon glyphicon-triangle-myright" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							  </a>
							</div>
							<!-- ends-->
				
							<div class="col-xs-9 nopadding">
										<!-- dateline-->
											<div class="col-xs-1 nopadding" style="width:57px;">
											<div class="cell-dummy">
												<a class="fixed-myleft fixed-myleft-clock carousel-control" href="#" style="padding-top:0px;" role="button" data-slide="prev">
												<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
												<span class="sr-only">Previous</span>
											  </a>
											</div>
													<div class="event_change_timeline" id="showStrtAndEndTimeDiv">
														<!-- <div class="cell green">10:00</div>
														<div class="cell">10:30</div>
														<div class="cell green">11:00</div>
														<div class="cell">11:30</div>
														<div class="cell">12:00</div>
														<div class="cell">12:30</div>
														<div class="cell">13:00</div>
														<div class="cell">13:30</div>
														<div class="cell">14:00</div>
														<div class="cell">14:30</div>
														<div class="cell">15:00</div>
														<div class="cell">15:30</div>
														<div class="cell">16:00</div>
														<div class="cell">16:30</div>
														<div class="cell">17:00</div>
														<div class="cell">17:30</div> -->													
													</div>
											</div>
							<!-- dateline ends -->
							<div id="thumbcarousel2" class="carousel carousel_info slide" data-ride="carousel" style="padding:0px 20px 0px 0px;">
								<div class="carousel-inner" role="listbox" id="dropWidget">
									
							  </div>

							  <!-- Controls -->
							  <a class="service_change_myleft carousel-control" href="#thumbcarousel2" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-triangle-left glyphicon glyphicon-triangle-myleft" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a>
							  <a class="fixed-myright carousel-control" href="#thumbcarousel2" role="button" data-slide="next" style="margin: 0px 600px 0px;">
								<span class="glyphicon glyphicon-triangle-right glyphicon glyphicon-triangle-myright" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							  </a>
							</div>
				
				</div>
				</div>
		<!-- ends-->
		</div>
	</div>
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
	</div>
	<!--ends-->
</div>
<!--Main COntent ends -->
</div>
</body>

<script>
	
	var handleDragStart;
	var handleDragOver;
	var handleDragLeave;
	var handleDrop;
	
	function handlerEventFunctn(){
		
		var fras = document.querySelectorAll('.room_shift');
		[].forEach.call(fras, function(fra) {
		  fra.addEventListener('dragstart', handleDragStart, false);
		  fra.addEventListener('dragover', handleDragOver, false);
		  fra.addEventListener('dragleave', handleDragLeave, false);
		  fra.addEventListener('drop', handleDrop, false);
		  
		});
	}
	
	//drag and drop
	handleDragStart = function handleDragStart(e) {
	var source = this;
	
	e.dataTransfer.effectAllowed = 'move';
	e.dataTransfer.setData('text', this.innerHTML);
	}
	handleDragOver = function handleDragOver(e) {
	
	if (e.preventDefault) {
	e.preventDefault();
	}
	this.classList.add('over');
	return false;
	}
	handleDragLeave = function handleDragLeave(e) {
	this.classList.remove('over'); 
	}
	
	handleDrop =function handleDrop(e) {
		if (e.preventDefault){
			e.preventDefault();
		}
	this.classList.remove('over');
		
	     var droppableInfo = jQuery(this).find('input').val();
	     var draggableInfo = jQuery('.draggableInfo').find('input').val();
		//alert("drag ok--->"+draggableInfo);
	    /*  alert('droppableVal :'+droppableInfo);
		alert('draggableVal :'+draggableInfo); */
		
		changeReservation(draggableInfo,droppableInfo);
	    //changeReservation(draggableVal,droppableVal,e);
	//alert('dropped successfullly');
	this.innerHTML = e.dataTransfer.getData('text');
	}
	
	
	function changeReservation(draggableInfo,droppableInfo){
		//alert('dragable info in fun :'+draggableInfo);
		//alert('droppable info in fun :'+droppableInfo);
		
				 $.ajax({
					url  : 'changeEventReservation.htm',
					type : 'POST',
					data : {draggableInfo:draggableInfo,droppableInfo:droppableInfo},
					success : function(data){
						
					/* 	var response=data;
						alert("Data--->"+response);
						if(response=="true") {
							onLoad();
						}
						if(onLoadFinish==true) {
							alert("onLoad Finish-->"+onLoadFinish);
							 $.ajax({
									url  : ' eventChangeReservationMail.htm',
									type : 'POST',
									success : function(data){
										
									}
									});
						} */
						
					}
				}); 
				
				
	}




</script>
</html>
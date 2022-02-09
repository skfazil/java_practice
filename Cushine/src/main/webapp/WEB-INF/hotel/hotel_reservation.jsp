<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="tag"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
          <%--   <%@ page language="java" session="false"%> --%>
<html> 
	<head>
	<!-- <meta charset="utf-8">
		<meta name="viewport" content="width=700">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
		<title>Hotel Reservation</title>		
	    <link rel="stylesheet" href="css/bootstrap.min.css"/>
		<link href="css/style.css" rel="stylesheet">
		<link rel="stylesheet" href="css/non-responsive.css">
		<link rel="stylesheet" href="css/datepicker.css">
        
	   <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	   <script type="text/javascript" src="js/bootstrap.min.js"></script>
	   <script type="text/javascript" src="js/jquery.validate.js"></script>
	    <!-- Google Map API Key Source -->
		<!-- <script src="http://maps.google.com/maps/api/js?sensor=false"></script> -->
		<!-- Google Map  Source -->
		<!-- <script type="text/javascript" src="js/gmaps.js"></script> -->
	    <script type="text/javascript" src="js/customjs.js"></script>
	    <script type="text/javascript" src="js/date.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="js/jquery.session.js"></script>

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
		.loggedInUser{
			color:rgb(0,150,250)  !important;
			font-weight: bold;
		}
	</style>
 <script type="text/javascript">
         
            $(document).ready(function () {
                $('#datepick').datepicker({
                	  autoclose: true,
                    format: "dd/mm/yyyy"
                });  
                $('#datepickadmin').datepicker({
              	  autoclose: true,
                  format: "dd/mm/yyyy"
              });  
                
			$('#datepicker').datepicker({
				autoclose : true,
				format : "dd/mm/yyyy"
			});
			$("select#pickHotel").change(function() {
				var pickHotel = $('#pickHotel').val();
				document.location.href = "pickHotel.htm?name=" + pickHotel;
			});
		});// document.ready function close.
	</script>
      
	<script type="text/javascript">
 function getStopSharing() { 
		 
		 $.ajax({
	            url: "getUpdateEmail.htm",
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
	 </script>
	<script type="text/javascript">
	function validateEmail(sEmail) {
		var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (filter.test(sEmail)) {
		       return true;
		   }else {
		  return false;
		  }
		}
		
  function	getEmailVisible(){	 
	  $.ajax({
          url: "getEmailListDet.htm",
          type: 'GET',         
          success: function(data){         
          var response  = jQuery.parseJSON(data); 
          var html = '';
          //$('#emailreservation').empty();
          
          for(i in response){
        	  html += '<div class="reservation-data-section">';
          html += '<div class="col-xs-5 col-md-5">';
          html += '<p>';
          html += '<strong>Name of Restaurant</strong><br />';
          html += ''+response[i].hotelName+'';
          html += '<br />';
          html += ''+response[i].hotelAddress+'';
          html += '<br />';
          html += ''+response[i].phoneNumber+','+response[i].city+','+response[i].postalCode+'';
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
        
     	  $('#emailreservation').append(html);
     	  $('#myemail_list').show();
         }
     
      });	
  }
			
	function getHotel() {
		    	   $.ajax({
			     url : "getContactDetail.htm",
			     type : "GET",
			     cache : false,
			    
			     success : function(
			       response) {
			         	         
		  		  var brands = $
		             .parseJSON(response);
			          
				var html='';
			        $(brands)
		             .each(
		               function(i,client) {
		          
		              	 html +=
		              	 '<tr>'
		              	 +'<td><strong >Restaurant:</strong>&nbsp;&nbsp;</td>'+
		              	 '<td><strong>'+client.hotelName+'</strong></td>'+ 
		               		'</tr>';
		               	html += '<tr>'; 
		              	  html +='<td>Address</td>'+
		              	 '<td>'+client.hotelAddress+'</td>'+
		              	 '</tr>';
		              	 html += '<tr>'; 
		              	  html +='<td>PostalCode</td>'+
		              	 '<td>'+client.postalCode+'</td>'+
		              	 '</tr>';
		              	 html += '<tr>'; 
		              	  html +='<td>Phone</td>'+
		              	 '<td>'+client.phoneNumber+'</td>'+
		              	 '</tr>';
		              	 html += '<tr>'; 
		              	  html +='<td>Email</td>'+
		              	 '<td>'+client.email+'</td>'+
		              	 '</tr>';
		              	 html += '<tr>'; 
		              	 html +='<td>Website</td>'+
		              	 '<td>'+client.webSiteURL+'</td>'+
		              	 '</tr>';
		               });
			            
			        $('#addressdetails').html(html);
			        $('#hotel_details').show();
			     }
			 	    		    
			    });
			    	  
		}
			function getWhiteList() {        
			     $.ajax({
			             url: "getWhiteListDet.htm",
			             type: 'GET',            
			             success: function(data){            
			             var response  = jQuery.parseJSON(data);
			          
			             var html = '';
			             $('#whitelistdetails').empty();
			                for(i in response){
			                html += '<div class="col-xs-7 col-md-7">';
			                html += '<p>';
			                html += '<strong>Name of Restaurant</strong><br />';
			                html += ''+response[i].hotelName+'';
			                html += '<br />';
			                html += ''+response[i].hotelAddress+'';
			                html += '<br />';
			                html += ''+response[i].phoneNumber+','+response[i].city+','+response[i].postalCode+'';
			                html += '<br />';
			               
			                html += '</p>';
			                html += '</div>';
			                html += '<div class="col-xs-5 col-md-5">';
			                html += '<p>';
			                html += 'Since : '+response[i].strtDate+'';
			                html += '<br /> ';              
			                html += '</p>';
			                html += '</div>';             
			                }
			           $('#whitelistdetails').append(html);
			           $('#mywhite_list').show();
			           document.getElementById('fade').style.display='block';
			            }
			        
			         });      
			       
			     } 
			
			function getBlackList() {
			    $.ajax({
			             url: "getBlackListDet.htm",
			             type: 'GET',            
			             success: function(data){            
			             var response  = jQuery.parseJSON(data);          
			             var html = '';
			             $('#blacklistdetails').empty();
			                for(i in response){
			                html += '<div class="col-xs-7 col-md-7">';
			                html += '<p>';
			                html += '<strong>Name of Restaurant</strong><br />';
			                html += ''+response[i].hotelName+'';
			                html += '<br />';
			                html += ''+response[i].hotelAddress+'';
			                html += '<br />';
			                html += ''+response[i].phoneNumber+','+response[i].city+','+response[i].postalCode+'';
			                html += '<br />';
			               
			                html += '</p>';
			                html += '</div>';
			                html += '<div class="col-xs-5 col-md-5">';
			                html += '<p>';
			                html += 'Since : '+response[i].strtDate+'';
			                html += '<br /> ';              
			                html += '</p>';
			                html += '</div>';             
			                }        
			           $('#blacklistdetails').append(html);
			           $('#myblack_list').show();
			           document.getElementById('fade').style.display='block';
			            }        
			         });      
			     
			   
			      } 
			
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
						
						 $('#myreservation_details').hide();
						 $('#changeReservtn_popUp').show();
						 document.getElementById('fade').style.display='block';
					
					}
				});
				
					}//changeReservtn close
			
			
			function changeHotel(hotelId){
				document.location.href = "changeHotel.htm?hotelId="+hotelId;
			}
	</script>
	
	
	         <script>  
	         var userBkdRecrds = ''; 
	         
	         function showDatesListOnDatesBar(){
	        	
	        	 getLoggedInUserDates();
	        	 
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
                 var fullDate =  Day + '/' + Month + '/' + Year;
	        	 
	        	 var noOfDays = $('#noOfDays').val();
	        	 var catgry = $('#category').val();
	        	 
	        	 if(noOfDays == ''){
               		noOfDays = 0;
               	 }
	        	 fullDate = fullDate.replace('/','-');
           		 fullDate = fullDate.replace('/','-');
	        	 
	        	 $.ajax({
	        		 url   :  'showDates.htm?noOfDays='+noOfDays+'&catgry='+catgry,
	        		 type  :  'POST',
	        		 success : function(data){
	        			 var response = jQuery.parseJSON(data);
	        			 var currDate = $('#currentDate span').text();
                      	currDate = currDate.replace('/','-');
                      	currDate = currDate.replace('/','-');
                      	 
                      	 var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
                      	 var html = '';
               			
               			 html += '<div class="piece getDateVal" >';
               			 html += '<div class="small_square" >';
               			
               			var mnthNme = '';
               			var count = 0;
           				var pxls = 0;
               			  for(j in response){
               				
                        		 if(j == 0){
                        			 var monthNum = response[j].selecteDt.slice(3,5);
                        			 monthNum = (monthNum-1) ;
                        			 jQuery.each( monthArray, function( i, mnthName ) {
                                   	 	if(monthNum == i){
                                   	 		//mnthNme += ' <span>'+mnthName+'</span>';
                                   	 		html += '<span class="month_title">'+mnthName+'</span>';
                                   	 	}
                                   	});
                        			 }
                        		} // for close.
                        		var currentDtVal = fullDate.replace('-','/');
                        		    currentDtVal = currentDtVal.replace('-','/');
               			for(i in response){
              				if(currentDtVal == response[i].selecteDt){
              				
              					html += '<span class="cal_date current_date hiddenSpan">';
              					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
              					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
              				}else{
              					if(response[i].selecteDt.slice(0,2) == 01 || 
                      					 response[i].selecteDt.slice(0,2) == 15 &&
                        					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
              						var monthNum = response[i].selecteDt.slice(3,5);
                          			 monthNum = (monthNum-1) ;
                          			 jQuery.each( monthArray, function( i, mnthName ) {
                                     	 	if(monthNum == i){
                                     	 		html += '<span class="month_title">'+mnthName+'</span>';
                                     	 	}
                                     	});
              					}
              					
              					var blueDtval = '';
              					  for(var record in userBkdRecrds){	
              						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
              								 && userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
              							html += '<span class="cal_date loggedInUser hiddenSpan">';
                      					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                      					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                      					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
              						}
              					} // for (userBkdRecrds)close.
              					
              					if(blueDtval != response[i].selecteDt.slice(0,2)){
              						
              						if(response[i].selecteDt.slice(0,2) == 01 || 
                         					 response[i].selecteDt.slice(0,2) == 15 &&
                           					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                 						var monthNum = response[i].selecteDt.slice(3,5);
                             			 monthNum = (monthNum-1) ;
                             			 jQuery.each( monthArray, function( i, mnthName ) {
                                        	 	if(monthNum == i){
                                        	 		html += '<span class="month_title">'+mnthName+'</span>';
                                        	 	}
                                        	});
                 					}
              						
              						if(response[i].status == "B"){
                  						html += '<span class="cal_date hiddenSpan">';
                  						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                  						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                  					}else  {
                  						html += '<span class="cal_date green hiddenSpan">';
                  						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                  						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                  					}
              					}
              					
              				}// else close.
          				}
                        		
          			html += '</div>';
          			html += '</div>';
          			$('#secndryDatesList').empty();
          			$('#prmryDatesList').empty();
          			$('#prmryDatesList').append(html);
          			$('#prmryDatesList').show();
          			
          			
          			//removing ajax loader.
          			$('.ajax_loader').removeClass();	 
	        			
	        		 }
	        	 });
	         }
	         function showSearchDatesListOnDatesBar(selctdVal,selectedDate,blckDate){
	        	
		        	getLoggedInUserDates();
	        	 
            	 var dateVal = selectedDate.slice(0,2);
	        	 
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
                 var fullDate =  Day + '/' + Month + '/' + Year;
	        	 
	        	 var noOfDays = $('#noOfDays').val();
	        	 var catgry = $('#category').val();
	        	 
	        	 if(noOfDays == ''){
               		noOfDays = 0;
               	 }
	        	 fullDate = fullDate.replace('/','-');
           		 fullDate = fullDate.replace('/','-');
           		 
           		 if(catgry == 0 ){
           			showSearchDates(noOfDays,catgry,selctdVal,fullDate);
           		 }else{
           			getDatesListOnCatgryValType(catgry,fullDate,selctdVal,blckDate);
           		 }
	        	 
	        	 
	         }
	         //catVal,fullDate,selctdVal,blckDate
	         function getDatesListOnCatgryValType(catVal,fullDate,selctdVal,blckDate){
	        	 var noOfDays = $('#noOfDays').val();
	        	 if(noOfDays == ''){
	        		 noOfDays = 0;
	        	 }
	        	 
	        	 $('#secndryDatesList').empty();
                 $('#prmryDatesList').empty();
                  
                 $.ajax({
                	url   : 'getSelectedDatesListOnCatgryType.htm',
                 	type  : 'POST',
                 	data  :{selctdDate:selctdVal,catVal:catVal,noOfDays:noOfDays},
                 	success : function(data){
                 		var response =  jQuery.parseJSON(data);
                 		var currDate = $('#currentDate span').text();
                    	currDate = currDate.replace('/','-');
                    	currDate = currDate.replace('/','-');
                    	
                    	 var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
                    	 var html = '';
             			
             			 html += '<div class="piece getDateVal" >';
             			 html += '<div class="small_square" >';
             			
           			 for(j in response){
           				
                    		 if(j == 0){
                    			 var monthNum = response[j].selecteDt.slice(3,5);
                    			 monthNum = (monthNum-1) ;
                    			 jQuery.each( monthArray, function( i, mnthName ) {
                               	 	if(monthNum == i){
                               	 		html += '<span class="month_title">'+mnthName+'</span>';
                               	 	}
                               	});
                    			 }
                    		} // for close.
             			
             				for(i in response){
             					
                 				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2)
                 						&& fullDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
                 					
                 					if( response[i].selecteDt == selctdVal ){
                 						html += '<span class="cal_date current_date mark_date hiddenSpan">';
                     					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                     					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                 					}else{
                 						html += '<span class="cal_date current_date hiddenSpan">';
                     					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                     					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                 					}
                 					
                 				}else{
                 					if(response[i].selecteDt.slice(0,2) == 01 || 
                         					 response[i].selecteDt.slice(0,2) == 15 &&
                           					response[i].selecteDt.slice(3,5) >= fullDate.slice(3,5) ){
                 						var monthNum = response[i].selecteDt.slice(3,5);
                             			 monthNum = (monthNum-1) ;
                             			 jQuery.each( monthArray, function( i, mnthName ) {
                                        	 	if(monthNum == i){
                                        	 		html += '<span class="month_title">'+mnthName+'</span>';
                                        	 	}
                                        	});
                 					}
                 					
                 					var blueDtval = '';
                 					
                 					  for(var record in userBkdRecrds){	
                 						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
                 								 && userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
                 							if( response[i].selecteDt == selctdVal ){
                 								html += '<span class="cal_date mark_date loggedInUser hiddenSpan">';
                             					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                             					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                             					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
                 							}else{
                 								html += '<span class="cal_date  loggedInUser hiddenSpan">';
                             					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                             					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                             					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
                 							}
                 							  
                 							
                 						}
                 					} // for (userBkdRecrds)close.
                 					
                 					if(blueDtval != response[i].selecteDt.slice(0,2)){
                 						
                 						if(response[i].selecteDt.slice(0,2) == 01 || 
                            					 response[i].selecteDt.slice(0,2) == 15 &&
                              					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                    						var monthNum = response[i].selecteDt.slice(3,5);
                                			 monthNum = (monthNum-1) ;
                                			 jQuery.each( monthArray, function( i, mnthName ) {
                                           	 	if(monthNum == i){
                                           	 		html += '<span class="month_title">'+mnthName+'</span>';
                                           	 	}
                                           	});
                    					}
                 						
                 						if(response[i].status == "B"){
                 							if( response[i].selecteDt == selctdVal ){
                 								html += '<span class="cal_date mark_date hiddenSpan">';
                         						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                         						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                 							}else{
                 								html += '<span class="cal_date hiddenSpan">';
                         						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                         						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                 							}
                 							
                     					}else  {
                     						if( response[i].selecteDt == selctdVal ){
                     							html += '<span class="cal_date mark_date green hiddenSpan">';
                         						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                         						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                     						}else{
                     							html += '<span class="cal_date green hiddenSpan">';
                         						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                         						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                     						}
                     						
                     					}
                 					}
                 					
                 				}//else close.
             				}
             			html += '</div>';
             			html += '</div>';
             			
             			$('#secndryDatesList').empty();
             			$('#prmryDatesList').empty();
             			$('#prmryDatesList').append(html);
             			$('#prmryDatesList').show();
             			
             			//removing ajax loader.
              			$('.ajax_loader').removeClass();
                 	}
                 }); //ajax function close. 
	         }
	         
	         function showSearchDates(noOfDays,catgry,selctdVal,fullDate){
	        	
	        	 dateVal = selctdVal.split(0,2);
	        	 $.ajax({
	        		 url   :  'showSearchDates.htm?noOfDays='+noOfDays+'&catgry='+catgry+'&date='+selctdVal,
	        		 type  :  'POST',
	        		 success : function(data){
	        			 var response = jQuery.parseJSON(data);
	        			 var currDate = $('#currentDate span').text();
                      	currDate = currDate.replace('/','-');
                      	currDate = currDate.replace('/','-');
                      	 
                      	 var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
                      	 var html = '';
               			
               			 html += '<div class="piece getDateVal" >';
               			 html += '<div class="small_square" >';
               		
               			  for(j in response){
               				
                        		 if(j == 0){
                        			 var monthNum = response[j].selecteDt.slice(3,5);
                        			 monthNum = (monthNum-1) ;
                        			 jQuery.each( monthArray, function( i, mnthName ) {
                                   	 	if(monthNum == i){
                                   	 		html += '<span class="month_title">'+mnthName+'</span>';
                                   	 	}
                                   	});
                        			 }
                        		} // for close.
                        		
               			for(i in response){
              				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2)
              						&& fullDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
              				
              					if(response[i].selecteDt.slice(0,2) == dateVal ){
              						html += '<span class="cal_date current_date mark_date hiddenSpan">';
                  					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                  					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
              					}else{
              						html += '<span class="cal_date current_date hiddenSpan">';
                  					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                  					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
              					}
              					
              				}else{
              					if(response[i].selecteDt.slice(0,2) == 01 || 
                      					 response[i].selecteDt.slice(0,2) == 15 &&
                        					response[i].selecteDt.slice(3,5) >= fullDate.slice(3,5) ){
              						var monthNum = response[i].selecteDt.slice(3,5);
                          			 monthNum = (monthNum-1) ;
                          			 jQuery.each( monthArray, function( i, mnthName ) {
                                     	 	if(monthNum == i){
                                     	 		html += '<span class="month_title">'+mnthName+'</span>';
                                     	 	}
                                     	});
              					}
              					
              					var blueDtval = '';
              					  for(var record in userBkdRecrds){	
              						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
              								 && userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
              							if( response[i].selecteDt == selctdVal ){
              								html += '<span class="cal_date mark_date loggedInUser hiddenSpan">';
                          					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                          					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                          					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
              							}else{
              								html += '<span class="cal_date  loggedInUser hiddenSpan">';
                          					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                          					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                          					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
              							}
              						}
              					} // for (userBkdRecrds)close.
              					
              					if(blueDtval != response[i].selecteDt.slice(0,2)){
              						
              						if(response[i].selecteDt.slice(0,2) == 01 || 
                         					 response[i].selecteDt.slice(0,2) == 15 &&
                           					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                 						var monthNum = response[i].selecteDt.slice(3,5);
                             			 monthNum = (monthNum-1) ;
                             			 jQuery.each( monthArray, function( i, mnthName ) {
                                        	 	if(monthNum == i){
                                        	 		html += '<span class="month_title">'+mnthName+'</span>';
                                        	 	}
                                        	});
                 					}
              						
              						if(response[i].status == "B"){
              							if( response[i].selecteDt == selctdVal ){
              								html += '<span class="cal_date mark_date hiddenSpan">';
                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
              							}else{
              								html += '<span class="cal_date hiddenSpan">';
                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
              							}
              							
                  					}else  {
                  						if( response[i].selecteDt == selctdVal ){
                  							html += '<span class="cal_date mark_date green hiddenSpan">';
                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                  						}else{
                  							html += '<span class="cal_date green hiddenSpan">';
                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                  						}
                  						
                  					}
              					}
              					
              				}// else close.
          				}
                        		
          			html += '</div>';
          			html += '</div>';
          			$('#secndryDatesList').empty();
          			$('#prmryDatesList').empty();
          			$('#prmryDatesList').append(html);
          			$('#prmryDatesList').show();
          			
          			//removing ajax loader.
          			$('.ajax_loader').removeClass();	 
	        			
	        		 }
	        	 });
	         }
	         
		         function getSelectedDayCount(){
		             var selectedCheckedoutDate=$("#selectedDate").val();
		             var checkedindate=$("#checkedInvalue").val();
		            
		             if(selectedCheckedoutDate == 0){
		            	 $('#change_option_div').show();
		            	 $('#change_option').hide();
		             }
		             $.ajax({
		               url   : 'getCountNumberOfNights.htm', 
		               type  : 'get',
		               data:{checkoutdate:selectedCheckedoutDate,checkIndate:checkedindate},
		               success : function(data){
		               var noofNights = data;
		               $('#change_option_div').hide();
		               var html='<div id="change_option">'+'You have booked for '+ noofNights+' Night(s)</div>';
		               $('#detailschenged').html(html);
		                
		               }
		             });
		            
		            }
		         
		         //I observed this function not used  any where in this page.
                    function isLoggedIn() {
                    	var loggedin=$('#loggedin').val();
                         if(loggedin != ''){
                    			$('#myreservation').show();
                    			
                    		}else {
                    			$('#myreservation_options').show();	
                    			document.getElementById('fade').style.display='block';
                    	}
                    }
                   
                    function getUser() {
                        document.location.href = "getusers.htm";
                    }
                    
                    function downloadPDF(){
                    	 document.location.href = "downloadPDF.htm";
                    }
                    
                  
                    function closePopUp(){
                    	document.location.href = "returnToHme.htm";
                    }
                    
                    function bookingHistry(){
                    	$.ajax({
                			url   : 'bookingHistory.htm', 
                			type  : 'POST',
                			success : function(data){
                				
                				 var response  = jQuery.parseJSON(data);
                				 $('#reservationHistory').empty();
                				 var html = '';
                				 for(i in response){
	                				 html += '<div class="col-xs-5 col-md-5">';
	               					 html += '<p>';
	               					 html += '<strong>Name of Restaurant</strong><br />';
	               					 html += ''+response[i].hotelName+'';
	               					 html += '<br />';
	               					 html += ''+response[i].hotelAddress+'';
	               					 html += '<br />';
	               					 html += ''+response[i].hotelPhneNumber+','+response[i].city+','+response[i].postalCode+'';
	               					 html += '<br />';
	               					
	               					 html += '</p>';
	               					 html += '</div>';
	               					 html += '<div class="col-xs-5 col-md-5">';
	               					 html += '<p>';
	               					 html += '<strong>Reference Number :</strong> '+response[i].reservationNumber+'';
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
		               					 html += '<button class="btn btn-default btn-save bookingID" id="cancelBtn" type="button">';
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
	               						 html += '<span class="glyphicon glyphicon-trash arrivedResrvtn" aria-hidden="true"></span>';
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
	               						 html += '<span class="glyphicon glyphicon-trash deleteRecrdID " aria-hidden="true">';
	               						 html += '<input type="hidden" id="deleteBtn" value="'+response[i].bookingId+'"/>';
	               						 html += '</span>';
	               						 html += '</button>';
	               						 html += '</div>';
	               						 html += '</div>';
	               					 }else if ( response[i].status == 'not visited'){
	               						 html += '<div class="col-xs-2 col-md-2">';
	               						 html += '<div class="flag_icon"><span class="glyphicon glyphicon-minus red" aria-hidden="true"></span></div>';
	               						 html += '<div class="flag_icon">';
	               						 html += '<button class="btn btn-default btn-icon" type="button">';
	               						 html += '<span class="glyphicon glyphicon-trash notVisitedReservtn" aria-hidden="true">';
	               						 html += '<input type="hidden" id="deleteBtn" value="'+response[i].bookingId+','+response[i].reservationNumber+'"/>';
	               						 html += '</span>';
	               						 html += '</button>';
	               						 html += '</div>';
	               						 html += '</div>';
	               					 }
	               					 
                				}// for loop close.
                				$('#reservationHistory').append(html);
                				$('#myreservation_details').show();
                				document.getElementById('fade').style.display='block';
                			}//success close.
                		}); //ajax close.
                    }
                    
                    function getBookingHistory(){ 
                    	bookingHistry();
                	}                  
                 
                    function hidepwd(){
                    	$("#pwd").hide();
                    }
                    
                    function hidename(){
                    	$("#firstName").hide();
                    }
                    
                    function hideemailId(){
                    	$("#email").hide();
                    }
                    
                    function hidelastname(){
                    	$("#lastName").hide();
                    }
                    
                    function hidestreetname(){
                    	$("#streetName").hide();
                    }
                    function hideusername(){
                    	$("#userName").hide();
                    }
                                       
                    function hideConfirmpassword(){
                    	$("#confirmpassword").hide();
                    }
                    
                    function hidecontactnumber(){
                    	$("#contactNumber").hide();
                    }
                    
                    function hidedateofbirth(){
                    	$("#dateOfBirth").hide();
                    }
                    function hidecity(){
                    	$("#city").hide();
                    }
                    
                    function hidecountry(){
                    	$("#country").hide();
                    }
                    
                    function hidenotify(){
                    	$("#notifyduration").hide();
                    }
                                 
                    function hidepostalcode(){
                    	$("#postalcode").hide();
                    }
                     
                    function closeWindow(){
                    	document.location.href = 'returnToHme.htm';
                    }
                    
                    function emailShareFun(){                    	
               			var emailShare=document.getElementById("emailShare").value = "Y";               	
               			return emailShare;
                  	 } 
                    var pdfPathVal; 
                    function exportPdf(){
                    	 document.location.href = "exportPDF.htm";
                    }
                    
                    
                    function stopDefAction(evt) {
                        evt.preventDefault();
                    }
                    function reserveTable(event){  
                    	
                    	 var notes=$('#notes').val();
                     	 var value=$('#value').val();
                    	 var share=$('#emailShare').val();
                     	 var checkedindate=$('#checkedInvalue').val();
                     	 var res = value.split(',');
                     	 var categoryId=res[0];
                     	 var roomid=res[1];
                     	 var availcnt=res[2];
                     	 var checkedoutdate=$('#selectedDate').val();
                         var json='{"notes":"'+notes+'","emailShare":"'+share+'","checkedindate":"'+checkedindate+'","categoryId":"'+categoryId+'","roomid":"'+roomid+'","checkedoutdate":"'+checkedoutdate+'","availcnt":"'+availcnt+'"}';
                         
                         if(checkedoutdate == 0){
                        	 $('#ajaxLoader').hide();
                        	 event.preventDefault();
                        	 var statusMsg = 'please select checkOut date';
                        	 
                        	 $('#statusInfoSpan').html(statusMsg);
                        	 $('#statusInfoDiv').show().fadeOut(4000);
                        	
                         }
                         if(checkedoutdate != 0){
                        	 
                        	 $.ajax({
                            	 url  :  'isReservationProgress.htm',
                            	 type :  'POST',
                            	 data: {jsonData:json},
                            	 success : function(data){
                            		 
                            		 var result = data;
                            		 if(result == "true"){
                            			 
                            			 $.ajax({                 		 
                                     		 url:'saveReservation.htm',
                                     		 type: 'POST',  
                                             data: {jsonData:json},
                                             success: function(data){
                                             var response  = jQuery.parseJSON(data);
                                             //console.log('console json :'+JSON.stringify(data));
                                            	pdfPathVal = response.pdfPath;//pdfPath
                                             	console.log('iterate val :'+pdfPathVal);
                                            
                                             var html = '<p class="center">';
                                             var htmls = '<p >';
                                             $(response).each(function(i,res) {
                  	                            html+=' Reference Number : <b><span>'+res.reservationNumber +'</span></b>';
                  	                            htmls+='CheckedIn : ' +res.chckedInDate;
                                                htmls+='<br /> CheckedOut :  '+res.chckedOutDate+' ';
                                                htmls+=' <br />'+res.numberOfDays+' Night(s)';
                           					});
                                             html+='</p>';
                                             htmls+='</p>';
                                                $('#datesValues').html(htmls);
                                         		$('#referenceNumber').html(html);
                                         		$('#myreservation').hide();
                                         		$('#myreservation_confirm').show();
                                         		$('#reserveTable').prop('disabled', true);
                                            }                     
                                         }); 
                            			  
                            		 }else{
                            			 $('#isReservtnPrgrs').html('This Room is not availble,Please choose another one');
                                  		 $('.reserventnHide').prop('disabled', true);
                                  		 $('#ajaxLoader').hide();
                                  		 $('#hideTimer').hide();
                                  		 $('#myreservation').show();
                            		 }
                            	 }
                             });  //ajax call close.
                        	 $('#ajaxLoader').show();
                         }else{
                        	 $('#ajaxLoader').hide();
                         }
                     		  
                       	$.ajax({
                            url: "getHotelDetails.htm",
                            type: 'GET',
                            success: function(data){
                            var response  = jQuery.parseJSON(data);
                            var htmls = '<p>';
                            $(response).each(function(i,res) {
                            	 htmls+=res.hotelName;
                            	 htmls+='<br/>'+res.hotelAddress;
                            	 htmls+='<br/>'+res.phoneNumber;
                            	 	$.ajax({
                                        url: "getUserDetails.htm",
                                        type: 'GET',
                                        success: function(data){
                                        var response  = jQuery.parseJSON(data);
                                        var htmlls = '<p>';
                                        //showing username, email and contact number.
                                        $(response).each(function(i,res) {
                                        	 htmlls += res.userName;
                                        	 htmlls += '<br/>'+res.email;
                                        	 htmlls += '<br/>'+res.phoneNumber;
                						 });
                                        htmlls+='</p>';
                                   	 		$('#confirmuserDetails').html(htmlls);
                                       }
                                    });
    						 });
                             htmls+='</p>';
                       		 $('#confirmHotelDetails').html(htmls);
	                       		
  						   }//success function close.
                        });// getHotelDetails.htm ajax call close.
                     	
                        //$('#ajaxLoader').show(); // 
                      }// reserveTable() function close.
                     
                      var userBkdRecrds = '';
                      function getLoggedInUserDates(){
                    	  userBkdRecrds = '';
                    	  $.ajax({
	   	                    	 url    : 'getLoggedInUserReservedDates.htm',
	   	                    	 type   : 'POST',
	   	                    	 success : function(data){
	   	                    		userBkdRecrds =  jQuery.parseJSON(data);
	                       	 }//success function close.
	                         });//ajax function close.
                      }
                      
                      function showDatesOnDatesBar(catVal,fullDate,selctdVal,blckDate){
                    	var val = false;
                    	
                    	 userBkdRecrds = '';
                   	  $.ajax({
                   		 url   : 'getLoggedInUserReservedDates.htm ',
                   		 type  : 'POST',
                   		 success : function(data){
                   			 if(data !== ""){
                   				userBkdRecrds = jQuery.parseJSON(data);
                   				datesList(catVal,fullDate,selctdVal,blckDate);
                   			 }else{
                   				datesList(catVal,fullDate,selctdVal,blckDate);
                   			 }
                   		   }
                   	  	});
                      }
                      
                      function datesList(catVal,fullDate,selctdVal,blckDate){
                    	  
                    	  $('#secndryDatesList').empty();
	                      $('#prmryDatesList').empty();
	                      $.ajax({
	                     	url   : 'getDatesListOnCatgryType.htm?catgryId='+catVal,
	                      	type  : 'POST',
	                      	success : function(data){
	                      		var response =  jQuery.parseJSON(data);
	                      		var currDate = $('#currentDate span').text();
	                         	currDate = currDate.replace('/','-');
	                         	currDate = currDate.replace('/','-');
	                         	
	                         	 var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
	                         	 var html = '';
	                  			
	                  			 html += '<div class="piece getDateVal" >';
	                  			 html += '<div class="small_square" >';
	                  			var mnthNme = '';
	                			var count = 0;
	            				var pxls = 0;
	                			 for(j in response){
	                				
	                         		 if(j == 0){
	                         			 var monthNum = response[j].selecteDt.slice(3,5);
	                         			 monthNum = (monthNum-1) ;
	                         			 jQuery.each( monthArray, function( i, mnthName ) {
	                                    	 	if(monthNum == i){
	                                    	 		html += '<span class="month_title">'+mnthName+'</span>';
	                                    	 	}
	                                    	});
	                         			 }
	                         		} // for close.
	                  			
	                  				for(i in response){
	                  					
	                      				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2)
	                      						&& fullDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                      					
	                      					if( response[i].selecteDt == selctdVal ){
	                      						html += '<span class="cal_date current_date mark_date hiddenSpan">';
		                      					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                      					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                      					}else{
	                      						html += '<span class="cal_date current_date hiddenSpan">';
		                      					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                      					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                      					}
	                      					
	                      				}else{
	                      					var blueDtval = '';
		                      					if(response[i].selecteDt.slice(0,2) == 01 || 
		                             					 response[i].selecteDt.slice(0,2) == 15 &&
		                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
		                     						var monthNum = response[i].selecteDt.slice(3,5);
		                                 			 monthNum = (monthNum-1) ;
		                                 			 jQuery.each( monthArray, function( i, mnthName ) {
		                                            	 	if(monthNum == i){
		                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
		                                            	 	}
		                                            	});
		                     					}
	                      					  
		                      					for(var record in userBkdRecrds){	
	                      						  
	                      						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
	                      								&& userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                      							if(selctdVal == response[i].selecteDt){
	                      								html += '<span class="cal_date loggedInUser mark_date hiddenSpan">';
		                              					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                              					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
		                              					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	                      							}else{
	                      								html += '<span class="cal_date loggedInUser hiddenSpan">';
		                              					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                              					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
		                              					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	                      							}
	                      							  
	                      						}
	                      					} // for (userBkdRecrds)close.
	                      					
	                      					if(blueDtval != response[i].selecteDt.slice(0,2)){
	                      						//showing  month name.
	                      						if(response[i].selecteDt.slice(0,2) == 01 || 
		                             					 response[i].selecteDt.slice(0,2) == 15 &&
		                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
		                     						var monthNum = response[i].selecteDt.slice(3,5);
		                                 			 monthNum = (monthNum-1) ;
		                                 			 jQuery.each( monthArray, function( i, mnthName ) {
		                                            	 	if(monthNum == i){
		                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
		                                            	 	}
		                                            	});
		                     					}
	                      						
	                      						if(response[i].booked == response[i].noOFRooms){
	                      							if(selctdVal == response[i].selecteDt){
	                      								html += '<span class="cal_date mark_date hiddenSpan">';
		                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                      							}else{
	                      								html += '<span class="cal_date hiddenSpan">';
		                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                      							}
	                          					}else {
	                          						if(response[i].selecteDt.slice(0,2) == 01 || 
	   	                             					 response[i].selecteDt.slice(0,2) == 15 &&
	   	                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
	   	                     						var monthNum = response[i].selecteDt.slice(3,5);
	   	                                 			 monthNum = (monthNum-1) ;
	   	                                 			 jQuery.each( monthArray, function( i, mnthName ) {
	   	                                            	 	if(monthNum == i){
	   	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
	   	                                            	 	}
	   	                                            	});
	   	                     					}
	                          						if(blckDate == selctdVal && blckDate == response[i].selecteDt){
	                          							html += '<span class="cal_date mark_date hiddenSpan">';
		                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                          						}  else if(selctdVal == response[i].selecteDt  ){
	                          							html += '<span class="cal_date mark_date green hiddenSpan">';
		                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                          						}else{
	                          							html += '<span class="cal_date green hiddenSpan">';
		                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
		                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                          						}
	                          						
	                          					}
	                      					}
	                      					
	                      				}
	                  				}
	                  			html += '</div>';
	                  			html += '</div>';
	                  			
	                  			$('#secndryDatesList').empty();
	                  			$('#prmryDatesList').empty();
	                  			$('#prmryDatesList').append(html);
	                  			$('#prmryDatesList').show();
	                      	}
	                      });//ajax function close.
                      }
                  
                      var userIdVal = '';
                		function getGreenUserDetails(){
                			$.ajax({
    							url : 'getGreenUserDetails.htm',
    							type : 'POST',
    							success: function(data){
    								var userDetails = jQuery.parseJSON(data);
    								userIdVal = userDetails.userId;
    							}
    						});
                		}
                		
                      
                      function getDatesByRoomNumber(fullDate,userIdVal,selectedCategory,roomNo){
                    	  var val = false;
                    	  getGreenUserDetails();
                    	  val = true;
                    	  if(val == true){
                    		  $.ajax({
                          		url   : 'getDatesByRoomNum.htm',
                          		type  : 'POST',
                          		data : {cat:selectedCategory,roomNo:roomNo,currDate:fullDate},
                          		success:function(data){
                          			var response = jQuery.parseJSON(data);
                          			
                          			$('#prmryDatesList').hide();
                          			 var html = '';
                          			 html += '<div class="piece">';
                          			 html += '<div class="small_square">';
                          			var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec']; 
  									
                          			var mnthNme = '';
                         			var count = 0;
                     				var pxls = 0;
                         			 for(j in response){
                         				
                                  		 if(j == 0){
                                  			 var monthNum = response[j].roomAvailDate.slice(3,5);
                                  			 monthNum = (monthNum-1) ;
                                  			 jQuery.each( monthArray, function( i, mnthName ) {
                                             	 	if(monthNum == i){
                                             	 		html += '<span class="month_title">'+mnthName+'</span>';
                                             	 	}
                                             	});
                                  			 }
                                  		} // for close.
                                  		
                                  		fullDate = fullDate.replace('-','/');
                                  		fullDate = fullDate.replace('-','/');
                          				for(i in response){
                          					
                          					//showing month name code.
                          					if(response[i].roomAvailDate.slice(0,2) == 01 || 
	                             					 response[i].roomAvailDate.slice(0,2) == 15 &&
	                               					response[i].roomAvailDate.slice(3,5) >= fullDate.slice(3,5) ){
	                     						var monthNum = response[i].roomAvailDate.slice(3,5);
	                                 			 monthNum = (monthNum-1) ;
	                                 			 jQuery.each( monthArray, function( i, mnthName ) {
	                                            	 	if(monthNum == i){
	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
	                                            	 	}
	                                            	});
	                     					}
  	                          				if(fullDate == response[i].roomAvailDate){
  	                          					html += '<span class="cal_date current_date">'+response[i].roomAvailDate.slice(0,2)+'</span>';
  	                          				}else{
	  	                          				
  	                          					if(response[i].roomAvailDate.slice(0,2) == 01 || 
		                             					 response[i].roomAvailDate.slice(0,2) == 15 &&
		                               					response[i].roomAvailDate.slice(3,5) >= fullDate.slice(3,5) ){
		                     						var monthNum = response[i].roomAvailDate.slice(3,5);
		                                 			 monthNum = (monthNum-1) ;
		                                 			 jQuery.each( monthArray, function( i, mnthName ) {
		                                            	 	if(monthNum == i){
		                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
		                                            	 	}
		                                            	});
		                     					}
  	                          					if(userIdVal == response[i].userId){
  	                          						html += '<span class="cal_date loggedInUser hideSpan">'+response[i].roomAvailDate.slice(0,2)+'';
  	                          						html += '<input type = "hidden" value="'+response[i].roomAvailDate+'">';
  	                          						html += '</span>';
  	                          					}else if(response[i].colourCode == 'green'){
  	                          						html += '<span class="cal_date green hideSpan">'+response[i].roomAvailDate.slice(0,2)+'';
  	                          						html += '<input type = "hidden" value="'+response[i].roomAvailDate+'">';
  	                          						html +='</span>';
  	                          					}else {
  	                          						html += '<span class="cal_date hideSpan">'+response[i].roomAvailDate.slice(0,2)+'';
  	                          						html += '<input type = "hidden" value="'+response[i].roomAvailDate+'">';
  	                          						html += '</span>';
  	                          					}
  	                          				}
                          				}
                          			html += '</div>';
                          			html += '</div>';
                          			$('#secndryDatesList').empty();
                          			$('#secndryDatesList').append(html);
                          			$('#secndryDatesList').show(); 
                          		}
                          	}); //ajax function close.
                    	  }
                    	 
                      }
                      
                      // thre is no specific category, we havae show blue dates
                      function getLoggedInUserDatesOncategoryType(catVal){
                    	  userBkdRecrds = '';
                    	  //'getLoggedInUserDatesOnCategorytype.htm?catgry='+catVal
                    		// is replaced with getLoggedInUserReservedDates.htm  call.
                    	  $.ajax({
                    		 url   : 'getLoggedInUserReservedDates.htm',
                    		 type  : 'POST',
                    		 success : function(data){
                    			 userBkdRecrds = jQuery.parseJSON(data);
                    		 }
                    	  });
                    	  
                      }
                      
                      
                      function  getDatesListOnCatgryType(catVal,fullDate){
                    	 if(catVal != 0){
                    		 getLoggedInUserDatesOncategoryType(catVal);
                    	 }
                    	 
                    	 function getLoggedInUserDatesOncategoryType(catVal){
                       	  userBkdRecrds = '';
                       	  $.ajax({
                       		 url   : 'getLoggedInUserReservedDates.htm ',
                       		 type  : 'POST',
                       		 success : function(data){
                       			 if(data !== ""){
                       				userBkdRecrds = jQuery.parseJSON(data);
                       				getDatesOnCatgryType(catVal,fullDate);
                       			 }else{
                       				getDatesOnCatgryType(catVal,fullDate);
                       			 }
                       		 }
                       	  });
                         }
                      }
                      
                      function getDatesOnCatgryType(catVal,fullDate){
                    	  //get dates list and shows it on dates bar.
 	                      $('#secndryDatesList').empty();
	                      $('#prmryDatesList').empty();
	                      $.ajax({
	                     	url   : 'getDatesListOnCatgryType.htm?catgryId='+catVal,
	                      	type  : 'POST',
	                      	success : function(data){
	                      		var response =  jQuery.parseJSON(data);
	                      		
	                      		var currDate = $('#currentDate span').text();
	                         	currDate = currDate.replace('/','-');
	                         	currDate = currDate.replace('/','-');
	                         	
	                         	 var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
	                         	 var html = '';
	                  			
	                  			 html += '<div class="piece getDateVal" >';
	                  			 html += '<div class="small_square" >';
	                  			var mnthNme = '';
	                			var count = 0;
	            				var pxls = 0;
	                			 for(j in response){
	                				
	                         		 if(j == 0){
	                         			 var monthNum = response[j].selecteDt.slice(3,5);
	                         			 monthNum = (monthNum-1) ;
	                         			 jQuery.each( monthArray, function( i, mnthName ) {
	                                    	 	if(monthNum == i){
	                                    	 		html += '<span class="month_title">'+mnthName+'</span>';
	                                    	 	}
	                                    	});
	                         			 }
	                         		} // for close.
	                         		
	                  				for(i in response){
	                      				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2)
	                      						&& currDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                      					html += '<span class="cal_date current_date hiddenSpan">';
	                      					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                      					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                      				}else{
	                      					var blueDtval = '';
	                      					
	                      					if(response[i].selecteDt.slice(0,2) == 01 || 
	                             					 response[i].selecteDt.slice(0,2) == 15 &&
	                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
	                     						var monthNum = response[i].selecteDt.slice(3,5);
	                                 			 monthNum = (monthNum-1) ;
	                                 			 jQuery.each( monthArray, function( i, mnthName ) {
	                                            	 	if(monthNum == i){
	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
	                                            	 	}
	                                            	});
	                     					}
	                      					  for(var record in userBkdRecrds){	
	                      						if(response[i].selecteDt.slice(0,2) == 01 || 
	 	                             					 response[i].selecteDt.slice(0,2) == 15 &&
	 	                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
	 	                     						var monthNum = response[i].selecteDt.slice(3,5);
	 	                                 			 monthNum = (monthNum-1) ;
	 	                                 			 jQuery.each( monthArray, function( i, mnthName ) {
	 	                                            	 	if(monthNum == i){
	 	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
	 	                                            	 	}
	 	                                            	});
	 	                     					}
	                      						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
	                      								&& userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                      							html += '<span class="cal_date loggedInUser hiddenSpan">';
	                              					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                              					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                              					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	                      						}
	                      					} // for (userBkdRecrds)close.
	                      					
	                      					if(blueDtval != response[i].selecteDt.slice(0,2)){
	                      						
	                      						if(response[i].selecteDt.slice(0,2) == 01 || 
		                             					 response[i].selecteDt.slice(0,2) == 15 &&
		                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
		                     						var monthNum = response[i].selecteDt.slice(3,5);
		                                 			 monthNum = (monthNum-1) ;
		                                 			 jQuery.each( monthArray, function( i, mnthName ) {
		                                            	 	if(monthNum == i){
		                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
		                                            	 	}
		                                            	});
		                     					}
	                      						
	                      						if(response[i].booked == response[i].noOFRooms){
                      								html += '<span class="cal_date hiddenSpan">';
	                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                          					}else {
                          							html += '<span class="cal_date green hiddenSpan">';
	                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                          					}
	                      					}
	                      					
	                      				}
	                  				}
	                  			html += '</div>';
	                  			html += '</div>';
	                  			
	                  			$('#secndryDatesList').empty();
	                  			$('#prmryDatesList').empty();
	                  			$('#prmryDatesList').append(html);
	                  			$('#prmryDatesList').show();
	                      	}
	                      });//ajax function close. 
 	                      
                      }// getDatesOnCatgryType function close.
                  
                    function getLoginDetails(){
                    	var logindetails=$("#logindetails").val;
                    	  var logoutsuccess = $("#logoutsuccess").val();                    	
                    	if(logoutsuccess != ''){
                    		$("#alertMsgPopUp2").show();                    			                      		
                           	     var pickHotel = $('#pickHotel').val();                      	                          
           	                    $("#alertPopSpanId").empty();
           	                    $("#alertPopSpanIdGreen").append(logoutsuccess);
           	                               	
                    	}else if(logindetails="login popup show"){
                    		$('#mylogin').show();
                    		document.getElementById('fade').style.display='block';
                    	}
                    }
                      
                    function getServiceLoginDetails(){  
                    	$('#emailOrUsernme').val('');
                   	    $('#adminPwd').val('');
                   		$('#myadminlogin').show();
                   		document.getElementById('fade').style.display='block';                    	
                    }
                    
                      
                  var arrLength = 0;
                  $(document).ready(function() {

                	  var selctdVal = '';
                	  
                	  $("select#category").change(function() {
                		  $('#noOfDays').val('');
                		  $('#noOfDays').prop('readonly', false);
                		  var category=$('#category').val();
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
                          var fullDate =  Day + '/' + Month + '/' + Year;
                         
 	   	               	   $('#currentDateVal').empty();
 	                       $('#currentDateVal').append(fullDate);
 	                       $('#currentDateVal').show(); 
   	               	 	 
   	               	    var catVal = $('#category').val();
   	               		
   	               	    if(catVal != 0){
   	               			
   	               	    	$('#noOfDays').val('');
   	               	    	 $.ajax({
 	                           url: 'getRoomsList.htm?selectedCategory='+catVal,
 	                           type: 'POST',
 	                           success: function(data){
 	                           var response = result = jQuery.parseJSON(data);
 	                          	
 	                          	$('#roomNo').empty();
 	                              var selectRoom = 0;
 	                             	   $('#roomNo').append("<option value="+ 0 +">"+"Select Room"+"</option>");
 	                                    for(i in response){
 	                                 	   $('#roomNo').append("<option value="+ response[i] +">"+response[i]+"</option>");
 	                                    } 
 	                           		}
 	                          });//ajax functio close.  
 	                          
 	                         getDatesListOnCatgryType(catVal,fullDate);
 	                          
   	               	    }  // if close.
   	               	    
   	               		if(catVal != 0 && currentDate != null){
   	               			
   	               		var noOfDays = $('#noOfDays').val();
  	               		 if(noOfDays == ''){
  	               			noOfDays = 0;
  	               		 }
  	               		 $('#roomNo').val('');
  	               		 $('#roomNo').empty();
                        	
     	               		var noOfDays = $('#noOfDays').val();
     	               		$.ajax({
                          		url : 'roomInfoByCategoryType.htm?type='+catVal+'&currDarte='+fullDate,
                          		type: 'POST',
                          		success:function(data){
                         			var response = jQuery.parseJSON(data);
                          			$('#roomInfoDiv').empty();
                          			
                          			var html = '';
                                     	html += '<div class="item active">';
                                     	html += ' <div class="carousel-caption">';
          	                           	for(i in response){
          	                           		if('green' == response[i].colourCode && 
          	                           			response[i].availcnt != 0){
     	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
     	     	                           		html += '<div class="cell_room booked" id="removeProcessing">';
     	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
     	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+'"></input>';
     	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
     	     	                           		html += ''+response[i].categoryName +'<br/>';
     	     	                           		html += ''+response[i].availcnt;
     	     	                           		html += '</a>';
     	     	                           		html += '</div>';
     	     	                           		html += '</div>';
          	                           		}else{
          	                           			var val = 'X';
     	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
     	     	                           		html += '<div class="cell_room reserved">';
     	     	                           		html += '<p class="room_info red" >';
     	     	                           		html += '<br/>';
     	     	                           		html += ''+val+'';
     	     	                             	html += '<br/>';
     	     	                             	html += '</p>';
     	     	                           		html += '</div>';
     	     	                           		html += '</div>';
          	                           		}
          	                           	}
                                     	html += '</div>';
                                 		html += '</div>';
                                 		$('#carouselDivPrimary').hide();
                                 		$('#carouselDiv').empty();
                                 		$('#carouselDiv').append(html);
                                 		$('#carouselDiv').show();
                                   }//success close.
                          	});//ajax functio close.
                          	
     	               	  } // if close.
   	               	 		
 		  	               	 if(catVal == 0){
 		  	               		 var noOfDays = $('#noOfDays').val();
 		  	               		 if(noOfDays == ''){
 		  	               			noOfDays = 0;
 		  	               		 }
 		  	               		 $('#roomNo').val('');
 		  	               		 $('#roomNo').empty();
 		                           var userBkdRecrds = ''; 
 		                           $.ajax({
 		      	                    	 url    : 'getLoggedInUserReservedDates.htm',
 		      	                    	 type   : 'POST',
 		      	                    	 success : function(data){
 		      	                    		userBkdRecrds =  jQuery.parseJSON(data);
 		                          	 }//success function close.
 		                            });//ajax function close.
 		                       	
 		                       	
 		                           $('#secndryDatesList').empty();
 		                           $('#prmryDatesList').empty();
 		                           $.ajax({
 		                          	url   : 'getDatesList.htm',
 		                           	type  : 'POST',
 		                           	success : function(data){
 		                           		var response =  jQuery.parseJSON(data);
 		                           		var currDate = $('#currentDate span').text();
 		                              	currDate = currDate.replace('/','-');
 		                              	currDate = currDate.replace('/','-');
 		                              	
 		                              	 var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
 		                              	 var html = '';
 		                       			
 		                       			 html += '<div class="piece getDateVal" >';
 		                       			 html += '<div class="small_square" >';
 		                       			var mnthNme = '';
 		                     			var count = 0;
 		                 				var pxls = 0;
 		                     			 for(j in response){
 		                     				
 		                              		 if(j == 0){
 		                              			 var monthNum = response[j].selecteDt.slice(3,5);
 		                              			 monthNum = (monthNum-1) ;
 		                              			 jQuery.each( monthArray, function( i, mnthName ) {
 		                                         	 	if(monthNum == i){
 		                                         	 		html += '<span class="month_title">'+mnthName+'</span>';
 		                                         	 	}
 		                                         	});
 		                              			 }
 		                              		} // for close.
 		                              		
 		                       				for(i in response){
 		                           				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2)
 		                           						&& currDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
 		                           					html += '<span class="cal_date current_date hiddenSpan">';
 		                           					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
 		                           					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
 		                           				}else{
 		                           					var blueDtval = '';
	 		                           				 
 		                           					if(response[i].selecteDt.slice(0,2) == 01 || 
		 	                             					 response[i].selecteDt.slice(0,2) == 15 &&
		 	                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
		 	                     						var monthNum = response[i].selecteDt.slice(3,5);
		 	                                 			 monthNum = (monthNum-1) ;
		 	                                 			 jQuery.each( monthArray, function( i, mnthName ) {
		 	                                            	 	if(monthNum == i){
		 	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
		 	                                            	 	}
		 	                                            	});
		 	                     					}
 		                           					  for(var record in userBkdRecrds){	
 		                           						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
 		                           								&& userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
 		                           							html += '<span class="cal_date loggedInUser hiddenSpan">';
 		                                   					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
 		                                   					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
 		                                   					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
 		                           						}
 		                           					} // for (userBkdRecrds)close.
 		                           					
 		                           					if(blueDtval != response[i].selecteDt.slice(0,2)){
 		                           					 
 		                           						if(response[i].selecteDt.slice(0,2) == 01 || 
 		 	                             					 response[i].selecteDt.slice(0,2) == 15 &&
 		 	                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
 		 	                     						var monthNum = response[i].selecteDt.slice(3,5);
 		 	                                 			 monthNum = (monthNum-1) ;
 		 	                                 			 jQuery.each( monthArray, function( i, mnthName ) {
 		 	                                            	 	if(monthNum == i){
 		 	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
 		 	                                            	 	}
 		 	                                            	});
 		 	                     						}
 		                           					 
 		                           						if(response[i].booked == response[i].noOFRooms){
 		                               						html += '<span class="cal_date hiddenSpan">';
 		                               						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
 		                               						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
 		                               					}else  {
 		                               						html += '<span class="cal_date green hiddenSpan">';
 		                               						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
 		                               						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
 		                               					}
 		                           					}
 		                           					
 		                           				}
 		                       				}
 		                       			html += '</div>';
 		                       			html += '</div>';
 		                       			
 		                       			$('#secndryDatesList').empty();
 		                       			$('#prmryDatesList').empty();
 		                       			$('#prmryDatesList').append(html);
 		                       			$('#prmryDatesList').show();
 		                           	}
 		                           });//ajax function close.
 		                         
 		                           var userId ='';
			                        $.ajax({
											url : 'getGreenUserDetails.htm',
											type : 'POST',
											success: function(data){
												var userDetails = jQuery.parseJSON(data);
												userId = userDetails.userId;
											}
									}); 
 		                            $('#carouselDiv').empty();
 		                          	 $.ajax({
 		                                   url: 'getRoomsAvailOnCurrentDate.htm?noOfDays='+noOfDays,
 		                                   type: 'POST',
 		                                   success: function(data){
 		                                  var response =  jQuery.parseJSON(data);
 		                                	var html = '';
 		                                 	html += '<div class="item active">';
 		                                 	html += ' <div class="carousel-caption">';
 		      	                           	
 		                                 	for(i in response){
 		                                 		if(userId == response[i].userId){		
					 	                           	html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
				 	                           		html += '<div class="cell_room booked mybook_room ">';
				 	                           		html += '<span href="javascript:void(0)" class="user_bkd_room_info usrBkdRroomInfo" >';
				 	                             	html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
				 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
				 	                           		html += ''+response[i].categoryName +'<br/>';
				 	                           		html += ''+response[i].availcnt;
				 	                           		html += '</span>';
				 	                           		html += '</div>';
				 	                           		html += '</div>';
					                           	}else if('green' == response[i].colourCode && 
 		           	                           		response[i].availcnt != 0){
 		      	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
 		      	     	                           		html += '<div class="cell_room booked" id="removeProcessing">';
 		      	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
 		      	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+'"></input>';
 		      	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
 		      	     	                           		html += ''+response[i].categoryName +'<br/>';
 		      	     	                           		html += ''+response[i].availcnt;
 		      	     	                           		html += '</a>';
 		      	     	                           		html += '</div>';
 		      	     	                           		html += '</div>';
 		           	                           		}else{
 		           	                           			var val = 'X';
 		      	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
 		      	     	                           		html += '<div class="cell_room reserved">';
 		      	     	                           		html += '<p class="room_info red" >';
 		      	     	                           		html += '<br/>';
 		      	     	                           		html += ''+val+'';
 		      	     	                             	html += '<br/>';
 		      	     	                             	html += '</p>';
 		      	     	                           		html += '</div>';
 		      	     	                           		html += '</div>';
 		           	                           		}
 		      	                           	}
 		                                 	html += '</div>';
 		                             		html += '</div>';
 		                             		$('#roomInfoDiv').empty();
 		                             		$('#carouselDivPrimary').hide();
 		                             		$('#carouselDiv').append(html);
 		                             		$('#carouselDiv').show(); 
 		                               }
 		                           });//ajax function close.
 		  	               	 }//if close.
                      	  
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
                             var fullDate =  Day + '/' + Month + '/' + Year;
                            
  	   	               	   $('#currentDateVal').empty();
  	                       $('#currentDateVal').append(fullDate);
  	                       $('#currentDateVal').show(); 
                	  });// select#category close.
                	  
                	  $('#hidePwd').change(function(){
                        	$("#pwd").hide();
                        });
                  	  
                  	  $('#hideEmail').change(function(){
                        	$("#emailId").hide();
                        });
                  	  
                	  
	            		
	            		$('#noOfDays').change(function(event){
	            			
	            			var selectedCategory = $("#category").val();
                        	var currDate = $('#currentDate span').text();
                        
                      		var roomNo = $('#roomNo').val();
                      		var noOfDays = $('#noOfDays').val();
                      		$('#carouselDiv').empty();
                          	$('#roomInfoDiv').empty();
                          	var dateValue = $('#currentDate span').text();
                          	
                          	if(noOfDays == ''){
                          		noOfDays = 0;
                          	}
                          	
                          	if(selectedCategory == 0){
                          		
			                        var userId ='';
			                        var blckDate = '';
			                        $.ajax({
											url : 'getGreenUserDetails.htm',
											type : 'POST',
											success: function(data){
												var userDetails = jQuery.parseJSON(data);
												userId = userDetails.userId;
											}
									});  		
			                       
			                    	var val = 1;
			                      
			                    	$.ajax({
			                            url   :'getSelectedDateAvailRooms.htm',
			 							type  :'POST',
			 							data  :{date:currDate,selectedCategory:selectedCategory,noOfDays:noOfDays},
			                              success: function(data){
				                            var response =  jQuery.parseJSON(data);
				                            var length = response.length;
				                            
	                      					var count  = 0;
				                          	var html = '';
				                           	html += '<div class="item active">';
				                           	html += ' <div class="carousel-caption">';
				                           	html += '<div class="ajax_loader"></div>';
				                           	for(i in response){
				                           		if(userId == response[i].userId){		
					 	                           	html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
				 	                           		html += '<div class="cell_room booked mybook_room ">';
				 	                           		html += '<span href="javascript:void(0)" class="user_bkd_room_info usrBkdRroomInfo" >';
				 	                           	    html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
				 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
				 	                           		html += ''+response[i].categoryName +'<br/>';
				 	                           		html += ''+response[i].availcnt;
				 	                           		html += '</span>';
				 	                           		html += '</div>';
				 	                           		html += '</div>';
					                           	}else if('green' == response[i].colourCode && response[i].availcnt >= 1 &&
			     	                           			response[i].availcnt >= noOfDays ){
				     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
				     	                           		html += '<div class="cell_room booked" id="removeProcessing">';
				     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
				     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+'"></input>';
				     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
				     	                           		html += ''+response[i].categoryName +'<br/>';
				     	                           		html += ''+response[i].availcnt;
				     	                           		html += '</a>';
				     	                           		html += '</div>';
				     	                           		html += '</div>';
			     	                           		}else{
			     	                           			var val = 'X';
				     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
				     	                           		html += '<div class="cell_room reserved">';
				     	                           		html += '<p class="room_info red" >';
				     	                           		html += '<br/>';
				     	                           		html += ''+val+'';
				     	                             	html += '<br/>';
				     	                             	html += '</p>';
				     	                           		html += '</div>';
				     	                           		html += '</div>';
				     	                           		
				     	                           		count = count + 1;
				     	                           		if(count == length){
				     	                           			blckDate = response[i].roomAvailDate;
	         	     	                           		}
			     	                           		}
				                           	}
			                           	html += '</div>';
			                       		html += '</div>';
			                       		
			                       		
			                       		$('#roomInfoDiv').empty();
			                       		$('#carouselDivPrimary').hide();
			                       	    $('#carouselDiv').empty();
			                       		$('#carouselDiv').append(html);
			                       		$('#carouselDiv').show(); 
			                       		
			                       		var dateVal = selctdVal.slice(0,2);
			                       		fullDate = fullDate.replace('/','-');
		                          		fullDate = fullDate.replace('/','-');
			                       			
			                       			showDatesListOnDatesBar();
			                       		
			                         }
			 							
				                     });//ajax function close.
				                     
				    					
                    }else if(selectedCategory != 0 && roomNo == 0 ){
                      		//getting  userId 
                      		var userId ='';
                      		var blckDate = ''; 
                      		
							$.ajax({
								url : 'getGreenUserDetails.htm',
								type : 'POST',
								success: function(data){
									var userDetails = jQuery.parseJSON(data);
									userId = userDetails.userId;
								}
							});
							
                      			$.ajax({
                      				url : 'getRoomsByDayCountAndCategory.htm',
                      				type : 'POST',
                      				data :{selectedCategory:selectedCategory,noOfDays:noOfDays,currentDate:dateValue},
                      				success : function(data){
                      					var response  = jQuery.parseJSON(data);
                      					var length = response.length;
                      					var count  = 0;
                      					
                      					$('#roomInfoDiv').empty();
                              			var html = '';
                                         	html += '<div class="item active">';
                                         	html += ' <div class="carousel-caption">';
                                         	html += '<div class="ajax_loader"></div>';
              	                           	for(i in response){
              	                           	if(userId == response[i].userId){		
        		 	                           	html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
        	 	                           		html += '<div class="cell_room booked mybook_room ">';
        	 	                           		html += '<span href="javascript:void(0)" class="user_bkd_room_info usrBkdRroomInfo" >';
        	 	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
        	 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
        	 	                           		html += ''+response[i].categoryName +'<br/>';
        	 	                           		html += ''+response[i].availcnt;
        	 	                           		html += '</span>';
        	 	                           		html += '</div>';
        	 	                           		html += '</div>';
        		                           	}else if('green' == response[i].colourCode && 
              	                           			response[i].availcnt >= noOfDays && response[i].availcnt != 0 ){
         	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
         	     	                           		html += '<div class="cell_room booked" id="removeProcessing">';
         	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
         	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+'"></input>';
         	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
         	     	                           		html += ''+response[i].categoryName +'<br/>';
         	     	                           		html += ''+response[i].availcnt;
         	     	                           		html += '</a>';
         	     	                           		html += '</div>';
         	     	                           		html += '</div>';
              	                           		}else{
              	                           			var val = 'X';
         	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
         	     	                           		html += '<div class="cell_room reserved">';
         	     	                           		html += '<p class="room_info red" >';
         	     	                           		html += '<br/>';
         	     	                           		html += ''+val+'';
         	     	                             	html += '<br/>';
         	     	                             	html += '</p>';
         	     	                           		html += '</div>';
         	     	                           		html += '</div>';
         	     	                           		count = count + 1;
         	     	                           		if(count == length){
         	     	                           		blckDate = response[i].roomAvailDate;
         	     	                           		}
              	                           		}
              	                           	}// for clsoe.
              	                           	
              	                           	
                                         	html += '</div>';
                                     		html += '</div>';
                                     	
                                     		$('#carouselDivPrimary').hide();
                                     		$('#carouselDiv').empty();
                                     		$('#carouselDiv').append(html);
                                     		$('#carouselDiv').show();
                                     		
                                     	
                                     		var dateVal = selctdVal.slice(0,2);
        		                       		fullDate = fullDate.replace('/','-');
        	                          		fullDate = fullDate.replace('/','-');
        		                       		
        	                          		showDatesListOnDatesBar();
                      				}
                      				
                      			}); // ajax close.
                      				
                      		}//else if close.
                      		
	            		});//no of days change function closed.
	                	
	            		
	            		jQuery(document).on('click', "#changeReservation", function(){
	            			var hotelId = jQuery(this).find('input').val();
	            			changeHotel(hotelId);
	            		});
	            		
	            		jQuery(document).on('click', "#cancelReservationBtn", function(){
	            			document.location.href="returnToHme.htm";
	            		});
	            		
	            		jQuery(document).on('click', ".hideSpan", function(){
	            			var selectedDate = jQuery(this).find('input').val();
	            			$('#currentDateVal').html(selectedDate);
	            		});
	            		
	            		//room Number chaged.
                      	$('#roomNo').change(function(){
                      		$('#noOfDays').val('');
                      		$('#noOfDays').prop('readonly', true);
                      		
                      		var selectedCategory = $("#category").val();
                   
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
                            
                      		var roomNo = $('#roomNo').val();
                      		
                          	if(roomNo != 0){
                          		//get dates and shows it on dates bar.
                          		getDatesByRoomNumber(fullDate,userIdVal,selectedCategory,roomNo);
                          		                              	
                             // get room numbers available dates and shows it on room info grid. 
                              	$.ajax({
                                    url: "roomAvailByCategoryAndRoomNum.htm",
                                    type: 'POST',
                                    data : {cat:selectedCategory,roomNo:roomNo,currDate:fullDate},
                                    success: function(data){
                                    var response  = jQuery.parseJSON(data);
                                    console.log('userIdVal :'+userIdVal);
                                    var html = '';
                                  	html += '<div class="item active">';
                                  	html += '<div class="carousel-caption">';
       	                           	for(i in response){               		
       	                           		if(userIdVal == response[i].userId){
    	   	                           		html += '<div class="col-xs-3  col-xs-3-fixed nopadding book_info">';
    	   	                           		html += '<div class="cell mybook_room">';
    	   	                           		html += '<a href="javascript:void(0)" class="usrBkdRroomInfo userBkd_room">';
    	   	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
    	   	                           		html += ''+response[i].roomAvailDate+'';
    	   	                           		html += '</a>';
    	   	                           		html += '</div>';
    	   	                           		html += '</div>';
       	                           		}else if(response[i].statusCode == 'A'){
    	   	                           		html += '<div class="col-xs-3  col-xs-3-fixed nopadding book_info">';
    	   	                           		html += '<div class="cell booked">';
    	   	                           		html += '<a href="javascript:void(0)" class="room_info">';
    	   	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+',1,'+response[i].categoryId+'"></input>';
    	   	                           		html += ''+response[i].roomAvailDate+'';
    	   	                           		html += '</a>';
    	   	                           		html += '</div>';
    	   	                           		html += '</div>';
       	                           		}else{
       	                           			var val = 'X';
    	   	                           		html += '<div class="col-xs-3  col-xs-3-fixed nopadding book_info">';
    	   	                           		html += '<div class="cell reserved">';
    	   	                           		html += '<p class="room_info red" style="margin: 0px 53px 0px;">';
    	   	                           		html += ''+val+'';
    	   	                           		html += '</p>';
    	   	                           		html += '</div>';
    	   	                           		html += '</div>';
       	                           		}
       	                           	}
                                  	html += '</div>';
                              		html += '</div>';
                              		$('#carouselDivPrimary').hide();
                              		$('#carouselDiv').hide();
                              		$('#roomInfoDiv').empty();
                              		$('#roomInfoDiv').append(html);
                              		$('#roomInfoDiv').show();
                                   }
                                }); //ajax call close.
                              
                                var currentDate = fullDate;
                                currentDate = currentDate.replace('-','/');
                                currentDate = currentDate.replace('-','/');
                                
                                $('#currentDateVal').html(currentDate);
       	                        $('#currentDateVal').show(); 
                                
	            			}else{
	            				$('#noOfDays').val('');
	            				$('#noOfDays').prop('readonly', false);
	            				
	            				var fullDate = $('#currentDate span').text();
	            				fullDate = fullDate.replace('-','/');
	            				fullDate = fullDate.replace('-','/');
	            				var catVal = $('#category').val();
	            				
	            				var userBkdRecrds = ''; 
	 	                         $.ajax({
		    	                    	 url    : 'getLoggedInUserReservedDates.htm',
		    	                    	 type   : 'POST',
		    	                    	 success : function(data){
		    	                    	 userBkdRecrds =  jQuery.parseJSON(data);
	 	                        	 }//success function close.
	 	                          });//ajax function close.
	 	                          
	 	                        //get dates list and shows it on dates bar.
	 	                          $.ajax({
	 	                         	url   : 'getDatesList.htm',
	 	                          	type  : 'POST',
	 	                          	success : function(data){
	 	                          		var response =  jQuery.parseJSON(data);
	 	                          		var currDate = $('#currentDate span').text();
	 	                             	currDate = currDate.replace('/','-');
	 	                             	currDate = currDate.replace('/','-');
	 	                             	
	 	                             	var monthsArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
	 	                             	var html = '';
	 	                      			 html += '<div class="piece getDateVal" >';
	 	                      			 html += '<div class="small_square" >';
	 	                      			var mnthNme = '';
	 	                     			var count = 0;
	 	                 				var pxls = 0;
	 	                     			 for(j in response){
	 	                     				
	 	                              		 if(j == 0){
	 	                              			 var monthNum = response[j].selecteDt.slice(3,5);
	 	                              			 monthNum = (monthNum-1) ;
	 	                              			 jQuery.each( monthsArray, function( i, mnthName ) {
	 	                                         	 	if(monthNum == i){
	 	                                         	 		html += '<span class="month_title">'+mnthName+'</span>';
	 	                                         	 	}
	 	                                         	});
	 	                              			 }
	 	                              		} // for close.
	 	                              		
	 	                      				for(i in response){
	 	                          				if(currDate.slice(0,2) == response[i].selecteDt.slice(0,2) 
	 	                          						&& fullDate == response[i].selecteDt){
	 	                          					html += '<span class="cal_date current_date hiddenSpan">';
	 	                          					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                          					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                          				}else{
	 	                          				  var blueDtval = '';
	 	                          				 
	 	                          				  if(response[i].selecteDt.slice(0,2) == 01 || 
	 	                             					 response[i].selecteDt.slice(0,2) == 15 &&
	 	                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
	 	                     						var monthNum = response[i].selecteDt.slice(3,5);
	 	                                 			 monthNum = (monthNum-1) ;
	 	                                 			 jQuery.each( monthsArray, function( i, mnthName ) {
	 	                                            	 	if(monthNum == i){
	 	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
	 	                                            	 	}
	 	                                            	});
	 	                     					}
	 	                      					  for(var record in userBkdRecrds){	
	 	                      						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
	 	                      								&& userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	 	                      							html += '<span class="cal_date loggedInUser hiddenSpan">';
	 	                              					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                              					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                              					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	 	                      						}
	 	                      					} // for (userBkdRecrds)close.
	 	                      					
	 	                      					if(blueDtval != response[i].selecteDt.slice(0,2)){
	 	                      					 
	 	                      						if(response[i].selecteDt.slice(0,2) == 01 || 
	 	                             					 response[i].selecteDt.slice(0,2) == 15 &&
	 	                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
	 	                     						var monthNum = response[i].selecteDt.slice(3,5);
	 	                                 			 monthNum = (monthNum-1) ;
	 	                                 			 jQuery.each( monthsArray, function( i, mnthName ) {
	 	                                            	 	if(monthNum == i){
	 	                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
	 	                                            	 	}
	 	                                            	});
	 	                     					}
	 	                      						
	 	                      						if(response[i].booked == response[i].noOFRooms){
	 	                          						html += '<span class="cal_date hiddenSpan">';
	 	                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                          					}else  {
	 	                          						html += '<span class="cal_date green hiddenSpan">';
	 	                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                          					}
	 	                      					}
	 	                          				}// else condition close.
	 	                      				}
	 	                      			html += '</div>';
	 	                      			html += '</div>';
	 	                      			$('#secndryDatesList').empty();
	 	                      			$('#prmryDatesList').empty();
	 	                      			$('#prmryDatesList').append(html);
	 	                      			$('#prmryDatesList').show();
	 	                          	}
	 	                          });//ajax function close.
							  
	 	                         $.ajax({
	                           		url : 'roomInfoByCategoryType.htm?type='+catVal+'&currDarte='+fullDate,
	                           		type: 'POST',
	                           		success:function(data){
	                          			var response = jQuery.parseJSON(data);
	                           			$('#roomInfoDiv').empty();
	                           			var html = '';
	                                      	html += '<div class="item active">';
	                                      	html += ' <div class="carousel-caption">';
	           	                           	for(i in response){
	           	                           		if('green' == response[i].colourCode && 
	           	                           			response[i].availcnt != 0){
	      	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	      	     	                           		html += '<div class="cell_room booked" id="removeProcessing">';
	      	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
	      	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+'"></input>';
	      	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	      	     	                           		html += ''+response[i].categoryName +'<br/>';
	      	     	                           		html += ''+response[i].availcnt;
	      	     	                           		html += '</a>';
	      	     	                           		html += '</div>';
	      	     	                           		html += '</div>';
	           	                           		}else{
	           	                           			var val = 'X';
	      	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	      	     	                           		html += '<div class="cell_room reserved">';
	      	     	                           		html += '<p class="room_info red" >';
	      	     	                           		html += '<br/>';
	      	     	                           		html += ''+val+'';
	      	     	                             	html += '<br/>';
	      	     	                             	html += '</p>';
	      	     	                           		html += '</div>';
	      	     	                           		html += '</div>';
	           	                           		}
	           	                           	}
	                                      	html += '</div>';
	                                  		html += '</div>';
	                                  		$('#carouselDivPrimary').hide();
	                                  		$('#carouselDiv').empty();
	                                  		$('#carouselDiv').append(html);
	                                  		$('#carouselDiv').show();
	                                    }//success close.
	                           	});//ajax functio close.
	 	                          
	            			}//else close.
                          	 
                      	});// room no onchange function close.
                	  

                      	var tdate = '';
                      	var roomId = 0;
                      	var availcnt = 0;
                      	var categoryId = 0;
                      	var usrId = '';
                      	var guestUsr = '';
                      	var reservationNumber = '';
     				    var dateVal = '';
     				    var guestUsr = '';
     				    var roomAvailDate = '';
                      	//room_info usrBkdRroomInfo
                      	 jQuery(document).on('click', ".usrBkdRroomInfo", function(){
                      		var userBkdRoomInfo = jQuery(this).find('input').val();
                      		var splitInfo = userBkdRoomInfo.split(',');
                      		roomAvailDate = splitInfo[0];//
                 			roomId = splitInfo[1];
                 			availCnt = splitInfo[2];
                 			if(availCnt = 'null'){
                 				availCnt = 0;
                 			}
                 			categoryId = splitInfo[3];
                 			usrId = splitInfo[4]; 
                 			reservationNumber = splitInfo[5];
                 			dateVal = splitInfo[6];
                 			guestUsr = splitInfo[7];
                 			
                 			var json='{"dateVal":"'+dateVal+'","roomId":"'+roomId+'","availCnt":"'+availCnt+'","categoryId":"'+categoryId+'","usreId":"'+usrId+'","reservationNumber":"'+reservationNumber+'","guestUsr":"'+guestUsr+'"}';
                 			if( usrId != 'null' || guestUsr != 'null' && reservationNumber != 'null'){
                      			$.ajax({
                     				url  : 'getUserReservedRoomDetails.htm',
                     				type : 'POST',
                     				data: {jsonData:json},
                                    success: function(data){
                                    	var response = jQuery.parseJSON(data);
                                    	
                                    	var hotelDetails = '';
                                    	hotelDetails += '<p>';
                                    	hotelDetails += ''+response.hotelName+' <br/>';
                                    	hotelDetails += ''+response.hotelAddress+','+response.city+'<br/>';
                                    	hotelDetails += ''+response.phoneNumber+'';
                                    	hotelDetails += '</p>';
                                    	$('#hotelInfoDiv').html(hotelDetails);
                                    	
                                    	var checkInOutDt = '';
                                    	checkInOutDt += '<p>';
                                    	checkInOutDt += 'CheckIn date   : '+response.checkInDt+'<br/>';
                                    	checkInOutDt += 'CheckOut date  : '+response.checkOutDt+'<br/>';
                                    	checkInOutDt += '</p>';
                                    	$('#checkInandOutTimeDiv').html(checkInOutDt);
                                    	
                                    	var userDetails = '';
                                    	userDetails += '<p style="padding-bottom:10px;" >';
                                    	userDetails += ''+response.userName+'<br/>';
                                    	userDetails += ''+response.email+'<br/>';
                                    	userDetails += ''+response.phoneNumber+'';
                                    	userDetails += '</p>';
                                    	
                                    	$('#userDetailDiv').html(userDetails);
                                    	
                                    	var notes = response.notes;
                                    	 //appending notes to texarea
                                    	 $('#noteID').append(notes);
                                    	 $('#noteID').prop('readonly', true);
                                    	 
                                    	 //showing reservation popUP.
                                    	$('#loggedInUserData').show();
                                    	document.getElementById('fade').style.display='block';
                                    }// success close.
                     			}); // ajax close.
                      			
                      		}// if close.
                      	 });// .usrBkdRroomInfo close.
                      	
                      	
                      	
                  	  jQuery(document).on('click', ".room_info", function(){
                  		//var roomInfo = this;
                  		
                  		//$(this).removeClass("processing");
	                  			var availabilityID = jQuery(this).find('input').val();
	                  			var res = availabilityID.split(',');
	                  			tdate=res[0];
	                  			roomId=res[1];
	                  			availcnt=res[2];
	                  			categoryId=res[3];
	                  			var qhtml = '<input type="hidden" value="'+tdate+','+roomId+','+availcnt+','+categoryId+'" id="valuesinquickform" name="valuesinquickform">';
	                  			$('#quickformvalues').html(qhtml);
                  				var loggedin=$('#loggedin').val();
                  				 var providerLoggedIn = $('#providerLoggedIn').val(); 
                        		
                  				
                  			if(loggedin != '' || providerLoggedIn != ''){
                  				
                        			$.ajax({
                        				url     : 'isBlackUsr.htm?roomId='+roomId,
                        				type    : 'POST',
                        				success : function(data){
                        					var response = jQuery.parseJSON(data);
                        					if(response == false ){
                        						var warning = 'your not allowed to book white room';
                        						$('.processing').removeClass("processing");
                        						$('#wrningMsgId').html(warning);
                        						$(this).removeClass("processing");
                        						$('#warningMsgDiv').show();
                        						 document.getElementById('fade').style.display='block';
                        						 //$(".book_info").removeClass('processing');//book_info
                        						 //document.getElementById('.book_info').style.display='#b5e51d';
                        						 //$(this).removeClass("processing");
                        						 
                        						 $(this).addClass("processing");
                        						// document.getElementById('.book_info').style.display='';
                        						 //booked
                        						 //$(".book_info").addClass('booked');
                        						 $("#removeProcessing").removeClass(processing);
                        						//$("#removeProcessing div").css({'background-color' : 'green', 'font-weight' : ''});
                        						
                        					}else{
                        						$.ajax({
                                                  url: "reservation.htm",
                                                  type: 'GET',
                                                  data : {date:tdate,roomId:roomId,availcnt:availcnt,categoryId:categoryId},
                                                  success: function(data){
                                                  var response  = jQuery.parseJSON(data);
                                                  var roomAvailLen = response.length;
                                                  var html = '<p>';
												  if(roomAvailLen != 0){
													  for(i in response){
		                                                  	if(i == 0){
		                                                  		html += 'Checkin Date :'+ response[i].checkedInDate+ '</br>';
		                                                  		html += '<input type="hidden" value="'+response[i].checkedInDate+'" id="checkedInvalue" name="checkedInvalue">';
		                                                  	}
		                                                  }  
													  
													     html += '<input type="hidden" value="'+categoryId+','+roomId+','+availcnt+'" id="value">';
														 html += '<select name="checkedoutDate" class="form-control" id="selectedDate"  onchange="getSelectedDayCount()">';
	             		                                 
														 html += '<option value="'+0+'">'+'Select CheckOut Date'+'</option>';
														 $(response).each(function(i,res) {
	             		  									html += '<option value="'+res.checkedOutDate+'">'+res.checkedOutDate+'</option>';                                  	
	             		          						 });
		                	                             		
                                                  }
                	                              		 
                                               	$.ajax({
                                                      url: "getHotelDetails.htm",
                                                      type: 'GET',
                                                      success: function(data){
                                                      var response  = jQuery.parseJSON(data);
                                                      var htmls = '<p>';
                                                      $(response).each(function(i,res) {
                                                      	 htmls+=res.hotelName;
                                                      	 htmls+='<br/>'+res.hotelAddress;
                                                      	 htmls+='<br/>'+res.phoneNumber;
                                                      	 	$.ajax({
                                                                  url: "getUserDetails.htm",
                                                                  type: 'GET',
                                                                  success: function(data){
                                                                  var response  = jQuery.parseJSON(data);
                                                                  var htmlls = '<p>';
                                                                  $(response).each(function(i,res) {
                                                                  	 htmlls+=res.userName;
                                                                  	 htmlls+='<br/>'+res.email;
                                                                  	 htmlls+='<br/>'+res.phoneNumber;
                                          						 	});
                                                                  htmlls+='</p>';
                                                             	 $('#userDetails').html(htmlls);
                                                                 }
                                                             });
                              						 });
                                                      htmls+='</p>';
                                                 	 $('#reservationHotelDetails').html(htmls);
                                                     }
                                                  });
                                                  html+='</select> ';
                                                  html+='</p>';
                                                  
                                                 
                                                 //showTimer(randumNum);
                                                  $('#checkedoutDates').html(html);
                                                  $('#myreservation').show();
                                                  document.getElementById('fade').style.display='block';
                                                 }
                                              });
                        						
                        					}
                        				}
                        			});
                        	   	
                        	}else{
                        		
                        		var isQuciUserLoggedIn = $('#quickUserLoggedIn').val();     
            					if( isQuciUserLoggedIn != ''){
            						$('#myreservation_options').hide();
            						$.ajax({
                                        url: "reservation.htm",
                                        type: 'GET',
                                        data : {date:tdate,roomId:roomId,availcnt:availcnt,categoryId:categoryId},
                                        success: function(data){
                                        var response  = jQuery.parseJSON(data);
                                        var roomAvailLen = response.length;
                                        var html = '<p>';
                                        
                                        if(roomAvailLen != 0){
                                        	for(i in response ){
                                            	if(i == 0){
                                            		html += 'Checkin Date :'+ response[i].checkedInDate+ '</br>';
                                            		html += '<input type="hidden" value="'+response[i].checkedInDate+'" id="checkedInvalue" name="checkedInvalue">';
                                            	}
                                            }                                
          	                             		 html += '<input type="hidden" value="'+categoryId+','+roomId+','+availcnt+'" id="value">';
          	                              		 html += '<select name="checkedoutDate" class="form-control" id="selectedDate"  onchange="getSelectedDayCount()">';
          		                                 
          	                              	 	 html += '<option value="'+0+'">'+'Select CheckOut Date'+'</option>';
          	                              		 $(response).each(function(i,res) {
          		  									html += '<option value="'+res.checkedOutDate+'">'+res.checkedOutDate+'</option>';                                  	
          		          						 });
                                        }
                                        
      		                                 
      		                                 
                                     	$.ajax({
                                            url: "getHotelDetails.htm",
                                            type: 'GET',
                                            success: function(data){
                                            var response  = jQuery.parseJSON(data);
                                            var htmls = '<p>';
                                            $(response).each(function(i,res) {
                                            	 htmls+=res.hotelName;
                                            	 htmls+='<br/>'+res.hotelAddress;
                                            	 htmls+='<br/>'+res.phoneNumber;
                                            	 	$.ajax({
                                                        url: "getUserDetails.htm",
                                                        type: 'GET',
                                                        success: function(data){
                                                        var response  = jQuery.parseJSON(data);
                                                        var htmlls = '<p>';
                                                        $(response).each(function(i,res) {
                                                        	 htmlls+=res.userName;
                                                        	 htmlls+='<br/>'+res.email;
                                                        	 htmlls+='<br/>'+res.phoneNumber;
                                						 	});
                                                        htmlls+='</p>';
                                                   	 $('#userDetails').html(htmlls);
                                                       }
                                                   });
                    						 });
                                            htmls+='</p>';
                                       	 $('#reservationHotelDetails').html(htmls);
                                           }
                                        });
                                        html+='</select> ';
                                        html+='</p>';
                                        $('#checkedoutDates').html(html);
                                        $('#myreservation').show();
                                       }
                                    });
            					}else{
            						$('#myreservation_options').show();
            						document.getElementById('fade').style.display='block';
            						
            					}//else condition close.
                        	}
                  		
                  	});// jQuery.documnt On click close.
                  	
                 
                	  $("#alertMsgPopUp").hide();
                	  var mode = $("#mode").val();
                	  if(mode != '') {
                			 $('#myprofile').show();
                			 document.getElementById('fade').style.display='block';
                	  }
                	  var guestmode = $("#guestmode").val();
                	  if(guestmode != '') {
                			 $('#myquickreservation').show();
                	  }
                	  var reservemode = $("#reservemode").val();

                	  if(reservemode != '') {
                		  var qucikuserunique = $("#qucikuserunique").val();
                          if (qucikuserunique != '') {
                         	$('#myquickreservation').show();
                                                	
                          }else{
                        	  
                        	$(this).addClass("processing");
      						 var count = 60;
      						var callsPerSecond = 1;
      						var delay = 1000;
      						function offsetLoop(i, counter, idsRemaining) {
      							while (i < counter) {
      								var secondsRemaining = (idsRemaining * delay) / 1000;
      								if (secondsRemaining > 60) {
      									var remainder = secondsRemaining % 60;
      									secondsRemaining -= remainder;            
      									var minute = (secondsRemaining) / secondsRemaining;
      								}
      								secondsRemaining - i;
      								if (typeof minute == 'undefined') {
      									minute = 0;
      								}
      								log(minute + ':' + secondsRemaining);
      								i++;
      								idsRemaining--;
      								if (i % callsPerSecond == 0) {
      									setTimeout(function () {
      										offsetLoop(i, counter, idsRemaining);
      									}, delay);
      									break;
      								}
      							}
      							if (i == counter ) {
      								
      								$("#flash").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="returnToHme.htm" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
      								$('.processing').removeClass("processing");
      								$('.button_padding').hide();
      								$('#hideTimer').hide();
      							}
      						};

      						function log(text) {
      							$('#log').html(text);
      							
      						}

      						offsetLoop(0, count, count); 

                  		   	var tdate = '';
                            	var roomId = 0;
                            	var availcnt = 0;
                            	var categoryId = 0;
                  		   var quickreservalues=$("#valuesinquickform").val();
                  		 
                  		  if(quickreservalues!='undefined'){
                  			  var res = quickreservalues.split(',');
                    			tdate=res[0];
                    			roomId=res[1];
                    			availcnt=res[2];
                    			categoryId=res[3];  
                  		  } 
                      	  
                  		  $.ajax({
                                url: "reservation.htm",
                                type: 'GET',
                                data : {date:tdate,roomId:roomId,availcnt:availcnt,categoryId:categoryId},
                                success: function(data){
                                var response  = jQuery.parseJSON(data);
                                var roomAvalLen = response.length;
                                var html = '<p>';
                                if(roomAvalLen != 0){
                                	
                                	 for(i in response ){
                                     	if(i == 0){
                                     		html += 'Checkin Date :'+ response[i].checkedInDate+ '</br>';
                                     		html += '<input type="hidden" value="'+response[i].checkedInDate+'" id="checkedInvalue" name="checkedInvalue">';
                                     	}
                                     }                                
                                    		 
                                	 		 html += '<input type="hidden" value="'+categoryId+','+roomId+','+availcnt+'" id="value">';
                                     		 html += '<select name="checkedoutDate" class="form-control" id="selectedDate"  onchange="getSelectedDayCount()">';
       	                                 
                                     		 html += '<option value="'+0+'">'+'Select CheckOut Date'+'</option>';
                                     		 $(response).each(function(i,res) {
       	  									html += '<option value="'+res.checkedOutDate+'">'+res.checkedOutDate+'</option>';                                  	
       	          						 });
                                	
                                }
                               
                               
                             	$.ajax({
                                    url: "getHotelDetails.htm",
                                    type: 'GET',
                                    success: function(data){
                                    var response  = jQuery.parseJSON(data);
                                    var htmls = '<p>';
                                    $(response).each(function(i,res) {
                                    	 htmls+=res.hotelName;
                                    	 htmls+='<br/>'+res.hotelAddress;
                                    	 htmls+='<br/>'+res.phoneNumber;
                                    	 	$.ajax({
                                                url: "getGuestUserDetails.htm",
                                                type: 'GET',
                                                success: function(data){
                                                var response  = jQuery.parseJSON(data);
                                                var htmlls = '<p>';
                                                $(response).each(function(i,res) {
                                                	 htmlls+=res.userName;
                                                	 htmlls+='<br/>'+res.email;
                                                	 htmlls+='<br/>'+res.phoneNumber;
                        						 	});
                                                htmlls+='</p>';
                                           	 $('#userDetails').html(htmlls);
                                               }
                                           });
            						 });
                                    htmls+='</p>';
                               	 $('#reservationHotelDetails').html(htmls);
                                   }
                                });
                                html+='</select> ';
                                html+='</p>';
                               
                                $('#checkedoutDates').html(html);
                                $('#myreservation').show();
                                document.getElementById('fade').style.display='block';
                               }
                            });
                  		  
                          }
                	  }
                	  var loggedin = $("#loggedin").val();
                	  var providerLoggedIn = $('#providerLoggedIn').val();
                	  
                	  $('#adminCustmrList').click(function(){
                		  document.location.href = "serviceProviderPage.htm";
                	  });
                	  
					  $('#custmrReservtns').click(function(){
						document.location.href = "serviceProviderPage.htm";
                	  });
                	 
                	  $('#adminWhiteList').click(function(){
						document.location.href = "serviceProviderPage.htm";
                	  });
                	  
                	  $('#adminBlckList').click(function(){
  						document.location.href = "serviceProviderPage.htm";
                  	  });
                  	  
                  	  $('#custmrsEmailList').click(function(){
  						document.location.href = "serviceProviderPage.htm";
                  	  });
                  	  
                  	  $('#addToWhitLstCat').click(function(){
  						document.location.href = "serviceProviderPage.htm";
                  	  });
                  	  
                  	  $('#dragAndDrop').click(function(){
                  		  document.location.href = "dragAndDropPage.htm";
                  	  });
                  	  
                  	  $('#permanentRreservation').click(function(){
	                  		document.location.href = "serviceProviderPage.htm";
                  	  });
                  	  
                  	  $('#chngeResevtnByProvider').click(function(){
	                  		document.location.href = "serviceProviderPage.htm";
                  	  });
                  	  
                	  
                	  
                	  if(providerLoggedIn != '' && loggedin == ''){
                		 
                			  $('#blackuser').hide();
                    		  $('#greenuser').show();
                		  	  $('#redUser').show();
                		  	  $('#changePageMode').show();
                		  	  $('#htlNavgtnBtn').hide();
                		 
                	  }else if(loggedin != '' && providerLoggedIn == '') {// check SPUsrLoggedIn condition also.
                		  
                			  $('#blackuser').hide();
                			  $('#redUser').hide();
                    		  $('#greenuser').show();
                    		  $('#changePageMode').hide();
                		 
                	  } else {
                		  $('#blackuser').show();
                		  $('#greenuser').hide();
                		  $('#redUser').hide();
                		  $('#changePageMode').hide();
                	  }
                	  
                	  var headerpopup = $('#headerpopup').val();
                	  if(headerpopup != ''){
                		  $('#my_registration').show();
                          $("ul li").removeClass("selected");
                		  
                	  }
                	  
                	  var adminerrors = $('#adminerrors').val();
                	  if(adminerrors != ''){
                		  $('#my_adminregistration').show();
                          $("ul li").removeClass("selected");
                		  
                	  }
                	  
                	  var history = $("#history").val();
                      if(history == 'Your action performed successfully.'){
                    	  $("#alertMsgPopUp").show();
                          $("#alertPopSpanId").empty();
                          $("#alertPopSpanIdGreen").append(history);
                      }   
                	  
                	  var isTokenValid = $("#isTokenValid").val();                   
                      if (isTokenValid !='') {                   	  
                          $("#alertPopSpanId").empty();
                          $("#alertPopSpanIdGreen").append(isTokenValid);
                          $("#alertMsgPopUp").show();
                      }    
                	                              	
                     var loginpopup = $("#loginpopup").val();
                     if (loginpopup == 'Please enter the Login details') {
                    	 $('#mylogin').show();
                    	 document.getElementById('fade').style.display='block';
                    	 
                     }
                     
                     var errorDet = $("#errorDet").val();
                     if (errorDet == 'Please enter the Login details') {
                    	 $('#myadminlogin').show();
                    	 document.getElementById('fade').style.display='block';
                    	 
                     }
                     
                     $('#forgotPopUp').click(function(){
                    	$('#mylogin').hide();
                    	$('#inputEmail3').val('');
                    	$('#forgorStatusMsg').text('');
                    	$("#invalidForgot").val('');
                    	$('#myforgot').show();
                     });
                     
                     $('#forgotAdminPopUp').click(function(){
                      	$('#myadminlogin').hide();
                      	$('#inputEmail3').val('');
                      	$('#forgorStatusMsg').text('');
                      	$("#invalidForgot").val('');
                      	$('#myforgot').show();
                       });
                     
                     var invalidForgot = $("#invalidForgot").val();
                    	if(invalidForgot != '') { 
                            $('#myforgot').show();
                     }
                  
                     
                     var invalidLogin = $("#invalidLogin").val();
                     if (invalidLogin != '') { 
                    	 $('#mylogin').show();
                    	 document.getElementById('fade').style.display='block';
                      	
                     }
                     
                     var invalidServLogin = $("#invalidServLogin").val();
                     if (invalidServLogin != '') { 
                    	 $('#emailOrUsernme').val('');
                    	 $('#adminPwd').val('');
                    	 $('#myadminlogin').show();
                    	 document.getElementById('fade').style.display='block';
                      	
                     }
                     
                     var accountinactivepop = $("#accountinactivepop").val();
                     if (accountinactivepop != '') { 
                    	 $('#mylogin').show();
                    	 document.getElementById('fade').style.display='block';
                      	 $("#alertPopSpanId").empty();
                      	 $("#alertPopSpanIdRed").append(accountinactivepop);
                    	 $("#alertMsgPopUp").show();
                     }
                     
                     var servAcnt_inactive = $("#servAcnt_inactive").val();
                     if (servAcnt_inactive != '') { 
                    	 $('#emailOrUsernme').val('');
                    	 $('#adminPwd').val('');
                    	 $('#myadminlogin').show();
                    	 document.getElementById('fade').style.display='block';
                      	 $("#alertPopSpanId").empty();
                      	 $("#alertPopSpanIdRed").append(servAcnt_inactive);
                    	 $("#alertMsgPopUp").show();
                     }
                     
                     var isTokenInValid = $("#isTokenInValid").val();
                     if (isTokenInValid !='') {	
	                  	$("#alertMsgPopUp").show();
	                    $("#alertPopSpanId").empty();
	                    $("#alertPopSpanIdRed").append(isTokenInValid+"<br>"
	                    		+"<a href=\"resendMail.htm?token=${token}\">Resend Activation Link </a>");
	                   }
                     
                     var signUp = $("#signUp").val();
                     if (signUp != '') {	                   	
 	                    $("#regSuccessPopupGreen").empty();
 	                    $("#regSuccessPopupGreen").append(signUp);
 	                   	$("#regSuccessPopup").show();                         
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
                    	document.getElementById('fade').style.display='block';             	
                     }
                     
                     var mailsent = $('#mailsent').val();
                     if(mailsent == 'User Activation Link  Is Sent To Your Mail') {
                    	$("#alertMsgPopUp").show();
   	                    $("#alertPopSpanId").empty();
   	                    $("#alertPopSpanIdGreen").append(mailsent);
                     }
                                          
                     var updateUser = $("#updateUser").val();
                     if (updateUser != '') {                    	 
                    	 $('#myprofile').show();
                    	 document.getElementById('fade').style.display='block';
                    	 var hideMessage = function() {
                    		 $('#showUpdate').hide();
                    	 };
                    	 setTimeout(hideMessage,2000);
                     }
                                                 
                     var updateFailure = $("#updateFailure").val();
                     if (updateFailure != '') {
                    	 $('#myprofile').show();
                    	 document.getElementById('fade').style.display='block';
                    	 $("#alertMsgPopUp").show();
    	                 $("#alertPopSpanId").empty();
    	                 $("#alertPopSpanIdRed").append(updateFailure);
                     }
                     
                     var logoutsuccess = $("#logoutsuccess").val();
                     if (logoutsuccess != '') {                    	 
                    	 $("#alertMsgPopUp").show();
    	                 $("#alertPopSpanId").empty();
    	                 $("#alertPopSpanIdGreen").append(logoutsuccess);
                     }
                     
                     var mode = $("#mode").val();
                     if (mode != '') {
                         $('#myprofile').show();
                         document.getElementById('fade').style.display='block';
                     }
                     
                     var hotel_details = $("#testUser").val();
                     if (hotel_details != '') {
                         $('#hotel_details').show();
                         $("ul li").removeClass("selected");
                     }
                     
                     var reservedate = $("#testDate").val();
                     if (reservedate != '') {
                         $('#reservedate').show();
                         $("ul li").removeClass("selected");
                     }
                    
                     //getting selected date values.
                     jQuery(document).on('click', ".hiddenSpan", function(){
                    	
                    	var selectedDate = jQuery(this).find('input').val();
                    	 selctdVal = selectedDate;
						 var userId ='';
							$.ajax({
								url : 'getGreenUserDetails.htm',
								type : 'POST',
								success: function(data){
									var userDetails = jQuery.parseJSON(data);
									userId = userDetails.userId;
								}
							});
						var catVal = $('#category').val();
						var roomNo = $('#roomNo').val();
						var noOfDays = $('#noOfDays').val();
						var count = 0;
						var blckDate = '';
						
						if(noOfDays == ''){
							noOfDays = 0;
						}
						
							$('#carouselDiv').empty();
							$.ajax({
								url   :'getSelectedDateAvailRooms.htm',
								type  :'POST',
								data  :{date:selectedDate,selectedCategory:catVal,noOfDays:noOfDays},
								success:function(data){
								var response = jQuery.parseJSON(data);
								var length = response.length;
								var count = 0;
								var html = '';
	                           	html += '<div class="item active">';
	                           	html += ' <div class="carousel-caption">';
	                           	html += '<div class="ajax_loader"></div>';
								for(i in response){
									if(userId == response[i].userId){		
		 	                           	html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	 	                           		html += '<div class="cell_room booked mybook_room ">';
	 	                           		html += '<span href="javascript:void(0)" class="user_bkd_room_info usrBkdRroomInfo" >';
	 	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
	 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	 	                           		html += ''+response[i].categoryName +'<br/>';
	 	                           		html += ''+response[i].availcnt;
	 	                           		html += '</span>';
	 	                           		html += '</div>';
	 	                           		html += '</div>';
		                           	}else if('green' == response[i].colourCode &&  
	      	                           			response[i].availcnt >= noOfDays && response[i].availcnt != 0){
			                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
		 	                           		html += '<div class="cell_room booked" id="removeProcessing">';
		 	                           		html += '<a href="javascript:void(0)" class="room_info" >';
		 	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+'"></input>';
		 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
		 	                           		html += ''+response[i].categoryName +'<br/>';
		 	                           		html += ''+response[i].availcnt;
		 	                           		html += '</a>';
		 	                           		html += '</div>';
		 	                           		html += '</div>';
		                           		}else{
		 	                           		var val = 'X';
		 	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
		 	                           		html += '<div class="cell_room reserved">';
		 	                           		html += '<p class="room_info red" >';
		 	                           		html += '<br/>';
		 	                           		html += ''+val+'';
		 	                             	html += '<br/>';
		 	                             	html += '</p>';
		 	                           		html += '</div>';
		 	                           		html += '</div>';
		 	                           		
		 	                           		count = count + 1;
			 	                           	 if(count == length){
			 	                           		blckDate = response[i].roomAvailDate;
		  	                           		 } 
		                           		}
									}
									html += '</div>';
		                       		html += '</div>';
		                       		$('#roomInfoDiv').empty();
		                       		$('#carouselDiv').append(html);
		                       		$('#carouselDivPrimary').hide();
		                       		$('#carouselDiv').show();
			                        $('#currentDate span').text(selectedDate);
			                        var dateVal = selctdVal.slice(0,2);
		                       		fullDate = fullDate.replace('/','-');
	                          		fullDate = fullDate.replace('/','-');
	                          		var currDate = $('#currentDate span').text();
		                       		if(catVal == 0){
		                       			showSearchDatesListOnDatesBar(selctdVal,selectedDate,blckDate);
		                       		}else{
		                       			showSearchDatesListOnDatesBar(selctdVal,selectedDate,blckDate);
		                       			//showDatesOnDatesBar(catVal,fullDate,selctdVal,blckDate);
		                       		}
	                          		
								}
							});// ajax call close.
							
							
					});// jQuery. hidden span close.
                    
                     $('#currentDate').dblclick(function(){
                    	 $('#noOfDays').val('');
                    	 var noOfDays = $('#noOfDays').val();
                    	 if(noOfDays == ''){
                    		 noOfDays = 0;
                    	 }
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
                           var fullDate =  Day + '/' + Month + '/' + Year;
                          
                         	//get loggedIn user dates and compared with dates list, if both dates are equal shows 
                           // that dates should be shows in blue color.
                           var userBkdRecrds = ''; 
                           $.ajax({
      	                    	 url    : 'getLoggedInUserReservedDates.htm',
      	                    	 type   : 'POST',
      	                    	 success : function(data){
      	                    		userBkdRecrds =  jQuery.parseJSON(data);
                          	 }//success function close.
                            });//ajax function close.
                            
                            
                         $.ajax({
                        	url   : 'getDatesList.htm',
                         	type  : 'POST',
                         	success : function(data){
                         		var response = result = jQuery.parseJSON(data);
                         		fullDate = fullDate.replace('/','-');
                         		fullDate = fullDate.replace('/','-');
                            	 var html = '';
                     			
                     			 html += '<div class="piece getDateVal" >';
                     			 html += '<div class="small_square" >';

                     			var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec']; 

                     			var mnthNme = '';
                     			
                     			  for(j in response){
                     				
                              		 if(j == 0){
                              			 var monthNum = response[j].selecteDt.slice(3,5);
                              			 monthNum = (monthNum-1) ;
                              			 jQuery.each( monthArray, function( i, mnthName ) {
                                         	 	if(monthNum == i){
                                         	 		html += '<span class="month_title" style="float: left; margin-top: -22px">'+mnthName+'</span>';
                                         	 	}
                                         	});
                              			 }
                              		}  // for close.
                              		
                     				for(i in response){
                         				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2) &&
                         						fullDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
                         					/* if(i == 0){
                                     			 var monthNum = response[i].selecteDt.slice(3,5);
                                     			 monthNum = (monthNum-1) ;
                                     			 jQuery.each( monthArray, function( i, mnthName ) {
                                                	 	if(monthNum == i){
                                                	 		html += '<span class="month_titile">'+mnthName+'</span>';
                                                	 	}
                                                	});
                                     			 } */
                         					
                         					html += '<span class="cal_date current_date hiddenSpan">';
                         					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                         					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                         				}else{  	
                         				  var blueDtval = '';
                         				 
                         				  if(response[i].selecteDt.slice(0,2) == 01 || 
                             					 response[i].selecteDt.slice(0,2) == 15 &&
                               					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                     						var monthNum = response[i].selecteDt.slice(3,5);
                                 			 monthNum = (monthNum-1) ;
                                 			 jQuery.each( monthArray, function( i, mnthName ) {
                                            	 	if(monthNum == i){
                                            	 		html += '<span class="month_title">'+mnthName+'</span>';
                                            	 	}
                                            	});
                     					}
                       					  for(var record in userBkdRecrds){	
                       						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
                       								&& userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
                       							html += '<span class="cal_date loggedInUser hiddenSpan">';
                               					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                               					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                               					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
                       						}
                       					} // for (userBkdRecrds)close.
                       					
                       					if(blueDtval != response[i].selecteDt.slice(0,2)){
                       						
                       						
                       						if(response[i].booked == response[i].noOFRooms){
                       							
                       							if(response[i].selecteDt.slice(0,2) == 01 || 
                                   					 response[i].selecteDt.slice(0,2) == 15 &&
                                     					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                           						var monthNum = response[i].selecteDt.slice(3,5);
                                       			 monthNum = (monthNum-1) ;
                                       			 jQuery.each( monthArray, function( i, mnthName ) {
                                                  	 	if(monthNum == i){
                                                  	 		html += '<span class="month_title">'+mnthName+'</span>';
                                                  	 	}
                                                  	});
                           					}
                           						html += '<span class="cal_date hiddenSpan">';
                           						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                           						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                           					}else  {
                           						if(response[i].selecteDt.slice(0,2) == 01 || 
                                   					 response[i].selecteDt.slice(0,2) == 15 &&
                                     					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                           						var monthNum = response[i].selecteDt.slice(3,5);
                                       			 monthNum = (monthNum-1) ;
                                       			 jQuery.each( monthArray, function( i, mnthName ) {
                                                  	 	if(monthNum == i){
                                                  	 		html += '<span class="month_title">'+mnthName+'</span>';
                                                  	 	}
                                                  	});
                           					}
                           						html += '<span class="cal_date green hiddenSpan">';
                           						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                           						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                           					}
                       					}
                         				}
                     				}
                     			html += '</div>';
                     			html += '</div>';
                     			$('#secndryDatesList').empty();
                     			$('#prmryDatesList').empty();
                     			$('#prmryDatesList').append(html);
                     			$('#prmryDatesList').show();
                         	}
                         });//ajax function close.
                         var userId ='';
	                        $.ajax({
									url : 'getGreenUserDetails.htm',
									type : 'POST',
									success: function(data){
										var userDetails = jQuery.parseJSON(data);
										userId = userDetails.userId;
									}
							}); 
                    	 $.ajax({
                             url: 'dbclk.htm?noOfDays='+noOfDays,
                             type: 'POST',
                             success: function(data){
                            var response =  jQuery.parseJSON(data);
                           	var html = '';
                           	html += '<div class="item active">';
                           	html += ' <div class="carousel-caption">';
	                           	for(i in response){
	                           		if(userId == response[i].userId){		
		 	                           	html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	 	                           		html += '<div class="cell_room booked mybook_room ">';
	 	                           		html += '<span href="javascript:void(0)" class="user_bkd_room_info usrBkdRroomInfo" >';
	 	                              	html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
	 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	 	                           		html += ''+response[i].categoryName +'<br/>';
	 	                           		html += ''+response[i].availcnt;
	 	                           		html += '</span>';
	 	                           		html += '</div>';
	 	                           		html += '</div>';
		                           	}else if('green' == response[i].colourCode && 
     	                           			response[i].availcnt != 0){
	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room booked" id="removeProcessing">';
	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	     	                           		html += ''+response[i].categoryName +'<br/>';
	     	                           		html += ''+response[i].availcnt;
	     	                           		html += '</a>';
	     	                           		html += '</div>';
	     	                           		html += '</div>';
     	                           		}else{
     	                           			var val = 'X';
	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room reserved">';
	     	                           		html += '<p class="room_info red" >';
	     	                           		html += '<br/>';
	     	                           		html += ''+val+'';
	     	                             	html += '<br/>';
	     	                             	html += '</p>';
	     	                           		html += '</div>';
	     	                           		html += '</div>';
     	                           		}
	                           	}
                           	html += '</div>';
                       		html += '</div>';
                       		$('#carouselDivPrimary').hide();
                       		$('#roomInfoDiv').empty();
                       		$('#carouselDiv').empty();
                       		$('#carouselDiv').append(html);
                       		$('#carouselDiv').show();
                       		
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
                             var fullDate =  Day + '/' + Month + '/' + Year;
                            
  	   	               	   $('#currentDateVal').empty();
  	                       $('#currentDateVal').append(fullDate);
  	                       $('#currentDateVal').show(); 
                        }//success function close.
                    });//ajax call close.
                       
                         $.ajax({
                             url: 'getCategoryList.htm',
                             type: 'POST',
                             success: function(data){
                            	 $('#category').empty();
                            	 $('#roomNo').empty();
                            	var response = result = jQuery.parseJSON(data);
                                var selectCat = 0;
                               	  $('#category').append("<option value="+ selectCat +">"+"Select Category"+"</option>");
                                      for(i in response){
                                   	   $('#category').append("<option value="+ response[i].categoryId +">"+response[i].categoryName+"</option>");
                                      } 
                                      $('#category').show();
                             }
                         }); 
                    
                    
                     });// db click function close.
                     
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
                      var fullDate =  Day + '/' + Month + '/' + Year;
  	               	  $('#currentDateVal').empty();
                      $('#currentDateVal').append(fullDate);
                      $('#currentDateVal').show(); 
                     
                    // get list of categories and shows it on page, while page loading.
                     $.ajax({
                         url: 'getCategoryList.htm',
                         type: 'POST',
                         success: function(data){
                        	var response = result = jQuery.parseJSON(data);
                            var selectCat = 0;
                           	  $('#category').append("<option value="+ selectCat +">"+"Select Category"+"</option>");
                                  for(i in response){
                               	   $('#category').append("<option value="+ response[i].categoryId +">"+response[i].categoryName+"</option>");
                                  } 
                                  $('#category').show();
                         }
                     }); 
                     
                    
                    //get noOfDays val. If value is '' then assing values = 0.
                    var noOfDays = $('#noOfDays').val();
                     if(noOfDays == ' '){
                    	 noOfDays = 0;
                     }
                     
                     //get loggedIn user dates and compared with dates list, if both dates are equal shows 
                     // that dates should be shows in blue color on page loading.
                     var userBkdRecrds = ''; 
                     $.ajax({
	                    	 url    : 'getLoggedInUserReservedDates.htm',
	                    	 type   : 'POST',
	                    	 success : function(data){
	                    		userBkdRecrds =  jQuery.parseJSON(data);
                    	 
	                    	 }//success function close.
                      });//ajax function close.
                 	
                 	
                
                     $('.previousBtn').click(function(){
                    	 clickCount = 0;
                    	 $('.rightArrow').show();
                     });
                     
                    
                     //get rooms list while page loading.
                     var count = '';
                     var blckDate = '';
                     var currDate = $('#currentDate span').text();
                     var selctdVal = currDate;
                     var dateVal = selctdVal.slice(0,2); 
                    
                     var userId ='';
						$.ajax({
							url : 'getGreenUserDetails.htm',
							type : 'POST',
							success: function(data){
								var userDetails = jQuery.parseJSON(data);
								userId = userDetails.userId;
							}
						});
                     
                      $('#carouselDiv').empty();
                    	 $.ajax({
                             url: 'getRoomsAvailOnCurrentDate.htm?noOfDays='+noOfDays,
                             type: 'POST',
                             success: function(data){
                            var response =  jQuery.parseJSON(data);
                          	var html = '';
                           	html += '<div class="item active">';
                           	html += ' <div class="carousel-caption">';
	                           	for(i in response){
	                           		if(userId == response[i].userId){		
		 	                           	html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	 	                           		html += '<div class="cell_room booked mybook_room ">';
	 	                           		html += '<span href="javascript:void(0)" class="user_bkd_room_info usrBkdRroomInfo" >';
	 	                           	html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
	 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	 	                           		html += ''+response[i].categoryName +'<br/>';
	 	                           		html += ''+response[i].availcnt;
	 	                           		html += '</span>';
	 	                           		html += '</div>';
	 	                           		html += '</div>';
		                           	}else if('green' == response[i].colourCode && 
     	                           			response[i].availcnt != 0){
	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room booked" id="removeProcessing">';
	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+'"></input>';
	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	     	                           		html += ''+response[i].categoryName +'<br/>';
	     	                           		html += ''+response[i].availcnt;
	     	                           		html += '</a>';
	     	                           		html += '</div>';
	     	                           		html += '</div>';
     	                           		}else{
     	                           			var val = 'X';
	     	                           		html += '<div class="col-xs-3 col-xs-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room reserved">';
	     	                           		html += '<p class="room_info red" >';
	     	                           		html += '<br/>';
	     	                           		html += ''+val+'';
	     	                             	html += '<br/>';
	     	                             	html += '</p>';
	     	                           		html += '</div>';
	     	                           		html += '</div>';
	     	                           	count = count + 1;
     	                           		if(count == length){
     	                           			blckDate = response[i].roomAvailDate;
	     	                           		}
     	                           		}
	                           	}
                           	html += '</div>';
                       		html += '</div>';
                       		$('#roomInfoDiv').empty();
                       		$('#carouselDivPrimary').hide();
                       		$('#carouselDiv').append(html);
                       		$('#carouselDiv').show(); 
                       		
                       		var dateVal = selctdVal.slice(0,2);
                       		fullDate = fullDate.replace('/','-');
                      		fullDate = fullDate.replace('/','-');
                         }
                     });//ajax function close.
                     
                     //get dates list and show it on dates bar on page loading.
                     
                      $('#secndryDatesList').empty();
                      $('#prmryDatesList').empty();
                      $.ajax({
                     	url   : 'getDatesList.htm',
                      	type  : 'POST',
                      	success : function(data){
                      		var response =  jQuery.parseJSON(data);
                      		arrLength = response.length;
                      		var currDate = $('#currentDate span').text();
                         	currDate = currDate.replace('/','-');
                         	currDate = currDate.replace('/','-');
                         	 
                         	var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
                         	 var html = '';
                  			
                  			 html += '<div class="piece getDateVal" >';
                  			 html += '<div class="small_square" >';
                  			
                  			var mnthNme = '';
                  			var count = 0;
              				var pxls = 0;
                  			  for(j in response){
                  				
                           		 if(j == 0){
                           			 var monthNum = response[j].selecteDt.slice(3,5);
                           			 monthNum = (monthNum-1) ;
                           			 jQuery.each( monthArray, function( i, mnthName ) {
                                      	 	if(monthNum == i){
                                      	 		//mnthNme += ' <span>'+mnthName+'</span>';
                                      	 		html += '<span class="month_title">'+mnthName+'</span>';
                                      	 	}
                                      	});
                           			 }
                           		} // for close.
                           		
                  				for(i in response){
                      				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2)
                      						&& currDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
                      				
                      					html += '<span class="cal_date current_date hiddenSpan">';
                      					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                      					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                      				}else{
                      					if(response[i].selecteDt.slice(0,2) == 01 || 
                              					 response[i].selecteDt.slice(0,2) == 15 &&
                                					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                      						var monthNum = response[i].selecteDt.slice(3,5);
                                  			 monthNum = (monthNum-1) ;
                                  			 jQuery.each( monthArray, function( i, mnthName ) {
                                             	 	if(monthNum == i){
                                             	 		html += '<span class="month_title">'+mnthName+'</span>';
                                             	 	}
                                             	});
                      					}
                      					
                      					var blueDtval = '';
                      					  for(var record in userBkdRecrds){	
                      						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
                      								 && userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
                      							html += '<span class="cal_date loggedInUser hiddenSpan">';
                              					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                              					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                              					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
                      						}
                      					} // for (userBkdRecrds)close.
                      					
                      					if(blueDtval != response[i].selecteDt.slice(0,2)){
                      						
                      						if(response[i].selecteDt.slice(0,2) == 01 || 
                                 					 response[i].selecteDt.slice(0,2) == 15 &&
                                   					response[i].selecteDt.slice(3,5) >= currDate.slice(3,5) ){
                         						var monthNum = response[i].selecteDt.slice(3,5);
                                     			 monthNum = (monthNum-1) ;
                                     			 jQuery.each( monthArray, function( i, mnthName ) {
                                                	 	if(monthNum == i){
                                                	 		html += '<span class="month_title">'+mnthName+'</span>';
                                                	 	}
                                                	});
                         					}
                      						
                      						if(response[i].booked == response[i].noOFRooms){
                          						html += '<span class="cal_date hiddenSpan">';
                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                          					}else  {
                          						html += '<span class="cal_date green hiddenSpan">';
                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
                          					}
                      					}
                      					
                      				}// else close.
                  				}
                  			html += '</div>';
                  			html += '</div>';
                  			
                  			$('#secndryDatesList').empty();
                  			$('#prmryDatesList').empty();
                  			$('#prmryDatesList').append(html);
                  			$('#prmryDatesList').show();
                  			
                  			
                  			var fullOn = $.session.get('fullOn');
                  			
                  			if(fullOn != 'false'){
                				$('#fs').addClass("full_on");
                				$(".dummyfull").addClass('fullwidth');
                				$('#fs').attr("src","images/fs-min.png");
                				$(".col-xs-3-fixed").css('width','14.2%');
                  			}
                  			
                  			
                      	}
                      });//ajax function close. 
                      
                      
                     $('#cancelReservation').click(function(){
                    	document.location.href ='returnToHme.htm'; 
                     });
                   
                     //cancel reservation.
                     jQuery(document).on('click', ".bookingID", function(){
               			var bookingID = jQuery(this).find('input').val();
               			 
               			$.ajax({
               				url   : 'cancelReservation.htm?bookingID='+bookingID,
               				type  : 'POST',
               				success : function(data){
               					bookingHistry();
               					$('#statusInformtn').html(data);
    				 			$('#statusInformtn').show().fadeOut(3000);
               				}
               			}); //ajax function close.
               			
                     });
                     
                     jQuery(document).on('click', ".arrivedResrvtn", function(){
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
                     
                     jQuery(document).on('click', ".notVisitedReservtn", function(){
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
						
						 $('#myreservation_details').hide();
						 $('#deleteReservtn_popUp').show();
						 document.getElementById('fade').style.display='block';
                    	 
                     });
                     
                     //dltReservtn
                     jQuery(document).on('click', "#dltReservtn", function(){
                    	 var deleteRcrdId = jQuery(this).find('input').val();
	                    	
                    	 $.ajax({
	         				url   : 'deleteReservation.htm?deleteRcrdId='+deleteRcrdId,
	         				type  : 'POST',
	         				success : function(data){
	         				 var statusInformtn = data;
	         				 bookingHistry();
	         				$('#statusInformtn').html(statusInformtn);
					 			$('#statusInformtn').show().fadeOut(3000);
	         				}
	         			});   //ajax function close.
                     });
                     
                     //delete reservation. 
                     jQuery(document).on('click', ".deleteRecrdID", function(){
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
                            
                 });//doucument.ready.close 
                 
                var total_left=0;
             	var click_count=0;
             	var scroll_limit=32;
             	var total_scroll=0;
             	$(document).on('click','.carousel-control.right',function()
             	{ 
             	  if(total_scroll >= arrLength){
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
             	$(document).on('click','.carousel-control.left',function()
             	{
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
                 
             </script>
	   </head>
	   <!-- ---------------------------------------------Div tags for custom PopUps --------------------------------->
<div id="alertMsgPopUp" class="white_content"
	style="display: none;z-index:999; width: 467px; margin: 0px -98px 0px;">
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
<div id="alertMsgPopUp2" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Alert Message</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('alertMsgPopUp').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>

	<!-- popup for showing avalable hotels  -->
	<div style="padding: 20px 100px;">
		<span id="alertPopSpanIdRed" style="color: red; font-weight: bold;"></span>
		<span id="alertPopSpanIdGreen"
			style="color: green; font-weight: bold;"></span>
		<div id="hotelName">
			<font size="3" color="green">Please Select Hotel</font></br> <select
				name="pickHotel" id="pickHotel">
				<option value="Hotel Alpha">Hotel Alpha</option>
				<option value="Hotel Alpha">Hotel Alpha</option>
				<option value="Hotel Taj Krishna">Hotel Taj</option>
			</select>

		</div>
	</div>
</div>
<div id="alertMsgPopUp3" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Alert Message</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="closeWindow()"> <!-- 	onclick="document.getElementById('alertMsgPopUp').style.display='none';document.getElementById('fade').style.display='none'"> -->
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

		<font size="3" color="green">email sharing is successfully stopped</font>

	</div>
</div>
<div id="regSuccessPopup" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Registration Success</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('regSuccessPopup').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="regSuccessPopupRed" style="color: red; font-weight: bold;"></span>
		<span id="regSuccessPopupGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
<div id="tokenValidPopup" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Account activated!</h3>
	<a href="javascript:void(0)" class="close_popup"
		onclick="document.getElementById('tokenValidPopup').style.display='none';document.getElementById('fade').style.display='none'">
		<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
	</a>
	<div style="padding: 20px 100px;">
		<span id="tokenValidPopupRed" style="color: red; font-weight: bold;"></span>
		<span id="tokenValidPopupGreen"
			style="color: green; font-weight: bold;"></span>
	</div>
</div>
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



<!-- ---------------------------------------------End of Custom Div tags --------------------------------->
	   <body style="margin:20px 0px;">
	<input type="hidden" value="${guestmode}" id="guestmode"> 
	<input type="hidden" value="${reservemode }" id="reservemode"> 
		<input type="hidden" id="history" value="${succuessVal}">
		<input type="hidden" value="${updateFailure}" id="updateFailure">
		<input type="hidden" value="${isTokenValid}" id="isTokenValid">
		<input type="hidden" value="${isTokenInValid}" id="isTokenInValid">
		<input type="hidden" value="${logoutsuccess}" id="logoutsuccess">
		<input type="hidden" value="${account_inactive}"
			id="accountinactivepop">
		<input type="hidden" value="${servAcnt_inactive}"
			id="servAcnt_inactive">
		<input type="hidden" value="${loginsuccess}" id="loginsuccess">
		<input type="hidden" value="${signUp}" id="signUp">
		<input type="hidden" value="${admin_not_unique}" id="adminexistpopup">
		<input type="hidden" value="${admail_not_unique}" id="admailexistpopup">
		<input type="hidden" value="${user_not_unique}" id="userexistpopup">
		<input type="hidden" value="${email_not_unique}" id="emailexistpopup">
		<input type="hidden" value="${pemail_not_unique}" id="pemailexistpopup">
		<input type="hidden" value="${mail_sent}" id="mailsent"></input>
		<input type="hidden" value="${sessionScope.loggedIn}" id="loggedin"></input>
		<input type="hidden" value="${sessionScope.providerLoggedIn}" id="providerLoggedIn"></input>
		<input type="hidden" value="${loginhide}" id="loginhide">
		 <input type="hidden" value="${invalidLogin}" id="invalidLogin">
		 <input type="hidden" value="${invalidServLogin}" id="invalidServLogin">
		<input type="hidden" value="${loginDetails}" id="logindetails">
<!-- Main COntents -->
<div class="col-xs-12 col-lg-12">
	<!-- widget starts -->
	<div class="col-xs-7 col-xs-9 col-xs-12 dummyfull">
		<div class="widget_wrapper">
		<!-------------------------------- MODAL Login --------------------------------------------->
			<div id="mylogin" class="white_content">
			<input type="hidden" value="${tokenerror}" id="loginpopup">
			<h3>Login Form</h3>
			<a href ="returnToHme.htm" class="close_popup">
			<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<span style="color: red; padding-left: 110px; font-weight: bold;">${invalidLogin}</span>
			<div style="padding: 20px 100px;">
						<spring:form action="login.htm" commandName="userLogin"
							id="myForm" class="form-horizontal" method="post">
							<%-- <spring:form class="form-horizontal"> --%>
							<div class="form-group">
								<div class="col-sm-12">
									<spring:input path="email" class="form-control"
										id="hideEmail" placeholder="Email or username"></spring:input>
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
             You have forgotten your password, 
                <a href="javascript:void(0)" id="forgotPopUp">click here</a>
      
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
								<!-- <li>-	Hinweis bei Buchungüberlappungen</li> -->
							</ul>
					</div>
			</div>
			<div id="fade" class="black_overlay"></div>
		<!-- ------------------------------------   END   ---------------------------------------------->
		
		
		<!-------------------------------- MODAL Admin Login --------------------------------------------->
			<div id="myadminlogin" class="white_content">
			<input type="hidden" value="${errorDet}" id="errorDet">
			<h3>Admin Login</h3>
			<a href ="returnToHme.htm" class="close_popup">
			<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<span style="color: red; padding-left: 110px; font-weight: bold;">${invalidServLogin}</span>
			<div style="padding: 20px 100px;">
						<spring:form action="servicelogin.htm" commandName="adminLogin"
							id="myForm" class="form-horizontal" method="post">
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
             You have forgotten your password, 
                <a href="javascript:void(0)" id="forgotAdminPopUp">click here</a>
      
                </p>
					<div style="padding:0px 30px 15px 30px;">
					<b>Not yet registered</b>
						<div class="checkbox">
							<a href = "javascript:void(0)" onclick = "document.getElementById('myadminlogin').style.display='none';document.getElementById('my_adminregistration').style.display='block';document.getElementById('fade').style.display='block'">
							<span class="glyphicon glyphicon-expand" aria-hidden="true"></span>  Yes, I would like to register</a>
						</div>
							<ul class="register_note">
								<li>-	Einmalige Registrierung für alle angeschlossen Anbieter</li>
								<li>-	Übersciht aller vergangen und zukünftigen Reservierungen </li>
								<li>-	Storno via one click</li>
								<li>-	Sie entscheiden welcher Anieter Ihre E-mail Addresse Sehen kann</li>
								<!-- <li>-	Hinweis bei Buchungüberlappungen</li> -->
							</ul>
					</div>
			</div>
			<div id="fade" class="black_overlay"></div>
		<!-- ------------------------------------   END of Admin Login  ---------------------------------------------->
		
		
		 <!-------------------------------- MODAL Login --------------------------------------------->
    <input type="hidden" value="${loginhide}" id="loginhide"> <input
     type="hidden" value="${invalidForgot}" id="invalidForgot">
    <div id="myforgot" class="white_content">
     

     <%-- <span style="color: red;padding-left: 110px;font-weight: bold;">${invalidLogin}</span> --%>

     <h3>Forgot Password</h3>
     <a href="javascript:void(0)" class="close_popup"
      onclick="document.getElementById('myforgot').style.display='none';document.getElementById('fade').style.display='none'"><span
      class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
     <span style="color: red; padding-left: 110px; font-weight: bold;" id="forgorStatusMsg">${invalidForgot}</span>
     <div style="padding: 20px 100px;">
      <spring:form action="forgotpass.htm" commandName="userLogin"
       id="myForm" class="form-horizontal" method="post">
       
       <div class="form-group">
        <div class="col-sm-12">
         <spring:input path="email" class="form-control"
          id="inputEmail3" placeholder="Email(or)Username" onchange="hideemail()"></spring:input>
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
	
	<!-- ================================= WARNING MESSAGE ============================================== -->
<!-- show warning message -->
		<div id="warningMsgDiv" class="white_content "
			style="display: none; width: 467px; margin: margin: 10px 40px 0px;">
			<!-- <div id="fade" class="black_overlay" style="display: block;"></div> -->
			<h3 align="center">Warning Message</h3>
			<a href="javascript:void(0)" class="close_popup"
				onclick="document.getElementById('warningMsgDiv').style.display='none';document.getElementById('fade').style.display='none'">
				<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
			</a>
			<div style="padding: 20px 100px;">
				<span id="wrningMsgId" style="color: red; font-weight: bold;"></span>
				<span id="wrningMsgId"
					style="color: green; font-weight: bold;"></span>
			</div>
		</div>
		<!-- <div id="fade" class="black_overlay" style="display: block;"></div>  -->
	<!-- ================================== WARNING MESSAGE END =============================================== -->
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
						<spring:form action="register.htm" id="regform"
							class="form-horizontal" method="post" commandName="registerUser">

							<!-- 	<form class="form-horizontal" id="regform"> -->
							<div class="form_scroller">
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Language</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="language"
											name="language" id="language" style="color:#000;"
											items="${languages}">
											
										</spring:select>
										<spring:errors style="color: red" path="language"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Title</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="title" name="title"
											id="title" items="${title}">

										</spring:select>
										<spring:errors style="color:red" path="title"></spring:errors>
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
										<spring:input path="dob" class="form-control"
											name="dob" placeholder="dd/mm/yyyy"
											onchange="hidedateofbirth()" id="datepick"></spring:input>

										<spring:errors style="color: red" path="dob"
											id="dateOfBirth"></spring:errors>
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
											showPassword="true" name="pwd" placeholder="Password"
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
								
								<spring:hidden path="isSP" value="N" />
								
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
						klicken auf Ã¢â¬Å¡ Registrieung abschlieÃÅ¸enÃ¢â¬Ë stimmen sie den
						Nutzungbedinungen und der DatenschutzerklÃÂ¤rung von world of
						Reservation zu</p>
				</div>
			<div id="fade" class="black_overlay"></div>
			<!-- /End registration modal -->
			
			
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
						<spring:form action="adminregister.htm" id="regform"
							class="form-horizontal" method="post" commandName="registerAdmin">

							<!-- 	<form class="form-horizontal" id="regform"> -->
							<div class="form_scroller">
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Language</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="language"
											name="language" id="language" style="color:#000;"
											items="${languages}">
											<!--  <option value="Select Language">Select Language</option>
                                                                <option value="English">English</option>
                                                                <option value="Germany">Germany</option>
                                                                <option value="Deutch">Deutch</option> -->
										</spring:select>
										<spring:errors style="color: red" path="language"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Title</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="title" name="title"
											id="title" items="${title}">

										</spring:select>
										<spring:errors style="color:red" path="title"></spring:errors>
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
										<spring:input path="dob" class="form-control"
											name="dob" placeholder="dd/mm/yyyy"
											onchange="hidedateofbirth()" id="datepickadmin"></spring:input>

										<spring:errors style="color: red" path="dob"
											id="dateOfBirth"></spring:errors>
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
											showPassword="true" name="pwd" placeholder="Password"
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
						klicken auf Ã¢â¬Å¡ Registrieung abschlieÃÅ¸enÃ¢â¬Ë stimmen sie den
						Nutzungbedinungen und der DatenschutzerklÃÂ¤rung von world of
						Reservation zu</p>
				</div>
			<div id="fade" class="black_overlay"></div>
			<!-- /End of Admin registration modal -->
			
			
			
			<!-- Contact data Modal-->
			<!-------------------------------- MYCONTACT DATA MODAL --------------------------------------------->
			<input type="hidden" value="${isQuickUserLogged}" id="quickUserLoggedIn"/>
		<input type="hidden" value="${qucikuserunique}" id="qucikuserunique"/>
			<div id="myquickreservation" class="white_content">
					<h3>Quick reservation</h3>
					<span style="color: red;margin: 0px 130px 0px;">${qucikuserunique}</span>
					<h4 class="center form-subtitle">Please Fill Up the Below
						Forms</h4>
					<a href="returnToHme.htm" class="close_popup"
						onclick=""><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						
						<spring:form action="guestUserReservation.htm" commandName="quickReservation" class="form-horizontal" method="POST">
							
						   <div id="statusMsgDiv" align="center" style="color: red;"><span id="statusMsg" 
								style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
							</div>
							<div id="successStatusMsgDiv" align="center" style="color: red;"><span id="statusMsg" 
								style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
							</div>
							<div style="display: none" id="quickformvalues"></div>
							<spring:hidden path="valuesinquickform"/>
							
							<div class="form_scroller">
								<div class="form-group">
									<div class="col-sm-12">
									<!-- 	<input type="text" class="form-control firstName" id="inputEmail3"
											placeholder="First Name/User Name"> -->
								<spring:input path="firstName" class="form-control"
										id="hideEmail" placeholder="firstName"></spring:input>
									<spring:errors style="color: red" path="firstName" id="first" ></spring:errors>
											<%-- <spring:input path="firstName" class="form-control" placeholder="First Name"/> --%>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<!-- <input type="text" class="form-control lastName" id="inputEmail3"
											placeholder="Last Name"> -->
									<spring:input path="lastName" class="form-control"
										id="hideEmail" placeholder="LastName"></spring:input>
									<spring:errors style="color: red" path="lastName" id="lastId" ></spring:errors>
											<%-- <spring:input path="lastName" class="form-control" placeholder="Last Name" /> --%>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
									<!-- 	<input type="email" class="form-control email" id="inputEmail3"
											placeholder="Email Address"> -->
												<spring:input path="email" class="form-control" id="hideEmail" placeholder="Email"></spring:input>
									
									<spring:errors style="color: red" path="email"></spring:errors>
											<%-- <spring:input path="email" type="email" class="form-control" placeholder="Email Address"/> --%>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<!-- <input type="text" class="form-control contactNumber" id="inputEmail3"
											placeholder="Contact Number"> -->
												<spring:input path="phoneNumber" class="form-control" id="hideEmail" placeholder="phoneNumber"></spring:input>
										
									<spring:errors style="color: red" path="phoneNumber" id="phoneId" ></spring:errors>
											<%-- <spring:input path="contactNumber"  class="form-control" placeholder="Contact Number"/> --%>
									</div>
								</div>

							</div>
							<div style="padding: 0px 100px;">
								<div class="form-group">
									<div class="col-sm-12">
									<button type="submit" class="btn btn-default btn-save" id="guestUserBtn">Proceed as Guest<br/>Data will not be saved.
									</button>	
									
									
									</div>
								</div>
								
							</div>
			              </spring:form>
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
							<li>- Einmalige Registrierung fÃÂ¼r alle angeschlossen
								Anbieter</li>
							<li>- ÃÅbersciht aller vergangen und zukÃÂ¼nftigen
								Reservierungen</li>
							<li>- Storno via one click</li>
							<li>- Sie entscheiden welcher Anieter Ihre E-mail Addresse
								Sehen kann</li>
							<!-- <li>- Hinweis bei BuchungÃÂ¼berlappungen</li> -->
						</ul>
					</div>
				</div>
			<div id="fade" class="black_overlay"></div>
			<!-- /End contact data modal -->
<!-------------------------------- MYRESERVATION DATA MODAL --------------------------------------------->
		<div id="myreservation" class="white_content">
					<h3>Reservation</h3>
					<a href="javascript:void(0)" class="close_popup" onclick = "closePopUp()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div style="padding: 0px">
						<form class="form-horizontal" id="reservationForm">
						<div id="statusInfoDiv" align="center"><span id="statusInfoSpan" 
							style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
								</div>
							<div align="center"><span id="isReservtnPrgrs" 
								style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
							</div>
							
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
								<div id="reservationHotelDetails"></div>
							
								</div>
							</div>
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-time"  aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
									<div id="checkedoutDates"></div>
									 <div class="change_option_div"id="change_option_div" 
									 	style="color: #09C;  padding-bottom:4px;">please select the check-out date</div> 
									<div id="detailschenged"></div>
								</div>
							</div>
						
							<div class="reservation-data-section">
								<div class="col-xs-1">
									<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
								</div>
								<div class="col-xs-11">
									<%-- <p>
										${userName} <br /> ${useremail} <br /> ${userPhone}
									</p> --%>
									<div id="userDetails"></div>
								</div>
							</div>
							<div class="col-xs-12" style="padding: 20px 20px 6px 20px;">
								<div class="form-group">
									<div class="col-sm-1">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									</div>
									<div class="col-xs-11">
									  <textarea class="form-control" id="notes" rows="1" name="notes"></textarea>
									</div>
								</div>
								<p style="padding-left: 40px;">
								<input type="hidden" name="emailShare" id="emailShare" value="N">
									<button id="share-email" type="button"
										class="btn btn-default btn-share" data-toggle="tooltip"
										data-placement="top" title="Share Email Address">
										<span class="glyphicon glyphicon-envelope" aria-hidden="true" onclick="emailShareFun()"></span>
									</button>
									&nbsp;&nbsp; Agree to share Email address with service
									provider.
								</p>
							</div>
							<!--ends col-xs-12 -->
							<div class="clearfix"></div>
							<div class="button_padding">
								<div class="form-group">
									<div class="col-sm-6">
										<!-- <a href="javascript:void(0)" class="btn btn-default btn-save"
											onclick="document.getElementById('myreservation').style.display='none';document.getElementById('myreservation_confirm').style.display='block';document.getElementById('fade').style.display='block'">Reserve
											table</a> -->
											<input type="button" class="btn btn-default btn-save reserventnHide" value="Reserve table" onclick="reserveTable(event)">
										<img id="ajaxLoader" src="images/ajax-loading.gif" style="display:none; margin: -200px 150px 0px;">
									</div>
									<div class="col-sm-6">
										<button type="button" class="btn btn-default btn-save" id="cancelReservation">Cancel</button>
									</div>
								</div>
							</div>
						</form>
						<div id="hideTimer">
						<p class="center"
							style="padding: 10px 0px 0px 0px; margin-bottom: 2px;" >
							Reservation Process Continues for next <span id="log">60</span>
							Secs
						</p>
						</div>
						<div id="flash" class="center"></div>

					</div>
				</div>
			<div id="fade" class="black_overlay"></div>
<!-- ------------------------------------------------------------------------------------------------------------- -->
<!-- ============================= CHANGE RESERVATION MODAL START =============================================-->
	<div id="changeReservtn_popUp" class="white_content">
			<h3>Change Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "closePopUp()"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					
					<div class="reservation-data-section">
					
						<div class="col-xs-11">
						<span style="font-size: 16px;font-weight: 700;" id="changeReservtnText"></span>
						</div>
					</div>
					
				<div class="clearfix"></div>
				   <div style="padding:0px 100px;" id="changeAndCancelBtns">
					
				  </div>
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!-- ============================= CHANGE RESERVATION MODAL END =============================================-->

<!-- ============================= DELETE RESERVATION MODAL START =============================================-->
	<div id="deleteReservtn_popUp" class="white_content">
			<h3>Delete Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "closePopUp()"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					
					<div class="reservation-data-section">
					
						<div class="col-xs-11">
						<span style="font-size: 16px;font-weight: 700;" id="deleteReservtnText"></span>
						</div>
					</div>
					
				<div class="clearfix"></div>
				   <div style="padding:0px 100px;" id="deleteAndCancelBtns"></div>
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!-- ============================= DELETE RESERVATION MODAL START =============================================-->
                      
<!-------------------------------------- LOGGED IN USER DATA MADEL START --------------------------------------------->
			<div id="loggedInUserData" class="white_content">
			<h3>Reservation Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "closePopUp()"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px" id="reseravationDiv">
				<form class="form-horizontal">
					<div id="statusDiv" align="center"><span id="statusInfo" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
					</div>
					<div class="reservation-data-section">
					<div class="col-xs-1">
					<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
					</div>
					<div class="col-xs-11" id="hotelInfoDiv"></div>
					</div>
						<div class="reservation-data-section">
						<div class="col-xs-1">
						<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
						</div>
						<div class="col-xs-11" id="checkInandOutTimeDiv">
						<p>
						
						</p>
						</div>
						
						</div>
					<div class="reservation-data-section">
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11" id="userDetailDiv">
							 <p style="padding-bottom:10px;" >
							</p>
						</div>
					</div>
					
					<div class="col-xs-12" style="padding:20px 20px 6px 20px;">
						<div class="form-group">
							<div class="col-sm-1">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</div>
							<div class="col-xs-11" id="noteDiv">
							  <textarea class="form-control" rows="1" id="noteID"></textarea>
							</div>
						</div>
					
					</div><!--ends col-xs-12 -->
					<div class="clearfix"></div>
				   <div style="padding:0px 100px;">
					
				  </div>
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!------------------------------------- LOGGED IN USER DATA MODEL END ---------------------------------------------->

<!-------------------------------- RESERVATION CONFIRMATION DATA MODAL --------------------------------------------->
		<div id="myreservation_confirm" class="white_content">
                                        <h3>Reservation Confirmation</h3>
                                        <a href="javascript:void(0)" class="close_popup" onclick="closePopUp()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
                                        <div style="padding: 0px">
                                            <form class="form-horizontal">
                                                <div class="reservation-data-section">
                                                    <div class="col-xs-12">
                                                        <div class="alert alert-success" role="alert" style="padding: 5px 12px; margin-bottom: 5px;">
                                                            <strong><span class="glyphicon glyphicon-ok-circle"
																aria-hidden="true"></span> Success!</strong> You successfully made reservation.
                                                        </div>
                                                        <div id="referenceNumber"></div>
                                                    </div>
                                                </div>
                                                <div class="reservation-data-section">
                                                    <div class="col-xs-4" style="text-align: right;">Sponsor By
                                                    <img src="images/pepsiMdfied.png" class="img-responsive hotel_logo" style="margin: -25px 250px 0px;height: 50px;width: 130px;" />
                                                    </div>
                                                    <div class="col-xs-8"></div>
                                                </div>
                                                <div class="reservation-data-section">
                                                    <div class="col-xs-1">
                                                        <span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
                                                    </div>
                                                    <div class="col-xs-11">
                                                        <div id="confirmHotelDetails"></div>
                                                    </div>
                                                </div>
                                                <div class="reservation-data-section">
                                                    <div class="col-xs-1">
                                                        <span class="glyphicon glyphicon-time" aria-hidden="true"></span>
                                                    </div>
                                                    <div class="col-xs-11">
                                                        <div id="datesValues"></div>
                                                    </div>
                                                </div>
                                                <div class="reservation-data-section">
                                                    <div class="col-xs-1">
                                                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                                    </div>
                                                    <div class="col-xs-11">
	                                                    <!-- This div for showing user details -->
	                                                    <div id="confirmuserDetails"></div>
                                                    </div>
                                                </div>

                                                <div class="clearfix"></div>
                                                <div style="padding: 20px 100px;" >
                                                    <div class="form-group exportCls" id="showExportDiv">
                                                       <a href="#" class="btn btn-default ">I</a> 
                                                       <a href="#" class="btn btn-default">F</a> <a href="#" class="btn btn-default ">F</a> 
                                                       <a href="javascript:void(0)" class="btn btn-default"  id="exportBtn" onclick="exportPdf()">Export</a>

                                                    </div>
                                                </div>
                                            </form>

                                        </div>
                                    </div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- MYRESERVATION DETAILS DATA MODAL --------------------------------------------->
			<div id="myreservation_details" class="white_content"
					style="left: 8%; width: 85%;" >
					<h3>My Reservation Details</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="closeWindow()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
						
						<div id="statusDiv" align="center"><span id="statusInformtn" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
						</div>
					<div class="user_data_wrapper" style="padding: 0px">

						<form class="form-horizontal">
							<div class="reservation-data-section" id="reservationHistory">
								
							</div>
							<!-- div ends-->
						</form>
					</div>
				</div>
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- WHITELIST DETAILS DATA MODAL --------------------------------------------->
			<input type="hidden" value="${whiteDemo}" id="whiteDemo">
				<%-- 			<core:if test="${not empty whiteDemo }"> --%>
				<input type="hidden" value="${whiteDemo}" id="whiteDemo">
				<div id="mywhite_list" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>My White List Category</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="closeWindow()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form action="getWhiteListDet.htm" class="form-horizontal"
							id="mywhite_list">
							<div class="reservation-data-section">
								<!-- <div class="col-xs-7 col-md-7">
                                         <div id="whitelistdetails">HotelName:</div>                                         
									 </div>
									  <div class="col-xs-5 col-md-5">									
										<p>											
											<div id="startDate">since:</div>										
										</p>
									</div>
								</div>		 -->
								<div id="whitelistdetails"></div>
								<div id="startDate"></div>
							</div>
						</form>
					</div>
				</div>
				<%-- 	</core:if> --%>
				<div id="fade" class="black_overlay"></div>
<!-------------------------------- BLACK LIST DETAILS DATA MODAL --------------------------------------------->
			<input type="hidden" value="${blackDemo}" id="blackDemo">
				<%-- 	<core:if test="${not empty blackDemo}"> --%>
				<input type="hidden" value="${blackDemo}" id="blackDemo">
				<div id="myblack_list" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>My Black List Category</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="closeWindow()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form action="getBlackListDet.htm" class="form-horizontal"
							id="myblack_list">
							<!--  <form class="form-horizontal"> -->
							<div class="reservation-data-section">
								<div id="blacklistdetails"></div>
								<div id="stratDate"></div>
								<!-- <div class="col-xs-7 col-md-7">
									  <div id="blacklistdetails"></div>
									</div>
									<div class="col-xs-5 col-md-5">								
										<p>
										<div id="strtDate">since:</div>										
										</p>
									</div> -->
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
						onclick="closeWindow()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form class="form-horizontal">
							<div id="emailreservation"></div>

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
			<input type="hidden" value="${testUser}" id="testUser"> <input
					type="hidden" value="${listOfAPIs}" id="listOfAPIs"> <input
					type="hidden" value="${testUser}" id="testUser">
				<div id="hotel_details" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>Contact Details</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('hotel_details').style.display='none';document.getElementById('fade').style.display='none'"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<form class="form-horizontal">
						
						<div class="reservation-data-section">
							<div class="col-xs-10 col-md-10">
								<p>
								<table>
									<div id="addressdetails"></div>
								</table>
								<iframe
									src="https://www.google.com/maps/embed/v1/place?key=AIzaSyCqOFwiSrw2ZtqU-523kQ2ftKHpi2tSDCc
          					  						&q=${hotel.hotelName}, ${hotel.hotelAddress} ${hotel.postalCode} ${hotel.city}"
									width="120%" height="230" frameborder="0"
									style="border: 0; margin: 10px 0px 0px;"></iframe>

							   </div>
							<div class="col-md-2">
								<button class="btn btn-default btn-success"
									onclick="downloadPDF()" type="button">Download</button>
								<button class="btn btn-default" style="margin-top: 10px;"
									type="button">Timings</button>
							</div>
							<div class="map" id="map" style="width: 100%; height: 250px;"></div>
						</div>
						</form>

					</div>
				
				<input type="hidden" value="${mode}" id="mode"> <input
					type="hidden" value="${login}" id="login"> <input
					type="hidden" value="${profilehide}" id="profilehide">
			<div id="fade" class="black_overlay"></div>
<!-------------------------------- MY PROFILE DETAILS DATA MODAL --------------------------------------------->
			<input type="hidden" value="${mode}" id="mode"> 
			<input type="hidden" value="${updateUser}" id="updateUser"> 
			<input type="hidden" value="${mode}" id="mode">
			<div id="myprofile" class="white_content"
					style="left: 10%; width: 80%;">

					<%-- <span style="color:red;padding-left: 164px;font-weight: bold;">${pemail_not_unique}</span>
                                      		<span style="color:green;padding-left: 42px;font-weight: bold;" id="showUpdate">${updateUser}</span> --%>

					<h3>My Profile</h3>
					<!-- <a href ="javascript:void(0)" class="close_popup" onclick = " ><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a> -->
					<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myprofile').style.display='none';document.getElementById('fade').style.display='none';"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<%-- <spring:form action="" method="post" commandName="updateuser"  > --%>
					<spring:form action="updateUser.htm" commandName="getUser"
						class="form-horizontal">
						<div class="reservation-data-section">
							<span style="color: red; padding-left: 170px; font-weight: bold;">${pemail_not_unique}</span>
							<span
								style="color: green; padding-left: 10px; font-weight: bold;"
								id="showUpdate">${updateUser}</span>
							<h5>My Profile Details</h5>
							<div class="profile_scroller">
								<div class="form-group">
									<spring:hidden path="userId" />
									<label for="inputEmail3" class="col-md-2 control-label">UserName</label>
									<div class="col-sm-9 col-xs-12">
										<spring:input type="text" path="userName" class="form-control"
											id="inputEmail3" placeholder="Username" readonly="true"></spring:input>
										<spring:errors style="color: red" path="userName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Password</label>
									<div class="col-sm-9">
										<spring:password path="password" class="form-control"
											showPassword="true" id="inputEmail3" onchange="hidepwd"></spring:password>
										<spring:errors style="color: red" path="password" id="pwd"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Confirm
										Password </label>
									<div class="col-sm-9">
										<spring:password path="confirmPassWord" class="form-control"
											showPassword="true" id="inputEmail3"
											onchange="hideConfirmpassword()"></spring:password>
										<spring:errors style="color: red" path="confirmPassWord"
											id="confirmpassword"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Title</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="title" name="title"
											id="title" items="${title}">
										</spring:select>
										<spring:errors style="color:red" path="title"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">First
										Name </label>
									<div class="col-sm-9">
										<spring:input path="firstName" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="firstName"
											id="firstName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Last
										Name </label>
									<div class="col-sm-9">
										<spring:input path="lastName" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="lastName"
											id="lastName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Street</label>
									<div class="col-sm-9">
										<spring:input path="streetName" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="streetName"
											id="streetName"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Pincode</label>
									<div class="col-sm-9">
										<spring:input path="postalCode" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="postalCode"
											id="postalcode"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">City</label>
									<div class="col-sm-9">
										<spring:input path="city" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="city" id="city"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Country</label>
									<div class="col-sm-9 col-xs-12">
										<spring:select class="form-control" path="country"
											name="countryid" id="countryid" style="color:#000;"
											items="${countryList}">
										</spring:select>
										<spring:errors style="color: red" path="country" id="country"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Date
										Of Birth</label>
									<div class="col-sm-9">
										<spring:input path="dob" class="form-control" name="dob"
											placeholder="dd/mm/yyyy" onchange="hidedateofbirth()"
											id="datepicker"></spring:input>
										<spring:errors style="color: red" path="dob" id="dateOfBirth"></spring:errors>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
									<div class="col-sm-9">
										<spring:input path="email" class="form-control"
											id="inputEmail3"></spring:input>
										<spring:errors style="color: red" path="email" id="email"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Phone</label>
									<div class="col-sm-9">
										<spring:input type="text" class="form-control"
											path="phoneNumber" id="contact_no" name="contact_no"
											placeholder="Phone Number" onchange="hidecontactnumber()"></spring:input>
										<spring:errors style="color: red" path="phoneNumber"
											id="contactNumber"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Language</label>
									<div class="col-sm-9">
										<spring:select class="form-control" path="language"
											name="language" id="language" style="color:#000;"
											items="${languages}"></spring:select>
										<spring:errors style="color: red" path="language"></spring:errors>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">Join
										Date </label>
									<div class="col-sm-9">
										<spring:input path="joinDate" class="form-control"
											id="inputEmail3" readonly="true"></spring:input>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-md-2 control-label">Notify
										Period </label>
									<div class="col-sm-9 col-xs-12">
										<spring:select class="form-control"
											path="notificationDuration" name="notify" id="notify"
											style="color:#000;" items="${notificationPeriod}">
										</spring:select>
										<spring:errors style="color: red" path="notificationDuration"
											id="notifyduration"></spring:errors>
									</div>
								</div>
							</div>
						</div>
						<div class="line"></div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label"></label>
							<div class="col-sm-9">
								<button type="submit" class="btn btn-default btn-save"
									style="width: auto;">Update</button>
							</div>
						</div>
						<!--ends-->
					</spring:form>
				</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!-- =========================================Main html code body==============================================================================================-->
			<div class="col-xs-3 hotel_title nopadding">
					<img src="images/logo_positiv(hotel).jpg" class="img-responsive hotel_logo" style="margin-bottom:5px;" />
			</div>
			<%-- <div class="col-md-3 hotel_title nopadding">
                 <img src="<%=request.getContextPath()%>/${image}" class="img-responsive hotel_logo" style=" width: 163px; height: 50px;   margin: 30px 0px 0px;" />
                </div> --%>
			<div class="col-md-6" style="padding: 20px 20px 20px 35px;">
				<c:if test="${sessionScope.hotel != null}">
					<b> ${sessionScope.hotel.hotelName}</b>
					<br />${sessionScope.hotel.hotelAddress} ${sessionScope.hotel.postalCode} ${sessionScope.hotel.city}
                                    </c:if>
			</div>
			
				<!-- <div><a href="serviceProviderView.htm" style="font-style: italic; color: cornflowerblue;">service Provider View</a>
				</div> -->
			
			<div class="col-xs-3" style="padding:35px 0px 0px;">				
				<div class="iconset">
					<a href = "javascript:void(0)" class="btn-group" onclick = "getHotel()" class="btn-blank" id="htlNavgtnBtn">
					<img src="images/more-24.png" /></a>
					<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false" id="changePageMode">
							<img src="images/greenpat.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu">
							<li><a href="javascript:void(0)" id="dragAndDrop" ><img src="images/brownpat.png" style="padding-right:6px;" /></a></li>
							<li><a href="javascript:void(0)" id="permanentRreservation" ><img src="images/bluepat.png" style="padding-right:6px;" /></a></li>
							<li><a href="javascript:void(0)" id="chngeResevtnByProvider" ><img src="images/greenpat.png" id="" style="padding-right:6px;" /></a></li>
						  </ul>
						</div>
						<!--ends-->
					
					<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false" >
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
<!-- 							<li><a href = "javascript:void(0)" onclick = "document.getElementById('mylogin').style.display='block';document.getElementById('fade').style.display='block'">Login</a></li> -->
	                          <li><a href="javascript:void(0)" 
								onclick="getLoginDetails()">login</a></li>
							<li><a href = "javascript:void(0)" onclick = "document.getElementById('my_registration').style.display='block';document.getElementById('fade').style.display='block'">Registration</a></li>
							<!-- <li><a href = "javascript:void(0)" onclick = "document.getElementById('myquickreservation').style.display='block';document.getElementById('fade').style.display='block'">As Guest</a></li> -->
			
						  </ul>
						</div>
						<!--ends-->
						<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false" id="greenuser">
							<img src="images/green-user.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu" style="left:-100px;">
							<li><a href="javascript:void(0)" onclick="getUser()" >My Profile</a></li>
							<li><a href = "javascript:void(0)" onclick ="getBookingHistory()">My Reservations</a></li>
							<li><a href = "javascript:void(0)" onclick ="getWhiteList()" class="circle">White List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="getBlackList()" class="circle">Black List Category</a></li>
							<li><a href = "javascript:void(0)" onclick ="getEmailVisible()" class="circle">E-mail Visible </a></li>
							<div class="divider"></div>
							<li><a href="logout.htm" onclick="logoutUser()" >Logout</a></li>
			
						  </ul>
						</div>
						<!--ends-->
						<!-- Red user button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false" id="redUser">
							<img src="images/red_user.png" />
						  </button>
						  <!-- onclick = "document.getElementById('admin_customer_list').style.display='block';document.getElementById('fade').style.display='block'" -->
						  <ul class="dropdown-menu" role="menu" style="left:-140px;">
						  <li><a href = "javascript:void(0)" id="adminCustmrList">Customers List</a></li>
						  <li><a href = "javascript:void(0)" id ="custmrReservtns">Customers Reservation</a></li>
						  <li><a href = "javascript:void(0)" id ="adminWhiteList">White List Customers</a></li>
						  <li><a href = "javascript:void(0)" id = "adminBlckList">Black List Customers</a></li>
						  <li><a href = "javascript:void(0)" id = "custmrsEmailList">Email Of Customers</a></li>
						  <li><a href = "javascript:void(0)" id = "addToWhitLstCat">Add to White List Category</a></li>
							
						  </ul>
						</div>
						<!--ends-->
					<a class="btn-blank" ><img src="images/fullscreen.png" id="fs" /></a>
				</div>
			</div>
			<div class="clearfix"></div>
			
				<div class="clearfix"></div>
				<div class="col-md-3 col-sm-3 col-xs-12 nopadding">
					<button class="btn btn-default btn-current-date" id="currentDate">
						<span  id="currentDateVal"></span>
			        </button>
				</div>
				
				<div class="col-md-3 col-sm-3 col-xs-12">
				<select class="selectpicker form-control" id="category"></select>
			</div>
			
				<div class="col-md-4 col-sm-4 col-xs-12  nopadding">
				<select class="selectpicker form-control" id="roomNo"></select>
			</div>
			
				<div class="col-xs-2 col-xs-2 col-xs-12" style="padding-right:0px;">
					<input type="number" class="form-control" Placeholder=" Days" id="noOfDays"   />
				</div>
			<div class="clearfix"></div>
			<div class="ajax_container">
			<div class="ajax_loader"></div>
				<div class="col-xs-12 nomargin_lf_rt date_wrapper">
							<div id="thumbcarousel1" class="carousel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							  
							   <div class="carousel-caption1" id="prmryDatesList">
									</div><!-- carousel-caption1 -->
								<div class="carousel-caption1" id="secndryDatesList"></div>
							 </div><!-- thumbcarousel1 close -->

							  <!-- Controls -->
							  <a class="left carousel-control" href="#" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-triangle-left previousBtn" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a>
							  <a class="right carousel-control nextBtn" href="#" role="button" data-slide="next" > 
								<span class="glyphicon glyphicon-triangle-right rightArrow" id="nextButton"  aria-hidden="true" style="display: inline;"></span>
								<span class="sr-only">Next</span>
							  </a>
							</div>
				
				</div><!-- ends date list -->
		<!-- Object or week list and data  -->
		
		<div class="clearfix"></div>
				<div class="col-xs-12 nomargin info_wrapper">
							<div class="ajax_container_inner">
							
							<!-- <div class="ajax_loader_inner"></div> -->
							<div id="thumbcarousel2" class="carousel carousel_info_hotel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							  
							  
							  <div class="carousel-inner" role="listbox" id="carouselDivPrimary">
							  </div>
							    <div  class="carousel-inner" role="listbox" id="carouselDiv">
							    </div>
								<div  class="carousel-inner" role="listbox" id="roomInfoDiv">
								</div>
							
							  <!-- Controls -->
							  <!-- <a class="myleft carousel-control" href="#thumbcarousel2" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-triangle-left glyphicon glyphicon-triangle-myleft" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a> -->
							  <!-- <a class="myright carousel-control" href="#thumbcarousel2" role="button" data-slide="next">
								<span class="glyphicon glyphicon-triangle-right glyphicon glyphicon-triangle-myright" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							  </a> -->
							</div>
				
				</div>
				</div>
		<!-- ends-->
		</div>
	
	
		<ul class="footer_menu">
		<li><a href="#">AGB</a></li>
		<li><a href="#">Impressum</a></li>
		<!-- <li><a href="serviceProviderView.htm" style="font-style: italic; color: cornflowerblue;">Admin</a></li> -->
		<a href="javascript:void(0)"  onclick="getServiceLoginDetails()" style="font-style: italic; color: cornflowerblue;">Admin</a>
		<li><a href="#">Über Reservat</a></li>
		<li><a href="#">Help</a></li>
		</ul>
	</div>


	<!-- ends-->
	<div class="col-xs-5 col-xs-3 col-xs-12">
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
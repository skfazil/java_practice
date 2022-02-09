<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="spring"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="tag"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
             <%-- <%@ page language="java" session="false"%> --%>
<html>
<head>
<!-- <meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
		<!-- <meta name="viewport" content="width=700">	 -->
		<title>Service Provider View</title>	
		
		<!-- <link href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" rel="Stylesheet"></link>
		<script src="YourJquery source path"></script>
		<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js" ></script> -->
		
	    <link rel="stylesheet" href="css/bootstrap.min.css"/>
		<link href="css/style.css" rel="stylesheet">
		<link rel="stylesheet" href="css/non-responsive.css">
		<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css">
		<link rel="stylesheet" href="css/datepicker.css">
		
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	    <script type="text/javascript" src="js/bootstrap.min.js"></script>
	   	<script type="text/javascript" src="js/jquery.validate.js"></script>
	    <script src="js/bootstrap-datepicker.js"></script>
	   
			   <script src="js/customjs.js"></script>
			   <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
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
	            	
	            	var chngePopup = $('#chngePopup').val();
	            	var username = $('#inUser').val();
	            	var email = $('#inEmail').val();
	            	var phone = $('#inPhone').val();
	            	var notes = $('#inNotes').val();
	            	if( chngePopup == 'changing') {
	            		$("#userName").val(username);
	            		$("#userName").prop('readOnly',true);
	            		$("#userEmail").val(email);
	            		$("#userEmail").prop('readOnly',true);
	            		$("#userPhoneNum").val(phone);
	            		$("#userPhoneNum").prop('readOnly',true);
	            		$("#userNotes").val(notes);
	            		$("#userNotes").prop('readOnly',true);
	            	}
	            	
	                $('#datepick').datepicker({
	                	  autoclose: true,
	                    format: "dd/mm/yyyy"
	                });  
                
				$('#datepicker').datepicker({
						autoclose : true,
						format : "dd/mm/yyyy"
					});
			
		});// document.ready function close.
	</script>
			
			<script type="text/javascript">
			
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
	         
	         function showDatesOnDateBar(){
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
			
			
			
				$(document).ready(function() {
					
					var mode = $("#mode").val();
					if (mode != '') {
						$('#myprofile').show();
						document.getElementById('fade').style.display='block';
					}
	
					var updateUser = $("#updateUser").val();
					if (updateUser != '') {
						$('#myprofile').show();
						document.getElementById('fade').style.display='block';
						var hideMessage = function() {
							$('#showUpdate').hide();
						};
						setTimeout(hideMessage, 2000);
					}
				});
				
				function getProfile() {
                    document.location.href = "getProfile.htm";
                }
			</script>
			   <script type="text/javascript">
			   
			   function displySearchDatesListOnDatesBar(selctdVal,blckDate){
				   
				   getLoggedInUserDates();
				   var dateVal = selctdVal.slice(0,2);
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
	           			displySearchDates(noOfDays,catgry,selctdVal,fullDate);
	           		 }else{
	           			getDatesListOnCatgryValType(catgry,fullDate,selctdVal,blckDate);
	           		 }
				   
			   }
			   
			   function getDatesListOnCatgryValType(catgry,fullDate,selctdVal,blckDate){
				   
				   getLoggedInUserDates();
				   var noOfDays = $('#noOfDays').val();
		        	 if(noOfDays == ''){
		        		 noOfDays = 0;
		        	 }
		        	 
		        	 $('#secndryDatesList').empty();
	                 $('#prmryDatesList').empty();
	                 
	                 $.ajax({
	                 	url   : 'getSelectedDatesListOnCatgryType.htm',
	                  	type  : 'POST',
	                  	data  :{selctdDate:selctdVal,catVal:catgry,noOfDays:noOfDays},
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
	                  								 && userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)
	                  								 && userBkdRecrds[record].categoryId == response[i].categoryId){
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
			   
			   function displySearchDates(noOfDays,catgry,selctdVal,fullDate){
				  
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
			   
			   function showBlackdate(blckDate,selctdVal,dateVal,fullDate,currDate){
			        
		        	 var dateValue = selctdVal;
	                      //get loggedIn user dates and compared with dates list, if both dates are equal shows 
	                        // that dates should be shows in blue color.
	                        var userBkdRecrds  = ''; 
	                        $.ajax({
	   	                    	 url    : 'getLoggedInUserReservedDates.htm',
	   	                    	 type   : 'POST',
	   	                    	 success : function(data){
	   	                    		userBkdRecrds =  jQuery.parseJSON(data);
	                       	 }//success function close.
	                         });//ajax function close.
	                        
	                        var dateVal = selctdVal.slice(0,2);
	                        
	                      $.ajax({
	                     	url   : 'getDatesList.htm',
	                      	type  : 'POST',
	                      	success : function(data){
	                      		var response =  jQuery.parseJSON(data);
	                      		fullDate = fullDate.replace('/','-');
	                      		fullDate = fullDate.replace('/','-');
	                         	var html = '';
	                         	
	                  			 html += '<div class="piece getDateVal" >';
	                  		
	                  			var monthsArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec']; 

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
	                          		
	                          		html += '<h3 class="month_title">'+mnthNme+'</h3>';
	                  				html += '<div class="small_square" >';
	                  				for(i in response){
	                      				if(fullDate.slice(0,2) == response[i].selecteDt.slice(0,2) && 
	                      						fullDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                      					html += '<span class="cal_date current_date hiddenSpan">';
	                      					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                      					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                      				}else{
	                      				  var blueDtval = '';
	                    					  for(var record in userBkdRecrds){	
	                    						 
	                    						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)
	                    								  && userBkdRecrds[record].userBkdDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                    							  
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
	                    							  
	                    							 if(blckDate.slice(0,2) == dateValue ){
	                    								 
	                      								html += '<span class="cal_date mark_date hiddenSpan">';
	                              						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                              						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                              						blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	                  								}
	                    							 
	                    							 if( response[i].selecteDt.slice(0,2) == dateVal ){
	                  									
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
	                    					} //for (userBkdRecrds)close.
	                    					
	                    					if(blueDtval != response[i].selecteDt.slice(0,2)){
	                    						
	                    						if(response[i].booked == response[i].noOFRooms ){
	                    							
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
	                    							if( response[i].selecteDt.slice(0,2) == dateVal ){
	                    								
	                    								if(dateVal == blckDate.slice(0,2) && 
	                        									dateValue.slice(3,5) == response[i].selecteDt.slice(3,5) ){
	                        								html += '<span class="cal_date  mark_date hiddenSpan">';
	                                						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                                						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                    								}else{
	                    									if(dateValue.slice(0,2) == response[i].selecteDt.slice(0,2)&&
	                    											dateValue.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                    										html += '<span class="cal_date mark_date   hiddenSpan">';
	                                    						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                                    						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                    									}else{
	                    										html += '<span class="cal_date hiddenSpan">';
	                                    						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                                    						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                    									}
	                    								}
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
	                    								
	                    								html += '<span class="cal_date hiddenSpan">';
	                            						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                            						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                    							}
	                        						
	                        					}else{
	                        						if( response[i].selecteDt.slice(0,2) == dateVal ){
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
	                        							if(dateVal == blckDate.slice(0,2) && 
	                        									dateValue.slice(3,5) == response[i].selecteDt.slice(3,5) ){
	                        								html += '<span class="cal_date  mark_date hiddenSpan">';
	                                						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                                						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                    								}else{
	                    									if(dateValue.slice(0,2) == response[i].selecteDt.slice(0,2)&&
	                    											dateValue.slice(3,5) == response[i].selecteDt.slice(3,5)){
	                    										html += '<span class="cal_date mark_date  green hiddenSpan">';
	                                    						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                                    						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                    									}else{
	                    										html += '<span class="cal_date green hiddenSpan">';
	                                    						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                                    						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                    									}
	                    								}
	                    							} else{
	                    								
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
			   
			   
			   
			   function closeWindow(){
	               	document.location.href = 'returnToServiceHme.htm';
	               }
			   
			   function closeNReset() {
				   document.getElementById('myreservation_manual').style.display='none';
				   document.getElementById('fade').style.display='none';
				   
					
					$('#myreservation').hide();
					
					$('#hideTimer').css({'display': 'true'});
					$('#button_padding').css({'display': 'true'});
					
					clearTimeout(timer);
					var timerVal = '0:1';
					if(timer == timerVal){
						
						$("#flash").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="returnToHme.htm" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
						$('.processing').removeClass("processing");
						$('.button_padding').hide();
						$('#hideTimer').hide();
					}
				   
			   }
				
				   function getStopSharing(bookingId) { 
					   
						  $.ajax({
					            url: "stopServiceEmailShare.htm?bookingId="+bookingId,
					            type: 'POST',	           
					            success: function(data) {
					            	
					            	$.ajax({
								          url: "getServiceEmailListDet.htm",
								          type: 'POST',         
								          success: function(data){  
									          var response  = jQuery.parseJSON(data); 
									          var html = '';
									          $('#emailVisibleForm').empty();
									          html += '<div class="reservation-data-section">';
									          html += '<div class="col-xs-5 col-md-5">';
											  html += '<strong>User Details</strong>';
											  html += '</div>';
											  html += '<div class="col-xs-4 col-md-4">';
											  html += '</div>';
											  html += '<div class="col-xs-3 col-md-3">';
											  html += '</div>';
											  html += '</div>';
									          for(i in response) {			        		        	
									        		
										        	html += '<div class="reservation-data-section">';
													html += '<div class="col-xs-5 col-md-5">';
													html += '<p>';
													html += '<strong>'+response[i].userName+'</strong><br />';
													html += response[i].email +'<br />';
													html += 'Ph : '+response[i].phoneNumber + '<br />';
													html += '</p>';
													html += '</div>';
													html += '<div class="col-xs-4 col-md-4">';
													html += '<p>';
													html += 'Sharing since:' +response[i].bookingDt+ '<br / >';
													html += '</p>';
													html += '</div>';
													html += '<div class="col-xs-3 col-md-3">';
													html += '<button type="button" class="btn btn-default" onclick="getStopSharing('+response[i].bookingId+')">Stop Sharing</button>';
													html += '</div>';
													html += '</div>';           
									         
									         }// for loop close.
									        
									     	  $('#emailVisibleForm').append(html);
									     	  $('#myemail_list').show();
									     	  document.getElementById('fade').style.display='block';
									         }							     
								      });
					            	
					            	$('#isEmailShared').html(data);
	    				 			$('#isEmailShared').show().fadeOut(4000);				            	
					            }
					        });
					 }
				   
				   function	getEmailVisible() {	 
					   
					   $.ajax({
					          url: "getProviderEmailListDet.htm",
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
					        
					     	  $('#emailVisibleForm').append(html);
					     	  $('#myemail_list').show();
					     	  document.getElementById('fade').style.display='block';
					         }
					     
					      });	
					  }
				   
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
					            		document.getElementById('fade').style.display='block';
					            	} 
					            }
					        });	
					 }
				   
				   
	             
				   function getServiceWhiteList() {
						$.ajax({
							url : "getWhiteListCategoryCustmrs.htm",
							type : 'POST',
							success : function(data) {
								var response = jQuery.parseJSON(data);
								var html = '';
								$('#whitelistdetails').empty();
								 for (i in response) {
									html += '<div class="col-xs-7 col-md-7">';
									html += '<p>';
									
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
								$('#whitelistdetails').append(html);
								$('#mywhite_list').show();
								document.getElementById('fade').style.display='block';
							}

						});

					}
				   
				   function getServiceBlackList() {
						$.ajax({
							url : "getBlackListCategoryCustmrs.htm",
							type : 'POST',
							success : function(data) {
								var response = jQuery.parseJSON(data);
								var html = '';
								$('#blacklistdetails').empty();
								for (i in response) {
									html += '<div class="col-xs-7 col-md-7">';
									html += '<p>';
									
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
								document.getElementById('fade').style.display='block';
							}
						});

					}
				   
				   function providerReservtnHistyr(){
					  //getMyReservations.htm
					   $.ajax({
							url : "bookingHistory.htm",
							type : 'POST',
							success : function(data) {
								var response = jQuery.parseJSON(data);
								var html = '';
								$('#reservationHistory').empty();
								
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
	               					 
               				}
								
								$('#reservationHistory').append(html);
								$('#myreservation_details').show();
								document.getElementById('fade').style.display='block';
							}
						});//ajax call close...
				   }
				   
				   function getMyReservations() {
					   providerReservtnHistyr();
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
					
					//   I Observed no where this function used.
					 function getBookingHistory(){ 
		             		
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
		              					 if(response[i].status == 'active'){
		              						 html += '<div class="col-xs-2 col-md-2">';
			               					 html += '<button class="btn btn-default btn-save bookingID" id="cancelBtn">';
			               					 html += '<input type="hidden" id = "bookingId" value="'+response[i].bookingId+'"/>';
			               					 html += 'Cancel';
			               					 html += '</button>';
			               					 
			               					 html += '<button class="btn btn-default btn-success btn-change " type="button" id="changeBtn" onclick= "changeReservtn('+response[i].bookingId+','+response[i].reservationNumber+')">';
			               					 html += '<input type="hidden"  value="'+response[i].bookingId+'"/>';
			               					 html += 'Change</button>';
			               					 html += '</div>';
		              					 }else{
		              						 html += '<div class="col-xs-2 col-xs-2">';
		              						 html += '<div class="flag_icon"><span class="red">Cancelled</span>';
		              						 html += '</div>';
		              						 
		              						 html += '<div class="flag_icon">';
		              						 html += '<button class="btn btn-default btn-icon " >';
		              						 html += '<span class="glyphicon glyphicon-trash deleteRecrdID " aria-hidden="true">';
		              						 html += '<input type="hidden" id="deleteBtn" value="'+response[i].bookingId+'"/>';
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
					 function changeReservtn(id,reservtnNum){
						  $.ajax({
								url    : 'isSameHotel.htm?bookingId='+id,
								type   : 'POST',
								success : function(data){
									var hotelId = jQuery.parseJSON(data);
									
									 var html = '';
									 var btnsDiv = '';
									 //var chngeCanclBtns = '';
									 html += 'you are about to change your reservation : Reference number: '+reservtnNum+',  ';
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
									 btnsDiv += '<button type="button" class="btn btn-default btn-save" id="cancelReservation">';
									 //btnsDiv += '<input type="hidden" value="'+hotelId+'"/>';
									 btnsDiv += 'Cancel</button>';
									 btnsDiv += '</div>';
									 btnsDiv += '</div>';
									 $('#chngeAndCancelBtns').html(btnsDiv);
									 
									 $('#changeReservtn').show();
									 $('#myreservation_details').hide();
									 document.getElementById('fade').style.display='block';
								
								}
							}); 
						 
						 
						 
						 /* var html = '';
						 var chngeCanclBtns = '';
						 html += 'you are about to change your reservation : Reference number: '+reservtnNum+',  ';
						 html += 'please on clicking on any available reservation field your current reservation will be ';
						 html += 'transferred to selected reservation field after clicking on change button.';
						 $('#changeReservtnText').html(html);
						 
						 chngeCanclBtns += '<div class="form-group" style="padding: 10px;">';
						 
						 chngeCanclBtns += '<div class="col-sm-6" >';
						 chngeCanclBtns += '<button type="button" class="btn btn-default btn-save" id="changeReservation">';
						 chngeCanclBtns += '<input type="hidden" value="'+reservtnNum+','+id+'"/>';
						 chngeCanclBtns += 'Change</button>';
						 chngeCanclBtns += '</div>';
						 
						 chngeCanclBtns += '<div class="col-sm-6" >';
						 chngeCanclBtns += '<button type="button" class="btn btn-default btn-save" id="cancelReservation">';
						 chngeCanclBtns += 'Cancel</button>';
						 chngeCanclBtns += '</div>';
						 chngeCanclBtns += '</div>';
						 $('#chngeAndCancelBtns').html(chngeCanclBtns);
						 
						 $('#changeReservtn').show();
						 $('#myreservation_details').hide(); */
						 
							//document.location.href = "changeServiceReservation.htm?bookingID="+id;
						}// function close.
					 
					 function validatePhone(phneNum) {
						 //var a = document.getElementById(txtPhone).value;
						 var filter = /^[0-9-+]+$/;
						 if (filter.test(phneNum)) {
						 	return true;
						 }else {
						 	return false;
						 	}
						 }
					 
					 
			   function validateEmail(sEmail) {
					var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
					if (filter.test(sEmail)) {
					       return true;
					   }else {
					  return false;
					  }

					}
			   
			   function permanentRreservation(){
				   document.location.href = "permanentRreservation.htm";
			   }
			   
			   function backToHomePage(){
				   document.location.href = "backToHomePage.htm";
			   }
			   
			   
			   function getSelectedDayCount(){
		             var selectedCheckedoutDate=$("#selectedDate").val();
		             
		             var checkedindate = $("#checkedInvalue").val();
		             var checkOutVal = $('#selectedDate').val();
		             
		             if(checkOutVal != 0){
		            	 $.ajax({
				               url   : 'getCountNumberOfNights.htm', 
				               type  : 'get',
				               data:{checkoutdate:selectedCheckedoutDate,checkIndate:checkedindate},
				               success : function(data){
				               var noofNights=data;
				               $('#change_option_div').hide();
				               var html = '';
				            	   html += 'You have booked for '+ noofNights+' Night(s)';
				               $('#change_option').html(html);
				                
				               }
				             }); 
		             }else{
		            	 $('#change_option').empty();
		            	 $('#change_option_div').show();
		            	 
		             }
		              
		            }//getSelectedDayCount function closed.
			   
			   
		            function showErrorMsg(){
		            	  var errorMsg = 'please select checkout date';
						     $('#errorStatusMsg').empty();
						  	 $('#errorStatusMsg').append(errorMsg).fadeOut(4000);
		            }
			   
		            function changeHotel(hotelId){
		            	document.location.href = "changeHotel.htm?hotelId="+hotelId;
					}
		            
			   var arrLength = 0;
			   $(document).ready(function() {
				   
				   jQuery(document).on('click', "#changeReservation", function(){
					   var hotelId = jQuery(this).find('input').val();
					   changeHotel(hotelId);
				   });
				   
				   jQuery(document).on('click', "#cancelReservation", function(){
					   document.location.href="backToHomePage.htm";
				   });
				   
				   
				  /*Beginning --------- Ajax calls for arrived, not arrived, cancel reservations */
				   $('#cancelServiceReservation').click(function(){
                    	document.location.href ='returnToHme.htm'; 
                     });
				  
				  //cancel reservation
				  jQuery(document).on('click', ".bookingID", function(){
               			var bookingID = jQuery(this).find('input').val();
               			 
               			 $.ajax({
               				url   : 'cancelServiceReservation.htm?bookingID='+bookingID,
               				type  : 'POST',
               				success : function(data){
               					providerReservtnHistyr();
               					$('#statusInformtn').html(data);
    				 			$('#statusInformtn').show().fadeOut(4000);
               				}
               			});  //ajax function close.
               			
                     });
				  
				  jQuery(document).on('click', ".arrivedResrvtn", function(){
                 	 var deleteRcrdId = jQuery(this).find('input').val();
                 	
                 	$.ajax({
             				url   : 'deleteServiceReservation.htm?deleteRcrdId='+deleteRcrdId,
             				type  : 'POST',
             				success : function(data){
             					providerReservtnHistyr();
             					$('#statusInformtn').html(data);
  				 				$('#statusInformtn').show().fadeOut(4000);
             				}
             			}); //ajax function close.
                 	 
                  });
				  
				  jQuery(document).on('click', ".notVisitedReservtn", function(){
                 	 var deleteRcrdInfo = jQuery(this).find('input').val();
                 	 
                 	 var deleteArray = deleteRcrdInfo.split(',');
                 	 var deleteRcrdId = deleteArray[0];
                 	 var reservtnNo = deleteArray[1];
                 	 
                 	 
                 	var btnsDiv = '';
               	 	var html = '';
					 html += 'on clicking delete, reservation with reference number  '+reservtnNo+',  ';
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
				  jQuery(document).on('click', "#cancelReservationBtn", function(){
          			document.location.href="closeCnfrmtnPopUp.htm";
          		 });
				  
				  jQuery(document).on('click', "#dltReservtn", function(){
                 	 var deleteRcrdId = jQuery(this).find('input').val();
                 	  
                 	 $.ajax({
		      				url   : 'deleteServiceReservation.htm?deleteRcrdId='+deleteRcrdId,
		      				type  : 'POST',
		      				success : function(data){
		      				 var statusInformtn = data;
		      				  providerReservtnHistyr();
		      				    $('#statusInformtn').html(statusInformtn);
					 			$('#statusInformtn').show().fadeOut(4000);
		      				}
		      			});   //ajax function close.
                  });
				  
				  
				  
				//delete reservation. 
                  jQuery(document).on('click', ".deleteRecrdID", function(){
                 	 var deleteRcrdId = jQuery(this).find('input').val();
                 	 $.ajax({
            				url   : 'deleteServiceReservation.htm?deleteRcrdId='+deleteRcrdId,
            				type  : 'POST',
            				success : function(data){
            					providerReservtnHistyr();
            				$('#statusInformtn').html(data);
 				 			$('#statusInformtn').show().fadeOut(4000);
            				}
                 	 });
                  });
				   
				   
				  /*  Ending ---------- Ajax calls for visited, not visited, and cancel reservations */
				  
				  /* Stop sharing ajax call ------------- Starting*/
				  jQuery(document).on('click',"#stopShare", function(){
					 var bookingId = jQuery(this).find('input').value; 
				  });
				   
				   
				   
				   $('#cancelBtn').click(function(){
					   document.location.href = 'serviceProviderView.htm';
				   });
				 
				   jQuery(document).on('click', ".whiteBtn", function(){
                	   var userInfo = jQuery(this).find('input').val();
                	   var userValues = userInfo.split(',');
                	   var userId = userValues[0];                	   
                	   var hotelId = userValues[1];
                	   var guestUsrId = userValues[2];
                	   if(userId == 'null'){
                		   userId = 0;
                	   }else if(guestUsrId == 'null'){
                		   guestUsrId = 0;
                	   }
                	     
                	   $.ajax({
   				 		url   : 'addCustmerTOWhiteList.htm?userId='+userId+'&hotelId='+hotelId+'&guestUsrId='+guestUsrId,
   				 		type  : 'POST',
   				 		success:function(data){
	   				 		$('#statusInfo').html(data);
				 			$('#statusInfo').show().fadeOut(4000);
   				 		}
   				 	}); 
				   });
				   
				   jQuery(document).on('click', ".blackBtn", function(){
                	   var userInfo = jQuery(this).find('input').val();  
                	   var userValues = userInfo.split(',');
                	   var userId = userValues[0];
                	   var hotelId = userValues[1];
                	   var guestUsrId = userValues[2];
                	   if(userId == 'null'){
                		   userId = 0;
                	   }else if(guestUsrId == 'null'){
                		   guestUsrId = 0;
                	   }
                	   
                	   $.ajax({
   				 		url   : 'addCustmerToBlackList.htm?userId='+userId+'&hotelId='+hotelId+'&guestUsrId='+guestUsrId,
   				 		type  : 'POST',
   				 		success:function(data){
   				 		var response = data;
   				 			$('#statusInfo').html(response);
				 			$('#statusInfo').show().fadeOut(4000);
   				 		}
   				 	});  
				   });
				   
				   jQuery(document).on('click', ".paymentBtn", function(){
                	   var userInfo = jQuery(this).find('input').val();  
                	   var userValues = userInfo.split(',');
                	   var userId = userValues[0];
                	   var hotelId = userValues[1];
                	   var reservtnNum = userValues[2];
                	   var guestUsrId = userValues[3];
                	   if(userId == 'null'){
                		   userId = 0;
                	   }else if(guestUsrId == 'null'){
                		   guestUsrId = 0;
                	   }
                	   
                	   $.ajax({
   				 		url   : 'paidAmt.htm?userId='+userId+'&hotelId='+hotelId+'&reservtnNum='+reservtnNum+'&guestUsrId='+guestUsrId,
   				 		type  : 'POST',
   				 		success:function(data){
   				 			var status = data;
   				 			$('#statusInfo').html(status);
   				 			$('#statusInfo').show().fadeOut(4000);
   				 		}
   				 	});  
                	   
				   });
				   
				   jQuery(document).on('click', ".arrivedBtn", function(){
                	   var userInfo = jQuery(this).find('input').val();  
                	   var userValues = userInfo.split(',');
                	   var userId = userValues[0];
                	   var hotelId = userValues[1];
                	   var reservtnNum = userValues[2];
                	   var guestUsrId = userValues[3];
                	   if(userId == 'null'){
                		   userId = 0;
                	   }else if(guestUsrId == 'null'){
                		   guestUsrId = 0;
                	   }
                	 	
                	   $.ajax({
   				 		url   : 'arrived.htm?userId='+userId+'&hotelId='+hotelId+'&reservtnNum='+reservtnNum+'&guestUsrId='+guestUsrId,
   				 		type  : 'POST',
   				 		success:function(data){
   				 			var status = data;
   				 			$('#statusInfo').html(status);
   				 			$('#statusInfo').show().fadeOut(4000);
   				 		}
   				 	});  
                	   
				   });
				   
				    $('#paymentId').click(function(){
					   
					   var isPaid = 1;
					   $('#paymentId').val(isPaid);
					  
				   }); 
				   
				    $('#arrivedID').click(function(){
					   
					   var idArrived = 1;
					   $('#arrivedID').val(idArrived);
					  
				   }); 
				   
				    var selectedDate = '';
				    $('#selectedDate').change(function(){
						   selectedDate = $('#selectedDate').val();
					   });
				    
				   $('#reserveTable').click(function(e){
					   
					   var userName  = $('#userName').val();
					   var email     = $('#userEmail').val();
					   var phone 	 = $('#userPhoneNum').val();
					   var userNotes = $('#userNotes').val();
					   var checkedInDt = $('#checkedInvalue').val();
					   var checkedOutDt = $('#selectedDate').val();
					   var paymentVal = $('#paymentId').val();
					   var arrivedVal = $('#arrivedID').val();
					   
					   if($.trim(userName).length == 0){
						   var alertmsg = 'Please enter valid user Name';
              			  $('#useNameStatus').html(alertmsg).fadeOut(4000);
						   //e.preventDefault();
					   }
					   if(checkedOutDt == 0 ){
						   showErrorMsg();
						   e.preventDefault();
					   }
					   //validate phne num
					   if ($.trim(phone).length == 0) {
                			
                			var alertmsg = 'Please enter valid phone number';
                			 $('#PhoneStatus').html(alertmsg).fadeOut(4000);
                			 e.preventDefault();
                			}
					   if (validatePhone(phone)) {
               			//alert('Email is valid');
               			}else {
               				var alertmsg =  'Invalid Phone Number ';
               				$('#PhoneStatus').html(alertmsg).fadeOut(4000);
               				e.preventDefault();
               			}
					   
					   //validate email
					   if ($.trim(email).length == 0) {
						   var alertmsg = 'Please enter valid email address';
						   $('#EmailStatus').html(alertmsg).fadeOut(4000);
                			 e.preventDefault();
                			}
                		if (validateEmail(email)) {
                			}else {
                				var alertmsg = 'Invalid Email Address';
                				$('#EmailStatus').html(alertmsg).fadeOut(4000);
                				e.preventDefault();
                			}
                		
					   if(paymentVal == ''){
						   paymentVal = 0;
					   }
					   if(arrivedVal == ''){
						   arrivedVal = 0;
					   }
					   
					   var json = '{"userName":"'+userName+'","email":"'+email+'","phone":"'+phone+'","userNotes":"'+userNotes+'","checkedInDt":"'+checkedInDt+'","checkedOutDt":"'+checkedOutDt+'","paymentVal":"'+paymentVal+'","arrivedVal":"'+arrivedVal+'","roomId":"'+roomId+'","categoryId":"'+categoryId+'"}';
					   
					   if(checkedOutDt != 0 ){
						   
						   $.ajax({
								  url   : 'saveManualReservation.htm',
								  type  : 'POST',
								  data  : {json:json},
								  success : function(data){
									  var response = jQuery.parseJSON(data);
									  var referenceNum = '';
									  var hotelDetails = '';
									  var checkInOutDts = '';
									  var userInfrmtn = '';
									  referenceNum += '<div class="alert alert-success" role="alert" style="padding:5px 12px;margin-bottom:5px;">';
									  referenceNum += '<strong><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> Success!</strong> You successfully made reservation.';
									  referenceNum += '</div>';
									  referenceNum += ' <p class="center" >Reference Number : <b>'+response.reservationNumber+'</b></p>';
									  $('#referenceNumDiv').html(referenceNum);
									  
									  hotelDetails += '<p>';
									  hotelDetails += ''+response.hotelName+'<br/>';
									  hotelDetails += ''+response.hotelAddress+'<br/>';
									  hotelDetails += ''+response.city+', '+response.hotelPhneNumber+'';
									  hotelDetails += '</p>';
									  $('#hotelDetails').html(hotelDetails);
									  
									  checkInOutDts += '<p>';
									  checkInOutDts += 'Category :'+response.category+', Room No :'+response.roomId+'<br/>';
									  checkInOutDts += 'CheckIn Date :'+response.chckedInDt+'<br/>';
									  checkInOutDts += 'CheckOut Date :'+response.checkOutDt+'<br/>';
									  checkInOutDts += '<span style="float:left;">'+response.numberOfDays+' Night(s)</span><br/>';
									  checkInOutDts += '</p>';
									  $('#checkInOutDts').html(checkInOutDts);
									  
									  userInfrmtn += '<p>';
									  userInfrmtn += ''+response.userName+'<br/>';
									  userInfrmtn += ''+response.email+'<br/>';
									  userInfrmtn += ''+response.phoneNumber+'<br/>';
									  userInfrmtn += '</p>';
									  $('#userInfrmtn').html(userInfrmtn);
									  $('#myreservation_manual').hide();
									  $('#adminReservation_confirm').show();
									  document.getElementById('fade').style.display='block';
									 
									  
								  }//success function clsoe.
							   });  
					   }
					    
				   });				   
				
				   /* var chngePopup = $("#chngePopup").val(); */
				   var dateVal = '';
				   var roomId = '';
				   var availCnt = '';
				   var categoryId = '';
				   var usrId = '';
				   var reservationNumber = '';
				   var dateVal = '';
				   var guestUsr = '';
				   jQuery(document).on('click', ".room_info", function(){
					    var availabilityID = jQuery(this).find('input').val();
             			var userInfo = availabilityID.split(',');
             			dateVal = userInfo[0];
             			roomId = userInfo[1];
             			availCnt = userInfo[2];
             			categoryId = userInfo[3];
             			usrId = userInfo[4];
             			reservationNumber = userInfo[5];
             			dateVal = userInfo[6];
             			guestUsr = userInfo[7];
             			
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
                                	
                                	userDetails += '<button class="btn btn-default btn-blank whiteBtn" type="button" >';
                                	userDetails += '<input type="hidden" value="'+response.userId+','+response.hotelID+','+response.guestUserId+'"></input>'; 
                                	userDetails += '<img src="images/whitelist.png" title="add to white list"/>';
                                	userDetails += '</button> &nbsp;';
                                	
                                	userDetails += '<button class="btn btn-default btn-blank blackBtn"  type="button" >';
                                	userDetails += '<input type="hidden" value="'+response.userId+','+response.hotelID+','+response.guestUserId+'"></input>';
                                	userDetails += '<img src="images/blacklist.png" title="add to black list"/>';
                                	userDetails += '</button> &nbsp;';
                                	
                                	userDetails += '<button class="btn btn-default btn-blank paymentBtn" type="button">';
                                	userDetails += '<input type="hidden" value="'+response.userId+','+response.hotelID+','+reservationNumber+','+response.guestUserId+'"></input>';
                                	userDetails += '<img src="images/payment.png" title="payment paid"/>';
                                	userDetails += '</button> &nbsp;';
                                	
                                	userDetails += '<button class="btn btn-default btn-blank arrivedBtn" type="button" >';
                                	userDetails += '<input type="hidden" value="'+response.userId+','+response.hotelID+','+reservationNumber+','+response.guestUserId+'"></input>';
                                	userDetails += '<img src="images/arrived.png" title="arrived"/>';
                                	userDetails += '</button>';
                                	$('#userDetailDiv').html(userDetails);
                                	
                                	var notes = response.notes;
                                	 //appending notes to texarea
                                	  $('#noteID').empty();
                                	 $('#noteID').append(notes);
                                	 $('#noteID').prop('readonly', true);
                                	 
                                	 //showing reservation popUP.
                                	$('#myreservation').show();
                                	document.getElementById('fade').style.display='block';
                                }// success close.
                 			}); // ajax close.
                 			
             			}else{
             				
             				$.ajax({
                                url: "getHotelDetails.htm",
                                type: 'GET',
                                success: function(data){
                                	var hotelDetails  = jQuery.parseJSON(data);
                                	var hotelInfo = '';
    	             				hotelInfo += '<p>';
    	             				hotelInfo += ''+hotelDetails.hotelName+'<br/>';
    	             				hotelInfo += ''+hotelDetails.hotelAddress+'<br/>';
    	             				hotelInfo += ''+hotelDetails.phoneNumber+'';
    	             				hotelInfo += '</p>';
    	             				$('#hotelInfo').html(hotelInfo);
                                }
                             });
             				
             				//this ajax call is used to check isChangeReservation or not, that means
             				// if user already  had a manual reservation, If he asks for change reservation
             				// this call w'll check wheather user had already reservation. If yes, it just get that 
             				// user details and shows it on username,email and phone number in non-editable format.
             				$.ajax({
             					url      : 'isChangeReservtn.htm',
             					type     : 'POST',
             					success  : function(data){
             						var response = jQuery.parseJSON(data);
             						if(response.userName != 'null' && response.email != 'null' && response.phoneNumber ){
             						$('#userName').val(response.userName);
             						$('#userEmail').val(response.email);
             						$('#userPhoneNum').val(response.phoneNumber);
             						$('#userName').prop('readonly', true);
             						$('#userEmail').prop('readonly', true);
             						$('#userPhoneNum').prop('readonly', true);
             						}
             					}
             				});
             				
             				$.ajax({
             					url  : 'manualReservation.htm',
             					type : 'POST',
             					data : {date:dateVal,roomId:roomId,availcnt:availCnt,categoryId:categoryId},
             					success:function(data){
             						var roomAvailList = jQuery.parseJSON(data);
            						var roomAvailLen = roomAvailList.length;
            						$('#change_option').empty();
             						var checkInOut = '';
    	             				checkInOut += '<p>';
    	             				if(roomAvailLen != 0){
    	             					for(i in roomAvailList ){
    	             						var checkIn = roomAvailList[i].checkedInDate;
                                          	if(i == 0){
                                          		checkInOut += 'Checkin Date :  '+ roomAvailList[i].checkedInDate+ '</br>';
                                          		checkInOut += '<input type="hidden" value="'+roomAvailList[i].checkedInDate+'" id="checkedInvalue" name="checkedInvalue">';
                                          	}
                                          } 
    	             				}else if(roomAvailLen == 0){
                                       		checkInOut += 'Checkin Date :  '+ dateVal+ '</br>';
                                       		checkInOut += '<input type="hidden" value="'+dateVal+'" id="checkedInvalue" name="checkedInvalue">';
    	             				}
    	             				 checkInOut += '<select name="checkedOutDate" class="form-control" id="selectedDate" onchange="getSelectedDayCount()">';
    	             				 
    	             				if(roomAvailLen != 0){
    	             					checkInOut += '<option value="'+0+'">'+'Select CheckOut Date'+'</option>'; 
    	             					$(roomAvailList).each(function(i,res) {
                                       	 checkInOut += '<option value="'+res.checkedOutDate+'">'+res.checkedOutDate+'</option>';                                  	
                 						 });
    	             				}else if(roomAvailLen == 0){
    	             					 checkInOut += '<option value="'+dateVal+'">'+dateVal+'</option>'; 
    	             				}
    	             				 
                                     checkInOut += '</select>';
                                     checkInOut += '</p>';                                     
    	             				 $('#checkInAndOutDiv').html(checkInOut);
    	             				 $('#statusMsg').html();
    	             				 $('#myreservation_manual').show();	    	             				 
    	             				 document.getElementById('fade').style.display='block';
                                     
             					}
             				});// ajax function close.
             						 
             			}//else block close.
             			
				   });
				   
				   //getting selected date values.
                   jQuery(document).on('click', ".hiddenSpan", function(){
                	   
                	   var selectedDate = jQuery(this).find('input').val(); 
                	   
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
						var blckDate = '';
						
						if(noOfDays == ''){
							noOfDays =0;
						}
						
						$('#carouselDiv').empty();
						$.ajax({
							url   :'getSelectedDateAvailRoomsForProvider.htm',
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
                           		if('green' == response[i].colourCode &&  
  	                           			response[i].availcnt >= noOfDays && response[i].availcnt != 0){
     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
     	                           		html += '<div class="cell_room_admin booked">';
     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
     	                           		html += ''+response[i].categoryName +'<br/>';
     	                           		html += ''+response[i].availcnt;
     	                           		html += '</a>';
     	                           		html += '</div>';
     	                           		html += '</div>';
 	                           		}else if ('gray' == response[i].colourCode){
 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
 	                           		html += '<div class="cell_room_admin reserved">';
 	                           		html += '<div class="cell_room_addition-info">';
	 	                           	if(response[i].paid == true){
	 	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
	                        			}
	     	                            if(response[i].arrived == true){
	     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
		                           			}
		     	                            if(response[i].notes != ''){
		     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
		                           			}
 	                           		html += '</div>';
 	                           		html += '<a href="javascript:void(0)" class="room_info" >';
 	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
 	                           		html += ''+response[i].categoryName +'<br/>';
 	                           		html += ''+response[i].availcnt;
 	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
 	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
 	                           		html += '</a>';
 	                           		html += '</div>';
 	                           		html += '</div>';
 	                           		}else{
 	                           		var val = 'X';
	 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
	 	                           		html += '<div class="cell_room_admin ">';
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
	                       	    var dateVal = selectedDate.slice(0,2);
	                       	 	selctdVal = selectedDate;
	                       		fullDate = fullDate.replace('/','-');
                       		    fullDate = fullDate.replace('/','-');
                       		    var currDate = $('#currentDate span').text();
	                       		
                       		 displySearchDatesListOnDatesBar(selctdVal,blckDate);
                       		   
                       		 //showBlackdate(blckDate,selctdVal,dateVal,fullDate,currDate);
		                        $('#currentDate span').text(selectedDate);
		                        
							}
						});
                	   
                   });// jQuery. hidden span close.
				   
				   $("select#category").change(function() {
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
		                             	   $('#roomNo').append("<option value="+ 0 +">"+"Select Room"+"</option>");
		                                    for(i in response){
		                                 	   $('#roomNo').append("<option value="+ response[i] +">"+response[i]+"</option>");
		                                    } 
		                           		}
		                          });//ajax functio close.
		                          
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
	 	                          						&& currDate.slice(3,5) == response[i].selecteDt.slice(3,5)){
	 	                          					html += '<span class="cal_date current_date hiddenSpan">';
	 	                          					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                          					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                          				}else{
	 	                          				  /* var blueDtval = '';
	 	                      					  console.log('user booked recrds :'+userBkdRecrds);
	 	                      					  for(var record in userBkdRecrds){	
	 	                      						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)){
	 	                      							html += '<span class="cal_date loggedInUser hiddenSpan">';
	 	                              					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                              					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                              					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	 	                      						}
	 	                      					} */ // for (userBkdRecrds)close.
	 	                      					
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
	 	                      					
	 	                      					//if(blueDtval != response[i].selecteDt.slice(0,2)){
	 	                      						if(response[i].booked == response[i].noOFRooms){
	 	                          						html += '<span class="cal_date hiddenSpan">';
	 	                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                          					}else  {
	 	                          						html += '<span class="cal_date green hiddenSpan">';
	 	                          						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	 	                          						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	 	                          					}
	 	                      					//}
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
		                          
					   }// if close.
					  
					   if(catVal != 0 && currentDate != null){
    	               		var noOfDays = $('#noOfDays').val();
    	               		$.ajax({
                         		url : 'getRoomInfoByCatgryToProvider.htm?type='+catVal+'&currDarte='+fullDate,
                         		type: 'POST',
                         		success:function(data){
                        			var response = jQuery.parseJSON(data);
                         			$('#roomInfoDiv').empty();
                         			var html = '';
                                    	html += '<div class="item active">';
                                    	html += ' <div class="carousel-caption">';
         	                           	for(i in response){
	         	                           	if('green' == response[i].colourCode){
		     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
		     	                           		html += '<div class="cell_room_admin booked">';
		     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
		     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
		     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
		     	                           		html += ''+response[i].categoryName +'<br/>';
		     	                           		html += ''+response[i].availcnt;
		     	                           		html += '</a>';
		     	                           		html += '</div>';
		     	                           		html += '</div>';
		 	                           		}else{
		 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room_admin reserved">';
	     	                           		html += '<div class="cell_room_addition-info">';
		     	                           	if(response[i].paid == true){
		     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
	 	                           			}
			     	                            if(response[i].arrived == true){
			     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
		 	                           			}
				     	                            if(response[i].notes != ''){
				     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
				                           			}
	     	                           		html += '</div>';
	     	                           		
	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	     	                           		html += ''+response[i].categoryName +'<br/>';
	     	                           		html += ''+response[i].availcnt;
	     	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
	     	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
	     	                           		html += '</a>';
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
						   
						   $('#noOfDays').val('');
						   var noOfDays = $('#noOfDays').val();
	  	               		 if(noOfDays == ''){
	  	               			noOfDays = 0;
	  	               		 }
	  	               		 $('#roomNo').val('');
	  	               		 $('#roomNo').empty();
	  	               		 
	  	               	  
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
	                           					/* var blueDtval = '';
	                           					  console.log('user booked recrds :'+userBkdRecrds);
	                           					  for(var record in userBkdRecrds){	
	                           						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)){
	                           							html += '<span class="cal_date loggedInUser hiddenSpan">';
	                                   					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                                   					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                                   					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	                           						}
	                           					} */ // for (userBkdRecrds)close.
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
	                           					//if(blueDtval != response[i].selecteDt.slice(0,2)){
	                           						if(response[i].booked == response[i].noOFRooms){
	                               						html += '<span class="cal_date hiddenSpan">';
	                               						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                               						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                               					}else  {
	                               						html += '<span class="cal_date green hiddenSpan">';
	                               						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                               						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                               					}
	                           					//}
	                           					
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
                           
                       
                           
                            //get rooms list while page loading.
                             var count = '';
		                     var blckDate = '';
		                     var currDate = $('#currentDate span').text();
		                     var selctdVal = currDate;
		                     var dateVal = selctdVal.slice(0,2);
                            $('#carouselDiv').empty();
                            $.ajax({
                                 url: 'getRoomsOnCurrentDate.htm?noOfDays='+noOfDays,
                                 type: 'POST',
                                 success: function(data){
                                var response =  jQuery.parseJSON(data);
                              	var html = '';
                               	html += '<div class="item active">';
                               	html += ' <div class="carousel-caption">';
                               	html += '<div class="ajax_loader"></div>';
    	                           	for(i in response){
    	                           		if('green' == response[i].colourCode){
	     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room_admin booked">';
	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	     	                           		html += ''+response[i].categoryName +'<br/>';
	     	                           		html += ''+response[i].availcnt;
	     	                           		html += '</a>';
	     	                           		html += '</div>';
	     	                           		html += '</div>';
		 	                           		}else{
		 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
		 	                           		html += '<div class="cell_room_admin reserved">';
		 	                           		html += '<div class="cell_room_addition-info">';
			 	                           	if(response[i].paid == true){
		     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
	 	                           			}
			     	                            if(response[i].arrived == true){
			     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
		 	                           			}
				     	                            if(response[i].notes != ''){
				     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
				                           			}
		 	                           		html += '</div>';
		 	                           		
		 	                           		html += '<a href="javascript:void(0)" class="room_info" >';
		 	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
		 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
		 	                           		html += ''+response[i].categoryName +'<br/>';
		 	                           		html += ''+response[i].availcnt;
		 	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
		 	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
		 	                           		html += '</a>';
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
                           		
                           		$('.ajax_loader').removeClass();
                             }
                         });//ajax function close.
                           
                          //removing ajax loader.
                  		 
					   }// if close.
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
	                       	 
				   });//select category on change close.
				   
				   $('#noOfDays').change(function(event){
           			
					   
					var selectedCategory = $("#category").val();
                   	var currDate = $('#currentDate span').text();
                   	/* currDate = currDate.replace('/','-');
                   	currDate = currDate.replace('/','-'); */
                 		var roomNo = $('#roomNo').val();
                 		var noOfDays = $('#noOfDays').val();
                 		$('#carouselDiv').empty();
                     	$('#roomInfoDiv').empty();
                     	var dateVal = $('#currentDate span').text();
                     	
                     	if(noOfDays == ''){
                     		noOfDays = 0;
                     	}
                     	
                     	 if(selectedCategory == 0){
                     		
		                       /*  var userId ='';
									$.ajax({
										url : 'getGreenUserDetails.htm',
										type : 'POST',
										success: function(data){
											var userDetails = jQuery.parseJSON(data);
											userId = userDetails.userId;
										}
								});  */  		
                  				
								//get rooms list while page loading.
	                            $('#carouselDiv').empty();
	                            $.ajax({
	                                 url: 'getSelectedDateAvailRoomsForProvider.htm',
	                                 type: 'POST',
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
	    	                           		if('green' == response[i].colourCode && response[i].availcnt >= 1 &&
		     	                           			response[i].availcnt >= noOfDays ){
		     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
		     	                           		html += '<div class="cell_room_admin booked">';
		     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
		     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
		     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
		     	                           		html += ''+response[i].categoryName +'<br/>';
		     	                           		html += ''+response[i].availcnt;
		     	                           		html += '</a>';
		     	                           		html += '</div>';
		     	                           		html += '</div>';
			 	                           		}else if('gray' == response[i].colourCode){
			 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
			 	                           		html += '<div class="cell_room_admin reserved">';
			 	                           		html += '<div class="cell_room_addition-info">';
				 	                           	if(response[i].paid == true){
			     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
		 	                           			}
				     	                            if(response[i].arrived == true){
				     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
			 	                           			}
					     	                            if(response[i].notes != ''){
					     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
					                           			}
			 	                           		html += '</div>';
			 	                           		
			 	                           		html += '<a href="javascript:void(0)" class="room_info" >';
			 	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
			 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
			 	                           		html += ''+response[i].categoryName +'<br/>';
			 	                           		html += ''+response[i].availcnt;
			 	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
			 	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
			 	                           		html += '</a>';
			 	                           		html += '</div>';
			 	                           		html += '</div>';
		 	                           		}else{
		 	                           		
		 	                           			var val = 'X';
		     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
		     	                           		html += '<div class="cell_room_admin ">';
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
	                           		
	                           		var dateVal = currDate.slice(0,2);
		                       		fullDate = fullDate.replace('/','-');
	                          		fullDate = fullDate.replace('/','-');
	                          		
	                          		showDatesOnDateBar();
	                          		
	                             }
	                         });//ajax function close.
			                     return false;
              		}else if(selectedCategory != 0 && roomNo == 0 ){
              			
                 		//getting  userId 
                 		/* var userId ='';
						$.ajax({
							url : 'getGreenUserDetails.htm',
							type : 'POST',
							success: function(data){
								var userDetails = jQuery.parseJSON(data);
								userId = userDetails.userId;
							}
						}); */
						
						var blckDate = '';
						//get rooms by room count based on category.
                 		$.ajax({
                 				url : 'getAvailRoomsByDayCountAndCategory.htm',
                 				type : 'POST',
                 				data :{selectedCategory:selectedCategory,noOfDays:noOfDays,currentDate:dateVal},
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
         	                           		if('green' == response[i].colourCode && response[i].availcnt >= 1 &&
		     	                           			response[i].availcnt >= noOfDays){
		     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
		     	                           		html += '<div class="cell_room_admin booked">';
		     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
		     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
		     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
		     	                           		html += ''+response[i].categoryName +'<br/>';
		     	                           		html += ''+response[i].availcnt;
		     	                           		html += '</a>';
		     	                           		html += '</div>';
		     	                           		html += '</div>';
		 	                           		}else if('gray' == response[i].colourCode){
			 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
			 	                           		html += '<div class="cell_room_admin reserved">';
			 	                           		html += '<div class="cell_room_addition-info">';
				 	                           	if(response[i].paid == true){
			     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
		 	                           			}
				     	                            if(response[i].arrived == true){
				     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
			 	                           			}
					     	                            if(response[i].notes != ''){
					     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
					                           			}
			 	                           		html += '</div>';
			 	                           		
			 	                           		html += '<a href="javascript:void(0)" class="room_info" >';
			 	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
			 	                           		html += 'Room No:'+response[i].roomId+'<br/>';
			 	                           		html += ''+response[i].categoryName +'<br/>';
			 	                           		html += ''+response[i].availcnt;
			 	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
			 	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
			 	                           		html += '</a>';
			 	                           		html += '</div>';
			 	                           		html += '</div>';
	 	                           			}else{
	 	                           			var val = 'X';
		     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
		     	                           		html += '<div class="cell_room_admin ">';
		     	                           		html += '<p class="room_info red" >';
		     	                           		html += '<br/>';
		     	                           		html += ''+val+'';
		     	                             	html += '<br/>';
		     	                             	html += '</p>';
		     	                           		html += '</div>';
		     	                           		html += '</div>';
		     	                           		
		     	                           		
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
                                		var dateVal = currDate.slice(0,2);
    		                       		fullDate = fullDate.replace('/','-');
    	                          		fullDate = fullDate.replace('/','-');
    	                          		
    	                          		showDatesOnDateBar();
                                		
                 				}
                 				
                 			}); 
                 		}
                 		
           		});//no of days change function closed.
           		
           		
           		
           		jQuery(document).on('click', ".hideDt", function(){
             	   var selectedDate = jQuery(this).find('input').val();
             	   $('#currentDateVal').html(selectedDate);
             	   
           		});
				   
				   $('#roomNo').change(function(){
					   
					   $('#noOfDays').val('');
                 	   $('#noOfDays').prop('readonly', true);
                 	  
                 	   //get noOfDays val. If value is '' then assing values = 0.
                       var noOfDays = $('#noOfDays').val();
                        if(noOfDays == ' '){
                       	 noOfDays = 0;
                        }
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
                   		
                   		 
                   		 var currDtVal = fullDate.replace('-','/');
                   			currDtVal = currDtVal.replace('-','/');
                   		 
                   		 
                   		if(roomNo != 0){
                   		//get dates and shows it on dates bar.
                      		$.ajax({
                          		url   : 'getDatesOnSelectedRoomNum.htm',
                          		type  : 'POST',
                          		data : {cat:selectedCategory,roomNo:roomNo,currDate:fullDate},
                          		success:function(data){
                          			var response = jQuery.parseJSON(data);
                          			$('#prmryDatesList').hide();
                          			 var html = '';
                          			 html += '<div class="piece">';
                          			 html += '<div class="small_square">';
                          			 
                          			var monthsArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
                          			for(j in response){
                          				 if(j == 0){
                          					 var monthNum = response[j].roomAvailDate.slice(3,5);
                          					 monthNum = (monthNum-1) ;
                          					 jQuery.each( monthsArray, function( i, mnthName ) {
                          							if(monthNum == i){
                          								html += '<span class="month_title">'+mnthName+'</span>';
                          							}
                          						});
                          				 	}
                          				} // for close.

                          				for(i in response){
                          					if(response[i].roomAvailDate.slice(0,2) == 01 || 
                                					 response[i].roomAvailDate.slice(0,2) == 15 &&
                                  					response[i].roomAvailDate.slice(3,5) >= fullDate.slice(3,5) ){
                        						var monthNum = response[i].roomAvailDate.slice(3,5);
                                    			 monthNum = (monthNum-1) ;
                                    			 jQuery.each( monthsArray, function( i, mnthName ) {
                                               	 	if(monthNum == i){
                                               	 		html += '<span class="month_title">'+mnthName+'</span>';
                                               	 	}
                                               	});
                        					}
	                          				if(currDtVal == response[i].roomAvailDate){
	                          					html += '<span class="cal_date current_date hideDt">';
	                          					html += '<input type="hidden" value="'+response[i].roomAvailDate+'" />';
	                          					html += ''+response[i].roomAvailDate.slice(0,2)+'</span>';
	                          				}else{
	                          					
	                          					if(response[i].colourCode == 'green'){
	                          						html += '<span class="cal_date green hideDt">';
	                          						html += '<input type="hidden" value="'+response[i].roomAvailDate+'" />';
	                          						html +=''+response[i].roomAvailDate.slice(0,2)+'</span>';
	                          					}else {
	                          						html += '<span class="cal_date hideDt">';
	                          						html += '<input type="hidden" value="'+response[i].roomAvailDate+'" />';
	                          						html += ''+response[i].roomAvailDate.slice(0,2)+'</span>';
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
                          	
                          //get rooms list while page loading.
                            $('#carouselDiv').empty();
                          	 $.ajax({
                                   url: 'getRoomsAvailOnCurrentDateToProvider.htm',
                                   type: 'POST',
                                   data : {cat:selectedCategory,roomNo:roomNo,currDate:fullDate},
                                   success: function(data){
                                  var response =  jQuery.parseJSON(data);
                                	var html = '';
                                 	html += '<div class="item active">';
                                 	html += ' <div class="carousel-caption">';
      	                           	for(i in response){
	                           		if('green' == response[i].colourCode){
	     	                           		html += '<div class="col-xs-3  col-xs-3-fixed nopadding book_info">';
	     	                           		html += '<div  class="cell_room_admin_week booked">';
	     	                           		html += '<a href="javascript:void(0)" class="room_info" style="padding-top: 6px;">';
	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	     	                           		html += ''+response[i].categoryName +'<br/>';
	     	                           		html += ''+response[i].availcnt +'<br/>';
	     	                           		html += ''+response[i].roomAvailDate+'<br />';
	     	                           		html += '</a>';
	     	                           		html += '</div>';
	     	                           		html += '</div>';
	 	                           		}else{
		 	                           		html += '<div class="col-xs-3  col-xs-3-fixed nopadding book_info">';
	     	                           		html += '<div class="cell_room_admin_week reserved">';
	     	                           		html += '<div class="cell_room_addition-info">';
	     	                           	if(response[i].paid == true){
	     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
		                           			}
		     	                            if(response[i].arrived == true){
		     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
	 	                           			}
			     	                            if(response[i].notes != ''){
			     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
			                           			}
     	                           		html += '</div>';
     	                           		
     	                           		html += '<a href="javascript:void(0)" class="room_info" style="padding-top: 10px;">';
     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
     	                           		html += ''+response[i].categoryName +'<br/>';
     	                           		html += ''+response[i].availcnt;
     	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
     	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
     	                           		html += '</a>';
     	                           		html += '</div>';
     	                           		html += '</div>';
	 	                           		}
	                           		}
			                       	html += '</div>';
			                   		html += '</div>';
			                   		$('#roomInfoDiv').empty();
			                   		$('#carouselDivPrimary').hide();
			                   		$('#carouselDiv').empty();
			                   		$('#carouselDiv').append(html);
			                   		$('#carouselDiv').show(); 
		                      	}
                           });//ajax function close.
                           
                          	 var currentDate = fullDate;
                             currentDate = currentDate.replace('-','/');
                             currentDate = currentDate.replace('-','/');
                             $('#currentDateVal').html(currentDate);
    	                     $('#currentDateVal').show(); 
    	                     
                   		} else{
                   			$('#noOfDays').val('');
            				$('#noOfDays').prop('readonly', false);
            				
            				var fullDate = $('#currentDate span').text();
            				var catVal = $('#category').val();
            				
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
	       	                  					/* var blueDtval = '';
	       	                  					  console.log('user booked recrds :'+userBkdRecrds);
	       	                  					  for(var record in userBkdRecrds){	
	       	                  						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)){
	       	                  							html += '<span class="cal_date loggedInUser hiddenSpan">';
	       	                          					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	       	                          					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	       	                          					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	       	                  						}
	       	                  					} // for (userBkdRecrds)close. */
	       	                  					
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
	       	                  					
	       	                  					//if(blueDtval != response[i].selecteDt.slice(0,2)){
	       	                  						if(response[i].booked == response[i].noOFRooms){
	       	                      						html += '<span class="cal_date hiddenSpan">';
	       	                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	       	                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	       	                      					}else  {
	       	                      						html += '<span class="cal_date green hiddenSpan">';
	       	                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	       	                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	       	                      					}
	       	                  					//}
	       	                  					
	       	                  				}// else close.
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
		                    		url : 'getRoomInfoByCatgryToProvider.htm?type='+catVal+'&currDarte='+fullDate,
		                    		type: 'POST',
		                    		success:function(data){
		                   			var response = jQuery.parseJSON(data);
		                    			$('#roomInfoDiv').empty();
		                    			var html = '';
		                               	html += '<div class="item active">';
		                               	html += ' <div class="carousel-caption">';
		    	                           	for(i in response){
		        	                           	if('green' == response[i].colourCode){
			     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
			     	                           		html += '<div class="cell_room_admin booked">';
			     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
			     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
			     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
			     	                           		html += ''+response[i].categoryName +'<br/>';
			     	                           		html += ''+response[i].availcnt;
			     	                           		html += '</a>';
			     	                           		html += '</div>';
			     	                           		html += '</div>';
			 	                           		}else{
			 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
		    	                           		html += '<div class="cell_room_admin reserved">';
		    	                           		html += '<div class="cell_room_addition-info">';
			    	                           		if(response[i].paid == true){
				     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
			 	                           			}
					     	                            if(response[i].arrived == true){
					     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
				 	                           			}
						     	                            if(response[i].notes != ''){
						     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
						                           			}
		    	                           		html += '</div>';
		    	                           		
		    	                           		html += '<a href="javascript:void(0)" class="room_info" >';
		    	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
		    	                           		html += 'Room No:'+response[i].roomId+'<br/>';
		    	                           		html += ''+response[i].categoryName +'<br/>';
		    	                           		html += ''+response[i].availcnt;
		    	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
		    	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
		    	                           		html += '</a>';
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
						     	
                   		} //else close.(room no change)
                   		
				   });//room number.change function close.
				   
				   $('#currentDate').dblclick(function(){
                  	 
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
    	                  					/* var blueDtval = '';
    	                  					  console.log('user booked recrds :'+userBkdRecrds);
    	                  					  for(var record in userBkdRecrds){	
    	                  						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)){
    	                  							html += '<span class="cal_date loggedInUser hiddenSpan">';
    	                          					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
    	                          					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
    	                          					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
    	                  						}
    	                  					} // for (userBkdRecrds)close. */
    	                  					
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
    	                  					
    	                  					//if(blueDtval != response[i].selecteDt.slice(0,2)){
    	                  						if(response[i].booked == response[i].noOFRooms){
    	                      						html += '<span class="cal_date hiddenSpan">';
    	                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
    	                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
    	                      					}else  {
    	                      						html += '<span class="cal_date green hiddenSpan">';
    	                      						html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
    	                      						html += ''+response[i].selecteDt.slice(0,2)+'</span>';
    	                      					}
    	                  					//}
    	                  					
    	                  				}// else close.
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
                           url: 'doubleclick.htm?noOfDays='+noOfDays,
                           type: 'POST',
                           success: function(data){
                            var response =  jQuery.parseJSON(data);
                         	var html = '';
                         	html += '<div class="item active">';
                         	html += ' <div class="carousel-caption">';
	                           	for(i in response){
	                           		if('green' == response[i].colourCode){
     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
     	                           		html += '<div class="cell_room_admin booked">';
     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
     	                           		html += ''+response[i].categoryName +'<br/>';
     	                           		html += ''+response[i].availcnt;
     	                           		html += '</a>';
     	                           		html += '</div>';
     	                           		html += '</div>';
 	                           		}else{
	 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
     	                           		html += '<div class="cell_room_admin reserved">';
     	                           		html += '<div class="cell_room_addition-info">';
	     	                           	if(response[i].paid == true){
	     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
		                           			}
		     	                            if(response[i].arrived == true){
		     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
	 	                           			}
			     	                            if(response[i].notes != ''){
			     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
			                           			}
     	                           		html += '</div>';
     	                           		
     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
     	                           		html += ''+response[i].categoryName +'<br/>';
     	                           		html += ''+response[i].availcnt;
     	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
     	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
     	                           		html += '</a>';
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
	                  					/* var blueDtval = '';
	                  					  console.log('user booked recrds :'+userBkdRecrds);
	                  					  for(var record in userBkdRecrds){	
	                  						  if(userBkdRecrds[record].userBkdDate.slice(0,2) == response[i].selecteDt.slice(0,2)){
	                  							html += '<span class="cal_date loggedInUser hiddenSpan">';
	                          					html += '<input type="hidden" class="hiddenDt" value="'+response[i].selecteDt+'"></input>';
	                          					html += ''+response[i].selecteDt.slice(0,2)+'</span>';
	                          					blueDtval = userBkdRecrds[record].userBkdDate.slice(0,2);
	                  						}
	                  					} // for (userBkdRecrds)close. */
	                  					
	                  					//if(blueDtval != response[i].selecteDt.slice(0,2)){
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
	                  					//}
	                  					
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
	                 
	                  
	                  //get noOfDays val. If value is '' then assing values = 0.
	                  var noOfDays = $('#noOfDays').val();
	                  if(noOfDays == ''){
	                 	  noOfDays = 0;
	                  }
	                  
	                  //get rooms list while page loading.
	                  
	                  $('#carouselDiv').empty();
	                	 $.ajax({
	                         url: 'getRoomsOnCurrentDate.htm?noOfDays='+noOfDays,
	                         type: 'POST',
	                         success: function(data){
	                        var response =  jQuery.parseJSON(data);
	                    
	                      	var html = '';
	                       	html += '<div class="item active">';
	                       	html += '<div class="carousel-caption">';
	                           	for(i in response){
	                           		if('green' == response[i].colourCode){
	     	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room_admin booked">';
	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+'"></input>';
	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	     	                           		html += ''+response[i].categoryName +'<br/>';
	     	                           		html += ''+response[i].availcnt;
	     	                           		html += '</a>';
	     	                           		html += '</div>';
	     	                           		html += '</div>';
	 	                           		}else{
	 	                           			
		 	                           		html += '<div class="col-md-3 col-sm-3 col-xs-3 nopadding book_info">';
	     	                           		html += '<div class="cell_room_admin reserved">';
	     	                           		html += '<div class="cell_room_addition-info">';
			     	                           	if(response[i].paid == true){
			     	                           		html += '<span class="glyphicon right" aria-hidden="true">P</span>';
		 	                           			}
				     	                            if(response[i].arrived == true){
				     	                        	  	html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin: 0px 10px 0px;"></span>';
			 	                           			}
					     	                            if(response[i].notes != ''){
					     	                        	 	html +=	'<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
					                           			}
	     	                           		html += '</div>';
	     	                           		
	     	                           		html += '<a href="javascript:void(0)" class="room_info" >';
	     	                           		html += '<input type="hidden" value="'+response[i].roomAvailDate+','+response[i].roomId+','+response[i].availcnt+','+response[i].categoryId+','+response[i].userId+','+response[i].reservationNumber+','+response[i].roomAvailDate+','+response[i].guestUsrId+','+response[i].guestUsrId+'"></input>';
	     	                           		html += 'Room No:'+response[i].roomId+'<br/>';
	     	                           		html += ''+response[i].categoryName +'<br/>';
	     	                           		html += ''+response[i].availcnt;
	     	                           		html += '<span class="customer_name">'+response[i].userName+'</span>';
	     	                           		html += '<span class="customer_phone">'+response[i].phoneNumber+'</span>';
	     	                           		html += '</a>';
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
	                 
	                 
	                 
	                 $('#adminCustmrList').click(function(){
	                	 $('#custmrName').val('');
	                	// $('#custmtrLstExprtBtn').prop('disabled', true);
	                	 $.ajax({
	                		url   : 'adminCustmrList.htm',
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
	                 });
	                 
	                 jQuery(document).on('click', ".usrBookedRecrdId", function(){
                   		var customerRecrdId = jQuery(this).find('input').val();
                   		 customerRecrdId = customerRecrdId.split(',');
                   		var userId = customerRecrdId[0];
                   		
                   		$.ajax({
            					url : 'getCustmrReservedHistry.htm?userId='+userId,
            					type : 'POST',
            					success : function(data){
            					
            						var html = '';
            						var response =  jQuery.parseJSON(data);
            						if(response.length != 0){
            							
            							for(j in response){
	    	                				html += '<div class="col-md-6">'+response[j].roomId+' - ' +response[j].category+'</div><div class="col-md-6">'+response[j].checkInDt+'</div>';
                						}
            							$('#reservtn_toggle').html(html);
            							
            						}else{
            							var msg = 'customer does not reserve no one room';
            							$('#adminCustmrsMsg').html(msg);
            							$('#adminCustmrsMsgDiv').show().fadeOut(4000);
            						}
            					}
            				});
	                 });
	                
	                 //sohowing customer reservation details on popup.
	                 $('#custmrReservtns').click(function(){
	                	 $('#custmrReservtnInputVal').val('');
	                	 //$('#CustmrResrvtnExprtBtn').prop('disabled', true);

	                	 $.ajax({
	                		url   : 'custmrsBookingHistory.htm',
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
			                				html += ''+response[i].reservationNumber+' ['+response[i].category+']<br/>';
			                				html += 'Check In  : '+response[i].checkInDt+'<br/>';
			                				html += 'Check Out : '+response[i].checkOutDt+'<br/>';
			                				html += 'Period : '+ response[i].numberOfDays+' Night(s)';
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
			                				html += ''+response[i].reservationNumber+' [ '+response[i].category+']<br/>';
			                				html += 'Check In  : '+response[i].checkInDt+'<br/>';
			                				html += 'Check Out : '+response[i].checkOutDt+'<br/>';
			                				html += 'Period : '+response[i].numberOfDays+' Night(s)';
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
	                 });
	                 
	                 
	                 //customer reservation details search option.
	                 $('#custmrReservtnGOBtn').click(function(){
	                	 var cstmrName = $('#custmrReservtnInputVal').val();
	                	
	                	 $.ajax({
	                		 url   : 'getCustomerReservtnDetails.htm?cstmrName='+cstmrName,
	                		 type  : 'POST',
	                		 success : function(data){
	                			 var response = jQuery.parseJSON(data);
	                			 var html = '';
	                			 
	                			 if(response == 'user name is not exits in list'){
	                				 if(cstmrName.length == 0){
	                					 $('#CustmrResrvtnExprtBtn').prop('disabled', true);
	                					 var msg = 'please enter customer name to search';
	                					 $('#adminReservtnMsg').html(msg);
	                					 $('#adminReservtnMsgDiv').show().fadeOut(4000);
	                				 }else{
	                					 $('#CustmrResrvtnExprtBtn').prop('disabled', true);
	                					 var msg = 'Customer name is not exists in list';
	                					 $('#adminReservtnMsg').html(msg);
	                					 $('#adminReservtnMsgDiv').show().fadeOut(4000);
	                					 $('#custmrReservtnInputVal').val('');
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
				                				html += ''+response[i].reservationNumber+' ['+response[i].category+']<br/>';
				                				html += 'Check In  : '+response[i].checkInDt+'<br/>';
				                				html += 'Check Out : '+response[i].checkOutDt+'<br/>';
				                				html += 'Period : '+ response[i].numberOfDays+' Night(s)';
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
				                				html += ''+response[i].reservationNumber+' [ '+response[i].category+']<br/>';
				                				html += 'Check In  : '+response[i].checkInDt+'<br/>';
				                				html += 'Check Out : '+response[i].checkOutDt+'<br/>';
				                				html += 'Period : '+response[i].numberOfDays+' Night(s)';
				                				html += '</p>';
				                				html += '</div>';
				                				html += '</div>';
			                				}//else close.
			                				
			                			}//for function close.
			                			$('#custmrHstryRecrds').html(html);
			                			$('#admin_myreservation_details').show();
			                			
			                			//enable Export Btn.
			                			$('#CustmrResrvtnExprtBtn').prop('disabled', false);
	                			 }
	                			 
	                		 }
	                	 });
	                 });
	                
	                 $('#custmrReservtnCSV').click(function(){
	                	var userName = $('#custmrReservtnInputVal').val();
	                	if(userName != ''){
	                		document.location.href = "exportCustmrReservntHstryCSV.htm?un="+userName;
	                	}else{
	                		document.location.href = "exporAllCustomersLstCSV.htm";
	                	}
	                	
	                 });
	                 
	                 
	                 
	                 //custmrEmailList
	                 $('#custmrsEmailList').click(function(){
	                	 $('#custmrEmailLstExprtBtn').prop('disabled', true);
	                	$('#custmrEmailListId').val('');
	                	  $.ajax({
	                		  url   : 'adminCustmrList.htm',
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
	                 });
	                 
	                 $('#custmrEmailListSearchBtn').click(function(){
	                	 
	                	 $('#custmrEmailLstExprtBtn').prop('disabled', false);
	                	 var custmrName = $('#custmrEmailListId').val();
	                	 $.ajax({
	                		url   : 'getCustomerInfo.htm?custmrName='+custmrName,
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
		                			$('#admin_email_list').show();
	                			}else{
	                				if(custmrName.lenght == 0 || custmrName == ''){
	                					var msg = 'please enter customer name to search';
	                					$('#adminEmailLstStatusMsg').html(msg);
	                					$('#adminEmailLstMsgStatusDiv').show().fadeOut(4000);
	                				}else{
	                					var msg  = "customer name is not in list";
	                					$('#adminEmailLstStatusMsg').html(msg);
	                					$('#adminEmailLstMsgStatusDiv').show().fadeOut(4000);
	                					$('#custmrEmailListId').val('');
			             				
	                				}
	                				
	                			}	
	                			
	                		}
	                	 });
	                	 
	                	 
	                 });
	                 
	                 //Customers Email List CSV file download.
	                 $('#custmrEmailCsvBtn').click(function(){
	                	 var custmrName = $('#custmrEmailListId').val();
	                	 document.location.href = "exportCustmrEmailCSV.htm?un="+custmrName;
	                 });
	                 
	                 
	              
	             	 //search button for customer list.
	             	 var userName = '';
	             	 $('#searchBtn').click(function(event){
	             		 var custmrName = $('#custmrName').val();
	             		$.ajax({
	             			url : 'getCustomerInfo.htm?custmrName='+custmrName,
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
	                			$('#admin_customer_list').show();
	                			$('#custmtrLstExprtBtn').prop('disabled', false);
	             				}else{
	             					var status  = "customer name is not in list";
	             					if(custmrName.length == 0){
	             						$('#custmtrLstExprtBtn').prop('disabled', true);
	             						var msg = 'please enter customer name to search';
	             						
	             						$('#adminCustmrsMsg').html(msg);
		    				 			$('#adminCustmrsMsgDiv').show().fadeOut(4000);
	             					}else{
	             						$('#custmtrLstExprtBtn').prop('disabled', true);
	             						$('#adminCustmrsMsg').html(status);
		    				 			$('#adminCustmrsMsgDiv').show().fadeOut(4000);
		    				 			$('#custmrName').val('');
	             					}
		             					
	             				}
	             			}
	             		});
	             	}); 
	             	 
	             	 
	             	 $('#custmrLstCSV').click(function(){
	             		 var userName = $('#custmrName').val();
	             		 if(userName != ''){
	             			document.location.href = "exporCustomerLstCSV.htm?userName="+userName;
	             		 }else{
	             			document.location.href = "exporAllCustomersLstCSV.htm";
	             		 }
	             		
	             	 });
	             	
	             	 jQuery(document).on('click', ".rmveWhteCustmr", function(){
	                 	 var userId = jQuery(this).find('input').val();
	                 	 $.ajax({
	                 		url  : "removeWhteCustmrFrmLst.htm",
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
		                 		url  : "removeWhteGuestUsrFrmLst.htm",
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
	             	
	             	jQuery(document).on('click', ".rmveBlackCustmr", function(){
	                 	 var userId = jQuery(this).find('input').val();
	                 	 
	                 	 $.ajax({
		                 		url   : 'removeBlackUser.htm',
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
	                 	
	                 	 $.ajax({
	                 		url   : 'removeBlackGuestUser.htm',
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
	             	
	             	 
	             	 
	             	 $('#adminWhiteList').click(function(){
	             		adminWhiteList();
	             	 });
	             	 
	             	
	             	 $('#whiteLstGOBtn').click(function(){
	             		 var userName = $('#whiteListInput').val();
	             		 
	             		 $.ajax({
	             			 url   : 'getWhitCustmrRecrd.htm?userName='+userName,
	             			 type  : 'POST',
	             			 success : function(data){
	             				 var response = jQuery.parseJSON(data);
	             				 var html = '';
	             				 if(response.statusMsg == 'no customer exists in white list'){
	             					 if(userName.length == 0){
	             						$('#adminWhteLstExprtBtn').prop('disabled', true);
	             						var msg = 'please enter customer name';
	             						$('#deleteWhteUsrStatusMsg').html(msg);
		    				 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
		    				 			$('#whiteListInput').val('');
	             					 }else{
	             						$('#adminWhteLstExprtBtn').prop('disabled', true);
	             						 var msg = 'no customer exists in white list';
	             						$('#deleteWhteUsrStatusMsg').html(msg);
		    				 			$('#deleteWhteUsrStatusMsgDiv').show().fadeOut(4000);
		    				 			$('#whiteListInput').val('');
	             					 }
	             					 event.preventdefault();
	             				 }else{
	             					if(response.userId != null){
	             						html += '<div class="panel panel-default">';
		             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
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
		             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-12">Since : '+response.strtDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					}else if(response.guestUserId != null){
	             						html += '<div class="panel panel-default">';
		             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
		             					html += '<h4 class="panel-title">';
		             					var colpse = 'collapse'+i;
		             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response.userName+'';
		             					html += '</a>';
		             					
		             					html += '<a class="right btn btn-default rmveWhteGuestCustmr" style=" margin-left: 20px; margin-top: -10px;" value="'+response.guestUserId+'">';
		             					html += '<input type="hidden" value="'+response.guestUserId+'"/>';
		             					html += 'Remove</a>';
		             					
		             					html += '<a class="right">'+response.email+'</a>';
		             					html += '<a class="right" style="padding-right:20px;">'+response.phoneNumber+'</a>';
		             					html += '</h4>';
		             					html += '</div>';
		             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-12">Since : '+response.strtDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					}
	             					
			             				$('#adminWhtLst').html(html);
			             				$('#admin_white_list').show();
	             					
			             				//enable Export Btn.
			             				$('#adminWhteLstExprtBtn').prop('disabled', false);
	             				 }
	             				 
	             			 }
	             		 });
	             	 });
	             	 
	             	
	             	 
	             	 
	             	 $('#whteUsrCSVBtn').click(function(){
	             		 var userName = $('#whiteListInput').val();
	             		 document.location.href = "exportWhiteCustmrCSV.htm?userName="+userName;
	             	 });
	             	 
	             	 //admin black list 
	                 $('#adminBlckList').click(function(){
	                	 adminBlackList();
	                 });
	                 
	             	 $('#blckGOBtn').click(function(){
	             		
	             		 var userName = $('#blckLstSearchInput').val();
	             		
	             		 $.ajax({
	             			url   : 'getBlackCustmrRecrd.htm?userName='+userName,
	             			type  : 'POST',
	             			success : function(data){
	             				var response =  jQuery.parseJSON(data);
	             				var html = '';
	             				if(response.statusMsg == 'no customer exists in black list'){
	             					if(userName.length == 0){
	             						$('#adminBlackLstExprtBtn').prop('disabled', true);
	             						var msg = 'please enter customer name';
	             						$('#deleteBlackUsrStatusMsg').html(msg);
	             						$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
	             					}else{
	             						$('#adminBlackLstExprtBtn').prop('disabled', true);
	             						var msg = 'no customer exists in black list';
	             						$('#deleteBlackUsrStatusMsg').html(msg);
	             						$('#deleteBlackUsrStatusMsgDiv').show().fadeOut(4000);
	             						$('#blckLstSearchInput').val('');
	             					}
	             					 event.preventdefault();
	             				 }else{
		             				 if(response.userId  != null){
		             						html += '<div class="panel panel-default">';
			             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
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
			             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
			             					html += '<div class="panel-body">';
			             					html += '<div class="col-md-6">Since : '+response.strtDate+'</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
		             					 }else if(response.guestUserId != null) {
		             						html += '<div class="panel panel-default">';
			             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
			             					html += '<h4 class="panel-title">';
			             					var colpse = 'collaps'+i;
			             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
			             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response.userName+'';
			             					html += '</a>';
			             					
			             					html += '<a class="right btn btn-default rmveBlackGuestCustmr" style="margin-left: 20px;margin-top: -10px;"  href="#"  id="blckGustUsrId" value="'+response.guestUserId+'">';
			             					html += '<input type="hidden" value="'+response.guestUserId+'"/>';
			             					html += 'Remove</a>';
			             					
			             					html += '<a class="right">'+response.email+'</a>';
			             					html += '<a class="right" style="padding-right:20px;">'+response.phoneNumber+'</a>';
			             					html += '</h4>';
			             					html += '</div>';
			             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
			             					html += '<div class="panel-body">';
			             					html += '<div class="col-md-6">Since : '+response.strtDate+'</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
			             					html += '</div>';
		             					 }
		             				$('#blckCustmrsLst').html(html);
		             				$('#admin_black_list').show(); 
		             				//enable Export Btn...
		             				$('#adminBlackLstExprtBtn').prop('disabled', false);
	             				 }
	             				
	             				
	             			}
	             			
	             		});
	             	 });
	             	 
	             	 $('#blckUsrCSVBtn').click(function(){
	             		var userName = $('#blckLstSearchInput').val();
	             		document.location.href = "exportBlckCustmrCSV.htm?userName="+userName;
	             	 });
	             	 
	             	 $('#addToWhitLstCat').click(function(){
	             		 $('#roomNum').val('');
	             		 $.ajax({
	             			 url   : 'getAdminCatgryLst.htm',
	             			 type  : 'POST',
	             			 success : function(data){
	             				 var response = jQuery.parseJSON(data);
	             				 var html = '';
	             				 html +=  '<option value="0">Select Category</option>';
	             				 for(i in response){
	             					 html += '<option value="'+response[i].categoryId+'">'+response[i].categoryName+'</option>'
	             				 }
	             				 $('#roomCategryLst').html(html);
	             				 $('#admin_addwhite_list').show();
	             				document.getElementById('fade').style.display='block';
	             			 }
	             		 });
	             		
	             	 });
	             	 
	             	 
	             	 $('#roomCategryLst').change(function(){
	             		 var catVal  = $('#roomCategryLst').val();
	             		 
	             		 $.ajax({
	             			url  : 'getAdminRoomsLst.htm?catVal='+catVal,
	             			type  : 'POST',
	             			success : function(data){
	             				var response = jQuery.parseJSON(data);
	             				console.log('jsonww :'+JSON.stringify(data));
	             				 
	             				  var html = '';
	             				  html +=  '<option value="0">Select Room</option>';
	             				  for(i in response){
	             					html += '<option value="'+response[i]+'">'+response[i]+'</option>'
	             				 } 
	             				  $('#roomNum').html(html);
	             			}
	             		 });
	             	 });
	             	 
	             	 $('#adminAddBtn').click(function(){
	             		 var roomId = $('#roomNum').val();
	             		 var catVal  = $('#roomCategryLst').val();
	             		 $.ajax({
	             			 url   :  'addRoomTOWhteLst?catVal='+catVal+'&roomId='+roomId,
	             			 type  : 'POST',
	             			 success : function(data){
	             				 var response = data;
	             				 if(response == roomId){
	             					 var status = 'Room No : '+roomId+' added to white list category successfully'
	             					 $('#addRoomToWhteStstusMsg').html(status);
	             					 $('#addRoomToWhteStstusMsg').show().fadeOut(4000);
	             					
	             				 }
	             			 }
	             		 });
	             	 });
	             	 
	                 
			   });// document.ready close.
			   
			   
			   function adminBlackList(){
				  
				 $('#blckLstSearchInput').val('');
              	 $('#adminBlackLstExprtBtn').prop('disabled', true);
              	 
              	$.ajax({
              		url : 'adminBlackList.htm',
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
		             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
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
		             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-6">Since : '+response[i].strtDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					 }else if(response[i].guestUserId != null) {
	             						html += '<div class="panel panel-default">';
		             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
		             					html += '<h4 class="panel-title">';
		             					var colpse = 'collaps'+i;
		             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response[i].userName+'';
		             					html += '</a>';
		             					
		             					html += '<a class="right btn btn-default rmveBlackGuestCustmr" style="margin-left: 20px;margin-top: -10px;"  href="#"  id="blckGustUsrId" value="'+response[i].guestUserId+'">';
		             					html += '<input type="hidden" value="'+response[i].guestUserId+'"/>';
		             					html += 'Remove</a>';
		             					
		             					html += '<a class="right">'+response[i].email+'</a>';
		             					html += '<a class="right" style="padding-right:20px;">'+response[i].phoneNumber+'</a>';
		             					html += '</h4>';
		             					html += '</div>';
		             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
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
			   
			   function adminWhiteList(){

           		 $('#whiteListInput').val('');
           		 $('#adminWhteLstExprtBtn').prop('disabled', true);
           		 
           		 $.ajax({
           			url  : 'adminWhiteList.htm',
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
		             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
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
		             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-12">Since : '+response[i].strtDate+'</div>';
		             					html += '</div>';
		             					html += '</div>';
		             					html += '</div>';
	             					}else if(response[i].guestUserId != null){
	             						html += '<div class="panel panel-default">';
		             					html += '<div class="panel-heading" role="tab" id="headingOne1">';
		             					html += '<h4 class="panel-title">';
		             					var colpse = 'collapse'+i;
		             					html += '<a data-toggle="collapse" data-parent="#accordion1" href="#'+colpse+'" aria-expanded="true" aria-controls="collapseOne">';
		             					html += '<span class="glyphicon glyphicon-user" aria-hidden="true"></span> '+response[i].userName+'';
		             					html += '</a>';
		             					
		             					html += '<a class="right btn btn-default rmveWhteGuestCustmr" style=" margin-left: 20px; margin-top: -10px;" value="'+response[i].guestUserId+'">';
		             					html += '<input type="hidden" value="'+response[i].guestUserId+'"/>';
		             					html += 'Remove</a>';
		             					
		             					html += '<a class="right">'+response[i].email+'</a>';
		             					html += '<a class="right" style="padding-right:20px;">'+response[i].phoneNumber+'</a>';
		             					html += '</h4>';
		             					html += '</div>';
		             					html += '<div id="'+colpse+'" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">';
		             					html += '<div class="panel-body">';
		             					html += '<div class="col-md-12">Since : '+response[i].strtDate+'</div>';
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
            	     //alert(total_scroll);
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
   <div id="alertMsgPopUp3" class="white_content"
	style="display: none; width: 467px; margin: 0px -98px 0px;">
	<h3 align="center">Alert Message</h3>
	<a href="javascript:void(0)" class="close_popup" onclick="closeWindow()" >
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
			
			<font size="3" color="green">email sharing is successfully stopped</font>
		       
	   </div>
    </div>
<body style="margin:20px 0px;">
<input type="hidden" value="${sessionScope.chngePopup}" id="chngePopup"></input>
<input type="hidden" value="${sessionScope.guestBean.userName}" id="inUser" />
<input type="hidden" value="${sessionScope.guestBean.email}" id="inEmail" />
<input type="hidden" value="${sessionScope.guestBean.phoneNumber}" id="inPhone" />
<input type="hidden" value="${sessionScope.changeReservBean.notes}" id="inNotes" />
<!-- Main COntents -->
<div class="col-md-12 col-lg-12">
	<!-- widget starts -->
	<div class="col-md-7 col-sm-9 col-xs-12 dummyfull">
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
								<li>-	Einmalige Registrierung fÃƒÂ¼r alle angeschlossen Anbieter</li>
								<li>-	ÃƒÂœbersciht aller vergangen und zukÃƒÂ¼nftigen Reservierungen </li>
								<li>-	Storno via one click</li>
								<li>-	Sie entscheiden welcher Anieter Ihre E-mail Addresse Sehen kann</li>
								<li>-	Hinweis bei BuchungÃƒÂ¼berlappungen</li>
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
			<p style="padding:0px 80px 15px 80px;font-size:13px;">Durch klicken auf Ã¢Â€Âš Registrieung abschlieÃƒÂŸenÃ¢Â€Â˜ stimmen sie den Nutzungbedinungen und der DatenschutzerklÃƒÂ¤rung von world of Reservation zu</p>
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
				<li>-	Einmalige Registrierung fÃƒÂ¼r alle angeschlossen Anbieter</li>
				<li>-	ÃƒÂœbersciht aller vergangen und zukÃƒÂ¼nftigen Reservierungen </li>
				<li>-	Storno via one click</li>
				<li>-	Sie entscheiden welcher Anieter Ihre E-mail Addresse Sehen kann</li>
				<li>-	Hinweis bei BuchungÃƒÂ¼berlappungen</li>
			</ul>
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
			<!-- /End contact data modal -->
<!-------------------------------- -------------MYRESERVATION DATA MODAL --------------------------------------------->
			<div id="myreservation" class="white_content">
			<h3>Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "backToHomePage()">
			<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
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
<!-- ================================  MANUAL RESERVATION FOR SERVICE PROVIDER MODAL START ==========================================-->
			<div id="myreservation_manual" class="white_content">
			<h3>Manual Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick="backToHomePage()">
				<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
				<div id="statusMsgDiv" align="center"><span id="statusMsg" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
					</div>
					
					<div id="errorStatusMsgDiv" align="center" style="color: red;"><span id="errorStatusMsg" 
							style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
					</div>
					<div class="reservation-data-section">
					<div class="col-xs-1">
					<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
					</div>
					<div class="col-xs-11" id="hotelInfo">
					
					</div>
					</div>
										<div class="reservation-data-section">
										<div class="col-xs-1">
										<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
										</div>
										<div class="col-xs-11" id="checkInAndOutDiv">
										
										</div>
										<div class="change_option_div" id="change_option_div" 
									 	style="color: #09C;  padding-bottom:4px;margin: 0px 60px 0px;">please select the check-out date</div> 
										<div id="change_option" style="margin: 0px 63px 0px;"></div>
										</div>
					<div class="reservation-data-section">
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11">
						<input type="text" class="form-control margin-bottom-10" placeholder="User Name" id="userName"/>
						<div id="userNameStatusDiv" align="center">
						<span id="useNameStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
						</div>
						
						<input type="text" class="form-control margin-bottom-10" placeholder="Email" id="userEmail"/>
						<div id="EmailStatusDiv" align="center">
						<span id="EmailStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
						</div>
						
						<input type="text" class="form-control margin-bottom-10" placeholder="Phone" id="userPhoneNum"/>
						<div id="PhoneStatusDiv" align="center">
						<span id="PhoneStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
						</div>
					
					
						<button class="btn btn-default btn-blank" type="button" id="paymentId"><img src="images/payment.png" title="payment paid" /></button>
						<button class="btn btn-default btn-blank" type="button" id="arrivedID"><img src="images/arrived.png" title="customer arrived" id="arrivedBtn"/></button>
						</div>
					</div>
					<div class="col-xs-12" style="padding:10px 20px 6px 20px;">
						<div class="form-group">
							<div class="col-sm-1">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</div>
							<div class="col-xs-11">
							  <textarea class="form-control"  rows="1" id="userNotes"></textarea>
							</div>
						</div>
					
					</div><!--ends col-xs-12 -->
					<div class="clearfix"></div>
				   <div id="hideReserve" style="padding:0px 100px;">
					<div class="form-group">
						<div class="col-sm-6" >
						  <button type="button" class="btn btn-default btn-save" id="reserveTable">Reserve table</button>
						  <img id="ajaxLoader" src="images/ajax-loading.gif" style="display:none; margin: -200px 150px 0px;">
						</div>
						<div class="col-sm-6" >
						  <button type="button" class="btn btn-default btn-save" id="cancelBtn">Cancel</button>
						</div>
				  </div>
				  </div>
				</form>
				
				<div id="hideTimer">
					<p class="center" style="padding:0px 0px 0px 0px;" >Reservation Process Continues for next <span id="log">60</span> Secs</p> 
				</div> 
				
				<div id="flash1" class="center" ></div>
				
			</div>
			
			</div>
			<div id="fade" class="black_overlay"></div>		
<!-- =================  MANUAL RESERVATION FOR SERVICE PROVIDER MODAL END ============================== -->	


<!-- =======================  ADMIN RESERVATION CONFIRMATION START ===================================== -->
<div id="adminReservation_confirm" class="white_content">
			<h3>Admin Reservation Confirmation</h3>
			
			<a href ="javascript:void(0)" class="close_popup" onclick = "backToHomePage()"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
						<div class="reservation-data-section">

						<div class="col-xs-12" id="referenceNumDiv">
								<!-- <div class="alert alert-success" role="alert" style="padding:5px 12px;margin-bottom:5px;">
								  <strong><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> Success!</strong> You successfully made reservation.
								</div> -->
						<!-- <p class="center" >Reference Number : <b>NHDJU6438</b></p> -->
						</div>
						</div>
					<div class="reservation-data-section">
						<div class="col-xs-4" style="text-align:right;">
							Sponsor By
							<img src="images/pepsiMdfied.png" class="img-responsive hotel_logo" style="margin: -25px 250px 0px;height: 50px;width: 130px;" />
						</div>
						<div class="col-xs-8"></div>
					</div>
					<div class="reservation-data-section">
					<div class="col-xs-1">
					<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
					</div>
					<div class="col-xs-11" id="hotelDetails">
					<!-- <p>
					Couching Simone, Garrison<br/>
					Woodway 115-445, Prisbane, GB Sooulfolk<br/>
					49-99-999-9999
					</p> -->
					</div>
					</div>
										<div class="reservation-data-section">
										<div class="col-xs-1">
										<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
										</div>
										<div class="col-xs-11" id="checkInOutDts">
										<!-- <p>
										Donnarster 14 July 2015 - ab 17:00<br/>
										Donnarster 14 July 2015 - bis 19:00
										<span style="float:right;">2 hours</span><br/>
										Table 2 (2)<br/>
										2 Palatz
										</p> -->
										</div>
										</div>
					<div class="reservation-data-section">
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11" id="userInfrmtn">
							<!-- <p>
							David Adams<br/>
							davidadamas@gmail.com<br/>
							+49-888-8888 
							</p> -->
						</div>
					</div>

					<div class="clearfix"></div>
				   <div style="padding:20px 100px;">
					<!-- <div class="form-group">
						  <a href="#" class="btn btn-default ">I</a>
						  <a href="#" class="btn btn-default">F</a>
						  <a href="#" class="btn btn-default ">F</a>
						  <a href="#" class="btn btn-default">Export</a>

				  </div> -->
				  </div>
				</form>

			</div>
			</div>
			<div id="fade" class="black_overlay"></div>
<!-- ============================= ADMIN RESERVATION CONFIRMATION END =======================================  -->			
<!-- ============================= CHANGE RESERVATION MODAL START =============================================-->
	<div id="changeReservtn" class="white_content">
			<h3>Manual Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "backToHomePage()"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
					
					<div class="reservation-data-section">
					
						<div class="col-xs-11">
						<span style="font-size: 16px;font-weight: 700;" id="changeReservtnText"></span>
						</div>
					</div>
					
				<div class="clearfix"></div>
				   <div style="padding:0px 100px;" id="chngeAndCancelBtns">
				  </div>
				</form>
				
			</div>
			</div>
			<div id="fade" class="black_overlay"></div>

<!-- ============================= CHANGE RESERVATION MODAL END =============================================-->
<!-- ============================= DELETE RESERVATION MODAL START =============================================-->
	<div id="deleteReservtn_popUp" class="white_content">
			<h3>Delete Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "backToHomePage()"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
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
<!-------------------------------- WHITELIST DETAILS DATA MODAL --------------------------------------------->
				<div id="mywhite_list" class="white_content"
					style="left: 8%; width: 85%;">
					<h3>My White List Category</h3>
					<a href="javascript:void(0)" class="close_popup"
						onclick="closeWindow()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form class="form-horizontal"
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
						onclick="closeWindow()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<div class="user_data_wrapper" style="padding: 0px">
						<form action="getServBlackListDet.htm" class="form-horizontal"
							id="myblack_list">
							<!--  <form class="form-horizontal"> -->
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
						onclick="closeWindow()"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					
					<div id="statusDiv" align="center"><span id="isEmailShared" 
							style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span>
						</div>
					
					<div class="user_data_wrapper" style="padding: 0px">
						<form class="form-horizontal" id="emailVisibleForm">
							
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

						<form class="form-horizontal" id="reservationHistory">
							
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
			<h3>Customers List</h3>
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
						 <label for="inputEmail3" class="col-md-3 control-label">Room Category</label>
							<div class="col-md-8">
								<select class="form-control" name="language" id="roomCategryLst">
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-md-3 control-label">Room Number</label>
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
		<input type="hidden" value="${mode}" id="mode"> 
		<input type="hidden" value="${updateUser}" id="updateUser"> 
		<input type="hidden" value="${mode}" id="mode">
		<div id="myprofile" class="white_content" style="left:10%;width:80%;">
			<h3>Admin Profile</h3>
			<a href="javascript:void(0)" class="close_popup"
						onclick="document.getElementById('myprofile').style.display='none';document.getElementById('fade').style.display='none';"><span
						class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
					<spring:form action="updateProfile.htm?name=test2" commandName="getSPUser"
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
										<spring:input  path="userName" class="form-control"
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
									<div class="col-sm-9 col-xs-12">
										<spring:input path="dob" class="form-control"
											name="dob" placeholder="dd/mm/yyyy"
											onchange="hidedateofbirth()" id="datepick"></spring:input>

										<spring:errors style="color: red" path="dob"
											id="dateOfBirth"></spring:errors>
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
								<spring:hidden path="isServiceProvider" />
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
					<c:if test="${sessionScope.hotel != null}">
					<b> ${sessionScope.hotel.hotelName}</b>
					<br />${sessionScope.hotel.hotelAddress} ${sessionScope.hotel.postalCode} ${sessionScope.hotel.city}
                     </c:if>
			</div>
			<div class="col-xs-3" style="padding:35px 0px 0px;">
				<div class="iconset">
						<!-- Single button -->
						<div class="btn-group">
						  <button type="button" class="btn btn-default dropdown-toggle btn-blank" data-toggle="dropdown" aria-expanded="false">
							<img src="images/greenpat.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu">
							<li><a href="initChangeRoom.htm"><img src="images/brownpat.png" style="padding-right:6px;" /></a></li>
							<li><a href="permanentRreservation.htm" ><img src="images/bluepat.png" style="padding-right:6px;" /></a></li>
							<li><a href="javascript:void(0)"><img src="images/greenpat.png" style="padding-right:6px;" /></a></li>
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

							<li><a href="javascript:void(0)" onclick="getProfile()" >My Profile</a></li>
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
				
				<div class="col-md-3 col-sm-3 col-xs-12 nopadding">
					<button class="btn btn-default btn-current-date" id="currentDate">
						<span  id="currentDateVal"></span>
			        </button>
				</div>
				
				<div class="col-md-3 col-sm-3 col-xs-12">
					<select class="selectpicker form-control" id="category">
					</select>
				</div>
				
				<div class="col-md-4 col-sm-4 col-xs-12  nopadding">
					<select class="selectpicker form-control" id="roomNo"></select>
				</div>
				
				<div class="col-md-2 col-sm-2 col-xs-12" style="padding-right:0px;">
					<!-- <input type="number" class="form-control" name="" value="" Placeholder=" Days" value="1" /> -->
					<input type="number" class="form-control" Placeholder=" Days" id="noOfDays"   />
				</div>
			<div class="clearfix"></div>
			<div class="ajax_container">
			<div class="ajax_loader"></div>
				<div class="col-md-12 nomargin_lf_rt date_wrapper">
							<div id="thumbcarousel1" class="carousel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							   <div class="carousel-caption1" id="prmryDatesList"></div>
							   <div class="carousel-caption1" id="secndryDatesList"></div>
							</div><!-- thumbcarousel1 end -->
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
				<div class="col-md-12 nomargin info_wrapper">
							<div class="ajax_container_inner">
							<!-- <div class="ajax_loader_inner"></div> -->
							<div id="thumbcarousel2" class="carousel carousel_info_hotel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							  <div class="carousel-inner" role="listbox" id="carouselDivPrimary" >
							  </div>

							<div class="carousel-inner" role="listbox" id="carouselDiv">
							</div>
							<div class="carousel-inner" role="listbox" id="roomInfoDiv">
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
		</ul>
	</div>

	<!-- ends-->
	<div class="col-md-5 col-sm-3 col-xs-12">
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
<!-- </div> -->
<!--Main COntent ends -->
<!-- </div> -->


</body>
</html>
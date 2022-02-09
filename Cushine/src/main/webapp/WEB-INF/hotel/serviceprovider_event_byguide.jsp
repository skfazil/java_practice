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
		
	    <link rel="stylesheet" href="css/bootstrap.min.css"/>
		<link href="css/style.css" rel="stylesheet">
		<link rel="stylesheet" href="css/non-responsive.css">
			<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css">
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	    <script type="text/javascript" src="js/bootstrap.min.js"></script>
	   	<script type="text/javascript" src="js/jquery.validate.js"></script>
	    <!-- Google Map API Key Source -->
		<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<!-- Google Map  Source -->
	   	<script type="text/javascript" src="js/gmaps.js"></script>
		<script src="js/customjs.js"></script>
		<script src="js/scroll-7.js"></script>
	
	
	
	<script type="text/javascript">
	var geventName;
	var gorgName;
	var gaddress;
	var gstrTime;
	var gendTime;
	var gduration;
	var currentDt ;
	var arrLength;
	var globalScheduleId;
	var gStrtTme;
	var gEndTme;
	var gDuration;
	var gAvailSeats;
	var gEventName;
	$(document).ready(function() {
 	
		 
		 $('#selectTour').change(function(){
			var eventId =  $('#selectTour').val();
			var guideId = $('#availGuides').val();
			var clickedDt = currentDt;
			var frmtdDt = formatDate(currentDt);
			var html = '<option value="0">Select Guide</option>';
			if(parseInt(eventId) != 0){
				availGuidesBsdOnTour(eventId);
			}else{
				$('#availGuides').html(html);
			}
			var seatCount= $('#countId').val();
			//alert('count'+seatCount);
			if(seatCount == '') {
				seatCount = 0;
 			}
			$.ajax({
				url : "selectedDate.htm?clickedDt="+clickedDt+"&eventId="+eventId+"&guideId="+guideId,
				type : 'POST',
				success : function(data) {
					var response = jQuery.parseJSON(data);
					var k=0;
					var map = {'08:00':'eight','08:30':'eightthirty','09:00':'nine','09:30':'ninethirty','10:00':'ten','10:30':'tenthirty',
							'11:00':'eleven','11:30':'eleventhirty','12:00':'twelve','12:30':'twelvethirty','13:00':'thirteen','13:30':'thirteenthirty','14:00':'fourteen',
							'14:30':'fourteenthirty','15:00':'fifteen','15:30':'fifteenthirty','16:00':'sixteen','16:30':'sixteenthirty','17:00':'seventeen','17:30':'seventeenthirty',
							'18:00':'eighteen','18:30':'eighteenthirty','19:00':'nineteen','19:30':'nineteenthirty'};


                     var minEvent = {'08:00':'','08:30':'','09:00':'','09:30':'','10:00':'','10:30':'',
							          '11:00':'','11:30':'','12:00':'','12:30':'','13:00':'','13:30':'','14:00':'',
							         '14:30':'','15:00':'','15:30':'','16:00':'','16:30':'','17:00':'','17:30':'',
							         '18:00':'','18:30':'','19:00':'','19:30':''};
					
					var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
					var dateSet = Object.keys(response);
					var html1 = '';
					var html2 = '';
					var count = 0;
					//1. for displaying primary dates
					html1 += '<div class="piece">';
	 				html1 += '<div class="small_square">';
	 				for(var a=0,key = Object.keys(response),aa=key.length;a<aa;a++) {
	 					if(a==0 || a==14) {
	 						var monthNum = key[a].slice(3,5);
                 			 monthNum = (monthNum-1) ;
                 			 jQuery.each( monthArray, function( i, mnthName ) {
                            	 	if(monthNum == i){
                            	 		html1 += '<span class="month_title">'+mnthName+'</span>';
                            	 	}
                            	});
	 					}
	 					var bool = isSeatsAvailable(response[key[a]], seatCount);
	 					if(currentDt == key[a]){
	 						html1 += '<span class="cal_date current_date hiddenSpan">'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';		 				 
	 					}
	 					else if(bool == true)
	 					{
	 						html1 += '<span class="cal_date green hiddenSpan" >'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';
	 					}
	 					else{
	 						html1 += '<span class="cal_date hiddenSpan" >'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';
	 					}
	 				}
	 				html1 += '</div>';
	 				html1 += '</div>';
	 				
	 				$('#datesListDiv').html(html1);
	 				$('#datesListDiv').show();
	 				//end of primary dates display
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
										globalScheduleId=val.scheduleId;
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
										if(val.userNotes!=null) {
											html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
										}
										if(val.isPaid==true) {
											
											html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

										}
										if(val.isArrived==true) {
											html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
										}
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
										html2 += '<a href="javascript:void(0)" onclick="showReservationPopup('+val.scheduleId+')">';
										if(val.availableSeats > 2) {
											html2 += '>2 <br/>'+val.guideName+'</a>';
										}else {
											html2 += val.availableSeats+'<br/>'+val.guideName+'</a>';
										}
										html2 += '</p></div>';								
									}else {
									html2 += '<div class="cell eventbooked-'+val.divCount+'">';
									html2 += '<div class="cell_event_addition-info">';
									if(val.userNotes!=null) {
										html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
									}
									if(val.isPaid==true) {
										
										html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

									}
									if(val.isArrived==true) {
										html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
									}
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
	 					//alert("Key :: "+keyVal+" Value :: "+minEvent[keyVal]);
	 					if(minEvent[keyVal] != '') {
	 						var id = '#'+map[keyVal] ;		 						
	 						//alert("adding green class");
	 						//alert("id to add is : "+id);
	 						//alert("#'+map[keyVal]+'\");
	 						$(id).addClass("green");
	 					}
	 				}

	 				
	 				$('#dateButton').html(frmtdDt);
					$('#carousalDatesLst').html(html2);
					$('.ajax_loader_inner').removeClass();						
				}
			 });
			
		 });
		 
		 
		$('#availGuides').change(function() {
			var eventId = $('#selectTour').val();
			var guideId = $('#availGuides').val();
			var clickedDt = currentDt;
			var frmtdDt = formatDate(clickedDt);
			
			var seatCount= $('#countId').val();
			//alert('count'+seatCount);
			
			$.ajax({
				url : "selectedDate.htm?clickedDt="+clickedDt+"&eventId="+eventId+"&guideId="+guideId,
				type : 'POST',
				success : function(data) {
					var response = jQuery.parseJSON(data);
					var k=0;
					var map = {'08:00':'eight','08:30':'eightthirty','09:00':'nine','09:30':'ninethirty','10:00':'ten','10:30':'tenthirty',
							'11:00':'eleven','11:30':'eleventhirty','12:00':'twelve','12:30':'twelvethirty','13:00':'thirteen','13:30':'thirteenthirty','14:00':'fourteen',
							'14:30':'fourteenthirty','15:00':'fifteen','15:30':'fifteenthirty','16:00':'sixteen','16:30':'sixteenthirty','17:00':'seventeen','17:30':'seventeenthirty',
							'18:00':'eighteen','18:30':'eighteenthirty','19:00':'nineteen','19:30':'nineteenthirty'};


                     var minEvent = {'08:00':'','08:30':'','09:00':'','09:30':'','10:00':'','10:30':'',
							          '11:00':'','11:30':'','12:00':'','12:30':'','13:00':'','13:30':'','14:00':'',
							         '14:30':'','15:00':'','15:30':'','16:00':'','16:30':'','17:00':'','17:30':'',
							         '18:00':'','18:30':'','19:00':'','19:30':''};
					
					var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
					var dateSet = Object.keys(response);
					var html1 = '';
					var html2 = '';
					var count = 0;
					//1. for displaying primary dates
					html1 += '<div class="piece">';
	 				html1 += '<div class="small_square">';
	 				for(var a=0,key = Object.keys(response),aa=key.length;a<aa;a++) {
	 					if(a==0 || a==14) {
	 						var monthNum = key[a].slice(3,5);
                 			 monthNum = (monthNum-1) ;
                 			 jQuery.each( monthArray, function( i, mnthName ) {
                            	 	if(monthNum == i){
                            	 		html1 += '<span class="month_title">'+mnthName+'</span>';
                            	 	}
                            	});
	 					}
	 					var bool = isSeatsAvailable(response[key[a]], seatCount);
	 					if(currentDt == key[a]){
	 						html1 += '<span class="cal_date current_date hiddenSpan">'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';		 				 
	 					}
	 					else if(bool == true)
	 					{
	 						html1 += '<span class="cal_date green hiddenSpan" >'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';
	 					}
	 					else{
	 						html1 += '<span class="cal_date hiddenSpan" >'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';
	 					}
	 				}
	 				html1 += '</div>';
	 				html1 += '</div>';
	 				
	 				$('#datesListDiv').html(html1);
	 				$('#datesListDiv').show();
	 				//end of primary dates display
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
										globalScheduleId=val.scheduleId;
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
										if(val.userNotes!=null) {
											html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
										}
										if(val.isPaid==true) {
											
											html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

										}
										if(val.isArrived==true) {
											html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
										}
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
										html2 += '<a href="javascript:void(0)" onclick="showReservationPopup('+val.scheduleId+')">';
										if(val.availableSeats > 2) {
											html2 += '>2 <br/>'+val.guideName+'</a>';
										}else {
											html2 += val.availableSeats+'<br/>'+val.guideName+'</a>';
										}
										html2 += '</p></div>';								
									}else {
									html2 += '<div class="cell eventbooked-'+val.divCount+'">';
									html2 += '<div class="cell_event_addition-info">';
									if(val.userNotes!=null) {
										html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
									}
									if(val.isPaid==true) {
										
										html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

									}
									if(val.isArrived==true) {
										html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
									}
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
	 					//alert("Key :: "+keyVal+" Value :: "+minEvent[keyVal]);
	 					if(minEvent[keyVal] != '') {
	 						var id = '#'+map[keyVal] ;		 						
	 						//alert("adding green class");
	 						//alert("id to add is : "+id);
	 						//alert("#'+map[keyVal]+'\");
	 						$(id).addClass("green");
	 					}
	 				}

	 				
	 				$('#dateButton').html(frmtdDt);
					$('#carousalDatesLst').html(html2);
					$('.ajax_loader_inner').removeClass();						
				}
			 });
		});
		 
		 //on pageload.
		 currentDt = getCurrentDate();
		 //getEvntDatesLst();
		 getStrtAndEndTime();
		 
		 //carousalDatesLst();
		 
		 selectTour();//selectTour
		 selectGuide();
		 onLoad();
		 		 
	 });//document.ready function close.
	 var count=null;
	 function slctCount(){
		  count= $('#countId').val();
			alert('count'+count);
			
	 }
	 function isSeatsAvailable(arr, seatCount) {
		// alert("inside isSeatsAvailable()");
		 //alert("array : "+arr+" seatCount : "+seatCount);
		 for(var i=0;i<arr.length;i++) {
			// alert(arr[i].availableSeats+" >= "+seatCount);
			 if(arr[i].availableSeats >= seatCount) {
				// alert("returning true");
				 return true;
			 }
		 }
		 //alert("returning false");
		 return false;
	 }
	 
	 function availGuidesBsdOnTour(tourVal){
		 $('#selectTour').val();
		 
		 $.ajax({
			 url     : 'getGuidesInfo.htm?tourVal='+tourVal,
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
	 
	 function  selectTour(){
		 $.ajax({
			url     : 'selectEvent.htm',
			type    : 'POST',
			success : function(data){
				var response = jQuery.parseJSON(data);
				var html = '';
				
				html += '<option value="0">Select Tour</option>';
				for(i in response){
					html += '<option value='+response[i].eventId+'>'+response[i].eventName+'</option>';
				}
				 $('#selectTour').html(html);
				 
				
			}
		 });
	 }
	 
	 function selectGuide() {
		 var html = '';
		 html += '<option value="0">Select Guide</option>';
		 $("#availGuides").html(html);
	 }
	 
	 function carousalDatesLst(){
		 $.ajax({
			url   : "getDatesLst.htm",
			type  : 'POST',
			success: function(data){
				var response  =  jQuery.parseJSON(data);
				var responseLen = response.length;
				var remngLength = 0;
				var html = '';
				var flagCnt = 0,length=0,remngElmnts=0;
				for(i in response){
					
					var count = 0;
					if(i < 7){
						if(i == 0){
							html += '<div class="item active">';
							html += '<div class="carousel-caption">';
						} 
						var val = 'X';
						var guide = 'Guide 1';
						    html += '<div class="col-xs-3 col-xs-3-dateline nopadding book_info">';
							html += '<div class="cell cell_header cell_byobject">'+response[i].evntDt+'</div>';
							
							html += '<div class="cell eventbooked-4">';
							html += '<div class="cell_event_addition-info">';
							html += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
							html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
							html += '</div>';
							html += '<p class="merge_padding">';
							html += '<a href ="javascript:void(0)" >';
							var evntDuration  = parseInt(response[i].eventScheduleDTOs[0].duration); 								
							html += ''+val+'<br/>';
							html += ''+guide+'';
							html += '</a>';
							html += '</p>';
							html += '</div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell eventbooked-2">';
							html += '<div class="cell_event_addition-info">';
							html += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
							html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
							html += '</div>';
							html += '<p class="merge_padding">';
							html += '<a href ="javascript:void(0)">';
							html += ''+val+'<br/>';
							html += ''+guide+'';
							html += '</a>';
							html += '</p>';
							html += '</div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell eventbooked-2">';
							html += '<div class="cell_event_addition-info">';
							html += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
							html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
							html += '</div>';
							html += '<p class="merge_padding">';
							html += '<a href ="javascript:void(0)">';
							html += ''+val+'<br/>';
							html += ''+guide+'';
							html += '</a>';
							html += '</p>';
							html += '</div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
						
							html += '</div>'; 
					
					}//if close.
					if(i == 6){
						
						html += '</div>';
						html += '</div>'; //item active close
					}
					
					
					if(i != 0 && i % 7 === 0  ){  //i % 7 === 0  i == 7
						
						var subtrctn = responseLen - i;
						
						if(subtrctn < 7){
							if(flagCnt == 0){
								remngLength = responseLen - i;
								length = responseLen;
								remngElmnts = i;
								
								html += '<div class="item">';
								html += '<div class="carousel-caption">';
								
							}flagCnt++;
							break;
						}else{
							html += '<div class="item">';
							html += '<div class="carousel-caption">';
							count = count++;
						}
					}
					
					if(i >= 7){
						var val = 'X';
						var guide = 'Guide 1';
						    html +='<div class="col-xs-3 col-xs-3-dateline nopadding book_info">';
							html += '<div class="cell cell_header cell_byobject">'+response[i].evntDt+'</div>';
							html += '<div class="cell eventbooked-4">';
							html += '<div class="cell_event_addition-info">';
							html += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
							html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
							html += '</div>';
							html += '<p class="merge_padding">';
							html += '<a href ="javascript:void(0)" >';
							html += ''+val+'<br/>';
							html += ''+guide+'';
							html += '</a>';
							html += '</p>';
							html += '</div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell eventbooked-2">';
							html += '<div class="cell_event_addition-info">';
							html += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
							html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
							html += '</div>';
							html += '<p class="merge_padding">';
							html += '<a href ="javascript:void(0)">';
							html += ''+val+'<br/>';
							html += ''+guide+'';
							html += '</a>';
							html += '</p>';
							html += '</div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell eventbooked-2">';
							html += '<div class="cell_event_addition-info">';
							html += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true"></span>';
							html += '<span class="glyphicon glyphicon-ok right" aria-hidden="true"></span>';
							html += '</div>';
							html += '<p class="merge_padding">';
							html += '<a href ="javascript:void(0)">';
							html += ''+val+'<br/>';
							html += ''+guide+'';
							html += '</a>';
							html += '</p>';
							html += '</div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
							html += '<div class="cell storeclose"></div>';
						
							html += '</div>'; 
					}
					if( i != 0 && i % 7 === 0 ){
						count += 1;
					}
					
					if( i != 0 && count === 2 && i % 7 === 0 ){
						html += '</div>';
						html += '</div>'; 
						count = 1;
					}
					
					
					$('#carousalDatesLst').html(html); 
				
				}//for close.
				
				
			}
		 });//ajax call close.
	 } 
	 
	 function getStrtAndEndTime(){
		 
		 $.ajax({
	 			url  : "getStrtAndEndTimeInfo.htm?currDt="+currentDt,
	 			type : 'POST',
	 			success : function(data){
	 				var response =  jQuery.parseJSON(data);
	 				var html = '';
	 				var timeInterval = makeTimeIntervals(response.evntStrtTme, response.evntEndTme, 30);
	 				//alert('interval length :'+timeInterval.length);
	 				/* for(i in timeInterval){
	 					if(i%2 == 0){
	 						//alert('even');
	 						html += '<div class="cell green">'+timeInterval[i]+'</div>';
	 					}else{
	 						//alert('odd');
	 						html += '<div class="cell">'+timeInterval[i]+'</div>';
	 					}
	 				} */
	 				
	 				html += '<div class="cell" id="eight">08:00</div>';
	 				html += '<div class="cell" id="eightthirty">08:30</div>';
	 				html += '<div class="cell" id="nine">09:00</div>';
	 				html += '<div class="cell" id="ninethirty">09:30</div>';
	 				html += '<div class="cell" id="ten">10:00</div>';
	 				html += '<div class="cell" id="tenthirty">10:30</div>';
	 				html += '<div class="cell" id="eleven">11:00</div>';
	 				html += '<div class="cell" id="eleventhirty">11:30</div>';
	 				html += '<div class="cell" id="twelve">12:00</div>';
	 				html += '<div class="cell" id="twelvethirty">12:30</div>';
	 				html += '<div class="cell" id="thirteen">13:00</div>';
	 				html += '<div class="cell" id="thirteenthirty">13:30</div>';
	 				html += '<div class="cell" id="fourteen">14:00</div>';
	 				html += '<div class="cell" id="fourteenthirty">14:30</div>';
	 				html += '<div class="cell" id="fifteen">15:00</div>';
	 				html += '<div class="cell" id="fifteenthirty">15:30</div>';
	 				html += '<div class="cell" id="sixteen">16:00</div>';
	 				html += '<div class="cell" id="sixteenthirty">16:30</div>';
	 				html += '<div class="cell" id="seventeen">17:00</div>';
	 				html += '<div class="cell" id="seventeenthirty">17:30</div>';
	 				html += '<div class="cell" id="eighteen">18:00</div>';
	 				html += '<div class="cell" id="eighteenthirty">18:30</div>';
	 				html += '<div class="cell" id="nineteen">19:00</div>';
	 				html += '<div class="cell" id="nineteenthirty">19:30</div>';
	 				//html += '<div class="cell">20:00</div>';
	 				
	 				$('#showStrtAndEndTimeDiv').html(html); 
	 				
	 			}
	 		 });
	 		 
	 }
	function getEvntDatesLst(){
		 $.ajax({
			 url  : "getEventDatesLst.htm",
			 type : 'POST',
			 success : function(data){
				 var response = jQuery.parseJSON(data);
				 arrLength = response.length;
				 var html = '';
				 html += '<div class="piece">';
				 html += '<div class="small_square">';
				 html += '<span class="month_title">July</span>';
				 
				 for(i in response){
					if(currentDt == response[i].evntDt){
						html += '<span class="cal_date current_date">'+response[i].evntDt.slice(0,2)+'';
						html += '<input type = "hidden" value="'+response[i].evntDt.slice(0,2)+'">';
						html += '</span>';
	 				 }else if( response[i].availableSeats == 0){
						html += '<span class="cal_date" >'+response[i].evntDt.slice(0,2)+'</span>';
					}else{
						html += '<span class="cal_date green" >'+response[i].evntDt.slice(0,2)+'</span>';
					}
					 
				 }
				 html += '</div>';
				 html += '</div>';
				 
				 $('#datesListDiv').html(html);
				 $('#datesListDiv').show();
			 }
		 });
	 }
	 function makeTimeIntervals(startTime, endTime, increment) {
   	    startTime = startTime.split(':');
    	    endTime = endTime.split(':');
    	    increment = parseInt(increment, 10);
	
    	    
    	    var pad = function (n) { return (n < 10) ? '0' + n.toString() : n; },
    	        startHr = parseInt(startTime[0], 10),
    	        startMin = parseInt(startTime[1], 10),
    	        endHr = parseInt(endTime[0], 10),
    	        endMin = parseInt(endTime[1], 10),
    	        currentHr = startHr,
    	        currentMin = startMin,
    	        previous = currentHr + ':' + pad(currentMin),
    	        current = '',
    	        r = [];
    	  		
    	    do {
    	        currentMin += increment;
    	        
    	        if ((currentMin % 60) === 0 || currentMin > 60) {
    	            currentMin = (currentMin === 60) ? 0 : currentMin - 60;
    	            currentHr += 1;
    	        }
    	        current = currentHr + ':' + pad(currentMin);
    	        
    	        r.push(previous);
    	        previous = current;
    	        
    	  } while (parseInt(currentHr) !== parseInt(endHr) );
    	    return r;
    	};
	 
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
        var fullDate =  Day + '/' + Month + '/' + Year;
        return fullDate;
	 }
	 
	 function getEventReservedUsers(scheduleId) {
		// alert('resv'+globalScheduleId);
		 $.ajax({
			url : "getReservedUsers.htm?scheduleId="+scheduleId,
			type : 'POST',
			success : function(data) {
				var response = jQuery.parseJSON(data);
				//alert('response'+response);
				var html = '';
				for(key in response) {
					var parsedKey = JSON.parse(key);
					html += '<form class="form-horizontal">';
					html += '<div id="statusInfoDiv" align="center">';
					html += '<span id="statusInfoSpan" style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span></div>';
					html += '<div class="reservation-data-section">';
					html += '<div class="col-xs-1">';
					html += '<span class="glyphicon glyphicon-flag" aria-hidden="true"></span></div>';
					html += '<div class="col-xs-11">';
					html += '<p>';
					html += parsedKey.eventName+' ('+parsedKey.guideName+') <br/>';
					html += parsedKey.evntStrtTme+'<br/>';
					html += parsedKey.evntEndTme ;
					html += '</p></div></div>';
					var serial=1;
					var reservedId;
					for(j in response[key]) {
						reservedId = response[key][j].reserveId;
						//alert('response'+reservedId);
						html += '<div class="reservation-data-section">';
						html += '<div class="col-xs-1">'+(serial++)+'</div>';
						html += '<div class="col-xs-8">';
						html += '<p>';
						html += response[key][j].email+'<br/>';
						html += '+'+response[key][j].phoneNumber+'</p>' ;
						html += '<button type="button" class="btn btn-default btn-blank" id="whiteList" onclick="addToWhiteList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/whitelist.png" /></button>';
						html += '<button type="button" class="btn btn-default btn-blank" id="blackList" onclick="addToBlackList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/blacklist.png" /></button>';
						html += '<button type="button" class="btn btn-default btn-blank" id="isPaid" onclick="setPaid('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/payment.png" /></button>';
						html += '<button type="button" class="btn btn-default btn-blank" id="isArrived" onclick="setArrived('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/arrived.png" /></button>';
						html += '<textarea class="form-control" readonly="true" id="inputEmail3" rows="1">'+response[key][j].notes+'\</textarea>';
						html += '</div>';
						html += '<div class="col-xs-3">';
						html += '<button class="btn btn-default btn-xs btn-danger">X</button>';
						html += ' <button class="btn btn-default  btn-xs btn-primary">Save</button>';
						html += '</div></div>';
					}
					html += '<div class="clearfix"></div>';
					html += '</form>';
					
					
				}
				//$("#reservedList").empty();
				$("#reservedList").html(html);
				$("#reservation_details").show();
			}
		 });
	 }
	 
	 function addToWhiteList(scheduleId,reserveId) {
		 
		 $.ajax({
			  url : "addTowhtlst.htm?reserveId="+reserveId+"&scheduleId="+scheduleId,
		 	 type : 'POST',
		  success : function(status) {
		 		 
		 		 $.ajax({
					url : "getReservedUsers.htm?scheduleId="+scheduleId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						for(key in response) {
							var parsedKey = JSON.parse(key);
							html += '<form class="form-horizontal">';
							html += '<div id="statusInfoDiv" align="center">';
							html += '<span id="statusInfoSpan" style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span></div>';
							html += '<div class="reservation-data-section">';
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicon glyphicon-flag" aria-hidden="true"></span></div>';
							html += '<div class="col-xs-11">';
							html += '<p>';
							html += parsedKey.eventName+' ('+parsedKey.guideName+') <br/>';
							html += parsedKey.evntStrtTme+'<br/>';
							html += parsedKey.evntEndTme ;
							html += '</p></div></div>';
							var serial=1;
							var reservedId;
							for(j in response[key]) {
								reservedId = response[key][j].reserveId;
								html += '<div class="reservation-data-section">';
								html += '<div class="col-xs-1">'+(serial++)+'</div>';
								html += '<div class="col-xs-8">';
								html += '<p>';
								html += response[key][j].email+'<br/>';
								html += '+'+response[key][j].phoneNumber+'</p>' ;
								html += '<button type="button" class="btn btn-default btn-blank" id="whiteList" onclick="addToWhiteList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/whitelist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="blackList" onclick="addToBlackList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/blacklist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isPaid" onclick="setPaid('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/payment.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isArrived" onclick="setArrived('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/arrived.png" /></button>';
								html += '<textarea class="form-control" readonly="true" id="inputEmail3" rows="1">'+response[key][j].notes+'\</textarea>';
								html += '</div>';
								html += '<div class="col-xs-3">';
								html += '<button class="btn btn-default btn-xs btn-danger">X</button>';
								html += ' <button class="btn btn-default  btn-xs btn-primary">Save</button>';
								html += '</div></div>';
							}
							html += '<div class="clearfix"></div>';
							html += '</form>';
							
							
						}
						//$("#reservedList").empty();
						$("#reservedList").html(html);
						$("#reservation_details").show();							
						$("#statusInfoSpan").html(status);
						$("#statusInfoDiv").show().fadeOut(4000);
					}
		 		});
		 	 }
		 });
		 
	 }
	 
	 function addToBlackList(scheduleId,reserveId) {

		 $.ajax({
			 url : "addToBlcklist.htm?reserveId="+reserveId+"&scheduleId="+scheduleId,
		 	 type : 'POST',
		 	 success : function(status) {
		 		$.ajax({
					url : "getReservedUsers.htm?scheduleId="+scheduleId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						for(key in response) {
							var parsedKey = JSON.parse(key);
							html += '<form class="form-horizontal">';
							html += '<div id="statusInfoDiv" align="center">';
							html += '<span id="statusInfoSpan" style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span></div>';
							html += '<div class="reservation-data-section">';
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicon glyphicon-flag" aria-hidden="true"></span></div>';
							html += '<div class="col-xs-11">';
							html += '<p>';
							html += parsedKey.eventName+' ('+parsedKey.guideName+') <br/>';
							html += parsedKey.evntStrtTme+'<br/>';
							html += parsedKey.evntEndTme ;
							html += '</p></div></div>';
							var serial=1;
							var reservedId;
							for(j in response[key]) {
								reservedId = response[key][j].reserveId;
								html += '<div class="reservation-data-section">';
								html += '<div class="col-xs-1">'+(serial++)+'</div>';
								html += '<div class="col-xs-8">';
								html += '<p>';
								html += response[key][j].email+'<br/>';
								html += '+'+response[key][j].phoneNumber+'</p>' ;
								html += '<button type="button" class="btn btn-default btn-blank" id="whiteList" onclick="addToWhiteList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/whitelist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="blackList" onclick="addToBlackList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/blacklist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isPaid" onclick="setPaid('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/payment.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isArrived" onclick="setArrived('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/arrived.png" /></button>';
								html += '<textarea class="form-control" readonly="true" id="inputEmail3" rows="1">'+response[key][j].notes+'\</textarea>';
								html += '</div>';
								html += '<div class="col-xs-3">';
								html += '<button class="btn btn-default btn-xs btn-danger">X</button>';
								html += ' <button class="btn btn-default  btn-xs btn-primary">Save</button>';
								html += '</div></div>';
							}
							html += '<div class="clearfix"></div>';
							html += '</form>';
							
							
						}
						//$("#reservedList").empty();
						$("#reservedList").html(html);
						$("#reservation_details").show();
						$("#statusInfoSpan").html(status);
						$("#statusInfoDiv").show().fadeOut(4000);
					}
				 });
		 	 }
		 });
	 }
	 
	 function setPaid(scheduleId,reserveId) {

		 $.ajax({
			 url : "setpaid.htm?reserveId="+reserveId,
		 	 type : 'POST',
		 	 success : function(status) {
		 		$.ajax({
					url : "getReservedUsers.htm?scheduleId="+scheduleId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						for(key in response) {
							var parsedKey = JSON.parse(key);
							html += '<form class="form-horizontal">';
							html += '<div id="statusInfoDiv" align="center">';
							html += '<span id="statusInfoSpan" style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span></div>';
							html += '<div class="reservation-data-section">';
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicon glyphicon-flag" aria-hidden="true"></span></div>';
							html += '<div class="col-xs-11">';
							html += '<p>';
							html += parsedKey.eventName+' ('+parsedKey.guideName+') <br/>';
							html += parsedKey.evntStrtTme+'<br/>';
							html += parsedKey.evntEndTme ;
							html += '</p></div></div>';
							var serial=1;
							var reservedId;
							for(j in response[key]) {
								reservedId = response[key][j].reserveId;
								html += '<div class="reservation-data-section">';
								html += '<div class="col-xs-1">'+(serial++)+'</div>';
								html += '<div class="col-xs-8">';
								html += '<p>';
								html += response[key][j].email+'<br/>';
								html += '+'+response[key][j].phoneNumber+'</p>' ;
								html += '<button type="button" class="btn btn-default btn-blank" id="whiteList" onclick="addToWhiteList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/whitelist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="blackList" onclick="addToBlackList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/blacklist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isPaid" onclick="setPaid('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/payment.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isArrived" onclick="setArrived('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/arrived.png" /></button>';
								html += '<textarea class="form-control" readonly="true" id="inputEmail3" rows="1">'+response[key][j].notes+'\</textarea>';
								html += '</div>';
								html += '<div class="col-xs-3">';
								html += '<button class="btn btn-default btn-xs btn-danger">X</button>';
								html += ' <button class="btn btn-default  btn-xs btn-primary">Save</button>';
								html += '</div></div>';
							}
							html += '<div class="clearfix"></div>';
							html += '</form>';
							
							
						}
						//$("#reservedList").empty();
// 						$("#reservedList").html(html);
						$("#reservation_details").show();
						$("#statusInfoSpan").html(status);
						$("#statusInfoDiv").show().fadeOut(4000);
					}
				 });
		 	 }
		 });
	 }
	 
	 function setArrived(scheduleId,reserveId) {

		 $.ajax({
			 url : "setarrived.htm?reserveId="+reserveId,
		 	 type : 'POST',
		 	 success : function(status) {
		 		$.ajax({
					url : "getReservedUsers.htm?scheduleId="+scheduleId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						var html = '';
						for(key in response) {
							var parsedKey = JSON.parse(key);
							html += '<form class="form-horizontal">';
							html += '<div id="statusInfoDiv" align="center">';
							html += '<span id="statusInfoSpan" style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span></div>';
							html += '<div class="reservation-data-section">';
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicon glyphicon-flag" aria-hidden="true"></span></div>';
							html += '<div class="col-xs-11">';
							html += '<p>';
							html += parsedKey.eventName+' ('+parsedKey.guideName+') <br/>';
							html += parsedKey.evntStrtTme+'<br/>';
							html += parsedKey.evntEndTme ;
							html += '</p></div></div>';
							var serial=1;
							var reservedId;
							for(j in response[key]) {
								reservedId = response[key][j].reserveId;
								html += '<div class="reservation-data-section">';
								html += '<div class="col-xs-1">'+(serial++)+'</div>';
								html += '<div class="col-xs-8">';
								html += '<p>';
								html += response[key][j].email+'<br/>';
								html += '+'+response[key][j].phoneNumber+'</p>' ;
								html += '<button type="button" class="btn btn-default btn-blank" id="whiteList" onclick="addToWhiteList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/whitelist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="blackList" onclick="addToBlackList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/blacklist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isPaid" onclick="setPaid('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/payment.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isArrived" onclick="setArrived('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/arrived.png" /></button>';
								html += '<textarea class="form-control" readonly="true" id="inputEmail3" rows="1">'+response[key][j].notes+'\</textarea>';
								html += '</div>';
								html += '<div class="col-xs-3">';
								html += '<button class="btn btn-default btn-xs btn-danger">X</button>';
								html += ' <button class="btn btn-default  btn-xs btn-primary">Save</button>';
								html += '</div></div>';
							}
							html += '<div class="clearfix"></div>';
							html += '</form>';
							
							
						}
						//$("#reservedList").empty();
						$("#reservedList").html(html);
						$("#reservation_details").show();
						$("#statusInfoSpan").html(status);
						$("#statusInfoDiv").show().fadeOut(4000);
					}
				 });
		 	 }
		 });
	 }
	 
	 function showReservationPopup(scheduleId) {
		//alert('global schdld id'+globalScheduleId);
		 $.ajax({
				url : "getReservedUsers.htm?scheduleId="+scheduleId,
				type : 'POST',
				success : function(data) {
					var response = jQuery.parseJSON(data);
					var html = '';
					for(key in response) {							
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
						html += parsedKey.evntStrtTme+'<br />';
						html += parsedKey.evntEndTme+'</p></div>';
						html += '<div class="col-xs-1">';
						html += '<span class="glyphicons glyphicon-times" aria-hidden="true">#</span></div>';
						html += '<div class="col-xs-11">';
						html += '<p>'+parsedKey.eventName+'</p>';							
						html += '<select class="form-control" id="noOfSeats">';
						html += '<option value="0">Select Tickets</option>';
						var seats = getIncrementalArray(parsedKey.availableSeats);
						for(var i=1;i<=seats.length;i++) {	
							if(i<10) {
								html += '<option value="'+i+'">&nbsp;&nbsp;'+i+' Seat(s)</option>';
							}else {
								html += '<option value="'+i+'">'+i+' Seat(s)</option>';
							}
							
						}
						html += '</select>';
						html += '<div id="seatsStatusDiv" align="center">';
						html += '<span id="seatsStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span></div></div>';
					}
						
					$("#selectSeats").html(html);
					$("#myreservation").show();
			}
		});
	 }
	 function showAllResvList(){
			alert('global schdld id'+globalScheduleId);
			 $.ajax({
					url : "getReservedUsers.htm?scheduleId="+globalScheduleId,
					type : 'POST',
					success : function(data) {
						var response = jQuery.parseJSON(data);
						alert('response'+response);
						var html = '';
						for(key in response) {
							var parsedKey = JSON.parse(key);
							alert('response'+parsedKey);
							html += '<form class="form-horizontal">';
							html += '<div id="statusInfoDiv" align="center">';
							html += '<span id="statusInfoSpan" style="margin: 0px 100px 0px; color: green; font-weight: 600;"></span></div>';
							html += '<div class="reservation-data-section">';
							html += '<div class="col-xs-1">';
							html += '<span class="glyphicon glyphicon-flag" aria-hidden="true"></span></div>';
							html += '<div class="col-xs-11">';
							html += '<p>';
							html += parsedKey.eventName+' ('+parsedKey.guideName+') <br/>';
							html += parsedKey.evntStrtTme+'<br/>';
							html += parsedKey.evntEndTme ;
							html += '</p></div></div>';
							var serial=1;
							var reservedId;
							for(j in response[key]) {
								reservedId = response[key][j].reserveId;
								//alert('response'+reservedId);
								html += '<div class="reservation-data-section">';
								html += '<div class="col-xs-1">'+(serial++)+'</div>';
								html += '<div class="col-xs-8">';
								html += '<p>';
								html += response[key][j].email+'<br/>';
								html += '+'+response[key][j].phoneNumber+'</p>' ;
								html += '<button type="button" class="btn btn-default btn-blank" id="whiteList" onclick="addToWhiteList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/whitelist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="blackList" onclick="addToBlackList('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/blacklist.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isPaid" onclick="setPaid('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/payment.png" /></button>';
								html += '<button type="button" class="btn btn-default btn-blank" id="isArrived" onclick="setArrived('+parsedKey.scheduleId+', '+reservedId+')"><img src="images/arrived.png" /></button>';
								html += '<textarea class="form-control" readonly="true" id="inputEmail3" rows="1">'+response[key][j].notes+'\</textarea>';
								html += '</div>';
								html += '<div class="col-xs-3">';
								html += '<button class="btn btn-default btn-xs btn-danger">X</button>';
								html += ' <button class="btn btn-default  btn-xs btn-primary">Save</button>';
								html += '</div></div>';
							}
							html += '<div class="clearfix"></div>';
							html += '</form>';
							
							
						}
						$("#myreservation").hide();
						$("#reservedList").html(html);
						$("#reservation_details").show();
					}
				 });
	 }
	 
	 function saveReservation(e) {
		  var userName = $("#guestUser").val();
		  var email = $("#guestEmail").val();
		  var phone = $("#guestPhone").val();
		  var notes = $("#notes").val();
		  var slctdSeats = $("#noOfSeats").val();
		  
		  
		  //Validations start--------------------------------------------
		  if($.trim(userName).length == 0){
			   var alertmsg = 'Please enter valid user Name';
			  $('#useNameStatus').html(alertmsg).fadeOut(4000);
			   //e.preventDefault();
		  }
		  
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
  		  
  		  if ($.trim(slctdSeats).length == 0) {
			   var alertmsg = 'Please select number of seats to reserve';
			   $('#seatsStatus').html(alertmsg).fadeOut(4000);
			 e.preventDefault();
		   }
  		  //validations end -----------------------------------------------------------
  		  
  		  var json = '{"userName":"'+userName+'","email":"'+email+'","phone":"'+phone+'","slctdSeats":"'+slctdSeats+'","notes":"'+notes+'","scheduleId":"'+globalScheduleId+'","strtTime":"'+gStrtTme+'","endTime":"'+gEndTme+'","duration":"'+gDuration+'","availSeats":"'+gAvailSeats+'","eventName":"'+gEventName+'"}';
  		  var parseJson = JSON.parse(json);
  		  var duration = gDuration.slice(0,1);
  		  $.ajax({
  			 url : "saveGuestReservationInfo.htm",
  			 type  : 'POST',
			 data  : {json:json},
			 success : function(data){
				 var response = jQuery.parseJSON(data);
				 var html1 = '';
				 var html2 = '';
				 var html3 = '';
				 
				 html1 += '<div class="col-xs-12">';
				 html1 += '<div class="alert alert-success" role="alert" style="padding:5px 12px;margin-bottom:5px;">';
				 html1 += '<strong><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> Success!</strong> You successfully made reservation. </div>';
				 html1 += '<p class="center">Reference Number : <b>'+response+'</b></p></div>';
				 
				 html2 += '<div class="col-xs-1">';
				 html2 += '<span class="glyphicon glyphicon-time" aria-hidden="true"></span></div>';
				 html2 += '<div class="col-xs-11"><p>';
				 html2 += gStrtTme+'<br />';
				 html2 += gEndTme ;
				 html2 += '<span style="float:right;">'+duration+' hours</span><br/>';
				 html2 += slctdSeats+' Seat(s)';
				 html2 += '</p></div>';
				 
				 html3 += '<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>';
				 html3 += '<div class="col-xs-11"><p>';
				 html3 += userName+' <br/>';
				 html3 += email+' <br/>';
				 html3 += '+'+phone+'</p></div>' ;
				 
				 $("#refConfirm").html(html1);
				 $("#timeConfirm").html(html2);
				 $("#userConfirm").html(html3);
				 
				 $("#myreservation").hide();
				 $("#myreservation_confirm").show();
				 
			}
  		  });
	 }		 
	 
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
	 
	 function getIncrementalArray(noOfSeats) {
			var arr = new Array;
			for(var i=1;i<=noOfSeats;i++) {
				arr.push((i));
			}
			return arr;			
		}
	 
	 function contains(arr , str) {
		 for(var i=0;i<arr.length;i++) {
			 if(arr[i].evntStrtTme == str) {
				 return arr[i];
			 }
		 }
		 return null;
	 }
	 
	 function formatDate(date) {
		 var frmtdDt = date.slice(0,2)+"-"+date.slice(3,5)+"-"+date.slice(6,10) ;
		 return frmtdDt;
	 }
	 
	 
	 function onLoad() {
		 var eventId = '0';
		 var guideId = $('#availGuides').val();
		 var clickedDt = currentDt;
		 var frmtdDt = formatDate(clickedDt);
		 
		 var seatCount= $('#countId').val();
			//alert('count'+seatCount);
		 
		 $.ajax({
				url : "selectedDate.htm?clickedDt="+clickedDt+"&eventId="+eventId+"&guideId="+guideId,
				type : 'POST',
				success : function(data) {
					var response = jQuery.parseJSON(data);
					var k=0;
					var map = {'08:00':'eight','08:30':'eightthirty','09:00':'nine','09:30':'ninethirty','10:00':'ten','10:30':'tenthirty',
							'11:00':'eleven','11:30':'eleventhirty','12:00':'twelve','12:30':'twelvethirty','13:00':'thirteen','13:30':'thirteenthirty','14:00':'fourteen',
							'14:30':'fourteenthirty','15:00':'fifteen','15:30':'fifteenthirty','16:00':'sixteen','16:30':'sixteenthirty','17:00':'seventeen','17:30':'seventeenthirty',
							'18:00':'eighteen','18:30':'eighteenthirty','19:00':'nineteen','19:30':'nineteenthirty'};


                     var minEvent = {'08:00':'','08:30':'','09:00':'','09:30':'','10:00':'','10:30':'',
							          '11:00':'','11:30':'','12:00':'','12:30':'','13:00':'','13:30':'','14:00':'',
							         '14:30':'','15:00':'','15:30':'','16:00':'','16:30':'','17:00':'','17:30':'',
							         '18:00':'','18:30':'','19:00':'','19:30':''};
					
					var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
					var dateSet = Object.keys(response);
					var html1 = '';
					var html2 = '';
					var count = 0;
					//1. for displaying primary dates
					html1 += '<div class="piece">';
	 				html1 += '<div class="small_square">';
	 				for(var a=0,key = Object.keys(response),aa=key.length;a<aa;a++) {
	 					if(a==0 || a==14) {
	 						var monthNum = key[a].slice(3,5);
                 			 monthNum = (monthNum-1) ;
                 			 jQuery.each( monthArray, function( i, mnthName ) {
                            	 	if(monthNum == i){
                            	 		html1 += '<span class="month_title">'+mnthName+'</span>';
                            	 	}
                            	});
	 					}
	 					var bool = isSeatsAvailable(response[key[a]], seatCount);
	 					if(currentDt == key[a]){
	 						html1 += '<span class="cal_date current_date hiddenSpan">'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';		 				 
	 					}
	 					else if(bool == true)
	 					{
	 						html1 += '<span class="cal_date green hiddenSpan" >'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';
	 					}
	 					else{
	 						html1 += '<span class="cal_date hiddenSpan" >'+key[a].slice(0,2)+'';
	 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
	 						html1 += '</span>';
	 					}
	 				}
	 				html1 += '</div>';
	 				html1 += '</div>';
	 				
	 				$('#datesListDiv').html(html1);
	 				$('#datesListDiv').show();
	 				//end of primary dates display
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
										globalScheduleId=val.scheduleId;
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
										if(val.userNotes!=null) {
											html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
										}
										if(val.isPaid==true) {
											
											html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

										}
										if(val.isArrived==true) {
											html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
										}
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
										html2 += '<a href="javascript:void(0)" onclick="showReservationPopup('+val.scheduleId+')">';
										if(val.availableSeats > 2) {
											html2 += '>2 <br/>'+val.guideName+'</a>';
										}else {
											html2 += val.availableSeats+'<br/>'+val.guideName+'</a>';
										}
										html2 += '</p></div>';								
									}else {
									html2 += '<div class="cell eventbooked-'+val.divCount+'">';
									html2 += '<div class="cell_event_addition-info">';
									if(val.userNotes!=null) {
										html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
									}
									if(val.isPaid==true) {
										
										html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

									}
									if(val.isArrived==true) {
										html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
									}
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
	 					//alert("Key :: "+keyVal+" Value :: "+minEvent[keyVal]);
	 					if(minEvent[keyVal] != '') {
	 						var id = '#'+map[keyVal] ;		 						
	 						//alert("adding green class");
	 						//alert("id to add is : "+id);
	 						//alert("#'+map[keyVal]+'\");
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
		 var eventId = $("#selectTour").val();
		 var guideId = $("#availGuides").val();
		 var frmtdDt = formatDate(clickedDt);
		 var seatCount= $('#countId').val();
			//alert('count'+seatCount);
		 
		 $.ajax({
			url : "selectedDate.htm?clickedDt="+clickedDt+"&eventId="+eventId+"&guideId="+guideId,
			type : 'POST',
			success : function(data) {
				var response = jQuery.parseJSON(data);
				var k=0;
				var map = {'08:00':'eight','08:30':'eightthirty','09:00':'nine','09:30':'ninethirty','10:00':'ten','10:30':'tenthirty',
						'11:00':'eleven','11:30':'eleventhirty','12:00':'twelve','12:30':'twelvethirty','13:00':'thirteen','13:30':'thirteenthirty','14:00':'fourteen',
						'14:30':'fourteenthirty','15:00':'fifteen','15:30':'fifteenthirty','16:00':'sixteen','16:30':'sixteenthirty','17:00':'seventeen','17:30':'seventeenthirty',
						'18:00':'eighteen','18:30':'eighteenthirty','19:00':'nineteen','19:30':'nineteenthirty'};


                 var minEvent = {'08:00':'','08:30':'','09:00':'','09:30':'','10:00':'','10:30':'',
						          '11:00':'','11:30':'','12:00':'','12:30':'','13:00':'','13:30':'','14:00':'',
						         '14:30':'','15:00':'','15:30':'','16:00':'','16:30':'','17:00':'','17:30':'',
						         '18:00':'','18:30':'','19:00':'','19:30':''};
				
				var monthArray = ['Jan','Feb','Mar', 'Apr', 'May','June','July','Aug','Sep','Oct','Nov','Dec'];
				var dateSet = Object.keys(response);
				var html1 = '';
				var html2 = '';
				var count = 0;
				//1. for displaying primary dates
				html1 += '<div class="piece">';
 				html1 += '<div class="small_square">';
 				for(var a=0,key = Object.keys(response),aa=key.length;a<aa;a++) {
 					if(a==0 || a==14) {
 						var monthNum = key[a].slice(3,5);
             			 monthNum = (monthNum-1) ;
             			 jQuery.each( monthArray, function( i, mnthName ) {
                        	 	if(monthNum == i){
                        	 		html1 += '<span class="month_title">'+mnthName+'</span>';
                        	 	}
                        	});
 					}
 					var bool = isSeatsAvailable(response[key[a]], seatCount);
 					if(currentDt == key[a]){
 						html1 += '<span class="cal_date current_date hiddenSpan">'+key[a].slice(0,2)+'';
 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
 						html1 += '</span>';		 				 
 					}
 					else if(bool == true)
 					{
 						html1 += '<span class="cal_date green hiddenSpan" >'+key[a].slice(0,2)+'';
 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
 						html1 += '</span>';
 					}
 					else{
 						html1 += '<span class="cal_date hiddenSpan" >'+key[a].slice(0,2)+'';
 						html1 += '<input type = "hidden" value="'+key[a]+'" />';
 						html1 += '</span>';
 					}
 				}
 				html1 += '</div>';
 				html1 += '</div>';
 				
 				$('#datesListDiv').html(html1);
 				$('#datesListDiv').show();
 				//end of primary dates display
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
									globalScheduleId=val.scheduleId;
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
									if(val.userNotes!=null) {
										html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
									}
									if(val.isPaid==true) {
										
										html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

									}
									if(val.isArrived==true) {
										html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
									}
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
									html2 += '<a href="javascript:void(0)" onclick="showReservationPopup('+val.scheduleId+')">';
									if(val.availableSeats > 2) {
										html2 += '>2 <br/>'+val.guideName+'</a>';
									}else {
										html2 += val.availableSeats+'<br/>'+val.guideName+'</a>';
									}
									html2 += '</p></div>';								
								}else {
								html2 += '<div class="cell eventbooked-'+val.divCount+'">';
								html2 += '<div class="cell_event_addition-info">';
								if(val.userNotes!=null) {
									html2 += '<span class="glyphicon glyphicon-pencil left" aria-hidden="true" style="color: brown;"></span>';
								}
								if(val.isPaid==true) {
									
									html2 += '<span class="glyphicon right" aria-hidden="true" style="margin-right: 3px;">P</span>';

								}
								if(val.isArrived==true) {
									html2 += '<span class="glyphicon glyphicon-ok right" aria-hidden="true" style="margin-right: 10px;"></span>';
								}
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
 					//alert("Key :: "+keyVal+" Value :: "+minEvent[keyVal]);
 					if(minEvent[keyVal] != '') {
 						var id = '#'+map[keyVal] ;		 						
 						//alert("adding green class");
 						//alert("id to add is : "+id);
 						//alert("#'+map[keyVal]+'\");
 						$(id).addClass("green");
 					}
 				}

 				
 				$('#dateButton').html(frmtdDt);
				$('#carousalDatesLst').html(html2);
				$('.ajax_loader_inner').removeClass();						
			}
		 });
		 
	 });
	 
	 function resetPage() {
		document.location.href = "returnToServiceByguide.htm";
	 }
	 
	 var total_left=0;
 	var click_count=0;
 	var scroll_limit=32;
 	var total_scroll=0;
 	$(document).on('click','.carousel-control.right',function()
 	{ 
 	  if(total_scroll <= arrLength){
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
  
  <input type="hidden" value="${mode}" id="mode"> 
  <body style="margin:20px 0px;">
  <input type="hidden" id="orgName" value="${sessionScope.event.eventOrgName}" />
  <input type="hidden" id="orgAddress" value="${sessionScope.event.eventOrgAddress}" />
  <input type="hidden" id="orgPhone" value="${sessionScope.event.eventOrgPhoneNumber}" />
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
<!-------------------------------- -------------MYRESERVATION DATA MODAL --------------------------------------------->
				<div id="myreservation" class="white_content">
			<h3>Reservation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myreservation').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal" id="reservationDiv">
					<div class="reservation-data-section">
					<div class="col-xs-1">
					<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
					</div>
					<div class="col-xs-11">
					<p>
					${sessionScope.event.eventOrgName} <br/>
					${sessionScope.event.eventOrgAddress}<br/>
					${sessionScope.event.eventOrgPhoneNumber}
					<!-- Couching Simone, Garrison<br/>
					Woodway 115-445, Prisbane, GB Sooulfolk<br/>
					49-99-999-9999 -->
					</p>
					</div>
					</div>
										<div class="reservation-data-section" id="selectSeats">
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
					<div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11">
							<input type="text" class="form-control margin-bottom-10" placeholder="User Name" id="guestUser">
							<div id="userNameStatusDiv" align="center">
							<span id="useNameStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
							</div>
							<input type="text" class="form-control margin-bottom-10" placeholder="Email" id="guestEmail">
							<div id="EmailStatusDiv" align="center">
							<span id="EmailStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
							</div>
							<input type="text" class="form-control margin-bottom-10" placeholder="Phone" id="guestPhone">
							<div id="PhoneStatusDiv" align="center">
							<span id="PhoneStatus" style="margin: 0px 100px 0px; color: red; font-weight: 600;"></span>
							</div>
						</div>
					</div>
					<div class="col-xs-12" style="padding:20px 20px 6px 20px;">
						<div class="form-group">
							<div class="col-xs-1">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</div>
							<div class="col-xs-11">
							  <textarea class="form-control" id="notes" rows="1"></textarea>
							</div>
						</div>
					
					</div>
					<div class="clearfix"></div>
				   <div style="padding:0px 100px;" class="button_padding">
					<div class="form-group">
						<div class="col-xs-4" >
						  <button type="button" class="btn btn-default btn-save"  id="reserveSeats" onclick="saveReservation('')">Reserve Now</button>
						</div>
						<div class="col-xs-4 nopadding" >
						  <!-- <a href="javascript:void(0)" class="btn btn-default btn-save" onclick = "showAllResvList()">Reservation List</a> -->
						    <button type="button" class="btn btn-default btn-save"  onclick = "showAllResvList()">Reservation List</button>
						</div>
						<div class="col-xs-4" >
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
<!-------------------------------- -------------RESERVATION DATA DETAILS MODAL --------------------------------------------->
			<div id="reservation_details" class="white_content">
			<h3>Reservation Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('reservation_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
		<div class="eventreservation_scroller" id="reservedList">
				<!-- <form class="form-horizontal">
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
										ends
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
										/ends
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
				</form> -->
				<!--p class="center" style="padding:10px 0px 0px 0px;margin-bottom:2px;" >Reservation Process Continues for next <span id="log">60</span> Secs</p--> 
				<!--div id="flash" class="center" ></div-->
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
						<div class="col-xs-4" >
						  <button type="submit" class="btn btn-default btn-save">Reserve table</button>
						</div>
						<div class="col-xs-4 nopadding" >
						  <a href="javascript:void(0)" class="btn btn-default btn-save" onclick="document.getElementById('myreservation').style.display='none';document.getElementById('myreservation_details').style.display='block';document.getElementById('fade').style.display='block'">Reservation List</a>
						</div>
						<div class="col-xs-4" >
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
<!-------------------------------- ADMIN RESERVATION DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_myreservation_details" class="white_content" style="left:8%;width:85%;">
			<h3>Customer Reservation Details</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_myreservation_details').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
			<div class="col-xs-12" style="margin-bottom:10px;">
				<div class="col-xs-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-xs-2">
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
					<div class="col-xs-6 col-xs-6">
						<p>
						<strong>Name of Cutomer</strong><br/>
						+49-99-999-9999<br/>
						customer@gmail.com
						</p>
					</div>
					<div class="col-xs-6 col-xs-6">
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
					<div class="col-xs-6 col-xs-6">
						<strong>Name Of Customer</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-6 col-xs-6">
						<p>
						Donnerstag, 14 July 2014<br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					
					</div>
					<!--ends-->
					<div class="reservation-data-section">
					<div class="col-xs-6 col-xs-6">
						<strong>Restaurant "Pegasus"</strong><br/>
						Woodway 115-445, Prisbane, GB Sooulfolk<br/>
						49-99-999-9999
						</p>
					</div>
					<div class="col-xs-6 col-xs-6">
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
			
<!-------------------------------- RESERVATION CONFIRMATION DATA MODAL --------------------------------------------->
			<div id="myreservation_confirm" class="white_content">
			<h3>Reservation Confirmation</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('myreservation_confirm').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div style="padding:0px">
				<form class="form-horizontal">
						<div class="reservation-data-section" id="refConfirm">
<!-- 
						<div class="col-xs-12">
								<div class="alert alert-success" role="alert" style="padding:5px 12px;margin-bottom:5px;">
								  <strong><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> Success!</strong> You successfully made reservation.
								</div>
						<p class="center">Reference Number : <b>NHDJU6438</b></p>
						</div> -->
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
					${sessionScope.event.eventOrgName} <br/>
					${sessionScope.event.eventOrgAddress}<br/>
					${sessionScope.event.eventOrgPhoneNumber}
					</p>
					</div>
					</div>
										<div class="reservation-data-section" id="timeConfirm">
										<!-- <div class="col-xs-1">
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
										</div> -->
										</div>
					<div class="reservation-data-section" id="userConfirm">
					<!-- <div class="col-xs-1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></div>
						<div class="col-xs-11">
							<p>
							David Adams<br/>
							davidadamas@gmail.com<br/>
							+49-888-8888 
							</p>
						</div> -->
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
<!-------------------------------- CUSTOMERS LIST DETAILS DATA MODAL --------------------------------------------->
			<div id="admin_customer_list" class="white_content" style="left:8%;width:85%;">
			<h3>Customes List</h3>
			<a href ="javascript:void(0)" class="close_popup" onclick = "document.getElementById('admin_customer_list').style.display='none';document.getElementById('fade').style.display='none'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></a>
			<div class="user_data_wrapper" style="padding:0px">
			<div class="col-xs-12" style="margin-bottom:10px;">
				<div class="col-xs-10 nopadding">
				<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-xs-2">
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
        <div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
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
        <div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
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
        <div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
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
        <div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
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
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
			<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
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
					<div class="col-xs-12" style="margin-bottom:10px;">
				<div class="col-xs-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-xs-2">
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
											<div class="col-xs-12">Since : 02-09-15</div>
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
											<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
												<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
												<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
												<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
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
											<div class="col-xs-6">Since : 02-09-15</div>
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
											<div class="col-xs-6">Since : 02-09-15</div>
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
												<div class="col-xs-6">Since : 02-09-15</div>
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
					<div class="col-xs-12 nopadding" style="margin-bottom:10px;">
				<div class="col-xs-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-xs-2">
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
							<div class="col-xs-12 listpad">
								<div class="col-xs-4 nopadding">Adman Herlary</div>
								<div class="col-xs-4">adman@hotmail.com</div>
								<div class="col-xs-4">+77-327928289</div>
							</div>
							<div class="col-xs-12 listpad">
								<div class="col-xs-4 nopadding">Herlary wildtone</div>
								<div class="col-xs-4">adman@hotmail.com</div>
								<div class="col-xs-4">+77-327928289</div>
							</div>
							<div class="col-xs-12 listpad">
								<div class="col-xs-4 nopadding">George hangman</div>
								<div class="col-xs-4">adman@hotmail.com</div>
								<div class="col-xs-4">+77-327928289</div>
							</div>
												<div class="col-xs-12 listpad">
								<div class="col-xs-4 nopadding">Ad M jacky</div>
								<div class="col-xs-4">adman@hotmail.com</div>
								<div class="col-xs-4">+77-327928289</div>
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
						 <label for="inputEmail3" class="col-xs-3 control-label">Room Category</label>
							<div class="col-xs-8">
								<select class="form-control" name="language">
								<option value="0">Select Category</option>
								<option value="0">Single</option>
								<option value="0" selected="">Double</option>
								<option value="0">Suite</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-3 control-label">Room Number</label>
							<div class="col-xs-8">
								<select class="form-control" name="language">
								<option value="0">Select Remainder</option>
								<option value="0">Room 1</option>
								<option value="0" selected="">Room 2</option>
								<option value="0">Room 3</option>
							  </select>
							</div>
						</div>
						<div class="form-group">
						 <label for="inputEmail3" class="col-xs-3 control-label"></label>
							<div class="col-xs-8">
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
					<div class="col-xs-12" style="margin-bottom:10px;">
				<div class="col-xs-10 nopadding">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div>
				</div>
				<div class="col-xs-2">
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
											<div class="col-xs-6">Since : 02-09-15</div>
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
											<div class="col-xs-12">Since : 02-09-15</div>
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
											<div class="col-xs-12">Since : 02-09-15</div>
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
											<div class="col-xs-6">Since :02-09-15</div>
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
												<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
												<div class="col-xs-6">WhiteList - Touren</div><div class="col-xs-6">02-09-15</div>
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
							<img src="images/greenpat.png" />
						  </button>
						  <ul class="dropdown-menu" role="menu">
							<li><a href="#"><img src="images/brownpat.png" style="padding-right:6px;" /></a></li>
							<li><a href="seriveprovider_view.html"><img src="images/greenpat.png" style="padding-right:6px;" /></a></li>
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
				<div class="col-xs-2 nopadding" ><button type="button" class="btn btn-default btn-current-date" ondblclick="resetPage()" id="dateButtonDiv"><span id="dateButton"><!-- 06-06-2015 --></span></button></div>
				<div class="col-xs-4">
					<select class="selectpicker form-control" name="" id="selectTour">
						<!-- <option value="0">Select Tour</option>
						<option value="1">Tour 1</option>
						<option value="1">Tour 2</option>				
						<option value="2">Tour 3</option>
						<option value="3">All Tours</option> -->
					</select>
				</div>
				<div class="col-xs-4 nopadding">
					<select class="selectpicker form-control" name="" id="availGuides">
						<!-- <option value="0">Select Guide</option>
						<option value="1">Guide 1</option>
						<option value="1">Guide 2</option>				
						<option value="2">Guide 3</option>
						<option value="3">Guide 4</option> -->
					</select>
				</div>
				<div class="col-xs-2" style="padding-right:0px;">
					<input type="number" class="form-control" name=""  id="countId" Placeholder="Count"  onchange="slctCount()"/>
				</div>
			<div class="clearfix"></div>
			<div class="ajax_container">
			<div class="ajax_loader"></div>
				<div class="col-xs-12 nomargin_lf_rt date_wrapper">
							<div id="thumbcarousel1" class="carousel slide" data-ride="carousel">
							  <!-- Wrapper for slides -->
							   <div class="carousel-caption1" id="datesListDiv">
										
								</div><!-- carousel-caption1 -->
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
				<div class="timeline timeline_object" id="showStrtAndEndTimeDiv" style="top: 0px;"> 
					<div class="cell">08:00</div>
					<div class="cell">08:30</div>
					<div class="cell">09:00</div>
					<div class="cell">09:30</div>
					<div class="cell">10:00</div>
					<div class="cell">10:30</div>
					<div class="cell">11:00</div>
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
					<div class="cell">17:30</div>
					<div class="cell">18:00</div>
					<div class="cell">18:30</div>
					<div class="cell">19:00</div>
					<div class="cell">19:30</div>
				</div><!-- timeline close. -->
				
							<div class="ajax_container_inner">
							<div class="ajax_loader_inner"></div>
							<div id="thumbcarousel2" class="carousel carousel_info slide" data-ride="carousel" style="padding-right:19px;">
							  <div class="carousel-inner" role="listbox" id="carousalDatesLst">
									
							  </div>

							  <!-- Controls -->
							  <a class="myleft carousel-control" href="#thumbcarousel2" role="button" data-slide="prev" style="left:0px;">
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
		<li><a href="#">AGB</a></li>
		<li><a href="#">Impressum</a></li>
		<li><a href="#">Admin</a></li>
		<li><a href="#">Über Reservat</a></li>
		<li><a href="#">Help</a></li>
		</ul>
	</div>

	<!-- ends-->
<!--Main COntent ends -->
</div>
	<!-- custom scrollbar plugin -->
	<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
</body>
</html>
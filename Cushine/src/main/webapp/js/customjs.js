var handleDragStart;
var handleDragOver;
var handleDragLeave;
var handleDrop;
var fullOn;
$(document).ready(function()
	{
	$('#share-email').tooltip();
	$('.ajax_loader').hide();
		$('.carousel').carousel({
		interval: false,
		}) ;
	   /* ----------------------------------------------------------- */
   /*  Contact map
   /* ----------------------------------------------------------- */
    /*var map;
        map = new GMaps({
          div: '#map',
          lat: 23.709921,
          lng: 90.407143,
          scrollwheel: true,
          panControl: true,
          zoomControl: true,
        });

        map.addMarker({
          lat: 23.709921,
          lng: 90.407143,
    });*/
	//Full screen
	$(document).on('click','#fs',function(){
		if($(this).hasClass("full_on")){
				$(this).removeClass("full_on");
				$(".dummyfull").removeClass('fullwidth');
				$(this).attr("src","images/fullscreen.png");
				$(".col-xs-3-fixed").css('width','14.2%');
				$.session.set('fullOn', false);
			}else{
				$(this).addClass("full_on");
				$(".dummyfull").addClass('fullwidth');
				$(this).attr("src","images/fs-min.png");
				$(".col-xs-3-fixed").css('width','14.2%');
				$.session.set('fullOn', true);//true
				
			}
	});
	//check and re-direct to registration popup
	$(document).on('click','#goto_reg',function()
	{
		$("#mylogin").hide();
		$("#my_registration").show();
	});
	//Share email addresss with servive provider
	 $(document).on('click','#share-email',function()
	 {
	  var $aSelected = $('#share-email').find('.shareon');
	  if($(this).hasClass('shareon'))
	  { 
	   $(this).removeClass('shareon');
	   $(this).attr('title', 'Turn Off Sharing').tooltip('fixTitle').tooltip('show');
	   $(".glyphicon-envelope").addClass("active_share");
	  }
	  else{
	   $(this).attr('title', 'Share Email Address').tooltip('fixTitle').tooltip('show');
	   $(this).addClass('shareon');
	   $(".glyphicon-envelope").removeClass("active_share");
	  }
	 });
	//Auto load after 3 sec
	/*	setInterval(function()
		{
		$('.ajax_loader').hide()
		},3000);
	*/
	//Again get current address on double click
	/*$(".btn-current-date").dblclick(function()
	{
	$(this).html('18-06-2015');
	
	});*/
	//removing processing class on close popup
	$(document).on('click','.close_popup',function()
	{
		$('.processing').removeClass("processing");
	});
	//Mark Date on click
	$(document).on('click','.cal_date',function()
	{
		var $aSelected = $('.small_square').find('.cal_date');
		if( $aSelected.hasClass('mark_date') ){
			$aSelected.removeClass('mark_date');
			}
				if($('.small_square').find('.mark_date'))
				$(this).removeClass('mark_date');
					$(this).addClass("mark_date");
					//$(".btn-current-date").text($(this).attr('date-value'));
					$('.ajax_loader_inner').show();
	});
	//Mark time on click
	$(document).on('click','.timeline .cell',function()
			{
				$(".timescale").css("background","none");
				$(this).css("background","url(images/arrow.png) bottom no-repeat");
				$('.ajax_loader_inner').show();	
			});
/*=============================== Event Reservation Jquery functionality ============================= */

$(document).on('click','.cell_byobject',function()
{
	var $aSelected = $('.cell_byobject').find('.cell_header');
	/*if($(this).hasClass('mark_date'))
	{
		$(this).removeClass('mark_date');

	}
			else{*/
			$('.cell_byobject').removeClass("mark_date");
			$(this).addClass("mark_date");
			/*}*/
				//if($('.cell_byobject').find('.cell_header'))
				//$(this).removeClass('mark_date');
					

	
	
});
/* ================================  ENDS =============================================================*/
var minute = 0,secondsRemaining = 0;
var timer ;
$(document).on('click','.close_popup',function(){
	minute = 0;
	secondsRemaining = 60;
	log(minute + ':' + secondsRemaining);
	$('#log').html('0:60');
	$('#myreservation').hide();
	$('#hideTimer').css({'display': 'true'});
	$('#button_padding').css({'display': 'true'});

	setTime();
	
});
$(document).on('click','.btn-save',function(){
	//setTime();
});
function setTime(){
	clearTimeout(timer);
}
//pointing
var count = 60;
var callsPerSecond = 1;
var delay = 1000;
	$(document).on('click','.booked',function(){
		//$(this).addClass("processing");
			offsetLoop(0, count, count);
	});
	
	function offsetLoop(i, counter, idsRemaining) {
		while (i < counter) {
			secondsRemaining = (idsRemaining * delay) / 1000;
			if (secondsRemaining > 60) {
				var remainder = secondsRemaining % 60;
				secondsRemaining -= remainder;            
				minute = (secondsRemaining) / secondsRemaining;
			}
			secondsRemaining - i;
			if (typeof minute == 'undefined') {
				minute = 0;
			}
			log(minute + ':' + secondsRemaining);
			log1(minute + ':' + secondsRemaining);
			i++;
			idsRemaining--;
			if (i % callsPerSecond == 0) {
				timer = setTimeout(function () {
					offsetLoop(i, counter, idsRemaining);
				}, delay);
				break;
			}
		}
		if (i == counter ) {
			
			//log('0:00');
			$("#flash").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="returnToHme.htm" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
			$("#flash1").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="backToHomePage.htm" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
			$("#flash2").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="returnToServiceEvent.htm" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
			$("#flash3").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="returnToUserEventReservation.htm" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
			$("#flash4").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="returnToUserEventReservation.htm" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
			$('.processing').removeClass("processing");
			$('.button_padding').hide();
			$('.timeOutDisable').hide();
			$("#hideReserve").hide();
			
			$('#hideTimer').hide();
			$('#hideTimer1').hide();
		}
	};
	
	function log(text) {	
		$('#log').html(text);		
	}
	
	function log1(text) {	
		$('#log1').html(text);		
	}
	
	//popup manager
	 $("#link_login").click(function()
	 {
	  $("#myquickreservation").hide();
	  $("#mylogin").show();
	  return false;
	 
	 });

	/* ================================Date Slider Functionality =======================================================*/
	
	
jQuery('#thumbcarousel2').carousel(0);
		var $thumbItems2 = jQuery('#thumbcarousel2 .item');
		jQuery('#carousel2').on('slide.bs.carousel', function (event) {
		var $slide2 = jQuery(event.relatedTarget);
		var thumbIndex2 = $slide2.data('thumb');
		var curThumbIndex2 = $thumbItems2.index($thumbItems2.filter('.active').get(0));
		if (curThumbIndex2>thumbIndex2) {
			jQuery('#thumbcarousel2').one('slid.bs.carousel', function (event) {
				jQuery('#thumbcarousel2').carousel(thumbIndex2);
			});
			if (curThumbIndex2 === ($thumbItems2.length-1)) {
				jQuery('#thumbcarousel2').carousel('next');
			}else{
				jQuery('#thumbcarousel2').carousel(numThumbItems2-1);
			}
		} 
                else {
			jQuery('#thumbcarousel2').carousel(thumbIndex2);
		}
	});
/*//drag and drop
		handleDragStart = function handleDragStart(e) {
	var source = this;
	
    e.dataTransfer.effectAllowed = 'move';
	//e.originalEvent.dataTransfer.setData('Text');
    e.dataTransfer.setData('text', this.innerHTML);
}
		handleDragOver = function handleDragOver(e) {
	
  if (e.preventDefault) {
    e.preventDefault();
  }
  alert('handleDragOver function over');
  this.classList.add('over');
  return false;
}
		handleDragLeave = function handleDragLeave(e) {
	
	this.classList.remove('over'); 
}

		handleDrop =function handleDrop(e) {
	
  if (e.preventDefault)
  {
    e.preventDefault();
  }
  	this.classList.remove('over');
  		alert('handle drop function over');
	 ajax call where you need to update in database 
	 endsc 
	//this.innerHTML ='<div style="padding:15px;text-align:center;"><img src="images/ajax-loading.gif" text-a/></div>';
	//source.addClass('center_txt');
	//source.innerHTML = '';
	this.innerHTML = e.dataTransfer.getData('text');
  }
*/
/*var fras = document.querySelectorAll('.room_shift');
//alert('fras val abc :'+JSON.stringify(fras));
[].forEach.call(fras, function(fra) {
	//alert('queryselector drag functionality start inside foreach');
  fra.addEventListener('dragstart', handleDragStart, false);
  fra.addEventListener('dragover', handleDragOver, false);
  fra.addEventListener('dragleave', handleDragLeave, false);
  fra.addEventListener('drop', handleDrop, false);
});*/



//form validation 
		var validateadmin = jQuery("#myAdminLoginForm").validate({
		      rules: {
		    	  email: {
		          required: true,
		        },
		       
		        password: {
		          required: true,
		        },
		        },
		      messages:{
		    	  email:"UserName is Required",
		    	  password:"Password is required"
		      },
		      errorElement: "span",
		      errorClass: "help-inline-error",
		      submitHandler: function (form) {
		    	  var datastring=   $('#myAdminLoginForm').serialize();
				 
				// alert(datastring);
				 
				    	var html='';
				    	$.ajax({
				    		
				    		type: "GET",
				            url: "eventAdminAjaxLogin.htm",
				            data: datastring,
				         
				            success: function(data) {
				          
				            	if(data=='success email/User Name and password.'){
				            		
				         
				            		document.location.href="serviceprovider_view.htm";
				            		
				            	}else{
				            	
				            		html=data;
				            		$("#invalidLoginmessage").html(html);
				            		 $('#myadminlogin').show();
				            		/* $('#mylogin').hide();
				            		 $('#blackuser').hide();
				         				$('#greenuser').show();*/
				         			
				         				
				            	}
				                
				            },
				    	});
		      }
		    	 
		});
	
		var validate = jQuery("#myLoginForm").validate({
		      rules: {
		    	  email: {
		          required: true,
		        },
		       
		        password: {
		          required: true,
		        },
		        },
		      messages:{
		    	  email:"UserName is Required",
		    	  password:"Password is required"
		      },
		      errorElement: "span",
		      errorClass: "help-inline-error",
		      submitHandler: function (form) {
		    	  var datastring=   $('#myLoginForm').serialize();
				 
				// alert(datastring);
				 
				    	var html='';
				    	$.ajax({
				    		
				    		type: "GET",
				            url: "eventUserAjaxLogin.htm",
				            data: datastring,
				         
				            success: function(data) {
				            	
				            var html="";
				            	
				            	var response = jQuery.parseJSON(data);
				            	/*alert(response.message);*/
				            	if(response.message!='success email/User Name and password.'){
				            		html=response.message;
				            		$("#invalidmessage").html(html);
				            		 $('#mylogin').show();
				            		
				            	}else{
				            		 $('#mylogin').hide();
				            		 $('#blackuser').hide();
				         				$('#greenuser').show();
				         				/*$("#loggedin").html(response.sessionVal);*/
				         				html+='<input type="hidden" value="'+response.sessionVal+'" id="loggedin">';
				         				document.getElementById('fade').style.display = 'none';
				         			$("#valLogged").html(html);
				         			location.reload();
				         				
				            	}
				                
				            },
				    	});
		      }
		    	 
		});
	
		 var v = jQuery("#myRegForm").validate({
			 
			 onsubmit: true,
		      rules: {
		    	  language: {
		          required: true,
		        },
		        title: {
		          required: true,
		        },
		        firstName: {
		          required: true,
		        },
		        lastName: {
		          required: true,
		        },
		        streetName: {
		          required: true,
		        },
		        postalCode: {
		          required: true,
		          number: true,
		          
		          
		        },
		        city: {
		          required: true,
		        },
		        country:{
					required:true,
				},
				dob: {
		          required: true,
		          dateFormat:true,
		         
		        },
		        email: {
		          required: true,
		          
		        },
		        phoneNumber: {
		          required: true,
		          phoneFormat:true,
		         
		        },
		        userName: {
		            required: true,
		            
		          },
		          notificationDuration: {
		              required: true,
		              
		            },
		        
		            password: {
		            required: true,
		          
		          },
		          confirmPassWord: {
		            required: true,
		            equalTo:'#regpassword',
		            
		           
		          }
		      },
		      messages:{
		    	  language:"Language is required",  
		    	  	title:"Title is required",
		    	  	firstName:"First Name is required",
		    	  		lastName:"Last Name is required",
		    	  		streetName:"Street Name is required",
		    	  		postalCode:"Postal Code is required",
		    	  		city:"City is required",
		    	  		country:"Country is required",
		    	  		
		    	  		userName:"User Name is required",
		    	  		notificationDuration:"Notification Duration is required",
		    	  		password:"Password is required",
		    	  		confirmPassWord:"ConfirmPassword must be same as Password",
		      },
		      errorElement: "span",
		      errorClass: "help-inline-error",
		      submitHandler: function (form) {
		    	  var datastring=    $('#myRegForm').serialize();
		    	  alert("coming");
				
					 var html='';
				    	$.ajax({
				    		
				    		type: "POST",
				            url: "eventAjaxUserRegister.htm",
				            data: datastring,
				         
				            success: function(data) {
				            	
				            	//alert(data);
				            	if(data!='You have successfully registered with our system. Please click on the activation link sent to your email provided during the registration'){
				            		html=data;
				            		$("#invalidRegmessage").html(html);
				            		 $('#my_registration').show();
				            		
				            	}else{
				            		 $('#my_registration').hide();
				            		 $("#alertMsgPopUp").show();
				         			$("#alertPopSpanId").empty();
				         			$("#alertPopSpanIdGreen").append(data);
				            		 
				         				
				            	}
				                
				            },
				    	});
				 
		      }
		    
		    });
var adminreg = jQuery("#adminregform").validate({
			 
			 onsubmit: true,
		      rules: {
		    	  language: {
		          required: true,
		        },
		        title: {
		          required: true,
		        },
		        firstName: {
		          required: true,
		        },
		        lastName: {
		          required: true,
		        },
		        streetName: {
		          required: true,
		        },
		        postalCode: {
		          required: true,
		          number: true,
		          
		          
		        },
		        city: {
		          required: true,
		        },
		        country:{
					required:true,
				},
				dob: {
		          required: true,
		          dateFormat:true,
		         
		        },
		        email: {
		          required: true,
		          
		        },
		        phoneNumber: {
		          required: true,
		          phoneMyFormat:true,
		         
		        },
		        userName: {
		            required: true,
		            
		          },
		          notificationDuration: {
		              required: true,
		              
		            },
		        
		            password: {
		            required: true,
		          
		          },
		          confirmPassWord: {
		            required: true,
		            equalTo:'#adminregpassword',
		            
		           
		          }
		      },
		      messages:{
		    	  language:"Language is required",  
		    	  	title:"Title is required",
		    	  	firstName:"First Name is required",
		    	  		lastName:"Last Name is required",
		    	  		streetName:"Street Name is required",
		    	  		postalCode:"Postal Code is required",
		    	  		city:"City is required",
		    	  		country:"Country is required",
		    	  		
		    	  		userName:"User Name is required",
		    	  		notificationDuration:"Notification Duration is required",
		    	  		password:"Password is required",
		    	  		confirmPassWord:"ConfirmPassword must be same as Password",
		      },
		      errorElement: "span",
		      errorClass: "help-inline-error",
		      submitHandler: function (form) {
		    	  var datastring=    $('#adminregform').serialize();
		    	  alert("coming");
				
					 var html='';
				    	$.ajax({
				    		
				    		type: "POST",
				            url: "adminEventAjaxRegister.htm",
				            data: datastring,
				         
				            success: function(data) {
				            	
				            	//alert(data);
				            	if(data!='You have successfully registered with our system. Please click on the activation link sent to your email provided during the registration'){
				            		html=data;
				            		$("#invalidRegmessage").html(html);
				            		 $('#my_adminregistration').show();
				            		
				            	}else{
				            		 $('#my_adminregistration').hide();
				            		 $("#alertMsgPopUp").show();
				         			$("#alertPopSpanId").empty();
				         			$("#alertPopSpanIdGreen").append(data);
				         			document.getElementById('fade').style.display='none';
				            		 
				         				
				            	}
				                
				            },
				    	});
				 
		      }
		    
		    });

		 $.validator.addMethod("dateFormat",
				    function(value, element) {
			 //alert(value);
				        return value.match(/\b\d{1,2}[\/-]\d{1,2}[\/-]\d{4}\b/);
				    },
				    "Please enter a date in the format dd-mm-yyyy.");
		 $.validator.addMethod("phoneFormat",
				    function(value, element) {
			 //alert(value);
				        return value.match(/^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/);
				    },
				    "Invalid format, valid formats are 123456789,(123).456.7899,(123)-456-7899");
		 $.validator.addMethod("phoneMyFormat",
				    function(value, element) {
			 //alert(value);
				        return value.match(/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/);
				    },
				    "Invalid format, valid formats are 123456789,(123).456.7899,(123)-456-7899");
		
		 
				 $.validator.addMethod("postalCodeFormat",
						    function(value, element) {
					 //alert(value);
						        return value.match(/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/);
						    },
						    "Invalid Format,valid formats are 1234567,123-456-7890");
				 
				 
		 var forgot = jQuery("#myForgotForm").validate({
			 rules: {
				 email: {
			            required: true,
			            
			          },
			 },
			 meesages:{
				 email:"Email/UserName is Required",
			 },
			 errorElement: "span",
		      errorClass: "help-inline-error",
		      submitHandler: function (form) {
		    	  var datastring=    $('#myForgotForm').serialize(); 
		    	 // alert(datastring);
		    	  var html='';
			    	$.ajax({
			    		
			    		type: "POST",
			            url: "forgotPassEventUser.htm",
			            data: datastring,
			         
			            success: function(data) {
			            	html=data;
		            		$("#invalidForgotmessage").html(html);
		            		 $('#myforgot').show();
			            	
			                
			            },
			    	});
		      }
		 });
				 
				 
				 var pro = jQuery("#myProfileForm").validate({
					 rules: {
						 userName: {
					            required: true,
					            
					          },
					          password: {
						            required: true,
						          
						          },
						          confirmPassWord: {
						            required: true,
						            equalTo: "#mypassword"
						           
						          },
						          firstName: {
							          required: true,
							        },
							        lastName: {
							          required: true,
							        },
				    	  language: {
				          required: true,
				        },
				        title: {
				          required: true,
				        },
				      
				        streetName: {
				          required: true,
				        },
				        postalCode: {
				          required: true,
				          number: true,
				          
				          
				        },
				        city: {
				          required: true,
				        },
				        country:{
							required:true,
						},
						dob: {
				          required: true,
				          dateFormat:true,
				          check_date_of_birth:true,
				         
				        },
				        email: {
				          required: true,
				          
				        },
				        phoneNumber: {
				          required: true,
				          phoneFormat:true,
				      
				        },
				       
				          notificationDuration: {
				              required: true,
				              
				            },
				        
				         
				      },
				      messages:{
				    	  userName:"User Name is required",
				    	  password:"Password is required",
			    	  		confirmPassWord:"ConfirmPassword must be same as Password",
			    		  	firstName:"First Name is required",
			    	  		lastName:"Last Name is required",
				    	  language:"Language is required",  
				    	  	title:"Title is required",
				    
				    	  		streetName:"Street Name is required",
				    	  		postalCode:"Postal Code is required",
				    	  		city:"City is required",
				    	  		country:"Country is required",
				    	  		
				    	  		
				    	  		notificationDuration:"Notification Duration is required",
				    	  		
				      },
				      errorElement: "span",
				      errorClass: "help-inline-error",
				      submitHandler: function (form) {
				    	  var datastring=    $('#myProfileForm').serialize();
				    	 // alert("coming");
						
							 var html='';
						    	$.ajax({
						    		
						    		type: "POST",
						            url: "eventAjaxUserUpdate.htm",
						            data: datastring,
						         
						            success: function(data) {
						            	html=data;
					            		$("#invalidUpdatemessage").html(html);
					            		 $('#myprofile').show();
						            	
						                
						            },
						    	});
						 
				      }
				    
				    });

				 $.validator.addMethod("dateFormat",
						    function(value, element) {
					 //alert(value);
						        return value.match(/\b\d{1,2}[\/-]\d{1,2}[\/-]\d{4}\b/);
						    },
						    "Please enter a date in the format dd-mm-yyyy.");
				 $.validator.addMethod("check_date_of_birth", function(value, element) {

					    var day = $("#dob_day").val();
					    var month = $("#dob_month").val();
					    var year = $("#dob_year").val();
					    var age =  18;

					    var mydate = new Date();
					    mydate.setFullYear(year, month-1, day);

					    var currdate = new Date();
					    currdate.setFullYear(currdate.getFullYear() - age);

					    return currdate > mydate;

					}, "You must be at least 18 years of age.");
 
 /*var total_left=0;
 var click_count=0;
 var scroll_limit=32;
 var total_scroll=0;
 $(document).on('click','.carousel-control.right',function()
 { 
 		if(total_scroll>=90)
 		{
 			
 		$(this).attr("disabled","disabled");
 		}
 		else
 		{
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
 	
 });*/
 //Service changes drag drop fixed carousel navigation
 jQuery('#thumbcarousel1').carousel(0);
 		var $thumbItems1 = jQuery('#thumbcarousel1 .item');
 		jQuery('#carousel').on('slide.bs.carousel', function (event) {
 		var $slide1 = jQuery(event.relatedTarget);
 		var thumbIndex1 = $slide1.data('thumb');
 		var curThumbIndex1 = $thumbItems1.index($thumbItems1.filter('.active').get(0));
 		if (curThumbIndex1>thumbIndex1) {
 			jQuery('#thumbcarousel1').one('slid.bs.carousel', function (event) {
 				jQuery('#thumbcarousel1').carousel(thumbIndex2);
 			});
 			if (curThumbIndex1 === ($thumbItems1.length-1)) {
 				jQuery('#thumbcarousel1').carousel('next');
 			}else{
 				jQuery('#thumbcarousel1').carousel(numThumbItems2-1);
 			}
 		} 
                 else {
 			jQuery('#thumbcarousel1').carousel(thumbIndex2);
 		}
 	});
 //ends	
 jQuery('#thumbcarousel2').carousel(0);
 		var $thumbItems2 = jQuery('#thumbcarousel2 .item');
 		jQuery('#carousel2').on('slide.bs.carousel', function (event) {
 		var $slide2 = jQuery(event.relatedTarget);
 		var thumbIndex2 = $slide2.data('thumb');
 		var curThumbIndex2 = $thumbItems2.index($thumbItems2.filter('.active').get(0));
 		if (curThumbIndex2>thumbIndex2) {
 			jQuery('#thumbcarousel2').one('slid.bs.carousel', function (event) {
 				jQuery('#thumbcarousel2').carousel(thumbIndex2);
 			});
 			if (curThumbIndex2 === ($thumbItems2.length-1)) {
 				jQuery('#thumbcarousel2').carousel('next');
 			}else{
 				jQuery('#thumbcarousel2').carousel(numThumbItems2-1);
 			}
 		} 
                 else {
 			jQuery('#thumbcarousel2').carousel(thumbIndex2);
 		}
 	});
 //==================drag and drop==========================
 function handleDragStart(e) {
 	var source = this;
     e.dataTransfer.effectAllowed = 'move';
 	//e.originalEvent.dataTransfer.setData('Text');
     e.dataTransfer.setData('text', this.innerHTML);
 }
 function handleDragOver(e) {
   if (e.preventDefault) {
     e.preventDefault();
   }
   this.classList.add('over');
   return false;
 }
 function handleDragLeave(e) {
 	this.classList.remove('over'); 
 }

 function handleDrop(e) {
   if (e.preventDefault)
   {
     e.preventDefault();
   }
   	this.classList.remove('over');
 	/* aajax call where you need to update in database */
 	/* endsc */
 	//this.innerHTML ='<div style="padding:15px;text-align:center;"><img src="images/ajax-loading.gif" text-a/></div>';
 	//source.addClass('center_txt');
 	this.innerHTML = e.dataTransfer.getData('text');
   }

 var fras = document.querySelectorAll('.room_shift');
 [].forEach.call(fras, function(fra) {
   fra.addEventListener('dragstart', handleDragStart, false);
   fra.addEventListener('dragover', handleDragOver, false);
   fra.addEventListener('dragleave', handleDragLeave, false);
   fra.addEventListener('drop', handleDrop, false);
 });
 //Registration form form validation 
  var v = jQuery("#regform").validate({
       rules: {
        fname: {
           required: true,
         },
         lname: {
           required: true,
         },
 		pwd: {
           required: true,
         },
 		e_mail: {
           required: true,
         },
 		contact_no: {
           required: true,
         },
 		uname: {
           required: true,
         },
 		notify: {
           required: true,
         },
 		language:{
 			required:true,
 		},
         pwd: {
           required: true,
           minlength: 6,
           maxlength: 15,
         },
         cpwd: {
           required: true,
           minlength: 6,
           equalTo: "#pwd",
         }

       },
       errorElement: "span",
       errorClass: "help-inline-error",
     });
 //Registration form form validation 
  var v = jQuery("#passreset").validate({
       rules: {
 		e_mail: {
           required: true,
         },

       },
       errorElement: "span",
       errorClass: "help-inline-error",
     });
	
	});
//global varible declaration
var scroll_limit=32;
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
		var map;
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
    });
	//Full screen
	$(document).on('click','#fs',function()
	{
	if($(this).hasClass("full_on"))
	{
		$(this).removeClass("full_on");
		$(".dummyfull").removeClass('fullwidth');
		$(this).attr("src","images/fullscreen.png");
		$(".info_wrapper").css('height','353px');
		$(".col-xs-fs-8").css("width","67%");
		//scroll_limit=32;
		}
			else
			{
			$(this).addClass("full_on");
			$(".dummyfull").addClass('fullwidth');
			$(this).attr("src","images/fs-min.png");
			$(".info_wrapper").css('height','500px');
			$(".col-xs-3-dateline").css('width','14.2%');
			$("#thumbcarousel2").css("padding-right","10px")
			$(".col-xs-fs-8").css("width","76.2%");
			//scroll_limit=44;
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
	$(".btn-current-date").dblclick(function()
	{
		$(this).html('18-06-2015');
	});
	//removing processing class on close popup
	$(document).on('click','.close_popup',function()
	{
		$('.processing').removeClass("processing");
	});
	//=================================Mark Date on click
	$(document).on('click','.cal_date',function()
	{
	var $aSelected = $('.small_square').find('.cal_date');
		if( $aSelected.hasClass('mark_date') ){
			$aSelected.removeClass('mark_date');
			}
				if($('.small_square').find('.mark_date'))
				$(this).removeClass('mark_date');
					$(this).addClass("mark_date");
					$(".btn-current-date").text($(this).attr('date-value'));
					$('.ajax_loader_inner').show();	
	});
	//======================================Mark time on click
	$(document).on('click','.timeline .cell',function()
	{
	$(this).css("background","url(images/arrow.png) bottom no-repeat");
	$('.ajax_loader_inner').show();	
	});	
/*=============================== Event Reservation Jquery functionality ============================= */

$(document).on('click','.cell_byobject',function()
{
	var $aSelected = $('.cell_byobject').find('.cell_header');
	if($(this).hasClass('mark_date'))
	{
		$(this).removeClass('mark_date');
	}
			else{
			$(this).addClass("mark_date");
			}
});
/*=============================== Event Reservation Modify(change) functionality ============================= */

/* $(document).on('click','.cell_byobject_change',function()
{
	$("#selected_tour").html($(this).parents('.col-xs-3').html()).fadeOut(10).fadeIn("slow");
	$(".timeline").css("left","230px");
	$(this).parents('.col-xs-3').css("border","1px #000 dashed");
	var $aSelected = $('.cell_byobject').find('.cell_header');
	if($(this).hasClass('mark_date'))
	{
		$(this).removeClass('mark_date');
	}
			else{
			$(this).addClass("mark_date");
			}
}); */
/* ================================  ENDS =============================================================*/
	//pointing
	$(document).on('click','.booked',function()
	{
	$(this).addClass("processing");
					var count = 5;
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
							log('0:00');
							$("#flash").html('<div class="form-group"><p>Reservation time has been Expired!</p><a href="hotel_reservation.html" class="btn btn-default width100 btn-danger">Go Back & Try Again</a></div>');
							$('.processing').removeClass("processing");
							$('.button_padding').hide();
						}
					};

					function log(text) {
						$('#log').html(text);
					}

					offsetLoop(0, count, count);
	});
	//popup manager
	$("#link_login").click(function()
	{
		$("#fade, #myquickreservation").hide();
		$("#fade, #mylogin").show();
		return false;
	
	});
/* ================================Date Slider Functionality =======================================================*/
var total_left=0;
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
	
});
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
/* ================= /Ends /=============================== */

	});
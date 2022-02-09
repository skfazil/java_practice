/* ================  scroll bar =============================*/
$(document).ready(function()
{
			$(window).load(function(){
				$(".info_wrapper").mCustomScrollbar({
						scrollButtons:{enable:true},
							snapAmount:1,
							scrollButtons:{enable:true},
							keyboard:{scrollAmount:10},
							mouseWheel:{deltaFactor:10},
							scrollInertia:0,
					callbacks:
					{
						whileScrolling: function(){ 
						var c=Math.abs(this.mcs.top);
						if(c<=12)
						{
						$(".cell_header").css({"position":"relative",top:'0px','width':'auto','background':'#FFF'});
						$(".myleft,.myright,.no_of_cols,.fixed-myleft,.fixed-myright,.myright_event_change").css({'background':'#FFF','height':'53px','z-index':999,top:'0px','opacity':1});
						$(".fixed-myleft-clock").css("height","55px");
						$(".myleft,.no_of_cols").css({'border-bottom':'none'});
						$(".timeline_object,.timeline").css('top','53px');
						$(".event_change_timeline").css({'top':'54px'});
						$(".service_change_myleft").css({'top':'0px'});
						}
							else
							{
							$(".cell_header").hide().css({"position":"absolute",top:c+'px',width:'100%','background':'#FFF','z-index':10}).show();
							//$(".event_change_timeline").css({"margin-top":-c+'px','z-index':10});
							//$(".no_of_cols").css({top:c+'px','background':'#FFF','z-index':999,'border-bottom':'2px #CCC solid','height':'55px'});
							$(".myleft,.myright").css({'background':'#FFF','height':'55px','z-index':999,top:c+'px','opacity':1});
							$(".myleft").css({'border-bottom':'2px #CCC solid','width':'46px'});
							$(".myright").css({'background':'#FFF','height':'53px','z-index':999,top:c+'px','opacity':1});
							$(".fixed-myleft,.fixed-myright,.myright_event_change").css({'top':c+'px'});
							$(".fixed-myleft").css("height","55px");
							$(".service_change_myleft").css({'top':c+'px'});
							$(".event_change_timeline").css({"position":"absolute","top":'0px',"width":'100%'});
							$(".timeline_object,.timeline").css({'top':'0px'});
							$(".myleft span").css("left","-10px");
							}
						}
					}

			});
			});
});
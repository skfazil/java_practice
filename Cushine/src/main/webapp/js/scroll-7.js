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
						$(".cell_header").css({"position":"relative",top:'0px','background':'#FFF'});
						$(".myleft,.myright,.no_of_cols").css({'background':'#FFF','height':'53px','z-index':999,top:'0px','opacity':1});
						$(".timeline_object,.timeline").css('top','53px');
						$(".myleft").css({'border-bottom':'none'});
						}
							else
							{
							//$(".cell_header").hide().css({"position":"absolute",top:c+'px',width:'100%','background':'#FFF'});
							$(".cell_header").hide().css({"position":"absolute",top:c+'px',width:'100%','background':'#FFF','z-index':10}).show();
							$(".no_of_cols").css({top:c+'px','background':'#FFF','z-index':999,'border-bottom':'2px #CCC solid'});
							$(".myleft").css({'width':'46px','border-right':'1px #CCC solid'});
							$(".myleft").css({'border-bottom':'2px #CCC solid'});
							$(".myleft,.myright").css({'background':'#FFF','height':'55px','z-index':999,top:c+'px','opacity':1});
							$(".myright").css({'background':'#FFF','height':'53px','z-index':999,top:c+'px','opacity':1});
							$(".timeline_object,.timeline").css({'top':'0px'});
							$(".myleft span").css("left","-10px");
							}
						}
					}

			});
			});
});
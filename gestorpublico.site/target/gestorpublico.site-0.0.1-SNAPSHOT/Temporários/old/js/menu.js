$(document).ready(function() {
	$('#cssmenu li').children('ul').hide();
	
	var link = window.location.pathname.split('\/');
	$("#cssmenu a[onclick=\"javascript:execute('"+ link[link.length -1] + "')\" ]").addClass('acctive');
	$(".acctive").parents("li.has-sub").addClass('open');
	$("li.open").children("a").find(".mais").removeClass("glyphicon-plus plus").addClass("glyphicon-minus minus");
	$("li.open").children('ul').slideDown(200);
	
	$('#cssmenu li.has-sub > a').on('click', function() {
		var element = $(this).parent('li');
		if (element.hasClass('open')) {
			element.removeClass('open');
			element.children("a").find(".mais").removeClass("glyphicon-minus minus").addClass("glyphicon-plus plus");
			element.find('li').removeClass('open');
			element.find('ul').slideUp(200);
		} else {
			element.addClass('open');
			element.children("a").find(".mais").removeClass("glyphicon-plus plus").addClass("glyphicon-minus minus");
			element.children('ul').slideDown(200);
			element.siblings('li').children('ul').slideUp(200);
			element.siblings('li').removeClass('open');
			element.siblings('li').find('li').removeClass('open');
			element.siblings('li').find('ul').slideUp(200);
		}
	});
});

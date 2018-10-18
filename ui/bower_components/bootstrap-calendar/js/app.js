(function($) {

	"use strict";

	var options = {
		modal: "#events-modal",
		modal_type: "template",
		modal_title: function(e) {
			return e.title
		},
		events_source: function(){
			var assignments = new Array();

	    var url = "http://localhost:8080/scheduler-service/api/1/assignments?assignmentType="; //change congregation id later
	    $.ajax({
	      url: url,
	      type: 'GET',
	      dataType: 'json',
	      async: false
	    }).done(function(data){
	      //update assignments
	      var i;
	      for(i = 0; i < data.length; i++) {
	        var a = data[i];

	        //get date
	        var startDate = new Date(a.date);
	        startDate = Date.parse(new Date(startDate.getTime() - startDate.getTimezoneOffset() * -60000));

	        //create assignment object and add it to array
	        var assignment = {
	          id: a.id,
	          title: a.assignmentType,
	          url: "www.example.com",
	          class: "event-important",
	          start: startDate,
	          end: startDate + 1,
	          description: "Test description"
	        };

	        assignments[i] = assignment;
				}

			});

			return assignments;
		},
		view: 'month',
		tmpl_path: './tmpls/',
		tmpl_cache: false,
		day: 'now',
		onAfterEventsLoad: function(events) {
			if(!events) {
				return;
			}
			var list = $('#eventlist');
			list.html('');

			$.each(events, function(key, val) {
				$(document.createElement('li'))
					.html('<a href="' + val.url + '">' + val.title + '</a>')
					.appendTo(list);
			});
		},
		onAfterViewLoad: function(view) {
			$('.page-header h3').text(this.getTitle());
			$('.btn-group button').removeClass('active');
			$('button[data-calendar-view="' + view + '"]').addClass('active');
		},
		classes: {
			months: {
				general: 'label'
			}
		}
	};

	var calendar = $('#calendar').calendar(options);

	$('.btn-group button[data-calendar-nav]').each(function() {
		var $this = $(this);
		$this.click(function() {
			calendar.navigate($this.data('calendar-nav'));
		});
	});

	$('.btn-group button[data-calendar-view]').each(function() {
		var $this = $(this);
		$this.click(function() {
			calendar.view($this.data('calendar-view'));
		});
	});

	$('#first_day').change(function(){
		var value = $(this).val();
		value = value.length ? parseInt(value) : null;
		calendar.setOptions({first_day: value});
		calendar.view();
	});

	$('#language').change(function(){
		calendar.setLanguage($(this).val());
		calendar.view();
	});

	$('#events-in-modal').change(function(){
		var val = $(this).is(':checked') ? $(this).val() : null;
		calendar.setOptions({modal: val});
	});
	$('#format-12-hours').change(function(){
		var val = $(this).is(':checked') ? true : false;
		calendar.setOptions({format12: val});
		calendar.view();
	});
	$('#show_wbn').change(function(){
		var val = $(this).is(':checked') ? true : false;
		calendar.setOptions({display_week_numbers: val});
		calendar.view();
	});
	$('#show_wb').change(function(){
		var val = $(this).is(':checked') ? true : false;
		calendar.setOptions({weekbox: val});
		calendar.view();
	});
	$('#events-modal .modal-header, #events-modal .modal-footer').click(function(e){
		//e.preventDefault();
		//e.stopPropagation();
	});
}(jQuery));

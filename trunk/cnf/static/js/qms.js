var today = new Date();
var activityStart = new Date();

function getWeek(activityStart) {
    var target  = new Date(activityStart);
    var dayNr   = (target.getDay() + 6) % 7;
    target.setDate(target.getDate() - dayNr + 3);
    var jan4    = new Date(target.getFullYear(), 0, 4);
    var dayDiff = (target - jan4) / 86400000;
    return weekNr = 1 + Math.ceil(dayDiff / 7);
}

function addRowToDatepick() {

    var row = $("<div class='row'></div");

    var arrow = $("<div class='column week'> W" + getWeek(activityStart) + " &rarr;</div>");
    arrow.appendTo(row);
    for (var i = 0; i < 5; i++) {
        addDayToRow(activityStart, row);
        activityStart.setDate(activityStart.getDate() + 1);
    }

    arrow.hover(function() {
        arrow.parent().find(".day").addClass('highlight');
    }, function() {
        arrow.parent().find(".day").removeClass('highlight');
    });

    arrow.toggle(function() {
       arrow.parent().find(".day").addClass('selected');
    }, function() {
       arrow.parent().find(".day").removeClass('selected');
    });

    row.appendTo('.datepick');

    activityStart.setDate(activityStart.getDate() - 12);
}



function addDayToRow(date, row) {

    var day;

    if (today.getFullYear() == date.getFullYear() && today.getMonth() == date.getMonth() && today.getDate() == date.getDate()) {
        day = $(" <div id='" + date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "' class='today column day'>" + getDateString(date) + " </div>");

    } else {
        day = $(" <div id='" + date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "' class='column day'>" + getDateString(date) + " </div>");
    }

    day.hover(function() {
        day.addClass('highlight');
    }, function() {
        day.removeClass('highlight');
    });

    day.click(function() {
        day.toggleClass('selected');
    });

    day.appendTo(row);
}

function getDateString(date) {
    var result = date.getDate() + " ";
    switch (date.getMonth()) {
        case 0: result += " Января"; break;
        case 1: result += " Февраля"; break;
        case 2: result += " Марта"; break;
        case 3: result += " Апреля"; break;
        case 4: result += " Мая"; break;
        case 5: result += " Июня"; break;
        case 6: result += " Июля"; break;
        case 7: result += " Августа"; break;
        case 8: result += " Сентября"; break;
        case 9: result += " Октября"; break;
        case 10: result += " Ноября"; break;
        case 11: result += " Декабря"; break;
    }
    return result;
}

$(document).ready(function() {

    activityStart.setDate(activityStart.getDate() + 7);
    while (activityStart.getDay() != 1) {
        activityStart.setDate(activityStart.getDate() - 1);
    }

    $(".toggle").click(function() {
        $("#" + $(this).attr("rel")).toggle();
    });

    $(".cleanOnFocus").focus(function () {
        $(this).attr("value", "");
        $(this).css("color", "black");
        $(this).removeClass('cleanOnFocus');
    });

    $("#searchToggle").click(function() {
        $("#login").toggle();
        $("#search").toggle();
    });

    $(".submit").click(function() {
        $(this).parent().submit();
    });

    // datepick

    addRowToDatepick();
    addRowToDatepick();
    addRowToDatepick();
    addRowToDatepick();

    $('#clearReport').click(function() {
        $('.selected').removeClass("selected");
    });

    $('#newWeek').click(function() {
        addRowToDatepick();
    });

    $('#reportSubmit').click(function() {

        var ids = new Array();
        $('.selected').each(function() {
            ids.push(this.id);
        });

        $('#reportSubmit').text("Сохраняем...");

        $.ajax({
            type: "POST",
            url: '/report/activity/' + $(this).attr("rel"),
            data: { text : $('#reportText').val(), selected : ids},
            success: function(data) {
                $('#reportSubmit').text("Ещё?");
                $('#reportList').prepend(data);
            }
        });
    });

    $('#contactForm').ajaxForm({
        beforeSubmit: function() {
            $('#contactFormSubmit').text("Сохраняем...");
        },
        success: function(responseText, statusText) {
            $('#contactFormSubmit').text("Сохранить");
            $('#contactFormMessage').html(responseText);
            $('#contactFormMessage').fadeTo(200, 1);
            $('#contactFormMessage').fadeIn(500);
            setTimeout(function() {
                $('#contactFormMessage').fadeTo(500, 0.33);
            }, 3000);
        }
    });

    $('#passwordForm').ajaxForm({
        beforeSubmit: function() {
            $('#passwordFormSubmit').text("Сохраняем...");
        },
        success: function(responseText, statusText) {
            $('#passwordFormSubmit').text("Сохранить");
            $('#passwordFormMessage').html(responseText);
            $('#passwordFormMessage').fadeTo(200, 1);
            $('#passwordFormMessage').fadeIn(500);
            setTimeout(function() {
                $('#passwordFormMessage').fadeTo(500, 0.33);
            }, 3000);
        }
    });
});

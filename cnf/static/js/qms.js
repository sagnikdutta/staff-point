var activityStart = new Date();

function addRowToDatepick() {
    var row = $("<div class='row'></div");
    $("<div class='column week'>&rarr;</div>").appendTo(row);
    for (var i = 0; i < 5; i++) {
        addDayToRow(activityStart, row);
        activityStart.setDate(activityStart.getDate() + 1);
    }
    row.appendTo('.datepick');

    activityStart.setDate(activityStart.getDate() - 12);
}

function addDayToRow(date, row) {
    $(" <div id='" + date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "' class='column day'>" + getDateString(date) + " </div>").appendTo(row);
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

function updateDatepickHighlight() {
    $('.day').hover(function() {
        $(this).addClass('highlight');
    }, function() {
        $(this).removeClass('highlight');
    });

    $('.week').hover(function() {
        $(this).parent().find(".day").addClass('highlight');
    }, function() {
        $(this).parent().find(".day").removeClass('highlight');
    });

    $('.day').click(function() {
        $(this).toggleClass('selected');
    });

    $('.week').toggle(function() {
        $(this).parent().find(".day").addClass('selected');
    }, function() {
        $(this).parent().find(".day").removeClass('selected');
    });
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

    updateDatepickHighlight();

    $('#newWeek').click(function() {
        addRowToDatepick();
        updateDatepickHighlight();
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
});

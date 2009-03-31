$(document).ready(function() {

    Cufon.replace('#subnav ul li a', { fontFamily: 'AZGillSansC' });

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

    // datepick

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

    $('.week').click(function() {
        $(this).parent().find(".day").toggleClass('selected');
    });

    $('#reportSubmit').click(function() {

        var ids = new Array();
        $('.selected').each(function() {
            ids.push(this.id);
        });

        $.post('/report/activity/' + $(this).attr("rel"),
        {
            text : $('#reportText').val(),
            selected : ids
        },
                function(data) {
                    alert(data)
                }, 'json');
    });

});

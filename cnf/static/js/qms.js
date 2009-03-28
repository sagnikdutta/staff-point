$(document).ready(function() {

    $(".toggle").click(function() {
        $("#" + $(this).attr("rel")).toggle();
    });

    $(".cleanOnFocus").focus(function () {
        $(this).attr("value", "");
        $(this).css("color", "black");
        $(this).removeClass("cleanOnFocus");
    });

    $("#searchToggle").click(function() {
        $("#login").toggle();
        $("#search").toggle();
    });

    $('.column').hover(function() {
        $(this).addClass('highlight');
    }, function() {
        $(this).removeClass('highlight');
    });
});

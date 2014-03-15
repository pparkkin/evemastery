$(document).ready(function() {
//    $(".skills").slideUp();
    $("h3.certificate").click(function() {
        $(this).next(".skills").slideToggle();
    });
});

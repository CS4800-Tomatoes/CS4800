//pulling the search bar and search button from the html to be used
const search_bar = document.getElementById("search_bar");
const search_button = document.getElementById("search_button");

window.addEventListener('DOMContentLoaded', event=>{
    const main = document.body.querySelector('#mainNav');
    if(mainNav){
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 74,
        });
    };

    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(document.querySelectorAll('#navbarResponsice .nav-link'));

    responsiveNavItems.map(function (responsiveNavItems){
        responsiveNavItems.addEventListener('click', ()=> {
            if(window.getComputedStyle(navbarToggler).display !== 'none'){
                navbarToggler.click();
            }
        });
    });
});

// for seraching up them classes :(
function search(){
    $("#result_list").empty();

    var course = $('#budget_input').val()
    $.ajax({
        url: "/welcome" + course,
        success: function (res){
            console.log("The result from this server is: " + res);
            alert(courseNumber)
        },
        error: function(error){
            alert("bruh")
            console.log("Failed miserably." + error);
        }
    });
}
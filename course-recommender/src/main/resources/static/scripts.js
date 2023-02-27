//pulling the search bar and search button from the html to be used
const search_bar = document.getElementById("search_bar");
const search_button = document.getElementById("search_button");

search_button.addEventListener("click", () => {
    if(search_bar.val == ""){
        alert("bruh");
    }
    else{
        search(search_bar.val);
        search_bar.val = "";
    }
});


// for seraching up them classes :(
function search(courseNumber){
    $("#result_list").empty();
    //get input
    var course = $('#course_input').val()
    //send HTTP request
    $.ajax({
            url: "/welcome",
            success: function (res){
                console.log("The result from this server is: " + res);
                alert(courseNumber)
            },
            error: function(error){
                alert("bruh")
                console.log("Failed miserably." + error);
            }
        });
    //render result   
    
}

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
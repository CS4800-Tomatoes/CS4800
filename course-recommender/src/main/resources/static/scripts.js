//pulling the search bar and search button from the html to be used

$(document).ready(function()
{
    var search_bar = document.getElementById("search_bar");
    var search_button = document.getElementById("search_button");

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

    document.getElementById("search_button").addEventListener("click", () => {
        var searchBar = document.getElementById("search_bar");
        console.dir(searchBar);
        if(searchBar.value == ""){
            alert("bruh");
        }
        else{
            search(searchBar.value);
            searchBar.value = "";
        }
    });
});


// window.addEventListener('DOMContentLoaded', event=>{
//     const main = document.body.querySelector('#mainNav');
//     if(mainNav){
//         new bootstrap.ScrollSpy(document.body, {
//             target: '#mainNav',
//             offset: 74,
//         });
//     };

//     const navbarToggler = document.body.querySelector('.navbar-toggler');
//     const responsiveNavItems = [].slice.call(document.querySelectorAll('#navbarResponsice .nav-link'));

//     responsiveNavItems.map(function (responsiveNavItems){
//         responsiveNavItems.addEventListener('click', ()=> {
//             if(window.getComputedStyle(navbarToggler).display !== 'none'){
//                 navbarToggler.click();
//             }
//         });
//     });
// });
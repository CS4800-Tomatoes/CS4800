//pulling the search bar and search button from the html to be used

$(document).ready(function()
{
    var search_bar = document.getElementById("search_bar");
    var search_button = document.getElementById("search_button");

    // for seraching up them classes 
    function search(courseNumber){
        $("#result_list").empty();
        //get input
        var course = $('#course_input').val()
        //send HTTP request
        $.ajax({
                url: "/mongoSearch",
                type: "get",
                data: {
                    courseNum: courseNumber
                },
                success: function (res){
                    alert("The result from this server is: " + res);
                    //Step 1: create a list of the courses that match the tags of the searched words
                    
                    //Step 2: Populate into cards

                    //Step 3: Add a cute tomato at the bottom of the card to show matches/top recommendations
                    
                },
                error: function(error){
                    alert("There was an issue :(")
                    console.log("Failed miserably." + error);
                }
            });
        //render result   
        
    }

    function searchByString(searchString){
        $("#result_list").empty();
        //get input
        var course = $('#course_input').val()
        //send HTTP request
        $.ajax({
                url: "/mongoSearch",
                type: "get",
                data: {
                    searchString: searchString
                },
                success: function (res){
                    var jason = JSON.parse(res);
                    if(jason["status"] == 1){
                        //finding the important elements in DOM tree :)
                        //container == the things we putting the other things in
                        //<div card container>
                        //  <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3"> == other divider
                        
                        //   <div class="col-4">
                        //     <div class="card" style="width: 18rem;">
                        //       <img class="card-img-top" src="https://i.kym-cdn.com/editorials/icons/mobile/000/001/508/hackerman-icon.jpg" alt="Card image cap">
                        //       <div class="card-body">
                        //         <h6>Class Title</h6>
                        //         <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                        //       </div>
                        //     </div>
                        //   </div>
                        cardContainer = document.getElementById("cardContainer");
                        cardContainer.innerHTML = "";

                        items = jason["classData"].length;
                        const cards = [];
                        var count = 1;

                        if(items <4){
                            for(let step=0; step<items; step++){
                                cards[step] = createCard(results, courseNum, className);
                                var row = document.createElement("div");
                                row.setAttribute("class", "row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3")
                                cardContainer.appendChild(row);
                                row.appendChild(cards)
                            }
                        }
                        else{
                            for(let step = 0; step < items; step++){
                                var results = jason["classData"][step];
                                var courseNum = results["Course Number"];
                                var className = results["Class Name"];
                                
                                //add to DOM tree
                                cards[count%3-1] = createCard(results, courseNum, className);
                                if(count%3 == 0){
                                    var row = document.createElement("div");
                                    row.setAttribute("class", "row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3")
                                    cardContainer.appendChild(row);
                                    row.appendChild(cards)
                                }
                                count++;
                            }
                        
                        }

                    }
                    else{
                        alert("That is not a valid tag. Hint: Try \"AI\" or \"Programming\"");
                    }
                    
                },
                error: function(error){
                    alert("There was an issue :(")
                    console.log("Failed miserably." + error);
                }
            });
        //render result   
        
    }

    document.getElementById("search_button").addEventListener("click", () => {
        var searchBar = document.getElementById("search_bar");
        console.dir(searchBar);
        if(searchBar.value == ""){
            alert("Please enter in an interest.");
        }
        else{
            searchByString(searchBar.value);
            searchBar.value = "";
        }
    });
});

function createCard(results, courseNum, className){
    //Create items
    var column = document.createElement("div");
    column.setAttribute("class", "col-4");
    var cardStyle = document.createElement("div");
    cardStyle.setAttribute("class", "card");
    cardStyle.setAttribute("style", "width: 18rem");
    var img = document.createElement("img");
    img.setAttribute("class", "card-img-top");
    img.setAttribute("src", "https://i.kym-cdn.com/editorials/icons/mobile/000/001/508/hackerman-icon.jpg");
    var cardBody = document.createElement("div");
    cardBody.setAttribute("class", "card-body");
    var cardTitle = document.createElement("h6");
    var description = document.createElement("p");
    cardTitle.innerText = courseNum;
    description.innerText = className;

    //append to parents
    cardBody.appendChild(cardTitle);
    cardBody.appendChild(description);
    cardStyle.appendChild(img);
    cardStyle.appendChild(cardBody);
    column.appendChild(cardStyle);

    return column;
}


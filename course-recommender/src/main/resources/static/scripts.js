//pulling the search bar and search button from the html to be used
var footer;
$(document).ready(function()
{
    var search_bar = document.getElementById("search_bar");
    var search_button = document.getElementById("search_button");
    footer = document.getElementById("footer");
    var about_button = document.getElementById("about_page");

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
                        mongodbDataToCards(jason);
                        var searchBar = document.getElementById("search_bar");
                        searchBar.value = "";
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
            // searchBar.value = "";
        }
    });

    //enter button now can aid in searching :)
    document.addEventListener("keyup", function(event){
        if(event.code== 'Enter'){
            var searchBar = document.getElementById("search_bar");
            console.dir(searchBar);
            if(searchBar.value == ""){
                alert("Please enter in an interest.");
            }
            else{
                searchByString(searchBar.value);
                // searchBar.value = "";
            }
        }
    })

    // document.getElementById("test_button").addEventListener("click", () => {
    //     fakeData();
    // });
});

// function fakeData(){
//     var fakeJson = '{"status":1,"classData":[{"_id":{"$oid":"63f3d09c53536d5016712d9f"},"Course Number":4200,"Class Name":"Artificial Intelligence","Description":"Overview of the different application areas of AI. Introduction to the basic AI concepts and techniques such as heuristic search, knowledge representation, automated reasoning. In-depth discussion of several AI application areas: their specific problems, tools and techniques."},{"_id":{"$oid":"64094700f1ce5c225d4fb181"},"Course Number":4210,"Class Name":"Machine Learning and its Applications","Description":"Supervised learning techniques, including linear models for regression, linear models for classification, decision trees, support vector machines, neural networks. Unsupervised learning techniques, including clustering and dimensionality reduction. Related advanced topics. Case studies and applications."},{"_id":{"$oid":"6409476cf1ce5c225d4fb183"},"Course Number":3520,"Class Name":"Symbolic Programming","Description":"Languages for processing symbolic data with emphasis on applications in artificial intelligence."},{"_id":{"$oid":"63f3d06753536d5016709a67"},"Course Number":4800,"Class Name":"Software Engineering","Description":"Models of the software development process and metrics. Software requirements and specifications. Methodologies, tools and environments. Human-computer interaction. Software architecture, design and implementation techniques. Project management. Cost estimation. Testing and validation. Automated build, deployment and continuous integration. Maintenance and evolution."},{"_id":{"$oid":"6409448ff1ce5c225d4fb17a"},"Course Number":2450,"Class Name":"Programming Graphical User Interfaces","Description":"Computer interfaces. Usability of interactive systems. GUI development processes. GUI components. Input and viewing devices. Event-handling. Animation use in GUIs. Problem-solving techniques. Introduction to Human-Computer Interface."},{"_id":{"$oid":"64094512f1ce5c225d4fb17b"},"Course Number":3560,"Class Name":"Objected-Oriented Design and Programming","Description":"Elements of the object model. Abstraction, encapsulation, modularity, hierarchy and polymorphism. Object-oriented design principles. Design patterns. Implementation and programming of system design. Object and portable data. Comprehensive examples using a case study approach."},{"_id":{"$oid":"640946d1f1ce5c225d4fb180"},"Course Number":4750,"Class Name":"Mobile Application Development","Description":"Mobile development environments and fundamentals. Mobile user interface (UI) design and implementation. Mobile data storage and management. Network programming in mobile and integration with cloud services. Advanced mobile development with external sensors, libraries and frameworks. Hands-on mobile development practices and the distribution of mobile applications. Mobile application security."}]}';
//     var jason = JSON.parse(fakeJson);
//     if(jason["status"] == 1)
//     {
//         mongodbDataToCards(jason);
//     }
//     else{
//         alert("That is not a valid tag. Hint: Try \"AI\" or \"Programming\"");
//     }
// }

function mongodbDataToCards(pojo)
{
    var jason = pojo;
    cardContainer = document.getElementById("cardContainer");
    cardContainer.innerHTML = "";

    items = jason["classData"].length;

    
    var cards = [0, 0, 0];
    var count = 0;

    for(let step = 0; step < items; step++){
        var results = jason["classData"][step];
        var desc = results["Description"];
        var courseNum = results["Course Number"];
        var className = results["Class Name"];
        var pic = results["Image"];
        console.log(pic);
        
        //add to DOM tree
        cards[count%3] = createCard(results, courseNum + ": " + className, desc, pic);
        if(count%3 == 2 || count+1 == items){
            var row = document.createElement("div");
            row.setAttribute("class", "row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3")
            
            for(let i = 0; i < 3; i++)
            {
                if(cards[i] != 0)
                {
                    row.appendChild(cards[i]);
                }
            }

            cards = [0, 0, 0]
            
            //Add to container
            cardContainer.appendChild(row);
        }
        count++;
    }

    //Make sure the new document is showing up
    var toe = document.getElementById("toe");
    //determine if footer should be fixed or not
    var heightComparision = window.innerHeight - document.body.offsetHeight <= toe.offsetHeight;
    if(items > 0 && heightComparision)
    {
        toe.removeAttribute("class", "fixed-bottom");
    }
    else{
        toe.setAttribute("class", "fixed-bottom");
    }
}

function createCard(results, courseTitle, courseDesc, pic){
    //Create items
    //var column = document.createElement("div");
    //column.setAttribute("class", "col-4");
    var cardStyle = document.createElement("div");
    cardStyle.setAttribute("class", "card col-4");
    //cardStyle.setAttribute("style", "width: 18rem");
    var img = document.createElement("img");
    img.setAttribute("class", "card-img-top");
    img.setAttribute("src", pic);
    img.setAttribute("style", "width: 350px; height: 210px;")
    var cardBody = document.createElement("div");
    cardBody.setAttribute("class", "card-body");
    var cardTitle = document.createElement("h6");
    // var descContainer = document.createElement("div");
    // descContainer.setAttribute("class", "overflow-auto");
    // descContainer.appendChild(description);
    var description = document.createElement("p");
    cardTitle.innerText = courseTitle;
    description.innerText = courseDesc;

    //append to parents
    cardBody.appendChild(cardTitle);
    // cardBody.appendChild(descContainer);
    cardBody.appendChild(description);
    cardStyle.appendChild(img);
    cardStyle.appendChild(cardBody);
    //column.appendChild(cardStyle);

    return cardStyle;
}


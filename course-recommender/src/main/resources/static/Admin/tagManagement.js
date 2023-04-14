let key;

const classTableCol1 = "Course Number";
const classTableCol2 = "Class Name";
const classTableCol3 = "Tag";

let tagList;

$(document).ready(function()
{
    //Add event listener for sign in form
    // let signInForm = document.getElementById("signInForm");
    // signInForm.addEventListener("submit", function(event){
    //     event.preventDefault();

    //     const formData = new FormData(signInForm);

    //     fetch('/Admin/SignIn', {
    //         method: 'POST',
    //         body: formData
    //       })
    //       .then(response => response.json())
    //       .then(data => {
    //         if(data.status == 'success')
    //         {
    //             key = data.token;
    //         }
    //         else
    //         {
    //             alert('sign in failed');
    //         }
    //       })
    //       .catch(error => {
    //         // handle errors
    //         console.error(error);
    //       });
    // });

    //Initalizing the DataTable
    let classDataTable = $("#myTable").DataTable({
        columns: [
            {name: classTableCol1, title: classTableCol1},
            {name: classTableCol2, title: classTableCol2},
            {name: classTableCol3, title: classTableCol3}
        ]
    });

    //Use ajax to get all the tags.
    $.ajax({
        url: "/Admin/GetTags",
        type: "Get",
        success: function(data){
            tagList = JSON.parse(data);
            createTagDropdown(tagList);
        },
        error: function(request, error){
            alert("Error retriving tag data");
        }
    });

    //Start with DataTable being full
    getAllClassesInDataTable(classDataTable);

    //Hookup EventLister to Tag Form
    let tagForm = document.getElementById("tagForm");
    tagForm.addEventListener("submit", function(event){
        event.preventDefault();
        let tagId = $("#tagForm select")[0].value;
        if(tagId == "all")
        {
            //ajax all classes
            getAllClassesInDataTable(classDataTable);
        }
        else
        {
            //ajax the corresponding tag number
            getTagClassesInDataTable(classDataTable, tagId);
        }
    });

    
});

//tableData: An Array of Class Objects
function drawClassDataTable(dataTable, tableData)
{
    dataTable.clear();
    for(let i = 0; i < tableData.length; i++)
    {
        classNumber = tableData[i][classTableCol1];
        className = tableData[i][classTableCol2];
        classTag = tableData[i][classTableCol3];
        dataTable.row.add([classNumber, className, classTag]);
    }
    dataTable.draw();
}

function getAllClassesInDataTable(classDataTable){
    $.ajax({
        url: "/Admin/GetClasses",
        type: "Get",
        success: function(data){
            let tableData = JSON.parse(data);
            drawClassDataTable(classDataTable, tableData);
        },
        error: function(request, error){
            alert("Error retriving table data");
        }
    });
}

function getTagClassesInDataTable(classDataTable, tagId)
{
    $.ajax({
        url: "/Admin/GetTaggedClasses",
        type: "Get",
        data: {"tagId": tagId},
        success: function(data){
            let result = JSON.parse(data);
            if(result["status"] == 1)
            {
                let tableData = result["classData"];
                drawClassDataTable(classDataTable, tableData);
            }
            else
            {
                alert("Error retriving table data");
            }
        },
        error: function(request, error){
            alert("Error retriving table data");
        }
    });
}

function createTagDropdown(data)
{
    let dropdown = document.getElementById("tag")
    for(let i = 0; i < data.length; i++)
    {
        let option = document.createElement("option");
        option.setAttribute("value", data[i]["tagId"]);
        option.innerText = data[i]["tagName"];
        dropdown.appendChild(option);
    }
}
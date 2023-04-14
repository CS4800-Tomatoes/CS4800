let key;

const COURSE_NUMBER = "Course Number";
const CLASS_NAME = "Class Name";
const TAG = "Tag";

let tagList;
let classDataTable;

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
    classDataTable = $("#myTable").DataTable({
        dom: '<"dataTableTop"fB>rt<"dataTableBottom"ip>',
        buttons: [
            {
                text: "Add",
                action: function(e, dt, node, config){
                    //Create Modal which represents which classes are in a tag
                    //and which classes aren't. Have the classes get moved over

                    //Called a Dual Select Box or a Dual List Box
                    //Bootstrap has UI for it
                    //JQuery UI has a widget for it
                    alert("test");
                }
            }
        ],
        columns: [
            {name: CLASS_NAME, title: CLASS_NAME,
                render: function(data, type, row){
                    return `CS${data[COURSE_NUMBER]} ${data[CLASS_NAME]}`;
                }
            },
            {
                render: function(data, type, row, meta){
                    let editBtn = `<button id="editBtn${meta.row}" 
                        class="btn btn-secondary"
                        onclick="EditClassInDataTable(${meta.row})">Edit</button>`
                    return editBtn;
                }
            }
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
        //provide rowInfo to dataTable
        dataTable.row.add([tableData[i], i]);
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

function EditClassInDataTable(row)
{
    console.log(row);

    //Get Data fro row=4 as an array. [0]=class pojo, [1] = row
    //classDataTable.row(4).data()[0] == class pojo

    //Probably will make a modal show up which shows all the tags it has on right
    //Have arrows to move all leftover tags to left
    

    //I just realized I'm silly and I don't even need an edit btn.
    //Just need an add button to add a class to a certain tag. oops.
}
let key;

const COURSE_NUMBER = "Course Number";
const CLASS_NAME = "Class Name";
const TAG = "Tag";

let tagList;
let currentTagId = -1;

let completeClassList;
let currentClassList;

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
    const updateClassTagDialogBox = $("#updateOneClassByOneTagDialog").dialog({
        autoOpen: false,
        modal: true,
        width: window.innerWidth/2
    });

    //Initalizing the DataTable
    classDataTable = $("#myTable").DataTable({
        dom: '<"dataTableTop"fB>rt<"dataTableBottom"ip>',
        buttons: [
            {
                text: "Add Tag to Class",
                action: function(e, dt, node, config){
                    //Creating a Dialog Box which will allow a user to add a tag to a class

                    if(currentTagId == -1)
                        alert('No tag to add');
                    else{
                        //Display the Tag to Add's Name
                        let tagObject = tagList.find((el)=>{
                            return el.tagId == currentTagId;
                        });
                        $("#tagToAdd")[0].innerText = tagObject.tagName;

                        //Finding which classes don't have the current tags
                        let classIdOfClassesWithTag = currentClassList.map(el=>el["_id"]["$oid"]);
                        let classesWithoutTag = completeClassList.filter(
                            el => {
                                let id = el["_id"]["$oid"];
                                return !classIdOfClassesWithTag.includes(id);
                            }
                        );

                        //Fill in the addTagsToClassesDropdown
                        let classesDropdown = document.getElementById("addTagToClassOptionList")
                        for(let i = 0; i < classesWithoutTag.length; i++)
                        {
                            let option = document.createElement("option");
                            option.setAttribute("value", classesWithoutTag[i]["_id"]["$oid"]);
                            option.innerText = classesWithoutTag[i]["Class Name"];
                            classesDropdown.appendChild(option);
                        }

                        //Actually opening the dialog
                        updateClassTagDialogBox.dialog("open");
                    }
                }
            },
            {
                text: "Delete Tag from Class",
                className: "btn-delete-tag-from-class",
                action: function(e, dt, node, config){
                    if(currentTagId == -1)
                        alert('No tag to delete');
                    else
                    {
                        let classToRemoveId = classDataTable.rows({selected:true}).data()[0][1]["_id"]["$oid"];
                        let tagToRemoveID = currentTagId;

                        $.ajax({
                            url: "/Admin/RemoveTagFromClass",
                            type: "Patch",
                            data: {
                                classId: classToRemoveId,
                                tagId: tagToRemoveID
                            },
                            success: function(data){
                                getTagClassesInDataTable(classDataTable, currentTagId);
                            },
                            error: function(request, error){
                                alert("Error retriving updating table data");
                            }
                        });
                    }
                }
            }
        ],
        columns: [
            {
                orderable: false,
                className: 'select-checkbox',
                targets:   0
            }, 
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
        ],
        select: {
            style: 'os',
            selector: 'td:first-child',
            multi: true
        },
        order: [[ 1, 'asc' ]]
    });

    //Make sure you can only delete when you are clicked on one thing
    //classDataTable.button(".btn-delete-tag-from-class").disable();

    classDataTable.on('select deselect draw.dt', function (){
        var selectedRows = classDataTable.rows({selected: true}).count();
        if (selectedRows === 1) {
            classDataTable.button(".btn-delete-tag-from-class").enable();
        } else {
            classDataTable.button(".btn-delete-tag-from-class").disable();
        }
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

    //Hookup EventListener to Tag Form
    let tagForm = document.getElementById("tagForm");
    tagForm.addEventListener("submit", function(event){
        event.preventDefault();
        let tagId = $("#tagForm select")[0].value;
        if(tagId == "all")
        {
            //ajax all classes
            getAllClassesInDataTable(classDataTable);
            currentTagId = -1;
        }
        else
        {
            //ajax the corresponding tag number
            getTagClassesInDataTable(classDataTable, tagId);
            currentTagId = tagId;
        }
    });

    //Hookup EventListener to Add Tag to Class Form
    let addTagToClassForm = $("#addTagToClassForm")[0];
    addTagToClassForm.addEventListener("submit", function(e){
        e.preventDefault();
        let mongodbOID = $("#addTagToClassForm select")[0].value;
        console.log(mongodbOID);
        console.log(currentTagId);
        //Maybe make it so that the select dropdown of Tag is automatic instead of by submit

        $.ajax({
            url: "/Admin/AddTagToClass",
            type: "Patch",
            data: {
                classId: mongodbOID,
                tagId: currentTagId
            },
            success: function(data){
                getTagClassesInDataTable(classDataTable, currentTagId);
                updateClassTagDialogBox.dialog("close");
            },
            error: function(request, error){
                alert("Error retriving updating table data");
            }
        });
    });
});

//tableData: An Array of Class Objects
function drawClassDataTable(dataTable, tableData)
{
    dataTable.clear();
    for(let i = 0; i < tableData.length; i++)
    {
        //provide rowInfo to dataTable
        dataTable.row.add([null, tableData[i], i]);
    }
    dataTable.draw();
}

function getAllClassesInDataTable(classDataTable){
    if(completeClassList == undefined)
    {
        $.ajax({
            url: "/Admin/GetClasses",
            type: "Get",
            success: function(data){
                let tableData = JSON.parse(data);
                completeClassList = tableData;
                drawClassDataTable(classDataTable, tableData);
            },
            error: function(request, error){
                alert("Error retriving table data");
            }
        });
    }
    else
    {
        drawClassDataTable(classDataTable, completeClassList);
    }
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
                currentClassList = tableData;
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

function createTagDropdown(tagList)
{
    let dropdown = document.getElementById("tag")
    for(let i = 0; i < tagList.length; i++)
    {
        let option = document.createElement("option");
        option.setAttribute("value", tagList[i]["tagId"]);
        option.innerText = tagList[i]["tagName"];
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
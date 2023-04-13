var key;

$(document).ready(function()
{
    //Add event listener for sign in form
    var signInForm = document.getElementById("signInForm");
    document.addEventListener("submit", function(event){
        event.preventDefault();

        const formData = new FormData(signInForm);

        fetch('/Admin/SignIn', {
            method: 'POST',
            body: formData
          })
          .then(response => response.json())
          .then(data => {
            if(data.status == 'success')
            {
                key = data.token;
            }
            else
            {
                alert('sign in failed');
            }
          })
          .catch(error => {
            // handle errors
            console.error(error);
          });
    });
});
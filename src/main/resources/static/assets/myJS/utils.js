function filterTbl(input)
{
  var input, filter, table, tr, td, cell, i, j;
  input = document.getElementById("search");
  filter = input.value.toUpperCase();
  table = document.getElementById("tbl");
  tr = table.getElementsByTagName("tr");
  for (i = 1; i < tr.length; i++) {
    // Hide the row initially.
    tr[i].style.display = "none";

    td = tr[i].getElementsByTagName("td");
    for (var j = 0; j < td.length; j++) {
      cell = tr[i].getElementsByTagName("td")[j];
      if (cell) {
        if (cell.innerHTML.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
          break;
        }
      }
    }
  }
}

function confirmDelete(url)
{
    Swal.fire({
      title: 'Are you sure you want to delete this record?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          'Deleted!',
          'Record has been deleted successfully.',
          'success'
        ).then((result) =>
        {
             if (result.isConfirmed)
             {
                window.location.replace(url);
             }
         });
      }
    })
}

function printPos(id, type)
{
    var endpoint;

    if(type == 1)
    {
        endpoint = '/water/print/' + id + '/';
    }
    else
    {
        endpoint = '/para/print/' + id + '/';
    }

    axios
      .get(endpoint)
      .then((response) =>
      {
          var status = response.data;
          console.log("status = " + status);

          if(status == 1)
          {
              Swal.fire('Success', 'Receipt is ready', 'success');
          }
          else
          {
              Swal.fire('Oops!', 'Printer may not be Connected or Check power supply', 'error');
          }
      })
      .catch((error) =>
      {
          console.log("error " + error);
              Swal.fire('Oops!', 'Printer may not be Connected or Check power supply', 'error');
      });
}

$(document).ready(function() {
    var formInfo = $('#formInfo');
    var formLogin = $('#formLogin');
    var btnSaveInfo = $('#btnSaveInfo');
    var btnSaveLogin = $('#btnSaveLogin');

    formInfo.on('submit', function(event) 
    {
        if (formInfo[0].checkValidity()) 
        {
            event.preventDefault();

            btnSaveInfo.val('Please Wait');
            btnSaveInfo.addClass('disabled')

            var endpoint = '/config/info/';

            var data = new FormData();
            data.append('title', $('#title').val());
            data.append('header', $('#header').val());
            data.append('footer', $('#footer').val());
            data.append('contact', $('#contactNo').val());
            data.append('address', $('#address').val());
            data.append('printer', $('#printer').val());

            axios.post(endpoint, data)
            .then((response) =>
                  {
                      var status = response.data;
                      console.log("status = " + status);
            
                      if(status == 1)
                      {
                          Swal.fire('Success', 'Data Saved Successfully', 'success');
                      }
                      else
                      {
                          Swal.fire('Oops!', 'Something went wrong. Please Try again', 'error');
                      }
                  })
                  .catch((error) =>
                  {
                      console.log("error " + error);
                      Swal.fire('Oops!', 'Some Error occured while saving data. Please try again later.', 'error');
                  })
                  .finally(() => {
                     btnSaveInfo.val('Save');
                     btnSaveInfo.removeClass('disabled')
                  });
        }
    });
    
    formLogin.on('submit', function(event) 
        {
            if (formLogin[0].checkValidity()) 
            {
                event.preventDefault();
    
                btnSaveLogin.val('Please Wait');
                btnSaveLogin.addClass('disabled')
    
                var endpoint = '/config/login/';

                var data = new FormData();
                data.append('usernameCur', $('#usernameCur').val());
                data.append('usernameNew', $('#usernameNew').val());
                data.append('passwordCur', $('#passwordCur').val());
                data.append('passwordNew', $('#passwordNew').val());
    
                console.log('data = ' + data);
                console.log('data.toString() = ' + data.toString());
    
                axios.post(endpoint, data)
                .then((response) =>
                      {
                          var status = response.data;
                          console.log("status = " + status);
                
                          if(status == 1)
                          {
                              Swal.fire('Success', 'Login credentials updated Successfully', 'success');
                              formLogin[0].reset();
                          }
                          else
                          {
                              Swal.fire('Oops!', 'Invalid Username or Password', 'error');
                          }
                      })
                      .catch((error) =>
                      {
                          console.log("error " + error);
                          Swal.fire('Oops!', 'Some Error occured while saving data. Please try again later.', 'error');
                      })
                      .finally(() =>
                      {
                             btnSaveLogin.val('Save');
                             btnSaveLogin.removeClass('disabled')
                      });;
                }
        });
});

var isEditing = 0;

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

function deleteOrderDet(id, btnDelete)
{
    axios.get("/water/order/delete/" + id + "/")
    .then((response) =>
      {
          var status = response.data;
          console.log("status = " + status);

          if(status == 1)
          {
              $(btnDelete).closest('tr').remove();
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
}

function showData(billNo)
{
    isEditing = 1;
    $('#formOrderDet')[0].reset();
    $('#tblOrder > tbody').empty();

    axios.get("/water/orderdet/" + billNo + "/")
        .then((response) =>
          {
              var listObj = response.data;

              if(listObj == null)
              {
                  Swal.fire('Oops!', 'Something went wrong. Please Try again', 'error');
              }
              else
              {
                  $.each(listObj, function (index, obj)
                  {
//                      const row = $("<tr style='background: var(--bs-modal-bg);'>");
//                      row.append($("<td style='text-align: left;padding: 4px;'>").text($("#idActivity option[value='" + obj.idActivity + "']").text()));
//                      row.append($("<td style='text-align: right;padding: 4px;'>").text(obj.rate));

                      var str = "<tr style='background: var(--bs-modal-bg);'> <td style='text-align: left;padding: 4px;'> " + $("#idActivity option[value='" + obj.idActivity + "']").text() + " </td> <td style='text-align: right;padding: 4px;'>" + obj.rate + "</td> <td style='text-align: right;padding: 4px;'>" + obj.persons + "</td> <td style='text-align: right;padding: 4px;'>" + (obj.rate * obj.persons) + "</td> <td style='text-align: center;padding: 4px;'> <div class='btn-group' role='group'> <button class='btn btn-danger' id='btnDelete2' type='button' style='border-color: var(--bs-pink);' onclick='deleteOrderDet(" + obj.id + ", this)'> <i class='fas fa-trash'></i> </button></div> </td> </tr>";
                      $('#tblOrder > tbody').append(str);
                  });

                  var columnIndex = 3;
                  var sum = 0;

                  $("#tblOrder tbody tr").each(function() {
                      var cellValue = parseFloat($(this).find("td:eq(" + columnIndex + ")").text());
                      if (!isNaN(cellValue)) {
                          sum += cellValue;
                      }
                  });

                  // Display the sum
                  $("#total").text(sum);
              }
          })
          .catch((error) =>
          {
              console.log("error " + error);
              Swal.fire('Oops!', 'Some Error occured while saving data. Please try again later.', 'error');
          })

    axios.get("/water/order/" + billNo + "/")
        .then((response) =>
          {
              var obj = response.data;
              console.log("obj = " + obj.toString());

              if(obj == null)
              {
                  Swal.fire('Oops!', 'Something went wrong. Please Try again', 'error');
              }
              else
              {
                  $('#billNo').val(obj.billNo);
                  $('#customerName').val(obj.customerName);
                  $('#contact').val(obj.contact);
              }
          })
          .catch((error) =>
          {
              console.log("error " + error);
              Swal.fire('Oops!', 'Some Error occured while saving data. Please try again later.', 'error');
          })
}

function confirmLogout()
{
    Swal.fire({
      title: 'Are you sure you want to Logout?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, Logout'
    }).then((result) => {
      if (result.isConfirmed) {
                window.location.replace('/logout');
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
              Swal.fire('Oops!', 'Something went wrong. Please Try again', 'error');
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
    var formOrderDet = $('#formOrderDet');
    var btnSaveInfo = $('#btnSaveInfo');
    var btnSaveLogin = $('#btnSaveLogin');
    var btnAddOrder = $('#btnAddOrder');
    var tbl = $('#tblOrder > tbody');

    formOrderDet.on('submit', function(event) 
    {
        if (formOrderDet[0].checkValidity()) 
        {
            event.preventDefault();

            btnAddOrder.addClass('disabled')

            const endpoint = '/water/add/';

            var data = {
              billNo:  $('#billNo').val(),
              customerName: $('#customerName').val(),
              contact: $('#contact').val(),
              rate: $('#rate').val(),
              persons: $('#persons').val(),
              idActivity: $('#idActivity').val()
            };

            axios.post(endpoint, {}, {params:data})
                .then((response) =>
                  {
                      var obj = response.data;
            
                      if(obj == null)
                      {
                          Swal.fire('Oops!', 'Something went wrong. Please Try again', 'error');
                      }
                      else
                      {
                          var str = "<tr style='background: var(--bs-modal-bg);'> <td style='text-align: left;padding: 4px;'> " + $("#idActivity option[value='" + obj.idActivity + "']").text() + " </td> <td style='text-align: right;padding: 4px;'>" + obj.rate + "</td> <td style='text-align: right;padding: 4px;'>" + obj.persons + "</td> <td style='text-align: right;padding: 4px;'>" + (obj.rate * obj.persons) + "</td> <td style='text-align: center;padding: 4px;'> <div class='btn-group' role='group'> <button class='btn btn-danger' id='btnDelete2' type='button' style='border-color: var(--bs-pink);' onclick='deleteOrderDet(" + obj.id + ", this)'> <i class='fas fa-trash'></i> </button></div> </td> </tr>";

                          tbl.append(str);

                          $("#idActivity").prop("selectedIndex", 0);
                          $('#rate').val('');
                          $('#persons').val('');
                          $('#billNo').val(obj.billNo);

                          var columnIndex = 3;
                          var sum = 0;

                          $("#tblOrder tbody tr").each(function() {
                              var cellValue = parseFloat($(this).find("td:eq(" + columnIndex + ")").text());
                              if (!isNaN(cellValue)) {
                                  sum += cellValue;
                              }
                          });

                          // Display the sum
                          $("#total").text(sum);
                      }
                  })
                  .catch((error) =>
                  {
                      console.log("error " + error);
                      Swal.fire('Oops!', 'Some Error occured while saving data. Please try again later.', 'error');
                  })
                  .finally(() => {
                     btnAddOrder.removeClass('disabled')
                  });
        }
    });

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
                      });
                }
        });

    $("#btnAdd").click(function()
    {
        isEditing = 0;
        $('#formOrderDet')[0].reset();
        $('#tblOrder > tbody').empty();
    });

    $("#modalAddNewItem").on("hide.bs.modal", function () {
        var billNo = $("#billNo").val();
        if(billNo && isEditing === 0)
        {
            window.location.replace('/water/');
        }

        isEditing = 0;
    });

    $("#filter").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#tbl tbody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });
});

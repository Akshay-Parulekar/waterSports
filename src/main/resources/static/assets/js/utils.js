var isNew = 0;

function confirmClearLogs(url)
{
    axios.get("/config/adminpassword/")
    .then((response) =>
      {
          var passwordCurDb = response.data;

          if(passwordCurDb)
          {
                (async () => {
                      const { value: password } = await Swal.fire({
                        title: "Enter admin password",
                        input: "password",
                        inputLabel: "To clear logs, you must be an Admin",
                        inputPlaceholder: "Enter admin password",
//                        inputAttributes: {
//                          maxlength: "10",
//                          autocapitalize: "off",
//                          autocorrect: "off"
//                        }
                      });

                      if (password == passwordCurDb)
                      {
                          Swal.fire(
                            'Done!',
                            'All Logs has been cleared successfully.',
                            'success'
                          ).then((result) =>
                          {
                               if (result.isConfirmed)
                               {
                                  window.location.replace(url);
                               }
                           });
                        }
                        else
                        {
                          Swal.fire('Invalid Password!', 'You have entered an Invalid Password', 'error');
                        }

                })()
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

function showRefData(id, name, idOwner)
{
    $('#id').val(id);
    $('#name').val(name);
    $('#idOwner').val(idOwner);
}

function showData(billNo)
{
    isNew = 0;
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
                  $('#idOrder').val(obj.id);
                  $('#billNo').val(obj.billNo);
                  $('#customerName').val(obj.customerName);
                  $('#contact').val(obj.contact);
                  $('#serialNo').val(obj.serialNo);
                  $('#idRef').val(obj.idRef);
                  $('#idRef').trigger('change');
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

//    $('.js-example-basic-single').select2({
//                                            placeholder: "Select Owner",
//                                             allowClear: true,
//                                             dropdownParent: $('#modalAddNewItem')
//                                          });

    var formInfo = $('#formInfo');
    var formLogin = $('#formLogin');
    var formOrderDet = $('#formOrderDet');
    var formAdminPassword = $('#formAdminPassword');
    var btnSaveInfo = $('#btnSaveInfo');
    var btnSaveLogin = $('#btnSaveLogin');
    var btnAddOrder = $('#btnAddOrder');
    var btnSaveAdminPassword = $('#btnSaveAdminPassword');
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
              serialNo: $('#serialNo').val(),
              idRef: $('#idRef').val(),
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

                           if ($("#tblOrder td:nth-child(1):contains('" + $("#idActivity option[value='" + obj.idActivity + "']").text() + "')").length > 0)
                           {
                                $("#tblOrder td:nth-child(2)").text(obj.rate);
                                $("#tblOrder td:nth-child(3)").text(obj.persons);
                           }
                           else
                           {
                                tbl.append(str);
                           }

                          $("#idActivity").prop("selectedIndex", 0);
                          $('#rate').val('');
                          $('#persons').val('');
                          $('#billNo').val(obj.billNo);
                          $('#idOrder').val(obj.id);

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

    formAdminPassword.on('submit', function(event)
    {
        if (formAdminPassword[0].checkValidity())
        {
            event.preventDefault();

            btnSaveAdminPassword.val('Please Wait');
            btnSaveAdminPassword.addClass('disabled')

            var endpoint = '/config/adminpassword/';

            var data = new FormData();
            data.append('passwordCur', $('#passwordAdminCur').val());
            data.append('passwordNew', $('#passwordAdminNew').val());

            console.log('data = ' + data);
            console.log('data.toString() = ' + data.toString());

            axios.post(endpoint, data)
            .then((response) =>
                  {
                      var status = response.data;
                      console.log("status = " + status);

                      if(status == 1)
                      {
                          Swal.fire('Success', 'Admin Password updated Successfully', 'success');
                          formAdminPassword[0].reset();
                      }
                      else
                      {
                          Swal.fire('Oops!', 'Invalid Current Admin Password', 'error');
                      }
                  })
                  .catch((error) =>
                  {
                      console.log("error " + error);
                      Swal.fire('Oops!', 'Some Error occured while saving data. Please try again later.', 'error');
                  })
                  .finally(() =>
                  {
                         btnSaveAdminPassword.val('Save');
                         btnSaveAdminPassword.removeClass('disabled')
                  });
            }
    });

    $("#btnAdd").click(function()
    {
        isNew = 1;
        $('#idOrder').val('');
        $('#billNo').val('');

        $('#formOrderDet')[0].reset();
        $('#tblOrder > tbody').empty();
    });

    $("#btnAddRef").click(function()
    {
        $('#id').val('');
        $('#name').val('');
        $('#idOwner').val('');
        $('#formRef')[0].reset();
    });

    $("#modalAddNewItem").on("hide.bs.modal", function () {
        var billNo = $("#billNo").val();
        if(billNo && isNew)
        {
            window.location.replace('/water/');
        }

        $('#idOrder').val('');
        $('#billNo').val('');
    });

    $("#filter").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#tbl tbody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });
});

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
              Swal.fire('Oops!', 'Printer not Connected. Check power supply', 'error');
          }
      })
      .catch((error) =>
      {
          console.log("error " + error);
          Swal.fire('Oops!', 'Some Error occured while saving data. Please try again later.', 'error');
      });
}

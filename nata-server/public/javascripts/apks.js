$(function() {
  $('#btn-logapk').click(function (e) {
    e.preventDefault();
    var apk = $('#apk-form').serialize();
    console.log(apk);
    $.ajax({
      url: '/api/v1/apks',
      type: 'POST',
      data:  apk,
      success: function (apk) {
        //var toAppend = "<tr>";
        //toAppend += "<td>" + apk.name + "</td>";
        //toAppend += "<td>" + apk.version+ "</td>";
        //toAppend += "<td>" + apk.package_name+ "</td>";
        //toAppend += "<td>" + apk.activity_name+ "</td>";
        //toAppend += '<td><a href="#" class="btn-remove btn btn-danger btn-xs" role="button" data-id=' +apk._id + '>删除' + '</a></td></tr>';
        //$('#apk_table').append(toAppend);
        location.reload();
      },
      error: function (message) {

      },
    });
  });


  //$('#apk_table').delegate('a','click', function(e) {
  //  e.preventDefault();
  //  var apk_id = $(this).data('id');
  //  $.ajax({
  //    url: '/api/v1/apks/' + apk_id,
  //    type: 'DELETE',
  //    success: function () {
  //      $(e.target).parent().parent().remove();
  //    },
  //    error: function (message) {
  //      alert("删除失败");
  //    }
  //  });
  //});
});


$(function() {
  $('.btn-del-actpath').click(function (e) {
    e.preventDefault();
    var apk_id = $(this).data("id");
    var act_name = $(this).data("act");

    $.ajax({
      url: '/api/v1/apks/' + apk_id +"/" + act_name,
      type: 'DELETE',
      success: function (message) {
        location.reload();
      },
      error: function (message) {

      },
    });
  });

  $('#btn-updateapk').click(function (e) {
    e.preventDefault();
    var apk = $('#apk-form').serialize();
    var apk_id = $(this).data("id");
    console.log(apk);
    $.ajax({
      url: '/api/v1/apks/'+ apk_id,
      type: 'PUT',
      data:  apk,
      success: function (apk) {
        location.reload();
      },
      error: function (message) {

      },
    });
  });

});
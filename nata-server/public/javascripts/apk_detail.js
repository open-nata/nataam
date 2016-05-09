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
});
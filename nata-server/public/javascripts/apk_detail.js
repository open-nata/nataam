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

  $('#btn-blacklist').click(function(e){
    e.preventDefault();
    var item = $('#input-blacklist').val().trim();
    if( !item ) return;
    var apk_id = $(this).data('id');

    $.ajax({
      url: '/api/v1/apks/'+ apk_id +'/blacklist',
      type: 'POST',
      data:  {item : item},
      success: function () {
        location.reload();
      },
      error: function (message) {
        alert("保存出错");
      },
    });


    //var toAppend = '<li class="list-group-item" >'
    //  + item
    //  + '<span class="remove-blacklist text-danger icon ion-minus-circled pull-right"></span>'
    //  + '</li>';
    //
    //$('#ul-blacklist').append(toAppend);
  })


  $('#ul-blacklist').delegate('.remove-blacklist','click',function(e){
    var listItem = $(this).parent().text();
    var apk_id = $(this).data('id');

    $.ajax({
      url: '/api/v1/apks/'+ apk_id +'/blacklist',
      type: 'DELETE',
      data:  {item : listItem},
      success: function () {
        location.reload();
        return false;
      },
      error: function (message) {
        alert("删除出错");
        return false;
      },
    });

  });

});
$(function () {
  var baseUrl = "http://localhost:9001";

  $('#btn-create').on('click', function (e) {
    e.preventDefault();
    var testcase = $('#testcase-form').serialize();

    $.ajax({
      url: "/api/v1/testcases",
      data: testcase,
      type: 'POST',
      success: function (testcase) {
        location.reload();
      },
      error: function (request) {
        alert("创建录制任务失败");
      }
    });
  });

  $('.btn-remove').on('click', function (e) {
    e.preventDefault();
    var testcase_id = $(this).data('id');
    $.ajax({
      url: '/api/v1/testcases/' + testcase_id,
      type: 'DELETE',
      success: function (message) {
        location.reload();
      },
      error: function (message) {
        alert("删除失败");
      }
    });
  });

  //$('.btn-start').on('click', function (e) {
  //  e.preventDefault();
  //  var testcase_id= $(this).data('id');
  //  $.ajax({
  //    url: '/testcases/' + testcase_id + '/start',
  //    type: 'GET',
  //    success: function (message) {
  //
  //    },
  //    error: function(message){
  //    }
  //  });
  //});

});
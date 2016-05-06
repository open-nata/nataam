$(function() {
  $('#btn-save').on('click',function (e) {
    e.preventDefault();
    var testcase_id = $(this).data("id");
    var name = $('#name').val();
    var actions = $('#actions').val().split("\n");
    $.ajax({
      url: "/api/v1/testcases/"+ testcase_id+"/save",
      type: 'PUT',
      data: {name:name,actions: actions},
      traditional:true,
      success: function (message) {
        window.location.href = '/testcases';
      },
      error: function () {

      }
    });
  });
});

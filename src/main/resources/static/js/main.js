$('#table #eBtn').on('click', function (event) {
    event.preventDefault();
    let href = $(this).attr('href');
    $.get(href, function (user, status) {
        $('#id').val(user.id);
        $('#name').val(user.name);
        $('#password').val(user.password);
    });
    $('#myForm #exampleModal').modal();
});
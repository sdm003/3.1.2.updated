$(document).ready(
    getAllUsers());

// Для отображения списка пользователей в таблице
function getAllUsers() {
    alert("getAllUsers");
    $("#table").empty();
    $.ajax({
        type: 'GET',
        url: '/admin/rest',
        // timeout: 3000, // сработает через 3с
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) { // из полученных данных проходим по всем элементам
                $('#table').append($('<tr>').append(
                    $('<td>').append($('<span>')).text(user.id), //Установить текстовое содержимое для всех элементов <span>
                    $('<td>').append($('<span>')).text(user.name), // firstname
                    $('<td>').append($('<span>')).text(user.street),
                    $('<td>').append($('<span>')).text(user.age),
                    $('<td>').append($('<span>')).text(user.roles),
                    $('<td>').append($('<button>').text("Edit").attr({ //Метод attr () устанавливает или возвращает атрибуты и значения выбранных элементов.
                        "type": "button",  // .attr(attribute,value)
                        "class": "btn btn-info edit",
                        "data-toggle": "modal",
                        "data-target": "#myModalEdit",
                    }).data("user", user)), //data(name,value) возращает данные из выбранного элемента(здесь button)
                    $('<td>').append($('<button>').text("Delete").attr({
                        "type": "button",
                        "class": "btn btn-danger delete",
                        "data-toggle": "modal",
                        "data-target": "#myModalDelete",
                    }).data("user", user))
                ))
            });
        }
    });
}

//Для редактирования пользователя через модальное окно
$(document).on("click", ".edit", function () {
    let user = $(this).data('user');
    $('#nameInput').val(user.name);
    $('#streetInput').val(user.street);
    $('#idInput').val(user.id);
    $('#ageInput').val(user.age);
    $('#passwordInput').val(user.password);

});

$(document).on("click", ".editUser", function () {
    let editUser = {};
    editUser.id = $('#idInput').val();
    editUser.name = $('#nameInput').val();
    editUser.age = $('#ageInput').val();
    editUser.street = $('#streetInput').val();
    editUser.password = $('#passwordInput').val();
    editUser.roles = getCheckedRoles( $('#roleEdit').val());

    $.ajax({
        type: 'POST',
        url: '/admin/edit',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(editUser),
        success: function () {
            getAllUsers();
        }
    });
});

//Для удаления пользователя через модальное окно
$(document).on("click", ".delete", function () {
    let user = $(this).data('user');
    $('#id').val(user.id);
    $('#name').val(user.name);
    $('#street').val(user.street);
    $('#age').val(user.age);
    $('#role').val(user.roles);
    $('#password').val(user.password);

    $(document).on("click", ".deleteUser", function () {
        $.ajax({
            type: 'GET',
            url: '/admin/delete/',
            data: { 'id': user.id },
            success: function () {
                getAllUsers();
            },
            error: function(xhr) {
                console.log(error);
            }
        });
    });

});

// Для добавления пользователя
$('.addUser').click(function () {
    $('#usersTable').trigger('click');

    let addUser = {};
    addUser.name = $('#addName').val();
    addUser.age = $('#addAge').val();
    addUser.street = $('#addStreet').val();
    addUser.password = $('#addPassword').val();
    addUser.roles = getCheckedRoles($('#addRole').val());

    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        url: '/admin/add',
        data: JSON.stringify(addUser),
        // timeout: 100,
        success: function () {
            $('.formAddUser')[0].reset(); // очищает форму
            getAllUsers();
        }
    });
});
//Для отображения информации о пользователе на его странице и сокрытия меню в зависимости от роли
// $(document).ready(function () {
$(function () {
    $("#userTable").empty(); // Удалить содержимое всех элементов блока с id = "table"
    $.ajax({
        type: 'GET',
        url: '/user/getUser',
        // timeout: 3000,
        error: function () {
            $('#blockMenuForUser').hide();
        },
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                if (user.roles === "USER") {
                    $('#menuUser').trigger('click');
                    $('#main2').trigger('click');
                    $('#blockMenuForAdmin').hide();
                }
                $('#userTable').append($('<tr>').append(
                    $('<td>').append($('<span>')).text(user.id),
                    $('<td>').append($('<span>')).text(user.name),
                    $('<td>').append($('<span>')).text(user.age),
                    $('<td>').append($('<span>')).text(user.street),
                    $('<td>').append($('<span>')).text(user.roles)
                ))
            });
        }
    });
});

function getCheckedRoles(roleName) {
    let roles = [];
    let role = {};

    if (roleName === "ADMIN") {
        role.id = "1";
    } else if (roleName === "USER") {
        role.id = "2";
    }

    roles.push(role);
    return roles;
};
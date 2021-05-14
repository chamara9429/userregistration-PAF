$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});




$(document).on("click", "#adduser", function (event) {
    // Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    // Form validation-------------------
    var status = validateUserForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
    // If valid------------------------
    var type = ($("#hiddenIDSave").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "UserrRegisterAPI",
            type: type,
            data: $("#registrationform").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onUserSaveComplete(response.responseText, status);
            }
        });
});

function onUserSaveComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully saved.");
            $("#alertSuccess").show();
            $("#divuserGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while saving.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while saving..");
        $("#alertError").show();
    }
    $("#hiddenIDSave").val("");
    $("#registrationform")[0].reset();
}

$(document).on("click", ".btnUpdate", function (event) {
    $("#hiddenIDSave").val($(this).data("id"));
    $("#name").val($(this).closest("tr").find('td:eq(1)').text());
    $("#email").val($(this).closest("tr").find('td:eq(2)').text());
    $("#contact").val($(this).closest("tr").find('td:eq(3)').text());
    $("#type").val($(this).closest("tr").find('td:eq(4)').text());
});

$(document).on("click", ".btnRemove", function (event) {
    $.ajax(
        {
            url: "UserrRegisterAPI",
            type: "DELETE",
            data: "id=" + $(this).data("id"),
            dataType: "text",
            complete: function (response, status) {
                onUserDeleteComplete(response.responseText, status);
            }
        });
});

function onUserDeleteComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully deleted.");
            $("#alertSuccess").show();
            $("#divuserGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while deleting.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while deleting..");
        $("#alertError").show();
    }
}

//CLIENT-MODEL================================================================
function validateUserForm() {
    // Name
    if ($("#name").val().trim() == "") {
        return "Insert User Name.";
    }
    // Email
    if ($("#email").val().trim() == "") {
        return "Insert Email.";
    }
    // Contact-------------------------------
    if ($("#contact").val().trim() == "") {
        return "Insert Contact.";
    }
 // Type-------------------------------
    if ($("#type").val().trim() == "-") {
        return "Insert Type.";
    }
   
    return true;
}
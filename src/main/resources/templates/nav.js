window.addEventListener('load', function () {
    // 네비바 추가
    var allElements = document.getElementsByTagName('header');
    Array.prototype.forEach.call(allElements, function (el) {
        var includePath = el.dataset.includePath;
        if (includePath) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    el.outerHTML = this.responseText;
                }
            };
            xhttp.open('GET', includePath, true);
            xhttp.send();
        }
    });
});

function checkForm() {
    let id = $("#userID").val();
    let pw = $("#userPW").val();
    let pwCheck = $("#userPW_check").val();
    let email = $("#userEmail").val();

    // alert(id)
    // alert(pw)
    // alert(pwCheck)
    // alert(email)

    if (id.length < 4 || id.length > 21) {
        checkFormReset();
        $("#userID_label").css("display", "inline");
        $("#userID").focus();
        return false;
    }

    if (pw.length < 8) {
        checkFormReset();
        $("#userPW_label").css("display", "inline");
        $("#userPW").focus();
        return false;
    }

    if (pwCheck != pw) {
        checkFormReset();
        $("#userPW_check_label").css("display", "inline");
        $("#userPW_check").focus();
        return false;
    }

    var emailReg = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);

    if (!emailReg.test(email) || email.length < 1) {
        checkFormReset();
        $("#userEmail_label").css("display", "inline");
        $("#userEmail").focus();
        return false;
    }

    checkFormReset();

    var dup = checkDupID(id);

    return dup;
}

function checkFormReset() {
    $("#userID_label").css("display", "none");
    $("#userPW_label").css("display", "none");
    $("#userPW_check_label").css("display", "none");
    $("#userEmail_label").css("display", "none");
}

function checkDupID(id) {
    $.ajax({
        type: "POST",
        url: "/auth/checkID",
        data: { "id": id },
        success: function (data) {
            // true : 중복
            if (data) {
                alert("중복된 아이디!");
            } else {
                alert("회원가입 성공, 로그인 해주세요");
            }
            return data;
        }
    });

}

function confirmLogout() {
    if (confirm("로그아웃 하시겠습니까?")) {
        location.href = "/logout";
        return true;
    } else {
        return false;
    }
}
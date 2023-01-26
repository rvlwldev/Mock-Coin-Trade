// TODO : @Valid 로 변경 필요
function checkForm() {
    let id = $("#userID").val();
    let nickname = $("#userNickname").val();
    let pw = $("#userPW").val();
    let pwCheck = $("#userPW_check").val();
    let email = $("#userEmail").val();

    if (id.length < 4 || id.length > 21) {
        checkFormReset();
        $("#userID_label").css("display", "inline");
        $("#userID").focus();
        return false;
    }

    if (nickname.length < 2 || nickname.length > 6) {
        checkFormReset();
        $("#userNickname_label").css("display", "inline");
        $("#userNickname").focus();
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

    if(!dup){
        return false;
    }

    dup = checkDupNickName(nickname);

    if (!dup) {
        return false;
    }

    alert("회원가입 성공, 로그인 해주세요");
    $("#loginButton").click();

//    return true;
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
                return false;
            }
            return data;
        }
    });
}

function checkDupNickName(nickname) {
    $.ajax({
        type: "POST",
        url: "/auth/checkNickname",
        data: { "nickname": nickname },
        success: function (data) {
            // true : 중복
            if (data) {
                alert("중복된 닉네임!");
                return false;
            }
            return data;
        }
    });
}

function openLoginModal() {
    alert('로그인이 필요합니다.');
    document.getElementById("loginButton").click();
}
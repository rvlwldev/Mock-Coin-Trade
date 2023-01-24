// number Format
function toNumberFormat(number) {
    return Number(number).toString()
        .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}

// toOnlyNumber
function toPureNumber(numberStr) {
    return Number(numberStr.replace(/[^0-9.]/g, ""))
}

// modal
function getModalObject(id) {
    let option = { backdrop: true, keyboard: true, focus: true }
    return new bootstrap.Modal(document.getElementById(id), option);
}

function openModal(fullName, modalType, countNumber, priceNumber) {
    $('#modalLabel').text(fullName + " " + modalType);

    // result 설정
    $('#input_count').val(1);
    $('#result_count').text("1개");

    $('#input_price').val(toNumberFormat(countNumber) + "개");
    $('#result_price').text(toNumberFormat(priceNumber) + " ₩");

    modal.show();
    $('#input_count').focus();
}
//

// table Row To OBJ
function getTableRowInfo(elementArr) {
    let obj = {};

    elementArr.forEach = Array.prototype.forEach;

    elementArr.forEach(child => {
        let objname = child.getAttribute("name");
        let val = child.innerText;

        if (objname) obj[objname] = val;
    });

    return obj;
}


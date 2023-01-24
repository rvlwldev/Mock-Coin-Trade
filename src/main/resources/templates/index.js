setTimeout(function () {
    refreshList();
}, 1000);

// 목록 최신화 0.8초마다
function refreshList() {
    var interval = setInterval(
        function () {
            $.ajax({
                type: "POST",
                url: "/refresh",
                success: function (data) {
                    redrawList(data);
                },
                error: function () {
                    console.error("ERROR");
                    setTimeout(function () {
                        refreshList();
                    }, 1000);
                }
            });
        }
        , 800);
}

function redrawList(data) {
    let bodyChildren = $('#tableBody').children();

    for (let i = 0; i < bodyChildren.length; i++) {
        let tr = bodyChildren.get(i);
        let tds = tr.children;
        let info = data[i];

        if (tds.item(0).innerHTML == info.fullNameKO) {
            tds.item(1).innerHTML = info.tradePrice.toLocaleString() + ' ₩';
            tds.item(2).innerHTML = info.signedChangeRate.toLocaleString() + ' %';
            tds.item(3).innerHTML = Math.round(info.accTradePrice24h).toLocaleString() + '백만 원';
        }
    }
}

var modal = getModalObject("modal_buy");
var modal_info = {};
var modal_inputMode = "count";

// modal Click event
document.getElementsByName("showModalButton").forEach(button => {
    button.addEventListener('click', function (e) {

        modal_info = getTableRowInfo(e.target.parentElement.parentElement.children);

        openModal(
            modal_info.fullNameKO,
            "매입",
            modal_info.fullNameKO,
            modal_info.tradePrice
        );

        setViewOfModalResult();
    });
});

function setViewOfModalResult() {
    let count;
    let price;

    if (modal_inputMode == "count") {
        count = toPureNumber($('#input_count').val());
        price = toPureNumber(modal_info.tradePrice) * count;
    }
    else if (modal_inputMode == "price") {
        price = toPureNumber($('#input_price').val());
        count = price / toPureNumber(modal_info.tradePrice);
    }

    $('#result_count').text(toNumberFormat(count) + "개");
    $('#result_price').text(toNumberFormat(price) + " ₩");
}

function model_changeInputMode() {
    if (modal_inputMode == "count") {
        $("#input_count").attr("readOnly", false);
        $("#input_price").attr("readOnly", true);
    }
    else if (modal_inputMode == "price") {
        $("#input_count").attr("readOnly", true);
        $("#input_price").attr("readOnly", false);
    }
};

// dynamic event
$("#input_count").bind("focus", function () {
    modal_inputMode = "count";
    model_changeInputMode();
    setViewOfModalResult();
});
$("#input_price").bind("focus", function () {
    modal_inputMode = "price";
    model_changeInputMode();
    setViewOfModalResult();
});

$("#input_count").bind("change keyup", function () { setViewOfModalResult(); });
$("#input_price").bind("change keyup", function () { setViewOfModalResult(); });

function submitCheck() {
    modal_info.buyPrice = toPureNumber($('#result_price').text());
    modal_info.buyCount = toPureNumber($('#result_count').text());

    $.ajax({
        type: "POST",
        url: "/user/buy",
        data: modal_info,
        success: function (data) {
            modal.hide();
        },
        error: function () {
            alert("ERROR");
        }
    });
}
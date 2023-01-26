// TODO: amqp (RabbitMQ) 적용 알아보기 (interval 말구 subscribe 방식으로 ?)

setTimeout(function () {
    refreshList();
}, 1000);

function refreshList() {
    var interval = setInterval(
        function () {
            $.ajax({
                type: "POST",
                url: "/myPage/refresh",
                success: function (data) {
                    replaceList(data);
                },
                error: function () {
                    console.error("ERROR");
                    clearInterval(interval);

                    setTimeout(function () {
                        refreshList();
                    }, 10000);

                }
            });
        }
        , 800);
}


// 보유코인의 실시간으로 [v]
// 코인별 [현재가,수익률], 전체의 [현재가,수익률] 계산되어야함 [v]

function replaceList(data) {
    let bodyChildren = $('#tableBody').children();

    $('#total_percent').text('내 현재 수익률 : ' + data[1] + '%');
    $('#total_money').text('내 현재 수익금 : ' + data[2] + '₩');

    for (let i = 0; i < bodyChildren.length; i++) {
        let tr = bodyChildren.get(i);
        let tds = tr.children;
        let info = data[0][i];

        if (tds.item(0).innerHTML == info.fullNameKO) {
            tds.item(4).innerHTML = info.nowPrice.toLocaleString() + ' ₩';
            tds.item(5).innerHTML = info.rateOfReturn.toLocaleString() + ' %';
        }
    }
}

// TODO: 보유한 최대갯수, 보유한 최대금액 이상 또는 0 매도 안되게 막기 [v]
// TODO: 매도금액 또는 매도갯수의 따라 반응형 데이터 변경 (result_count, result_price) [v]
var modal = getModalObject("modal_sell");
var modal_info = {};
var modal_inputMode = "count";

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

document.getElementsByName("showSellButton").forEach(button => {
    button.addEventListener('click', function (e) {
        modal_info = getTableRowInfo(e.target.parentElement.parentElement.children);

        openModal(
            modal_info.fullNameKO,
            "매도",
            modal_info.cryptoCount,
            modal_info.investAmount
        );

        setViewOfModalResult();
    });
});

function setViewOfModalResult() {
    let price, count;

    if (modal_inputMode == "count") {
        count = toPureNumber($('#input_count').val());
        price = count * toPureNumber(modal_info.nowPrice);
    }
    else if (modal_inputMode == "price") {
        price = toPureNumber($('#input_price').val());
        count = price / toPureNumber(modal_info.nowPrice);
    }

    $('#result_count').text(toNumberFormat(count) + "개");
    $('#result_price').text(toNumberFormat(price) + " ₩");

    let sellCount = toPureNumber($('#result_count').text());

    if (sellCount > toPureNumber(modal_info.cryptoCount || sellCount <= 0)) {
        $('#sellWarning').css('display', 'inline');
        $("#sellButton").attr("disabled", true);
    } else {
        $('#sellWarning').css('display', 'none');
        $("#sellButton").attr("disabled", false);
    }
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

function submitCheck() {
    $.ajax({
        type: "POST",
        url: "/user/sell",
        data: convertModalInfoToDto(),
        success: function (data) {
            modal.hide();
            location.reload();
        },
        error: function () {
            alert("ERROR");
        }
    });
}

function convertModalInfoToDto() {
    return {
        cryptoId: modal_info.cryptoID,
        cryptoMarket: modal_info.cryptoMarket,
        pricePerOne: toPureNumber(modal_info.pricePerOne),
        sellCount: toPureNumber($('#result_count').text()),
        sellPrice: toPureNumber($('#result_count').text()) * toPureNumber(modal_info.nowPrice),
        profitMoney: getProfit(),
        rateOfReturn: toPureNumber(modal_info.rateOfReturn)
    }
}

function getProfit() {
    let sellCount = toPureNumber($('#result_count').text());
    let nowPrice = toPureNumber(modal_info.nowPrice) * sellCount;
    let buyPrice = toPureNumber(modal_info.pricePerOne) * sellCount;
    return buyPrice - nowPrice;
}

function resetLog() {
    if (confirm("모든 통계를 삭제하시겠습니까?")) {
        $.ajax({
            type: "POST",
            url: "/user/resetLog",
            success: function (data) {
                alert(data);
                location.reload();
            },
            error: function () {
                alert("ERROR");
            }
        });
    }
}
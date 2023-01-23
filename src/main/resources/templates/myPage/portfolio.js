// TODO: 매도금액 또는 매도갯수의 따라 반응형 데이터 변경 (result_count, result_price) [v]
// TODO: 보유한 최대갯수, 보유한 최대금액 이상 또는 0 매도 안되게 막기

// TODO: amqp (RabbitMQ) 적용 알아보기
// subscribe로 서버 부하 줄여야댐

// TODO: interval 생성해서 실시간 정보조회

setTimeout(function () {
    refreshList();
}, 1000);

function refreshList() {
    var interval = setInterval(function () {
        $.ajax({
            type: "POST",
            url: "/myPage/refresh",
            success: function (data) {
                replaceList(data);
            },
            error: function () {
                console.log("ERROR");
                setTimeout(function () {
                    refreshList();
                }, 1000);
            }
        });
    }, 800);
}


// 보유코인의 실시간으로 
// 코인별 [현재가,수익률]
// 전체의 [현재가,수익률] 계산되어야함
function replaceList(data) {
    let bodyChildren = $('#tableBody').children();

    $('#total_percent').text('내 현재 수익률 : ' + data[1] + '%');
    $('#total_money').text('내 현재 수익금 : ' + data[2] + '₩');

    for (let i = 0; i < bodyChildren.length; i++) {
        let tr = bodyChildren.get(i);
        let tds = tr.children;
        let info = data[0][i];

        if (tds.item(0).innerHTML == info.fullNameKO) {
            // 현재가
            tds.item(4).innerHTML = info.nowPrice.toLocaleString() + ' ₩';
            // 수익률
            tds.item(5).innerHTML = info.rateOfReturn.toLocaleString() + ' %';
        }
    }
}



var modal = new bootstrap.Modal(document.getElementById('modal_sell'), {
    backdrop: true,
    keyboard: true,
    focus: true
});

var sell_info = {
    ownCount: 0,
    ownPrice: 0,
    maxCount: 0,
    maxPrice: 0
}

// 매도버튼 클릭
document.getElementsByName("showSellButton").forEach(button => {
    button.addEventListener('click', function (e) {
        // console.log(e.target.parentElement.parentElement);
        // console.log(e.target.parentElement.parentElement.children);

        let coinName = e.target.parentElement.parentElement.children[0].innerText;

        let ownCount = e.target.parentElement.parentElement.children[1].innerText;
        let nowPrice = e.target.parentElement.parentElement.children[4].innerText;

        let avgBuyPrice = e.target.parentElement.parentElement.children[2].innerText;
        let avgPricePerOne = e.target.parentElement.parentElement.children[3].innerText;
        
        // info 
        console.log("보유 개수 : " + toPureNumber(ownCount));
        console.log("평균 매입가 : " + toPureNumber(avgBuyPrice));
        console.log("평단가 : " + toPureNumber(avgPricePerOne));
        console.log("현재가 : " + toPureNumber(nowPrice));

        showModal(coinName);
    });
});

function toPureNumber(str) {
    return Number(str.replace(/[^0-9.]/g, ""))
}

function showModal(fullName) {
    $('#modalLabel').text(fullName + " 매도");

    // result 설정

    modal.show();
}



// dynamic event
document.getElementById('input_count').addEventListener('focus', function (e) {
    modalEvent();
});
document.getElementById('input_count').addEventListener('change', function (e) {
    modalEvent();
});
document.getElementById('input_count').addEventListener('keyup', function (e) {
    modalEvent();
});

document.getElementById('input_price').addEventListener('focus', function (e) {
    modalEvent();
});
document.getElementById('input_price').addEventListener('change', function (e) {
    modalEvent();
});
document.getElementById('input_price').addEventListener('keyup', function (e) {
    modalEvent();
});

function modalEvent() {

}
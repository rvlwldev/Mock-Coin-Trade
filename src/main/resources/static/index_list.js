setTimeout(function () {
    refreshList();
}, 1000);

function refreshList() {
    var interval = setInterval(function () {

        $.ajax({
            type: "POST",
            url: "/refresh",
            success: function (data) {
                // emptyAndRedraw(data);
                replaceDraw(data);
                // clearInterval(interval);
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

function replaceDraw(data) {
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

/* 지우고 다시 append 하는 방식, 함수 안타서 안씀
        function emptyAndRedraw(data) {
            $('#tableBody').empty();

            data.forEach(info => {
                $('#tableBody').append('<tr>');
                $('#tableBody').append('<td style="display: none;">' + info + '</td>');
                $('#tableBody').append('<td class="align-middle" style="text-align: center;">' + info.fullNameKO + '</td>');
                $('#tableBody').append('<td class="align-middle" style="text-align: right;">' + info.tradePrice.toLocaleString() + ' ₩' + '</td>');
                $('#tableBody').append('<td class="align-middle" style="text-align: right;"> ' + info.signedChangeRate.toLocaleString() + ' %' + '</td>');
                $('#tableBody').append('<td class="align-middle" style="text-align: right;">' + Math.round(info.accTradePrice24h).toLocaleString() + '백만 원</td>')

                let button = '<button id="but" class="btn btn-info btn-sm btn-block" style="margin: 0px;" data-toggle="modal" data - target="#modal_buy" >'
                    + '매입'
                    + '</button>'

                $('#tableBody').append('<td align="center">' + button + '</td>')
                $('#tableBody').append('</tr>')
            });
        }
        */
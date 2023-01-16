// modal option
var modal = new bootstrap.Modal(document.getElementById('modal_buy'), {
    backdrop: true,
    keyboard: true,
    focus: true
});

// modal Click event grant
document.getElementsByName("showModalButton").forEach(button => {
    button.addEventListener('click', function (e) {
        let fullName = e.path[2].children[0].innerHTML;
        let coinId = e.path[2].children[4].innerHTML;
        let coinMarket = e.path[2].children[5].innerHTML;
        let buyPrice = Number(e.path[2].children[1].innerHTML.replace(/[₩|,]/g, "",));

        console.log("buyPrice : " + buyPrice);

        showModal(fullName, coinId, coinMarket, buyPrice);
    });
});

function showModal(fullName, coinId, coinMarket, buyPrice) {
    $('#modalLabel').text(fullName + " 매입");

    modal_info.coinId = coinId;
    modal_info.coinMarket = coinMarket;
    modal_info.buyPrice = buyPrice;

    console.log(modal_info);

    $('#input_count').val(1);
    $('#input_price').val(Number(modal_info.buyPrice).toString()
        .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개");

    $('#result_count').text(1);
    $('#result_price').text(Number(modal_info.buyPrice).toString()
        .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩");

    modal.show();
    $('#input_count').focus();
}

var modal_info = {
    coinId: "",
    coinMarket: "",
    buyPrice: 0
}

var modal_inputMode = true;
function model_changeInputMode() {
    if (modal_inputMode) {
        document.getElementById("input_count").readOnly = false;
        document.getElementById("input_price").readOnly = true;
    }
    else {
        document.getElementById("input_count").readOnly = true;
        document.getElementById("input_price").readOnly = false;
    }
};

// dynamic event
document.getElementById('input_count').addEventListener('focus', function (e) {
    modal_inputMode = true;
    model_changeInputMode();
    setViewOfModalResult();
});
document.getElementById('input_count').addEventListener('change', function (e) {
    setViewOfModalResult();
});
document.getElementById('input_count').addEventListener('keyup', function (e) {
    setViewOfModalResult();
});

document.getElementById('input_price').addEventListener('focus', function (e) {
    modal_inputMode = false;
    model_changeInputMode();
    setViewOfModalResult();
});
document.getElementById('input_price').addEventListener('change', function (e) {
    setViewOfModalResult();
});
document.getElementById('input_price').addEventListener('keyup', function (e) {
    setViewOfModalResult();
});

function setViewOfModalResult() {
    if (modal_inputMode) {
        let count = $('#input_count').val();

        $('#input_count').val(Number(count));

        $('#result_count').text(Number(count).toString()
            .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개");
        $('#result_price').text(Number(count * modal_info.buyPrice).toString()
            .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩");
    } else {
        let price = $('#input_price').val();

        $('#input_price').text(Number(price));

        $('#result_count').text(Number(price / modal_info.buyPrice).toString()
            .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개");
        $('#result_price').text(Number(price).toString()
            .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩");
    }
}
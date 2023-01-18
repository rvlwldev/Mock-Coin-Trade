// modal option
var modal = new bootstrap.Modal(document.getElementById('modal_buy'), {
    backdrop: true,
    keyboard: true,
    focus: true
});

var modal_info = {
    coinId: "",
    coinMarket: "",
    buyPrice: 0,
    buyCount: 0
}

// modal Click event grant
document.getElementsByName("showModalButton").forEach(button => {
    button.addEventListener('click', function (e) {
        console.log(e.target.parentElement.parentElement.children);

        let fullName = e.target.parentElement.parentElement.children[0].innerHTML;
        let coinId = e.target.parentElement.parentElement.children[4].innerHTML;
        let coinMarket = e.target.parentElement.parentElement.children[5].innerHTML;
        let buyPrice = Number(e.target.parentElement.parentElement.children[1].innerHTML.replace(/[₩|,]/g, "",));

        modal_info.coinId     = coinId;
        modal_info.coinMarket = coinMarket;
        modal_info.buyPrice   = buyPrice;
        modal_info.buyCount   = 1;

        showModal(fullName);
    });
});

function showModal(fullName) {
    $('#modalLabel').text(fullName + " 매입");

    $('#input_count').val(1);
    $('#input_price').val(Number(modal_info.buyPrice).toString()
        .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개");

    $('#result_count').text("1개");
    $('#result_price').text(Number(modal_info.buyPrice).toString()
        .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩");

    modal.show();
    $('#input_count').focus();
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
    // let count = $('#input_count').val();
    // let price = $('#input_price').val();

    // if (modal_inputMode) {
    //     count = $('#input_count').val();

    //     $('#input_count').val(Number(count));

    //     $('#result_count').text(Number(count).toString()
    //         .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개");
    //     modal_info.buyCount = count;

    //     $('#result_price').text(Number(count * modal_info.buyPrice).toString()
    //         .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩");
    //     modal_info.buyPrice = count * modal_info.buyPrice;

    // } else {
    //     price = $('#input_price').val();

    //     $('#input_price').text(Number(price));

    //     $('#result_count').text(Number(price / modal_info.buyPrice).toString()
    //         .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개");
    //     modal_info.buyCount = price / modal_info.buyPrice;

    //     $('#result_price').text(Number(price).toString()
    //         .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩");
    //     modal_info.buyPrice = price;
    // }

    
    if (modal_inputMode) {
        // 갯수입력
        let count = $('#input_count').val();

        $('#result_count').text(
            Number(count).toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개"
        );
        $('#result_price').text(
            Number(modal_info.buyPrice * count).toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩"
        );

    } else {
        // 가격입력
        let price = $('#input_price').val();

        $('#result_count').text(
            Number(price / modal_info.buyPrice).toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "개"
        );
        $('#result_price').text(
            Number(price).toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " ₩"
        );
    }

}


function submitCheck() {
    console.log(modal_info);

    modal_info.buyPrice = $('#result_price').text().replace(" ₩", "");
    modal_info.buyCount = $('#result_count').text().replace("개", "");

    modal_info.buyPrice = modal_info.buyPrice.replace(",", "");
    modal_info.buyCount = modal_info.buyCount.replace(",", "");

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
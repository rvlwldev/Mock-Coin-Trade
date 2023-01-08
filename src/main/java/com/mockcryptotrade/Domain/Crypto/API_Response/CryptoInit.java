package com.mockcryptotrade.Domain.Crypto.API_Response;

import lombok.Data;

@Data
public class CryptoInit {
    private String market;
    private String korean_name;
    private String english_name;
}

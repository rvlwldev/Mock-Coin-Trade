package com.mockcryptotrade.Common.Enum;

public enum API_URL {

    ALL_CRYPTO_INFO_API("https://api.upbit.com/v1/market/all");

    private final String url;

    API_URL(String name) {
        this.url = name;
    }

    @Override
    public String toString() {
        return this.url;
    }
}

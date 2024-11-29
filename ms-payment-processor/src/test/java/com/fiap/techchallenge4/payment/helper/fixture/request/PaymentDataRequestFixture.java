package com.fiap.techchallenge4.payment.helper.fixture.request;

import com.fiap.techchallenge4.payment.controller.request.PaymentDataRequest;

public class PaymentDataRequestFixture {

    public static PaymentDataRequest VALID() {
        return new PaymentDataRequest(
                "5538360554254365",
                "30/09/2026",
                "123"
        );
    }

    public static PaymentDataRequest UNAPPROVED() {
        return new PaymentDataRequest(
                "5538360554254365",
                "30/09/2026",
                "129"
        );
    }

    public static PaymentDataRequest WITHOUT_CARD_NUMBER() {
        return new PaymentDataRequest(
                null,
                "30/09/2026",
                "123"
        );
    }

    public static PaymentDataRequest WITHOUT_EXPIRATION_DATE() {
        return new PaymentDataRequest(
                "5538360554254369",
                null,
                "123"
        );
    }

    public static PaymentDataRequest WITHOUT_CODE() {
        return new PaymentDataRequest(
                "5538360554254369",
                "30/09/2026",
                null
        );
    }
}

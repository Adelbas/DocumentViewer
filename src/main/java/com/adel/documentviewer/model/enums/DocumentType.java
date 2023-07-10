package com.adel.documentviewer.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DocumentType {
    CONSIGNMENT(Constants.CONSIGNMENT),
    PAYMENT(Constants.PAYMENT),
    PAYMENT_REQUEST(Constants.PAYMENT_REQUEST);

    @JsonValue
    @Getter
    private final String name;

    public static class Constants {
        public static final String CONSIGNMENT = "Накладная";
        public static final String PAYMENT = "Платёжка";
        public static final String PAYMENT_REQUEST = "Заявка на оплату";
    }
}

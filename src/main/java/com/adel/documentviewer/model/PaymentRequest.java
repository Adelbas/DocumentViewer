package com.adel.documentviewer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Entity
@Table(name = "payment_requests")
@Data
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaymentRequest extends Document {

    @JsonProperty("Контрактор")
    private String contractor;

    @JsonProperty("Валюта")
    private String currency;

    @JsonProperty("Курс валюты")
    @Column(name = "currency_rate")
    private Double currencyRate;

    @JsonProperty("Комиссия")
    private Double commission;
}

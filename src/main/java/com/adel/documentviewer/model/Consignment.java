package com.adel.documentviewer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Entity
@Table(name = "consignments")
@Data
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Consignment extends Document {

    @JsonProperty("Валюта")
    private String currency;

    @JsonProperty("Курс валюты")
    @Column(name = "currency_rate")
    private Double currencyRate;

    @JsonProperty("Товар")
    private String product;

    @JsonProperty("Количество")
    private Double quantity;
}

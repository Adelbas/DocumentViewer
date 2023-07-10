package com.adel.documentviewer.model;

import com.adel.documentviewer.model.enums.DocumentType;
import com.fasterxml.jackson.annotation.*;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(value = {"id","checkbox"})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "Документ",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Consignment.class, name = DocumentType.Constants.CONSIGNMENT),
        @JsonSubTypes.Type(value = Payment.class, name = DocumentType.Constants.PAYMENT),
        @JsonSubTypes.Type(value = PaymentRequest.class, name = DocumentType.Constants.PAYMENT_REQUEST)})
public abstract class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("Документ")
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @JsonProperty("Номер")
    @Column(unique = true, nullable = false)
    private String number;

    @JsonProperty("Дата")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate date;

    @JsonProperty("Пользователь")
    private String user;

    @JsonProperty("Сумма")
    private Double sum;

    @Transient
    private CheckBox checkbox = new CheckBox();

}

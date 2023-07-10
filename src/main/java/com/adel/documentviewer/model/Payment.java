package com.adel.documentviewer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Entity
@Table(name = "payments")
@Data
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Payment extends Document{

    @JsonProperty("Сотрудник")
    private String employee;
}

package com.adel.documentviewer.controller;

import com.adel.documentviewer.model.Document;
import com.adel.documentviewer.model.Payment;
import com.adel.documentviewer.service.DocumentService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import static com.adel.documentviewer.model.enums.DocumentType.PAYMENT;

@Component
@FxmlView("create-payment.fxml")
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentController extends AbstractController{

    private final DocumentService documentService;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField number;
    @FXML
    private DatePicker date;
    @FXML
    private TextField user;
    @FXML
    private TextField sum;
    @FXML
    private TextField employee;

    @FXML
    public void initialize() {
        preInitialize();
        stage.setTitle(PAYMENT.getName());
        stage.setScene(new Scene(borderPane));
    }

    @FXML
    private void create(){
        Document payment = Payment.builder()
                .type(PAYMENT)
                .number(number.getText())
                .date(date.getValue())
                .user(user.getText())
                .sum(Double.parseDouble(sum.getText()))
                .employee(employee.getText())
                .build();
        documentService.saveDocument(payment);

        log.info("Created document {}",payment);

        stage.close();
    }
}


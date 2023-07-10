package com.adel.documentviewer.controller;

import com.adel.documentviewer.model.Document;
import com.adel.documentviewer.model.PaymentRequest;
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
import static com.adel.documentviewer.model.enums.DocumentType.PAYMENT_REQUEST;

@Component
@FxmlView("create-payment-requests.fxml")
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentRequestsController extends AbstractController {

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
    private TextField contractor;
    @FXML
    private TextField currency;
    @FXML
    private TextField currency_rate;
    @FXML
    private TextField commission;

    @FXML
    public void initialize() {
        preInitialize();
        stage.setTitle(PAYMENT_REQUEST.getName());
        stage.setScene(new Scene(borderPane));
    }

    @FXML
    private void create(){
        Document paymentRequest = PaymentRequest.builder()
                .type(PAYMENT_REQUEST)
                .number(number.getText())
                .date(date.getValue())
                .user(user.getText())
                .sum(Double.parseDouble(sum.getText()))
                .contractor(contractor.getText())
                .currency(currency.getText())
                .currencyRate(Double.parseDouble(currency_rate.getText()))
                .commission(Double.parseDouble(commission.getText()))
                .build();
        documentService.saveDocument(paymentRequest);

        log.info("Created document {}",paymentRequest);

        stage.close();
    }
}


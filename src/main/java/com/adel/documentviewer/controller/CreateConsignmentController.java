package com.adel.documentviewer.controller;

import com.adel.documentviewer.model.Consignment;
import com.adel.documentviewer.model.Document;
import com.adel.documentviewer.service.DocumentService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import static com.adel.documentviewer.model.enums.DocumentType.CONSIGNMENT;

@Component
@FxmlView("create-consignment.fxml")
@RequiredArgsConstructor
@Slf4j
public class CreateConsignmentController extends AbstractController{

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
    private TextField currency;
    @FXML
    private TextField currency_rate;
    @FXML
    private TextField product;
    @FXML
    private TextField quantity;

    @FXML
    public void initialize() {
        preInitialize();
        stage.setTitle(CONSIGNMENT.getName());
        stage.setScene(new Scene(borderPane));
    }
    public Stage show() {
        stage.show();
        return stage;
    }

    @FXML
    private void create(){
        Document consignment = Consignment.builder()
                .type(CONSIGNMENT)
                .number(number.getText())
                .date(date.getValue())
                .user(user.getText())
                .sum(Double.parseDouble(sum.getText()))
                .currency(currency.getText())
                .currencyRate(Double.parseDouble(currency_rate.getText()))
                .product(product.getText())
                .quantity(Double.parseDouble(quantity.getText()))
                .build();
        documentService.saveDocument(consignment);

        log.info("Created document {}",consignment);

        stage.close();
    }
}

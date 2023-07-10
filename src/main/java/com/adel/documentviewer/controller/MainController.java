package com.adel.documentviewer.controller;

import com.adel.documentviewer.model.Document;
import com.adel.documentviewer.service.DocumentService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FxmlView("main.fxml")
@RequiredArgsConstructor
public class MainController implements Initializable {

    private final FxWeaver fxWeaver;
    private final DocumentService documentService;

    @FXML
    public TableView<Document> documentTableView;
    @FXML
    public TableColumn<Document, String> name;
    @FXML
    public TableColumn<Document, LocalDate> date;
    @FXML
    public TableColumn<Document, String> number;
    @FXML
    public TableColumn<Document, CheckBox> checkbox;

    @FXML
    private void refreshTable() {
        documentTableView.getItems().clear();
        documentTableView.setItems(FXCollections.observableList(documentService.getAllDocuments()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        documentTableView.setEditable(false);
        documentTableView.setSelectionModel(null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        name.setCellValueFactory(doc->new SimpleStringProperty(doc.getValue().getType().getName()));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        checkbox.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        refreshTable();
    }

    @FXML
    private void createConsignment() {
        fxWeaver.loadController(CreateConsignmentController.class).show()
                .setOnHiding(event->{refreshTable();});
    }

    @FXML
    private void createPayment() {
        fxWeaver.loadController(CreatePaymentController.class).show()
                .setOnHiding(event->{refreshTable();});
    }

    @FXML
    private void createPaymentRequest() {
        fxWeaver.loadController(CreatePaymentRequestsController.class).show()
                .setOnHiding(event->{refreshTable();});
    }

    @FXML
    private void viewDocument() {
        Document document =documentTableView.getItems().stream()
                .filter(d->d.getCheckbox().isSelected()).findFirst().get();
        System.out.println(document);
        fxWeaver.loadController(ViewDocumentController.class)
                .show(document)
                .setOnHiding(event->{refreshTable();});
    }

    @FXML
    private void deleteButtonClicked() {
        for (Document d : getSelected())
            documentService.deleteDocument(d.getId());

        refreshTable();
    }

    @FXML
    private void saveFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовые документы (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Все файлы", "*"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file!=null) {
            List<Document> selected = getSelected();
            documentService.saveDocumentsToFile(selected, file);
        }

        refreshTable();
    }

    @FXML
    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открытие");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовые документы (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Все файлы", "*"));

        File file = fileChooser.showOpenDialog(new Stage());

        if (file!=null) {
            documentService.loadDocumentsFromFile(file);
        }

        refreshTable();
    }


    private List<Document> getSelected(){
        return documentTableView.getItems().filtered(d->d.getCheckbox().isSelected());
    }
}

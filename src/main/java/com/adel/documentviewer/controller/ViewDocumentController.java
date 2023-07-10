package com.adel.documentviewer.controller;

import com.adel.documentviewer.model.Document;
import com.adel.documentviewer.service.DocumentService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@FxmlView("view-document.fxml")
@RequiredArgsConstructor
public class ViewDocumentController extends AbstractController{
    private final DocumentService documentService;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextFlow textFlow;

    @FXML
    public void initialize() {
        preInitialize();
        stage.setScene(new Scene(pane));
    }

    public Stage show(Document document) {
        stage.show();
        stage.setTitle(document.getType().getName());

        Map<String, Object> fields = documentService.parseDocumentToMap(document);
        fields.forEach((k,v)-> {
            Text text = new Text(k+": "+v+"\n");
            text.setFont(Font.font("System",15));
            textFlow.getChildren().add(text);
        });

        return stage;
    }

    @FXML
    public void close(){
        stage.close();
    }

}

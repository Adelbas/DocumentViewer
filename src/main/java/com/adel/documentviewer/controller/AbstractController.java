package com.adel.documentviewer.controller;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractController {
    protected Stage stage;
    @Value("${fx.scene.min-height}")
    private Double height;
    @Value("${fx.scene.min-width}")
    private Double width;

    public void preInitialize() {
        this.stage = new Stage();
        stage.setMinHeight(height);
        stage.setHeight(height);
        stage.setMinWidth(width);
        stage.setWidth(width);
        stage.initModality(Modality.APPLICATION_MODAL);
    }
    public Stage show() {
        stage.show();
        return stage;
    }
}

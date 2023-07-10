package com.adel.documentviewer.event;

import com.adel.documentviewer.controller.MainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageReadyEventListener implements ApplicationListener<StageReadyEvent> {

    private final ApplicationContext applicationContext;
    private final String applicationTitle;

    private final Double sceneHeight;
    private final Double sceneWidth;

    public StageReadyEventListener(ApplicationContext applicationContext,
                                   @Value("${app.title}") String applicationTitle,
                                   @Value("${fx.scene.min-height}") Double height,
                                   @Value("${fx.scene.min-width}") Double width) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
        this.sceneHeight=height;
        this.sceneWidth=width;
    }


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        stage.setMinHeight(sceneHeight);
        stage.setHeight(sceneHeight);
        stage.setMinWidth(sceneWidth);
        stage.setWidth(sceneWidth);
        stage.setScene(scene);
        stage.setTitle(this.applicationTitle);
        stage.show();
    }
}

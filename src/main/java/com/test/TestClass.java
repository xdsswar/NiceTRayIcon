package com.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import xss.it.Assets;
import xss.it.tray.NiceTrayIcon;

/**
 * @author XDSSWAR
 * Created on 05/17/2023
 */
public class TestClass extends Application {
    private NiceTrayIcon trayIcon;

    @Override
    public void start(Stage stage){
        trayIcon=new NiceTrayIcon(new Image(Assets.load("/icon-16x16.png").toExternalForm()));
        ContextMenu menu=new ContextMenu();
        MenuItem item=new MenuItem("Open Window");
        MenuItem item1=new MenuItem("Exit");
        menu.getItems().addAll(item,item1);
        trayIcon.setContextMenu(menu);

        stage.setScene(new Scene(new AnchorPane(),1000,500));

        item.setOnAction(event -> {
            stage.show();
        });

        trayIcon.setOnAction(event -> {
            if (event.isDoubleClick()){
               Platform.runLater(stage::show);
            }
        });

        item1.setOnAction(event -> Platform.exit());

        trayIcon.install();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop(){
        trayIcon.uninstall();
    }
}

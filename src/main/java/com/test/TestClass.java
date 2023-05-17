package com.test;

import javafx.application.Application;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
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
        MenuItem item=new MenuItem("Exit");
        menu.getItems().add(item);

        trayIcon.setContextMenu(menu);
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

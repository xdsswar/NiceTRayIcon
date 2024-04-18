/*
 * Copyright © 2024. XTREME SOFTWARE SOLUTIONS
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software or any portion of it is strictly prohibited and may
 * result in severe civil and criminal penalties. This code is the sole
 * proprietary of XTREME SOFTWARE SOLUTIONS.
 *
 * Commercialization, redistribution, and use without explicit permission
 * from XTREME SOFTWARE SOLUTIONS, are expressly forbidden.
 */

package xss.it.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xss.it.tray.NiceTrayIcon;

/**
 * @author XDSSWAR
 * Created on 04/18/2024
 */
public class Demo extends Application {

    private static NiceTrayIcon icon;

    /**
     * The entry point of the Java application.
     * This method calls the launch method to start a JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called after the application has been launched.
     * Override this method to create and set up the primary stage of the application.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new VBox(new Label("NiceTrayIcon")), 400, 200));
        Image image = new Image(Assets.load("/icon.png").toExternalForm());
        icon = new NiceTrayIcon(image);
        final String styles = Assets.load("/style.css").toExternalForm();
        icon.getStyleSheets().add(styles);
        icon.install();
        icon.setContextMenu(build());

        /*
         * NOTE : This will prevent the Javafx Platform from exiting when closing the Stages
         */
        Platform.setImplicitExit(false);
        stage.show();
    }


    /**
     * Create test Context Menu
     * @return ContextMenu
     */
    ContextMenu build(){
        ContextMenu c = new ContextMenu();
        Image image = new Image(Assets.load("/icon.png").toExternalForm());
        for (int i = 0; i < 5; i++) {
            MenuItem mi = new MenuItem("  Action "+ i);
            mi.setOnAction(event -> System.out.println("Clicked "+ mi.getText()));
            mi.setGraphic(new ImageView(image));
            c.getItems().add(mi);
        }

        MenuItem mi = new MenuItem("  Exit");
        mi.setGraphic(new ImageView(image));
        mi.setOnAction(event -> {
            System.out.println("Exiting ...");
            icon.uninstall();
            Platform.exit();
        });

        c.getItems().add(mi);
        return c;
    }


    /**
     * The initialization method for the application.
     * This method is called immediately after the application class is loaded and
     * constructed. An application can override this method to perform initialization
     * tasks before the application is shown.
     *
     * @throws Exception if an error occurs during initialization.
     */
    @Override
    public void init() throws Exception {
        super.init();
    }

    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     *
     * @throws Exception if an error occurs during stopping the application.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if (icon != null){
            //Un-comment if you want to uninstall the icon
            // icon.uninstall();
        }
    }
}

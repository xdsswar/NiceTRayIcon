package com.jfx;

import com.jfx.ui.notify.NiceTrayIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Objects;

/**
 * @author XDSSWAR
 */
public class Run extends Application {

    private static NiceTrayIcon icon;//NiceTrayIcon

    /**
     * Create few JavaFx Images to use with the MenuItems Later
     */
    private static final javafx.scene.image.Image about=new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/about.png")).toExternalForm());

    private static final javafx.scene.image.Image help=new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/help.png")).toExternalForm());

    private static final javafx.scene.image.Image link=new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/link.png")).toExternalForm());

    private static final javafx.scene.image.Image skip=new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/skip.png")).toExternalForm());

    private static final javafx.scene.image.Image settings=new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/settings.png")).toExternalForm());

    private static final javafx.scene.image.Image exit=new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/exit.png")).toExternalForm());

    @Override
    public void start(Stage stage) throws AWTException {
        //Check if System Tray is Supported
        if (SystemTray.isSupported()) {
            //Create JavaFX ContextMenu
            ContextMenu m = new ContextMenu();

            //Create Few JavaFX MenuItems
            MenuItem m1 = new MenuItem("About");
            m1.setOnAction(event -> {
                System.out.println("About");
            });

            MenuItem m2 = new MenuItem("Help");
            m2.setOnAction(event -> {
                System.out.println("Help");
            });

            MenuItem m3 = new MenuItem("Open Link");
            m3.setOnAction(event -> {
                System.out.println("Open Link");
            });

            MenuItem m4 = new MenuItem("Skip Next");
            m4.setOnAction(event -> {
                System.out.println("Skip Next");
            });

            MenuItem m5 = new MenuItem("Settings");
            m5.setOnAction(event -> {
                System.out.println("Settings");
            });

            MenuItem m6 = new MenuItem("Exit");
            m6.setOnAction(event -> {
                System.out.println("Exit");
                Platform.exit();
            });

            //Set the JavaFX MenuItems Graphics using the JavaFx Images we created before
            m1.setGraphic(new ImageView(about));
            m2.setGraphic(new ImageView(help));
            m3.setGraphic(new ImageView(link));
            m4.setGraphic(new ImageView(skip));
            m5.setGraphic(new ImageView(settings));
            m6.setGraphic(new ImageView(exit));

            //Add 2 separators
            SeparatorMenuItem sp=new SeparatorMenuItem();
            SeparatorMenuItem sp1=new SeparatorMenuItem();
            //Add the JavaFX MenuItems/Separators to the ContextMenu
            m.getItems().addAll(m1, m2,sp, m3, m4, m5 ,sp1, m6);
            //Create the AWT image we gonna use for the Tray Icon
            Image ic = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/ic.png"));
            //Initialize the NiceTrayIcon we declared before
            icon = new NiceTrayIcon(ic, m);
            //Set the NiceTrayIcon to the System Tray
            SystemTray.getSystemTray().add(icon);
        }

    }

    //Remove the TrayIcon before Exiting
    public void stop(){
        if (SystemTray.isSupported()){
            SystemTray.getSystemTray().remove(icon);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

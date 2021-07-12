package com.jfx;

import com.jfx.ui.notify.NiceTryIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.*;

/**
 * @author XDSSWAR
 */
public class Run extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static NiceTryIcon icon;
    @Override
    public void start(Stage stage) throws AWTException {
        if (SystemTray.isSupported()) {
            ContextMenu m = new ContextMenu();
            MenuItem m1 = new MenuItem("Option 1 right here");
            m1.setOnAction(event -> {
                System.out.println("This is Option 1");
            });

            MenuItem m2 = new MenuItem("Option 2 right here");
            m2.setOnAction(event -> {
                System.out.println("This is Option 4");
            });

            MenuItem m3 = new MenuItem("Option 3 right here");
            m3.setOnAction(event -> {
                System.out.println("This is Option 3");
            });

            MenuItem m4 = new MenuItem("Option 4 right here");
            m4.setOnAction(event -> {
                System.out.println("This is Option 4");
            });

            MenuItem m5 = new MenuItem("Option 5 right here");
            m5.setOnAction(event -> {
                System.out.println("This is Option 5");
            });

            MenuItem m6 = new MenuItem("Option 6 right here");
            m6.setOnAction(event -> {
                System.out.println("This is Option 6");
                Platform.exit();
            });

            m.getItems().addAll(m1, m2, m3, m4, m5, m6);
            Image ic = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/ic.png"));
            icon = new NiceTryIcon(ic, m);

            var tray = SystemTray.getSystemTray();

            tray.add(icon);
        }

    }

    public void stop(){
        if (SystemTray.isSupported()){
            SystemTray.getSystemTray().remove(icon);
        }
    }
}

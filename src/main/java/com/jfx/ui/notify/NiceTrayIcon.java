package com.jfx.ui.notify;

import com.jfx.util.PlatformUtil;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * @author XDSSWAR
 */
public class NiceTrayIcon extends TrayIcon {
    /**
     * Variables/Constants
     */
    private final Stage iconStage = new Stage();
    private final Button button = new Button();
    private final ContextMenu contextMenu;
    private static final String STYLE_SHEET= Objects.requireNonNull(NiceTrayIcon.class.getResource("/tray-style.css")).toExternalForm();
    private static final String CONTEXT_MENU_STYLE_CLASS="tray-context";
    private static final String MENU_ITEMS_STYLE_CLASS="tray-context-menu-item";
    private static final String FIRST_STYLE_CLASS="tray-context-menu-item-first";
    private static final String LAST_STYLE_CLASS="tray-context-menu-item-last";

    /**
     * NiceTrayIcon Constructor
     * @param image AWT Image. Not a JavaFX Image
     * @param contextMenu JavaFX ContextMenu
     */
    public NiceTrayIcon(Image image, ContextMenu contextMenu) {
        super(image);
        this.contextMenu = contextMenu;

        setImageAutoSize(true);
        init();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON3){
                    Platform.runLater(() -> {
                        iconStage.toFront();
                        CalcPosition.Point calc;

                        if (PlatformUtil.isMac()) {
                            calc = new CalcPosition(e, contextMenu, CalcPosition.Pos.TOP_LEFT).calc();
                        } else {
                            calc = new CalcPosition(e, contextMenu).calc();
                        }

                        contextMenu.show(button, calc.getX(), calc.getY());
                    });
                }
            }
        });
    }

    /**
     * Initialize
     */
    private void init(){
        Scene scene = new Scene(button, 0, 0, Color.TRANSPARENT);

        iconStage.initStyle(StageStyle.UTILITY);
        iconStage.setScene(scene);
        iconStage.setIconified(false);
        iconStage.setMaxWidth(0);
        iconStage.setMaxHeight(0);
        iconStage.setResizable(false);
        iconStage.setX(Double.MIN_VALUE);
        iconStage.setY(Double.MAX_VALUE);
        iconStage.setAlwaysOnTop(true);

        scene.getStylesheets().add(STYLE_SHEET);

        button.setContextMenu(contextMenu);
        contextMenu.setAutoFix(false);

        contextMenu.getStyleClass().add(CONTEXT_MENU_STYLE_CLASS);
        //Add StyleClass to all MenuItems in the ContextMenu
        contextMenu.getItems().forEach(menuItem -> {
            menuItem.getStyleClass().add(MENU_ITEMS_STYLE_CLASS);
        });
        //Check the First and Last Menuitem to add an extra StyleClass
        if (contextMenu.getItems().size()>1){
            contextMenu.getItems().get(0).getStyleClass().add(FIRST_STYLE_CLASS);
            contextMenu.getItems().get(contextMenu.getItems().size()-1).getStyleClass().add(LAST_STYLE_CLASS);
        }

        iconStage.show();

        // The first click may not display, the reason is unknown
        // In order to ensure that it can be displayed for the first time, pop up once outside the screen.
        contextMenu.show(button, Double.MIN_VALUE, Double.MAX_VALUE);

    }

    /*
     * Load Fonts custom Font
     */
    static {
        try {
            Font.loadFont(Objects.requireNonNull(NiceTrayIcon.class.getResource("/fonts/Roboto-Medium.ttf")).openStream(),10.0D);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

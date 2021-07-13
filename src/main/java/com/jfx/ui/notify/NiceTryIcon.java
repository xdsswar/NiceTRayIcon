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
public class NiceTryIcon extends TrayIcon {
    /**
     * Variables/Constants
     */
    private static Stage iconStage;
    private Button button;
    private final ContextMenu contextMenu;
    private static final String STYLE_SHEET= Objects.requireNonNull(NiceTryIcon.class.getResource("/tray-style.css")).toExternalForm();
    private static final String CONTEXT_MENU_STYLE_CLASS="tray-context";
    private static final String MENU_ITEMS_STYLE_CLASS="tray-context-menu-item";
    private static final String FIRST_STYLE_CLASS="tray-context-menu-item-first";
    private static final String LAST_STYLE_CLASS="tray-context-menu-item-last";

    /**
     * NiceTrayIcon Constructor
     * @param image AWT Image. Not a JavaFX Image
     * @param contextMenu JavaFX ContextMenu
     */
    public NiceTryIcon(Image image, ContextMenu contextMenu) {
        super(image);
        this.contextMenu =contextMenu;
        init();
    }

    /**
     * Initialize
     */
    private void init(){
        setImageAutoSize(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON3){
                    Platform.runLater(()->{
                        iconStage=new Stage();
                        iconStage.initStyle(StageStyle.UTILITY);
                        iconStage.setMaxHeight(0);
                        iconStage.setMaxWidth(0);
                        iconStage.setX(Double.MAX_VALUE);
                        button =new Button();
                        button.setContextMenu(contextMenu);
                        Scene scene=new Scene(button);
                        scene.getStylesheets().add(STYLE_SHEET);
                        scene.setFill(Color.TRANSPARENT);
                        iconStage.setScene(scene);
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
                        contextMenu.show(iconStage);
                        contextMenu.setAutoHide(true);
                        contextMenu.setX(e.getX());
                        if (PlatformUtil.isMac()){
                            contextMenu.setY(e.getY());
                        }else {
                            contextMenu.setY(e.getY() - contextMenu.getHeight());
                        }
                    });
                }
            }
        });

    }

    /*
     * Load Fonts custom Font
     */
    static {
        try {
            Font.loadFont(Objects.requireNonNull(NiceTryIcon.class.getResource("/fonts/Roboto-Medium.ttf")).openStream(),10.0D);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

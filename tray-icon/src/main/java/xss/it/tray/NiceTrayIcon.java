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

package xss.it.tray;

import com.sun.it.tray.IconWindow;
import com.sun.it.tray.ImageUtils;
import com.sun.it.tray.NiceIcon;
import com.sun.it.tray.TaskBar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XDSSWAR
 * Created on 04/18/2024
 */
public final class NiceTrayIcon {
    /**
     * Represents the NiceIcon associated with the tray icon.
     */
    private final NiceIcon icon;

    /**
     * Represents the static instance of IconWindow.
     */
    private static IconWindow iconWindow;

    /**
     * Represents the list of style sheets associated with the window.
     */
    private final List<String> styleSheets;

    /**
     * Constructs a new NiceTrayIcon object with the specified image.
     *
     * @param image The image to be used for the tray icon.
     */
    public NiceTrayIcon(Image image) {
        icon = new NiceIcon(ImageUtils.toAwt(image, null));
        styleSheets = new ArrayList<>();
        initialize();
    }

    /**
     * Initializes the NiceTrayIcon.
     */
    private void initialize(){
        icon.setOnAction(event -> {
            if (event.isRightClick()){
                Platform.runLater(()->{
                    if (iconWindow != null){
                        if (iconWindow.isShowing()){
                            iconWindow.close();
                            iconWindow = null;
                        }
                    }

                    iconWindow = new IconWindow();
                    if (getContextMenu()!= null){
                        iconWindow.getScene().getStylesheets().addAll(styleSheets);
                        iconWindow.show();
                        double x = event.getX();
                        double y = event.getY();
                        getContextMenu().setOnShown(e->{
                            TaskBar.Position pos = TaskBar.getPosition();
                            switch (pos){
                                case TOP -> {
                                    //TODO: Implement Top
                                }
                                case LEFT -> {
                                    //TODO: Implement Left
                                }
                                case RIGHT -> {
                                    //TODO: Implement Right
                                }
                                case BOTTOM -> getContextMenu().setY(y - getContextMenu().getHeight() + 10);
                            }

                            fadeIn(getContextMenu(), 300);
                        });
                        getContextMenu().setOpacity(0);
                        getContextMenu().show(iconWindow, x, y);
                    }
                });
            }
        });
    }


    /**
     * Represents the property for the context menu.
     */
    private ObjectProperty<ContextMenu> contextMenu;

    /**
     * Retrieves the ObjectProperty for the context menu.
     *
     * @return The ObjectProperty for the context menu.
     */
    public ObjectProperty<ContextMenu> contextMenuProperty() {
        if (contextMenu == null) {
            contextMenu = new SimpleObjectProperty<>(this, "contextMenu", null);
        }
        return contextMenu;
    }

    /**
     * Retrieves the context menu.
     *
     * @return The context menu.
     */
    public ContextMenu getContextMenu() {
        return contextMenuProperty().get();
    }

    /**
     * Sets the context menu.
     *
     * @param contextMenu The context menu to set.
     */
    public void setContextMenu(ContextMenu contextMenu) {
        contextMenuProperty().set(contextMenu);
    }


    /**
     * Installs the tray icon.
     */
    public void install() {
        icon.install();
    }

    /**
     * Uninstalls the tray icon.
     */
    public void uninstall() {
        icon.uninstall();
        if (iconWindow != null){
            iconWindow.internalClose();
        }
    }

    /**
     * Retrieves the list of style sheets associated with the window.
     *
     * @return The list of style sheets.
     */
    public List<String> getStyleSheets() {
        return styleSheets;
    }


    /**
     * Fades in a ContextMenu over a specified duration in milliseconds.
     *
     * @param contextMenu the ContextMenu to fade in
     * @param millis      the duration of the fade in milliseconds
     */
    public static void fadeIn(ContextMenu contextMenu, int millis) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(contextMenu.opacityProperty(), 0)),
                new KeyFrame(new Duration(millis), new KeyValue(contextMenu.opacityProperty(), 1))
        );
        timeline.play();
    }
}

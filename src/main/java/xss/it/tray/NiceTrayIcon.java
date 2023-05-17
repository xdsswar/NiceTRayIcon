package xss.it.tray;

import xss.it.Assets;
import xss.it.tray.internal.NiceIcon;
import xss.it.util.PortableImageUtils;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

/**
 * @author XDSSWAR
 * Created on 05/16/2023
 */
public class NiceTrayIcon {
    private final String STYLE_SHEET= Assets.load("/tray-style.css").toExternalForm();
    private final NiceIcon niceIcon;
    private ContextMenu contextMenu=null;
    private Stage hiddenStage=null;
    private Scene scene =null;

    /**
     * Constructs a NiceTrayIcon with the specified image.
     *
     * @param image The image to be displayed as the tray icon.
     */
    public NiceTrayIcon(@NotNull Image image) {
        this.niceIcon=new NiceIcon(PortableImageUtils.convertToAwtImage(image,null));
        initialize();
    }

    /**
     * Constructs a NiceTrayIcon with the specified image and context menu.
     *
     * @param image       The image to be displayed as the tray icon.
     * @param contextMenu The context menu associated with the tray icon.
     */
    public NiceTrayIcon(@NotNull Image image, ContextMenu contextMenu){
        this.niceIcon=new NiceIcon(PortableImageUtils.convertToAwtImage(image,null));
        setContextMenu(contextMenu);
        initialize();
    }


    /**
     * Initializes the tray icon by setting up the hidden window and configuring the action event handler.
     */
    private void initialize(){
        initHiddenWindow();
        this.niceIcon.setOnAction(event -> {
            if (this.contextMenu!=null){
                Platform.runLater(() -> {
                    if (this.contextMenu.isShowing() && (!event.isRightClick() || event.isDoubleClick())){
                        this.contextMenu.hide();
                    }

                    if (event.isRightClick() && !event.isDoubleClick()) {
                        //Show Context
                        if (!contextMenu.isShowing()) {
                            this.hiddenStage.requestFocus();
                            this.contextMenu.setX(event.getX());
                            this.contextMenu.setY(event.getX());
                            this.hiddenStage.setX(event.getX());
                            this.hiddenStage.setY(event.getX());
                            this.contextMenu.setAutoHide(true);
                            this.contextMenu.show(this.hiddenStage);
                        }
                    }
                });
            }
        });
    }


    /**
     * Adds a style sheet to the scene by setting its value.
     *
     * @param styleSheet The style sheet to be added.
     */
    public void addStyleSheet(String styleSheet){
        if (this.scene!=null){
            scene.getStylesheets().add(styleSheet);
        }
    }


    /**
     * Removes a style sheet from the scene by setting its value to null.
     *
     * @param styleSheet The style sheet to be removed.
     */
    public void removeStyleSheet(String styleSheet){
        if (this.scene!=null){
            scene.getStylesheets().remove(styleSheet);
        }
    }


    /**
     * Sets the context menu for the NiceIcon.
     *
     * @param contextMenu The context menu to be set.
     */
    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }

    /**
     * Retrieves the context menu associated with the NiceIcon.
     *
     * @return The context menu associated with the NiceIcon.
     */
    public ContextMenu getContextMenu() {
        return this.contextMenu;
    }


    /**
     * Sets the event handler for the tray icon action event.
     *
     * @param event The event handler to set.
     */
    public void setOnAction(TrayActionEvent event){
        this.niceIcon.setOnAction(event);
    }


    /**
     * Installs the NiceIcon to the system tray.
     * If the system tray is supported, adds the NiceIcon to the system tray.
     * If the system tray is not supported, does nothing.
     */
    public void install(){
        this.niceIcon.install();
    }

    /**
     * Uninstalls the NiceIcon from the system tray.
     * If the system tray is supported, removes the NiceIcon from the system tray.
     * If the system tray is not supported, does nothing.
     */
    public void uninstall(){
        this.niceIcon.uninstall();
    }

    /**
     * Initializes the hidden window used by the NiceIcon.
     * This method creates a hidden Stage and shows it with zero width and height.
     * It is called internally when needed.
     */
    private void initHiddenWindow(){
        if (this.hiddenStage==null) {
            this.hiddenStage = new Stage(StageStyle.UTILITY);
            this.hiddenStage.setWidth(0.0);
            this.hiddenStage.setHeight(0.0);
            this.hiddenStage.setX(0.0);
            this.hiddenStage.setY(0.0);
            this.scene=new Scene(new Region());
            this.scene.getStylesheets().add(STYLE_SHEET);
            this.scene.setFill(Color.TRANSPARENT);
            this.hiddenStage.setScene(this.scene);
            this.hiddenStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue){
                    this.contextMenu.hide();
                }
            });
            this.hiddenStage.show();
        }
    }
}

package com.jfx.ui.notify;

import com.jfx.util.PlatformUtil;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author XDSSWAR
 */
public class NiceTryIcon extends TrayIcon {
    private static Stage iconStage;
    private Button button;
    private final ContextMenu contextMenu;

    /**
     * NiceTrayIcon Constructor
     * @param image AWT Image not, a JavaFX Image
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
                        iconStage.setScene(new Scene(button));
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

}

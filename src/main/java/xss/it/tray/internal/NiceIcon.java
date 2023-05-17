package xss.it.tray.internal;

import xss.it.tray.TrayActionEvent;
import xss.it.tray.TrayEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author XDSSWAR
 * Created on 05/16/2023
 * Custom tray icon class that extends TrayIcon.
 * Provides an additional ContextMenu property.
 */
public class NiceIcon extends TrayIcon {

    /**
     * Constructs a NiceIcon with the specified image.
     * @param image The image to be used as the tray icon.
     */
    public NiceIcon(Image image) {
        super(image);
    }

    /**
     * Sets the event handler for the tray icon action event.
     *
     * @param event The event handler to set.
     */
    public void setOnAction(TrayActionEvent event){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TrayEvent trayEvent =new TrayEvent(
                        e.getButton()==MouseEvent.BUTTON3,
                        e.getButton()==MouseEvent.BUTTON1,
                        e.getClickCount()==2,
                        e.getX(),
                        e.getY());
                event.event(trayEvent);
            }
        });

    }

    /**
     * Installs the NiceIcon to the system tray.
     * If the system tray is supported, adds the NiceIcon to the system tray.
     * If the system tray is not supported, does nothing.
     */
    public void install(){
        SwingUtilities.invokeLater(()->{
            if (SystemTray.isSupported()){
                try {
                    SystemTray.getSystemTray().add(this);
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Uninstalls the NiceIcon from the system tray.
     * If the system tray is supported, removes the NiceIcon from the system tray.
     * If the system tray is not supported, does nothing.
     */
    public void uninstall(){
        SwingUtilities.invokeLater(()->{
            if (SystemTray.isSupported()){
                SystemTray.getSystemTray().remove(this);
            }
        });
    }

}

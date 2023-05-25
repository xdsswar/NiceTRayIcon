package xss.it.util;

import java.awt.*;

/**
 * @author XDSSWAR
 * Created on 05/17/2023
 */
public class TaskBar {


    /**
     * Retrieves the position of the taskbar on the screen.
     *
     * @return The position of the taskbar, or Position. UNKNOWN if the taskbar is not found.
     */
    public static Position getPosition() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
        GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(defaultConfiguration);

        if (insets.top>0){
            return Position.TOP;
        }
        else if (insets.right>0){
            return Position.RIGHT;
        }
        else if (insets.bottom>0){
            return Position.BOTTOM;
        }
        else if (insets.left>0){
            return Position.LEFT;
        }
        else {
            return Position.UNKNOWN;
        }
    }


    /**
     * Enum representing different positions of the taskbar.
     */
    public enum Position{
        TOP, BOTTOM, LEFT, RIGHT, UNKNOWN
    }

}

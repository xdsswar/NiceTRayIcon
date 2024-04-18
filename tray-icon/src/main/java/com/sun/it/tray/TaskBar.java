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

package com.sun.it.tray;

import java.awt.*;

/**
 * @author XDSSWAR
 * Created on 04/18/2024
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

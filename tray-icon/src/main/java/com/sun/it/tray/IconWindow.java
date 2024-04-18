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

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import xss.it.nfx.AbstractNfxUndecoratedWindow;
import xss.it.nfx.HitSpot;

import java.util.List;

/**
 * @author XDSSWAR
 * Created on 04/18/2024
 */
public final class IconWindow extends AbstractNfxUndecoratedWindow {
    /**
     * Initializes the NiceTrayIcon.
     */
    private static final double MAX = 0d;

    /**
     * Constructs a new IconWindow.
     */
    public IconWindow() {
        super(true);;
        AnchorPane pane = new AnchorPane();
        setScene(new Scene(pane, MAX,MAX));
        setResizable(false);
        setUp();
    }

    /**
     * Sets up the IconWindow.
     */
    private void setUp(){
        setOpacity(MAX);
        setMaxHeight(MAX);
        setMaxWidth(MAX);
        setMinHeight(MAX);
        setMinWidth(MAX);
        setX(Integer.MAX_VALUE);
        setY(Integer.MAX_VALUE);
    }

    /**
     * Closes the internal window.
     */
    public void internalClose(){
        if (isShowing()){
            PauseTransition pt = new PauseTransition(Duration.millis(200));
            pt.setOnFinished(event -> {
                if (isShowing()) close();
            });
            pt.play();
        }
    }

    /**
     * Retrieves the hit spots associated with the window.
     *
     * @return An empty list of HitSpot objects.
     */
    @Override
    public List<HitSpot> getHitSpots() {
        return List.of();
    }

    /**
     * Retrieves the height of the title bar.
     *
     * @return The height of the title bar, which is 0.
     */
    @Override
    public double getTitleBarHeight() {
        return 0;
    }
}

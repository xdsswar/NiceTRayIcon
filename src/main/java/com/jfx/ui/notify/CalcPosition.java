package com.jfx.ui.notify;

import javafx.scene.control.ContextMenu;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CalcPosition {
    private final MouseEvent mouseEvent;
    private final Pos pos;
    private final double mouseX;
    private final double mouseY;
    private final double contextWidth;
    private final double contextHeight;
    private final double screenWidth;
    private final double screenHeight;

    public enum Pos {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    public static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    private static class OverstepException extends Exception {}

    public CalcPosition(final MouseEvent mouseEvent, final ContextMenu contextMenu, Pos pos) {
        this.mouseEvent = mouseEvent;
        this.pos = pos;

        double scaleX = contextMenu.getOutputScaleX();
        double scaleY = contextMenu.getOutputScaleY();

        mouseX = mouseEvent.getXOnScreen() / scaleX;
        mouseY = mouseEvent.getYOnScreen() / scaleY;

        contextWidth = contextMenu.getWidth();
        contextHeight = contextMenu.getHeight();

        Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
    }

    public CalcPosition(final MouseEvent mouseEvent, final ContextMenu contextMenu) {
        this(mouseEvent, contextMenu, Pos.BOTTOM_LEFT);
    }

    public Point calc() {
        try {
            return calcSelectMethod(pos);
        } catch (OverstepException ignored) {}

        for (Pos value : Pos.values()) {
            if (pos == value) continue;
            try {
                return calcSelectMethod(value);
            } catch (OverstepException ignored) {}
        }

        return new Point(mouseEvent.getX(), mouseEvent.getY());
    }

    private Point calcSelectMethod(Pos pos) throws OverstepException {
        switch (pos) {
            case TOP_LEFT:
                return calc_TOP_LEFT();
            case TOP_RIGHT:
                return calc_TOP_RIGHT();
            case BOTTOM_LEFT:
                return calc_BOTTOM_LEFT();
            case BOTTOM_RIGHT:
                return calc_BOTTOM_RIGHT();
        }
        throw new IllegalArgumentException("");
    }

    private Point calc_TOP_LEFT() throws OverstepException {
        if (mouseY + contextHeight > screenHeight || mouseX + contextWidth > screenWidth) {
            throw new OverstepException();
        }
        return new Point(mouseX, mouseY);
    }

    private Point calc_TOP_RIGHT() throws OverstepException {
        double fictiveMouseX = mouseX - contextWidth;
        if (fictiveMouseX < 0 || mouseY + contextHeight > screenHeight) {
            throw new OverstepException();
        }
        return new Point(fictiveMouseX, mouseY);
    }

    private Point calc_BOTTOM_LEFT() throws OverstepException {
        double fictiveMouseY = mouseY - contextHeight;
        if (fictiveMouseY < 0 || mouseX + contextWidth > screenWidth) {
            throw new OverstepException();
        }
        return new Point(mouseX, fictiveMouseY);
    }

    private Point calc_BOTTOM_RIGHT() throws OverstepException {
        double fictiveMouseX = mouseX - contextWidth;
        double fictiveMouseY = mouseY - contextHeight;
        if (fictiveMouseX < 0 || fictiveMouseY < 0) {
            throw new OverstepException();
        }
        return new Point(fictiveMouseX, fictiveMouseY);
    }
}

package xss.it.tray;

import java.util.Objects;

/**
 * @author XDSSWAR
 * Created on 05/17/2023
 * The Linker class represents a mouse click event with additional information such as the mouse button clicked,
 * whether it was a double click event, and the coordinates of the mouse click.
 */
public class TrayEvent {
    private final boolean rightClick;
    private final boolean leftClick;
    private final boolean doubleClick;
    private final int x;
    private final int y;

    /**
     * Creates a new instance of the Linker class with the specified parameters.
     *
     * @param rightClick  Indicates if the right mouse button was clicked.
     * @param leftClick   Indicates if the left mouse button was clicked.
     * @param doubleClick Indicates if a double click event occurred.
     * @param x           The x-coordinate of the mouse click.
     * @param y           The y-coordinate of the mouse click.
     */
    public TrayEvent(boolean rightClick, boolean leftClick, boolean doubleClick, int x, int y) {
        this.rightClick = rightClick;
        this.leftClick = leftClick;
        this.doubleClick = doubleClick;
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if the right mouse button was clicked.
     *
     * @return true if the right mouse button was clicked, false otherwise.
     */
    public boolean isRightClick() {
        return rightClick;
    }

    /**
     * Checks if the left mouse button was clicked.
     *
     * @return true if the left mouse button was clicked, false otherwise.
     */
    public boolean isLeftClick() {
        return leftClick;
    }

    /**
     * Checks if a double click event occurred.
     *
     * @return true if a double click event occurred, false otherwise.
     */
    public boolean isDoubleClick() {
        return doubleClick;
    }

    /**
     * Gets the x-coordinate of the mouse click.
     *
     * @return The x-coordinate of the mouse click.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the mouse click.
     *
     * @return The y-coordinate of the mouse click.
     */
    public int getY() {
        return y;
    }

    /**
     * Compares this Linker object with the specified object for equality.
     * Returns true if the specified object is also a Linker object and all
     * corresponding fields have the same values. Otherwise, it returns false.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrayEvent trayEvent = (TrayEvent) o;
        return  rightClick == trayEvent.rightClick
                && leftClick == trayEvent.leftClick
                && doubleClick == trayEvent.doubleClick
                && x == trayEvent.x && y == trayEvent.y;
    }

    /**
     * Returns a hash code value for the Linker object. The hash code is generated
     * based on the hash codes of each field. Two objects that are equal according
     * to the equals() method will produce the same hash code.
     *
     * @return the hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(rightClick, leftClick, doubleClick, x, y);
    }

    @Override
    public String toString() {
        return "TrayEvent{" +
                "rightClick=" + rightClick +
                ", leftClick=" + leftClick +
                ", doubleClick=" + doubleClick +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

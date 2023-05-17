/**
 * @author XDSSWAR
 * Created on 05/17/2023
 */
module jfx.tray {
    requires javafx.graphics;
    requires javafx.controls;
    requires org.jetbrains.annotations;
    requires java.desktop;

    exports xss.it.tray.internal to java.desktop;
    opens xss.it.tray.internal to java.desktop;

    exports xss.it.util to java.desktop;
    opens xss.it.util to java.desktop;


    exports xss.it;
    opens xss.it;
    exports xss.it.tray;
    opens xss.it.tray;
}
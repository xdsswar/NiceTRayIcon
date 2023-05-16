# NiceTrayIcon
A JavaFX 11 NiceTrayIcon with the AWT SystemTray but uses the JavaFx ContextMenu and MenuItems, so you can use CSS styles.

![NiceTrayIcon](/tray.png)

![NiceTrayIcon Hover](/tray-hover.png)

If you have any suggestions let me know. Also, if you can give it a try in MacOS and give me some feedback, it will be amazing since I don't own a mac pc.

# Usage
```java
public class Main extends Application {
    private static NiceTryIcon icon;//NiceTrayIcon

    /**
     * Create few JavaFx Images to use with the MenuItems Later
     */
    private static final javafx.scene.image.Image about = new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/about.png")).toExternalForm());

    private static final javafx.scene.image.Image help = new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/help.png")).toExternalForm());

    private static final javafx.scene.image.Image link = new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/link.png")).toExternalForm());

    private static final javafx.scene.image.Image skip = new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/skip.png")).toExternalForm());

    private static final javafx.scene.image.Image settings = new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/settings.png")).toExternalForm());

    private static final javafx.scene.image.Image exit = new javafx.scene.image.Image(
            Objects.requireNonNull(Run.class.getResource("/img/exit.png")).toExternalForm());

    @Override
    public void start(Stage stage) throws Exception {
        //Check if System Tray is Supported
        if (SystemTray.isSupported()) {
            //Create JavaFX ContextMenu
            ContextMenu m = new ContextMenu();

            //Create Few JavaFX MenuItems
            MenuItem m1 = new MenuItem("About");
            m1.setOnAction(event -> {
                System.out.println("About");
            });

            MenuItem m2 = new MenuItem("Help");
            m2.setOnAction(event -> {
                System.out.println("Help");
            });

            MenuItem m3 = new MenuItem("Open Link");
            m3.setOnAction(event -> {
                System.out.println("Open Link");
            });

            MenuItem m4 = new MenuItem("Skip Next");
            m4.setOnAction(event -> {
                System.out.println("Skip Next");
            });

            MenuItem m5 = new MenuItem("Settings");
            m5.setOnAction(event -> {
                System.out.println("Settings");
            });

            MenuItem m6 = new MenuItem("Exit");
            m6.setOnAction(event -> {
                System.out.println("Exit");
                Platform.exit();
            });

            //Set the JavaFX MenuItems Graphics using the JavaFx Images we created before
            m1.setGraphic(new ImageView(about));
            m2.setGraphic(new ImageView(help));
            m3.setGraphic(new ImageView(link));
            m4.setGraphic(new ImageView(skip));
            m5.setGraphic(new ImageView(settings));
            m6.setGraphic(new ImageView(exit));

            //Add 2 separators
            SeparatorMenuItem sp = new SeparatorMenuItem();
            SeparatorMenuItem sp1 = new SeparatorMenuItem();
            //Add the JavaFX MenuItems/Separators to the ContextMenu
            m.getItems().addAll(m1, m2, sp, m3, m4, m5, sp1, m6);
            //Create the AWT image we gonna use for the Tray Icon
            Image ic = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/ic.png"));
            //Initialize the NiceTrayIcon we declared before
            icon = new NiceTryIcon(ic, m);
            //Set the NiceTrayIcon to the System Tray
            SystemTray.getSystemTray().add(icon);
        }

    }

    //Remove the TrayIcon before Exiting
    public void stop() {
        if (SystemTray.isSupported()) {
            SystemTray.getSystemTray().remove(icon);
        }
    }

}

```

# NiceTrayIcon
Library name : jfx-tray 1.0.1

NiceTrayIcon is a utility class that provides a convenient way to create and manage a system tray icon
with a context menu in JavaFX applications. It allows you to display a custom image as the tray 
icon and associate a context menu that appears when the user interacts with the icon.

Still need some fixes, so try it, and let me know to improve. Enjoy!

Windows 10, jfx-tray version 1.0.0

![NiceTrayIcon](/tray.png)

![NiceTrayIcon Hover](/tray-hover.png)


Windows 11, jfx-tray version 1.0.1

![NiceTrayIcon Hover](/tray-w11.png)


### Not tested on linux, and it's not working on Mac yet 


## Features

- Create a system tray icon with a custom image.
- Associate a context menu with the tray icon.
- Handle actions and events triggered by the tray icon or context menu.
- Support for adding and removing style sheets to customize the appearance.

## Usage

1. Include the `NiceTrayIcon` class in your JavaFX project.

2. Create an instance of `NiceTrayIcon` by providing the tray icon image:

```
    NiceTrayIcon trayIcon = new NiceTrayIcon(new Image("path/to/icon.png"));
    MenuItem aboutMenuItem = new MenuItem("About");
    MenuItem helpMenuItem = new MenuItem("Help");
    //Add more menu items as neede
    ContextMenu contextMenu = new ContextMenu(aboutMenuItem, helpMenuItem)
    trayIcon.setContextMenu(contextMenu);
```

3. (Optional) Create a ContextMenu and add menu items:

```
    MenuItem aboutMenuItem = new MenuItem("About");
    MenuItem helpMenuItem = new MenuItem("Help");
    // Add more menu items as needed
    ContextMenu contextMenu = new ContextMenu(aboutMenuItem, helpMenuItem);
    trayIcon.setContextMenu(contextMenu);
```

4. (Optional) Set an action handler for the tray icon or menu items:

```
    trayIcon.setOnAction(event -> {
        // Handle tray icon action event
    });
    
    aboutMenuItem.setOnAction(event -> {
        // Handle "About" menu item action
    });
    
    helpMenuItem.setOnAction(event -> {
        // Handle "Help" menu item action
    });

```

5. Install the tray icon to the system tray:

```
    trayIcon.install();
```


6. (Optional) Add or remove style sheets to customize the appearance:

```
    trayIcon.addStyleSheet("path/to/styles.css");      // Add style sheet
    trayIcon.removeStyleSheet("path/to/styles.css");   // Remove style sheet
```

7. Uninstall the tray icon before exiting the application:

```
    trayIcon.uninstall();
```

# Example:
Here's an example of using NiceTrayIcon in a JavaFX application:


```java
public class Main extends Application{

        private static NiceTrayIcon trayIcon;

        // Create images for menu items
        private static final Image about = new Image("path/to/about.png");
        private static final Image help = new Image("path/to/help.png");
        // Add more images for other menu items

        @Override
        public void start(Stage stage) {
            // Create menu items with images
            MenuItem aboutMenuItem = new MenuItem("About", new ImageView(about));
            MenuItem helpMenuItem = new MenuItem("Help", new ImageView(help));
            // Add more menu items as needed

            ContextMenu contextMenu = new ContextMenu(aboutMenuItem, helpMenuItem);

            trayIcon = new NiceTrayIcon(new Image("path/to/icon.png"));
            trayIcon.setContextMenu(contextMenu);

            trayIcon.setOnAction(event -> {
                // Handle tray icon action event
            });

            trayIcon.install();
        }

        @Override
        public void stop() {
            trayIcon.uninstall();
        }

        public static void main(String[] args) {
            launch(args);
        }
}
```



In this updated version, the MIT license information has been added
at the end of the README, along with a link to the `LICENSE` file for more details.

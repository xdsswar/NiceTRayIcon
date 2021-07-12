# NiceTRayIcon
A JavaFX TrayIcon that uses the AWT SystemTray but usees the JavaFx ContextMenu and MenuItem, so you can use CSS to styles.

#Usage
```java

    private static NiceTryIcon icon;

    public void start(Stage stage) throws AWTException {
        if (SystemTray.isSupported()) {
            ContextMenu contextMenu = new ContextMenu();
            //Add some MenuItems
            MenuItem m1 = new MenuItem("Option 1 right here");
            m1.setOnAction(event -> {
                System.out.println("This is Option 1");
            }); 

            MenuItem m2 = new MenuItem("Option 2 right here");
            m2.setOnAction(event -> {
                 System.out.println("Exit all");
            });

            contextMenu.getItems().addAll(m1, m2);
            Image icon = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/ic.png"));

            icon = new NiceTryIcon(icon, contextMenu);
            //Add the TrayIcon to the System Tray if Supported
            SystemTray.getSystemTray().add(icon);        
        }        
    }

    public void stop(){
        if (SystemTray.isSupported()){
            SystemTray.getSystemTray().remove(icon);
        }
    }

```

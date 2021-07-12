# NiceTRayIcon
A JavaFX TrayIcon with the AWT SystemTray but uses the JavaFx ContextMenu and MenuItems, so you can use CSS styles.

#Usage
```java

    private static NiceTryIcon icon;

    public void start(Stage stage) throws AWTException {
    //Check if Supported
        if (SystemTray.isSupported()) {
            //Creating the JavaFX ContextMenu
            ContextMenu contextMenu = new ContextMenu();
            //Add some JavaFX MenuItems
            MenuItem m1 = new MenuItem("Option 1 right here");
            m1.setOnAction(event -> {
                System.out.println("This is Option 1");
            }); 

            MenuItem m2 = new MenuItem("Option 2 right here");
            m2.setOnAction(event -> {
                 System.out.println("Exit all");
                 Platform.exit();
            });
            //Add Menutims to the ContextMenu    
            contextMenu.getItems().addAll(m1, m2);
            //Create the AWT Image fot the NiceTrayIcon
            Image icon = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/ic.png"));
            //Initializing the NiceTrayIcon
            icon = new NiceTryIcon(icon, contextMenu);
            //Add the NiceTrayIcon to the System Tray 
            SystemTray.getSystemTray().add(icon);        
        }        
    }

    public void stop(){
        if (SystemTray.isSupported()){
            SystemTray.getSystemTray().remove(icon);
        }
    }

```

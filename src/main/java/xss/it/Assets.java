package xss.it;

import java.net.URL;

/**
 * @author XDSSWAR
 * Created on 05/17/2023
 */
public class Assets {


    /**
     * Loads a resource from the classpath using the specified location.
     *
     * @param location The location of the resource.
     * @return The URL of the loaded resource.
     */
    public static URL load(String location){
        return Assets.class.getResource(location);
    }
}

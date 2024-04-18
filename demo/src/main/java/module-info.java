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

/**
 * @author XDSSWAR
 * Created on 04/18/2024
 */
module demo {
    requires javafx.controls;
    requires javafx.graphics;
    requires nice.tray.icon;
    requires nfx.core;

    exports xss.it.demo;
    opens xss.it.demo;
}
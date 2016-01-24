/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.options;

import java.util.TreeMap;

public class KeybindManager extends TreeMap<String, String> {
    public KeybindManager() {
        put("B", ".t fastbreak");
        put("C", ".t fullbright");
        put("F", ".t fastplace");
        put("G", ".t flight");
        put("GRAVE", ".t speednuker");
        put("H", ".t /home");
        put("J", ".t phase");
        put("K", ".t multiaura");
        put("L", ".t nuker");
        put("LCONTROL", ".t clickgui");
        put("R", ".t killaura");
        put("U", ".t freecam");
        put("X", ".t x-ray");
        put("Z", ".t sneak");
    }
}

/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import net.minecraft.client.Minecraft;
import tk.wurst_client.api.GUI;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;
import tk.wurst_client.navigator.gui.NavigatorMainScreen;

@Info(category = Category.HIDDEN, description = "", name = "Navigator")
public class NavigatorMod extends Mod {
    @Override
    public void onToggle() {
        if (!(Minecraft.getMinecraft().currentScreen instanceof NavigatorMainScreen)) {
            GUI.displayGuiScreen(new NavigatorMainScreen());
        }
        //			Minecraft.getMinecraft().displayGuiScreen(new NavigatorMainScreen());
    }
}

/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import tk.wurst_client.WurstClient;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;

@Info(category = Category.MOVEMENT,
        description = "Makes you fall like if you had a hang glider.",
        name = "Glide",
        noCheatCompatible = false)
public class GlideMod extends Mod implements UpdateListener {
    @Override
    public void onEnable() {
        WurstClient.INSTANCE.events.add(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (Minecraft.getMinecraft().thePlayer.motionY < 0 && Minecraft.getMinecraft().thePlayer.isAirBorne &&
                !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isOnLadder() &&
                !Minecraft.getMinecraft().thePlayer.isInsideOfMaterial(Material.lava)) {
            Minecraft.getMinecraft().thePlayer.motionY = -0.125f;
            Minecraft.getMinecraft().thePlayer.jumpMovementFactor *= 1.21337f;
        }
    }

    @Override
    public void onDisable() {
        WurstClient.INSTANCE.events.remove(UpdateListener.class, this);
    }
}

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
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import tk.wurst_client.WurstClient;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;

@Info(category = Category.MOVEMENT,
        description = "Automatically sneaks all the time.",
        name = "Sneak")
public class SneakMod extends Mod implements UpdateListener {
    @Override
    public void onEnable() {
        WurstClient.INSTANCE.events.add(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (WurstClient.INSTANCE.mods.getModByClass(YesCheatMod.class).isActive()) {
            NetHandlerPlayClient sendQueue = Minecraft.getMinecraft().thePlayer.sendQueue;
            sendQueue.addToSendQueue(
                    new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, Action.START_SNEAKING));
            sendQueue.addToSendQueue(
                    new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, Action.STOP_SNEAKING));
        } else {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
                    new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, Action.START_SNEAKING));
        }
    }

    @Override
    public void onDisable() {
        WurstClient.INSTANCE.events.remove(UpdateListener.class, this);
        Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed = false;
        Minecraft.getMinecraft().thePlayer.sendQueue
                .addToSendQueue(new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, Action.STOP_SNEAKING));
    }
}

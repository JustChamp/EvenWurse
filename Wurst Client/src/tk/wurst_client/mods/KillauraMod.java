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
import net.minecraft.entity.EntityLivingBase;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;

import tk.wurst_client.WurstClient;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;
import tk.wurst_client.utils.EntityUtils;

@Info(category = Category.COMBAT,
	description = "Automatically attacks everything in your range.",
	name = "Killaura")
public class KillauraMod extends Mod implements UpdateListener
{
	public float normalSpeed = 20F;
	public float normalRange = 5F;
	public float yesCheatSpeed = 12F;
	public float yesCheatRange = 4.25F;
	public int fov = 360;
	public float realSpeed;
	public float realRange;
	
	@Override
	public void initSliders()
	{
		sliders.add(new BasicSlider("Killaura speed", normalSpeed, 2, 20, 0.1,
			ValueDisplay.DECIMAL));
		sliders.add(new BasicSlider("Killaura range", normalRange, 1, 6, 0.05,
			ValueDisplay.DECIMAL));
		sliders.add(new BasicSlider("Killaura FOV", fov, 30, 360, 10,
			ValueDisplay.DEGREES));
	}
	
	@Override
	public void updateSettings()
	{
		normalSpeed = (float)sliders.get(0).getValue();
		yesCheatSpeed = Math.min(normalSpeed, 12F);
		normalRange = (float)sliders.get(1).getValue();
		yesCheatRange = Math.min(normalRange, 4.25F);
		fov = (int)sliders.get(2).getValue();
	}
	
	@Override
	public void onEnable()
	{
		// TODO: Clean up this mess!
		if(WurstClient.INSTANCE.mods.killauraLegitMod.isEnabled())
			WurstClient.INSTANCE.mods.killauraLegitMod.setEnabled(false);
		if(WurstClient.INSTANCE.mods.multiAuraMod.isEnabled())
			WurstClient.INSTANCE.mods.multiAuraMod.setEnabled(false);
		if(WurstClient.INSTANCE.mods.clickAuraMod.isEnabled())
			WurstClient.INSTANCE.mods.clickAuraMod.setEnabled(false);
		if(WurstClient.INSTANCE.mods.triggerBotMod.isEnabled())
			WurstClient.INSTANCE.mods.triggerBotMod.setEnabled(false);
		WurstClient.INSTANCE.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		if(WurstClient.INSTANCE.mods.yesCheatMod.isActive())
		{
			realSpeed = yesCheatSpeed;
			realRange = yesCheatRange;
		}else
		{
			realSpeed = normalSpeed;
			realRange = normalRange;
		}
		updateMS();
		EntityLivingBase en = EntityUtils.getClosestEntity(true);
		if(hasTimePassedS(realSpeed) && en != null)
		{
			if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) <= realRange)
			{
				if(WurstClient.INSTANCE.mods.autoSwordMod.isActive())
					AutoSwordMod.setSlot();
				CriticalsMod.doCritical();
				WurstClient.INSTANCE.mods.blockHitMod.doBlock();
				EntityUtils.faceEntityPacket(en);
				Minecraft.getMinecraft().thePlayer.swingItem();
				Minecraft.getMinecraft().playerController.attackEntity(
					Minecraft.getMinecraft().thePlayer, en);
				updateLastMS();
			}
		}
	}
	
	@Override
	public void onDisable()
	{
		WurstClient.INSTANCE.events.remove(UpdateListener.class, this);
	}
}

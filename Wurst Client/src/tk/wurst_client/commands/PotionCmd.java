/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import tk.wurst_client.commands.Cmd.Info;
import tk.wurst_client.utils.MiscUtils;

import java.util.List;

@Info(help = "Changes the effects of the held potion.",
	name = "potion",
	syntax = {"add (<effect> <amplifier> <duration>)...",
		"set (<effect> <amplifier> <duration>)...", "remove <effect>"})
public class PotionCmd extends Cmd
{
	@Override
	public void execute(String[] args) throws Error
	{
		if(args.length == 0)
			syntaxError();
		if(!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
			error("Creative mode only.");
		
		ItemStack currentItem =
			Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();
		if(currentItem == null
			|| Item.getIdFromItem(currentItem.getItem()) != 373)
			error("You are not holding a potion in your hand.");
		
		NBTTagList newEffects = new NBTTagList();
		
		// remove
		if(args.length == 2)
		{
			if(!args[0].equalsIgnoreCase("Remove"))
				syntaxError();
			int id;
			id = parsePotionEffectId(args[1]);
			List oldEffects = new ItemPotion().getEffects(currentItem);
			if(oldEffects != null)
				for (Object oldEffect : oldEffects) {
					PotionEffect temp = (PotionEffect) oldEffect;
					if (temp.getPotionID() != id) {
						NBTTagCompound effect = new NBTTagCompound();
						effect.setInteger("Id", temp.getPotionID());
						effect.setInteger("Amplifier", temp.getAmplifier());
						effect.setInteger("Duration", temp.getDuration());
						newEffects.appendTag(effect);
					}
				}
			currentItem.setTagInfo("CustomPotionEffects", newEffects);
			return;
		}else if((args.length - 1) % 3 != 0)
			syntaxError();
		
		// add
		if(args[0].equalsIgnoreCase("add"))
		{
			List oldEffects = new ItemPotion().getEffects(currentItem);
			if(oldEffects != null)
				for (Object oldEffect : oldEffects) {
					PotionEffect temp = (PotionEffect) oldEffect;
					NBTTagCompound effect = new NBTTagCompound();
					effect.setInteger("Id", temp.getPotionID());
					effect.setInteger("Amplifier", temp.getAmplifier());
					effect.setInteger("Duration", temp.getDuration());
					newEffects.appendTag(effect);
				}
		}else if(!args[0].equalsIgnoreCase("set"))
			syntaxError();
		
		// add & set
		for(int i = 0; i < (args.length - 1) / 3; i++)
		{
			int id = parsePotionEffectId(args[1 + i * 3]);
			int amplifier = 0;
			int duration = 0;
			
			if(MiscUtils.isInteger(args[2 + i * 3])
				&& MiscUtils.isInteger(args[3 + i * 3]))
			{
				amplifier = Integer.parseInt(args[2 + i * 3]) - 1;
				duration = Integer.parseInt(args[3 + i * 3]);
			}else
				syntaxError();
			
			NBTTagCompound effect = new NBTTagCompound();
			effect.setInteger("Id", id);
			effect.setInteger("Amplifier", amplifier);
			effect.setInteger("Duration", duration * 20);
			newEffects.appendTag(effect);
		}
		currentItem.setTagInfo("CustomPotionEffects", newEffects);
	}
	
	public int parsePotionEffectId(String input) throws SyntaxError
	{
		int id = 0;
		try
		{
			id = Integer.parseInt(input);
		}catch(NumberFormatException var11)
		{
			try
			{
				id = Potion.func_180142_b(input).id;
			}catch(NullPointerException e)
			{
				syntaxError();
			}
		}
		if(id < 1)
			syntaxError();
		return id;
	}
}

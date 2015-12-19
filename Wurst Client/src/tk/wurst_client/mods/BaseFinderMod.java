/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import tk.wurst_client.WurstClient;
import tk.wurst_client.events.listeners.RenderListener;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;
import tk.wurst_client.utils.RenderUtils;

import java.awt.*;
import java.util.ArrayList;

@Info(category = Category.RENDER,
	description = "Finds player bases by searching for man-made blocks.\n"
		+ "Good for finding faction bases.",
	name = "BaseFinder")
public class BaseFinderMod extends Mod implements UpdateListener,
	RenderListener
{
	public BaseFinderMod()
	{
		initBlocks();
	}
	
	private ArrayList<Block> naturalBlocks = new ArrayList<>();
	private ArrayList<BlockPos> matchingBlocks = new ArrayList<>();
	private static final int RANGE = 50;
	private static final int MAX_BLOCKS = 1024;
	private boolean shouldInform = true;
	
	@Override
	public void onEnable()
	{
		shouldInform = true;
		WurstClient.INSTANCE.events.add(UpdateListener.class, this);
		WurstClient.INSTANCE.events.add(RenderListener.class, this);
	}
	
	@Override
	public void onRender()
	{
		for(BlockPos blockPos : matchingBlocks)
			RenderUtils.framelessBlockESP(blockPos, new Color(255, 0, 0));
	}
	
	@Override
	public void onUpdate()
	{
		updateMS();
		if(hasTimePassedM(3000))
		{
			matchingBlocks.clear();
			for(int y = RANGE; y >= -RANGE; y--)
			{
				for(int x = RANGE; x >= -RANGE; x--)
				{
					for(int z = RANGE; z >= -RANGE; z--)
					{
						int posX =
							(int)(Minecraft.getMinecraft().thePlayer.posX + x);
						int posY =
							(int)(Minecraft.getMinecraft().thePlayer.posY + y);
						int posZ =
							(int)(Minecraft.getMinecraft().thePlayer.posZ + z);
						BlockPos pos = new BlockPos(posX, posY, posZ);
						if(!naturalBlocks
							.contains(Minecraft.getMinecraft().theWorld
								.getBlockState(pos).getBlock()))
							matchingBlocks.add(pos);
						if(matchingBlocks.size() >= MAX_BLOCKS)
							break;
					}
					if(matchingBlocks.size() >= MAX_BLOCKS)
						break;
				}
				if(matchingBlocks.size() >= MAX_BLOCKS)
					break;
			}
			if(matchingBlocks.size() >= MAX_BLOCKS && shouldInform)
			{
				WurstClient.INSTANCE.chat.warning(getName()
					+ " found �lA LOT�r of blocks.");
				WurstClient.INSTANCE.chat
					.message("To prevent lag, it will only show the first "
						+ MAX_BLOCKS + " blocks.");
				shouldInform = false;
			}else if(matchingBlocks.size() < MAX_BLOCKS)
				shouldInform = true;
			updateLastMS();
		}
	}
	
	@Override
	public void onDisable()
	{
		WurstClient.INSTANCE.events.remove(UpdateListener.class, this);
		WurstClient.INSTANCE.events.remove(RenderListener.class, this);
	}
	
	private void initBlocks()
	{
		naturalBlocks.add(Block.getBlockFromName("air"));
		naturalBlocks.add(Block.getBlockFromName("stone"));
		naturalBlocks.add(Block.getBlockFromName("dirt"));
		naturalBlocks.add(Block.getBlockFromName("grass"));
		naturalBlocks.add(Block.getBlockFromName("gravel"));
		naturalBlocks.add(Block.getBlockFromName("sand"));
		naturalBlocks.add(Block.getBlockFromName("clay"));
		naturalBlocks.add(Block.getBlockFromName("sandstone"));
		naturalBlocks.add(Block.getBlockById(8));
		naturalBlocks.add(Block.getBlockById(9));
		naturalBlocks.add(Block.getBlockById(10));
		naturalBlocks.add(Block.getBlockById(11));
		naturalBlocks.add(Block.getBlockFromName("log"));
		naturalBlocks.add(Block.getBlockFromName("log2"));
		naturalBlocks.add(Block.getBlockFromName("leaves"));
		naturalBlocks.add(Block.getBlockFromName("leaves2"));
		naturalBlocks.add(Block.getBlockFromName("deadbush"));
		naturalBlocks.add(Block.getBlockFromName("iron_ore"));
		naturalBlocks.add(Block.getBlockFromName("coal_ore"));
		naturalBlocks.add(Block.getBlockFromName("gold_ore"));
		naturalBlocks.add(Block.getBlockFromName("diamond_ore"));
		naturalBlocks.add(Block.getBlockFromName("emerald_ore"));
		naturalBlocks.add(Block.getBlockFromName("redstone_ore"));
		naturalBlocks.add(Block.getBlockFromName("lapis_ore"));
		naturalBlocks.add(Block.getBlockFromName("bedrock"));
		naturalBlocks.add(Block.getBlockFromName("mob_spawner"));
		naturalBlocks.add(Block.getBlockFromName("mossy_cobblestone"));
		naturalBlocks.add(Block.getBlockFromName("tallgrass"));
		naturalBlocks.add(Block.getBlockFromName("yellow_flower"));
		naturalBlocks.add(Block.getBlockFromName("red_flower"));
		naturalBlocks.add(Block.getBlockFromName("cobweb"));
		naturalBlocks.add(Block.getBlockFromName("brown_mushroom"));
		naturalBlocks.add(Block.getBlockFromName("red_mushroom"));
		naturalBlocks.add(Block.getBlockFromName("snow_layer"));
		naturalBlocks.add(Block.getBlockFromName("vine"));
		naturalBlocks.add(Block.getBlockFromName("waterlily"));
		naturalBlocks.add(Block.getBlockFromName("double_plant"));
		naturalBlocks.add(Block.getBlockFromName("hardened_clay"));
		naturalBlocks.add(Block.getBlockFromName("red_sandstone"));
		naturalBlocks.add(Block.getBlockFromName("ice"));
		naturalBlocks.add(Block.getBlockFromName("quartz_ore"));
		naturalBlocks.add(Block.getBlockFromName("obsidian"));
		naturalBlocks.add(Block.getBlockFromName("monster_egg"));
		naturalBlocks.add(Block.getBlockFromName("red_mushroom_block"));
		naturalBlocks.add(Block.getBlockFromName("brown_mushroom_block"));
	}
}

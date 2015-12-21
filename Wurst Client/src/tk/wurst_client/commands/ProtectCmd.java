/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.commands;

import net.minecraft.entity.EntityLivingBase;
import tk.wurst_client.WurstClient;
import tk.wurst_client.mods.ProtectMod;
import tk.wurst_client.utils.EntityUtils;

@Cmd.Info(help = "Toggles Protect or makes it protect a specific entity.",
	name = "protect",
	syntax = {"[<entity>]"})
public class ProtectCmd extends Cmd
{
	@Override
	public void execute(String[] args) throws Error
	{
		if(args.length > 1)
			syntaxError();
		if(args.length == 0)
			WurstClient.INSTANCE.mods.getModByClass(ProtectMod.class).toggle();
		else
		{
			WurstClient.INSTANCE.mods.disableModsByClass(ProtectMod.class);
			EntityLivingBase entity = EntityUtils.searchEntityByName(args[0]);
			if(entity == null)
				error("Entity \"" + args[0] + "\" could not be found.");
			WurstClient.INSTANCE.mods.getModByClass(ProtectMod.class).setEnabled(true);
			WurstClient.INSTANCE.mods.getModByClass(ProtectMod.class).setFriend(entity);
		}
	}
}

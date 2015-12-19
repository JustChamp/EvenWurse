/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.commands;

import tk.wurst_client.WurstClient;
import tk.wurst_client.commands.Cmd.Info;
import tk.wurst_client.utils.MiscUtils;

@Info(help = "Changes the amount of Throw or toggles it.",
	name = "throw",
	syntax = {"[amount <amount>]"})
public class ThrowCmd extends Cmd
{
	@Override
	public void execute(String[] args) throws Error
	{
		if(args.length == 0)
		{
			WurstClient.INSTANCE.mods.throwMod.toggle();
			WurstClient.INSTANCE.chat.message("Throw turned "
				+ (WurstClient.INSTANCE.mods.throwMod.isEnabled()
					? "on" : "off") + ".");
		}else if(args.length == 2 && args[0].equalsIgnoreCase("amount")
			&& MiscUtils.isInteger(args[1]))
		{
			if(Integer.valueOf(args[1]) < 1)
			{
				WurstClient.INSTANCE.chat
					.error("Throw amount must be at least 1.");
				return;
			}
			WurstClient.INSTANCE.options.throwAmount = Integer.valueOf(args[1]);
			WurstClient.INSTANCE.files.saveOptions();
			WurstClient.INSTANCE.chat.message("Throw amount set to " + args[1]
				+ ".");
		}else
			syntaxError();
	}
}

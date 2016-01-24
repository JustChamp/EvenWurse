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

import java.util.Iterator;

@Info(help = "Shows the command list or the help for a command.",
        name = "help",
        syntax = {"[<page>]", "[<command>]"})
public class HelpCmd extends Cmd {
    @Override
    public void execute(String[] args) throws Error {
        if (args.length == 0) {
            execute(new String[]{"1"});
            return;
        }
        int pages = (int) Math.ceil(WurstClient.INSTANCE.commands.countCommands() / 8D);
        if (MiscUtils.isInteger(args[0])) {
            int page = Integer.valueOf(args[0]);
            if (page > pages || page < 1) syntaxError("Invalid page: " + page);
            WurstClient.INSTANCE.chat.message("Available commands: " + WurstClient.INSTANCE.commands.countCommands());
            WurstClient.INSTANCE.chat.message("Command list (page " + page + "/" + pages + "):");
            Iterator<Cmd> itr = WurstClient.INSTANCE.commands.getAllCommands().iterator();
            for (int i = 0; itr.hasNext(); i++) {
                Cmd cmd = itr.next();
                if (i >= (page - 1) * 8 && i < (page - 1) * 8 + 8) WurstClient.INSTANCE.chat.message(cmd.getCmdName());
            }
        } else {
            Cmd cmd = WurstClient.INSTANCE.commands.getCommandByName(args[0]);
            if (cmd != null) {
                WurstClient.INSTANCE.chat.message("Available help for ." + args[0] + ":");
                cmd.printHelp();
                cmd.printSyntax();
            } else {
                error("Command \"" + args[0] + "\" could not be found.");
            }
        }
    }
}

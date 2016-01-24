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
import tk.wurst_client.hooks.ServerHook;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

@Info(help = "Shows the IP of the server you are currently playing on or copies it to the clipboard.",
        name = "ip",
        syntax = {"[copy]"})
public class IpCmd extends Cmd {
    @Override
    public void execute(String[] args) throws Error {
        if (args.length == 0) {
            WurstClient.INSTANCE.chat.message("IP: " + ServerHook.getCurrentServerIP());
        } else if (args[0].toLowerCase().equals("copy")) {
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new StringSelection(ServerHook.getCurrentServerIP()), null);
            WurstClient.INSTANCE.chat.message("IP copied to clipboard.");
        } else {
            syntaxError();
        }
    }
}

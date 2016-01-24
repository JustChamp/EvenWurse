/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.gui.alts;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tk.wurst_client.WurstClient;
import tk.wurst_client.alts.Alt;
import tk.wurst_client.alts.LoginManager;

public class GuiAltEdit extends AltEditorScreen {
    private Alt editedAlt;

    public GuiAltEdit(GuiScreen par1GuiScreen, Alt editedAlt) {
        super(par1GuiScreen);
        this.editedAlt = editedAlt;
    }

    @Override
    protected String getDoneButtonText() {
        return "Save";
    }

    @Override
    protected String getEmailBoxText() {
        return editedAlt.getEmail();
    }

    @Override
    protected String getPasswordBoxText() {
        return editedAlt.getPassword();
    }

    @Override
    protected void onDoneButtonClick(GuiButton button) {// Save
        Alt newAlt = new Alt(emailBox.getText(), passwordBox.getText(), editedAlt.isStarred());
        if (passwordBox.getText().length() == 0) {// Cracked
            GuiAltList.alts.set(GuiAltList.alts.indexOf(editedAlt), newAlt);
            displayText = "";
        } else {// Premium
            displayText = LoginManager.check(emailBox.getText(), passwordBox.getText());
            if (displayText.equals("")) GuiAltList.alts.set(GuiAltList.alts.indexOf(editedAlt), newAlt);
        }
        if (displayText.equals("")) {
            GuiAltList.sortAlts();
            WurstClient.INSTANCE.files.saveAlts();
            mc.displayGuiScreen(prevMenu);
            GuiAlts.altList.elementClicked(GuiAltList.alts.indexOf(newAlt), false, 0, 0);
        } else {
            errorTimer = 8;
        }
    }

    @Override
    protected String getUrl() {
        return "/alt-manager/edit";
    }

    @Override
    protected String getTitle() {
        return "Edit this Alt";
    }
}

/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.spam.tag;

import tk.wurst_client.spam.exceptions.InvalidTagException;
import tk.wurst_client.spam.exceptions.SpamException;
import tk.wurst_client.spam.tag.tags.Random;
import tk.wurst_client.spam.tag.tags.Repeat;
import tk.wurst_client.spam.tag.tags.Var;

import java.util.ArrayList;

public class TagManager {
    private final ArrayList<Tag> activeTags = new ArrayList<>();

    public TagManager() {
        activeTags.add(new Random());
        activeTags.add(new Repeat());
        activeTags.add(new Var());
    }

    public Tag getTagByName(String name, int line) throws SpamException {
        for (Tag activeTag : activeTags) {
            if (activeTag.getName().equals(name)) return activeTag;
        }
        throw new InvalidTagException(name, line);
    }

    public ArrayList<Tag> getActiveTags() {
        return activeTags;
    }

    public String process(TagData tagData) throws SpamException {
        Tag tag = getTagByName(tagData.getTagName(), tagData.getTagLine());
        return tag.process(tagData);
    }
}

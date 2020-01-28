package com.hadroncfy.bungeewhitelist;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import net.md_5.bungee.api.chat.BaseComponent;

public interface IWhitelist {
    public boolean isWhitelisted(UUID uuid);
    public void update(UUID uuid, String name);
    public void remove(UUID uuid);
    public boolean removeByName(String name);
    public void reload();
    public void enable();
    public Profile createUUID(String name) throws IOException;
    public void disable();
    public List<String> list();
    public void broadcast(BaseComponent c);
}
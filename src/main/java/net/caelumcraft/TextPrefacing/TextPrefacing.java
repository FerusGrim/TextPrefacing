package net.caelumcraft.TextPrefacing;


/*
 * @author FerusGrim
 * @website http://www.caelumcraft.net/
 * Copyright under GPLv3 to Nicholas Badger (FerusGrim) - 2014
 */

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class TextPrefacing extends JavaPlugin {
    private PlayerManager pm;
    public String mStart = ChatColor.translateAlternateColorCodes('&', "&a&l[TextPrefacing]&r&e ");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        pm = new PlayerManager(this);
        getServer().getPluginManager().registerEvents(pm, this);
        getCommand("color").setExecutor(new CommandColor(this, pm));
        getCommand("name").setExecutor(new CommandName(this, pm));
    }
}

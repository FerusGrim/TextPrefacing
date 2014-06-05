/*
 * @author FerusGrim
 * @website http://www.caelumcraft.net/
 * Copyright under GPLv3 to Nicholas Badger (FerusGrim) - 2014
 */

package net.caelumcraft.TextPrefacing;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

import java.util.HashMap;
import java.util.logging.Level;

public class PlayerManager implements Listener {
    private TextPrefacing plugin;
    private HashMap<String, String> playerNames;
    private HashMap<String, ChatColor> playerColors;

    public PlayerManager(TextPrefacing plugin) {
        this.plugin = plugin;
        if (!plugin.getConfig().isSet("Players")) {
            plugin.getConfig().createSection("Players");
        }
        playerNames = new HashMap<>(getNames());
        playerColors = new HashMap<>(getColors());
    }

    @EventHandler (priority = EventPriority.HIGH)
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (plugin.getConfig().isSet("Players." + uuid)) {
            String name = plugin.getConfig().getString("Players." + uuid + ".name");
            String color = plugin.getConfig().getString("Players." + uuid + ".color");
            if (name == null || name.isEmpty()) {
                updateName(uuid, player.getName(), player);
            }
            if (color == null || color.isEmpty()) {
                updateColor(uuid, "white");
            }
            player.setDisplayName(name);
            player.setPlayerListName(name);
        } else {
            plugin.getConfig().createSection("Players." + uuid);
            plugin.saveConfig();
            updateName(uuid, player.getName(), player);
            updateColor(uuid, "white");
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    private void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();
        ChatColor color = playerColors.get(uuid);
        String name = playerNames.get(uuid);
        String msg = event.getMessage();
        event.setFormat(color + name + ChatColor.GRAY + " > " + msg);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    private void onAsyncPlayerReceiveNameTagEvent(AsyncPlayerReceiveNameTagEvent event) {
        Player player = event.getNamedPlayer();
        String uuid = player.getUniqueId().toString();
        String name = playerNames.get(uuid);
        ChatColor color = playerColors.get(uuid);
        event.setTag(color + name);
    }

    public void updateName(String uuid, String name, Player player) {
        plugin.getConfig().set("Players." + uuid + ".color", name);
        plugin.saveConfig();
        playerNames.put(uuid, name);
        player.setDisplayName(name);
        player.setPlayerListName(name);
    }

    public void updateColor(String uuid, String color) {
        plugin.getConfig().set("Players." + uuid + ".color", color);
        plugin.saveConfig();
        playerColors.put(uuid, getChatColor(color, null));
    }

    private HashMap<String, String> getNames() {
        HashMap<String, String> names = new HashMap<>();
        for (String uuid : plugin.getConfig().getConfigurationSection("Players").getKeys(false)) {
            names.put(uuid, plugin.getConfig().getString("Players." + uuid + ".name"));
        }
        return names;
    }

    private HashMap<String, ChatColor> getColors() {
        HashMap<String, ChatColor> colors = new HashMap<>();
        for (String uuid : plugin.getConfig().getConfigurationSection("Players").getKeys(false)) {
            if (uuid != null && !uuid.isEmpty()) {
                colors.put(uuid, getColorCode(uuid));
            }
        }
        return colors;
    }

    private ChatColor getColorCode(String uuid) {
        return getChatColor(plugin.getConfig().getString("Players." + uuid + ".color"), null);
    }

    private ChatColor getChatColor(String color, CommandSender sender) {
        switch(color) {
            case "black":
                return ChatColor.BLACK;
            case "dark_blue":
                return ChatColor.DARK_BLUE;
            case "dark_green":
                return ChatColor.DARK_GREEN;
            case "dark_aqua":
                return ChatColor.DARK_AQUA;
            case "dark_red":
                return ChatColor.DARK_RED;
            case "dark_purple":
                return ChatColor.DARK_PURPLE;
            case "gold":
                return ChatColor.GOLD;
            case "gray":
                return ChatColor.GRAY;
            case "dark_gray":
                return ChatColor.DARK_GRAY;
            case "blue":
                return ChatColor.BLUE;
            case "green":
                return ChatColor.GREEN;
            case "aqua":
                return ChatColor.AQUA;
            case "red":
                return ChatColor.RED;
            case "light_purple":
                return ChatColor.LIGHT_PURPLE;
            case "yellow":
                return ChatColor.YELLOW;
            case "White":
                return ChatColor.WHITE;
            default:
                if (sender != null) {
                    sender.sendMessage(plugin.mStart + "Color isn't valid! Setting to WHITE!");
                }
                return ChatColor.WHITE;
        }
    }
}
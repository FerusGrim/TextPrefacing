/*
 * @author FerusGrim
 * @website http://www.caelumcraft.net/
 * Copyright under GPLv3 to Nicholas Badger (FerusGrim) - 2014
 */

package net.caelumcraft.TextPrefacing;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

import java.util.ArrayList;
import java.util.logging.Level;

public class CommandColor implements CommandExecutor {
    private TextPrefacing plugin;
    private PlayerManager pm;
    private ArrayList<String> validColors = new ArrayList<>();

    public CommandColor(TextPrefacing plugin, PlayerManager pm) {
        this.plugin = plugin;
        this.pm = pm;
        validColors.add("black");
        validColors.add("dark_blue");
        validColors.add("dark_green");
        validColors.add("dark_aqua");
        validColors.add("dark_red");
        validColors.add("dark_purple");
        validColors.add("gold");
        validColors.add("gray");
        validColors.add("dark_gray");
        validColors.add("blue");
        validColors.add("green");
        validColors.add("aqua");
        validColors.add("red");
        validColors.add("light_purple");
        validColors.add("yellow");
        validColors.add("white");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(plugin.mStart + "Valid Colors!");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&0BLACK &1DARK_BLUE &2DARK_GREEN &3DARK_AQUA &4DARK_RED &5DARK_PURPLE &6GOLD &7GRAY &8DARK_GRAY &9BLUE &aGREEN &bAQUA &cRED &dLIGHT_PURPLE &eYELLOW &fWHITE"));
            } else {
                if (validColors.contains(args[0].toLowerCase())) {
                    pm.updateColor(((Player) sender).getUniqueId().toString(), args[0].toLowerCase());
                    TagAPI.refreshPlayer((Player) sender);
                    sender.sendMessage(plugin.mStart + "Changed name color to : " + args[0]);
                } else {
                    sender.sendMessage(plugin.mStart + "Invalid color!");
                }
            }
        } else {
            plugin.getLogger().log(Level.WARNING, "Must be done as player!");
        }
        return true;
    }
}

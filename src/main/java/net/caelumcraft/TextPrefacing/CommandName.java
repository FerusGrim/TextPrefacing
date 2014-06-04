/*
 * @author FerusGrim
 * @website http://www.caelumcraft.net/
 * Copyright under GPLv3 to Nicholas Badger (FerusGrim) - 2014
 */

package net.caelumcraft.TextPrefacing;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CommandName implements CommandExecutor {
    private TextPrefacing plugin;
    private PlayerManager pm;

    public CommandName(TextPrefacing plugin, PlayerManager pm) {
        this.plugin = plugin;
        this.pm = pm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(plugin.mStart + "Missing name!");
            } else {
                if (args[0].matches("[a-zA-Z0-9_]{3,16}")) {
                    pm.updateName(((Player) sender).getUniqueId().toString(), args[0], (Player) sender);
                } else {
                    sender.sendMessage(plugin.mStart + "Invalid name!");
                }
            }
        } else {
            plugin.getLogger().log(Level.WARNING, "Must be done as player!");
        }
        return true;
    }
}

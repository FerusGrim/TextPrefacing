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
import org.kitteh.tag.TagAPI;

import java.util.logging.Level;

public class CommandColor implements CommandExecutor {
    private TextPrefacing plugin;
    private PlayerManager pm;

    public CommandColor(TextPrefacing plugin, PlayerManager pm) {
        this.plugin = plugin;
        this.pm = pm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(plugin.mStart + "Missing color!");
            } else {
                pm.updateColor(((Player) sender).getUniqueId().toString(), args[0].toLowerCase());
                TagAPI.refreshPlayer((Player) sender);
                sender.sendMessage(plugin.mStart + "Changed name color to : " + args[0]);
            }
        } else {
            plugin.getLogger().log(Level.WARNING, "Must be done as player!");
        }
        return true;
    }
}

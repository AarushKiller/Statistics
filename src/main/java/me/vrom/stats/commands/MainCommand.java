package me.vrom.stats.commands;

import me.vrom.stats.GetStatistics;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class MainCommand implements CommandExecutor{
    private GetStatistics plugin;

    public MainCommand(GetStatistics plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player send = (Player)sender;
            if (args.length == 1) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null || !player.isOnline()) {
                    send.sendMessage(this.plugin.offline);
                    return true;
                }
                if (!player.hasPermission("statistics.use")) {
                    send.sendMessage(this.plugin.perm);
                    return true;
                }
                for (Statistic stat : this.plugin.stats) {
                    int value = 0;
                    try {
                        value = player.getStatistic(stat);
                    } catch (IllegalArgumentException e) {
                        this.plugin.getLogger().severe("[Statistics] The statistic" + stat.name() + " requires arguments");
                        continue;
                    }
                    String result = this.plugin.format.replace("%statistic", stat.name()).replace("%value%", Integer.toString(value));
                    send.sendMessage(result);
                }
            }   else {
                send.sendMessage(this.plugin.usage);
            }
        }   else {
            this.plugin.getLogger().severe("This command can be only run by players!");
        }
        return true;
    }
}

package me.vrom.stats;

import me.vrom.stats.commands.MainCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;

public final class GetStatistics extends JavaPlugin {
    public List<Statistic> stats = new ArrayList<>();

    public String offline;

    public String format;

    public String usage;

    public String perm;

    FileConfiguration config = getConfig();

    public void onEnable() {
        getServer().getConsoleSender().sendMessage("[Statistics] Plugin by Vrom");
        getServer().getConsoleSender().sendMessage("[Statistics] Loading statistics...");
        saveDefaultConfig();
        this.offline = ChatColor.translateAlternateColorCodes('&', this.config.getString("offline"));
        this.format = ChatColor.translateAlternateColorCodes('&', this.config.getString("format"));
        this.usage = ChatColor.translateAlternateColorCodes('&', this.config.getString("usage"));
        this.perm = ChatColor.translateAlternateColorCodes('&', this.config.getString("permission"));
        getCommand("getstats").setExecutor((CommandExecutor) new MainCommand(this));
        List<String> list = this.config.getStringList("statistics");
        for (String str : list) {
            try {
                this.stats.add(Statistic.valueOf(str));
                getServer().getConsoleSender().sendMessage("[Statistics] '" + str + "' added successfully!");
            } catch (IllegalArgumentException e) {
                getServer().getConsoleSender().sendMessage("[Statistics] '" + str + "' is not recognized as a valid statistic");
            }
        }
    }
}

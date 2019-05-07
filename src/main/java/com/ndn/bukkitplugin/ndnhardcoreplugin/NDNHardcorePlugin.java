package com.ndn.bukkitplugin.ndnhardcoreplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class NDNHardcorePlugin extends JavaPlugin implements Listener {

	Scoreboard board;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		recipieFurnace();
		board = Bukkit.getScoreboardManager().getMainScoreboard();
		setup();
	}

	@Override
	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		if (cmd.getName().equals("start") && sender.isOp()) {
			start();
			return true;
		} else if (cmd.getName().equals("join")) {
			board = Bukkit.getScoreboardManager().getMainScoreboard();
			if (args.length > 0 && args[0].equals("1")) {
				board.getTeam("team1").addEntry(sender.getName());
				sender.sendMessage("You Joined Team 1");
				return true;
			} else if (args.length > 0 && args[0].equals("2")) {
				board.getTeam("team2").addEntry(sender.getName());
				sender.sendMessage("You Joined Team 2");
				return true;
			} else {
				return false;
			}
		} else if (cmd.getName().equals("setup") && sender.isOp()) {
			setup();
			return true;
		}

		return true;

	}

	public void setup() {
		getServer().getWorld("world").getWorldBorder().setCenter(getServer().getWorld("world").getSpawnLocation());
		getServer().getWorld("world").getWorldBorder().setSize(40);
		getServer().getConsoleSender().sendMessage("WB set up");

		if (board.getTeams().size() < 0) {
			board.registerNewTeam("team1");
			board.registerNewTeam("team2");
		}
		getServer().getConsoleSender().sendMessage("Made Teams");

		board.getTeam("team1").setAllowFriendlyFire(false);
		board.getTeam("team2").setAllowFriendlyFire(false);
		getServer().getConsoleSender().sendMessage("Friendly Fire Enabled");

		board.getTeam("team1").setColor(ChatColor.BLUE);
		board.getTeam("team2").setColor(ChatColor.RED);
		getServer().getConsoleSender().sendMessage("Colors Set");

		getServer().getConsoleSender().sendMessage("COMPLETE!");
	}

	public void start() {
		getServer().getWorld("world").getWorldBorder().setSize(3250, 120);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {

	}

	private void recipieFurnace() {
		getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH));

	}

}

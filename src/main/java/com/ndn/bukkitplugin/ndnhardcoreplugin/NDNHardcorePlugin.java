package com.ndn.bukkitplugin.ndnhardcoreplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class NDNHardcorePlugin extends JavaPlugin implements Listener {

	Scoreboard board;
	boolean isStarted = false;
	int secondsLeft = 60;
	int minutes = 0;
	static private boolean noPVP = true;
	final static int PASSING_PEROID = 20;//20
	final static int CIRCLE_MOVES = 120;//120
	final static int CIRCLE_WARN = 5;//5
	static boolean circleMoveing;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		addRecipies();
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
			if (!isStarted) {
				board = Bukkit.getScoreboardManager().getMainScoreboard();
				if (args.length > 0 && args[0].equals("1")) {
					board.getTeam("team1").addEntry(sender.getName());
					sender.sendMessage(ChatColor.DARK_BLUE + "You Joined Team 1");
					return true;
				} else if (args.length > 0 && args[0].equals("2")) {
					board.getTeam("team2").addEntry(sender.getName());
					sender.sendMessage(ChatColor.DARK_RED + "You Joined Team 2");
					return true;
				} else {
					return false;
				}
			} else {
				sender.sendMessage("The game has started.");
				return true;
			}
		} else if (cmd.getName().equals("setup") && sender.isOp()) {
			setup();
			return true;
		} else if (cmd.getName().equals("pvp") && sender.isOp()) {
			noPVP = !noPVP;
			sender.sendMessage("pvp: " + noPVP);
			return true;
		} else if (cmd.getName().equals("rules")) {
			sender.sendMessage(ChatColor.ITALIC + "" + ChatColor.GOLD + "Rules: https://docs.google.com/document/d/1II77XN_pMzInO2cbRO6HsBfTOciXJiOUsc0ItPK5YPs/edit?usp=sharing");
			return true;
		}

		return true;

	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void dmg(final EntityDamageByEntityEvent event) {
		if(noPVP && event.getEntity() instanceof Player && event.getDamager() instanceof Player && ( event.getCause().equals(DamageCause.ENTITY_ATTACK) || event.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK) )) {
			event.setCancelled(true);
			event.getDamager().sendMessage(ChatColor.RED + "No PVP at this time.");
		}
	}
	

	public void setup() {
		isStarted = false;
		noPVP = true;
		getServer().getWorld("world").getWorldBorder().setCenter(getServer().getWorld("world").getSpawnLocation());
		getServer().getWorld("world").getWorldBorder().setSize(32);
		getServer().getConsoleSender().sendMessage("WB set up");

		try {
			board.registerNewTeam("team1");
			board.registerNewTeam("team2");
		} catch (Exception e) {
			getServer().getConsoleSender().sendMessage("Team error caught");
		}
		getServer().getConsoleSender().sendMessage("Made Teams");

		board.getTeam("team1").setAllowFriendlyFire(false);
		board.getTeam("team2").setAllowFriendlyFire(false);
		getServer().getConsoleSender().sendMessage("Friendly Fire Enabled");

		board.getTeam("team1").setColor(ChatColor.BLUE);
		board.getTeam("team2").setColor(ChatColor.RED);
		getServer().getConsoleSender().sendMessage("Colors Set");

		placeMiddle();
		getServer().getConsoleSender().sendMessage("Tower Placed!");

		getServer().getConsoleSender().sendMessage("COMPLETE!");
	}
	
	public void healPlayers() {
		for(Player p : getServer().getOnlinePlayers()) {
			p.setHealth(20);
			p.setFoodLevel(20);
		}
	}

	public void start() {
		getServer().broadcastMessage(ChatColor.GREEN + "The Game is starting in 60 Seconds!");
		secondsLeft = 60;
		isStarted = true;
//		seconds.scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				if(secondsLeft < 10) {
//					getServer().broadcastMessage(ChatColor.YELLOW + "" + secondsLeft + "!");
//				}
//				if(secondsLeft <= 0) {
//					healPlayers();
//					getServer().broadcastMessage(ChatColor.YELLOW + "GO!");
//					getServer().getWorld("world").getWorldBorder().setSize(3250, 120);
//					getServer().broadcastMessage(ChatColor.GREEN + "Twenty Minute Passing Peroid Started");
//					seconds.purge();
//				}
//				secondsLeft--;
//				
//			}
//		}, 1*1000, 1*1000);
		
		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				if(secondsLeft <= 20 && secondsLeft > 0) {
					getServer().broadcastMessage(ChatColor.YELLOW + "" + secondsLeft);
				}
				if(secondsLeft <= 0) {
					healPlayers();
					getServer().getWorld("world").setTime(1000* 10);
					getServer().broadcastMessage(ChatColor.YELLOW + "GO!");
					getServer().getWorld("world").getWorldBorder().setSize(2000, 120);
					getServer().broadcastMessage(ChatColor.GREEN + "" + PASSING_PEROID +" Minute Passing Peroid Started");
					secondsLeft = 20000;
				}
				if(secondsLeft < 1000) {
					secondsLeft--;
				}
				
				
			}
		}, 20, 20);
		
		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				minutes += 1;
				if(minutes >= PASSING_PEROID+1 && noPVP) {
					getServer().broadcastMessage(ChatColor.YELLOW + "Passing Peroid Ended");
					noPVP = false;
				}
				if(minutes == PASSING_PEROID/2 + 1) {
					getServer().broadcastMessage(ChatColor.YELLOW + "" + PASSING_PEROID/2 + " minutes of passing peroid left");
				}
				if(minutes == CIRCLE_MOVES+1-CIRCLE_WARN) {
					getServer().broadcastMessage(ChatColor.YELLOW + "" + CIRCLE_WARN + " minutes till the world border moves in.");
				}
				if(minutes >= CIRCLE_MOVES+1 && !circleMoveing) {
					circleMoveing = true;
					getServer().getWorld("world").getWorldBorder().setCenter(getServer().getWorld("world").getSpawnLocation());
					getServer().getWorld("world").getWorldBorder().setSize(50, 60*5);
					getServer().broadcastMessage(ChatColor.YELLOW + "The world border is now moveing. In five minutes it will be restricted to the spawn chunck");
				}
				
			}
		}, 20*60, 20*60);
		
//		twoMinutes.scheduleAtFixedRate(new TimerTask() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//			}
//		}, 2*60*1000, 2*60*1000);
		
	}

	public void placeMiddle() {
		World world = getServer().getWorld("world");
		Location loc = world.getSpawnLocation();
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				for (int y = 30; y < 97; y++) {
					if (y % 5 == 0 && (x == 0 || z == 0)) {
						if(x == z) {
							world.getBlockAt(loc.getBlockX() + x, y, loc.getBlockZ() + z).setType(Material.GLOWSTONE);
						} else {
							world.getBlockAt(loc.getBlockX() + x, y, loc.getBlockZ() + z).setType(Material.AIR);
						}
					} else {
						world.getBlockAt(loc.getBlockX() + x, y, loc.getBlockZ() + z).setType(Material.STONE_BRICKS);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		evt.getPlayer().sendMessage(ChatColor.GOLD + "Use /rules for rules.");
	}

	private void addRecipies() {
		getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH));
		ShapedRecipe grinderRecipe = new ShapedRecipe(new ItemStack(Material.SADDLE)).shape("lll", "lsl").setIngredient('l', Material.LEATHER).
				setIngredient('s', Material.STRING);
				getServer().addRecipe(grinderRecipe);

	}

}

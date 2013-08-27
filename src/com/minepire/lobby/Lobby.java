package com.minepire.lobby;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class Lobby extends JavaPlugin implements Listener {

	public ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
	public ItemStack eye = new ItemStack(Material.EYE_OF_ENDER);
	public ItemStack arrow = new ItemStack(Material.ARROW);
	public ItemStack egg = new ItemStack(Material.DRAGON_EGG);
	public ItemStack ball = new ItemStack(Material.SNOW_BALL);
	public ItemMeta egm = egg.getItemMeta();
	public ItemMeta bam = ball.getItemMeta();
	public BookMeta bm = (BookMeta) book.getItemMeta();
	public ItemMeta em = eye.getItemMeta();
	public ItemMeta am = arrow.getItemMeta();
	public static Inventory compass;
	//Block b = getServer().getWorld("Lobby").getBlockAt(488, 17, 1526);
	//Block eg = getServer().getWorld("Lobby").getBlockAt(489, 17, 1527);


	@Override
	public void onEnable(){
		getLogger().info("Enabling Lobby plugin!");
		compass = Bukkit.createInventory(null, 9, "Where to?");

		getServer().getPluginManager().registerEvents(this, this);
	}



	@EventHandler
	public void playerMove(PlayerMoveEvent e) {
		if (e.getPlayer().getLocation().getBlockY() == 10){
			e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 488, 17, 1512));
		}
	}

	//List<String> enderToggle = new ArrayList<String>();
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e) {
		
		
		//if (!(e.getPlayer().getWorld().getName() == "Lobby")) return;
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getPlayer().getItemInHand().getType() == Material.EYE_OF_ENDER) {
				em.setDisplayName(ChatColor.GREEN + "The Eye of No Lag");
				eye.setItemMeta(em);
				am.setDisplayName(ChatColor.GREEN + "The Arrow of Players");
				arrow.setItemMeta(am);
				e.setCancelled(true);
				for (Player all : e.getPlayer().getServer().getOnlinePlayers()) {
					if (all != e.getPlayer()) {
						e.getPlayer().hidePlayer(all);
						e.getPlayer().sendMessage(ChatColor.GREEN + "You have hidden all players");
						e.getPlayer().getInventory().remove(eye);
						Bukkit.getScheduler().runTaskLater(this, new Runnable(){

							@Override
							public void run(){
								e.getPlayer().getInventory().addItem(arrow);
							}

						}, 1L);

					}
					else {
						e.getPlayer().sendMessage(ChatColor.RED	+ "You are alone. There is no one to hide.");
					}
					
				}
			}
		}

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getPlayer().getItemInHand().getType() == Material.ARROW) {
				e.setCancelled(true);
				for (Player all : e.getPlayer().getServer().getOnlinePlayers()) {
					if (all != e.getPlayer()) {
						e.getPlayer().showPlayer(all);
						e.getPlayer().sendMessage(ChatColor.RED + "You can now see players again");
						e.getPlayer().getInventory().remove(arrow);
						e.getPlayer().getInventory().addItem(eye);
					}
					
				}
			}
		}
		List<String> eggList = new ArrayList<String>();
		List<String> ballList = new ArrayList<String>();
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR	|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getPlayer().getItemInHand().getType() == Material.COMPASS) {
				e.getPlayer().openInventory(compass);
				compass.clear();
				egm.setDisplayName(ChatColor.GREEN + "Race for the Dragon");
				eggList.add("A gamemode where you have 20 min to prepare for an Ender Dragon fight!");
				egm.setLore(eggList);
				bam.setDisplayName(ChatColor.GREEN + "UnderSpleef");
				ballList.add("You spleef people from under the arena!");
				bam.setLore(ballList);
				ball.setItemMeta(bam);
				egg.setItemMeta(egm);
				compass.addItem(egg);
				compass.addItem(ball);
			}

		}
	}
	
	
	
	@EventHandler
	public void invClick(InventoryClickEvent e){
		Player p = (Player)e.getWhoClicked();
		if (e.getInventory().getName() == compass.getName()){
			e.setCancelled(true);
			//p.sendMessage(ChatColor.GREEN + "" + p.getLocation().getWorld().getName() + " is the world you're in.");
		}
		if (e.getClick().equals(ball) == true) {
			//Player p = (Player)e.getWhoClicked();
			p.teleport(new Location(p.getWorld(), 488, 17, 1526, p.getLocation().getPitch(), 90));
		}
		if (e.getClick().equals(egg) == true) {
			//Player p = (Player)e.getWhoClicked();
			//p.teleport(eg.getLocation());
		}
	}



	@EventHandler
	public void playerJoin(PlayerJoinEvent e){
		e.setJoinMessage(ChatColor.GRAY + "Join: " + e.getPlayer().getName());
		Player p = e.getPlayer();
		bm.setPages(ChatColor.BLUE + "Rules - The Minepire Network" + "\n" 
				+ ChatColor.GREEN + "1. Respect all staff (Mods, Admins, Devs, Owners)" + "\n" 
				+ ChatColor.RED + "2. No advertising" + "\n" 
				+ ChatColor.GOLD + "3. No spam or rude remarks" + "\n"
				+ ChatColor.LIGHT_PURPLE + "4. No hacking or unauthorized client mods");
		bm.setAuthor("The Minepire Network");
		bm.setDisplayName(ChatColor.YELLOW + "Rules");
		book.setItemMeta(bm);
		p.getInventory().addItem(book);
		em.setDisplayName(ChatColor.GREEN + "The Eye of No Lag");
		eye.setItemMeta(em);
		em.setDisplayName(ChatColor.GREEN + "The Arrow of Players");
		arrow.setItemMeta(am);
		p.getInventory().addItem(eye);
		p.getInventory().addItem(new ItemStack(Material.COMPASS));

	}
	@EventHandler
	public void playerLeave(PlayerQuitEvent e){
		e.setQuitMessage(ChatColor.GRAY + "Leave: " + e.getPlayer().getName());
		Player p = e.getPlayer();
		p.getInventory().clear();

	
	}

	@EventHandler
	public void dropItem(PlayerDropItemEvent e){
		e.setCancelled(true);

	}


}

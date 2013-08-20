package me.masterejzz.lobby;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class Lobby extends JavaPlugin implements Listener {

	ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
	ItemStack eye = new ItemStack(Material.EYE_OF_ENDER);
	ItemStack arrow = new ItemStack(Material.ARROW);
	BookMeta bm = (BookMeta) book.getItemMeta();
	ItemMeta em = eye.getItemMeta();
	ItemMeta am = arrow.getItemMeta();



	@Override
	public void onEnable(){
		getLogger().info("Enabling Lobby plugin!");


		getServer().getPluginManager().registerEvents(this, this);
	}




	@EventHandler
	public void playerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();

		bm.setPages(ChatColor.BLUE + "Rules - Extreme Ultra Hardcore Server" + "\n" 
				+ ChatColor.GREEN + "1. Respect all staff (Mods, Admins, Devs, Owners)" + "\n" 
				+ ChatColor.RED + "2. No advertising" + "\n" 
				+ ChatColor.GOLD + "3. No spam or rude remarks");
		bm.setAuthor("EUHC Server");
		bm.setDisplayName(ChatColor.YELLOW + "Rules");
		book.setItemMeta(bm);
		p.getInventory().addItem(book);
		em.setDisplayName(ChatColor.GREEN + "The Eye of No Lag");
		eye.setItemMeta(em);
		em.setDisplayName(ChatColor.GREEN + "The Arrow of Players");
		arrow.setItemMeta(am);
		p.getInventory().addItem(eye);

	}


	List<String> enderToggle = new ArrayList<String>();
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e) {
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

						}, 5L);

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
	}



	@EventHandler
	public void playerLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		p.getInventory().clear();

	}

	@EventHandler
	public void dropItem(PlayerDropItemEvent e){
		e.setCancelled(true);

	}


}

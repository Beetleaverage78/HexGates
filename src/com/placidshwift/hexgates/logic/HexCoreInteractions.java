package com.placidshwift.hexgates.logic;


import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.placidshwift.hexgates.HexGates;
import com.placidshwift.hexgates.libs.CustomBlockData;

public class HexCoreInteractions implements Listener {
	
	@EventHandler
	public void onHexCorePlace(BlockPlaceEvent event) {
		if (event.getBlock().getType() != Material.RESPAWN_ANCHOR) return;
				
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (!item.getItemMeta().hasLore()) return;
		
		PersistentDataContainer hexCoreData = new CustomBlockData(event.getBlock(), HexGates.main);
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		hexCoreData.set(isHexCoreKey, PersistentDataType.STRING, "TRUE");
		
		
		
		event.getPlayer().sendMessage(ChatColor.AQUA+"HExCore Placed ur James");
	}
	
	@EventHandler
	public void onHexCoreBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() != Material.RESPAWN_ANCHOR) return;
		
		PersistentDataContainer hexCoreData = new CustomBlockData(event.getBlock(), HexGates.main);
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		if (!hexCoreData.has(isHexCoreKey, PersistentDataType.STRING)) return;
		if (hexCoreData.get(isHexCoreKey, PersistentDataType.STRING).equals("TRUE")) {
			event.setDropItems(false);
			World world = event.getPlayer().getWorld();
			world.dropItemNaturally(event.getBlock().getLocation(), HexCore.createHexCore());
			hexCoreData.set(isHexCoreKey, PersistentDataType.STRING, "FALSE");
		}
	}
	
	@EventHandler
	public void onHexCoreClick(PlayerInteractEvent event) {
		// Check if player is interacting with a HexCore
		PersistentDataContainer hexCoreData = new CustomBlockData(event.getClickedBlock(), HexGates.main);
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		if (!hexCoreData.has(isHexCoreKey, PersistentDataType.STRING)) return;
		if (hexCoreData.get(isHexCoreKey, PersistentDataType.STRING).equals("TRUE") && event.getAction() == Action.RIGHT_CLICK_BLOCK) { 
			event.setCancelled(true);
			
			// Open GUI
			return;
		}
	}
	
	
}

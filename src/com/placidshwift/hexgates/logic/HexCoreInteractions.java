package com.placidshwift.hexgates.logic;


import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.placidshwift.hexgates.HexGates;
import com.placidshwift.hexgates.libs.HexCoreData;
import com.placidshwift.hexgates.libs.HexCoreDataType;

public class HexCoreInteractions implements Listener {
	
	private HexCoreGUI gui;
	public HexCoreInteractions() {gui = new HexCoreGUI();}
	
	@EventHandler
	public void onHexCorePlace(BlockPlaceEvent event) {
		if (event.getBlockPlaced().getType() != Material.BEACON) return;
				
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (!item.getItemMeta().hasLore()) return;
		
		TileState state = (TileState)event.getBlockPlaced().getState();
		PersistentDataContainer hexCoreData = state.getPersistentDataContainer();
		
		// Build HexCore Data
		HexCoreData data = new HexCoreData(event.getBlockPlaced().getLocation().serialize());
		NamespacedKey hexCoreDataKey = new NamespacedKey(HexGates.main, "ishexcore");
		hexCoreData.set(hexCoreDataKey, new HexCoreDataType(), data);
		state.update();
		
		event.getPlayer().sendMessage(HexGates.pluginFormat("HExCore Placed ur James - "));
	}
	
	@EventHandler
	public void onHexCoreBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() != Material.BEACON) return;
		
		// Check if player broke HexCore
		TileState state = (TileState)event.getBlock().getState();
		PersistentDataContainer hexCoreData = state.getPersistentDataContainer();
		
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		if (!hexCoreData.has(isHexCoreKey, new HexCoreDataType())) return;
		
		event.setDropItems(false);
		World world = event.getPlayer().getWorld();
		world.dropItemNaturally(event.getBlock().getLocation(), HexCore.createHexCore());
	}
	
	@EventHandler
	public void onHexCoreClick(PlayerInteractEvent event) {
		if (event.getClickedBlock().getType() != Material.BEACON) return;
		
		// Check if player is interacting with a HexCore
		TileState state = (TileState)event.getClickedBlock().getState();
		PersistentDataContainer hexCoreContainer = state.getPersistentDataContainer();
		
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		if (!hexCoreContainer.has(isHexCoreKey, new HexCoreDataType())) return;
		
		HexCoreData data = hexCoreContainer.get(isHexCoreKey, new HexCoreDataType());
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		event.setCancelled(true);
		Player p = event.getPlayer();
		gui.setCurrentHexLoc(data);
		if (!data.isBuilt()) {
			p.openInventory(gui.getStartGUI());
		} else {
			p.openInventory(gui.getMainGUI());
		}
		
	}
	
	// GUI Stuff
	@EventHandler
	public void onHexCoreGUIClick(InventoryClickEvent event) {
		if (!event.getInventory().equals(gui.getStartGUI())) return;
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getItemMeta() == null);
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		event.setCancelled(true);

		if (event.getInventory().equals(gui.getStartGUI())) {
			gui.onStartGUIInteract(event);
		}

		
		
	}
	
	
}

package com.placidshwift.hexgates.logic;


import java.util.ArrayList;
import java.util.HashMap;

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

import com.placidshwift.hexgates.HexGates;
import com.placidshwift.hexgates.libs.HexCoreData;
import com.placidshwift.hexgates.libs.HexCoreDataType;

/*
 * Deals with all known interactions of the HexCore/HexGate
 */
public class HexCoreInteractions implements Listener {
	
	private HexCoreGUI gui;
	public HexCoreInteractions() {gui = new HexCoreGUI();}
	
	@EventHandler
	public void onHexCorePlace(BlockPlaceEvent event) {
		// Check if Player is placing a block near a HexCore
		for (Location hcd: HexGates.hexCoreLocations.get(event.getBlock().getWorld())) {
			NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
			TileState state = (TileState)event.getBlock().getWorld().getBlockAt(hcd).getState();
			HexCoreData actualData = state.getPersistentDataContainer().get(isHexCoreKey, new HexCoreDataType());
			if (actualData.isBuilt() && actualData.isWithin(event.getBlock().getLocation()) && event.getBlock().getType() != Material.BEACON) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(HexGates.pluginFormat("&cThis block is near a HexCore ..."));
				return;
			}
		}
		
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
		
		// If HexCore exists in the global list - do not do anything
		if (HexGates.hexCoreLocations.get(event.getBlockPlaced().getWorld()).contains(data.getLocation())) return;
		
		// If not then add it to that list
		ArrayList<Location> newEntry = HexGates.hexCoreLocations.get(event.getBlockPlaced().getWorld());
		newEntry.add(data.getLocation());
		HexGates.hexCoreLocations.put(event.getBlockPlaced().getWorld(), newEntry);
		
		// Debug
		event.getPlayer().sendMessage(HexGates.pluginFormat("HExCore Placed ur James - Total: "+
				HexGates.hexCoreLocations.get(event.getBlockPlaced().getWorld()).size()
				));
	}
	
	@EventHandler
	public void onHexCoreBreak(BlockBreakEvent event) {
		// Check if Player is breaking a block part of a HexCore
		for (Location hcd: HexGates.hexCoreLocations.get(event.getBlock().getWorld())) {
			NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
			TileState state = (TileState)event.getBlock().getWorld().getBlockAt(hcd).getState();
			HexCoreData actualData = state.getPersistentDataContainer().get(isHexCoreKey, new HexCoreDataType());
			if (actualData.isBuilt() && actualData.isWithin(event.getBlock().getLocation()) && event.getBlock().getType() != Material.BEACON) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(HexGates.pluginFormat("&cThis block is part of the HexCore"));
				return;
			}
		}
		
		if (event.getBlock().getType() != Material.BEACON) return;
		
		// Check if player broke HexCore
		TileState state = (TileState)event.getBlock().getState();
		PersistentDataContainer blockData = state.getPersistentDataContainer();
		
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		if (!blockData.has(isHexCoreKey, new HexCoreDataType())) return;
		
		// Remove it from the global list of HexCores
		HexCoreData data = blockData.get(isHexCoreKey, new HexCoreDataType());
		ArrayList<Location> newEntry = HexGates.hexCoreLocations.get(event.getBlock().getWorld());
		newEntry.remove(data.getLocation());
		HexGates.hexCoreLocations.put(event.getBlock().getWorld(), newEntry);
		blockData.remove(isHexCoreKey);
		state.update();

		event.setDropItems(false);
		World world = event.getPlayer().getWorld();
		world.dropItemNaturally(event.getBlock().getLocation(), HexCore.createHexCore());
		gui.updateMainGUI(world, data);
		
		// Debug
		event.getPlayer().sendMessage(HexGates.pluginFormat("HExCore Break - Total: "+
				HexGates.hexCoreLocations.get(event.getBlock().getWorld()).size()
				));
	}
	
	@EventHandler
	public void onHexCoreClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getClickedBlock().getType() != Material.BEACON) return;
		
		// Check if player is interacting with a HexCore
		TileState state = (TileState)event.getClickedBlock().getState();
		PersistentDataContainer hexCoreContainer = state.getPersistentDataContainer();
		
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		if (!hexCoreContainer.has(isHexCoreKey, new HexCoreDataType())) return;
		
		HexCoreData data = hexCoreContainer.get(isHexCoreKey, new HexCoreDataType());
		
		event.setCancelled(true);
		Player p = event.getPlayer();
		
		gui.setCurrentHexLoc(data);
		if (!data.isBuilt()) {
			p.openInventory(gui.getStartGUI());
		} else {
			gui.updateMainGUI(p.getWorld(), data);
			gui.setCurrentHexLoc(data);
			p.openInventory(gui.getMainGUI());
		}
		
	}
	
	// GUI Stuff
	@EventHandler
	public void onHexCoreGUIClick(InventoryClickEvent event) {
		if (!event.getInventory().equals(gui.getStartGUI()) && !event.getInventory().equals(gui.getMainGUI()) 
				&& !event.getInventory().equals(gui.getHexGateGUI())) return;
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getItemMeta() == null);
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		event.setCancelled(true);

		if (event.getInventory().equals(gui.getStartGUI())) {
			gui.onStartGUIInteract(event);
		} else if (event.getInventory().equals(gui.getMainGUI()) ) {
			gui.onMainGuiInteract(event);
		} else if (event.getInventory().equals(gui.getHexGateGUI())) {
			gui.onHexCoreGUIInteract(event);
		}
	}
	
	
}

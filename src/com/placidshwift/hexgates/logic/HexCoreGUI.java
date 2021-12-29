package com.placidshwift.hexgates.logic;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import com.placidshwift.hexgates.HexGates;
import com.placidshwift.hexgates.libs.HexCoreData;
import com.placidshwift.hexgates.libs.HexCoreDataType;

public class HexCoreGUI {
	
	private Inventory startInv;
	private Inventory mainInv;
	private Inventory hexCoreInv;
	
	public HexCoreGUI() {createStartInv();createMainInv();}
	
	private void createStartInv() {
		startInv = Bukkit.createInventory(null, 27, HexGates.format("&8&l[&8HexCore&8&l]"));
		
		ItemStack cBtn = new ItemStack(Material.RED_WOOL);
		ItemMeta cMeta = cBtn.getItemMeta();
		
		// Create HexCore Button
		cMeta.setDisplayName(HexGates.format("&b&lCreate Hexgate"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(HexGates.format("&7Ensure the Hexcore is 2 blocks"));
		lore.add(HexGates.format("&7high off the ground"));
		cMeta.setLore(lore);
		cMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		cBtn.setItemMeta(cMeta);
		
		startInv.setItem(13, cBtn);
		
		// Close Button
		cBtn.setType(Material.BARRIER);
		cMeta.setDisplayName(HexGates.format("&c&lEXIT"));
		lore.clear();
		cMeta.setLore(lore);
		cBtn.setItemMeta(cMeta);
		
		startInv.setItem(26, cBtn);
	}
	
	private void createMainInv() {
		mainInv = Bukkit.createInventory(null, 54, HexGates.format("&8&l[&8HexCore&8&l]"));
		
		ItemStack cBtn = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta cMeta = cBtn.getItemMeta();
		
	}
	
	private void createHexCoreInv(HexCoreData hexCore) {
		hexCoreInv = Bukkit.createInventory(null, 27, HexGates.format("&8&l[&8HexCore&8&l]"));
		
		ItemStack cBtn = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta cMeta = cBtn.getItemMeta();
		
		// Store Current HexCoreData in ItemStack
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		cMeta.getPersistentDataContainer().set(isHexCoreKey, new HexCoreDataType(), hexCore);
		
		cMeta.setDisplayName(HexGates.format("&b&lHexCore Info"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(HexGates.format("&7Fuel Left - &c1/10"));
		lore.add(HexGates.format("&7Status - &cDISABLED"));
		cMeta.setLore(lore);
		cBtn.setItemMeta(cMeta);
		
		hexCoreInv.setItem(4, cBtn);
		
		ItemStack btns = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta btnsmeta = cBtn.getItemMeta();
		
		// Teleport Single Button
		
		
		// Teleport Multiple Button
		
		
	}
	
	/*
	 * Handles Interaction on the StartGUI
	 * - i.e. the GUI that builds the HexGate
	 */
	public void onStartGUIInteract(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		switch (e.getSlot()) {
			case 13:
				// Grab HexCore location inside the Item
				p.sendMessage(HexGates.pluginFormat("Bulding HexGate ..."));
				NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
				PersistentDataContainer cont = startInv.getItem(4).getItemMeta().getPersistentDataContainer();
				
				if (!cont.has(isHexCoreKey, new HexCoreDataType())) return;
				HexCoreData data = cont.get(isHexCoreKey, new HexCoreDataType());
				data = HexCore.buildHexGate(data);
				
				// Grab the HexCore and Update its status to Built
				TileState state = (TileState)p.getWorld().getBlockAt(data.getLocation()).getState();
				PersistentDataContainer hexCont = state.getPersistentDataContainer();
				
				if (!hexCont.has(isHexCoreKey, new HexCoreDataType())) return;
				
				data.setBuilt(true);
				hexCont.set(isHexCoreKey, new HexCoreDataType(), data);
				state.update();
				
				p.closeInventory();
				
			case 26:
				p.closeInventory();
		}
	}
	
	/*
	 * Handles Interaction in the Main GUI
	 * - i.e. the GUI that displays the HexCores/HexGates
	 */
	public void onMainGuiInteract(InventoryClickEvent e) {
		int slot = e.getSlot();
		Player p = (Player)e.getWhoClicked();
		for (int i = 10;i < 17;i++) {
			ItemStack core = mainInv.getItem(i);
			if (i == slot && core != null && core.hasItemMeta() && core.getType() == Material.BEACON) {
				NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
				PersistentDataContainer itemData = core.getItemMeta().getPersistentDataContainer();
				if (itemData.has(isHexCoreKey, new HexCoreDataType())) {
					HexCoreData hexCoreData = itemData.get(isHexCoreKey, new HexCoreDataType());
					createHexCoreInv(hexCoreData);
					
					p.sendMessage(HexGates.pluginFormat("&7Teleporting to "+hexCoreData.getLocation().getBlockX()
							+" "+hexCoreData.getLocation().getBlockY()
							+" "+hexCoreData.getLocation().getBlockZ()));
					
					ArrayList<Player> players = new ArrayList<Player>();
					players.add(p);
					HexCore.teleport(players, hexCoreData.getLocation());
					return;
				}
			}
		}
	}
	
	/*
	 * Handles GUI Interaction with specific HexCores/HexGates
	 */
	public void onHexCoreGUIInteract(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		switch(e.getSlot()) {
			case 14:
		}
	}
	
	/*
	 * Sets the current HexCore/HexGate inside the GUI
	 * - This is the HexCore/HexGate that is currently interacting with the player
	 */
	public void setCurrentHexLoc(HexCoreData data) {
		ItemStack cBtn = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta cMeta = cBtn.getItemMeta();
		
		// Store Current HexCore Location in ItemStack
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		cMeta.getPersistentDataContainer().set(isHexCoreKey, new HexCoreDataType(), data);
		
		cMeta.setDisplayName(HexGates.format("&b&lHexCore Info"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(HexGates.format("&7Fuel Left - &c1/10"));
		lore.add(HexGates.format("&7Status - &cDISABLED"));
		cMeta.setLore(lore);
		cBtn.setItemMeta(cMeta);
		
		startInv.setItem(4, cBtn);
		mainInv.setItem(4, cBtn);
	}
	
	/*
	 * Update the MainGUI
	 * - Updates & Displays all current HexCores & HexGates in the world
	 */
	public void updateMainGUI(World w, HexCoreData currHexCore) {
		createMainInv();
		int count = 1;
		int slot = 10;
		NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
		for (Location loc: HexGates.hexCoreLocations.get(w)) {
			ItemStack cBtn = new ItemStack(Material.BEACON);
			ItemMeta cMeta = cBtn.getItemMeta();
			
			TileState state = (TileState)w.getBlockAt(loc).getState();
			HexCoreData hcd = state.getPersistentDataContainer().get(isHexCoreKey, new HexCoreDataType());
			
			if (slot < 17 && !hcd.equals(currHexCore)) {
				cMeta.setDisplayName(HexGates.format("&bHexGate #"+count));
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(HexGates.format("&7Fuel Left | &c1/10"));
				lore.add(HexGates.format("&7Status | &cDISABLED"));
				Location hexCoreLoc = hcd.getLocation();
				lore.add(HexGates.format("&7Location (xyz) | "+hexCoreLoc.getBlockX()+", "+hexCoreLoc.getBlockY()+", "+hexCoreLoc.getBlockZ()));
				cMeta.setLore(lore);
				cMeta.getPersistentDataContainer().set(isHexCoreKey, new HexCoreDataType(), hcd);
				cBtn.setItemMeta(cMeta);
				
				mainInv.setItem(slot, cBtn);
				count++;slot++;
			}
		}
	}
	
	public Inventory getStartGUI() {return this.startInv;}
	public Inventory getMainGUI() {return this.mainInv;}
	public Inventory getHexGateGUI() {return this.hexCoreInv;}
}

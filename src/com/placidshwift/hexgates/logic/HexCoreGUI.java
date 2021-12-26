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
	
	public HexCoreGUI() {createStartInv();createMainInv();}
	
	private void createStartInv() {
		startInv = Bukkit.createInventory(null, 27, HexGates.format("&8&l[&8HexCore&8&l]"));
		
		ItemStack cBtn = new ItemStack(Material.RED_WOOL);
		ItemMeta cMeta = cBtn.getItemMeta();
		
		// Create HexCore Button
		cMeta.setDisplayName(HexGates.format("&b&lCreate Hexgate"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(HexGates.format("&7Ensure the Hexcore is 2 blocks high off the ground"));
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
		mainInv = Bukkit.createInventory(null, 27, HexGates.format("&8&l[&8HexCore&8&l]"));
		
		ItemStack cBtn = new ItemStack(Material.RED_WOOL);
		ItemMeta cMeta = cBtn.getItemMeta();
	}
	
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
				HexCore.buildHexGate(data.getLocation());
				
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
	
	public void setCurrentHexLoc(HexCoreData data) {
		ItemStack cBtn = new ItemStack(Material.BEACON);
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
	
	public Inventory getStartGUI() {return this.startInv;}
	public Inventory getMainGUI() {return this.mainInv;}
}

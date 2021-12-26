package com.placidshwift.hexgates.logic;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HexCore {
	
	public static ItemStack createHexCore() {
		ItemStack hexCore = new ItemStack(Material.BEACON);
		ItemMeta meta = hexCore.getItemMeta();
		
		meta.setDisplayName(ChatColor.AQUA+"HexCore");
		ArrayList<String> itemDesc = new ArrayList<String>(
				Arrays.asList(ChatColor.GRAY+"The foundation of all HexTech contraptions"));
		meta.setLore(itemDesc);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		hexCore.setItemMeta(meta);
		return hexCore;
	}
	
	public static void buildHexGate(Location loc) {
		
	}
}

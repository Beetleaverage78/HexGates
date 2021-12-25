package com.placidshwift.hexgates;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import com.placidshwift.hexgates.libs.CustomBlockData;
import com.placidshwift.hexgates.logic.HexCore;

public class CommandHandler implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		// Player Commands
		if (sender instanceof Player) {
			Player player = (Player)sender;
				
			if (args.length == 0) {
				// Send command list
				return true;
			}
			
			switch(args[0]) {
				case "give":					
					ItemStack hexCore = HexCore.createHexCore();
					// Give hexcore
					if (player.getInventory().firstEmpty() == -1) {
						// Inventory Full
						World world = player.getWorld();
						Location loc = player.getLocation();
						world.dropItemNaturally(loc, hexCore);
						player.sendMessage("HexCore was dropped from the heavens "+player.getName());
						return true;
					}
					player.getInventory().addItem(hexCore);
					player.sendMessage("Giving Hex Core to "+player.getName());
					return true;
				case "build":
					World world = player.getWorld();
					Location loc = player.getLocation();
					
					ItemStack bruh = new ItemStack(Material.DIAMOND);
					world.dropItemNaturally(loc, bruh);
					
					
			}
			
		}
		
		// Console Commands
		else if (sender instanceof ConsoleCommandSender) {
			
		}
		
		return false;
	}
	
}

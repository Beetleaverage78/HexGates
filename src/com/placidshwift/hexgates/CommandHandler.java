package com.placidshwift.hexgates;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
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
					
					// Foundation
					for (int y = 0; y < 2; y++) {
						for (int z = 0; z < 5; z++) {
							for (int x = 0; x < 5; x++) {
								world.getBlockAt(loc.getBlockX()-2+x, loc.getBlockY()-2-y, loc.getBlockZ()-2+z).setType(Material.POLISHED_ANDESITE);
							}
						}
					}
					
			
					
					// Hex Gate
					world.getBlockAt(loc).setType(Material.RESPAWN_ANCHOR);
					
					// Layer 1
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.CRYING_OBSIDIAN);
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.CRYING_OBSIDIAN);
					
					world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ()-2).setType(Material.SEA_LANTERN);
					world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()-2).setType(Material.SEA_LANTERN);
					world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ()+2).setType(Material.SEA_LANTERN);
					world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()+2).setType(Material.SEA_LANTERN);
					
					Directional direction;
					Block block;
					Stairs stair;
					
					// North Layer 1 Stairs
					
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()-2).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // Middle
					block = world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()-2);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.SOUTH);
					block.setBlockData(direction);
					
					
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()-2).setType(Material.COBBLED_DEEPSLATE_STAIRS); // Left
					block = world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()-2);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.EAST);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_RIGHT);
					block.setBlockData(stair);

					
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()-2).setType(Material.COBBLED_DEEPSLATE_STAIRS); // Right
					block = world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()-2);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.WEST);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_LEFT);
					block.setBlockData(stair);
					
					// South Layer 1 Stairs
					
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()+2).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // Middle
					block = world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()+2);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.NORTH);
					block.setBlockData(direction);
					
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()+2).setType(Material.COBBLED_DEEPSLATE_STAIRS); // Left
					block = world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()+2);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.EAST);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_LEFT);
					block.setBlockData(stair);
					
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()+2).setType(Material.COBBLED_DEEPSLATE_STAIRS); // Right
					block = world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()+2);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.WEST);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_RIGHT);
					block.setBlockData(stair);
					
					// East Layer 1 Stairs
					
					world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // Middle
					block = world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ());
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.WEST);
					block.setBlockData(direction);
					
					world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // LEFT
					block = world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()-1);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.NORTH);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_LEFT);
					block.setBlockData(stair);
					
					
					
					world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // RIGHT
					block = world.getBlockAt(loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()+1);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.SOUTH);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_RIGHT);
					block.setBlockData(stair);
					
					
					// West Layer 1 Stairs
					
					
					world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ()).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // Middle
					block = world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ());
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.EAST);
					block.setBlockData(direction);
					
					world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // LEFT
					block = world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ()+1);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.SOUTH);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_LEFT);
					block.setBlockData(stair);
					
					
					world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.COBBLED_DEEPSLATE_STAIRS);  // RIGHT
					block = world.getBlockAt(loc.getBlockX()-2, loc.getBlockY()-1, loc.getBlockZ()-1);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.NORTH);
					block.setBlockData(direction);
					stair = (Stairs) block.getBlockData();
					stair.setShape(Shape.INNER_RIGHT);
					block.setBlockData(stair);
					
					// Layer 2
					
					// North Layer 2
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-2, loc.getBlockZ()-3).setType(Material.POLISHED_ANDESITE);  // LEFT
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-2, loc.getBlockZ()-3).setType(Material.POLISHED_ANDESITE);  // RIGHT
					
					// North Layer 2 Stairs
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-2, loc.getBlockZ()-3).setType(Material.STONE_BRICK_STAIRS);  // RIGHT
					block = world.getBlockAt(loc.getBlockX(), loc.getBlockY()-2, loc.getBlockZ()-3);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.SOUTH);
					block.setBlockData(direction);
					
					
					// Layer 3
					
					
					// North Layer 3
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-3, loc.getBlockZ()-3).setType(Material.POLISHED_ANDESITE);  // Middle
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-3, loc.getBlockZ()-3).setType(Material.POLISHED_ANDESITE);  // LEFT
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-3, loc.getBlockZ()-3).setType(Material.POLISHED_ANDESITE);  // RIGHT
					world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-3, loc.getBlockZ()-4).setType(Material.POLISHED_ANDESITE);  // FRONT LEFT
					world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-3, loc.getBlockZ()-4).setType(Material.POLISHED_ANDESITE);  // FRONT RIGHT
					
					// North Layer 3 Stairs
					world.getBlockAt(loc.getBlockX(), loc.getBlockY()-3, loc.getBlockZ()-4).setType(Material.STONE_BRICK_STAIRS);  // Front Midlee
					block = world.getBlockAt(loc.getBlockX(), loc.getBlockY()-3, loc.getBlockZ()-4);
					direction = (Directional) block.getBlockData();
					direction.setFacing(BlockFace.SOUTH);
					block.setBlockData(direction);
					
			}
			
		}
		
		// Console Commands
		else if (sender instanceof ConsoleCommandSender) {
			
		}
		
		return false;
	}
	
}

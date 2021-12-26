package com.placidshwift.hexgates.logic;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
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
		World world = loc.getWorld();
		
		// Foundation
		for (int y = 0; y < 2; y++) {
			for (int z = 0; z < 5; z++) {
				for (int x = 0; x < 5; x++) {
					world.getBlockAt(loc.getBlockX()-2+x, loc.getBlockY()-2-y, loc.getBlockZ()-2+z).setType(Material.POLISHED_ANDESITE);
				}
			}
		}
		
		
		// Layer 1
		world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()-1).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.IRON_BLOCK);
		world.getBlockAt(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()+1).setType(Material.IRON_BLOCK);
		
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

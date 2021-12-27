package com.placidshwift.hexgates;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import com.placidshwift.hexgates.libs.HexCoreData;
import com.placidshwift.hexgates.libs.HexCoreDataType;
import com.placidshwift.hexgates.logic.HexCoreInteractions;



public class HexGates extends JavaPlugin {

	public static HashMap<World, ArrayList<HexCoreData>> hexCoreLocations;
	public static HexGates main;
	
	@Override
	public void onEnable() {
		main = this;
		
		// Find all Existing HexCores in a World
		hexCoreLocations = new HashMap<World, ArrayList<HexCoreData>>();
		ArrayList<HexCoreData> hexcores;
		for (World w: this.getServer().getWorlds()) {
			if (w.getEnvironment() == World.Environment.NORMAL) {
				hexcores = new ArrayList<HexCoreData>();
				for (Chunk c: w.getLoadedChunks()) {
					for (BlockState b: c.getTileEntities()) {
						if (b instanceof Beacon) {
							TileState state = (TileState) b.getBlock().getState();
							PersistentDataContainer data = state.getPersistentDataContainer();
							NamespacedKey isHexCoreKey = new NamespacedKey(HexGates.main, "ishexcore");
							if (data.has(isHexCoreKey, new HexCoreDataType())) {
								hexcores.add(data.get(isHexCoreKey, new HexCoreDataType()));
							}
						}
					}
				}
				hexCoreLocations.put(w, hexcores);
			}
		}
		
		this.getServer().getPluginManager().registerEvents(new HexCoreInteractions(), this);
		this.getCommand("hg").setExecutor(new CommandHandler());
	}
	
	
	@Override
	public void onDisable() {

	}
	

	public static String format(String msg) {return ChatColor.translateAlternateColorCodes('&', msg);}
	public static String pluginFormat(String msg) {return ChatColor.translateAlternateColorCodes('&', "&8[&bHexGates&8] &7"+msg);}
	
}

package com.placidshwift.hexgates;

import org.bukkit.plugin.java.JavaPlugin;

import com.placidshwift.hexgates.logic.HexCoreInteractions;



public class HexGates extends JavaPlugin {

	public static HexGates main;
	
	@Override
	public void onEnable() {
		main = this;
		
		this.getServer().getPluginManager().registerEvents(new HexCoreInteractions(), this);
		this.getCommand("hg").setExecutor(new CommandHandler());
	}
	
	
	@Override
	public void onDisable() {

	}
	

	
}

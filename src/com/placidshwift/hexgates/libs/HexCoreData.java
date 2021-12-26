package com.placidshwift.hexgates.libs;

import java.io.Serializable;
import java.util.Map;

import org.bukkit.Location;


public class HexCoreData implements Serializable {

	private boolean built;
	private Map<String, Object> hexCoreLoc;
	
	public HexCoreData(Map<String, Object> hexCoreLoc) {
		this.built = false;
		this.hexCoreLoc = hexCoreLoc;
		
	}
	
	public void setBuilt(boolean bool) {this.built = bool;}
	public boolean isBuilt() {return this.built;}
	public Location getLocation() {return Location.deserialize(hexCoreLoc);}
}

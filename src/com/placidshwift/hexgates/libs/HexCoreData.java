package com.placidshwift.hexgates.libs;

import java.io.Serializable;
import java.util.Map;

import org.bukkit.Location;


public class HexCoreData implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean built;
	private Map<String, Object> hexCoreLoc;
	private double x,y,z;
	
	public HexCoreData(Map<String, Object> hexCoreLoc) {
		this.built = false;
		this.hexCoreLoc = hexCoreLoc;
		this.x =0;this.y=0;this.z=0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof HexCoreData) {
			HexCoreData obj = (HexCoreData)o;
			Location thisLoc = Location.deserialize(hexCoreLoc);
			Location objLoc = obj.getLocation();
			if (thisLoc.equals(objLoc)) {
				return true;
			}
		}
		return false;
	}
	
	public void setBuilt(boolean bool) {this.built = bool;}
	public void setArea(double x, double y, double z) {this.x = x;this.y = y;this.z = z;}
	public boolean isBuilt() {return this.built;}
	public Location getLocation() {return Location.deserialize(hexCoreLoc);}
	
	public boolean isWithin(Location loc) {
		
		return false;
	}
}

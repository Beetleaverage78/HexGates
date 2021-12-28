package com.placidshwift.hexgates.libs;

import java.io.Serializable;
import java.util.Map;

import org.bukkit.Location;

/*
 * Represents the Data stored inside a HexCore/HexGate
 * - This data is serializable so it can be stored as persistent data
 */
public class HexCoreData implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean built;
	private Map<String, Object> hexCoreLoc;
	private double xMin, xMax, yMin, yMax, zMin, zMax;
	
	public HexCoreData(Map<String, Object> hexCoreLoc) {
		this.built = false;
		this.hexCoreLoc = hexCoreLoc;
		this.xMin = 0;
		this.xMax = 0;
		this.yMin = 0;
		this.yMax = 0;
		this.zMin = 0;
		this.zMax = 0;
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
	public boolean isBuilt() {return this.built;}
	public Location getLocation() {return Location.deserialize(hexCoreLoc);}
	
	public void setParameters(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.zMin = zMin;
		this.zMax = zMax;
		
	}
	
	public boolean isWithin(Location loc) {
		if (loc.getX() < xMin || loc.getX() > xMax) {
			return false;
		}
		if (loc.getY() < yMin || loc.getY() > yMax) {
			return false;
		}
		if (loc.getZ() < zMin || loc.getZ() > zMax) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		if (this.getLocation() == null) return "HC - NULL";
		return "HC - "
				+ "LOC("+this.getLocation().getBlockX()+","+this.getLocation().getBlockY()+","+this.getLocation().getBlockZ()+") -";
	}
}
